package project2;

/**
 * Controls the target behaviour 
 * @author Matthew
 *
 */
public class Target extends Sprite {

	public Target(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	/**
	 * For debugging, print out the positions of all the targets 
	 */
	public void printTarget(){
		System.out.println(getImageSrc()+"," + getX() + "," + getY());
	}
	
}
