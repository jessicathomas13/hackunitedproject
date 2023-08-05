package com.game2d.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game2d.game.Displays.PlayerScreen;

public class Game2D extends Game {

	public static final int WIDTH =2500;
	public static final int HEIGHT =1400;
	public static final float PPM= 99;

	public static final short FLOORBIT= 1;
	public static final short HEROBIT= 2;

	public static final short TREEBIT= 16;
	public static final short MONSTERBIT= 32;
	public static final short MONSTERHEADBIT = 128;

	public SpriteBatch batch;

	public static AssetManager assetManager;

	
	@Override
	public void create () {
		batch = new SpriteBatch();

		assetManager = new AssetManager();
		assetManager.load("Sounds/Soundtrack.ogg", Music.class);
		assetManager.load("Sounds/Jump.ogg", Music.class);
		assetManager.load("Sounds/Score.wav", Music.class);
		assetManager.finishLoading();

		setScreen(new PlayerScreen(this));
	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		super.dispose();
		assetManager.dispose();
		batch.dispose();

	}
}
