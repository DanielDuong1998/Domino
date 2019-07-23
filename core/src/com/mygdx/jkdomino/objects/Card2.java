package com.mygdx.jkdomino.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.mygdx.jkdomino.Domino;
import com.mygdx.jkdomino.commons.Tweens;
import com.mygdx.jkdomino.commons._Image;
import com.mygdx.jkdomino.commons._Stage;
import com.mygdx.jkdomino.interfaces.ITileEventListener;
import com.mygdx.jkdomino.scenes.GameScene;

public class Card2 implements ITileEventListener {
    private Board board;
    private BoardConfig cfg;
    private  _Stage stage;
    private _Stage playerStage;
    private _Stage botStage;
    private Array<Vector2> tiles;
    public Array<Array<_Tile>> cards;
    public Array<Array<_Tile>> fitCards;
    public int turnAtTheMoment;
    public int[] positionWinner;
    private int[] numberFinal;
    private _Image frameTakeCards;
    private float maxXPlayer;
    private float maxXBot;

    public Card2(_Stage stage, Board board, _Stage playerStage, _Stage botStage) {
        this.stage = stage;
        this.board = board;
        this.playerStage = playerStage;
        this.botStage = botStage;
        this.cfg = new BoardConfig();
        this.turnAtTheMoment = 0;
        positionWinner = new int[]{0, 0};
        numberFinal = new int[]{0, 0};

        initTile();
        Tweens.setTimeout(stage,1f,()->{
            frameTakeCards = new _Image(stage, (Domino.SW-503)/2 + Domino.SW, (Domino.SH - 294 + 6)/2, "frameTakeCards.png", Align.bottomLeft);
            renderRandomCard();
            moveCardAvailable(true);

        });
//        Tweens.setTimeout(stage, 4f, ()->{
//            moveCardAvailable(false);
//        });
//        Tweens.setTimeout(stage,2.9f,()->{
//            firstPlayer();
//        });
    }

    public void initTile(){
        this.cards = new Array<Array<_Tile>>();
        Array<_Tile> _Tile1 = new Array<>();
        Array<_Tile> _Tile2 = new Array<>();
        Array<_Tile> _Tile3 = new Array<>();
        cards.add(_Tile1, _Tile2, _Tile3);
        this.tiles = new Array<Vector2>();

        this.fitCards = new Array<Array<_Tile>>();
        Array<_Tile> _fitTile1 = new Array<>();
        Array<_Tile> _fitTile2 = new Array<>();
        fitCards.add(_fitTile1, _fitTile2);
    }

    public void firstPlayer(){
        for(int index = 0; index < cards.size - 1; index++){
            for(int i = 0; i < cards.get(index).size; i++) {
                fitCards.get(index).add(cards.get(index).get(i));
            }
        }

        int[] maxCow = new int[]{0, 0};
        for(int index = 0; index < cards.size - 1; index++){//tim nguoi co card 6-6
            for(int i = 0; i < cards.get(index).size; i++) {
                if(cards.get(index).get(i).getCol() == cards.get(index).get(i).getRow()){
                    if(cards.get(index).get(i).getCol() > maxCow[index]){
                        maxCow[index] = cards.get(index).get(i).getCol();
                    }
                }
            }
        }

        if(maxCow[0] < maxCow[1]) {
            Gdx.app.log("debug", "player-bot: " + maxCow[0] + "-" + maxCow[1]);
            Gdx.input.setInputProcessor(null);
            turnAtTheMoment = 1;
            Tweens.setTimeout(stage, 1f, new Runnable() {
                @Override
                public void run() {
                    play();
                }
            });
        }
    }

    public void renderRandomCard() {
        for(int i = 0; i <= 6; i++) {
            for(int j = i; j <= 6; j++) {
                tiles.add(new Vector2(i, j));
            }
        }
        tiles.shuffle();

        float positionX = 0;
        int j=0;
        for(int i = 0; i < 28; i++) {
            if(i < 14) {
                _Stage stage_temp = i < 7 ? playerStage : botStage;
                boolean haveTileDown = i < 7 ? false : true;
                float[] position = setPosition(i);
                _Tile _tile = new _Tile(stage_temp, this, (int)tiles.get(i).x, (int)tiles.get(i).y, haveTileDown);
                cards.get(i/7).add(_tile);
                _tile.setIndex(i/7);
                _tile.setIndexP(i%7);
                _tile.setPosition(Domino.SW/2,Domino.SH/2 );
                if(i>6) {
                    j++;
                        Tweens.action(_tile.tileDown, Actions.moveTo(position[0] + position[2], position[1] + position[3], 0.2f * j, Interpolation.fastSlow), null);
                        Tweens.action(_tile.tileDown, Actions.rotateTo(360, 0.2f * j, Interpolation.fastSlow), null);
                        Tweens.action(_tile,Actions.moveTo(position[0] + position[2],position[1] + position[3],0.2f*j,Interpolation.fastSlow),null);
                        Tweens.action(_tile,Actions.rotateTo(360,0.2f*j,Interpolation.fastSlow),null);

                }else {
                        Tweens.action(_tile,Actions.moveTo(position[0] + position[2],position[1] + position[3],0.2f*i,Interpolation.fastSlow),null);
                        Tweens.action(_tile,Actions.rotateTo(360,0.2f*i,Interpolation.fastSlow),null);

                }
            }
            else {
                Domino.modeConfigAvailableCards = true;
                _Tile _tile = new _Tile(stage, this, (int)tiles.get(i).x, (int)tiles.get(i).y, true);
                cards.get(2).add(_tile);
                float positionY = i > 20 ? (Domino.SH - cfg.TH*2)/2 : (Domino.SH - cfg.TH*2)/2 + 10 + cfg.TH;
                positionX = i == 14 || i == 21 ? (Domino.SW - 7*cfg.TW)/2 + Domino.SW: positionX + cfg.TW;
                _tile.setPosition(positionX, positionY);
            }
        }
    }

