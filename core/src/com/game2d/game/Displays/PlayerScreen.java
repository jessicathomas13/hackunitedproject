package com.game2d.game.Displays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game2d.game.Entities.Dragon;
import com.game2d.game.Entities.Hero;
import com.game2d.game.Game2D;
import com.game2d.game.Miscellaneous.WorldCreator;
import com.game2d.game.Overlay.HUD;

public class PlayerScreen implements Screen {

    private Game2D game;
    private TextureAtlas atlas;
    private OrthographicCamera camera;
    private Viewport gamePort;
    private HUD hud;
    private TmxMapLoader tmxMapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private World world;
    private Dragon dragon;
    private Hero player;
    private Box2DDebugRenderer br;
    private Music music;



    public PlayerScreen(Game2D game){
        atlas= new TextureAtlas("characterentities.pack");
        this.game = game;
        camera = new OrthographicCamera();
        gamePort = new FitViewport(Game2D.WIDTH/Game2D.PPM, Game2D.HEIGHT/Game2D.PPM, camera);
        Gdx.graphics.setWindowedMode(1200, 900);
        hud = new HUD(game.batch);
        tmxMapLoader = new TmxMapLoader();
        map = tmxMapLoader.load("map.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(map,1/Game2D.PPM );
        camera.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0,-20f), true);
        br = new Box2DDebugRenderer();
        new WorldCreator(this);
        player= new Hero(this);

        new WorldCreator(this);

        //world.setContactListener(new WorldContactListener);

        music = Game2D.assetManager.get("Sounds/Soundtrack.ogg", Music.class);
        music.setVolume(0.1f);
        music.setLooping(true);
        music.play();
        dragon = new Dragon(this, 408/Game2D.PPM, 408/Game2D.PPM);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }
    @Override
    public void show() {

    }

    public void controlInput(float deltatime){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            player.body.applyLinearImpulse(new Vector2(0,9f), player.body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x<=3){
            player.body.applyLinearImpulse(new Vector2(2f,0), player.body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x>=-3){
            player.body.applyLinearImpulse(new Vector2(-2f,0), player.body.getWorldCenter(),true);
        }

    }
    public void update(float deltatime){
        controlInput(deltatime);
        world.step(1/60f, 6, 2);
        player.update(deltatime);
        dragon.update(deltatime);

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
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        //dragon.draw(game.batch);
        player.draw(game.batch);
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }
    public TiledMap getMap(){
        return map;
    }

    public World getWorld() {
        return world;
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
        world.dispose();
        orthogonalTiledMapRenderer.dispose();
        map.dispose();
        br.dispose();
        hud.dispose();

    }
}
