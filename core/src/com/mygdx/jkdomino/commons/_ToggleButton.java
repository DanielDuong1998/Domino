package com.mygdx.jkdomino.commons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.jkdomino.Domino;
import com.mygdx.jkdomino.interfaces.ToggleHandler;

public class _ToggleButton extends _Image {
    private boolean active = false;
    private TextureRegionDrawable t1;
    private TextureRegionDrawable t2;
    private ToggleHandler handler;
    private String ctx;
    public _ToggleButton(Stage stage, float x, float y, String key1, String key2, int align, String ctx, ToggleHandler handler) {
        super(stage, x, y, key1, align);
        t1 = new TextureRegionDrawable(Domino.assetManager.get(key1, Texture.class));
        t2 = new TextureRegionDrawable(Domino.assetManager.get(key2, Texture.class));
        this.handler = handler;
        this.ctx = ctx;

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Tweens.action(this, Actions.scaleTo(0.9f,0.9f,0.3f, Interpolation.bounceOut),null);
                active = !active;
                if (active)
                    _ToggleButton.this.handler.activeHandler(ctx);
                else
                    _ToggleButton.this.handler.deactiveHandler(ctx);

                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        setDrawable((active) ? t1 : t2);
    }
}