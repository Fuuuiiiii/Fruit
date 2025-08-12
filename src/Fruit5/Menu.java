package Fruit5;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

//開始畫面    
public class Menu extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton startButton;
	private JButton settingButton;
	private final int BUTTON_WIDTH = 350;
	private final int BUTTON_HEIGHT = 200;
	Image image2 = null;
	public static String soundFile2;
	public static String BGM;

	public Menu(CardLayout layout, JPanel container) {
		initComponents();
		setLayout(null); // 使用絕對定位
		add(startButton);
		// 背景音樂
		BGM = "soundFile/BGM.wav";
		SoundPlayer.playSound(BGM);

		startButton.addActionListener(e -> {
			layout.show(container, "game"); // 切換到遊戲畫面
		});
	}

	private void initComponents() {
		// 創建開始遊戲按鈕
		startButton = new JButton("開始遊戲");
		ImageIcon icon1 = new ImageIcon("background/遊戲開始.png");
		Image temp1 = icon1.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
//		SCALE_DEFAULT 代表使用 預設的縮放演算法。
//		SCALE_FAST快速縮放，SCALE_SMOOTH畫質較佳但速度較慢，SCALE_AREA_AVERAGING用區域平均方式縮放，畫質最好但最慢，SCALE_REPLICATE使用複製像素的方式，效果取決於原圖
		icon1 = new ImageIcon(temp1);
		startButton.setOpaque(false); // 不要使用不透明填充
		startButton.setContentAreaFilled(false); // 不填滿內容區域
		startButton.setBorderPainted(false); // 不畫邊框（可選）

		// 創建遊戲設定按鈕
		settingButton = new JButton("遊戲設定");
		ImageIcon icon2 = new ImageIcon("background/遊戲設定.png");
		Image temp2 = icon2.getImage().getScaledInstance(BUTTON_WIDTH, BUTTON_HEIGHT, Image.SCALE_SMOOTH);
//		SCALE_DEFAULT 代表使用 預設的縮放演算法。
//		SCALE_FAST快速縮放，SCALE_SMOOTH畫質較佳但速度較慢，SCALE_AREA_AVERAGING用區域平均方式縮放，畫質最好但最慢，SCALE_REPLICATE使用複製像素的方式，效果取決於原圖
		icon2 = new ImageIcon(temp2);
		settingButton.setOpaque(false); // 不要使用不透明填充
		settingButton.setContentAreaFilled(false); // 不填滿內容區域
		settingButton.setBorderPainted(false); // 不畫邊框（可選）

		// 添加按鈕懸停效果
		addHoverEffect(startButton, new Color(255, 140, 0), new Color(255, 165, 0));
		addHoverEffect(settingButton, new Color(200, 20, 60), new Color(220, 20, 60));

		startButton.setIcon(icon1);
		settingButton.setIcon(icon2);
		add(startButton);
		add(settingButton);
		
		// 事件監聽
		settingButton.addActionListener(e -> {
			SettingWindow sw = new SettingWindow();
			sw.setVisible(true);
		});

	}

	private void addHoverEffect(JButton button, Color hoverColor, Color normalColor) {
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(hoverColor);
//				//按鈕搞笑音效
//		        String soundFile2 = "soundFile/far1.wav";
//		        SoundPlayer.playSound(soundFile2);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(normalColor);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// 啟用抗鋸齒
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// 載入背景圖(要放根目錄)
		if (image2 == null) {
			try {
				image2 = ImageIO.read(new File("background/backgrond2.png"));
				System.out.println("背景圖片載入成功");
			} catch (IOException e) {
				System.out.println("無法載入背景圖片: " + e.getMessage());
				e.printStackTrace();
			}
		}

		if (image2 != null) {
			g2d.drawImage(image2, 0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT, null);
		} else {
			setBackground(GameConfig.BACKGROUND_COLOR);
			g2d.setFont(new Font("微軟正黑體", Font.BOLD, 100));

			// 標題陰影效果
			g2d.setColor(new Color(0, 0, 0, 150));
			String title = "果果樂工坊";
			FontMetrics fm = g2d.getFontMetrics();
			int titleX = (getWidth() - fm.stringWidth(title)) / 2;
			int titleY = 150;
			g2d.drawString(title, titleX + 3, titleY + 3);

			// 標題主體
			g2d.setColor(Color.ORANGE);
			g2d.drawString(title, titleX, titleY);

		}
	}

	@Override
	public void doLayout() {
		super.doLayout();

		// 設置按鈕位置（置中排列）
		int centerX = getWidth() / 2;
		int startY = getHeight() / 2 - 180;

		startButton.setBounds(centerX - BUTTON_WIDTH / 2, startY, BUTTON_WIDTH, BUTTON_HEIGHT);
		settingButton.setBounds(centerX - BUTTON_WIDTH / 2, startY + BUTTON_HEIGHT / 2 + 10, BUTTON_WIDTH,
				BUTTON_HEIGHT);

		// 按鈕搞笑音效
		soundFile2 = "soundFile/far1.wav";
		SoundPlayer.playSound(soundFile2);
	}
}