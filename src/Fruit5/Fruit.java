package Fruit5;

//水果位置計算與基礎物理特性
class Fruit {
    private double x, y;
    private double vx, vy;
    private FruitType type;
    private boolean merged;
    private int id;
    private static int nextId = 0;
    
    public Fruit(double x, double y, FruitType type) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.type = type;
        this.merged = false;
        this.id = nextId++;
    }
    
    public void update() {
        if (!merged) {
            vx *= GameConfig.FRICTION; //x移動增加摩擦
            vy += GameConfig.GRAVITY;  //y移動受重力影響
            x += vx;
            y += vy;
        }
    }
    
    public boolean isCollidingWith(Fruit other) {    
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.getRadius() + other.getRadius());
    }
    
    public double getDistanceTo(Fruit other) {      
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public boolean isAboveDangerLine() {
        return y - getRadius() < GameConfig.DANGER_LINE_Y;
    }
    
    // Getters and Setters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public int getRadius() { return type.radius; }
    public FruitType getType() { return type; }
    public boolean isMerged() { return merged; }
    public int getId() { return id; }
    
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setVx(double vx) { this.vx = vx; }
    public void setVy(double vy) { this.vy = vy; }
    public void setMerged(boolean merged) { this.merged = merged; }
}
