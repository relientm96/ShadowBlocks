package project2;

import org.newdawn.slick.Input;

public class Tim extends Sprite {
	
	public static boolean followPlayer = false;
	private int time = 0;
	private String currentMove = "down";
	public static float timX;
	public static float timY;

	public Tim(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	public void update (Input input, int delta){
		time+=delta;
		if(time>=200&&(!World.level9Fail)){
			if((!World.isBlocked(getX(), getY()+1))&&(!currentMove.equals("up")))
			{
			    currentMove = "down";
			}
			else if(((!World.isBlocked(getX(), getY()-1))&&(World.isBlocked(getX(), getY()+1)))&&
			(World.isBlocked(getX()+1, getY()))&&(!World.isBlocked(getX(), getY()-1)))
			{
				currentMove = "up";
			}
			else if(!World.isBlocked(getX()+1,getY()))
			{
				currentMove = "right";
			}
			move(currentMove);
			time = 0;
		}
		
		if((Player.getPlayerX()==getX())&&(Player.getPlayerY()==getY())){
			followPlayer = true;
			
		}
		
		if(followPlayer){
			setY(Player.getPlayerY()-1);
			setX(Player.getPlayerX());
			timX = getX();
			timY = getY();
		}
		
		
		if((getX()==Pizza.pizzaX)&&(getY()==Pizza.pizzaY)){
			World.level9Fail = true;
			setX(Pizza.pizzaX);
			setY(Pizza.pizzaY);
		}
		
	}
	
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
