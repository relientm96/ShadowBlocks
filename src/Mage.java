package project2;
import org.newdawn.slick.Input;

/**
 * Controls the movement and behaviour of the mage 
 * @author Matthew
 *
 */

public class Mage extends Sprite{
	
	private float currentPlayerX  = 0;
	private float currentPlayerY = 0;
	private Coordinate playerCoords = new Coordinate();
	private float distanceX = 0;
	private float distanceY = 0;
	private float sgnX = 0;
	private float sgnY = 0;
	private float absDistanceX=0;
	private float absDistanceY=0;
	
	public Mage(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	/**
	 * An update method for mage 
	 */
	public void update(Input input, int delta){
		
		playerCoords =  World.getCurrentPlayerCoordinates(); 
		currentPlayerX = playerCoords.getX();
		currentPlayerY = playerCoords.getY();
		
		if(Player.isPlayerMoving()){
			
			float delta_x = 0;
			float delta_y = 0;
			
			//calculate necessary values
			setValues(currentPlayerX,currentPlayerY);
			
			//move according to algorithmn
			if(absDistanceX>=absDistanceY){
				delta_x = sgnX ;
				delta_y = 0 ;
			}
			else{
				delta_x = 0;
				delta_y = sgnY;
			}
			
			//if blocked dont move into it
			if(!World.isBlocked(getX()+delta_x, getY()+delta_y)){
				setX(getX()+delta_x);
				setY(getY()+delta_y);
			}
		}	
	}
	
	/**
	 * Perform the necessary calculations for the mage's algorithmn 
	 * @param currentPlayerX, Player's x coordinate
	 * @param currentPlayerY, Player's y coordinate 
	 */
	public void setValues(float currentPlayerX, float currentPlayerY){
		distanceX = calculateDistX(currentPlayerX);
		distanceY = calculateDistY(currentPlayerY);
		absDistanceX=Math.abs(distanceX);
		absDistanceY=Math.abs(distanceY);
		sgnX = valueOfSgn(distanceX);
		sgnY = valueOfSgn(distanceY);
	}
	
	public float calculateDistX(float x){
		return x-getX();
	}
	
	public float calculateDistY(float y){
		return y-getY();
	}
	
	/**
	 * Determine the direction of the mage according to the player's position 
	 * @param var, the distance between player and mage 
	 * @return, -1 or 1 , the direction of the mage 
	 */
	public float valueOfSgn(float var){
		float currentSgn = 0;
			if(var<0){
				currentSgn=-1;
			}
			else{
				currentSgn=1;
			}
		return currentSgn;
	}
	

}
