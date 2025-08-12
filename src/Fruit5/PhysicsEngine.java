package Fruit5;

//碰撞、墜落物理計算
class PhysicsEngine {

	public void updateFruits(java.util.List<Fruit> fruits) {
		for (Fruit fruit : fruits) {
			fruit.update();
		}
		handleCollisions(fruits);
	}

	private void handleCollisions(java.util.List<Fruit> fruits) {
		for (Fruit fruit : fruits) {
			handleBoundaryCollision(fruit);
		}

		for (int i = 0; i < fruits.size(); i++) {
			for (int j = i + 1; j < fruits.size(); j++) {
				handleFruitCollision(fruits.get(i), fruits.get(j));
			}
		}
	}

	private void handleBoundaryCollision(Fruit fruit) {
		// 地面碰撞
		if (fruit.getY() + fruit.getRadius() > GameConfig.GROUND_Y) {
			fruit.setY(GameConfig.GROUND_Y - fruit.getRadius());
			fruit.setVy(fruit.getVy() * -GameConfig.BOUNCE_DAMPING);
			if (Math.abs(fruit.getVy()) < 1)
				fruit.setVy(0);
		}

		// 左右邊界碰撞!!
		if (fruit.getX() - fruit.getRadius() < GameConfig.GLASS_XL) {
			fruit.setX(GameConfig.GLASS_XL + fruit.getRadius());
			fruit.setVx(fruit.getVx() * -GameConfig.BOUNCE_DAMPING);
		}
		if (fruit.getX() + fruit.getRadius() > GameConfig.GLASS_XR) {
			fruit.setX(GameConfig.GLASS_XR - fruit.getRadius());
			fruit.setVx(fruit.getVx() * -GameConfig.BOUNCE_DAMPING);
		}
	}

	private void handleFruitCollision(Fruit fruit1, Fruit fruit2) {
		if (!fruit1.isCollidingWith(fruit2))   //兩個水果如果有碰撞到
			return;

		double dx = fruit2.getX() - fruit1.getX();
		double dy = fruit2.getY() - fruit1.getY();
		double distance = fruit1.getDistanceTo(fruit2);

		if (distance == 0)
			return;

		double minDistance = fruit1.getRadius() + fruit2.getRadius();
		double overlap = minDistance - distance;

		// 分離重疊的水果
		double separationX = (dx / distance) * overlap * 0.6;
		double separationY = (dy / distance) * overlap * 0.6;

		fruit1.setX(fruit1.getX() - separationX);
		fruit1.setY(fruit1.getY() - separationY);
		fruit2.setX(fruit2.getX() + separationX);
		fruit2.setY(fruit2.getY() + separationY);

		// 碰撞反應
		double normalX = dx / distance;
		double normalY = dy / distance;

		double relativeVelX = fruit2.getVx() - fruit1.getVx();
		double relativeVelY = fruit2.getVy() - fruit1.getVy();
		double speed = relativeVelX * normalX + relativeVelY * normalY;

		if (speed < 0)
			return;
		//彈跳中
		speed *= GameConfig.BOUNCE_DAMPING;
		fruit1.setVx(fruit1.getVx() + speed * normalX);
		fruit1.setVy(fruit1.getVy() + speed * normalY);
		fruit2.setVx(fruit2.getVx() - speed * normalX);
		fruit2.setVy(fruit2.getVy() - speed * normalY);
		
	}
}
