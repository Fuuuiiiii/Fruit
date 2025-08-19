package Fruit5;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

//都是畫面，只在一個地方用，所以放一起好找
//其他畫面繪製放在watermelongame

class GameRenderer {
	private FruitRenderer fruitRenderer;
	private UIRenderer uiRenderer;

	public GameRenderer() {
		fruitRenderer = new FruitRenderer();
		uiRenderer = new UIRenderer();
	}

	public void render(Graphics2D g2d, GameState gameState) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// 繪製遊戲區域
		drawGameArea(g2d);

		// 繪製水果
		fruitRenderer.renderFruits(g2d, gameState.getFruits());

		// 繪製主遊戲畫面的其他部分
		if (gameState.canDrop() && gameState.isGameRunning()) {
			fruitRenderer.renderFruitPreview(g2d, gameState.getDropX(), 50, gameState.getNextFruitType());
			drawDropLine(g2d, gameState);
		}

		uiRenderer.renderUI(g2d, gameState);
	}

	private void drawGameArea(Graphics2D g2d) {
		// 地面
		g2d.setColor(new Color(159, 208, 222, 40)); //水色、透明度
		g2d.setStroke(new BasicStroke(5));
		g2d.drawLine(GameConfig.GLASS_XL, GameConfig.GROUND_Y, GameConfig.GLASS_XR, GameConfig.GROUND_Y);

		// 邊界
		g2d.drawLine(GameConfig.GLASS_XL - 73, GameConfig.DANGER_LINE_Y, GameConfig.GLASS_XL, GameConfig.GROUND_Y);
		g2d.drawLine(GameConfig.GLASS_XR + 73, GameConfig.DANGER_LINE_Y, GameConfig.GLASS_XR, GameConfig.GROUND_Y);

		// 危險線
		g2d.setColor(new Color(159, 208, 222));
		g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5 }, 0));
		g2d.drawLine(GameConfig.GLASS_XL - 73, GameConfig.DANGER_LINE_Y, GameConfig.GLASS_XR + 73, GameConfig.DANGER_LINE_Y);
	}

	private void drawDropLine(Graphics2D g2d, GameState gameState) {
		g2d.setColor(new Color(255, 0, 0, 100));
		g2d.setStroke(new BasicStroke(2));
		int startY = 50 + gameState.getNextFruitType().radius;
		g2d.drawLine(gameState.getDropX(), startY, gameState.getDropX(), GameConfig.GROUND_Y);
	}
}


//水果圖，如果沒有載到圖片就用畫的
class FruitRenderer {

	public void renderFruits(Graphics2D g2d, java.util.List<Fruit> fruits) {
		for (Fruit fruit : fruits) {
			renderFruit(g2d, fruit);
		}
	}

	public void renderFruitPreview(Graphics2D g2d, int x, int y, FruitType type) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
		renderFruitShape(g2d, x, y, type);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	private void renderFruit(Graphics2D g2d, Fruit fruit) {
		renderFruitShape(g2d, (int) fruit.getX(), (int) fruit.getY(), fruit.getType());
	}

	private void renderFruitShape(Graphics2D g2d, int x, int y, FruitType type) {
		int radius = type.radius;
		try {
			// 方法1：從resources資料夾載入（推薦
			BufferedImage image = ImageIO.read(new File(String.format("res/images/%s", type.imagepath)));
			g2d.drawImage(image, x - radius, y - radius, radius * 2, radius * 2, null);

		} catch (Exception e) {
			// 繪製主體
			g2d.setColor(type.color);
			g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);
			
		}
	}
}

//主遊戲畫面的其他繪製
class UIRenderer {

	public void renderUI(Graphics2D g2d, GameState gameState) {
		// 分數
		g2d.setColor(Color.BLACK);
		g2d.setFont(GameConfig.SCORE_FONT);
		String scoreText = "" + gameState.getScore();
		FontMetrics metrics = g2d.getFontMetrics(GameConfig.SCORE_FONT);
		int textWidth = metrics.stringWidth(scoreText);
		int scoreCenterX = 1038; // 自己對準圖中木板中心
		int scoreCenterY = 146;

		int drawX = scoreCenterX - textWidth / 2;
		int drawY = scoreCenterY + metrics.getAscent() / 2 - 4;

		g2d.setColor(Color.BLACK);
		g2d.setFont(GameConfig.SCORE_FONT);
		g2d.drawString(scoreText, drawX, drawY);

		

		// 下一個水果預覽
		g2d.setFont(GameConfig.UI_FONT);
		if (gameState.canDrop()) {
			FruitType nextType = gameState.getNextFruitType();
			try {
				// 方法1：從resources資料夾載入（推薦
				BufferedImage image = ImageIO.read(new File(String.format("res/images/%s", nextType.imagepath)));
				int centerX = 164;  // 水果置中
				int centerY = 618;
				int imgX = centerX - nextType.radius;
				int imgY = centerY - nextType.radius;
				g2d.drawImage(image, imgX, imgY, nextType.radius * 2, nextType.radius * 2, null);
//             下一個水果位置
			} catch (Exception e) {
				g2d.setColor(nextType.color);
				g2d.fillOval(GameConfig.WINDOW_WIDTH - 50 - nextType.radius, 50 - nextType.radius, nextType.radius * 2,
						nextType.radius * 2);
			}
		}

		// 操作提示
		if (gameState.canDrop()) {
			g2d.setColor(Color.BLACK);
			g2d.setFont(GameConfig.HINT_FONT);
			g2d.drawString("計時三分鐘", 100,GameConfig. DANGER_LINE_Y-50);
			g2d.drawString("拖拽滑鼠左右移動", 100, GameConfig. DANGER_LINE_Y-30);
			g2d.drawString("點擊投放水果", 100, GameConfig. DANGER_LINE_Y-10);
			
		}
	}

}