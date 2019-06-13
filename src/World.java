package project2;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

/**
 * Handles the information of all the game's objects
 * Sends positions and information between each object 
 * @author Matthew
 *
 */

public class World {	
	
	//create an array list of sprites
	private static ArrayList<Sprite> sprites = new ArrayList<>();
	private static ArrayList<Sprite> targets = new ArrayList<>();	
	private static ArrayList<Bullet> bullets = new ArrayList<>();
	public static ArrayList<Portal> portals = new ArrayList<>();
	//doors are always close initially
	private static String doorState = "close"; 	

	//index of the door in the sprites list
	public static String doorState2 = "close";
	public static String doorState3= "close";
	//cracked wall burst?
	private static  boolean crackedBursted = false ;
	//shooting
	public static boolean isShooting = false;
	//20 undo's available
	private int undoMoves = 20;
	private int numbPlayerMoves = 0;
	private int levelNumber = 0;
	private static int gameTime = 0;
	private int timeVar = 0;
	public static boolean imbaMode = false;
	private int pizzaTime = 5;
	private int timeTempPizza = 0;
	public static boolean level9Special = false;
	public static boolean level9Fail = false;
	private int shortClock = 0;
	private boolean pizzaPicked = false;
	public static boolean changedLevel = false;
	private static int maxPortal = 2;
	private static int portalNumb = 0;
	public static boolean restartState = false;
	public static boolean PortalActive = false;
	
	public World(int levelNumb){
		//creating the arrays of different kinds of objects
    	sprites=Loader.loadSprites("levels/" + levelNumb + ".lvl");
    	targets=createTargets(sprites);
    	levelNumber = levelNumb;
    	shortClock = initialTime(levelNumber);
	}
	
	/**
	 * Creates a an array list of only target objects
	 * @param sprites, The ArrayList holding the game objects
	 * @return targets, an array list that contains only targets and its positions
	 */
	//create an array list of targets to check its coordinates to finish level
	public ArrayList<Sprite> createTargets(ArrayList<Sprite> sprites){
		ArrayList<Sprite> targets = new ArrayList<>(); 
		for(Sprite sp : sprites){
			if(sp.getImageSrc().equals("target")){
				targets.add(sp);
			}
		}
		return targets;
	}
	
	/**
	 * Decides whether that particular coordinate is blocked by a blocked object
	 * @param x, x position of object (float)
	 * @param y y position of object (float)
	 * @return true if the particular position is blocked 
	 */
	public static boolean isBlocked(float x, float y){
		for(int i = 0; i<sprites.size(); i++){
			if(blockedItem(sprites.get(i).getImageSrc())){
				//check for the boundaries making sure it is within map and also if its a blocked coordinate
				if((x==sprites.get(i).getX())&&(y==sprites.get(i).getY())&&
						(x>=0) && (x<Loader.getMap_Width()) && (y>=0) && (y<Loader.getMap_Height()))
					return true;
			}
		}
		//if it reaches here, the coordinates did not match up to any blocked coordinate
		return false;
	}
	
