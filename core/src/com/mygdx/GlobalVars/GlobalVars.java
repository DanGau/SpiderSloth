package com.mygdx.GlobalVars;

public class GlobalVars {
		
		//////////Camera Constants\\\\\\\\\\
		public static final float PPM = 100;
		public static final int V_WIDTH = 640;
		public static final int V_HEIGHT = 480;
		
		//////////Rope Constants\\\\\\\\\\
		public static final float SEG_WIDTH = 2;
		public static final float SEG_HEIGHT = 10;
		public static final float MAX_SEG_DIST = 10;
		
		
		//////////Collision Categories\\\\\\\\\\
		public static final short CATEGORY_GROUND = 0x2;
		public static final short CATEGORY_PLAYER = 0x4;
		public static final short CATEGORY_ROPE = 0x8;
		
		public static final short MASK_GROUND = CATEGORY_ROPE + CATEGORY_PLAYER;
		public static final short MASK_PLAYER = CATEGORY_GROUND;
		public static final short MASK_ROPE = CATEGORY_GROUND;
		
}
