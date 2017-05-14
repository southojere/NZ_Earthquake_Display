package unfolded;

import java.io.*;
import java.net.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.OpenStreetMap.OpenStreetMapProvider;
import de.fhpotsdam.unfolding.ui.CompassUI;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import processing.core.PVector;

public class myMap extends PApplet {

	private static final long serialVersionUID = 1L;

	UnfoldingMap map;
	CompassUI compass;
	URL geoNet;

	List<earthquakes> quakes;
	Set<String> locations;
	Map<String, List<Float>> placeToLatAnLong;

	public void setup() {
		size(500, 400);

		quakes = new ArrayList<earthquakes>();
		locations = new HashSet<String>();
		placeToLatAnLong = new HashMap<String, List<Float>>();

		// MAP Settings
		Location wellingtonLoc = new Location(-41.29f, 174.77f);
		map = new UnfoldingMap(this, new OpenStreetMapProvider());
		map.zoomAndPanTo(7, wellingtonLoc);
		MapUtils.createDefaultEventDispatcher(this, map);

		compass = new CompassUI(this, map);

		// LOADING URL
		try {
			geoNet = new URL("http://www.geonet.org.nz/quakes/all");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			print("Error loading website");

		}

		addLocationsFromFile();
		try {
			readEarthquakesFromURL();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		displayEarthquakes();
	}

	public void draw() {
		map.setBackgroundColor(240);
		map.draw();
		compass.draw();
		drawScale();

		// Lat & Long display
		fill(0);
		Location location = map.getLocation(mouseX, mouseY);
		text(location.getLat() + ", " + location.getLon(), 0, 10);
	}

	/**
	 * This method reads the geoNet URL, it then reads through and retrieves
	 * information about recent earthquakes - Outer loop scans through Each line
	 * of HTML from the URL. - Inner loop runs through the removed HTML text and
	 * pulls out recent earthquake information. - Uses Jsoup library to scrape
	 * and parse through the HTML.
	 */
	public void readEarthquakesFromURL() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(geoNet.openStream()));
		String inputLine;

		String intensity = "";
		int km = 0;
		String dir = "";
		while ((inputLine = in.readLine()) != null) {
			// Removes HTML
			Document doc = Jsoup.parse(inputLine);
			String hmtlRemovedtext = doc.body().text();
			// Scans through HTML free line of text
			Scanner sc = new Scanner(hmtlRemovedtext);
			while (sc.hasNext()) {
				String thiss = sc.next();
				print(thiss);

				if (thiss.equals("Intensity"))
					intensity = sc.next();
				if (thiss.equals("of")) {
					String nextOfThiss = sc.next();
					if (placeToLatAnLong.containsKey(nextOfThiss)) {
						Location l = new Location(placeToLatAnLong.get(nextOfThiss).get(0),
								placeToLatAnLong.get(nextOfThiss).get(1));
						quakes.add(new earthquakes(nextOfThiss, l, intensity));
					}
				}
			}
			sc.close();
		}
		in.close();
	}

	public void addLocationsFromFile() {
		try {
			Scanner sc = new Scanner(
					new File("C://Users/Jeremy Southon/workspace/unfolded/src/unfolded/myLocations.txt"));
			while (sc.hasNext()) {
				String place = sc.next();
				float lat = Float.parseFloat(sc.next());
				float log = Float.parseFloat(sc.next());
				List<Float> latAnLong = new ArrayList<Float>();
				// Fill Location to lat an long
				latAnLong.add(lat);
				latAnLong.add(log);
				placeToLatAnLong.put(place, latAnLong);

			}
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			println("Could not find file Containing Locations and their cord");
		}

	}

	public void displayEarthquakes() {
		for (earthquakes quake : quakes) {
			SimplePointMarker quakeMarker = new SimplePointMarker(quake.getLoc());
			quakeMarker.setRadius(quake.getRadFromIntesity(quake.getIntesity()));
			// selecting appropriate color based on strength
			quakeMarker = colourBasedOnIntensity(quakeMarker, quake);
			map.addMarker(quakeMarker);
			println(quakeMarker.getScreenPosition(map));

		}
	}

	public SimplePointMarker colourBasedOnIntensity(SimplePointMarker p, earthquakes quake) {
		if (quake.getIntesity().equals("unnoticeable"))
			p.setColor(color(50, 50, 50, 100));
		if (quake.getIntesity().equals("weak"))
			p.setColor(color(131, 205, 230, 100));
		if (quake.getIntesity().equals("light"))
			p.setColor(color(36, 76, 255, 100));
		if (quake.getIntesity().equals("moderate"))
			p.setColor(color(33, 191, 99, 100));
		if (quake.getIntesity().equals("strong"))
			p.setColor(color(188, 191, 3, 100));
		if (quake.getIntesity().equals("severe"))
			p.setColor(color(240, 70, 3, 100));
		return p;
	}

	public void drawScale(){
		int y = 10;
		int x=10;
		int rad=5;
		ellipse(x,y,10,10);
		y+=rad;
		//ellipse(x,y,);
			
		
	}
}
