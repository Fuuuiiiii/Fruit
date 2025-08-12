package Fruit5;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

//遊戲畫面及滑鼠、按鈕
public class WatermelonGame extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private GameEngine gameEngine;
	private GameRenderer renderer;
	private GameController controller;
	Image image = null;
	private JButton backButton;
	private final int BACKBUTTON_WIDTH = 120;
	private final int BACKBUTTON_HEIGHT = 120;
	public static int TIMELEFT = 180;
	private static JLabel timerLabel;
	private Timer timer;

	public WatermelonGame(CardLayout layout, JPanel container) {
		setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));

		// 初始化各個模組
		gameEngine = new GameEngine();
		renderer = new GameRenderer();
		controller = new GameController(gameEngine);

		setupUI();
		startGameLoop();
		initComponents();
		add(backButton);

		backButton.addActionListener(e -> {
			layout.show(container, "menu"); // 切換到選單
		});

	}

//返回鍵
	private void initComponents() {
		backButton = new JButton();
		ImageIcon icon3 = new ImageIcon("background/left-arrow .png");
		Image temp3 = icon3.getImage().getScaledInstance(BACKBUTTON_WIDTH, BACKBUTTON_HEIGHT, Image.SCALE_SMOOTH);
//		SCALE_DEFAULT 代表使用 預設的縮放演算法。
//		SCALE_FAST快速縮放，SCALE_SMOOTH畫質較佳但速度較慢，SCALE_AREA_AVERAGING用區域平均方式縮放，畫質最好但最慢，SCALE_REPLICATE使用複製像素的方式，效果取決於原圖
		icon3 = new ImageIcon(temp3);
		backButton.setOpaque(false); // 不要使用不透明填充
		backButton.setContentAreaFilled(false); // 不填滿內容區域
		backButton.setBorderPainted(false); // 不畫邊框（可選）

		backButton.setIcon(icon3);
		add(backButton);

//		倒數時間顯示
		timerLabel = new JLabel(String.valueOf(TIMELEFT), JLabel.CENTER);
		timerLabel.setFont(new Font("微軟正黑體", Font.BOLD, 60));
		countdown();
		add(timerLabel);

	}

//	絕對座標
	public void doLayout() {
		super.doLayout();
		backButton.setBounds(90, 20, BACKBUTTON_WIDTH, BACKBUTTON_HEIGHT);
		timerLabel.setBounds(60, 315, 200, 50);
	}

	private void setupUI() {
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
	}

	private void startGameLoop() {
		Timer gameTimer = new Timer(GameConfig.FPS_DELAY, this);
		gameTimer.start();
	}

	//倒數計時
	private void countdown() {
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (TIMELEFT > 0) {
					TIMELEFT--;
					timerLabel.setText(String.valueOf(TIMELEFT));
				} else {
					timer.stop(); 
				}
			}
		});

		timer.setInitialDelay(1000); // 1秒後開始
		timer.start(); // 開始倒數
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameEngine.update();
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 載入背景圖(要放根目錄)
		if (image == null) {
			try {
//                image = ImageIO.read(new File("C:\\Users\\tzu49\\eclipse-workspace\\Fruit3\\background.png"));
				image = ImageIO.read(new File("background/game_gb.png"));
				System.out.println("背景圖片載入成功");
			} catch (IOException e) {
				System.out.println("無法載入背景圖片: " + e.getMessage());
				e.printStackTrace();
			}
		}

		if (image != null) {
			g.drawImage(image, 0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT, null);
		} else {
			setBackground(GameConfig.BACKGROUND_COLOR);
		}

		renderer.render((Graphics2D) g, gameEngine.getGameState());
	}

	// 滑鼠事件委託給控制器
	@Override
	public void mousePressed(MouseEvent e) {
		controller.handleMousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		controller.handleMouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		controller.handleMouseDragged(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
