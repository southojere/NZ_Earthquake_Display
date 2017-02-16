package unfolded;

import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

public class earthquakes {
	private Location loc;
	private String sLocation;
	int kmFrom;
	String dir;// NORTH,EAST,SOUTH & WEST
	private double mag;
	private double depth;

	public earthquakes(String place, Location l) {
		sLocation = place;
		loc = l;
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

	public String toString() {
		return "Mag: " + mag + " depth: " + depth + " " + kmFrom + " Km From " + loc;
	}

}
