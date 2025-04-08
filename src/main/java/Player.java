//The entity that the human player controls in the game window
//The player moves in reaction to player input
public class Player extends Entity{
    
   //Location of image file to be drawn for a Player
   public static final String PLAYER_IMAGE_FILE = "assets/player.gif";

   public static final String PLAYER_IMAGE1 = "Materials/Player1.gif"; 
   public static final String PLAYER_IMAGE2 = "Materials/Player2.gif"; 
   
   //Dimensions of the Player
   public static final int PLAYER_WIDTH = 100;
   public static final int PLAYER_HEIGHT = 125;
   //Default speed that the Player moves (in pixels) each time the user moves it
   public static final int DEFAULT_MOVEMENT_SPEED = 8;
   //Starting hit points
   public static final int STARTING_HP = 50;
    
    //Current movement speed
    private int movementSpeed;
    //Remaining Hit Points (HP) -- indicates the number of "hits" (ie collisions
    //with Avoids) that the player can take before the game is over
    private int hp;
    
    
    public Player(){
        this(0, 0, PLAYER_IMAGE1);        
    }

    public Player(int x, int y){
        this(x, y, PLAYER_IMAGE1);     
    }

    public Player(int x, int y, String imageNamel){
        super(x, y, PLAYER_WIDTH, PLAYER_HEIGHT, imageNamel);  
        this.hp = STARTING_HP;
        this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
    }
    
    //Retrieve and set the Player's current movement speed 
    public int getMovementSpeed(){
        return this.movementSpeed;
    }
    
    public void setMovementSpeed(int newSpeed){
        this.movementSpeed = newSpeed;
    }  
    
    
    //Retrieve the Player's current HP
    public int getHP(){
        return hp;
    }
    
    
    //Set the player's HP to a specific value.
    //Returns an boolean indicating if Player still has HP remaining
    public boolean setHP(int newHP){
        this.hp = newHP;
        return (this.hp > 0);
    }
    
    //Set the player's HP to a specific value.
    //Returns an boolean indicating if Player still has HP remaining
    public boolean modifyHP(int delta){
        this.hp += delta;
        return (this.hp > 0);
    }    

    public int getHeight(){
        return PLAYER_HEIGHT;
    }

    public int getWidth(){
        return PLAYER_WIDTH;
    }
}
