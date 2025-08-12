package Fruit5;

//遊戲狀況參數設定
class GameState {
    private java.util.List<Fruit> fruits;
    private int score;
    private boolean gameRunning;
    private boolean canDrop;
    private FruitType nextFruitType;
    private int dropX;
    private boolean isDragging;
    
    public GameState() {
        fruits = new java.util.ArrayList<>();
        score = 0;
        gameRunning = true;
        canDrop = true;
        dropX = GameConfig.WINDOW_WIDTH / 2;
        isDragging = false;
        generateNextFruit();
    }
    
    //隨機產生小水果
    public void generateNextFruit() {  
        FruitType[] smallFruits = FruitType.getSmallFruits();
        nextFruitType = smallFruits[(int)(Math.random() * smallFruits.length)];
    }
    
    public void addScore(int points) {
        score += points;
    }
    
    // Getters and Setters
    public java.util.List<Fruit> getFruits() { return fruits; }
    public int getScore() { return score; }
    public boolean isGameRunning() { return gameRunning; }
    public boolean canDrop() { return canDrop; }
    public FruitType getNextFruitType() { return nextFruitType; }
    public int getDropX() { return dropX; }
    public boolean isDragging() { return isDragging; }
    
    public void setGameRunning(boolean gameRunning) { this.gameRunning = gameRunning; }
    public void setCanDrop(boolean canDrop) { this.canDrop = canDrop; }
    public void setDropX(int dropX) { this.dropX = dropX; }
    public void setDragging(boolean dragging) { this.isDragging = dragging; }
}

