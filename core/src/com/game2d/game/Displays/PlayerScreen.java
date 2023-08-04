package com.game2d.game.Displays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game2d.game.Entities.Hero;
import com.game2d.game.Game2D;
import com.game2d.game.Overlay.HUD;

public class PlayerScreen implements Screen {

    private Game2D game;
    Texture texture;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private HUD hud;
    private TmxMapLoader tmxMapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private World world;
    private Hero player;
    private Box2DDebugRenderer br;


    public PlayerScreen(Game2D game){
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new FitViewport(Game2D.WIDTH/Game2D.PPM, Game2D.HEIGHT/Game2D.PPM, camera);
        hud = new HUD(game.batch);
        tmxMapLoader = new TmxMapLoader();
        map = tmxMapLoader.load("map.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(map,1/Game2D.PPM );
        camera.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0,-15f), true);
        br = new Box2DDebugRenderer();
        player= new Hero(world);

        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for(MapObject mapObject : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth()/2)/Game2D.PPM, (rectangle.getY() + rectangle.getHeight()/2)/Game2D.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox((rectangle.getWidth()/2)/Game2D.PPM, (rectangle.getHeight()/2)/Game2D.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

    }
    @Override
    public void show() {

    }

    public void controlInput(float deltatime){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.body.applyLinearImpulse(new Vector2(0,9f), player.body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x<=3){
            player.body.applyLinearImpulse(new Vector2(0.5f,0), player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x>=-3){
            player.body.applyLinearImpulse(new Vector2(-0.5f,0), player.body.getWorldCenter(),true);
        }

    }
    public void update(float deltatime){
        controlInput(deltatime);
        world.step(1/60f, 6, 2);

        camera.position.x = player.body.getPosition().x;
        camera.update();
        orthogonalTiledMapRenderer.setView(camera);

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        orthogonalTiledMapRenderer.render();
        br.render(world, camera.combined);
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
