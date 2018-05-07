package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Map;

/**
 * Reader for CSV file containing Occupied Geo information for a Geo Block
 * @author mahfuzs
 *
 */
public class OccupiedFileReader {
	
	private String filename;
	private int maxValidGeoId;
	
	/**
	 * Constructor which takes in the CSV filename
	 * 
	 */
	public OccupiedFileReader(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Constructor which takes in the CSV filename and maximum Valid Geo Id
	 */
	public OccupiedFileReader(String filename, int maxValidGeoId) {
		this.filename = filename;
		this.maxValidGeoId = maxValidGeoId;
	}
	
	/**
	 * Reads the CSV file line by line and returns a map that maps each 
	 * occupied Geo ID to the name of the occupant and the date.
	 * @throws IOException
	 */
	public Map<Integer, String> readOccupiedGeoStats() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(this.filename));
		
		Map<Integer, String> occupiedGeoStats = new TreeMap<Integer, String>();
		
		String line = null;
		int geoId = -1;
		int line_count =1;
		while ((line = br.readLine()) != null) {
			String line_contents[] = line.split(",");
			
			if (line_contents.length < 3) {
				System.err.println("Incomplete information in CSV line " + line_count + " \"" + line + "\" ... Continuing ...");
				continue;
			}
			else if (line_contents.length > 3) {
				System.err.println("Too much information in CSV line " + line_count + " \"" + line + "\" ... Continuing ...");
				continue;
			}
			
			geoId = parseInt(line_contents[0].trim());
			if (geoId < 0 || geoId > this.maxValidGeoId) {
				System.err.println("Invalid Geo ID in CSV line " + line_count + " \"" + line + "\" ... Continuing ...");
			}
			else {
				occupiedGeoStats.put(geoId, line_contents[1].trim() + ", " + line_contents[2].trim());
			}
			
			line_count++;
		}       
		
		br.close();
		return occupiedGeoStats;
	}
	
	/**
	 * Returns -1 if GeoId cannot be converted to an Integer
	 */
	private static int parseInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
}
