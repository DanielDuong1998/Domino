package com.mygdx.jkdomino.commons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class _BitmapFont extends Actor {
    private BitmapFont text;
    Stage stage;
    String txt;
    public _BitmapFont(Stage stage,float x, float y, String key,String txt, int align) {
        this.stage = stage;
        this.txt = txt;
      //  text = PopCat.assetManager.get(key, BitmapFont.class);

        setPosition(x,y, align);
        setOrigin(align);
        stage.addActor(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        text.draw(batch,txt,getX(),getY());
    }
    public void dispose(){
        text.dispose();
    }
}
