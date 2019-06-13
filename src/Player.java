package project2;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Controls the movement and behaviour of the player 
 * @author Matthew
 *
 */
public class Player extends Sprite implements Movable  {
	
	private static float playerX;
	private static float playerY;
	private int numbMoves=0;
	//initialize the movement of the player as no movement 
	private static String currentMove = "unknown";
	//player doesnt move yet 
	private static boolean isPlayerMoving = false;
	// the history stack of the player's movements
	private MoveHistory playerMovesHist = new MoveHistory();
	private  boolean rightSide = false;
	private  boolean leftSide = false;
	private  boolean upSide= false;
	private  boolean downSide = false;
	private float deltaXPlayer = 0;
	private float deltaYPlayer = 0;
	public static Coordinate portalCoords = new Coordinate();
	
	public Player(String image_src, float x, float y){
		super(image_src, x, y);
	}
	
	/**
	 * Update movement of player 
	 */
	public void update(Input input, int delta) {
		
			String dir = "nomove";
			float delta_x=0;
			float delta_y=0;
			
			//if the size of the player coordinates are 0, add the first coordinate in
			if((playerMovesHist.getSizeX()==0)||(playerMovesHist.getSizeY()==0)){
				playerMovesHist.addPostition(getX(), getY());
			}
	
			if (input.isKeyPressed(Input.KEY_LEFT)) {
				rightSide = false;
				upSide = false;
				leftSide = true;
				downSide = false;
				setPlayerMoving(true);
				dir = "left";
				currentMove=dir;
				numbMoves+=1;
				delta_x = -1;
				playerMovesHist.addPostition(getX()+delta_x, getY());
			}
			
			else if (input.isKeyPressed(Input.KEY_RIGHT)) {
				rightSide = true;
				upSide = false;
				leftSide = true;
				downSide = false;
				setPlayerMoving(true);
				dir = "right"; 
				currentMove=dir;
				numbMoves+=1;
				delta_x = 1;
				playerMovesHist.addPostition(getX()+delta_x, getY());
			}
			else if (input.isKeyPressed(Input.KEY_UP)) {
				rightSide = false;
				upSide = true;
				leftSide = false;
				downSide = false;
				setPlayerMoving(true);
				dir = "up";
				currentMove=dir;
				numbMoves+=1;
				delta_y = -1;
				playerMovesHist.addPostition(getX(), getY()+delta_y);
			}
			else if (input.isKeyPressed(Input.KEY_DOWN)) {
				rightSide = false;
				upSide = false;
				leftSide = false;
				downSide = true;
				setPlayerMoving(true);
				dir = "down";
				currentMove=dir;
				numbMoves+=1;
				delta_y = 1;
				playerMovesHist.addPostition(getX(), getY()+delta_y);
			}
			else if(input.isKeyPressed(Input.KEY_SPACE)){
				World.isShooting = true;
			}

			else {
				setPlayerMoving(false);
			}
			
			//always set the value of the current player position so that the stone can be pushed accordingly 
			setPlayerX(getX()+delta_x);
			setPlayerY(getY()+delta_y);
			deltaXPlayer=deltaXValue(currentMove);
			deltaYPlayer=deltaYValue(currentMove);

			//moving the player
			move(dir);
			
			if(rightSide){
				try {
					super.setCurrentImage(super.stringToImage("player_right"));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
			
			else if(leftSide) {
				try {
					super.setCurrentImage(super.stringToImage("player_left"));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}		
			
			else if (upSide){
				try {
					super.setCurrentImage(super.stringToImage("player_up"));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			
			}
			else if (downSide){
				try {
					super.setCurrentImage(super.stringToImage("player_down"));
				} catch (SlickException e) {
					e.printStackTrace();
				}
			
			}
			
			if(World.PortalActive){
				setX(portalCoords.getX());
				setY(portalCoords.getY());
				World.destroyPortals();
			}
			World.PortalActive = false;

		}
	/**
	 * Move method inspired from project 1 sample solution 
	 */
	public void move(String dir){
		// Translate the direction to an x and y displacement
		float delta_x = 0,
			  delta_y = 0;
		switch (dir) {
			case "left":
				delta_x = -1;
				break;
			case "right":
				delta_x = 1;
				break;
			case "up":
				delta_y = -1;
				break;
			case "down":
				delta_y = 1;
				break;
		}
		
		//move player when nothing is blocking
		if (!World.isBlocked(getX() + delta_x, getY() + delta_y)) {
			setX(getX() + delta_x);
			setY(getY() + delta_y);
			}
		
		//if destination tile has a pushable item, only go into it if it is not blocked by other objects
		else if (World.pushableObject(getX() + delta_x, getY()+ delta_y)){
			//if the following tile is not blocked move into the destination
			if (!World.isBlocked(getX() + 2*delta_x, getY() + 2*delta_y)){
				setX(getX() + delta_x);
				setY(getY() + delta_y);
				
				if(World.isSpriteAtLocation(getX(), getY(),Stone.class)){
					Stone pushingItem ;
					pushingItem = World.getSpriteOfType("stone", getX() , getY(),  Stone.class);
					pushingItem.push(getX(), getY(), getCurrentMove());
					}
				else if(World.isSpriteAtLocation(getX(), getY(),Ice.class)){
					Ice pushingItem ;
					pushingItem = World.getSpriteOfType("ice", getX() , getY(),  Ice.class);
					pushingItem.push(getX(), getY(), getCurrentMove());
					}
				else if(World.isSpriteAtLocation(getX(), getY(),TNT.class)){
					TNT pushingItem ;
					pushingItem = World.getSpriteOfType("tnt", getX() , getY(), TNT.class);
					pushingItem.push(getX(), getY(), getCurrentMove());
					}
				}
				}
			}
	
	/**
	 * undo method for the player 
	 */
	public void undo(){
		
		if((playerMovesHist.getSizeX()>=1)||(playerMovesHist.getSizeY()>=1)){
			playerMovesHist.removePosition();
			
			float playerNewX = playerMovesHist.getxPos().get(playerMovesHist.getSizeX()-1);
			float playerNewY = playerMovesHist.getyPos().get(playerMovesHist.getSizeY()-1);
			
			if(!World.isBlocked(playerNewX, playerNewY)){
				setX(playerMovesHist.getxPos().get(playerMovesHist.getSizeX()-1));
				setY(playerMovesHist.getyPos().get(playerMovesHist.getSizeY()-1));
			}
			
			if(numbMoves>0){
				numbMoves-=1;
			}
		}
	}
	
	public static String getCurrentMove(){
		return currentMove;
	}

	public static float getPlayerY() {
		return playerY;
	}

	public static void setPlayerY(float playerY) {
		Player.playerY = playerY;
	}

	public static float getPlayerX() {
		return playerX;
	}

	public static void setPlayerX(float playerX) {
		Player.playerX = playerX;
	}

	public static boolean isPlayerMoving() {
		return isPlayerMoving;
	}

	public static void setPlayerMoving(boolean isPlayerMoving) {
		Player.isPlayerMoving = isPlayerMoving;
	}
	public float deltaXValue(String dir){
		if(dir.equals("left")){
			return (float) -1.0;
		}
		else if(dir.equals("right")){
			return (float) 1.0;
		}
		else 
			return 0;
	}
	
	public float deltaYValue(String dir){
		if(dir.equals("up")){
			return (float) -1.0;
		}
		else if(dir.equals("down")){
			return (float) 1.0;
		}
		else 
			return 0;
	}

	public float getDeltaYPlayer() {
		return deltaYPlayer;
	}

	public float getDeltaXPlayer() {
		return deltaXPlayer;
	}

}

	

