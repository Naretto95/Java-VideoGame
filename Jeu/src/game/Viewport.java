package game;

import java.io.Serializable;
/**
 * 
 * @author Corentin BRILLANT
 *
 */
public class Viewport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int maxX;
	private int maxY;
	private int width;
	private int height;
	
	public Viewport(int x,int y, int width, int height,int limX, int limY) {
		this.x=x;
		this.y=y;
		this.maxX=limX;
		this.maxY=limY;
		this.width=width;
		this.height=height;
	}
	
	public boolean move(int deltaX,int deltaY){
		if (deltaX>0) {
		if (this.getX()+deltaX<this.maxX) {
			this.setX(this.getX()+deltaX);
			this.setY(this.getY()+deltaY);
			return true;
		}
		}
		if (deltaX<0) {
			if (this.getX()+deltaX>0) {
				this.setX(this.getX()+deltaX);
				this.setY(this.getY()+deltaY);
				return true;
			}
			}
		if (deltaY>0) {
			if (this.getY()+deltaY<this.maxY) {
				this.setX(this.getX()+deltaX);
				this.setY(this.getY()+deltaY);
				return true;
			}
			}
		if (deltaY<0) {
			if (this.getY()+deltaY>0 ) {
				this.setX(this.getX()+deltaX);
				this.setY(this.getY()+deltaY);
				return true;
			}
			}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
