package com.game2d.game.Miscellaneous;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game2d.game.Displays.PlayerScreen;
import com.game2d.game.Entities.Dragon;
import com.game2d.game.Game2D;



public class WorldCreator {

    private Array<Dragon> dragons;
    public WorldCreator(PlayerScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for(MapObject mapObject : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth()/2)/ Game2D.PPM, (rectangle.getY() + rectangle.getHeight()/2)/Game2D.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox((rectangle.getWidth()/2)/Game2D.PPM, (rectangle.getHeight()/2)/Game2D.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        for(MapObject mapObject : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth()/2)/ Game2D.PPM, (rectangle.getY() + rectangle.getHeight()/2)/Game2D.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox((rectangle.getWidth()/2)/Game2D.PPM, (rectangle.getHeight()/2)/Game2D.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = Game2D.TREEBIT;
            body.createFixture(fixtureDef);
        }

        dragons = new Array<Dragon>();
        for(MapObject mapObject : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            dragons.add(new Dragon(screen, rectangle.getX()/Game2D.PPM, rectangle.getY()/Game2D.PPM));
        }

    }

    public Array<Dragon> getDragons() {
        return dragons;
    }
}
