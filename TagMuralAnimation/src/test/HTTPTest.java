package test;

import http.requests.GetRequest;
import processing.core.PApplet;

public class HTTPTest extends PApplet {

	public void settings() {
		size(400, 400);
	}
	
	public void setup() {
		GetRequest get = new GetRequest("http://138.197.115.126:3000/api/w");
		get.send();
		println(get.getContent());
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.HTTPTest"); 
	}
}
