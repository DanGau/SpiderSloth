package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimatedSloth implements ApplicationListener{
	
	private static final int    FRAME_COLS = 7;     // #1
    private static final int    FRAME_ROWS = 7;     // #2
    private static final int    START_FRAME = 15;     // #2

    Animation           walkAnimation;      // #3
    Texture             walkSheet;      // #4
    TextureRegion[]         walkFrames;     // #5
    SpriteBatch         spriteBatch;        // #6
    TextureRegion           currentFrame;       // #7
    
    Sprite currentSprite;

    float stateTime;                    // #8
    int horizPos;
    int vertPos;
    int rot;
    int rotFact;
    boolean paused;

    @Override
    public void create() {
        walkSheet = new Texture(Gdx.files.internal("sprite-sheet.png")); // #9
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        TextureRegion[] walkFrames2 = new TextureRegion[FRAME_COLS * FRAME_ROWS - START_FRAME];
        for(int i = 0; i < walkFrames2.length; i++)
        	walkFrames2[i] = walkFrames[i + START_FRAME];
        walkAnimation = new Animation(0.1f, walkFrames2);      // #11
        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;                         // #13
        
        //blah blah blah please update
        horizPos = 0;
        vertPos = 0;
        rot = 0;
        rotFact = 1;
        
        paused = false;
    }

    @Override
    public void render() {
        
        update();
        
        spriteBatch.begin();
        currentSprite.draw(spriteBatch);             // #17
        spriteBatch.end();
    }
    
    public void setPos(int x, int y)
    {
    	horizPos = x;
    	vertPos = y;
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		paused = true;
		
	}

	@Override
	public void resume() {
		paused = false;
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	private void update()
	{
		stateTime += Gdx.graphics.getDeltaTime();           // #15
		
		if(!paused)
        {	
        	currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16
        	currentSprite = new Sprite(currentFrame);
        	
        	horizPos += 10;
            if(horizPos > Gdx.graphics.getWidth() * .95)
            	horizPos = 10;
            
            if(rot == 45 || rot == -45)
            	rotFact *= -1;
            
            rot += rotFact;
            
            
            
            currentSprite.setPosition(horizPos, vertPos);
            currentSprite.setRotation(rot);
        }
	}

}
