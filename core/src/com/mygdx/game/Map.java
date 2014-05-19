package com.mygdx.game;

import java.awt.geom.Line2D;
import java.util.Random;
import java.util.ArrayList;

public class Map {
	
	private ArrayList<Line2D> topPoints = new ArrayList<Line2D>();
	
	public Map(long seed){
		Random mapGenerator = new Random(seed);
		Line2D last = new Line2D.Double(0,0,10,0);
		topPoints.add(last);
		for (int i = 0; i < 100; i++){
			Line2D next = new Line2D.Double(10*i,last.getY2(),10*(i+1),mapGenerator.nextInt(9));
			topPoints.add(next);
			last = next;
		}
	}
	
	public ArrayList<Line2D> getTopPoints(){
		return topPoints;
	}
}

