package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Danila on 14.05.2017.
 */
public class Arrow {
    Vector2 position;
    float speed;
    boolean active;

    public Arrow() {
        position = new Vector2(0, 0);
        speed = 10.0f;
        active = false;
    }
    public void enable(float x,float y){
        position.x = x;
        position.y = y;
        active = true;
    }
    public void disable(){
        active = false;
    }

    public void update(){
        position.x += speed;
        if(position.x > 1280){
            disable();
        }
    }
}
