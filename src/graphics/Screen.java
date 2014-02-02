package graphics;

import level.tile.Tile;

public class Screen {

	public int width;
	public int height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	
	public int xOffset, yOffset;
	
	public int[] tiles = new int [MAP_SIZE * MAP_SIZE];
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
	}
	
	public void clear() {
		for (int i=0; i<pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	/**
	 * Renders a tile to the screen (pixels). 
	 * @param xp tile position on x screen
	 * @param yp tile position on y screen
	 * @param tile
	 */
	public void renderTile(int xp, int yp, Tile tile) {
		renderTile(xp, yp, tile.sprite);
	}
	
	/**
	 * Renders a tile to the screen (pixels). 
	 * @param xp tile position on x screen
	 * @param yp tile position on y screen
	 * @param sprite
	 */
	public void renderTile(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y=0; y<sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x=0; x<sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) {
					break;
				}
				if (xa < 0) {
					// in the tutorial the mechanism is xa = 0;  
					continue; 	// if x coordinate is out of screen index -> contine (should not be visible)
				}
				int col = sprite.pixels[x+y*sprite.SIZE];
				// pink color on mac Oo
				if (col != -65281) pixels[xa + ya * width] = col;
//				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}

	public void renderPlayer(int xp, int yp, Sprite sprite, int flip) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y=0; y<32; y++) {
			int ya = y + yp;
			int ys = y;
			if (flip == 2 || flip == 3) {
				ys = 31 - y;
			}
			for (int x=0; x<32; x++) {
				int xa = x + xp;
				int xs = x;
				if (flip == 1 || flip == 3) {
					xs = 31 - x;
				}
				if (xa < -32 || xa >= width || ya < 0 || ya >= height) {
					break;
				}
				if (xa < 0) {
					// in the tutorial the mechanism is xa = 0;  
					continue; 	// if x coordinate is out of screen index -> contine (should not be visible)
				}
				int col = sprite.pixels[xs+ys*32];
				// pink color on mac Oo
				if (col != -327425) pixels[xa + ya * width] = col;
			}
		}
	}
	
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for (int y=0; y<sprite.getWidth(); y++) {
			int ya = y + yp;
			for (int x=0; x<sprite.getHeight(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
