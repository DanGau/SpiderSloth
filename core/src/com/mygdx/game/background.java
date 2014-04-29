package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class background implements ApplicationListener {
	
    private SpriteBatch spriteBatch;
    private Texture img;
    
    private int shiftAmt;
    private Sprite back1;
    private Sprite back2;
    
    float stateTime;
    boolean paused;

    public void create () {
    	
    	img = new Texture(Gdx.files.internal("back.png"));
    	
        spriteBatch = new SpriteBatch();
        
        //img = new Texture("back.png");
        back1 = new Sprite(img);
        back2 = new Sprite(img);
        
        back1.setPosition(0, 0);
        back2.setPosition(back2.getWidth(), 0);
        
        
        shiftAmt = (int)back1.getWidth();
        
        paused = false;
        
    }

    public void render () {
    	update();
        
        spriteBatch.begin();
        back1.draw(spriteBatch);             // #17
        //back2.draw(spriteBatch);
        spriteBatch.end();
    }

    public void resize (int width, int height) { }

    public void pause () { }

    public void resume () { }

    public void dispose () { }
    
    private void update()
	{
		stateTime += Gdx.graphics.getDeltaTime();           // #15
		
		if(!paused)
        {	
        	int temp = (int)(back1.getX()) - (int)stateTime; // - shiftAmt * 1.0 / stateTime);
        	
        	if(temp > 1000)
        		temp = 0;
        	
			back1.setPosition(temp, 0);
        }
	}
}