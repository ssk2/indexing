package uk.co.geekonabicycle.indexing.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import uk.co.geekonabicycle.indexing.types.IndexKeyValue;

public class TrieManager<T extends IndexKeyValue> {

	private TrieNode<T> rootNode = new TrieNode<T>();
	private final static String DELIMITER = "_";

	public TrieManager(Iterable<T> index) {
		buildTokenizedTrie(index);
	}

	private void buildTokenizedTrie(Iterable<T> index) {
		for (T item : index) {
			String key = item.getKey();

			rootNode.addItem(key, item);

			for (String keyWord : key.split(DELIMITER)) {
				rootNode.addItem(keyWord, item);
			}
		}
	}

	public List<T> getItems(String stem, int items) {
		List<T> results = new ArrayList<T>();
		PriorityQueue<T> foundChildren = new PriorityQueue<T>(items,
				Collections.reverseOrder());

		foundChildren.addAll(rootNode.findItems(stem));

		while (!foundChildren.isEmpty() && items > 0) {
			results.add(foundChildren.poll());
			items--;
		}

		return results;
	}

	/*
	 * Members for unit testing
	 */

	TrieNode<T> getRootNode() {
		return this.rootNode;
	}
}
