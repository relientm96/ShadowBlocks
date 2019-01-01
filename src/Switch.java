package project2;
import org.newdawn.slick.Input;

/**
 * Controls the state of the switch whether it has a block on it 
 */
public class Switch extends Sprite {

	public Switch(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	/**
	 * If it has a stone on it update the state of the door in world 
	 */
	
	public void update(Input input, int delta){
		
		
		if(App.levelNumb==7){
		//if  a block is on the switch, set the door status to open
			if(World.pushableObject(getX(), getY())||((Player.getPlayerX()==getX()) && (Player.getPlayerY()==getY()))){
				World.setDoorState("open");
			}
			else {
				World.setDoorState("close");
			}
		}
		
		else if (App.levelNumb == 18){
			if((Penguin.penguinX==getX())&&(Penguin.penguinY==getY())){
				World.setDoorState("open");
			}
			else {
				World.setDoorState("close");
			}
		}
		else{
			if(World.pushableObject(getX(), getY())){
				World.setDoorState("open");
			}
			else {
				World.setDoorState("close");
			}
		}
	}

}
