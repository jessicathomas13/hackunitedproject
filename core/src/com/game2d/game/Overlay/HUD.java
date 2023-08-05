package com.game2d.game.Overlay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game2d.game.Game2D;

public class HUD implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private static Integer score;

    private static Label scoreLabel;
    private Label levelLabel;
    private Label nameofgame;

    @SuppressWarnings("DefaultLocale")
    public HUD(SpriteBatch spriteBatch){
        score = 0;

        viewport = new FitViewport(Game2D.WIDTH, Game2D.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        BitmapFont font= new BitmapFont() ;

        font.getData().setScale(3, 3);

        scoreLabel = new Label((String.format("%03d",score)), new Label.LabelStyle(font, Color.GOLD));
        levelLabel = new Label ("LVL 1", new Label.LabelStyle(font , Color.GOLD));
        nameofgame = new Label ("The Protagonist", new Label.LabelStyle(font, Color.GOLD));

        table.add(nameofgame).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();

        stage.addActor(table);
    }

    public static void addScore(int val){
        score+=val;
        scoreLabel.setText(String.format("%03d",score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
