

public class Boss extends Entity {

    public static final String BOSS_IMAGE_FILE = "Materials/boss.gif";

    public static final int BOSS_WIDTH = 200;
    public static final int BOSS_HEIGHT = 200;

    public static final int GET_SCROLL_SPEED = 5;
    public static final int GET_POINT_VALUE = 1;

    private final int MAX_TRAVEL_DISTANCE_UP = 300;
    private final int MAX_TRAVEL_DISTANCE_DOWN = 100;
    public static final int originalX = 700;
    public static final int originalY = 300;
    public static final int DEFAULT_HP = 250;
    private int dy; // Change in y-direction
    private int movementSpeed;
    private int hp = 0;


    public Boss(){
        super(originalX, originalY, BOSS_WIDTH, BOSS_HEIGHT, BOSS_IMAGE_FILE);  
        this.dy = GET_SCROLL_SPEED;
        this.hp = DEFAULT_HP;
    }


    public void movePattern1() {
        // Check if the boss has reached the maximum travel distance
        if (y - originalY >= MAX_TRAVEL_DISTANCE_DOWN) {
            dy = -dy; // Reverse direction
        }else if (originalY - y >= MAX_TRAVEL_DISTANCE_UP){
            dy = -dy;
        }

        // Update the position of the boss
        int change = getY() + dy; 
        setY(change);
    }

    public int getMovementSpeed(){
        return this.movementSpeed;
    }
    
    public void setMovementSpeed(int newSpeed){
        this.movementSpeed = newSpeed;
    }  
    

    public int getHP(){
        return hp;
    }

    public boolean setHP(int newHP){
        this.hp = newHP;
        return (this.hp > 0);
    }

    public boolean modifyHP(int delta){
        this.hp += delta;
        return (this.hp > 0);
    }    


}

