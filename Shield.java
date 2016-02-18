import java.awt.*;

/**
 * Class representing the player-controlled shield/paddle in a brick-breaker
 * game
 * 
 * @author Brian Lorton
 *
 */
public class Shield extends PlaceableObject {

	// velocity in X direction
	private int vx;

	// width of viewable area
	private final int SCREEN_X = 786;

	// Y value of shield
	private final int SHIELD_Y = 500;

	// width of shield. the height of shield is 1/6 of this
	private final int SHIELD_SIZE = 80;

	/**
	 * Constructs a shield object
	 */
	public Shield() {
		this.setColor(Color.BLUE);
		this.setSizeX(SHIELD_SIZE);
		this.setSizeY(this.getSizeX() / 6);
		this.setY(SHIELD_Y);
		moveToCenter();
	}

	/**
	 * Centers the shield left-right on the game screen
	 */
	public void moveToCenter() {
		this.setX(SCREEN_X / 2 - (this.getSizeX() / 2));
	}

	/**
	 * Moves the shield based on it's current velocity
	 */
	public void move() {
		this.setX(this.getX() + vx);
	}

	/**
	 * Sets the velocity of the shield so it will move to the right
	 */
	public void setMovingRight() {
		vx = 5;
	}

	/**
	 * Sets the velocity of the shield so it will move to the left
	 */
	public void setMovingLeft() {
		vx = -5;
	}

	/**
	 * Sets the velocity of the shield to zero
	 */
	public void setMovingStop() {
		vx = 0;
	}

	/**
	 * Creates a ShieldGraphic representing this Shield
	 * 
	 * @return ShieldGraphic object
	 */
	public ShieldGraphic getShieldGraphic() {
		return new ShieldGraphic(this.getX(), this.getY(), this.getSizeX(),
				this.getSizeY(), this.getColor());
	}

	/**
	 * A graphical representation of this shield object containing only
	 * position, size, and color
	 * 
	 * @author Brian Lorton
	 *
	 */
	public class ShieldGraphic {

		private int x, y, sizeX, sizeY;
		private Color color;

		private ShieldGraphic(int x, int y, int sizeX, int sizeY, Color color) {
			this.x = x;
			this.y = y;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			this.color = color;
		}

		/**
		 * Draws this Shield on the given Graphics object
		 * 
		 * @param g
		 *            the Graphics object to draw this shield on
		 */
		public void drawMe(Graphics g) {
			g.setColor(color);
			g.fillRect(x, y, sizeX, sizeY);
		}
	}
}
