package project2;

import org.newdawn.slick.Input;

/**
 * Controls the movement and behaviour of the player 
 * @author Matthew
 *
 */

public class Caro extends Sprite implements Movable  {
	
	private  static float caroX;
	private static  float caroY;
	private  String currentMove = "unknown";
	private MoveHistory caroMovesHist = new MoveHistory();

	public Caro(String image_src, float x, float y){
		super(image_src, x, y);
	}
	
	/**
	 * Update movement of player 
	 */
	public void update(Input input, int delta) {
		
			String dir = Player.getCurrentMove();
			setDirection(dir);
						
			//if the size of the player coordinates are 0, add the first coordinate in
			if((caroMovesHist.getSizeX()==0)||(caroMovesHist.getSizeY()==0)){
				caroMovesHist.addPostition(getX(), getY());
			}
			if(Player.isPlayerMoving()){
			if(dir.equals("up")){
				currentMove = "down";
				caroMovesHist.addPostition(getX(), getY()+1);
			}
			else if(dir.equals("down")){
				currentMove = "up";
				caroMovesHist.addPostition(getX(), getY()-1);
			}
			else if(dir.equals("right")){
				currentMove = "left";
				caroMovesHist.addPostition(getX()-1, getY());
			}
			else if(dir.equals("left")){
				currentMove = "right";
				caroMovesHist.addPostition(getX()+1, getY());
				}
			move(currentMove);
			}
			
			
			caroX = getX();
			caroY = getY();
	}


	/**
	 * Move method inspired from project 1 sample solution 
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
		
		//move player when nothing is blocking
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
	
	/**
	 * undo method for the player 
	 */
	public void undo(){
		
		if((caroMovesHist.getSizeX()>=1)||(caroMovesHist.getSizeY()>=1)){
			caroMovesHist.removePosition();
			
			float caroNewX = caroMovesHist.getxPos().get(caroMovesHist.getSizeX()-1);
			float caroNewY = caroMovesHist.getyPos().get(caroMovesHist.getSizeY()-1);
			
			if(!World.isBlocked(caroNewX, caroNewY)){
				setX(caroMovesHist.getxPos().get(caroMovesHist.getSizeX()-1));
				setY(caroMovesHist.getyPos().get(caroMovesHist.getSizeY()-1));
			}
			
		}
	}
	
	public String getCurrentMove(){
		return currentMove;
	}

	public static float getcaroY() {
		return caroY;
	}
	
	public static float getcaroX(){
		return caroX;
	}

	public void setCurrentMove(String currentMove) {
		this.currentMove = currentMove;
	}

}

	


	

