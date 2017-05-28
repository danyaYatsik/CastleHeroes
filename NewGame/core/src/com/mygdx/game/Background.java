package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Danila on 14.05.2017.
 */
public class Background {
    Texture texture;

    public Background() {
        texture = new Texture("backgraund.jpg");
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, 0, 0);
    }
}
