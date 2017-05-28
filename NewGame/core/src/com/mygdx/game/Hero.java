package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by Danila on 14.05.2017.
 */
public class Hero {
    Vector2 position;
    float speed;
    Texture texture;
    ReloadingThread reloading;
    Rectangle hitBox;
    static int fireRate;
    static boolean isBowReady;
    int score;

    public Hero(){
            texture = new Texture("hero.psd");
            speed = 2.5f;
            position = new Vector2(50,328);
            isBowReady = true;
            fireRate = 400;
            reloading = new ReloadingThread();
            hitBox = new Rectangle(position.x, position.y, 22, 32);
            score = 0;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void update() {
         if(Gdx.input.isKeyPressed(Input.Keys.A)){
            position.x -= speed;
            }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            position.x += speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            position.y += speed;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            position.y -= speed;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            fire();
        }
        if(Gdx.input.isTouched()){
            fire();
            if(Gdx.input.getX() < position.x + 11) position.x -= speed;
            if(Gdx.input.getX() > position.x + 11) position.x += speed;
            if(Gdx.graphics.getHeight() - Gdx.input.getY() > position.y + 16) position.y +=speed;
            if(Gdx.graphics.getHeight() - Gdx.input.getY() < position.y + 16) position.y -=speed;
        }

        if(position.x > 1258) position.x -= speed;
        if(position.x < 0) position.x += speed;
        if(position.y < 0) position.y += speed;
        if(position.y > 688) position.y -= speed;

        hitBox.x = position.x;
        hitBox.y = position.y;

    }
    public void fire() {
        if (isBowReady) {
            for (int i = 0; i < CastleHeroes.arrows.length; i++) {
                if (!CastleHeroes.arrows[i].active) {
                    CastleHeroes.arrows[i].enable(position.x + 22, position.y + 16);
                    break;
                }
            }
            isBowReady = false;
        }
    }
    public void death() {
        position = new Vector2(50,328);
        isBowReady = true;
        hitBox = new Rectangle(position.x, position.y, 22, 32);
        score = 0;
    }

    class ReloadingThread extends Thread {
        public ReloadingThread() {
            start();
        }
        public void run() {
            while (true) {
                try {
                    sleep(Hero.fireRate);
                    Hero.isBowReady = true;
                } catch (InterruptedException e) {
                    Hero.isBowReady = true;
                }
            }
        }
    }
}
