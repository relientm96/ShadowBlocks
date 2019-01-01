package project2;
import org.newdawn.slick.Input;

/**
 * Controls behaviour of the ice 
 * @author Matthew
 *
 */

public class Ice extends Sprite implements Pushable,Movable{
	
	private int time = 0;
	private String dir = "noMove";
	//it doesnt move initially 
	private String currentIceMoving = "noMovement";
	//store the coordinates in a  history stack for undo method  
	private MoveHistory iceMovesHist = new MoveHistory();
	private boolean undoIsPressed = false;
	private boolean iceCantMove = false;
	private float newX=0;
	private float newY=0;
	private float deltaXPlayer = 0;
	private float deltaYPlayer = 0;

	public Ice(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	/**
	 * Updates the behaviour of the ice 
	 */
	public void update(Input input, int delta) {
		Player player;
		player = World.getSprite("player", Player.class);
		time+=delta;
		//can be pushed as well by player
		if(iceCantMove){
		push(Player.getPlayerX(),Player.getPlayerY(),Player.getCurrentMove());
		}		
		
		deltaXPlayer = player.getDeltaXPlayer();
		deltaYPlayer = player.getDeltaYPlayer();

		//Add the position of the ice into its history stack when the player moves 
		if(Player.isPlayerMoving()){
			if(iceCantMove){
				iceMovesHist.addPostition(getX(), getY());
			}
		}
				
		//if it is being pushed, move the ice in that same direction until it hits a wall/bloc
		//everytime it reaches 250 ms, perform the action and restart the timer
		if(time>=125){
			if((!undoIsPressed)){
				if(World.isBlocked(getX()+player.getDeltaXPlayer() , getY()+player.getDeltaYPlayer())){
					currentIceMoving = "nomove";
					}
				else{
					move(currentIceMoving);
					time = 0;
					}
				}
			}
			
		if(undoIsPressed){
			setX(newX);
			setY(newY);
			currentIceMoving  = "noMove";
		}
		undoIsPressed = false;
	}

	/** 
	 * Can be pushable, so include the push method that moves when the player 
	 * shares the same coordinate as the ice
	 */
	public void push(float playerX, float playerY, String currentMove){
		//initialize the movement to some arbitary string 
		if ((playerX==getX())&&(playerY==getY())){
			if(!World.isBlocked(getX()+deltaXPlayer, getY()+deltaYPlayer)){
				dir = currentMove;
				move(dir);
			}
		}
	}
	
	/**
	 * Move Method for pushabe blocks 
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
		
		currentIceMoving = dir;
		
		//checking if the moved coordinate is in bounds or not into a blocked object
		if (!World.isBlocked(getX() + delta_x, getY() + delta_y)) {
			
			setX(getX() + delta_x*0.5f);
			setY(getY() + delta_y*0.5f);
			
			
			//now stone can move
			iceCantMove = false;
			}
		else if((World.isBlocked(getX() + delta_x, getY() + delta_y))){
			//stone cant move 
			iceCantMove = true;
			}
		}
	
	/**
	 * Undo Method to undo the position of the ice 
	 */
	public void undo(){
		//same as stone and tnt
		if((iceMovesHist.getSizeX()>=1)||(iceMovesHist.getSizeY()>=1)){
			iceMovesHist.removePosition();
			newX=iceMovesHist.getxPos().get(iceMovesHist.getSizeX()-1);
			newY=iceMovesHist.getyPos().get(iceMovesHist.getSizeY()-1);
			undoIsPressed = true;
		}
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
}
	
