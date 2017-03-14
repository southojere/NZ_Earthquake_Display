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

	public earthquakes(String place, Location l,String in) {
		sLocation = place;
		loc = l;
		intesity=in;
		//kmFrom=kiloMeters;
	}
	public float getRadFromIntesity(String intensityy){
		if(intensityy.equals("unnoticeable"))return 5;
		else if(intensityy.equals("weak"))return 20;
		else if(intensityy.equals("light"))return 40;
		else if(intensityy.equals("moderate"))return 60;
		else if(intensityy.equals("strong"))return 80;
		else if(intensityy.equals("severe"))return 100;
		else return 200;
		
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
