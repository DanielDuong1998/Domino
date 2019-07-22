package com.mygdx.jkdomino.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.jkdomino.Domino;
import com.mygdx.jkdomino.commons._Button;
import com.mygdx.jkdomino.commons._Image;
import com.mygdx.jkdomino.objects.BoardConfig;

public class StartScene extends BaseScene {
    private BoardConfig cfg;
    private Game game;
    private _Image bg;

    private float bgX = 0;
    private  float bgY =0;
    private _Image label;
    private _Button btnPlay;
    private  _Button btnMutilplayer;

    public StartScene(Game game) {
        super(game);
        this.game = game;
        bg = new _Image(uiStage,bgX,bgY,"bgstartScene.png", Align.bottomLeft);

        uiStage.addActor(bg);
        label();
        button();



    }
    void label(){
        label = new _Image(uiStage, Domino.SW/2,bg.getHeight()-300,"label.png", Align.center);
        label.setScale(0.5f);
        uiStage.addActor(label);
    }
    void button(){

        btnPlay = new _Button(uiStage, Domino.SW/2, Domino.SH/2,"playBtn.png", Align.center, new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Domino.modePlay = 0;
                game.setScreen(new GameScene(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        btnMutilplayer = new _Button(uiStage, Domino.SW/2,btnPlay.getY()-btnPlay.getHeight()*2,"mutilplayerBtn.png", Align.center, new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Domino.modePlay = 1;
                game.setScreen(new GameScene(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }
}