    public void moveCardAvailable(boolean isRight2Left){
        int operation = isRight2Left ? -1 : 1;

        Tweens.action(frameTakeCards, Actions.moveTo(frameTakeCards.getX() + operation*Domino.SW, frameTakeCards.getY(), 1f, Interpolation.fastSlow), null);
        for(int i = 0; i < cards.get(2).size; i++) {
                    Tweens.action(cards.get(2).get(i).tileDown,Actions.moveTo(cards.get(2).get(i).getX() + operation*Domino.SW, cards.get(2).get(i).getY(), 1f, Interpolation.fastSlow), null);
                    Tweens.action(cards.get(2).get(i), Actions.moveTo(cards.get(2).get(i).getX() + + operation*Domino.SW, cards.get(2).get(i).getY(), 1f, Interpolation.fastSlow), null);
        }
    }

    public void makeFitCards(){
        for(int index = 0; index < fitCards.size; index++) {
            fitCards.get(index).clear();
        }

        for(int index = 0; index < cards.size - 1; index++) {
            for(_Tile tile : cards.get(index)){
                if(board.isConnectable(new Vector2(tile.values[0].z,tile.values[1].z)) == -1 || !tile.isAlive){
                    continue;
                }
                fitCards.get(index).add(tile);
            }
        }
    }

    public int checkEndGame(){
        for(int i = 0; i < positionWinner.length; i++) { // bai thang, tra ve nguoi thang
            if(positionWinner[i] == 7) {
                return i;
            }
        }
        if(fitCards.get(0).size == 0 && fitCards.get(1).size == 0)//bai thua, dem nut
            return 2;
        return -1;//bai chua thua
    }

    public void getScoreForNumberFinal(){
        for(int index = 0; index < cards.size - 1; index++) {
            for(int i = 0; i < cards.get(index).size; i++) {
                if(cards.get(index).get(i).isAlive) {
                    numberFinal[index] += cards.get(index).get(i).getRow();
                    numberFinal[index] += cards.get(index).get(i).getCol();
                }
            }
        }
    }

    public int[] getNumberFinal(){
        getScoreForNumberFinal();
        return numberFinal;
    }

    public int findWinnerMinScore(int[] arr){
        int min = 0;
        int position = 0;
        min = arr[0];
        for(int i = 1; i < arr.length; i++) {
            if(arr[i] < min){
                min = arr[i];
                position = i;
            }
        }
        return position;
    }

    public void play(){

        Gdx.app.log("debug Play", "turn: " + turnAtTheMoment);
        if(turnAtTheMoment == 0 && fitCards.get(turnAtTheMoment).size == 0) {
            if(checkEndGame()==2) {
                int[] score = getNumberFinal();
                int Winner = findWinnerMinScore(score);
                Gdx.app.log("Debug", "EndGame!!!  WINNER IS: " + Winner + " --  player: " + score[0] + " bot1: " + score[1]);
                disposeTilesDown();
                return;
            }
            turnAtTheMoment++;
            turnAtTheMoment%=2;
            Gdx.app.log("debug Play", "turn " +turnAtTheMoment + " pass");
            play();
            return;
        }
        if(turnAtTheMoment!= 0){
            if(fitCards.get(turnAtTheMoment).size != 0){
                int index = (int) Math.floor(Math.random()*fitCards.get(turnAtTheMoment).size);
                int row = (int) fitCards.get(turnAtTheMoment).get(index).values[0].z;
                int col = (int) fitCards.get(turnAtTheMoment).get(index).values[1].z;
                getRC(row, col);
            }
            else {
                if(checkEndGame()== 4) {
                    int[] score = getNumberFinal();
                    int Winner = findWinnerMinScore(score);
                    Gdx.app.log("Debug", "EndGame!!! WINNER IS: " + Winner + " -- player: " + score[0] + " bot1: " + score[1]);
                    disposeTilesDown();//show bai khi end game
                    return;
                }
                turnAtTheMoment++;
                turnAtTheMoment%=2;
                Gdx.app.log("debug Play", "turn " +turnAtTheMoment + " pass");
                if(turnAtTheMoment == 0) {
                    Gdx.input.setInputProcessor(stage);
                }
                play();
            }
        }
        else {
            for(_Tile tile: fitCards.get(0)){
                tile.setColor(Color.WHITE);
            }
        }
    }

