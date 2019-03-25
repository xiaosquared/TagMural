package global;

import java.util.LinkedList;

public interface Scene {
	public void addFromQueue(LinkedList<String> featured_word_queue);
	public void changeWordSet();
	public void run();
}
