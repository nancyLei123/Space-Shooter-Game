import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class LeiGame extends ScrollingGame {

    public static final int SHOOT_KEY = KeyEvent.VK_SPACE;

     private static final String STARTING_PLAYER1 = "Materials/Player1.gif"; 
     public static final String AVOID1 = "Materials/a1.png"; 
     public static final String AVOID2 = "Materials/a2.png"; 
     public static final String AVOID3 = "Materials/a3.png"; 
     public static final String AVOID4 = "Materials/a4.png"; 
     public static final String AVOID5 = "Materials/a5.png"; 
     public static final String AVOID6 = "Materials/a6.png"; 
    

     public static final String GET_SHIELD = "Materials/Shield.png"; 
     public int numberSpawn =0; 

     protected int score;
     protected Player lplayer;
     protected Clip currentClip;

     protected static final String LEI_SPLASH_FILE = "Materials/splash22.gif";        
     protected static final String GAME_INFO_SPLASH_FILE = "Materials/splash2.gif";
     protected static final String BOSS_SPLASH_FILE = "Materials/bossSplash.gif";
     protected static final String SUCCESS_SPLASH_FILE = "Materials/successSplash.gif"; 
     protected static final String FAILURE_SPLASH_FILE = "Materials/failureSplash.gif"; 
     protected static final String FAILURE_SPLASH_FILE_PRE = "Materials/preFailureSplash.gif"; 
     protected static final String FAILURE_SPLASH_FILE_BULLETS = "Materials/splash3.gif"; 
     protected static final String KEY_CONTROL_SPLASH_FILE = "Materials/keyControls.png"; 

     public static final int BOSS_SPAWN_THRESHOLD = 30; // Number of entities to spawn before the boss appears
     public boolean isBossEncounter = false; // Flag to indicate if the boss encounter is active
     protected Boss boss; // Reference to the boss entity
     

    public LeiGame(){
        super();
    }
    
    public LeiGame(int gameWidth, int gameHeight){
        super(gameWidth, gameHeight);
    }

    protected void pregame(){
        this.setBackgroundImage("Materials/Background.png");
        lplayer = new Player(STARTING_PLAYER_X, STARTING_PLAYER_Y, STARTING_PLAYER1);
        displayList.add(lplayer); 
        score = 50;

        setSplashImage(LEI_SPLASH_FILE); 
        playSound("Materials/spaceBackground.wav", Clip.LOOP_CONTINUOUSLY, 0.5f); 
    }
    
public void playSound(String soundFileName, int loopCount, float volume) {
    try {
        if (!soundFileName.equals("Materials/collide1.wav")
        &&!soundFileName.equals("Materials/Shield.wav")&&!soundFileName.equals("Materials/shoot.wav")) {
        stopCurrentClip(); // Stop and close the current clip
        }

        File soundFile = new File(soundFileName);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        Clip myCurrentClip = AudioSystem.getClip();
        myCurrentClip.open(audioIn);

        // Adjust the volume
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }
        FloatControl gainControl = (FloatControl) myCurrentClip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);

        myCurrentClip.loop(loopCount);
        myCurrentClip.start();
         if (!soundFileName.equals("Materials/collide1.wav")
        &&!soundFileName.equals("Materials/Shield.wav")&&!soundFileName.equals("Materials/shoot.wav")) {
        currentClip = myCurrentClip; 
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    
        // Method to stop and close the current clip
        private void stopCurrentClip() {
            if (currentClip != null) {
                if (currentClip.isRunning()) {
                    currentClip.stop();
                }
                currentClip.close();
            }
        }

    

    protected void updateGame(){
        pauseGameOnSplashScreen = false;
        //scroll all scrollable Entities on the game board
        scrollEntities();   
        //Spawn new entities only at a certain interval
        if (isBossEncounter) {
            updateBossEncounter();

        } else {
        if (super.getTicksElapsed() % SPAWN_INTERVAL == 0){            
            spawnEntities();
        }
  
        for (int i=1;i<displayList.size();i++){
            if(lplayer.isCollidingWith(displayList.get(i))){
                handlePlayerCollision((Collectable)displayList.get(i));
                if (displayList.get(i) instanceof Avoid){
                    playSound("Materials/collide1.wav", 0, 0.2f);
                }else if (displayList.get(i) instanceof Get){
                    playSound("Materials/Shield.wav", 0, 0.3f);
                }
                displayList.remove(i);
                break;
            }
        }

        //Update the title text on the top of the window
        setTitleText("Bullets: " + score + ", Shields: " + lplayer.getHP());   
        
        if (determineIfGameIsOver()) {
            postgame(); // Call the method to handle end-of-game logic
        }
    }
    }

    protected void updateBossEncounter() {
        boss.movePattern1();

        if (super.getTicksElapsed() % SPAWN_INTERVAL == 0) {
            spawnBossBullet();
        }

        for (int i = 0; i < displayList.size(); i++) {
            Entity entity = displayList.get(i);
            if (entity instanceof Bullet) {
                if (lplayer.isCollidingWith(entity)) {
                    handlePlayerCollision((Collectable) entity);
                    playSound("Materials/collide1.wav", 0, 0.2f);
                    displayList.remove(i); // Remove the bullet after collision
                    i--; // Adjust the loop index after removal
                }
            }else if (entity instanceof MyBullet) {
                if (boss.isCollidingWith(entity)) {
                    handleBossCollision((Collectable) entity);
                    displayList.remove(i); // Remove the bullet after collision
                    i--; // Adjust the loop index after removal
            }
        }

        setTitleText("Bullets: " + score + ", Shields: " + lplayer.getHP() + "              " + "Boss' HP: " + boss.getHP());   
        
        if (determineIfGameIsOver()) {
            postgame(); // Call the method to handle end-of-game logic
        }

    }
}


    private void spawnBossBullet() {
        // Logic to create and add a new bullet to the game
        int Exist = 0; 
        if (Exist % 2 ==0){
        Exist +=1;
        Bullet bullet = new Bullet(boss.getX(), boss.getY()); 
        displayList.add(bullet);
        }
    }
    

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

    protected void gcOutOfWindowEntities(){
        for (int i = 0; i < displayList.size(); i++) {
            Entity entity = displayList.get(i);
    
            if (entity.getX() + entity.getWidth() < 0) { 
                displayList.remove(i);
                i--; //as list size decreased
            }
        }
    }

    protected void handlePlayerCollision(Collectable collidedWith){
        lplayer.modifyHP(collidedWith.getDamage()); 
        score += collidedWith.getPoints();
    }

    protected void handleBossCollision(Collectable collidedWith){
        boss.modifyHP(collidedWith.getDamage()); 
    }


    
    protected void spawnEntities(){
        double numSpawn = rand.nextDouble(); 
        int entitiesToSpawn = 0; 
        if (numSpawn < 0.2){
            entitiesToSpawn = 3; 
        }else if (numSpawn <0.6){
            entitiesToSpawn = 2; 
        }else if (numSpawn <0.7){
            entitiesToSpawn = 1; 
        }else{
            entitiesToSpawn = 0; 
        }
    
        for (int i = 0; i < entitiesToSpawn; i++) {
            numberSpawn++; 
            int spawnX = getWindowWidth();
            int spawnY = rand.nextInt(getWindowHeight()); 
    
            Entity newEntity;
            int entityType = rand.nextInt(28);
    
            if (entityType < 4) {
                newEntity = new Avoid(spawnX, spawnY, 160, 150, AVOID1); 
            } else if (entityType < 6) {
                newEntity = new Avoid(spawnX, spawnY, 90, 90, AVOID2);  
            }else if (entityType < 11) {
                newEntity = new Avoid(spawnX, spawnY, 75, 75, AVOID3);  
            }else if (entityType < 14) {
                newEntity = new Avoid(spawnX, spawnY, 120, 120, AVOID4);  
            }else if (entityType < 17) {
                newEntity = new Avoid(spawnX, spawnY, 80, 80, AVOID5);  
            } else if (entityType < 21) {
                newEntity = new Avoid(spawnX, spawnY, 240, 160, AVOID6);  
            } else if (entityType < 24){
                newEntity = new Get(spawnX, spawnY);  
            }
            else{
                newEntity = new SpecialGet(spawnX, spawnY);  
            }
    
            newEntity.setY(rand.nextInt(getWindowHeight() - newEntity.getHeight()));
    
            // Check for collisions and add the new entity if it's clear
            if (checkCollision(newEntity).isEmpty()) {
                displayList.add(newEntity);
            }

            if (numberSpawn >= BOSS_SPAWN_THRESHOLD && !isBossEncounter) {
                startBossEncounter();
            }
        }
    }
    
    private void startBossEncounter() {
        isBossEncounter = true;
        // Initialize boss entity and add to display list
        boss = new Boss();
        displayList.add(boss);

        setSplashImage(BOSS_SPLASH_FILE);
        playSound("Materials/warning.wav", 1, 0.5f);
        pauseGameOnSplashScreen = true;

        // Optional: Clear existing entities or modify the game environment for the boss encounter
        displayList.clear();
        displayList.add(lplayer);
        displayList.add(boss);
    
        setTitleText("Bullets: " + score + ", Shields: " + lplayer.getHP()); 
        // Disable scrolling if needed
        // ... additional setup for the boss encounter ...
    }

    protected void postgame(){
        if (isBossEncounter == false){
        if (lplayer.getHP() <= 0) {
            setSplashImage(FAILURE_SPLASH_FILE_PRE);
         super.setTitleText("GAME OVER - You Lose!");
         playSound("Materials/failure.wav", 0, 0.4f);
        } 
        }else{
        if (boss.getHP() <= 0){
            setSplashImage(SUCCESS_SPLASH_FILE);
           super.setTitleText("You defeated the boss!");
           playSound("Materials/success.wav", 0, 0.4f);
        }else if (lplayer.getHP() <= 0) {
            setSplashImage(FAILURE_SPLASH_FILE);
           super.setTitleText("You were defeated by the boss!");
           playSound("Materials/failure.wav", 0, 0.4f);
        }
        if (score == 0){
            setSplashImage(FAILURE_SPLASH_FILE_BULLETS);
            super.setTitleText("You've run out the bullets!");
            playSound("Materials/failure.wav", 0, 0.4f);
        }
    }
    
    }
    
    
    //Determines if the game is over or not; Game can be over due to either a win or lose state
    protected boolean determineIfGameIsOver() {
        // Check for Win Condition
        if (score >= SCORE_TO_WIN) {
            return true; 
        }
    
        // Check for Lose Condition
        if (lplayer.getHP() <= 0 || score==0) {
            return true; 
        }
    
        if (isBossEncounter && boss != null && boss.getHP() <= 0) {
            return true; 
        }

    
        return false; // Game is not over yet
    }

    protected void showInfoSplashScreen() {
        setSplashImage(GAME_INFO_SPLASH_FILE);
        playSound("Materials/beginningBg.wav", Clip.LOOP_CONTINUOUSLY, 0.5f);
    }
    protected void showKeyControlSplashScreen() {
         setSplashImage(KEY_CONTROL_SPLASH_FILE);

    }

    //Reacts to a single key press on the keyboard
    protected void reactToKey(int key){
        // Display debug information
        setDebugText("Key Pressed!: " + KeyEvent.getKeyText(key) + ",  DisplayList size: " + displayList.size());
    
        // React only to the "advance splash" key if a splash screen is active
        if (getSplashImage() != null) {
            if (key == ADVANCE_SPLASH_KEY) {
                if (getSplashImage().equals(LEI_SPLASH_FILE)) {
                    showInfoSplashScreen(); // Show the info splash screen after the initial splash screen
                    super.setTitleText("Press \"Enter\" to Continue!");
                }else if(getSplashImage().equals(GAME_INFO_SPLASH_FILE)) {
                    showKeyControlSplashScreen();
                } else if (getSplashImage().equals(KEY_CONTROL_SPLASH_FILE)){
                    playSound("Materials/space2.wav", Clip.LOOP_CONTINUOUSLY, 0.5f);
                    setSplashImage(null); // Remove the info splash screen and start the game
                }else if (getSplashImage().equals(BOSS_SPLASH_FILE)) {   
                    setSplashImage(null); // Hide the splash screen
                    playSound("Materials/bossExist.wav", Clip.LOOP_CONTINUOUSLY, 0.35f);
            }
        }
    }

    
        // Handle game-specific key inputs
        if(isPaused==false){
            if(key==UP_KEY&&(lplayer.getY()-lplayer.getMovementSpeed())>=0){
                lplayer.setY(lplayer.getY()-lplayer.getMovementSpeed());
            }
            if(key==DOWN_KEY&&lplayer.getY()+lplayer.getMovementSpeed()<=(getWindowHeight()-lplayer.getHeight())){
                lplayer.setY(lplayer.getY()+lplayer.getMovementSpeed());
            }
            if(key==LEFT_KEY&&(lplayer.getX()-lplayer.getMovementSpeed())>=0){
                lplayer.setX(lplayer.getX()-lplayer.getMovementSpeed());
            }
            if(key==RIGHT_KEY&&(lplayer.getX()+lplayer.getMovementSpeed()<=(getWindowWidth()-lplayer.getWidth()))){
                lplayer.setX(lplayer.getX()+lplayer.getMovementSpeed());
            }
            }
    
            if(key==KEY_PAUSE_GAME){
                isPaused = !isPaused;
            }

            if (key == SHOOT_KEY && isBossEncounter && score>0) {
                playSound("Materials/shoot.wav", 0, 0.3f);
                MyBullet bullet = new MyBullet(lplayer.getX() + lplayer.getWidth(), lplayer.getY() + lplayer.getHeight() / 2);
                playSound("Materials/shoot.wav", 0, 0.3f);
                score--; 
                displayList.add(bullet);
                }
            }

}
    



