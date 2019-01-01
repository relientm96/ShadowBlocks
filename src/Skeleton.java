package project2;
import org.newdawn.slick.Input;

/**
 * Controls the movement of the skeleton 
 * @author Matthew
 *
 */
public class Skeleton extends Sprite implements Movable{
	
	private int time = 0;
	//initialize skeleton movement to up
	private String currentSkelMove = "up";
	private int timeLimit = 1000;
	
	public Skeleton(String image_src, float x, float y){
		super(image_src, x, y);
	}
	
	public void setCurrentMove(String var){
		currentSkelMove = var;
	} 
	
	public String getCurrentSkelMove(){
		return currentSkelMove;
	}
	
	/**
	 * Update the skeleton every 1 second 
	 */
	public void update(Input input, int delta){
		
		time+=delta;
		
		
		if(App.levelNumb<9){
			timeLimit = 1000;
		}
		
		else {
			timeLimit = 200;
		}
		
		//move every second
		if(time>=timeLimit){
			
				if(World.isBlocked(getX() , getY() - 1)){
					setCurrentMove("down");
					}
				
				else if(World.isBlocked(getX() , getY() + 1)){
					setCurrentMove("up");
					}
					
				move(currentSkelMove);

				//reset the timer back to 0;
				time = 0;
				}
		
		if(App.levelNumb>=9){
			if((Bullet.bulletx==getX())&&(Bullet.bullety==getY())){
				World.createExplosion("explosion",getX(),getY());
				World.destroyObjectReal(this);
				}
		}
		
		}
	
/**
 * Standard move method for movable objects 
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
						}
					}
				}
}




	




