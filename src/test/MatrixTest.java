package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.BooleanMatrix;

public class MatrixTest {


	@Test
	public void testGetValue() {
		BooleanMatrix matrix = new BooleanMatrix(4,7);
		matrix.fillMatrix(false);
		matrix.setValue(3, 0, true);
		matrix.setValue(1, 4, true);
		matrix.setValue(0, 6, true);
		
		assertTrue(matrix.getValue(3, 0));
		assertTrue(matrix.getValue(1, 4));
		assertTrue(matrix.getValue(0, 6));
		assertFalse(matrix.getValue(0, 0));
		assertFalse(matrix.getValue(3, 6));
		assertFalse(matrix.getValue(2, 3));
	}
	
	@Test
	public void testGetMatrix() {
		BooleanMatrix matrix = new BooleanMatrix(4,7);
		matrix.fillMatrix(false);
		matrix.setValue(3, 0, true);
		matrix.setValue(1, 4, true);
		matrix.setValue(0, 6, true);
		matrix.setValue(2, 0, true);
		matrix.setValue(1, 5, true);
		matrix.setValue(0, 2, true);
		
		boolean[][] expected = {
				{false, false, true, true},
				{false, false, false, false},
				{true, false, false, false},
				{false, false, false, false},
				{false, true, false, false},
				{false, true, false, false},
				{true, false, false, false}
		};
		
		assertArrayEquals(expected, matrix.getMatrix());
	}

}
