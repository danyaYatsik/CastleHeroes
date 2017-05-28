package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Danila on 14.05.2017.
 */
public class Enemy {
    Vector2 position;
    float speed;
    static  Texture texture;
    Rectangle hitBox;

    public Enemy() {
        position = new Vector2(1280 + (float) Math.random() * 1280, (float) Math.random() * 688);
        speed = 5.0f;
        hitBox = new Rectangle(position.x, position.y, 22, 32);
        if (texture == null) {
            texture = new Texture("ghost.psd");
        }
    }

    public void recreate() {
         position.x = 1280 + (float) Math.random() * 1280;
         position.y = (float) Math.random() * 688;
    }

    public void update() {
        position.x -= speed;
        if (position.x < -100) {
            recreate();
        }
        hitBox.x = position.x;
        hitBox.y = position.y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
}