    protected float[] setPosition(int index) {
        float[] position = new float[4];
        switch (index/7) {
            case 0: {
                position[0] = cfg.PLAYER_X;
                position[1] = cfg.PLAYER_Y;
                position[2] = cfg.TW*(index%7);
                position[3] = 0;
                break;
            }
            default: {
                position[0] = cfg.BOTUP_X;
                position[1] = cfg.BOTUP_Y;
                position[2] = cfg.TW*(index%7);
                position[3] = 0;
                break;
            }
        }
        return position;
    }

    public void disposeTilesDown(){
        for(int i = 0; i < cards.get(1).size; i++) {
            if(cards.get(1).get(i).isAlive) {
                cards.get(1).get(i).disposeTileDonw();
            }
        }
    }

    public float[] getMaxPositionX(){
        float[] positionX = new float[2];
        positionX[0] = cards.get(0).get(0).getX();
        positionX[1] = cards.get(1).get(0).getX();

        for(int index = 0; index < cards.size - 1; index++) {
            for(int i = 1; i < cards.get(index).size; i++) {
                if(cards.get(index).get(i).getX() > positionX[index] && cards.get(index).get(i).isAlive){
                    positionX[index] = cards.get(index).get(i).getX();
                }
            }
        }
        return positionX;
    }

    public float[] getSizeWidthCards(){
        float[] sizeWidth = new float[]{0, 0};
        for(int index = 0; index < cards.size - 1; index++) {
            for(int i = 0; i < cards.get(index).size; i++) {
                if(cards.get(index).get(i).isAlive){
                    sizeWidth[index]+= cfg.TW;
                }
            }
        }

        return sizeWidth;
    }

    @Override
    public void getRC(int row, int col) {
        Gdx.app.log("debug", "click");
        Vector2 tile = new Vector2(row, col);
        float values = board.isConnectable(tile);
        if(values >= 0){
            Gdx.input.setInputProcessor(null);
            positionWinner[turnAtTheMoment]++;
            board.addCard(row, col, (int)values);
            for(int index = 0; index < cards.size - 1; index++) {
                for(int i = 0; i < cards.get(index).size; i++) {
                    if(cards.get(index).get(i).values[0].z == row && cards.get(index).get(i).values[1].z == col){
                        for(int j = i+1; j < cards.get(index).size; j++) {
                            Tweens.action(cards.get(index).get(j), Actions.moveTo(cards.get(index).get(j).getX() - cfg.TW, cards.get(index).get(j).getY(), 0.2f, Interpolation.pow2Out), null);
                            if(index != 0)
                                Tweens.action(cards.get(index).get(j).tileDown, Actions.moveTo(cards.get(index).get(j).getX() - cfg.TW, cards.get(index).get(j).getY(), 0.2f, Interpolation.pow2Out), null);
                        }
                        GameScene.index = index;
                        GameScene.currentAdded = i;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void getCardAvailable(int row, int col) {
        Gdx.app.log("debug", "click Deep");

        for(int i = 0; i < cards.get(2).size; i++) {
            if(cards.get(2).get(i).values[0].z == row && cards.get(2).get(i).values[1].z == col) {
                cards.get(2).get(i).remove();
                cards.get(2).get(i).tileDown.remove();
                cards.get(2).removeIndex(i);
                _Stage stage_temp = turnAtTheMoment == 0 ? playerStage : botStage;
                boolean haveTileDown = turnAtTheMoment == 0 ? false : true;
                _Tile tile = new _Tile(stage_temp, this, row, col, haveTileDown);
                float[] positionX = getMaxPositionX();
                tile.setPosition(positionX[turnAtTheMoment] + cfg.TW, cards.get(turnAtTheMoment).get(0).getY());
                cards.get(turnAtTheMoment).add(tile);
                float[] sizeWidth = getSizeWidthCards();
                float fakeWidth = turnAtTheMoment == 0 ? GameScene.fakeWidthPlayer : GameScene.fakeWidthBot;
                if(sizeWidth[turnAtTheMoment] >= fakeWidth){
                    stage_temp.zoom(1.5f, 0.4f, Interpolation.linear, null);
                    stage_temp.move(new Vector2(stage_temp.getCamera().position.x + 33, stage_temp.getCamera().position.y), 0.4f, Interpolation.linear, null);
                }
                Gdx.app.log("debug", "size Width: " + sizeWidth[turnAtTheMoment]);
                Gdx.app.log("debug", "size after remove: " + cards.get(2).size + " size card0: " + cards.get(0).size);
                break;
            }
        }
    }
}
