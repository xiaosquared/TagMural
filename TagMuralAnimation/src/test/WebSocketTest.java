package test;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import processing.core.PApplet;
import processing.data.JSONObject;
import water.WaveScene;
import words.WordSetsManager;

public class WebSocketTest extends PApplet {

	public void settings() {
		size(400, 400);
		smooth(4);
	}
	
	public void setup() {
		try {
			WebSocketClient mWs = new WebSocketClient(new URI( "ws://138.197.115.126:3333" ), new Draft_6455() )
			{
				@Override
				public void onMessage( String message ) {
					JSONObject json = parseJSONObject(message);
					println(json.getString("dev_id"));
				}

				@Override
				public void onOpen( ServerHandshake handshake ) {
					System.out.println( "opened connection" );
				}

				@Override
				public void onClose( int code, String reason, boolean remote ) {
					System.out.println( "closed connection" );
				}

				@Override
				public void onError( Exception ex ) {
					ex.printStackTrace();
				}

			};
			mWs.connect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void webSocketEvent(String msg) {
		try {
			println("received msg: " + msg);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.WebSocketTest"); 
	}
}
