package Fruit5;

import java.awt.event.*;

//滑鼠及遊戲狀態的互動關係
class GameController {
    private GameEngine gameEngine;
    private GameState gameState;
    
    public GameController(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.gameState = gameEngine.getGameState();
    }
    
    public void handleMousePressed(MouseEvent e) {
        if (gameState.isGameRunning() && gameState.canDrop()) {
            gameState.setDragging(true);
            updateDropPosition(e.getX());
        }
    }
    
    public void handleMouseReleased(MouseEvent e) {
        if (gameState.isDragging() && gameState.isGameRunning()) {
            gameEngine.dropFruit();
            gameState.setDragging(false);
        }
    }
    
    public void handleMouseDragged(MouseEvent e) {
        if (gameState.isDragging() && gameState.canDrop()) {
            updateDropPosition(e.getX());
        }
    }
    
    private void updateDropPosition(int mouseX) {
        int radius = gameState.getNextFruitType().radius;
        int constrainedX = Math.max(radius, Math.min(GameConfig.WINDOW_WIDTH - radius, mouseX));
        gameState.setDropX(constrainedX);
    }
}
