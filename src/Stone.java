package project2;
import org.newdawn.slick.Input;

/**
 * A class that controls the movement of the stone 
 * @author Matthew
 *
 */
public class Stone extends Sprite implements Pushable,Movable{

	//create a history stack of its coordinates 
	private MoveHistory stoneMovesHist = new MoveHistory();
	private float newX=0;
	private float newY=0;
	private boolean undoIsPressed = false;

	public Stone(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	/**
	 * Updates the movement of the stone when is pushed 
	 */
	public void update(Input input, int delta) {
		
		//if there is no history, add the first coordinate in
		if((stoneMovesHist.getSizeX()==0)||(stoneMovesHist.getSizeY()==0)){
			stoneMovesHist.addPostition(getX(), getY());
		}
		
		//If the player is moving, add the new stone position into history
		if(Player.isPlayerMoving()){
			stoneMovesHist.addPostition(getX(), getY());
		}
		//if undo , set the new x's and y's
		if(undoIsPressed){
			setX(newX);
			setY(newY);
		}
		//reset undo state back 
		undoIsPressed = false;
	}

	/**
	 * Push method for pushable objects 
	 */
	public void push(float playerX, float playerY, String currentMove){
		String dir = "noMove";
		if ((playerX==getX())&&(playerY==getY())){
			dir = currentMove;
			move(dir);
		}
	}
	/**
	 * Standard move method for movable objects 
	 * @param dir
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
		
		//move stone if its not blocked by anything
		if (!World.isBlocked(getX() + delta_x, getY() + delta_y)) {
			setX(getX() + delta_x);
			setY(getY() + delta_y);
			}
		}
	
	/**
	 * undo method for movable sprites 
	 */
	public void undo(){
		//only perform undo when the size of the history stack is at least 1
		if((stoneMovesHist.getSizeX()>=1)||(stoneMovesHist.getSizeY()>=1)){
			//remove the last element in the stack
			stoneMovesHist.removePosition();	
			//set the new x and y destinations from the new last element of the stack
			newX=stoneMovesHist.getxPos().get(stoneMovesHist.getSizeX()-1);
			newY=stoneMovesHist.getyPos().get(stoneMovesHist.getSizeY()-1);
			undoIsPressed = true;
		}
	}
}



