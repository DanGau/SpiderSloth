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
        
        resize(0,0);
        
        
        back1.setPosition(0, 0);
        back2.setPosition(back2.getWidth(), 0);
        
        
        shiftAmt = (int)back1.getWidth();
        
        paused = false;
        
    }

    public void render () {
    	update();
        
        spriteBatch.begin();
        back1.draw(spriteBatch);             // #17
        back2.draw(spriteBatch);
        spriteBatch.end();
    }

    public void resize (int width, int height) 
    {
    	back1.setScale(back1.getHeight() / Gdx.graphics.getHeight());
    	back2.setScale(back2.getHeight() / Gdx.graphics.getHeight());
    }

    public void pause () 
    {
    	paused = true;
    }

    public void resume () 
    {
    	paused = false;
    }

    public void dispose () { }
    
    private void update()
	{
		stateTime += Gdx.graphics.getDeltaTime();           // #15
		
		if(!paused)
        {	
			if(back1.getX() <= back1.getWidth() * -1)
        		back1.setPosition(back2.getX() + back2.getWidth(), 0);
        	
        	if(back2.getX() <= back2.getWidth() * -1)
        		back2.setPosition(back1.getX() + back1.getWidth(), 0);
        	
			back1.setPosition(back1.getX() - stateTime, 0);
			back2.setPosition(back2.getX() - stateTime, 0);
        }
	}
}