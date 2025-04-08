import java.awt.*;
import java.awt.event.*;
import java.util.*;

//The basic ScrollingGame, featuring Avoids, Gets, and SpecialGets
//Players must reach a score threshold to win
//If player runs out of HP (via too many Avoid collisions) they lose
public class ScrollingGame extends GameEngine {
    
 
    
    //Starting Player coordinates
    protected static final int STARTING_PLAYER_X = 0;
    protected static final int STARTING_PLAYER_Y = 100;
    
    //Score needed to win the game
    protected static final int SCORE_TO_WIN = 300;
    
    //Maximum that the game speed can be increased to
    //(a percentage, ex: a value of 300 = 300% speed, or 3x regular speed)
    protected static final int MAX_GAME_SPEED = 300;
    protected static final int MIN_GAME_SPEED = 20;
    //Interval that the speed changes when pressing speed up/down keys
    protected static final int SPEED_CHANGE = 20;    
    
    protected static final String INTRO_SPLASH_FILE = "assets/splash.gif";        
    //Key pressed to advance past the splash screen
    public static final int ADVANCE_SPLASH_KEY = KeyEvent.VK_ENTER;
    
    //Interval that Entities get spawned in the game window
    //ie: once every how many ticks does the game attempt to spawn new Entities
    protected static final int SPAWN_INTERVAL = 45;
    
    
    //A Random object for all your random number generation needs!
    protected static final Random rand = new Random();
    
            
    
    //Player's current score
    protected int score;
    
    //Stores a reference to game's Player object for quick reference
    //(This Player will also be in the displayList)
    protected Player player;
    
    
    public ScrollingGame(){
        super();
    }
    
    public ScrollingGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }
    
    
    //Performs all of the initialization operations that need to be done before the game starts
    protected void pregame(){
        this.setBackgroundColor(Color.BLACK);
        player = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y);
        displayList.add(player); 
        score = 0;

        setSplashImage(INTRO_SPLASH_FILE);
        pauseGameOnSplashScreen = false; 
    }
    
    
    //Called on each game tick
    protected void updateGame(){
        //scroll all scrollable Entities on the game board
        scrollEntities();   
        //Spawn new entities only at a certain interval
        if (super.getTicksElapsed() % SPAWN_INTERVAL == 0){            
            spawnEntities();
        }
  
        for (int i=1;i<displayList.size();i++){
            if(player.isCollidingWith(displayList.get(i))){
                handlePlayerCollision((Collectable)displayList.get(i));
                displayList.remove(i);
                break;
            }
        }

        //Update the title text on the top of the window
        setTitleText("Score: " + score + ", HP: " + player.getHP());   
        
        if (determineIfGameIsOver()) {
            postgame(); // Call the method to handle end-of-game logic
        }
    }
    
    
    //Scroll all scrollable entities per their respective scroll speeds
    protected void scrollEntities(){
        for (int i = 0; i < displayList.size(); i++){
            Entity gameObject = displayList.get(i);

            if (gameObject instanceof Scrollable) {
                Scrollable scrollable = (Scrollable) gameObject;
                scrollable.scroll(); 
            }
        }
        gcOutOfWindowEntities();
    }

    //Removes entities from the displayList that have scrolled offscreen
    protected void gcOutOfWindowEntities(){
        for (int i = 0; i < displayList.size(); i++) {
            Entity entity = displayList.get(i);
    
            if (entity.getX() + entity.getWidth() < 0) { 
                displayList.remove(i);
                i--; //as list size decreased
            }
        }
    }
    
    
    //Called whenever it has been determined that the Player collided with a collectable
    protected void handlePlayerCollision(Collectable collidedWith){
            player.modifyHP(collidedWith.getDamage()); 
            score += collidedWith.getPoints();
    }
    
    //Spawn new Entities on the right edge of the game window
    // Spawn new Entities on the right edge of the game window
    protected void spawnEntities(){
    int entitiesToSpawn = rand.nextInt(5); 

    for (int i = 0; i < entitiesToSpawn; i++) {
        int spawnX = getWindowWidth();
        int spawnY = rand.nextInt(getWindowHeight()); 

        Entity newEntity;
        int entityType = rand.nextInt(10);

        if (entityType < 4) {
            newEntity = new Avoid(spawnX, spawnY); 
        } else if (entityType < 8) {
            newEntity = new Get(spawnX, spawnY); 
        } else {
            newEntity = new SpecialGet(spawnX, spawnY);
        }

        newEntity.setY(rand.nextInt(getWindowHeight() - newEntity.getHeight()));

        // Check for collisions and add the new entity if it's clear
        if (checkCollision(newEntity).isEmpty()) {
            displayList.add(newEntity);
        }
    }
}



    //Called once the game is over, performs any end-of-game operations
    protected void postgame(){
        if (score >= SCORE_TO_WIN){
            super.setTitleText("GAME OVER - You Win!");
        }else if (player.getHP() <= 0) {
         super.setTitleText("GAME OVER - You Lose!");
        } 
    }
    
    
    //Determines if the game is over or not; Game can be over due to either a win or lose state
    protected boolean determineIfGameIsOver(){
        // Check for Win Condition
        if (score >= SCORE_TO_WIN) {
            return true; 
        }
    
        // Check for Lose Condition
        if (player.getHP() <= 0) {
            return true; 
        }
    
        return false; // Game is not over yet
    }

    
    //Reacts to a single key press on the keyboard
    protected void reactToKey(int key){
        // Display debug information
        setDebugText("Key Pressed!: " + KeyEvent.getKeyText(key) + ",  DisplayList size: " + displayList.size());
    
        // React only to the "advance splash" key if a splash screen is active
        if (getSplashImage() != null){
            if (key == ADVANCE_SPLASH_KEY) {
                super.setSplashImage(null);
                return;
            }
        }
    
        // Handle game-specific key inputs
        if(isPaused==false){
            if(key==UP_KEY&&(player.getY()-player.getMovementSpeed())>=0){
                player.setY(player.getY()-player.getMovementSpeed());
            }
            if(key==DOWN_KEY&&player.getY()+player.getMovementSpeed()<=(getWindowHeight()-player.getHeight())){
                player.setY(player.getY()+player.getMovementSpeed());
            }
            if(key==LEFT_KEY&&(player.getX()-player.getMovementSpeed())>=0){
                player.setX(player.getX()-player.getMovementSpeed());
            }
            if(key==RIGHT_KEY&&(player.getX()+player.getMovementSpeed()<=(getWindowWidth()-player.getWidth()))){
                player.setX(player.getX()+player.getMovementSpeed());
            }
            }
    
            if(key==KEY_PAUSE_GAME){
                isPaused = !isPaused;
            }
    
            if (key == SPEED_UP_KEY && getGameSpeed() < MAX_GAME_SPEED) {
                setGameSpeed(Math.min(getGameSpeed() + SPEED_CHANGE, MAX_GAME_SPEED));
            }
            if (key == SPEED_DOWN_KEY && getGameSpeed() > MIN_GAME_SPEED) {
                setGameSpeed(Math.max(getGameSpeed() - SPEED_CHANGE, MIN_GAME_SPEED));
            }
            }
    
    
    
    
    //Handles reacting to a single mouse click in the game window
    //Won't be used in Milestone #2... you could use it in Creative Game though!
    protected MouseEvent reactToMouseClick(MouseEvent click){
        if (click != null){ //ensure a mouse click occurred
            int clickX = click.getX();
            int clickY = click.getY();
            setDebugText("Click at: " + clickX + ", " + clickY);
        }
        return click;//returns the mouse event for any child classes overriding this method
    }
    
}
