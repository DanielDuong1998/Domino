package com.mygdx.jkdomino.objects;


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
import com.mygdx.jkdomino.effect.SoundEffect;
import com.mygdx.jkdomino.interfaces.ITileEventListener;
import com.mygdx.jkdomino.scenes.GameScene;

public class Card implements ITileEventListener {
    private Board board;
    private BoardConfig cfg;
    private  _Stage stage;
    private Array<Vector2> tiles;
    public Array<Array<_Tile>> cards;
    public Array<Array<_Tile>> fitCards;
    public int turnAtTheMoment;
    public int[] positionWinner;
    private int[] numberFinal;
    private _Image FramePass;
    private String Key;



    public Card(_Stage stage, Board board){
        this.stage = stage;
        this.board = board;
        this.cfg = new BoardConfig();
        this.turnAtTheMoment = 0;
        positionWinner = new int[]{0, 0, 0, 0};
        numberFinal = new int[]{0, 0, 0, 0};
        initTile();
        renderRandomCard();
        Tweens.setTimeout(stage,1.5f,()->{
            firstPlayer();

        });
    }

    public void firstPlayer(){
        for(int index = 0; index < cards.size; index++){
            for(int i = 0; i < cards.get(index).size; i++) {
                fitCards.get(index).add(cards.get(index).get(i));
            }
        }

        int playerIndex = 0;
        boolean flag = false;
        for(int index = 0; index < cards.size; index++){//tim nguoi co card 6-6
            for(int i = 0; i < cards.get(index).size; i++) {
                if(cards.get(index).get(i).getRow() == 6 && cards.get(index).get(i).getCol() == 6){
                    playerIndex = index;
                    flag = true;
                    break;
                }
            }
            if(flag) break;
        }

        if(playerIndex != 0){
            Gdx.input.setInputProcessor(null);
            turnAtTheMoment = playerIndex;
            Tweens.setTimeout(stage, 1f, new Runnable() {
                @Override
                public void run() {
                    play();
                }
            });
        }
    }


    public int checkEndGame(){
        for(int i = 0; i < positionWinner.length; i++) { // bai thang, tra ve nguoi thang
            if(positionWinner[i] == 7) {
                return i;
            }
        }
        if(fitCards.get(0).size == 0 && fitCards.get(1).size == 0 && fitCards.get(2).size == 0 && fitCards.get(3).size == 0)//bai thua, dem nut
            return 4;
        return -1;//bai chua thua
    }

