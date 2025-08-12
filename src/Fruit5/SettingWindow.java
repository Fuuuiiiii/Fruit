package Fruit5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SettingWindow extends JFrame {
	private Image image;

	public SettingWindow() {
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));

		try {
			image = ImageIO.read(new File("background/setting_bg.png"));
			setSize(image.getWidth(null), image.getHeight(null));
		} catch (IOException e) {
			System.out.println("圖片載入失敗: " + e.getMessage());
			setSize(600, 400);
		}

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (image != null) {
					g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
				}
			}
		};

		panel.setOpaque(false);
		panel.setLayout(null);
		setContentPane(panel);

		// 關閉按鈕
		try {
			ImageIcon closeIcon = new ImageIcon("background/close_1.png");
			JButton closeBtn = new JButton(closeIcon);
			closeBtn.setBounds(220, 400, closeIcon.getIconWidth(), closeIcon.getIconHeight());
			closeBtn.setContentAreaFilled(false);
			closeBtn.setBorderPainted(false);
			closeBtn.setFocusPainted(false);

			closeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			panel.add(closeBtn);
		} catch (Exception e) {
			System.out.println("關閉按鈕載入失敗：" + e.getMessage());
		}

		// 音樂按鈕
		try {
			ImageIcon musicIcon = new ImageIcon("background/music.png");
			JButton musicBtn = new JButton(musicIcon);
			musicBtn.setBounds(90, 180, musicIcon.getIconWidth(), musicIcon.getIconHeight());
			musicBtn.setContentAreaFilled(false);
			musicBtn.setBorderPainted(false);
			musicBtn.setFocusPainted(false);						
			panel.add(musicBtn);
			
		} catch (Exception e) {
			System.out.println("音樂按鈕載入失敗：" + e.getMessage());
		}

		// 音效按鈕
		try {
			ImageIcon soundIcon = new ImageIcon("background/sound.png");
			JButton soundBtn = new JButton(soundIcon);
			soundBtn.setBounds(300, 180, soundIcon.getIconWidth(), soundIcon.getIconHeight());
			soundBtn.setContentAreaFilled(false);
			soundBtn.setBorderPainted(false);
			soundBtn.setFocusPainted(false);			
			panel.add(soundBtn);
			
		} catch (Exception e) {
			System.out.println("音效按鈕載入失敗：" + e.getMessage());
		}

		// ✅ 一定要放在最後
		setVisible(true);
	}
}
