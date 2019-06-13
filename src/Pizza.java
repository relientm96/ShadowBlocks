package project2;

import org.newdawn.slick.Input;

public class Pizza extends Sprite {
	
	private boolean isPickedUp = false;
	private int time = 0;
	public static float pizzaX ;
	public static float pizzaY ;
	
	public Pizza(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	public void update (Input input, int delta){
		
		if((Player.getPlayerX()==getX())&&(Player.getPlayerY()==getY())){
			isPickedUp = true;
		}
		
		if(isPickedUp){
			setX(Player.getPlayerX()-0.6f);
			setY(Player.getPlayerY());
			World.imbaMode = true;
			time+=delta;
		}
		
		if(time>=5000){
			World.createExplosion("explosion",getX(),getY());
			isPickedUp = false;
			World.imbaMode = false;
			World.destroyObjectReal(this);
			time = 0;
		}
		
		pizzaX = getX();
		pizzaY = getY();
	}
	

}
