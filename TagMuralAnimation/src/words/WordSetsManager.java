package words;

import java.util.ArrayList;
import java.util.HashMap;

import http.requests.GetRequest;
import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.data.Table;

public class WordSetsManager {

	private static String[] nodes = {"0x0001", "0x0002"};
	private static String url = "http://138.197.115.126:3000/api/w";
	
	private static HashMap<String, WordSet> word_sets;
	private static ArrayList<String> keys;
	
	private static String current_key;
	private static int current_key_index = 0;
	private static WordSet current_words;
	
	
	public static void init(PApplet parent) {
		word_sets = new HashMap<String, WordSet>();
		keys = new ArrayList<String>();
		try {
			GetRequest get = new GetRequest(url);
			get.send();
			parseJSON(get, parent);
			System.out.println("Got words from server");
		} catch (Exception e) {
			System.out.println("Can't connect to server. Loading local stuff...");
			loadLocalWords(parent);
		}
	}
	
	private static void parseJSON(GetRequest get, PApplet parent) {
		JSONObject json = parent.parseJSONObject(get.getContent());
		for (int i = 0; i < nodes.length; i++) {
			String key = nodes[i];
			JSONObject nodeData = (JSONObject) json.get(key);
			JSONArray words = nodeData.getJSONArray("words");
			
			WordSet ws = new WordSet(parent);
			
			for (int k = 0; k < words.size(); k++) {
				ws.addWord(words.getString(k));
			}
			
			word_sets.put(key, ws);
			keys.add(key);
			
			current_words = ws;
			current_key_index = i;
		}
	}
	

	private static void loadLocalWords(PApplet parent) {
	
		Table table = parent.loadTable("data/nola.csv");
	  
		WordSet ws;
		String key;
		int row;
		for (row = 0; row < table.getRowCount(); row++) { 
			
			key = table.getString(row, 0);
			ws = new WordSet(parent);
			for (int col = 1; col < table.getColumnCount(); col++) {
				String word = table.getString(row, col);
				if (word != null)
					ws.addWord(word);
			}
			word_sets.put(key, ws);
			keys.add(key);
			
			current_words = ws;
			current_key_index = row ;
		} 
	}
	
	public static WordSet getCurrentWordSet() {
		return current_words;
	}
	
	public static Word getRandomWord() {
		return current_words.getRandomWord();
	}
	
	public static void switchWordSet() {
		current_key_index++; 
		current_key_index%=keys.size();
		current_key = keys.get(current_key_index);
		current_words = word_sets.get(current_key);
		System.out.println("Switching to words about: " + current_key);
	}
	
	public static void switchWordSet(String key) {
		WordSet ws = word_sets.get(key); 
		if (ws != null) {
			current_words = ws;
			current_key = key;
		}
	}
	
	public static String getCurrentKey() {
		return current_key;
	}
	
	public static WordSet getWordSet(String key) {
		return word_sets.get(key);
	}
}