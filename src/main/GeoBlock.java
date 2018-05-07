package main;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.TreeSet;

public class GeoBlock {

	private int width;
	private int height;
	private Set<Integer> occupiedGeoIds;
	private BooleanMatrix geoBlockOccupancyArray;
	
	/**
	 * Construct a new GeoBlock
	 * @param width
	 * @param height
	 * @param occupiedGeoIds - set of Geo IDs which are occupied
	 */
	public GeoBlock(int width, int height, Set<Integer> occupiedGeoIds) {
		this.width = width;
		this.height = height;
		this.occupiedGeoIds = occupiedGeoIds;
		this.geoBlockOccupancyArray = new BooleanMatrix(width,height);
		this.populateGeoBlockOccupancyArray();	
	}
	
	/**
	 * Calculates the 2d array index of a Geo based on its ID
	 * @param GeoId
	 * @return index
	 */
	public int[] getGeoIndex(int GeoId) throws InvalidGeoIDException {
		
		if (GeoId < 0 || GeoId >= width * height) {
			throw new InvalidGeoIDException();
		}
		
		int x = (GeoId % this.width);
		int y = this.height - (GeoId / this.width) - 1;
		
		int[] result = {x, y};
		return result;
	}
	
	/**
	 * Returns the BooleanMatrix indicating which Geos are occupied within this GeoBlock
	 */
	public BooleanMatrix getGeoBlockOccupiedRepresentation() {
		return this.geoBlockOccupancyArray;
	}
	
	
	/**
	 * Returns a set of neighbouring occupied Geo IDs for a given Geo
	 * @param geoId
	 * @return neighbours
	 */
	public Set<Integer> getNeighbouringOccupiedIds(int geoId) {
		Set<Integer> neighbours = new TreeSet<Integer>();
		
		try {
			int[] index = this.getGeoIndex(geoId);
			if(index[0] > 0) {
				if (this.geoBlockOccupancyArray.getValue(index[0]-1, index[1])) {
					neighbours.add(geoId - 1);
				}
			}
			if (index[0] < this.width - 1) {
				if (this.geoBlockOccupancyArray.getValue(index[0]+1, index[1])) {
					neighbours.add(geoId + 1);
				}
			}
			if(index[1] > 0) {
				if (this.geoBlockOccupancyArray.getValue(index[0], index[1]-1)) {
					neighbours.add(geoId + this.width);
				}
			}
			if (index[1] < this.height - 1) {
				if (this.geoBlockOccupancyArray.getValue(index[0], index[1]+1)) {
					neighbours.add(geoId - this.width);
				}
			}
				
		} catch (InvalidGeoIDException e) {
			System.err.println("Oops! Invalid Geo ID " + geoId + "! Cannot determine neighbours.");
		}
		
		return neighbours;
	}
	
	
	/**
	 * Returns the maximum cluster for this Geo Block
	 */
	public Set<Integer> getMaxGeoCluster() {
		
		BooleanMatrix visited = new BooleanMatrix(this.width, this.height);
		visited.fillMatrix(false);
		
		Set<Integer> maxCluster = new TreeSet<Integer>();
		Set<Integer> currentCluster = new TreeSet<Integer>();
		
		for (int occupiedId: this.occupiedGeoIds) {
			int index[];
			try {
				index = this.getGeoIndex(occupiedId);
			
				if (!visited.getValue(index[0],index[1])) {
					// new cluster found
					currentCluster = this.getCluster(occupiedId, visited);
					if (currentCluster.size() > maxCluster.size()) {
						maxCluster = new TreeSet<Integer>(currentCluster);
					}
					else if (currentCluster.size() == maxCluster.size()) {
						if (this.getSetSum(currentCluster) > this.getSetSum(maxCluster)) {
							maxCluster = new TreeSet<Integer>(currentCluster);
						}
					}
				}
			
			} catch (InvalidGeoIDException e) {
				System.err.println("Oops Invalid Geo ID " + occupiedId + "! Ignoring ...");
			}
		}
		
		return maxCluster;
	}
	
	/**
	 * Uses DFS to return the cluster that contains a given Geo ID
	 * @param geoId
	 * @return cluster - set of Geo IDs in the cluster
	 * @throws InvalidGeoIDException
	 */
	public Set<Integer> getCluster(int geoId, BooleanMatrix visited) throws InvalidGeoIDException {
		
		Set<Integer> cluster = new TreeSet<Integer>();
		int[] index = this.getGeoIndex(geoId);
		
		if(!this.geoBlockOccupancyArray.getValue(index[0], index[1])) {
			return cluster;
		}
		
		Deque<Integer> stack = new ArrayDeque<Integer>();
		stack.add(geoId);
		
		visited.setValue(index[0], index[1], true);
		
		while(!stack.isEmpty()) {
			int id = stack.pop();
			cluster.add(id);
			Set<Integer> neighbours = this.getNeighbouringOccupiedIds(id);
			for(int neighboursGeoId: neighbours) {
				index = this.getGeoIndex(neighboursGeoId);
				if (!visited.getValue(index[0], index[1])) {
					stack.add(neighboursGeoId);
					visited.setValue(index[0], index[1], true);
				}
			}
		}
		
		return cluster;
	}
	
	
	/**
	 * Populates the BooleanMatrix indicating which Geos are occupied within this GeoBlock
	 * Private method called by the constructor
	 */
	private void populateGeoBlockOccupancyArray() {
		
		this.geoBlockOccupancyArray.fillMatrix(false);
		
		for(Integer ID: this.occupiedGeoIds) {
			int[] index;
			try {
				index = this.getGeoIndex(ID);
				this.geoBlockOccupancyArray.setValue(index[0],index[1], true);
			} catch (InvalidGeoIDException e) {
				System.err.println("Oops! Invalid Geo ID " + ID);
			}		
		}
	}
	
	/**
	 * Calculates the sum of a set of integers
	 */
	private int getSetSum(Set<Integer> set) {
		int sum = 0;
		for (int i : set) {
			sum+=i;
		}
		return sum;
	}
	
}
