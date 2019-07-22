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
import com.mygdx.jkdomino.interfaces.IBoardEventListener;
import com.mygdx.jkdomino.objects.Board;
import com.mygdx.jkdomino.objects.Card;
import com.mygdx.jkdomino.objects.Card2;

public class GameScene extends BaseScene implements IBoardEventListener {
    private Board board;
    Array<Vector2> tiles;
    public static int currentAdded;
    Card cards;
    Card2 cards2;
    public GameScene(Game game) {
        super(game);
        board = new Board(this);
        board.setViewport(new ExtendViewport(Domino.SW, Domino.SH, new OrthographicCamera()));
        if(Domino.modePlay == 1) {
            cards = new Card(uiStage, board);
        }
        else cards2 = new Card2(uiStage, board);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        board.act(delta);
        board.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        board.getViewport().update(width, height,true);
    }

    @Override
    public void dispose() {
        super.dispose();
        board.dispose();
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
        if(currentAdded/7 > 0){//Xoa bai up
            cards2.cards.get(currentAdded/7).get(currentAdded%7).disposeTileDonw();
        }
        cards2.cards.get(currentAdded/7).get(currentAdded%7).remove();
        cards2.cards.get(currentAdded/7).get(currentAdded%7).isAlive = false;
        cards2.makeFitCards();

        Gdx.app.log("debug", "card.size: " + cards2.cards.get(0).size);


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
