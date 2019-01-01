package project2;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Portal {
	
	private float x;
	private float y;
	private Image currentImage = null;
	private float gameWidth = App.TILE_SIZE*Loader.getMap_Width();
	private float gameHeight = App.TILE_SIZE*Loader.getMap_Height();
	
	public Portal(String image_src, float x, float y) {
		try {
			currentImage = stringToImage(image_src);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.x=x;
		this.y=y;
	}
	

	public void update (Input input, int delta){
		

		if(input.isKeyPressed(Input.KEY_Y)){
			if(World.portals.size()>1){
				if(World.checkPortals(Player.getPlayerX(), Player.getPlayerY())){
					World.PortalActive = true;
				}
			}
		}
		
		if(World.PortalActive){
			if((Player.getPlayerX()==x)&&(Player.getPlayerY()==y));
				Coordinate portalDest = new Coordinate();
				portalDest = World.playerTeleport(Player.getPlayerX(), Player.getPlayerY());
				Player.portalCoords = portalDest;
				}
		}
	
	public float getX() {
		return x;
	}


	public float getY() {
		return y;
	}


	public void render(Graphics g) throws SlickException{	
		//converting the tile numbers to actual pixels 
		float xRender = x*App.TILE_SIZE+((App.SCREEN_WIDTH-gameWidth)/2);
		float yRender = y*App.TILE_SIZE+((App.SCREEN_HEIGHT-gameHeight)/2);
		//Render the images using the draw command
		currentImage.drawCentered(xRender,yRender);
	}
	
	public Image stringToImage(String image_src) throws SlickException{
		//slowly find each image based on the image_src string 

		if (image_src.equals("cracked")){
			Image img = new Image("cracked_wall.png");
			return img;
		}
		else if (image_src.equals("player")){
			Image img = new Image("player_left.png");
			return img;
		}
		else {
			Image img = new Image(image_src + ".png");
			return img;
		}
	}
	
	
}