    public void play(){

        Gdx.app.log("debug Play", "turn: " + turnAtTheMoment);
        if(turnAtTheMoment == 0 && fitCards.get(turnAtTheMoment).size == 0) {
            if(checkEndGame()== 4) {
                int[] score = getNumberFinal();
                int Winner = findWinnerMinScore(score);
                Gdx.app.log("Debug", "EndGame!!!  WINNER IS: " + Winner + " --  player: " + score[0] + " bot1: " + score[1] + " bot2: " + score[2] + " bot3: " + score[3]);
                disposeTilesDown();
                return;
            }
            Key = "framePassDown.png";
            framePass(cards.get(turnAtTheMoment).get(1).getX(), cards.get(turnAtTheMoment).get(1).getY() + cfg.TH, Key);
            turnAtTheMoment++;
            turnAtTheMoment%=4;
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
                SoundEffect.Play(SoundEffect.Bot_tile);
            }
            else {

                if(checkEndGame()  == 4) {
                    int[] score = getNumberFinal();
                    int Winner = findWinnerMinScore(score);
                    Gdx.app.log("Debug", "EndGame!!! WINNER IS: " + Winner + " -- player: " + score[0] + " bot1: " + score[1] + " bot2: " + score[2] + " bot3: " + score[3]);
                    disposeTilesDown();
                    return;
                }

                 if(turnAtTheMoment==1){
                    Key="framePassLeft.png";
                    Tweens.setTimeout(stage,0.1f,()->{
                        framePass(cards.get(turnAtTheMoment).get(1).getX()+cfg.TW,cards.get(turnAtTheMoment).get(1).getY(),Key);

                    });
                }else if(turnAtTheMoment==2){
                    Key="framePassUp.png";
                    Tweens.setTimeout(stage,0.2f,()->{
                        framePass(cards.get(turnAtTheMoment).get(1).getX()-cfg.TW,cards.get(turnAtTheMoment).get(1).getY(),Key);

                    });
                }
                else {
                    Key="framePassDown.png";
                    Tweens.setTimeout(stage,0.3f,()->{
                        framePass(cards.get(turnAtTheMoment).get(1).getX(),cards.get(turnAtTheMoment).get(1).getY()-cfg.TH/2,Key);
                    });
                }
                turnAtTheMoment++;
                turnAtTheMoment%=4;
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
    void framePass(float x, float y, String key){
        FramePass = new _Image(stage,x,y,key, Align.center);
        SoundEffect.Play(SoundEffect.Player_pass);
        Tweens.action(FramePass,Actions.scaleTo(0.8f,0.8f,0.5f,Interpolation.bounceOut),()->{
            Tweens.action(FramePass,Actions.scaleTo(1,1,0.2f,Interpolation.bounceOut),()->{
                FramePass.dispose();

            });
        });

    }

    public void getScoreForNumberFinal(){
        for(int index = 0; index < cards.size; index++) {
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

    public void initTile(){
        this.cards = new Array<Array<_Tile>>();
        Array<_Tile> _Tile1 = new Array<>();
        Array<_Tile> _Tile2 = new Array<>();
        Array<_Tile> _Tile3 = new Array<>();
        Array<_Tile> _Tile4 = new Array<>();
        cards.add(_Tile1, _Tile2, _Tile3, _Tile4);
        this.tiles = new Array<Vector2>();

        this.fitCards = new Array<Array<_Tile>>();
        Array<_Tile> _fitTile1 = new Array<>();
        Array<_Tile> _fitTile2 = new Array<>();
        Array<_Tile> _fitTile3 = new Array<>();
        Array<_Tile> _fitTile4 = new Array<>();
        fitCards.add(_fitTile1, _fitTile2, _fitTile3, _fitTile4);
    }

    public void makeFitCards(){
        for(int index = 0; index < fitCards.size; index++) {
            fitCards.get(index).clear();
        }

        for(int index = 0; index < cards.size; index++) {
            for(_Tile tile : cards.get(index)){
                if(board.isConnectable(new Vector2(tile.values[0].z,tile.values[1].z)) == -1 || !tile.isAlive){
                    continue;
                }
                fitCards.get(index).add(tile);
            }
        }
    }

    //
    public void renderRandomCard(){
        for(int i = 0; i <= 6; i++) {
            for(int j = i; j <= 6; j++) {
                tiles.add(new Vector2(i, j));
            }
        }
        tiles.shuffle();
        int j=0,k=0,g=0;
        for(int i = 0; i < 28; i++){
            float[] position = setPosition(i);
            boolean haveTileDown = i/7 == 0 ? false : true;
            _Tile _tile = new _Tile(stage, this, (int)tiles.get(i).x, (int)tiles.get(i).y, haveTileDown);
            cards.get(i/7).add(_tile);
            _tile.setPosition(Domino.SW/2,Domino.SH/2);

          //  _tile.setPosition(position[0] + position[2], position[1] + position[3]);
            if(i>6 && i<14) {
                j++;
                Tweens.action(_tile, Actions.moveTo(position[0] + position[2], position[1] + position[3], 0.2f * j, Interpolation.pow2OutInverse), null);
                Tweens.action(_tile.tileDown, Actions.moveTo(position[0] + position[2], position[1] + position[3], 0.2f * j, Interpolation.pow2OutInverse), null);
            }else if(i>13&&i<21){
                k++;
                Tweens.action(_tile, Actions.moveTo(position[0] + position[2], position[1] + position[3], 0.2f * k, Interpolation.pow2OutInverse), null);
                Tweens.action(_tile.tileDown, Actions.moveTo(position[0] + position[2], position[1] + position[3], 0.2f * k, Interpolation.pow2OutInverse), null);
            }else if(i>20&&i<28){
                g++;
                Tweens.action(_tile, Actions.moveTo(position[0] + position[2], position[1] + position[3], 0.2f * g, Interpolation.pow2OutInverse), null);
                Tweens.action(_tile.tileDown, Actions.moveTo(position[0] + position[2], position[1] + position[3], 0.2f * g, Interpolation.pow2OutInverse), null);
            }else {
                SoundEffect.Play(SoundEffect.Tile);
                Tweens.action(_tile, Actions.moveTo(position[0] + position[2], position[1] + position[3],0.2f*i, Interpolation.pow2OutInverse),null);

            }

        }
    }

    protected float[] setPosition(int index){
        float[] position = new float[4];
        switch (index/7) {
            case 0: {
                position[0] = cfg.PLAYER_X;
                position[1] = cfg.PLAYER_Y;
                position[2] = cfg.TW*(index%7);
                position[3] = 0;
                break;
            }
            case 1: {
                position[0] = cfg.BOTLEFT_X;
                position[1] = cfg.BOTLEFT_Y;
                position[2] = 0;
                position[3] = cfg.TW*(index%7);
                break;
            }
            case 2: {
                position[0] = cfg.BOTUP_X;
                position[1] = cfg.BOTUP_Y;
                position[2] = cfg.TW*(index%7);
                position[3] = 0;
                break;
            }
            default:{
                position[0] = cfg.BOTRIGHT_X;
                position[1] = cfg.BOTRIGHT_Y;
                position[2] = 0;
                position[3] = cfg.TW*(index%7);
                break;
            }
        }
        return position;
    }

    public void disposeTilesDown(){
        for(int index = 1; index < cards.size; index++) {
            for(int i = 0; i < cards.get(1).size; i++) {
                if(cards.get(index).get(i).isAlive) {
                    cards.get(index).get(i).disposeTileDonw();
                }
            }
        }

    }

    @Override
    public void getRC(int row, int col) {
        Vector2 tile = new Vector2(row, col);
        float values = board.isConnectable(tile);
        if(values >= 0) {
            Gdx.input.setInputProcessor(null);
            positionWinner[turnAtTheMoment]++;
            board.addCard(row, col, (int)values);
            for(int i = 0; i < tiles.size; i++) {
                if(tiles.get(i).x == row && tiles.get(i).y == col){
                    GameScene.currentAdded = i;
                    int index = i/7;
                    float deltaX = index % 2 == 0 ? cfg.TW : 0;
                    float deltaY = index % 2 == 0 ? 0 : cfg.TW;

                    Gdx.app.log("debug", "sizecard: " + cards.get(i/7).size + " i: " + i/7);

                    for(int j = (i%7) + 1; j < cards.get(i/7).size; j++) {
                        Tweens.action(cards.get(i/7).get(j),Actions.moveTo(cards.get(i/7).get(j).getX() - deltaX,cards.get(i/7).get(j).getY() - deltaY,0.2f,Interpolation.pow2Out),null);
                        if(i/7 !=0 )
                            Tweens.action(cards.get(i/7).get(j).tileDown,Actions.moveTo(cards.get(i/7).get(j).getX() - deltaX,cards.get(i/7).get(j).getY() - deltaY,0.2f,Interpolation.pow2Out),null);
                    }
                    break;
                }
            }

        }
    }

    @Override
    public void getCardAvailable(int row, int col) {

    }
}
