package com.mygdx.game;

public class Sloth {
	//these are coordinates
	double x, y;
	double dx, dy;
	
	//only used when swingint = true
	boolean swinging;
	double ropeLength;
	double rotSpeed;
	double rotPos;
	double attatchX, attachY;
	
	Rope ourRope;
	
	
	public Sloth (double posX, double posY) {
		x = posX;
		y = posY;
		swinging = false;
	}
	
	public void throwRope()	{
		
	}
	//moves shit
	public void move(double dt){
		if (swinging) {
			dx = rotSpeed * ropeLength * Math.cos(rotPos);
			dy = rotSpeed * ropeLength * Math.sin(rotPos);
		} else {
			//Freefall physics
			dy = dy - 9.8*dt;
		}
		x = x + dx*dt;
		y = y + dy*dt;
	}
}
