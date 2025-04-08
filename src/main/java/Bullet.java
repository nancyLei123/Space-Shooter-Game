public class Bullet extends Avoid{
    public static final String AVOID_IMAGE_FILE1 = "Materials/bullets.png"; 

    public static final int Bullet_Width = 75;
    public static final int Bullet_Height = 75;

    public static final int STARTING_SCROLL_SPEED = 7;



    public Bullet(){
        this(0, 0);        
    }
    
    public Bullet(int x, int y){
        super(x, y, Bullet_Width, Bullet_Height, AVOID_IMAGE_FILE1);  
    }
    
    public int getScrollSpeed(){
        return STARTING_SCROLL_SPEED;
    }

     public int setScrollSpeed(int speed){
        return speed;
    }

    public void scroll(){
        setX(getX() - STARTING_SCROLL_SPEED);
    }



} 