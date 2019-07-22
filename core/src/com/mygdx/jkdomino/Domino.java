package com.mygdx.jkdomino;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.jkdomino.effect.SoundEffect;
import com.mygdx.jkdomino.objects.Tile;
import com.mygdx.jkdomino.scenes.GameScene;
import com.mygdx.jkdomino.scenes.StartScene;

public class Domino extends Game {
	public static AssetManager assetManager;
	public static float SH = 960;
	public static float SW = 540;
	public static int modePlay = 0;

	@Override
	public void create() {
		assetManager = new AssetManager();
		loadAssets();
		assetManager.finishLoading();
		initAssets();
		setScreen(new StartScene(this));
	}

	private void initAssets() {

		SoundEffect.initSound();
		Tile.initAssets();
	}

	private void loadAssets() {
		assetManager.load("badlogic.jpg",Texture.class);
		assetManager.load("domino.png",Texture.class);
		assetManager.load("bgstartScene.png",Texture.class);
		assetManager.load("label.png",Texture.class);
		assetManager.load("mutilplayerBtn.png",Texture.class);
		assetManager.load("playBtn.png",Texture.class);
		assetManager.load("frameTakeCards.png",Texture.class);
		assetManager.load("framePassUp.png",Texture.class);
		assetManager.load("framePassDown.png",Texture.class);
		assetManager.load("framePassLeft.png",Texture.class);
		///////////////////Sound/////////////////////
		assetManager.load("sound/Pop1.mp3", Sound.class);
		assetManager.load("sound/tile.mp3", Sound.class);
		assetManager.load("sound/bot_tile.mp3", Sound.class);
		assetManager.load("sound/playerPass.mp3", Sound.class);
	}
}
