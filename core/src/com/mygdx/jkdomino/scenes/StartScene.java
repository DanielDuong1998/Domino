package com.mygdx.jkdomino.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.mygdx.jkdomino.Domino;
import com.mygdx.jkdomino.commons.Tweens;
import com.mygdx.jkdomino.commons._Button;
import com.mygdx.jkdomino.commons._Image;
import com.mygdx.jkdomino.commons._Stage;
import com.mygdx.jkdomino.commons._ToggleButton;
import com.mygdx.jkdomino.effect.SoundEffect;
import com.mygdx.jkdomino.interfaces.ToggleHandler;
import com.mygdx.jkdomino.objects.BoardConfig;

public class StartScene extends BaseScene implements ToggleHandler {
    private BoardConfig cfg;
    private Game game;
    private _Image bg;

    private float bgX = 0;
    private  float bgY =0;
    private _Image label;
    private _Button btnPlay;
    private  _Button btnMutilplayer;
    private _Image menuOption;
    private _Button tutorialBtn;
    private _Button menuBtn;
    private _Button backBtn;
    private _ToggleButton muteBtn;
    private _ToggleButton hintBtn;
    public static boolean checkHint;



    public StartScene(Game game) {
        super(game);

        this.game = game;
        bg = new _Image(uiStage,bgX,bgY,"bgstartScene.png", Align.bottomLeft);

        uiStage.addActor(bg);
        label();
        button();
        anyApear();
        Tweens.setTimeout(uiStage,2f,()->{
            animation();
        });




    }
    void label(){
        label = new _Image(uiStage, Domino.SW/2,bg.getHeight()-300,"label.png", Align.center);
        label.setScale(0.2f);
        uiStage.addActor(label);


    }
    void button(){

        btnPlay = new _Button(uiStage, Domino.SW/2, Domino.SH/2,"playBtn.png", Align.center, new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.Select);
                Domino.modePlay = 0;
                Tweens.action(btnPlay,Actions.scaleTo(1f,1f,0.2f),()->{
                    Tweens.action(btnPlay,Actions.scaleTo(1.5f,1.5f,0.2f),()->{
                        game.setScreen(new GameScene(game));
                    });
                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        btnMutilplayer = new _Button(uiStage, Domino.SW/2,btnPlay.getY()-btnPlay.getHeight()*2,"mutilplayerBtn.png", Align.center, new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.Select);
                Domino.modePlay = 1;
                Tweens.action(btnMutilplayer,Actions.scaleTo(1f,1f,0.2f),()->{
                    Tweens.action(btnMutilplayer,Actions.scaleTo(1.5f,1.5f,0.2f),()->{
                        game.setScreen(new GameScene(game));
                    });
                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        btnPlay.setScale(0.5f);
        btnMutilplayer.setScale(0.5f);
        menuBtn = new _Button(uiStage,50,Domino.SH-45,"menuBtn.png",Align.center,new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.Select);
                Actions.touchable(Touchable.disabled);
                Gdx.input.setInputProcessor(settingStage);
                menuOptions();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        menuBtn.setScale(0.7f);


    }
    void anyApear(){
        Tweens.action(label, Actions.scaleTo(0.5f,0.5f,0.2f, Interpolation.bounceOut),()->{
            Tweens.action(btnPlay, Actions.scaleTo(1.5f,1.5f,0.2f, Interpolation.bounceOut),()->{
                Tweens.action(btnMutilplayer, Actions.scaleTo(1.5f,1.5f,0.2f, Interpolation.bounceOut),null);
            });
        });

    }
    void animation(){
        Tweens.action(label, Actions.scaleTo(0.4f,0.4f,0.3f, Interpolation.bounceOut),()->{
            Tweens.action(label, Actions.scaleTo(0.5f,0.5f,0.3f, Interpolation.bounceOut),()->{
                Tweens.setTimeout(uiStage,3f,()->{
                    animation();
                });
            });
        });



    }
    void menuOptions(){

        menuOption = new _Image(settingStage,Domino.SW*2,Domino.SH/2,"frameSetting.png",Align.center);
        backBtn = new _Button(settingStage,menuOption.getX(),menuOption.getY()+menuOption.getHeight(),"backBtn.png",Align.center,new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                SoundEffect.Play(SoundEffect.Select);
                Actions.touchable(Touchable.disabled);
                Gdx.input.setInputProcessor(uiStage);
                Tweens.action(menuOption,Actions.moveTo(Domino.SW,menuOption.getY(),0.5f,Interpolation.slowFast),null);
                Tweens.action(backBtn,Actions.moveTo(Domino.SW,menuOption.getY()+menuOption.getHeight()-100,0.5f,Interpolation.slowFast),null);
                Tweens.action(tutorialBtn,Actions.moveTo(Domino.SW,menuOption.getY()+menuOption.getHeight()-100,0.5f,Interpolation.slowFast),null);
                Tweens.action(muteBtn,Actions.moveTo(Domino.SW,menuOption.getY()+menuOption.getHeight()-100,0.5f,Interpolation.slowFast),null);
                Tweens.action(hintBtn,Actions.moveTo(Domino.SW,menuOption.getY()+menuOption.getHeight()-100,0.5f,Interpolation.slowFast),null);
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        tutorialBtn = new _Button(settingStage,Domino.SW*2,Domino.SH/2,"tutorialBtn.png",Align.center,new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                tutorialBtn.setTouchable(Touchable.disabled);
                SoundEffect.Play(SoundEffect.Select);
                Tweens.action(tutorialBtn,Actions.scaleTo(0.9f,0.9f,0.2f,Interpolation.bounceOut),()->{
                    Tweens.action(tutorialBtn,Actions.scaleTo(1,1,0.2f),()->{
                        tutorialBtn.setTouchable(Touchable.enabled);
                    });
                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        muteBtn = new _ToggleButton(settingStage,Domino.SW*2,Domino.SH/2,"muteBtn.png","unmuteBtn.png",Align.center,"muteSound",this);
        muteBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                muteBtn.setTouchable(Touchable.disabled);
                SoundEffect.Play(SoundEffect.Select);
                Tweens.action(muteBtn,Actions.scaleTo(0.9f,0.9f,0.2f,Interpolation.bounceOut),()->{
                    Tweens.action(muteBtn,Actions.scaleTo(1,1,0.2f),()->{
                        muteBtn.setTouchable(Touchable.enabled);
                    });
                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        hintBtn = new _ToggleButton(settingStage,Domino.SW*2,Domino.SH/2,"unhintBtn.png","hintBtn.png",Align.center,"hint",this);
        hintBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hintBtn.setTouchable(Touchable.disabled);
                SoundEffect.Play(SoundEffect.Select);
                Tweens.action(hintBtn,Actions.scaleTo(0.9f,0.9f,0.2f,Interpolation.bounceOut),()->{
                    Tweens.action(hintBtn,Actions.scaleTo(1,1,0.2f),()->{
                        hintBtn.setTouchable(Touchable.enabled);
                    });
                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        tweenMenu();


        settingStage.addActor(menuOption);
        settingStage.addActor(backBtn);
        settingStage.addActor(tutorialBtn);
        settingStage.addActor(muteBtn);
        settingStage.addActor(hintBtn);

    }

    void tweenMenu(){
        Tweens.action(menuOption,Actions.moveTo(10,menuOption.getY(),0.5f,Interpolation.bounceOut),null);
        Tweens.action(backBtn,Actions.moveTo(30,menuOption.getY()+menuOption.getHeight()-100,0.5f,Interpolation.bounceOut),null);
        Tweens.action(tutorialBtn,Actions.moveTo(15,menuOption.getY()+menuOption.getHeight()-300,0.5f,Interpolation.bounceOut),null);
        Tweens.action(muteBtn,Actions.moveTo(15,menuOption.getY()+menuOption.getHeight()-400,0.5f,Interpolation.bounceOut),null);
        Tweens.action(hintBtn,Actions.moveTo(15,menuOption.getY()+menuOption.getHeight()-500,0.5f,Interpolation.bounceOut),null);

    }

    @Override
    public void activeHandler(String ctx) {
        if(ctx=="muteSound"){
            SoundEffect.mute = true;
        }
        if(ctx=="hint"){
            checkHint = true;
        }


    }

    @Override
    public void deactiveHandler(String ctx) {
        if(ctx=="muteSound"){
            SoundEffect.mute = false;
        }
        if(ctx=="hint"){
            checkHint =false;
        }



    }
}