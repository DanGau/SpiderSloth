package com.mygdx.game;

public class Rope {

	double x, y;
	double dx, dy;
	boolean planted;
	static double fireSpeed = 1.0;
	
	public Rope(double xpos, double ypos) {
		planted = false;
		dx = fireSpeed;
		dy = -fireSpeed;
		x = xpos;
		y = ypos;
	}
	
	public void move(double dt)
	{
		if (!planted) {
			if(y > 100) {
				dx = 0;
				dy = 0;
				planted = true;
			} else {
				x = x + dx;
				y = y + dy;
			}
			
			
		}
		
	}
	
}
