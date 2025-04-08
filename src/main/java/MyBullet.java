public class MyBullet extends Entity implements Collectable,Scrollable {
    public static final String AVOID_IMAGE_FILE2 = "Materials/mybullets.png"; 

    public static final int Bullet_Width2 = 75;
    public static final int Bullet_Height2 = 75;

    public static final int STARTING_SCROLL_SPEED2 = 15;

    public MyBullet(){
        this(0, 0);        
    }

    public MyBullet(int x, int y){
        super(x, y, Bullet_Width2, Bullet_Height2, AVOID_IMAGE_FILE2);  
    }

    public int getScrollSpeed(){
        return STARTING_SCROLL_SPEED2;
    }
    
    public void scroll(){
        setX(getX() + STARTING_SCROLL_SPEED2);
    }

    public int getPoints(){
        return 0; 
    }

    public int getDamage(){
        return -10;
    }

    
}
