import java.awt.*;

/**
 * class representing a brick in a brick-breaker game
 * @author Admin
 *
 */
public class Brick extends PlaceableObject{
	
	// point value of brick
	private int points;
	
	/**
	 * Contructs a brick object
	 * @param points number of points this brick will
	 * be worth when broken
	 */
	public Brick(int points){
		this.points = points;
		this.setSizeX(58);
		this.setSizeY(this.getSizeX() / 3);
	}
	
	/**
	 * Retrieves the point value of this brick
	 * @return int representing the point value
	 */
	public int getPoints(){
		return this.points;
	}
	
	/**
	 * Creates a BrickGraphic object
	 * @return a BrickGraphic object representing this Brick
	 */
	public BrickGraphic getBrickGraphic(){
		return new BrickGraphic(this.getX(), this.getY(), this.getSizeX(),
				this.getSizeY(), this.getColor());
	}
	
	/**
	 * A graphical representation of this brick, containing only 
	 * position, size, and color
	 * @author Brian Lorton
	 *
	 */
	public class BrickGraphic {
		private int x, y, sizeX, sizeY;
		private Color color;
		
		private BrickGraphic(int x, int y, int sizeX, int sizeY, Color color){
			this.x = x;
			this.y = y;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			this.color = color;
		}
		
		/**
		 * Draws this Brick on the given Graphics object
		 * 
		 * @param g
		 *            the Graphics object to draw this brick on
		 */
		public void drawMe(Graphics g){
			g.setColor(color);
			g.fillRect(x, y, sizeX, sizeY);
		}
	}	
}