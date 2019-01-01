package project2;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Bullet {
	
	private int time = 0;
	private float x;
	private float y;
	private String currentDir = "unknown";
	private Image currentImage = null;
	private float gameWidth = App.TILE_SIZE*Loader.getMap_Width();
	private float gameHeight = App.TILE_SIZE*Loader.getMap_Height();
	public static float bulletx;
	public static float bullety;
	
	public Bullet(String image_src, float x, float y,String direction) {
		try {
			currentImage = stringToImage(image_src);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.x=x;
		this.y=y;
		currentDir = direction;
	}
	
	public void update(Input input, int delta){
		bulletx=x;
		bullety=y;
		time+=delta;
		float delta_x = 0;
		float delta_y = 0;
		if(time>=60){
			if(currentDir.equals("up")){
				y-=1;
				delta_y=-1;
			}
			else if(currentDir.equals("down")){
				y+=1;
				delta_y=1;
			}
			else if(currentDir.equals("left")){
				x-=1;
				delta_x=-1;
			}
			else if(currentDir.equals("right")){
				x+=1;
				delta_x=1;
			}
			else {
				y-=1;
			}
			time = 0;
		}
		if(World.isBlocked(x, y)){
			World.destroyBullet(this);
			}
		else if(World.isEnemy(x-delta_x, y-delta_y)){
			World.destroyBullet(this);
			}
		else if ((World.restartState)||(World.changedLevel)){
			World.destroyBullet(this);
			}
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
	