	/**
	 * A method that decides whether the object is a 'blocked' object
	 * @param obj, name of object used to test if its a blocked object
	 * @return true if the item is blockable 
	 */
	public static boolean blockedItem(String obj){
		if((obj.equals("wall"))||(obj.equals("stone"))||(obj.equals("cracked"))
				||(obj.equals("tnt"))||(obj.equals("ice"))||(obj.equals("door"))||(obj.equals("door2"))||
		(obj.equals("door3")))
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Check if item can be destroyed ( removed from the sprite list )
	 * @param x , x coordinate of object
	 * @param y, y coordinate of object
	 * @return true if the sprite can be destroyed 
	 */
	public static boolean destroyableItem(float x, float y){
		for(int i = 0; i<sprites.size(); i++){
			if((sprites.get(i).getImageSrc().equals("cracked"))){
				if((x==sprites.get(i).getX())&&(y==sprites.get(i).getY())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Check if object can be pushed
	 * @param x , x coordinate
	 * @param y, y coordinate
	 * @return true if the object is pushable 
	 */
	public static boolean pushableObject(float x, float y){
		for(int i = 0; i<sprites.size(); i++){
			if((sprites.get(i).getImageSrc().equals("stone"))||(sprites.get(i).getImageSrc().equals("ice"))||
					(sprites.get(i).getImageSrc().equals("tnt"))){
				//check for the boundaries making sure it is within map and also if its a blocked coordinate
				if((x==sprites.get(i).getX())&&(y==sprites.get(i).getY())){
					return true;
				}
			}
		}
		//if it reaches here, the coordinates did not match up to any blocked coordinate
		return false;
	}
	
	/** 
	 * Counts the number of moves made by player
	 */
	public void numbPlayerMoveCounter(){
		if(Player.isPlayerMoving()){
			numbPlayerMoves+=1;
		}
	}
	
	/**
	 * Sends the coordinates of the player to other classes that need them 
	 * @return playerCoord, the coordinates of the player (float x and float y)
	 */
	public static Coordinate getCurrentPlayerCoordinates(){
		Coordinate playerCords = new Coordinate();
		for(Sprite sp : sprites){
			sp.getImageSrc().equals("player");
			playerCords.setX(sp.getX());
			playerCords.setY(sp.getY());
		}
		return playerCords;
	}
	
	public int initialTime(int levelNumb){
		if(levelNumb==5){
			return 30;
		}
		else if (levelNumb==6){
			return 25;
		}
		else if (levelNumb==18){
			return 10;
		}
		else {
			return 0;
		}
	}

	/**
	 * Run all update methods in sprites  
	 * @param input, the input from keyboard
	 * @param delta, the time in ms, after each frame 
	 */
	public void update(Input input, int delta) {
		
		if(restartState){
			removeObjectsRestarted();
		}
		restartState = false;
		timeVar+=delta;
		if(timeVar>=1000){
			gameTime+=1;
			shortClock-=1;
			timeVar=0;
		}
		
		if(imbaMode){
			pizzaPicked = true;
			timeTempPizza += delta;
			if(timeTempPizza>=995){
				pizzaTime-=1;
				timeTempPizza = 0;
			}
		}
		
		if(levelNumber==5){
			if(isOutofTime(30)){
				App.isGamePaused = true;
			}
		}
		
		if(levelNumber==6){
			if(isOutofTime(25)){
				App.isGamePaused = true;
			}
		}
		
		if(levelNumber==18){
			if(isOutofTime(10)){
				App.isGamePaused = true;
			}
		}
	
			
		//shooot
		shootBullet();
		if(restartState){
			bullets.removeAll(bullets);
		}
		
		for (int i = 0 ; i<sprites.size(); i++) {
			if (sprites.get(i) != null) {
				sprites.get(i).update(input, delta);
			}
		}
		
		for (int i = 0 ; i < bullets.size(); i++){
			if(bullets.get(i)!=null){
				bullets.get(i).update(input,delta);
			}
		}
		
		for (int i = 0 ; i < portals.size(); i++){
			if(portals.get(i)!=null){
				portals.get(i).update(input,delta);
			}
		}
		
		
		//update the number of moves by player
		numbPlayerMoveCounter();
	
		
		//Undo when Z is Pressed
		if (input.isKeyPressed(Input.KEY_Z)){
			if(undoMoves>0){
				undoMovables();
				undoMoves-=1;
				if(numbPlayerMoves>0){
					numbPlayerMoves-=1;
				}
			}
		}
		
		if((App.levelNumb==4)||(App.levelNumb==18)){
			if(input.isKeyPressed(Input.KEY_P)){
				Player player;
				player = World.getSprite("player", Player.class);
				if(!World.isBlocked(Player.getPlayerX()+player.getDeltaXPlayer(),
						Player.getPlayerY()+player.getDeltaYPlayer())){
					createPortal("portal",Player.getPlayerX()+player.getDeltaXPlayer(),
						Player.getPlayerY()+player.getDeltaYPlayer());
				}
			}
		}
		
		if(changedLevel == true){
			portals.removeAll(portals);
			portalNumb = 0; 
		}
	     changedLevel = false;
	}
    
	/**
	 * Run undo method in all movable sprites
	 */
	private void undoMovables() {
		for(Sprite sp: sprites){
			if((sp.getImageSrc().equals("player"))||(sp.getImageSrc().equals("stone"))||
			(sp.getImageSrc().equals("caro"))||(sp.getImageSrc().equals("tnt")||(sp.getImageSrc().equals("ice")))){
				sp.undo();
			}
		}
	}
	
	/**
	 * Perform the render method in all sprites (drawing on screen)
	 * @param g , Slicik's graphic object 
	 * @throws SlickException
	 */
	public void render(Graphics g) throws SlickException {
	
		for (int i = 0 ; i < sprites.size(); i++){
			if(sprites.get(i) instanceof Floor){
				sprites.get(i).render(g);
			}
		}
		
		for (int i = 0 ; i < bullets.size(); i++){
			if(bullets.get(i)!=null){
				bullets.get(i).render(g);
			}
		}
		
		for (int i = 0 ; i < portals.size(); i++){
			if(portals.get(i)!=null){
				portals.get(i).render(g);
			}
		}
		
		for (int i = 0 ; i < sprites.size(); i++){
			if((sprites.get(i)!=null)&&(!(sprites.get(i) instanceof Floor))){
				sprites.get(i).render(g);
			}
		}
		
	
		
		
	
		
		//different levels have different titles and strings printed 
		
		if(levelNumber==0){
			g.drawString("Level 0 : Tutorial", 300, 0);
			g.drawString("Use arrow keys to move around", 5, 30);
			g.drawString("Get the stones into the targets!", 5, 45);
			g.drawString("R to restart or Z to undo", 5, 60);
		}
		
		if(levelNumber==1){
			g.drawString("Level 1 : Boom!", 320, 0);
		}
		
		if(levelNumber==2){
			g.drawString("Level 2 : Slippery Slope!", 300, 570);
		}
		
		if(levelNumber==3){
			g.drawString("Level 3 : You're not alone now", 250, 20);
			g.drawString("Be careful! dont hit any Enemies!", 240, 40);
		}
		
		if(levelNumber==4){
			g.drawString(" Level 4 : The MAGE-ician ", 269, 30);
			g.drawString("      Press P to make a portal!  ", 222, 50);
			g.drawString("Press Y to teleport between portals! ", 220, 70);
		}
		
		if(levelNumber==5){
			g.drawString("Level 5 : Angry Trump Wall ", 269, 30);
			g.drawString("Use SpaceBar to shoot the trumps!", 240, 50);
			g.drawString("QUICK you only have 30 seconds!", 250, 70);
			g.drawString("Time: " + shortClock, 350,90);
		}
		
		if(levelNumber==6){
			g.drawString("  Level 6 : Angry Trump Wall 2! ", 240, 20);
			g.drawString(" QUICK you only have 25 seconds!", 240, 50);
			g.drawString("Time: " + shortClock, 350,80);
		}
		
		if(levelNumber==7){
			g.drawString("  Level 7 : Useful Switch! ", 260, 50);
			//g.drawString(" QUICK you only have 20 seconds!", 240, 50);
			//g.drawString("Time: " + gameTime, 350,80);
		}
		
		if(levelNumber==8){
			g.drawString("Level 8 : Papa ANGRY, Y U NO REPLY WASAP! ", 200, 60);
			g.drawString("Run away before Papa rotan u", 260, 80);
		}
		
		if(levelNumber==9){
			g.drawString("Level 9: Pizza Time!", 300, 40);
			g.drawString("Help Kay Stop Greedy Timothy", 260, 60);
			g.drawString("Grab him and Bring him Back!!!", 260, 80);
			if(Tim.followPlayer){
				g.drawString("GRABBED", 350, 110 );
			}
		}
		
		if(levelNumber==10){
			g.drawString("  Level 10: More Enemies! ", 270, 10);
		//	g.drawString(" QUICK you only have 40 seconds!", 240, 30);
		//	g.drawString("Time: " + gameTime, 350,60);
		}
		
		if(levelNumber==11){
			g.drawString("  Level 11: Order of Things ", 200, 20);
			g.drawString(" *Can't kill rogue at this level ", 200, 40);
		}
		
		if(levelNumber==12){
			g.drawString("  Level 12: Introduction to multiple switches ", 170, 100);
		}
		
		if(levelNumber==13){
			g.drawString("  Level 13: Icy Doors ", 170, 80);
		}
		
		if(levelNumber==14){
			g.drawString("  Level 14: Caro is a Follower ", 240, 80);
		}
		
		if(levelNumber==15){
			g.drawString("  Level 15: Stop Following Me! ", 240, 80);
		}
		
		if(levelNumber==16){
			g.drawString("  Level 16: Twist and Twist and Twist", 230, 130);
		}
		
		if(levelNumber==17){
			g.drawString("  Level 17: AND Gate", 230, 20);
		}
		
		if(levelNumber==18){
			g.drawString("**You can use Portals", 300,120);
			g.drawString("  Level 18: Helper Penguin", 230, 50);
			g.drawString("QUICK you only have 10 seconds!", 220, 70);
			g.drawString("Time: " + shortClock, 320,90);
		}
		
		
		if(levelNumber<App.MAX_LEVEL){
			g.drawString("Moves: " + numbPlayerMoves, 0, 0);
			g.drawString("Undo Count: " + undoMoves, 670, 0);
		}
		
		//print this out when the player runs out of undos
		if(undoMoves==0){
			g.drawString("No more undos!", 670, 30);
		}
		
		if(pizzaPicked){
			if(pizzaTime>0){
				g.drawString("INVULNERABILITY!!!", 50, 50);
				g.drawString("PizzaTime: " + pizzaTime, 50, 80);
				g.drawString("INVULNERABILITY!!!", 600, 50);
				g.drawString("PizzaTime: " + pizzaTime, 600, 80);
			}
		}
		
		if(App.isGamePaused){
			if((levelNumber!=9)){
				if(checkTargets()){
					Font pulsingFont = new Font("arial", Font.PLAIN, (int) 40);
					TrueTypeFont pulsing = new TrueTypeFont(pulsingFont, true);
					pulsing.drawString(210, 230 , "       You DID IT!");
					pulsing.drawString(210, 280, "Press C to continue");
					}
				else {
					Font pulsingFont = new Font("arial", Font.PLAIN, (int) 40);
					TrueTypeFont pulsing = new TrueTypeFont(pulsingFont, true);
					pulsing.drawString(210, 230 , "       You FAILED :(");
					pulsing.drawString(210, 280, "Press F1 to try again");
					}
				}
			if(levelNumber==9){
					if(level9Special){
						Font pulsingFont = new Font("arial", Font.PLAIN, (int) 40);
						TrueTypeFont pulsing = new TrueTypeFont(pulsingFont, true);
						pulsing.drawString(210, 230 , "       You DID IT!");
						pulsing.drawString(210, 280, "Press C to continue");
						}
					if(level9Fail) {
						Font pulsingFont = new Font("arial", Font.PLAIN, (int) 40);
						TrueTypeFont pulsing = new TrueTypeFont(pulsingFont, true);
						pulsing.drawString(210, 230 , "       You FAILED :(");
						pulsing.drawString(210, 280, "Press F1 to try again");
					}
			}
				
				
			}
		}
	
	
	/**
	 * Check ifi all targets are covered by stones or ice
	 * @return true if all targets are covered by blocks 
	 */
	public boolean checkTargets(){
		//now check if the stone's positions are the same as targets	
		int correctTargets = 0;
		for (Sprite sp : sprites){
			if((sp.getImageSrc().equals("stone"))||(sp.getImageSrc().equals("ice"))){
					for(Sprite currentTarget: targets){
						if(((sp.getX()==currentTarget.getX())&&(sp.getY()==currentTarget.getY()))){
							correctTargets+=1;
						}
						else {
							continue;
						}
					}
				}
			}
		
		//if the number of filled targets equal the number of targets, win!
		if (correctTargets==targets.size()){
			return true;
		}
		//if not, try again :(
		else {
			return false;
		}
	}
	
	/**
	 * Perform this method when an explosion happens ( if tnt is pushed into cracked wall)
	 */
	public static void explosionEvent() {
		
		float explosiveX=0;
		float explosiveY=0;
		
		//obtain the coordinates of the cracked wall
		for(Sprite sprite : sprites){
			if(sprite.getImageSrc().equals("cracked")){
				explosiveX=sprite.getX();
				explosiveY=sprite.getY();
			}
		}
		
		for(int i=0 ; i<sprites.size() ; i++){
			if((sprites.get(i).getImageSrc().equals("cracked"))||(sprites.get(i).getImageSrc().equals("tnt"))){
				sprites.remove(i);
				setCrackedBursted(true);
				createExplosion("explosion",explosiveX,explosiveY);
			}
		}
	}
	
	/**
	 * Create an explosion object and add into sprites for explosion event
	 * @param explosion, the string of the image_src of the explosion object
	 * @param x, x position of the explosion
	 * @param y , y position of the explosion 
	 */
	static void createExplosion(String explosion,float x,float y){
		Sprite newExplosive = new Explosion(explosion,x,y);
		sprites.add(newExplosive);
	}
	
	public static void createPortal(String name, float x, float y){
		Portal portal = new Portal(name,x,y);
		portalNumb +=1;		
		if(portalNumb<=maxPortal){
			portals.add(portal);
			}
		else {
			portals.removeAll(portals);
			portalNumb = 0;
			}
		}
	
	
	public static void removeObjectsRestarted(){
		portals.removeAll(portals);
		bullets.removeAll(bullets);
		}
	


	/**
	 * See if enemy attacked the player
	 * @return, true if enemy and player share the same coordinates
	 */
	public boolean isHit(){
		
		Sprite thePlayer = null;
		
		//get the player's coordiantes
		for(Sprite sp:sprites){
			if(sp.getImageSrc().equals("player")){
				thePlayer = sp;
			}
		}
		
		if(!imbaMode){
		//check if the enemy share the same coordinates as the player
		for(Sprite sp : sprites){
			if((sp.getImageSrc().equals("skeleton"))||(sp.getImageSrc().equals("caro"))||(sp.getImageSrc().equals("trump"))||
			(sp.getImageSrc().equals("rogue"))||((sp.getImageSrc().equals("mage")))||((sp.getImageSrc().equals("papa"))))
			{
				if((sp.getX()==thePlayer.getX())&&(sp.getY()==thePlayer.getY())){
					App.isGamePaused = true;
					return true;
				}
			}
		}
		}
		return false;
	}
	

	/**
	 * Remove the game object from sprite list to destory it 
	 * @param imageName, the name of the image needed to be removed 
	 */
	public static void destroyObject(String imageName) {
		for(int i = 0 ; i < sprites.size() ; i++){
			if(sprites.get(i).getImageSrc().equals(imageName)){
				sprites.remove(i);
			}
		}
	}
	
	/**
	 * Gte door state , open or close
	 * @return doorState, string that is "open" or "close"
	 */
	public static String getDoorState(){
		return doorState;
	}
	
	/**
	 * Set door state to  open or close
	 * @param var, string that is "open" or "close"
	 */
	public static void setDoorState(String var){
		doorState = var;
	}

	/**
	 * Check if the cracked wall is destroyed 
	 * @return true if the cracked wall is destroyed and removed from sprites list 
	 */
	public static boolean isCrackedBursted() {
		return crackedBursted;
	}

	/**
	 * set it to true when the tnt and cracked share coordinates
	 * @param crackedBursted, the boolean value for the event
	 */
	public static void setCrackedBursted(boolean crackedBursted) {
		World.crackedBursted = crackedBursted;
	}
	
	public void shootBullet(){
		if(isShooting){
			Bullet bullet = new Bullet("bullet", Player.getPlayerX() , Player.getPlayerY(),Player.getCurrentMove());
			bullets.add(bullet);
		}
		isShooting = false;
	}
	
	public static void destroyBullet(Bullet tempBullet){
		bullets.remove(tempBullet);
	}
	
	public static void destroyObjectReal(Sprite object){
		sprites.remove(object);
	}
	
	public static boolean isEnemy(float bulletX, float bulletY){
		for(Sprite sp : sprites){
			if((sp.getImageSrc().equals("skeleton"))||(sp.getImageSrc().equals("rogue"))||
				(sp.getImageSrc().equals("caro"))||((sp.getImageSrc().equals("mage"))||
						(sp.getImageSrc().equals("trump"))))
			   {
				if((sp.getX()==bulletX)&&(sp.getY()==bulletY)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static Coordinate playerTeleport(float x, float y){
		int thisInd = 0;
		int newInd = 0;
		Coordinate portalCords = new Coordinate();
		for(int i = 0; i < portals.size() ; i++){
			if((portals.get(i).getX()==x)&&(portals.get(i).getY()==y)){
				thisInd = i;
			}
		if(thisInd==1){
			newInd = 0;
			}
		else if (thisInd == 0){
			newInd = 1;
			}
		for(int j = 0; j < portals.size(); j++){
			if(j==newInd){
				portalCords.setX(portals.get(j).getX());
				portalCords.setY(portals.get(j).getY());
				break;
				}
			}
		}
		if((portalCords.getX()!=0)&&(portalCords.getY()!=0)){
			return portalCords;
		}
		else {
			System.out.println("null");
			return null;
		}
	}
	
	
	
	public static boolean isOutofTime(int time){
		if(gameTime>time){
			return true;
		}
		return false;
	}
	
	public int getGameTime(){
		return gameTime;
	}
	
	public void setGameTime(int var){
		gameTime = var;
	}
	
	public static <C extends Sprite> C getSpriteOfType(String name, float x, float y, Class<C> subclass){
		for(Sprite sp : sprites){
			if(subclass.isInstance(sp)){
				if((sp.getX()==x)&&(sp.getY()==y)){
					return subclass.cast(sp);
				}
			}
		}
		return null;
	}
	
	public static <C extends Sprite> C getSprite(String name, Class<C> subclass){
		for(Sprite sp : sprites){
			if(subclass.isInstance(sp)){
				return subclass.cast(sp);
				}
			}
		return null;
	}
	
	public static <C> boolean isSpriteAtLocation(float x, float y, Class <C> subclass){
		for(Sprite sp : sprites){
			if((sp.getX()==x)&&(sp.getY()==y)){
				if(subclass.isInstance(sp)){
					return true;
				}
			}
		}
		return false;
	}
	
	
	public static boolean checkPortals(float x, float y){
		for(int i=0 ; i < portals.size(); i++){
			if((portals.get(i).getX()==x)&&(portals.get(i).getY()==y)){
			return true;
			}
		}
		return false;
	}

	public static void destroyPortals() {
		portals.removeAll(portals);
		portalNumb = 0;
		}
	
	public void sheepMethod(){
		System.out.println("shepeles");
	}
	
	public void printWorld(){
		System.out.println("World with levelNumber "+ levelNumber);
	}
	
	
	
}
	
	
	

