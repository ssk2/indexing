package uk.co.geekonabicycle.indexing.trie;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TrieNode<T> {

	private final boolean root;
	private final String key;
	private Collection<T> items = new HashSet<T>();
	private Map<Character, TrieNode<T>> children = new HashMap<Character, TrieNode<T>>();

	public TrieNode() {
		this.root = true;
		this.key = "";
	}

	public TrieNode(String key) {
		this.root = false;
		this.key = key;
	}

	public TrieNode(String key, T item) {
		this.root = false;
		this.key = key;
		items.add(item);
	}

	public void addItem(String childKey, T childItem) throws IOException {
		if (childKey.equals(this.key)) {
			items.add(childItem);
		} else if (parentOf(childKey)) {
			Character characterKey = childKey.charAt(this.key.length());
			TrieNode<T> nextNode;
			if (children.containsKey(characterKey)) {
				nextNode = children.get(characterKey);
			} else {
				nextNode = new TrieNode<T>(childKey.substring(0,
						this.key.length() + 1));
				children.put(characterKey, nextNode);
			}
			nextNode.addItem(childKey, childItem);
		} else {
			throw new IOException("Item cannot be added to this trie.");
		}
	}

	public Collection<T> findItems(String lookupKey) {
		if (lookupKey.equals(this.key)) {
			return this.items;
		} else if (parentOf(lookupKey)) {
			Character characterKey = lookupKey.charAt(this.key.length());

			if (children.containsKey(characterKey)) {
				return children.get(characterKey).findItems(lookupKey);
			}
		}
		return Collections.emptySet();
	}
	
	public Collection<T> removeItems(String lookupKey) {
		if (lookupKey.equals(this.key)) {
			Collection<T> removedItems = this.items;
			this.items = Collections.emptySet();
			return removedItems;
		} else if (parentOf(lookupKey)) {
			Character characterKey = lookupKey.charAt(this.key.length());

			if (children.containsKey(characterKey)) {
				return children.get(characterKey).removeItems(lookupKey);
			}
		}
		return Collections.emptySet();
	}

    boolean parentOf(String childKey) {
		return (childKey.length() > this.key.length() &&  (root || childKey
				.substring(0, this.key.length()).equals(this.key)));
	}


	public String getKey() {
		return key;
	}

	public Collection<T> getItems() {
		return items;
	}

	/*
	 * Members for unit testing
	 */
	
	boolean isRoot() {
		return root;
	}
	
    void addChild(Character key, TrieNode<T> childNode) {
    	children.put(key, childNode);
    }
    
	TrieNode<T> getChild(Character key) {
		return children.get(key);
	}
}
