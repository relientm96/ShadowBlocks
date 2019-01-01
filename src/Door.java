package project2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Door extends Sprite{
	
	public Door(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	/**
	 * Open or close the door when door can be opened or closed (from world)
	 */
	public void update(Input input, int delta){
		//if switch covered by block, open the door by changing it to a floor
		if((World.getDoorState().equals("open"))){
			super.setImageSrc("floor");
		}
		else {
			super.setImageSrc("door");
		}
	}
	
	public void render(Graphics g) throws SlickException{
		//converting the tile numbers to actual pixels 
				float xRender = getX()*App.TILE_SIZE+((App.SCREEN_WIDTH-getGameWidth())/2);
				float yRender = getY()*App.TILE_SIZE+((App.SCREEN_HEIGHT-getGameHeight())/2);
				//Render the images using the draw command
				if(World.getDoorState().equals("open")){
					stringToImage("floor").drawCentered(xRender,yRender);
					}
				else if (World.getDoorState().equals("close")){
					stringToImage("door").drawCentered(xRender, yRender);	
					}
		}
	
}
