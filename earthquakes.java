package unfolded;

import de.fhpotsdam.unfolding.geo.Location;

public class earthquakes {
	private Location loc;
	private String sLocation;
	int kmFrom;
	String dir;// NORTH,EAST,SOUTH & WEST
	private double mag;
	private double depth;
	String intesity;

	public earthquakes(String place, Location l,String in, int kiloMeters,String direction) {
		sLocation = place;
		loc = l;
		intesity=in;
		dir=direction;
		kmFrom=kiloMeters;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public String getsLocation() {
		return sLocation;
	}

	public void setsLocation(String sLocation) {
		this.sLocation = sLocation;
	}

	public double getMag() {
		return mag;
	}

	public void setMag(double mag) {
		this.mag = mag;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	public String getIntesity(){
		return this.intesity;
	}

	public String toString() {
		return  kmFrom+" km "+dir+" from "+"Location: "+sLocation +" lat and Long " + loc +" Intesity: " + intesity ;
	}

}
