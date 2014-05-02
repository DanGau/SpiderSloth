package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.AssetControl.AssetHandler;

public class DrawingRope implements ApplicationListener {
	
    private SpriteBatch spriteBatch;
    private Texture img;
    
    private int shiftAmt;
    private Sprite back1;
    private Sprite[] rope;
    
    float stateTime;
    boolean paused;
    int ropeLen;
    float rotFact;
    int rotCount;
    int maxRot;
    int rotDir;

    public void create () {
    	
    	HashMap<String, Texture> textureSet = AssetHandler.loadTextures("rope_sprites");
    	
    	img = textureSet.get("rope_segment2");
    	
        spriteBatch = new SpriteBatch();
        
        ropeLen = 40;
        
        //img = new Texture("back.png");
        rope = new Sprite[ropeLen];
        
        float x = Gdx.graphics.getWidth() / 2f;
        float y = 300f;
        
        for(int i = 0; i < ropeLen; i++)
        {
        	rope[i] = new Sprite(img);
        	rope[i].setScale(.25f);
        	rope[i].setPosition(x, y);
        	rope[i].setRotation(75);
        	y += rope[i].getHeight() * .2;
        	x += rope[i].getHeight() * .0;
        }
        
        paused = false;
        rotFact = .2f;
        rotCount = 0;
        maxRot = (int)(15 / rotFact);
        rotDir = 1;
        
    }

    public void render () {
    	update();
        
        spriteBatch.begin();
        for(int i = 0; i < ropeLen; i++)
        {
        	rope[i].draw(spriteBatch);
        }
        spriteBatch.end();
    }

    public void resize (int width, int height) 
    {
    	
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
		
		if(!paused)
        {	
			int yOffset = 0;
			for(int i = ropeLen - 1; i >= 0; i--)
			{
				
				rope[i].setPosition(rope[i].getX() + (ropeLen - i)*rotFact , rope[i].getY() + (ropeLen - i)*yOffset);
				rope[i].setRotation(rotCount * rotDir);
				yOffset++;
			}
			
			rotCount += rotDir;
        }
		
		if(rotCount > maxRot || rotCount < -1 * maxRot)
		{
			rotFact *= -1;
			rotDir *= -1;
		}
	}
}