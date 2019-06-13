package project2;
import org.newdawn.slick.Input;

/**
 * Controls the movement of the tnt 
 * @author Matthew
 *
 */
public class TNT extends Sprite implements Pushable,Movable{
	
	private MoveHistory tntMovesHist = new MoveHistory();
	private boolean undoIsPressed = false;
	private float newX=0;
	private float newY=0;

	public TNT(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	/**
	 * Updates the behaviour of the tnt 
	 */
	public void update(Input input, int delta) {
		
		//same as stone except only player can push it
		push(Player.getPlayerX(),Player.getPlayerY(),Player.getCurrentMove());
		
		if(Player.isPlayerMoving()){
			tntMovesHist.addPostition(getX(), getY());
		}
		
		if(undoIsPressed){
			setX(newX);
			setY(newY);
		}
		
		undoIsPressed = false;
		
	}
	
	/**
	 * For a pushable object, include the push method
	 */
	public void push(float playerX, float playerY, String currentMove){
		String dir = "noMove";
		
		if ((playerX==getX())&&(playerY==getY())){
			dir = currentMove;
			move(dir);
		}
	}
	
	/**
	 * Move method for movable objects 
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
		
		//checking if the moved coordinate is in bounds or not into a blocked object
	
		if (World.destroyableItem(getX() + delta_x ,getY() + delta_y)){
				World.explosionEvent();
			}
		if (!World.isBlocked(getX() + delta_x, getY() + delta_y)) {
			setX(getX() + delta_x);
			setY(getY() + delta_y);
		}
	}
	/**
	 * undo method for movable sprites 
	 */
	public void undo(){
		//same logic as stone
		if((tntMovesHist.getSizeX()>=1)||(tntMovesHist.getSizeY()>=1)){
			tntMovesHist.removePosition();
			newX=tntMovesHist.getxPos().get(tntMovesHist.getSizeX()-1);
			newY=tntMovesHist.getyPos().get(tntMovesHist.getSizeY()-1);
			undoIsPressed = true;
		}
	}
}
