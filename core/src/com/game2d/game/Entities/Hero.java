package com.game2d.game.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.game2d.game.Game2D;

public class Hero extends Sprite {
    public World world;
    public Body body;
    public Hero (World world){
        this.world= world;
        defineHero();


    }
    private void defineHero() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(50/Game2D.PPM,50/Game2D.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10000000/Game2D.PPM);
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef);

    }
}

