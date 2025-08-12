package Fruit5;
import javax.swing.*;

//合成跟判定
class GameEngine extends JPanel  {
	private static final long serialVersionUID = 1L;
	private GameState gameState;
    private PhysicsEngine physics;
    private MergeSystem mergeSystem;
    public static String soundFile;

    
    public GameEngine() {
        gameState = new GameState();
        physics = new PhysicsEngine();
        mergeSystem = new MergeSystem();
    }
    
    public void update() {
        if (!gameState.isGameRunning()) return;
        
        // 更新物理
        physics.updateFruits(gameState.getFruits());
        
        // 檢查合成
        int scoreGained = mergeSystem.checkAndPerformMerges(gameState.getFruits(), gameState);
        gameState.addScore(scoreGained);
        
        // 檢查遊戲結束
        checkGameOver();
    }
    
    public void dropFruit() {
        if (gameState.canDrop() && gameState.isGameRunning()) {
            Fruit newFruit = new Fruit(gameState.getDropX(), 80, gameState.getNextFruitType());
            gameState.getFruits().add(newFruit);
            gameState.generateNextFruit();
            gameState.setCanDrop(false);
            
            //掉落音效
            soundFile = "soundFile/toy.wav";
            SoundPlayer.playSound(soundFile);
            
            // 設置冷卻時間
            Timer cooldown = new Timer(GameConfig.DROP_COOLDOWN, e -> {
                gameState.setCanDrop(true);
                ((Timer) e.getSource()).stop();
            });
            cooldown.setRepeats(false);
            cooldown.start();
        }
    }
    
//    停止線要確認!!!
    private void checkGameOver() {
        // 檢查是否有大西瓜碰到停止線
        for (Fruit fruit : gameState.getFruits()) {
            if (fruit.getType() == FruitType.WATERMELON && fruit.isAboveDangerLine()) {
                gameState.setGameRunning(false);
                JOptionPane.showMessageDialog(null,
                    "遊戲結束！碰到停止線！\n最終得分: " + gameState.getScore(),
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
                
                System.exit(0); // 視窗關掉
                return;
            }
        }
    
 // 兩分鐘到結束
    if (WatermelonGame.TIMELEFT==0) {
        gameState.setGameRunning(false);
        JOptionPane.showMessageDialog(null,
                "遊戲結束！碰到停止線！\n最終得分: " + gameState.getScore(),
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
            
        	System.exit(0); // 視窗關掉
            return;
    	}
    }
    
    public GameState getGameState() {
        return gameState;
    }
}


