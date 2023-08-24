package com.game2d.game.Displays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game2d.game.Game2D;

import jdk.javadoc.internal.doclets.formats.html.markup.Text;

public class MainMenuScreen implements Screen {
    private static final int PLAY_BUTTON_WIDTH = 200;
    private static final int PLAY_BUTTON_HEIGHT = 100;
    private static final int PLAY_BUTTON_Y = 100;

    private static final int EXIT_BUTTON_WIDTH = 190;
    private static final int EXIT_BUTTON_HEIGHT = 100;
    private static final int EXIT_BUTTON_Y = 20;
    Game2D game;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont fontt;

    Texture playButtonActive;
    Texture playButtonInActive;
    Texture exitButtonInActive;
    Texture exitButtonActive;
    Texture background;
    Texture gamename;
    public MainMenuScreen(Game2D game){
        this.game = game;

        viewport=new FitViewport(Game2D.WIDTH, Game2D.HEIGHT, new OrthographicCamera());

        background = new Texture("sky2.jpg");
        gamename = new Texture("gamename.png");
        playButtonActive = new Texture("playbutton.png");
        playButtonInActive = new Texture("playbuttoninactive.png");
        exitButtonActive = new Texture("exitbutton.png");
        exitButtonInActive = new Texture("exitbuttoninactive.png");

        Gdx.graphics.setWindowedMode(1800, 950);


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 50,0,800, 500);
        game.batch.draw(gamename, 200, 300, 300, 95);


        int x = 250;

        if (Gdx.input.getX()-600 < x + PLAY_BUTTON_WIDTH && Gdx.input.getX()-600>x && 800-Gdx.input.getY()<PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && 800-Gdx.input.getY()>PLAY_BUTTON_Y){
            game.batch.draw(playButtonActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                game.setScreen(new PlayerScreen(game));
            }
        } else{
            game.batch.draw(playButtonInActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
        if (Gdx.input.getX()-600 < x + EXIT_BUTTON_WIDTH && Gdx.input.getX()-600>x && 900-Gdx.input.getY()<EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && 900-Gdx.input.getY()>EXIT_BUTTON_Y){
            game.batch.draw(exitButtonActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else{
            game.batch.draw(exitButtonInActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }


        //if (Gdx.input.getX()<x + PLAY_BUTTON_WIDTH && Gdx.input.getX()<x){



        game.batch.end();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);

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
        stage.dispose();

    }
}
