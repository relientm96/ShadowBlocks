package project2;

import org.newdawn.slick.Input;

public class Penguin extends Sprite {


	private int time = 0;
	//initialize skeleton movement to up
	private static String currentPengMove = "up";
	private int timeLimit = 1000;
	public static float penguinX;
	public static float penguinY;
	
	public float getPenguinX() {
		return penguinX;
	}

	public void setPenguinX(float penguinX) {
		Penguin.penguinX = penguinX;
	}

	public float getPenguinY() {
		return penguinY;
	}

	public void setPenguinY(float penguinY) {
		Penguin.penguinY = penguinY;
	}

	public Penguin(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	public void setCurrentMove(String var){
		currentPengMove = var;
	} 
	
	public static String getCurrentPengMove(){
		return currentPengMove;
	}
	
	/**
	 * Update the skeleton every 1 second 
	 */
	public void update(Input input, int delta){
		
		penguinX = getX();
		penguinY = getY();
		
		time+=delta;
		
		timeLimit = 300;

		if(time>=timeLimit){
			
			 if(World.isBlocked(getX()-1 , getY())){
				setCurrentMove("right");
				}
				
			else if(World.isBlocked(getX()+1 , getY() )){
				setCurrentMove("left");
				}
				move(currentPengMove);
				//reset the timer back to 0;
				time = 0;
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
