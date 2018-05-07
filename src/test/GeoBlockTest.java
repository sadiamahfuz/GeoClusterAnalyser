package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import main.GeoBlock;
import main.InvalidGeoIDException;
import main.OccupiedFileReader;

public class GeoBlockTest {

	private GeoBlock testGeo7x4;
	private GeoBlock testGeo4x7;
	
	@Before
	public void setUp() throws Exception {
		Set<Integer> s = new HashSet<Integer>();
		
		s.add(4);
		s.add(5);
		s.add(6);
		s.add(11);
		s.add(13);
		s.add(15);
		s.add(17);
		s.add(21);
		s.add(22);
		
		testGeo7x4 = new GeoBlock(7,4,s);
		
		testGeo4x7 = new GeoBlock(4,7,s);
	}
	
	
	@Test
	public void testGetGeoIndex() throws InvalidGeoIDException {
		
		int[] index = testGeo7x4.getGeoIndex(10);
		int[] expected = {3,2};
		assertArrayEquals(expected, index);	
		
		index = testGeo7x4.getGeoIndex(6);
		expected[0] = 6;
		expected[1] = 3;
		assertArrayEquals(expected, index);	
		
		index = testGeo7x4.getGeoIndex(21);
		expected[0] = 0;
		expected[1] = 0;
		assertArrayEquals(expected, index);	
		
		index = testGeo7x4.getGeoIndex(19);
		expected[0] = 5;
		expected[1] = 1;
		assertArrayEquals(expected, index);	
		
		index = testGeo7x4.getGeoIndex(0);
		expected[0] = 0;
		expected[1] = 3;
		assertArrayEquals(expected, index);
	}
	
	@Test
	public void testGetGeoBlockOccupiedRepresentation() {
		
		boolean[][] expected = {
				{true,	true,	false,	false,	false,	false,	false},	
				{false,	true,	false,	true,	false,	false,	false},	
				{false,	false,	false,	false,	true,	false,	true},	
				{false,	false,	false,	false,	true,	true,	true}
		};
		assertArrayEquals(expected, testGeo7x4.getGeoBlockOccupiedRepresentation().getMatrix());
	}
	
	
	@Test
	public void testGetNeighbouringOccupiedIdsSides() {
		Set<Integer> expected = new TreeSet<Integer>();
		expected.add(4);
		expected.add(6);
		
		Set<Integer> actual = testGeo7x4.getNeighbouringOccupiedIds(5);
		
		assertEquals(expected, actual);
	}

	@Test
	public void testGetNeighbouringOccupiedIdsUp() {
		Set<Integer> expected = new TreeSet<Integer>();
		expected.add(22);
		
		Set<Integer> actual = testGeo7x4.getNeighbouringOccupiedIds(15);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetNeighbouringOccupiedIdsDown() {
		Set<Integer> expected = new TreeSet<Integer>();
		expected.add(15);
		expected.add(21);
		
		Set<Integer> actual = testGeo7x4.getNeighbouringOccupiedIds(22);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetNeighbouringOccupiedIdsNone() {
		
		assertEquals(0, testGeo7x4.getNeighbouringOccupiedIds(19).size());
	}
	
	
	@Test
	public void testGetMaxGeoCluster7x4() throws InvalidGeoIDException, IOException {
		
		Set<Integer> expected = new HashSet<Integer>();
		
		expected.add(4);
		expected.add(5);
		expected.add(6);
		expected.add(11);
		expected.add(13);
		
		assertEquals(expected, testGeo7x4.getMaxGeoCluster());
	}
	
	@Test
	public void testGetMaxGeoCluster4x7() throws InvalidGeoIDException, IOException {
		
		Set<Integer> expected = new HashSet<Integer>();
		
		expected.add(13);
		expected.add(17);
		expected.add(21);
		expected.add(22);
		
		assertEquals(expected, testGeo4x7.getMaxGeoCluster());
	}
	
	@Test
	public void testGetMaxGeoClusterWithInvalidIds() throws InvalidGeoIDException, IOException {
		
		OccupiedFileReader reader = new OccupiedFileReader("src/Files/exampleWithInvalidGeoID.csv", 27);
		Map<Integer, String> map = reader.readOccupiedGeoStats();
		
		GeoBlock test = new GeoBlock(7, 4, map.keySet());
		
		Set<Integer> expected = new HashSet<Integer>();
		expected.add(5);
		expected.add(6);
		
		assertEquals(expected, test.getMaxGeoCluster());
	}
	
	@Test
	public void testGetGeoCluster10000UnderOneSecond() throws InvalidGeoIDException, IOException {

		long startTime = System.currentTimeMillis();
		
		OccupiedFileReader reader = new OccupiedFileReader("src/Files/test10000.csv", 10000 * 10000 - 1);
		Map<Integer, String> contents = reader.readOccupiedGeoStats();
		
		GeoBlock block = new GeoBlock(10000, 10000, contents.keySet());
		block.getMaxGeoCluster();
		
		long endTime = System.currentTimeMillis();
		
		double totalTimeSeconds = (endTime - startTime) / 1000.0;
		
		boolean lessThanSecond = totalTimeSeconds < 1.0;

		assertTrue(lessThanSecond);
		
	}
	
}
