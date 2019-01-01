package project2;

import org.newdawn.slick.Input;

public class Sheep extends Sprite{
		
	private int time = 0;

	public Sheep(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	public void update (Input input, int delta){
		time+=delta;
		super.setDirection("unknown");
		if(time>=100){
			if(!World.isBlocked(getX()-1, getY())){
				super.setImageSrc("SheepLeft");
				super.setDirection("left");
			}
			if (World.isBlocked(getX()-1, getY())){
				if(!super.getDirection().equals("left")){
					super.setImageSrc("SheepRight");
					super.setDirection("right");
				}
			}
			move(super.getDirection());
			time = 0;
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
			setX(getX() + delta_x*0.5f);
			setY(getY() + delta_y*0.5f);
			}
	}
	
}
