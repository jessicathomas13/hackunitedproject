package com.game2d.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.game2d.game.Displays.PlayerScreen;
import com.game2d.game.Game2D;

public class Dragon extends Monster {
    private Animation<TextureRegion> walking;;
    private float time;
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
    }

    public void update (float deltatime){
        time+= deltatime;
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
        setRegion(walking.getKeyFrame(time, true));
    }


    @Override
    protected void defineMonster() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(90/ Game2D.PPM,550/Game2D.PPM);
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

    }
}
