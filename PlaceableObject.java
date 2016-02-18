import java.awt.*;

public abstract class PlaceableObject {

	// location of upper left corner
	private int x, y;

	// size
	private int sizeX, sizeY;

	// color
	private Color color;

	/**
	 * Retrieves X component of the location of this object
	 * 
	 * @return the X value of the location of this object
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Retrieves Y component of the location
	 * 
	 * @return the Y value of the location
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Sets the X component of the location
	 * 
	 * @param newX
	 *            the new X component
	 */
	public void setX(int newX) {
		this.x = newX;
	}

	/**
	 * Sets the Y component of the location
	 * 
	 * @param newY
	 *            the new Y component
	 */
	public void setY(int newY) {
		this.y = newY;
	}

	/**
	 * Retrieves the width
	 * 
	 * @return width in pixels
	 */
	public int getSizeX() {
		return this.sizeX;
	}

	/**
	 * Retrieves the height
	 * 
	 * @return height in pixels
	 */
	public int getSizeY() {
		return this.sizeY;
	}

	/**
	 * Sets the width
	 * 
	 * @param newSize
	 *            the new width
	 */
	public void setSizeX(int newSize) {
		this.sizeX = newSize;
	}

	/**
	 * Sets the height
	 * 
	 * @param newSize
	 *            the new height
	 */
	public void setSizeY(int newSize) {
		this.sizeY = newSize;
	}

	/**
	 * Retrieves the Color
	 * 
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Sets the color
	 * 
	 * @param newColor
	 *            the new color
	 */
	public void setColor(Color newColor) {
		this.color = newColor;
	}
}