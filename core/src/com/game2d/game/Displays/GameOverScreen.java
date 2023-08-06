package com.game2d.game.Displays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game2d.game.Game2D;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private BitmapFont fontt;

    public GameOverScreen(Game game){
        this.game=game;
        viewport=new FitViewport(Game2D.WIDTH, Game2D.HEIGHT, new OrthographicCamera());
        stage=new Stage(viewport, ((Game2D) game).batch);
        fontt= new BitmapFont();
        fontt.getData().setScale(5, 5);
        Label.LabelStyle font = new Label.LabelStyle(fontt, Color.WHITE);
        Table table=new Table();
        table.center();
        table.setFillParent(true);
        Label gameover = new Label("YOU DIED", font);
        Label playagain = new Label("Click to Play Again", font);
        table.add(gameover).expandX();
        table.row();
        table.add(playagain).expandX().padTop(10f);
        stage.addActor(table);

     }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            game.setScreen(new PlayerScreen((Game2D) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
