package test;

import java.util.ArrayList;

import http.requests.GetRequest;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class HTTPTest extends PApplet {

	ArrayList<String> nodes;
	
	public void settings() {
		size(400, 400);
	}
	
	public void setup() {
		nodes = new ArrayList<String>();
		nodes.add("0x0001");
		nodes.add("0x0002");
		
		try {
			GetRequest get = new GetRequest("http://138.197.115.126:3000/api/w");
			get.send();
			JSONObject json = parseJSONObject(get.getContent());
			JSONObject nodeData = (JSONObject) json.get("0x0001");
			JSONArray words = nodeData.getJSONArray("words");
			for (int i = 0; i < words.size(); i++) {
				println(words.get(i));
			}
		} catch (Exception e) {
			println("Can't connect to server. Loading local stuff...");
		}
		
		background(0);
	}
	
	
	
	public static void main(String[] args) { 
		PApplet.main("test.HTTPTest"); 
	}
}
