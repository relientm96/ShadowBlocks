package project2;

import org.newdawn.slick.Input;

public class Trump extends Sprite {
	
	private int time = 0;
	//initialize skeleton movement to up
	private String currentSkelMove = "down";
	
	public Trump(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	public void setCurrentMove(String var){
		currentSkelMove = var;
	} 
	
	public  String getCurrentSkelMove(){
		return currentSkelMove;
	}
	
	/**
	 * Update the skeleton every 1 second 
	 */
	public void update(Input input, int delta){
		
		time+=delta;
		
		if(App.levelNumb==6){
			if(time>=200){
				
				
				if(World.isBlocked(getX() , getY() - 1)){
					setCurrentMove("down");
					move(currentSkelMove);
				}
				
				else if(World.isBlocked(getX() , getY() + 1)){
					setCurrentMove("up");
					move(currentSkelMove);
				}
				
				else {
					move(currentSkelMove);
					}
				//reset the timer back to 0;
				time = 0;
				}
		}
		
		//move every second
		if(time>=1000){
			
				if(World.isBlocked(getX() , getY() - 1)){
					setCurrentMove("down");
					move(currentSkelMove);
				}
				
				else if(World.isBlocked(getX() , getY() + 1)){
					setCurrentMove("up");
					move(currentSkelMove);
				}
				
				else {
					move(currentSkelMove);
					}
				//reset the timer back to 0;
				time = 0;
				}

		if((Bullet.bulletx==getX())&&(Bullet.bullety==getY())){
			if((!World.changedLevel)||(!World.restartState)){
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
	

	// returns the square of val
	public float square(float val) {
		return val * val;
	}

	// returns the distance between (x1, y1) and (x2, y2)
	public float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(square(x2 - x1) + square(y2 - y1));
	}
	
}

	

