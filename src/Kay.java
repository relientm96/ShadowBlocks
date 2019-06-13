package project2;

import org.newdawn.slick.Input;

public class Kay extends Sprite {

	public Kay(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	public void update (Input input, int delta){
		if((Tim.timX==getX())&&(Tim.timY==getY())){
			World.level9Special = true;
		}
	}

}
