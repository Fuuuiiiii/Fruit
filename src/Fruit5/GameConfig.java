package Fruit5;

import java.awt.Color;
import java.awt.Font;

//基本變數及水果載入
class GameConfig {
	// 視窗設定
	public static final int WINDOW_WIDTH = 1200;
	public static final int WINDOW_HEIGHT = 800;
	public static final Color BACKGROUND_COLOR = new Color(255, 248, 220);

	// 遊戲設定
	public static final int FPS_DELAY = 16; // 約60 FPS
	public static final double GRAVITY = 0.8;
	public static final double BOUNCE_DAMPING = 0.25;
	public static final double FRICTION = 0.6;
	public static final int GROUND_Y = WINDOW_HEIGHT - 80;
	public static final int DANGER_LINE_Y = 230;
	public static final int DROP_COOLDOWN = 1000; // 毫秒
	public static final int GLASS_XR = 784;
	public static final int GLASS_XL = 412;

	// UI設定
	public static final Font SCORE_FONT = new Font("微軟正黑體", Font.BOLD, 36);
	public static final Font UI_FONT = new Font("微軟正黑體", Font.PLAIN, 16);
	public static final Font HINT_FONT = new Font("微軟正黑體", Font.PLAIN, 20);
}

// 水果類型枚舉 
enum FruitType {
	CHERRY(18, new Color(220, 20, 60), 10, "藍莓", "0.png"), 
	GRAPE(28, new Color(255, 105, 180), 20, "葡萄", "1.png"),
	KIWI(32, new Color(128, 0, 128), 30, "奇異果", "2.png"), 
	SMALLORANGE(47, new Color(255, 255, 0), 40, "柳丁", "3.png"),
	LEMON(50, new Color(255, 165, 0), 50, "檸檬", "4.png"), 
	ORANGE(60, new Color(255, 0, 0), 60, "橘子", "6.png"),
	TOMATO(70, new Color(154, 205, 50), 80, "番茄", "fruit6.png"),
	PEACH(80, new Color(255, 218, 185), 100, "桃子", "fruit7.png"),
	PINEAPPLE(90, new Color(255, 215, 0), 150, "鳳梨", "fruit8.png"),
	COCONUT(105, new Color(139, 69, 19), 200, "椰子", "fruit9.png"), 
	WATERMELON(120, new Color(50, 205, 50), 300, "西瓜", "8.png");

	public final int radius;
	public final Color color;
	public final int score;
	public final String name;
	public final String imagepath;

	FruitType(int radius, Color color, int score, String name, String imagepath) {
		this.radius = radius;
		this.color = color;
		this.score = score;
		this.name = name;
		this.imagepath = imagepath;
	}

	public FruitType getNextType() { //合成下一個水果
		FruitType[] types = values();
		for (int i = 0; i < types.length - 1; i++) {
			if (types[i] == this) {
				return types[i + 1];
			}
		}
		return null;
	}

	public static FruitType[] getSmallFruits() {
		return new FruitType[] { CHERRY, GRAPE, KIWI, SMALLORANGE, LEMON }; //隨機產生小水果
	}
}
