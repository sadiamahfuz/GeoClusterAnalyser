package main;

import java.util.Arrays;

/**
 * Encapsulates a boolean 2d array.
 * Allows each element to be accessed using (x,y) coordinates
 * (whereas JAVA 2d arrays are accessed (y,x))
 * @author mahfuzs
 *
 */
public class BooleanMatrix {

	private boolean[][] matrix;
	
	/**
	 * Constructor
	 * @param width
	 * @param height
	 */
	public BooleanMatrix(int width, int height) {
		matrix = new boolean[height][width];
	}
	
	/**
	 * Sets the value at position (x,y)
	 * @param x
	 * @param y
	 * @param value
	 */
	public void setValue(int x, int y, boolean value) {
		matrix[y][x] = value;
	}
	
	/**
	 * Returns the value at position (x,y)
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getValue(int x, int y) {
		return matrix[y][x];
	}
	
	/**
	 * Returns the 2d array
	 */
	public boolean[][] getMatrix() {
		return matrix;
	}
	
	/**
	 * Sets each element to a default value
	 * @param defaultValue
	 */
	public void fillMatrix(boolean defaultValue) {
		for(boolean row[]: matrix) {
			Arrays.fill(row, defaultValue);
		}
	}
	
	/**
	 * Returns a String representation of the 2d array
	 */
	public String toString() {
		String matrixString = "";
		
		for(boolean row[]: matrix) {
			for (boolean value: row) {
				matrixString+=value;
				matrixString+=",\t";
			}
			matrixString+="\n";
		}
		
		return matrixString;
	}
}
