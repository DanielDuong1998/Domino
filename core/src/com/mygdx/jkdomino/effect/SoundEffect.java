package com.mygdx.jkdomino.effect;

import com.badlogic.gdx.audio.Sound;
import com.mygdx.jkdomino.Domino;

public class SoundEffect {
    public static int MAX_COMMON = 4;
    public static final int Select = 0;
    public static final int Tile = 1;
    public static final int Bot_tile = 2;
    public static final int Player_pass = 3;

    private static Sound[] commons;
    public static void initSound(){
        commons = new Sound[MAX_COMMON];
        commons[Select] = Domino.assetManager.get("sound/Pop1.mp3");
        commons[Tile] = Domino.assetManager.get("sound/tile.mp3");
        commons[Bot_tile] = Domino.assetManager.get("sound/bot_tile.mp3");
        commons[Player_pass] = Domino.assetManager.get("sound/playerPass.mp3");
    }
    public static void Play(int soundCode){
        commons[soundCode].play();
    }
}
