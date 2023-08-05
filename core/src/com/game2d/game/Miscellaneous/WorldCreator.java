package com.game2d.game.Miscellaneous;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game2d.game.Game2D;

public class WorldCreator {
    public WorldCreator(World world, TiledMap map){
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for(MapObject mapObject : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth()/2)/ Game2D.PPM, (rectangle.getY() + rectangle.getHeight()/2)/Game2D.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox((rectangle.getWidth()/2)/Game2D.PPM, (rectangle.getHeight()/2)/Game2D.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }
}