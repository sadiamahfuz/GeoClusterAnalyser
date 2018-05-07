package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import main.OccupiedFileReader;


public class OccupiedFileReaderTest {

	@Test
	public void testreadOccupiedGeoStatsNoErrors() throws IOException {
		OccupiedFileReader reader = new OccupiedFileReader("src/Files/example.csv", 27);
		
		Map<Integer, String> actual = reader.readOccupiedGeoStats();		
		
		Map<Integer, String> expected = new TreeMap<Integer, String>();
		
		expected.put(4, "Tom, 2010-10-10");
		expected.put(5, "Katie, 2010-08-24");
		expected.put(6, "Nicole, 2011-01-09");
		expected.put(11, "Mel, 2011-01-01");
		expected.put(13, "Matt, 2010-10-14");
		expected.put(15, "Mel, 2011-01-01");
		expected.put(17, "Patrick, 2011-03-10");
		expected.put(21, "Catherine, 2011-02-25");
		expected.put(22, "Michael, 2011-02-25");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testreadOccupiedGeoStatsErrors() throws IOException {
		OccupiedFileReader reader = new OccupiedFileReader("src/Files/exampleWithErrors.csv", 27);
		
		Map<Integer, String> actual = reader.readOccupiedGeoStats();		
		
		Map<Integer, String> expected = new TreeMap<Integer, String>();
		
		expected.put(4, "Tom, 2010-10-10");
		expected.put(5, "Katie, 2010-08-24");
		expected.put(11, "Mel, 2011-01-01");
		expected.put(21, "Catherine, 2011-02-25");
		expected.put(22, "Michael, 2011-02-25");
		
		assertEquals(expected, actual);
	}
	
}
