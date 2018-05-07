package main;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class GeoClusterAnalyser {

	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.err.println("Oops expected 3 command line parameters.");
			printUsage();
			System.exit(0);
		}
		
		// Parse command line arguments
		int width = parseInt(args[0]);
		int height = parseInt(args[1]);
		String occupiedFileName = args[2];
		
		if (width <= 0 || height <= 0) {
			System.err.println("Oops - please double check values for width and height!");
			printUsage();
			System.exit(0);
		}
		
		try{
			
			// Read occupied Geo Ids CSV file
			OccupiedFileReader reader = new OccupiedFileReader(occupiedFileName, width * height - 1);
			Map<Integer, String> occupiedGeoStats = reader.readOccupiedGeoStats();		
			Set<Integer> occupiedGeoIdsSet = occupiedGeoStats.keySet();
			
			// Create Geo Block and determine the largest cluster
			GeoBlock geoBlock = new GeoBlock(width, height, occupiedGeoIdsSet);
			Set<Integer> maxCluster = geoBlock.getMaxGeoCluster();
			
			// Print largest cluster information
			if (maxCluster.isEmpty()) {
				System.out.println("There are no occupied Geos in this GeoBlock.");
			}
			else {
				System.out.println("The Geos in the largest cluster of occupied Geos for this GeoBlock are:");
				for (int geoId: maxCluster) {
					System.out.println(geoId + ", " + occupiedGeoStats.get(geoId));
				}
			}
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}
	
	/**
	 * Returns -1 if width and height cannot be converted to an Integer
	 * Note: Width and height should be positive integers
	 */
	public static int parseInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	/**
	 * Prints usage information
	 */
	public static void printUsage() {
		System.out.println("Usage: ");
		System.out.println("\tGeoClusterAnalyser <width> <height> <occupiedFileList.csv>");
		System.out.println("\t where <width> and <height> are positive integers (> 0)");
	}

}
