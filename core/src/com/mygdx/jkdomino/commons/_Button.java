package com.mygdx.jkdomino.commons;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class _Button extends _Image {
    public _Button(Stage stage, float x, float y, String key, int align, InputListener listener) {
        super(stage, x, y, key, align);
        addListener(listener);
    }
}
