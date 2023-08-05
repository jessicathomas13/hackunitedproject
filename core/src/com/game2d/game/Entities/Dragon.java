package com.game2d.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.game2d.game.Displays.PlayerScreen;
import com.game2d.game.Game2D;

public class Dragon extends Monster {
    private Animation<TextureRegion> walking;;
    private float time;
    private boolean destroy;
    private boolean destroyed;
    private Array<TextureRegion> frames;
    public Dragon(PlayerScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int k=0; k<5; k++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("monster"), k*190, 0, 189, 200));
        }
        walking= new Animation<>(0.2f,frames);
        setBounds(getX(),getY(), 190/Game2D.PPM, 200/Game2D.PPM);
        time=0;
        destroy=false;
        destroyed=false;
    }

    @Override
    public void hitHead() {
        destroy = true;
    }


    public void update (float deltatime){
        time+= deltatime;
        if (destroy && !destroyed){
            world.destroyBody(body);
            destroyed=true;
            System.out.println("dead");
            setRegion(new TextureRegion(screen.getAtlas().findRegion("deadmonster"), 1, 0, 184, 184));
            time=0;
        }
        else if(!destroyed){
            setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
            setRegion(walking.getKeyFrame(time, true));
        }
    }


    @Override
    protected void defineMonster() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(75/Game2D.PPM);
        fixtureDef.filter.categoryBits= Game2D.MONSTERBIT;
        fixtureDef.filter.maskBits=Game2D.FLOORBIT|
                Game2D.TREEBIT|
                Game2D.HEROBIT|
                Game2D.MONSTERBIT;
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef);

        PolygonShape head = new PolygonShape();
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(-50,77).scl(1/Game2D.PPM);
        vertices[1] = new Vector2(50,77).scl(1/Game2D.PPM);
        vertices[2] = new Vector2(-30,60).scl(1/Game2D.PPM);
        vertices[3] = new Vector2(30,60).scl(1/Game2D.PPM);
        head.set(vertices);
        fixtureDef.shape = head;
        fixtureDef.restitution = 0.5f;
        fixtureDef.filter.categoryBits = Game2D.MONSTERHEADBIT;
        body.createFixture(fixtureDef).setUserData(this);
    }
}
