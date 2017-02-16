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
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import processing.core.PVector;

public class myMap extends PApplet {

	private static final long serialVersionUID = 1L;

	UnfoldingMap map;
	URL geoNet;

	List<earthquakes> quakes;
	Set<String> locations;
	Map<String, List<Float>> placeToLatAnLong;

	public void setup() {
		size(600, 400);

		quakes = new ArrayList<earthquakes>();
		locations = new HashSet<String>();
		placeToLatAnLong = new HashMap<String, List<Float>>();

		// MAP Settings
		Location wellingtonLoc = new Location(-41.29f, 174.77f);
		map = new UnfoldingMap(this, new OpenStreetMapProvider());
		map.zoomAndPanTo(7, wellingtonLoc);
		MapUtils.createDefaultEventDispatcher(this, map);

		// LOADING URL
		try {
			geoNet = new URL("http://www.geonet.org.nz/quakes/all");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		addLocationsFromFile();
		try {
			readEarthquakesFromURL();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw() {
		map.setBackgroundColor(240);
		map.draw();
		displayEarthquakes();

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

		while ((inputLine = in.readLine()) != null) {
			// Removes HTML
			Document doc = Jsoup.parse(inputLine);
			String hmtlRemovedtext = doc.body().text();
			// Scans through HTML free line of text
			Scanner sc = new Scanner(hmtlRemovedtext);
			while (sc.hasNext()) {
				String thiss = sc.next();
				if (thiss.equals("of")) {
					String nextOfThiss = sc.next();
					if (placeToLatAnLong.containsKey(nextOfThiss)) {
						Location l = new Location(placeToLatAnLong.get(nextOfThiss).get(0),
								placeToLatAnLong.get(nextOfThiss).get(1));
						quakes.add(new earthquakes(nextOfThiss, l));
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
			SimplePointMarker quakeMaker = new SimplePointMarker(quake.getLoc());
			map.addMarker(quakeMaker);
			//println(quake.getsLocation() + " " + quake.getLoc());
		}
	}

}
