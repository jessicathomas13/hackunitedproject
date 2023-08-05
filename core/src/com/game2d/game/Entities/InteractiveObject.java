package com.game2d.game.Entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game2d.game.Game2D;

import java.awt.Rectangle;

public abstract class InteractiveObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.bounds = bounds;
        this.map = map;
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((float) ((bounds.getX() + bounds.getWidth()/2) / Game2D.PPM), (float) ((bounds.getY() + bounds.getHeight()/2) / Game2D.PPM));
        body = world.createBody(bodyDef);
        shape.setAsBox((float) (bounds.getWidth()/2/Game2D.PPM), (float) (bounds.getHeight()/2/Game2D.PPM));
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
    }

    public abstract void headHit();


}
