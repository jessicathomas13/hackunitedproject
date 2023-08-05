package com.game2d.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.game2d.game.Displays.PlayerScreen;
import com.game2d.game.Game2D;

public class Hero extends Sprite {
    public World world;
    private TextureRegion heroidle;
    public Body body;
    public Hero (World world, PlayerScreen screen){

        super(screen.getAtlas().findRegion("mainhero"));
        this.world= world;
        defineHero();
        heroidle= new TextureRegion(getTexture(), 1, 1, 135, 135);
        setBounds(1, 1, 135/Game2D.PPM, 135/Game2D.PPM);
        setRegion(heroidle);


    }

    public void update(float deltatime){
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
    }
    private void defineHero() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(90/Game2D.PPM,550/Game2D.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(55/Game2D.PPM);
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef);

    }
}

