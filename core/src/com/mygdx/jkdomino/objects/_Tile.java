package com.mygdx.jkdomino.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.jkdomino.Domino;
import com.mygdx.jkdomino.commons._Stage;
import com.mygdx.jkdomino.effect.SoundEffect;
import com.mygdx.jkdomino.interfaces.ITileEventListener;

public class _Tile extends Tile {
    private ITileEventListener iTileEventListener;
    private _Stage stage;
    public  _Tile tileDown;
    private boolean haveTileDown;
    public boolean isAlive;
    private int index;
    private int indexP;


    _Tile(_Stage stage, ITileEventListener iTileEventListener,int row, int col, boolean haveTileDown) {
        super(row, col);
        this.stage = stage;
        this.iTileEventListener = iTileEventListener;
        this.haveTileDown = haveTileDown;
        stage.addActor(this);
        this.isAlive = true;

        if(haveTileDown) {
            this.tileDown = new _Tile(this.stage, this.iTileEventListener,6, 0, false);
            this.tileDown.setOrigin(Align.bottomLeft);
            this.stage.addActor(this.tileDown);
            if(Domino.modeConfigAvailableCards) {
                tileDown.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Gdx.app.log("debug", "click");
                        iTileEventListener.getCardAvailable((int)values[0].z, (int)values[1].z);
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
            }

        }
        else if(!(row == 6 && col == 0)){
            addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    iTileEventListener.getRC((int)values[0].z, (int)values[1].z);
                    SoundEffect.Play(SoundEffect.Tile);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }

    @Override
    public void setPosition(float x, float y) {
        if(haveTileDown)
            tileDown.setPosition(x , y );
        super.setPosition(x , y);

    }

    public int getRow(){
        return (int)values[0].z;
    }

    public int getCol(){
        return (int)values[1].z;
    }

    public int getIndex(){return index;}

    public int getIndexP(){return indexP;}

    public void setIndex(int index) { this.index = index; }

    public void setIndexP(int indexP) { this.indexP = indexP; }

    public void disposeTileDonw(){
        tileDown.remove();
    }
        
}
