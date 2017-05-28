package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;

public class CastleHeroes extends ApplicationAdapter {
	SpriteBatch batch;
	Background background;
	Hero hero;
	Enemy[] enemies;
	static Arrow[] arrows;
	Texture textureArrow;
    private BitmapFont font;
    PauseThread pause;
    int bestScore;
    Music music;

	public void create () {
		batch = new SpriteBatch();
		background = new Background();
		hero = new Hero();
		enemies = new Enemy[20];
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Enemy();
		}
		arrows = new Arrow[200];
		for (int i = 0; i < arrows.length; i++) {
			arrows[i] = new Arrow();
		}
		textureArrow = new Texture("arrow.png");
		font = new BitmapFont();
		pause = new PauseThread();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("score.txt"));
            bestScore = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (Exception e) {
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.play();
	}

	public void render () {
        update();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1.01f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
		background.render(batch);
		hero.render(batch);
		for (int i = 0; i < enemies.length; i++) {
			enemies[i].render(batch);
		}
		for (int i = 0; i <arrows.length; i++) {
			if(arrows[i].active){
			 batch.draw(textureArrow, arrows[i].position.x, arrows[i].position.y);
			}
		}
		font.draw(batch, "SCORE: " + hero.score, 100, 60);
		font.draw(batch, "best score " + bestScore, 100, 40);
		batch.end();
	}

	public void update() {
		hero.update();

		for (int i = 0; i < enemies.length; i++) {
			enemies[i].update();
			if (enemies[i].hitBox.overlaps(hero.hitBox)) {
			    gameOver();
			}
		}

		for (int i = 0; i <arrows.length; i++) {
			if(arrows[i].active){
				arrows[i].update();
				for (int j = 0; j < enemies.length; j++) {
					if(enemies[j].hitBox.contains(arrows[i].position)) {
						enemies[j].recreate();
						arrows[i].disable();
						hero.score += 10;
					}
				}
			}
		}

		try {
            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                pause.start();
                pause.join();
            }
        } catch (InterruptedException e) {
		    pause.interrupt();
        }
	}

	public void dispose () {
	    hero.reloading.interrupt();
	    pause.interrupt();
		batch.dispose();
	}

	public void gameOver() {
        if (hero.score > bestScore) {
            bestScore = hero.score;
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt"));
            writer.write(String.valueOf(bestScore));
            writer.close();
        } catch (IOException e) {

        }
        hero.death();

    }

	public class PauseThread extends Thread {
        public void run() {
            while (!isInterrupted()) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                    this.interrupt();
                }
            }
        }
    }
}
