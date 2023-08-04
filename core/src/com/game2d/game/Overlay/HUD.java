package com.game2d.game.Overlay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game2d.game.Game2D;

public class HUD {
    public Stage stage;
    private Viewport viewport;
    private Integer score;

    Label scoreLabel;
    Label levelLabel;
    Label nameofgame;

    @SuppressWarnings("DefaultLocale")
    public HUD(SpriteBatch spriteBatch){
        score = 0;

        viewport = new FitViewport(Game2D.WIDTH, Game2D.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        scoreLabel = new Label((String.format("%03d",score)), new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        levelLabel = new Label ("1", new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        nameofgame = new Label ("The Protagonist", new Label.LabelStyle(new BitmapFont(), Color.GOLD));

        table.add(nameofgame).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        stage.addActor(table);
    }
}
