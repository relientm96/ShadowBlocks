package project2;
import org.newdawn.slick.Input;
/**
 * Controls the movement of the rogue 
 * @author Matthew
 *
 */
public class Rogue extends Sprite implements Movable{

	//starts moving left first 
	private String currentMove = "left";
	
	public Rogue(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	
	public void setCurrentMove(String var){
		currentMove = var;
	}
	
	public String getCurrentMove(){
		return currentMove;
	}
	/**
	 * Update the movement of the rogue accordingly 
	 */
	public void update(Input input, int delta){
		
		float delta_x = 0;
		
		//perform actions only when player is moved
	if(Player.isPlayerMoving()){
		
		//setting values for delta x 
		if(currentMove.equals("left")){
			delta_x = -1;
		}
		else if (currentMove.equals("right")){
			delta_x = 1;
		}
		
		//if it sees a wall and not a stone, change direction BUT Does not Move yet (only moves in the next step)
		if((World.isBlocked(getX()+delta_x, getY()))&&(!World.pushableObject(getX()+delta_x, getY()))){
			if(currentMove.equals("left")){
				currentMove = "right";
			}
			else if (currentMove.equals("right")){
				currentMove = "left";
			}
		}
		
		if(!World.isBlocked(getX()+delta_x, getY())){
			move(currentMove);
		}
		
		else if (World.isBlocked(getX()+2*delta_x, getY())){
			if(currentMove.equals("left")){
				currentMove = "right";
			}
			else if (currentMove.equals("right")){
				currentMove = "left";
			}
		}
		
		else if(World.pushableObject(getX()+delta_x, getY())){
			move(currentMove);
			}
		
		}
	
	if(App.levelNumb==10){
		if((Bullet.bulletx==getX())&&(Bullet.bullety==getY())){
			World.createExplosion("explosion",getX(),getY());
			World.destroyObjectReal(this);
				}
			}
	}
	/**
	 * Standard way of moving based on movable sprites 
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
		
		//checking if the moved coordinate is in bounds or not into a blocked object
		if (!World.isBlocked(getX() + delta_x, getY() + delta_y)) {
			setX(getX() + delta_x);
			setY(getY() + delta_y);
		}
		else if (World.pushableObject(getX() + delta_x, getY()+ delta_y)){
			//if the following tile is not blocked move into the destination
			if (!World.isBlocked(getX() + 2*delta_x, getY() + 2*delta_y)){
				setX(getX() + delta_x);
				setY(getY() + delta_y);
				
				if(World.isSpriteAtLocation(getX(), getY(),Stone.class)){
					Stone pushingItem ;
					pushingItem = World.getSpriteOfType("stone", getX() , getY(),  Stone.class);
					pushingItem.push(getX(), getY(), getCurrentMove());
					}
				else if(World.isSpriteAtLocation(getX(), getY(),Ice.class)){
					Ice pushingItem ;
					pushingItem = World.getSpriteOfType("ice", getX() , getY(),  Ice.class);
					pushingItem.push(getX(), getY(), getCurrentMove());
					}
				else if(World.isSpriteAtLocation(getX(), getY(),TNT.class)){
					TNT pushingItem ;
					pushingItem = World.getSpriteOfType("tnt", getX() , getY(), TNT.class);
					pushingItem.push(getX(), getY(), getCurrentMove());
					}
				}
		}
	}
	
	public String toString(){
		return "Rougue Position is " + getX() + "," + getY();
	}
}


