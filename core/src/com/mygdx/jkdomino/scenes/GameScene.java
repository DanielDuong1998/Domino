package com.mygdx.jkdomino.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.jkdomino.Domino;
import com.mygdx.jkdomino.commons.Tweens;
import com.mygdx.jkdomino.commons._Stage;
import com.mygdx.jkdomino.interfaces.IBoardEventListener;
import com.mygdx.jkdomino.objects.Board;
import com.mygdx.jkdomino.objects.Card;
import com.mygdx.jkdomino.objects.Card2;

public class GameScene extends BaseScene implements IBoardEventListener {
    private Board board;
    private _Stage playerStage;
    private _Stage botStage;
    public static int currentAdded;
    public static int index;
    public static float fakeWidthPlayer = Domino.SW;
    public static float fakeWidthBot = Domino.SW;
    Card cards;
    Card2 cards2;
    public GameScene(Game game) {
        super(game);
        board = new Board(this);
        board.setViewport(new ExtendViewport(Domino.SW, Domino.SH, new OrthographicCamera()));
        playerStage = new _Stage();
        playerStage.setViewport(new ExtendViewport(Domino.SW, Domino.SH, new OrthographicCamera()));
        botStage = new _Stage();
        botStage.setViewport(new ExtendViewport(Domino.SW, Domino.SH, new OrthographicCamera()));

        if(Domino.modePlay == 1) {
            cards = new Card(uiStage, board);
        }
        else cards2 = new Card2(uiStage, board, playerStage, botStage);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(playerStage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        board.act(delta);
        board.draw();
        playerStage.act(delta);
        playerStage.draw();
        botStage.act(delta);
        botStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        board.getViewport().update(width, height,true);
        playerStage.getViewport().update(width, height,true);
        botStage.getViewport().update(width, height,true);
    }

    @Override
    public void dispose() {
        super.dispose();
        board.dispose();
        playerStage.dispose();
        botStage.dispose();
    }

    @Override
    public void moveComplete() {
        if(Domino.modePlay == 0) {
            moveComplete1();
        }
        else {
            moveComplete2();
        }
    }

    @Override
    public void zoomComplete() {

    }

    public void moveComplete1(){
        for(int i = 0; i < cards2.cards.get(0).size; i++) {
            cards2.cards.get(0).get(i).setColor(Color.GRAY);
        }
        if(index > 0){//Xoa bai up
            cards2.cards.get(index).get(currentAdded).disposeTileDonw();
        }
        cards2.cards.get(index).get(currentAdded).remove();
        cards2.cards.get(index).get(currentAdded).isAlive = false;
        cards2.makeFitCards();
        cards2.turnAtTheMoment++;
        cards2.turnAtTheMoment %= 2;
        if(cards2.turnAtTheMoment == 0) {
            Gdx.input.setInputProcessor(uiStage);
        }

        Tweens.setTimeout(uiStage, 0.55f, new Runnable() {
            @Override
            public void run() {
                if(cards2.checkEndGame() >= 0 && cards2.checkEndGame() < 2) {
                    Gdx.app.log("debug", "end game: Winner: " + cards2.checkEndGame());
                    cards2.disposeTilesDown();
                    return;
                }
                cards2.play();
            }
        });
    }

    public void moveComplete2(){
        Gdx.app.log("debug Play", "here! turn: " + cards.turnAtTheMoment);
        for(int i = 0; i < cards.cards.get(0).size; i++) {
            cards.cards.get(0).get(i).setColor(Color.GRAY);
        }
        if(currentAdded/7 > 0){//Xoa bai up
            cards.cards.get(currentAdded/7).get(currentAdded%7).disposeTileDonw();
        }
        cards.cards.get(currentAdded/7).get(currentAdded%7).remove();
        cards.cards.get(currentAdded/7).get(currentAdded%7).isAlive = false;
        cards.makeFitCards();

        cards.turnAtTheMoment++;
        cards.turnAtTheMoment %= 4;
        if(cards.turnAtTheMoment == 0) {
            Gdx.input.setInputProcessor(uiStage);
        }

        Tweens.setTimeout(uiStage, 0.55f, new Runnable() {
            @Override
            public void run() {
                if(cards.checkEndGame() >= 0 && cards.checkEndGame() < 4) {
                    Gdx.app.log("debug", "end game: Winner: " + cards.checkEndGame());
                    cards.disposeTilesDown();
                    return;
                }
                cards.play();
            }
        });
    }

}
