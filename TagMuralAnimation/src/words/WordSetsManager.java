package words;

import java.util.HashMap;

import processing.core.PApplet;

public class WordSetsManager {
	private static final String transport = "Mississippi River\nCrescent City\nstreetcar\nrailroads\nhighways\nwaterways\ncommerceport\ncotton\ntobacco\nmolasses\nsugar\nslave trade\nroadways\nautomobile\nCarrolton Railroad Company\nRoyal Street\nBourbon Street\nCanal Street\nRampart Street\nriverfront\nNOPSI\nsteamboats";
	private static final String arts = "painters\ncanvases\ntextiles\njewelry\nartisans\ndecorative arts\nfurnishings\narchitecture\nPaul Morphy\nchess\nwriter\nperformer\nbohemian\nArts and Crafts Club\nSherwood Anderson\nWilliam Faulkner\nTennessee Williams\nJon Webb\nLoujon Press\npublishing\nliterary\nThe Green Shutter\nmodernism\npatron\nsilversmith\nmolding\nSeignouret\nRudolph T. Lux\nporcelain\nenamel\ngilding\ncrasftsmanship\nEuropean Styles\nCarribean\ntownhouses\ncreole cottages\nbrick mansions\nvernacular\nbalconies\nneoclassical\nfilmmakers";
	private static final String music = "ceremonial\ndrumming\nparading\nmilitary bands\nrhythm\nspectacle\ntradition\nAfricans\nCongo Square\njazz\nR&B\nrock & roll\nCosimo Matassa\nJ&M Recording Studio\nFats Domino\nIrma Thomas\nDr. John\nAllen Toussaint\nOpera\nThreatre District\nmusic shops\ncomposers\nLouis Moreau Gottschalk\nErnest Guiraud\nLouis Varney\nEdmond Dédé\nDevriès\nSociété Philharmonique\nGerman singing societies\nFrench Opera House\nBunk Johnson\nGeorge Lewis\nPercy Humphrey\nBill Russell\nLarry Borenstein\nSweet Emma Barret\nPreservation Hall\nRoy Brown\nRay Charles\nLittle Richard\nDave Bartholomew\nGuitar Slim\nrecording";
	private static final String populations = "Plaquemine\nNatchez\nnative Americans\ncolonial\nSenegambian\nenslaved\nAfricans\nSpanish\nCanary Islanders\nAcadians\nCajuns\nAnglo\nHatian revolution\nfaubourgs\ndiversity\nfree people of color\nport\ncreole\nGerman\nIrish\nSicilian\nEastern European\nChinese\nworking class\nLGBTQ";
	
	private static HashMap<String, WordSet> word_sets;
	
	private static String current_key;
	private static WordSet current_words;
	
	public static void init(PApplet parent) {
		word_sets = new HashMap<String, WordSet>();
		WordSet ws1 = new WordSet(parent);
		ws1.addAllWords(transport);
		word_sets.put("transport", ws1);
		
		WordSet ws2 = new WordSet(parent);
		ws2.addAllWords(arts);
		word_sets.put("arts", ws2);
		
		WordSet ws3 = new WordSet(parent);
		ws3.addAllWords(music);
		word_sets.put("music", ws3);
		
		WordSet ws4 = new WordSet(parent);
		ws4.addAllWords(populations);
		word_sets.put("populations", ws4);
		
		current_key = "transport";
		current_words = ws1;
	}
	
	public static WordSet getCurrentWordSet() {
		return current_words;
	}
	
	public static void switchWordSet(String key) {
		WordSet ws = word_sets.get(key); 
		if (ws != null) {
			current_words = ws;
			current_key = key;
			PApplet.println("ok");
		}
	}
	
	public static String getCurrentKey() {
		return current_key;
	}
	
	public static WordSet getWordSet(String key) {
		return word_sets.get(key);
	}
}
