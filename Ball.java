import java.awt.*;

/**
 * 2D ball which keeps track of its position and velocity
 * 
 * @author Admin
 *
 */
public class Ball extends PlaceableObject {

	// velocity in x direction
	private int vx;

	// velocity in y direction
	private int vy;

	/**
	 * Constructs a Ball object
	 */
	public Ball() {
		this.setX(50);
		this.setY(50);
		setSizeX(20);
		setSizeY(20);
		setVX(4);
		setVY(4);
		setColor(Color.LIGHT_GRAY);
	}

	/**
	 * Retrieves the X velocity
	 * 
	 * @return the velocity in the X direction
	 */
	public int getVX() {
		return this.vx;
	}

	/**
	 * Retrieves the Y velocity
	 * 
	 * @return the velocity in the Y direction
	 */
	public int getVY() {
		return this.vy;
	}

	/**
	 * Sets the X velocity
	 * 
	 * @param vx
	 *            the new velocity
	 */
	public void setVX(int vx) {
		this.vx = vx;
	}

	/**
	 * Sets the Y velocity
	 * 
	 * @param vy
	 *            the new velocity
	 */
	public void setVY(int vy) {
		this.vy = vy;
	}

	/**
	 * moves the ball based on the current velocity
	 */
	public void move() {
		// System.out.println("X: " + getX() + " Y: " + getY());
		this.setX(this.getX() + this.getVX());
		this.setY(this.getY() + this.getVY());
		// System.out.println("X: " + getX() + " Y: " + getY());
	}

	/**
	 * Creates a BallGraphic object
	 * 
	 * @return BallGraphic representing this Ball
	 */
	public BallGraphic getBallGraphic() {
		return new BallGraphic(this.getX(), this.getY(), this.getSizeX(),
				this.getSizeY(), this.getColor());
	}

	/**
	 * A BallGraphic object representing this Ball, containing only position,
	 * size, and color information
	 * 
	 * @author Brian Lorton
	 *
	 */
	public class BallGraphic {

		private int x, y, sizeX, sizeY;
		private Color color;

		public BallGraphic(int x, int y, int newSizeX, int newSizeY, Color color) {
			this.x = x;
			this.y = y;
			this.sizeX = newSizeX;
			this.sizeY = newSizeY;
			this.color = color;
		}

		/**
		 * Draws this Ball on the given Graphics object
		 * 
		 * @param g
		 *            the Graphics object to draw this ball on
		 */
		public void drawMe(Graphics g) {
			g.setColor(color);
			g.fillOval(x, y, sizeX, sizeY);
		}
	}
}