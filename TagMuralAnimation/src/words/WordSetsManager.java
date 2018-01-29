package words;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.PApplet;
import processing.data.Table;

public class WordSetsManager {
	private static final String transport = "Mississippi River\nCrescent City\nstreetcar\nrailroads\nhighways\nwaterways\ncommerce\nport\ncotton\ntobacco\nmolasses\nsugar\nslave trade\nroadways\nautomobile\nCarrolton Railroad Company\nRoyal Street\nBourbon Street\nCanal Street\nRampart Street\nriverfront\nNOPSI\nsteamboats";
	private static final String arts = "painters\ncanvases\ntextiles\njewelry\nartisans\ndecorative arts\nfurnishings\narchitecture\nPaul Morphy\nchess\nwriter\nperformer\nbohemian\nArts and Crafts Club\nSherwood Anderson\nWilliam Faulkner\nTennessee Williams\nJon Webb\nLoujon Press\npublishing\nliterary\nThe Green Shutter\nmodernism\npatron\nsilversmith\nmolding\nSeignouret\nRudolph T. Lux\nporcelain\nenamel\ngilding\ncrasftsmanship\nEuropean Styles\nCarribean\ntownhouses\ncreole cottages\nbrick mansions\nvernacular\nbalconies\nneoclassical\nfilmmakers";
	private static final String music = "ceremonial\ndrumming\nparading\nmilitary bands\nrhythm\nspectacle\ntradition\nAfricans\nCongo Square\njazz\nR&B\nrock & roll\nCosimo Matassa\nJ&M Recording Studio\nFats Domino\nIrma Thomas\nDr. John\nAllen Toussaint\nOpera\nThreatre District\nmusic shops\ncomposers\nLouis Moreau Gottschalk\nErnest Guiraud\nLouis Varney\nEdmond Dédé\nDevriès\nSociété Philharmonique\nGerman singing societies\nFrench Opera House\nBunk Johnson\nGeorge Lewis\nPercy Humphrey\nBill Russell\nLarry Borenstein\nSweet Emma Barret\nPreservation Hall\nRoy Brown\nRay Charles\nLittle Richard\nDave Bartholomew\nGuitar Slim\nrecording";
	private static final String populations = "Plaquemine\nNatchez\nnative Americans\ncolonial\nSenegambian\nenslaved\nAfricans\nSpanish\nCanary Islanders\nAcadians\nCajuns\nAnglo\nHatian revolution\nfaubourgs\ndiversity\nfree people of color\nport\ncreole\nGerman\nIrish\nSicilian\nEastern European\nChinese\nworking class\nLGBTQ";
	
	private static HashMap<String, WordSet> word_sets;
	private static ArrayList<String> keys;
	
	private static String current_key;
	private static int current_key_index = 0;
	private static WordSet current_words;
	
	private static Table table;
	
	public static void init(PApplet parent) {

		table = parent.loadTable("data/nola.csv");
	  
		word_sets = new HashMap<String, WordSet>();
		keys = new ArrayList<String>();
		
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
