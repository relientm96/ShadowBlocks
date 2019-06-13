package project2;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Load all the data for positions and image names to render them from csv file 
 * @author Matthew
 *
 */

public class Loader {	
	
	// these are the dimensions needed for renderring 
	private static int map_Width;
	private static int map_Height;
	
	public static int getMap_Width() {
		return map_Width;
	}

	public static void setMap_Width(int map_Width) {
		Loader.map_Width = map_Width;
	}

	public static int getMap_Height() {
		return map_Height;
	}

	public static void setMap_Height(int map_Height) {
		Loader.map_Height = map_Height;
	}

	/**(Method inspired from Project 1 sample solution 
	 * A factory design method to pump out different objects from the read data 
	 * @param name, string name of the object created 
	 * @param x, x position
	 * @param y, y position 
	 * @return the Sprite object using polymorphism 
	 */
	private static Sprite createObject(String name, float x, float y) {
		switch (name) {
			case "wall":
				return new Wall(name,x, y);
			case "floor":
				return new Floor(name,x, y);
			case "stone":
				return new Stone(name,x, y);
			case "target":
				return new Target(name,x, y);
			case "player":
				return new Player(name,x, y);
			case "cracked":
				return new CrackedWall(name,x,y);
			case "door":
				return new Door(name,x,y);
			case "ice":
				return new Ice(name,x,y);
			case "mage":
				return new Mage(name,x,y);
			case "rogue":
				return new Rogue(name,x,y);
			case "skeleton":
				return new Skeleton(name,x,y);
			case "switch":
				return new Switch(name,x,y);
			case "tnt":
				return new TNT(name,x,y);
			case "caro":
				return new Caro(name,x,y);
			case "trump":
				return new Trump(name,x,y);
			case "papa":
				return new Papa(name,x,y);
			case "pizza":
				return new Pizza(name,x,y);
			case "tim":
				return new Tim(name,x,y);
			case "kay":
				return new Kay(name,x,y);
			case "switch2":
				return new Switch2(name,x,y);
			case "door2":
				return new Door2(name,x,y);
			case "switch3":
				return new Switch3(name,x,y);
			case "door3":
				return new Door3(name,x,y);
			case "penguin":
				return new Penguin(name,x,y);
			case "wizzard":
				return new Wizzard(name,x,y);
			case "sheep":
				return new Sheep(name,x,y);
				
		}
		return null;
	}
	
	/**
	 * A static method to read and load data of sprites 
	 * @param filename, name of the csv file being read 
	 * @return Sprites, an array list of sprites loaded 
	 */
	//method to load the files and read the data
	public static ArrayList<Sprite> loadSprites(String filename){	
		ArrayList<Sprite> sprites = new ArrayList<>();
		File file = new File(filename); //reading the appropriate file
	
		try {
			Scanner scanner = new Scanner (file);

			//reading the height and width of the map
			String line0 = scanner.next();
			String [] data0 = line0.split(",");
			setMap_Width((Integer.parseInt(data0[0])));
			setMap_Height((Integer.parseInt(data0[1])));
			
	        scanner.nextLine(); //go to the next line containing sprite information
	        
			while(scanner.hasNext()){ //see if there is input to be read from CSV file
				
				String line = scanner.next(); // reading the whole line
				String[] data = line.split(","); //Separate data between commas
				//now read the values into the array
				String type = data[0];
				float tile_x = Float.parseFloat(data[1]);
				float tile_y = Float.parseFloat(data[2]);	
				sprites.add(createObject(type,tile_x,tile_y));
			}
			scanner.close(); //closing scanner after done reading items
			//get the number of Sprite by using the j counter
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//once read, return the sprite array
		return sprites;
	}

}
