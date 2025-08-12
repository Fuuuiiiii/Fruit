package Fruit5;

//合成中及後重新計算
class MergeSystem {
    
	//判定要不要合成
    public int checkAndPerformMerges(java.util.List<Fruit> fruits, GameState gameState) {
        int scoreGained = 0;
        
        for (int i = 0; i < fruits.size(); i++) {
            Fruit fruit1 = fruits.get(i);
            if (fruit1.isMerged()) continue;
            
            for (int j = i + 1; j < fruits.size(); j++) {
                Fruit fruit2 = fruits.get(j);
                if (fruit2.isMerged() || fruit1.getType() != fruit2.getType()) continue;
                
                if (fruit1.isCollidingWith(fruit2)) {
                    scoreGained += performMerge(fruit1, fruit2, fruits, gameState);
                    return scoreGained; // 一次只處理一個合成
                }
            }
        }
        return scoreGained;
    }
    
    //合成後新的水果xy座標
    private int performMerge(Fruit fruit1, Fruit fruit2, java.util.List<Fruit> fruits, GameState gameState) {
        double newX = (fruit1.getX() + fruit2.getX()) / 2;
        double newY = (fruit1.getY() + fruit2.getY()) / 2;
        
        // 移除原來的水果
        fruits.removeIf(f -> f.getId() == fruit1.getId() || f.getId() == fruit2.getId());
        
        // 創建新水果
        FruitType nextType = fruit1.getType().getNextType();
        if (nextType != null) {
            Fruit newFruit = new Fruit(newX, newY, nextType);
            newFruit.setVx((fruit1.getVx() + fruit2.getVx()) / 2);
            newFruit.setVy((fruit1.getVy() + fruit2.getVy()) / 2);
            fruits.add(newFruit);
            
            return nextType.score;
        }
        
        return 0;
    }
}
