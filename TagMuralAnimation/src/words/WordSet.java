package words;

import java.util.ArrayList;
import java.util.Iterator;

import filling.Rectangle; 
import processing.core.PApplet;

public class WordSet {
	PApplet parent;
	
	ArrayList<Word> words;
	public int num_words;

	public WordSet(PApplet parent) {
		this.parent = parent;
		
		words = new ArrayList<Word>();  
		num_words = 0;
	}

	// @return index of random word
	public Word getRandomWord(float max_ratio) {
		int i = PApplet.floor(parent.random(num_words));
		while(words.get(i).getRatio() > max_ratio) { i = PApplet.floor(parent.random(num_words)); } 
		return words.get(i);
	}

	public Word getRandomWord() {
		int i = PApplet.floor(parent.random(num_words));
		return words.get(i);
	}
	
	// draws word at index "i", scales it to the specified width, returns bounding rectangle
//	public Rectangle drawWord(int i, float x, float y, float specified_width) {
//		Word word = words.get(i);
//		float specified_height = specified_width / word.getRatio();
//
//		parent.textSize(specified_height);
//		parent.text(word.getText(), x, y - (specified_height * 0.1f));
//
//		// bounding rectangle
//		Rectangle r = new Rectangle(x, y, specified_width, specified_height, parent);
//		return r;
//	}

	public boolean contains(String s) {
		for (Word w : words) {
			if (s.equals(w.getText()))
				return true;
		}
		return false;
	}

	public void addWord(String text) { 
		words.add(new Word(text, parent)); 
		num_words++; 
	}

	void addAllWords(String phrase) {
		String[] raw_words = PApplet.split(phrase, '\n');
		for (int i = 0; i < raw_words.length; i++) {
			String str = raw_words[i];
			if (!contains(str)) {
				addWord(str);
			}
		}
	}

	float getMinWidth(float layer_height) {
		if (words.size() == 0) {
			return 0;
		}
		if (words.size() == 1) {
			return words.get(0).getRatio() * layer_height;
		} 
		else {
			float min_ratio = words.get(0).getRatio();
			for (int i = 0; i < words.size(); i++) {
				min_ratio = PApplet.min(min_ratio, words.get(i).getRatio());
			}
			return min_ratio * layer_height;
		}

	}
	
	public String[] getTexts() {
		String[] texts = new String[words.size()];
		Iterator<Word> word_it = words.iterator();
		int i = 0;
		while(word_it.hasNext()) {
			texts[i] = word_it.next().getText();
			i++;
		}
		return texts;
	}
	
	public void print() {
		for (int i = 0; i < num_words; i++) {
			PApplet.println(words.get(i).getText() + " - width ratio " + words.get(i).getRatio());
		}
	}
}
