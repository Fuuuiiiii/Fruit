package Fruit5;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//畫面管控，主程式進入點
public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("果果樂工坊");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);

			CardLayout cardLayout = new CardLayout();
			JPanel cardPanel = new JPanel(cardLayout);

			// 傳遞 layout 和 container 給兩個 panel
			Menu menu = new Menu(cardLayout, cardPanel);
			WatermelonGame game = new WatermelonGame(cardLayout, cardPanel);

			cardPanel.add(menu, "menu");
			cardPanel.add(game, "game");

			frame.add(cardPanel);
			frame.pack();
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
