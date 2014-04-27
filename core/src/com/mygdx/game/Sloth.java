package com.mygdx.game;

public class Sloth {
	
	double x, y;
	double dx, dy;
	
	//only used when swingint = true
	boolean swinging;
	double ropeLength;
	double rotSpeed;
	double rotPos;
	double attatchX, attachY;
	
	
	
	public Sloth (double posX, double posY) {
		x = posX;
		y = posY;
		swinging = false;
	}
	
	public void move(double dt){
		if (swinging) {
			dx = rotSpeed * ropeLength * Math.cos(rotPos);
			dy = rotSpeed * ropeLength * Math.sin(rotPos);
		}
	}
}
