package uk.co.geekonabicycle.indexing.trie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

public class TrieNodeTest {

	@Test
	public void isRoot() {
		TrieNode<Integer> rootNode = new TrieNode<Integer>();
		assertTrue(rootNode.isRoot());
	}

	@Test
	public void keyConstructor() {
		TrieNode<Integer> node = new TrieNode<Integer>("test");
		assertFalse(node.isRoot());
		assertEquals("test", node.getKey());
		assertEquals(0, node.getItems().size());
	}

	@Test
	public void keyAndItemConstructor() {
		TrieNode<Integer> node = new TrieNode<Integer>("test", 1);
		assertFalse(node.isRoot());
		assertEquals("test", node.getKey());
		assertEquals(1, node.getItems().size());
		assertTrue(node.getItems().contains(1));
	}

	@Test
	public void addItemToCurrentNode() throws IOException {
		TrieNode<Integer> node = new TrieNode<Integer>("hippo");

		assertEquals("hippo", node.getKey());
		assertEquals(0, node.getItems().size());

		node.addItem("hippo", 10);

		assertEquals(1, node.getItems().size());
		assertTrue(node.getItems().contains(10));
	}

	@Test
	public void addDuplicateItemToCurrentNode() throws IOException {
		TrieNode<Integer> node = new TrieNode<Integer>("hippo");

		assertEquals("hippo", node.getKey());
		assertEquals(0, node.getItems().size());

		node.addItem("hippo", 10);

		assertEquals(1, node.getItems().size());
		assertTrue(node.getItems().contains(10));

		node.addItem("hippo", 10);
		assertEquals(1, node.getItems().size());
	}

	@Test
	public void addItemToChildNode() throws IOException {
		TrieNode<Integer> node = new TrieNode<Integer>("car");
		TrieNode<Integer> childNode = new TrieNode<Integer>("cart");
		node.addChild('t', childNode);

		assertEquals(childNode, node.getChild('t'));
		assertEquals(0, node.getItems().size());
		assertEquals(0, childNode.getItems().size());

		node.addItem("cart", 10);

		assertEquals(0, node.getItems().size());
		assertTrue(childNode.getItems().contains(10));
	}

	@Test
	public void createChildNodeAndAddItemToChildNode() throws IOException {
		TrieNode<Integer> node = new TrieNode<Integer>("car");

		assertEquals(0, node.getItems().size());
		assertNull(node.getChild('t'));

		node.addItem("cart", 10);

		TrieNode<Integer> childNode = node.getChild('t');

		assertEquals("cart", childNode.getKey());
		assertEquals(0, node.getItems().size());
		assertTrue(childNode.getItems().contains(10));
	}

	@Test
	public void addItemAndCreateMultipleChildNodes() throws IOException {
		TrieNode<Integer> node = new TrieNode<Integer>("bot");

		assertEquals(0, node.getItems().size());
		assertNull(node.getChild('t'));

		node.addItem("bottle", 100);

		TrieNode<Integer> tNode = node.getChild('t');
		TrieNode<Integer> lNode = tNode.getChild('l');
		TrieNode<Integer> eNode = lNode.getChild('e');

		assertEquals("bottle", eNode.getKey());
		assertEquals(0, node.getItems().size());
		assertEquals(0, tNode.getItems().size());
		assertEquals(0, lNode.getItems().size());
		assertTrue(eNode.getItems().contains(100));
	}
	
	@Test
	public void addMultipleItemsToRootNode() throws IOException {
		TrieNode<Integer> rootNode = new TrieNode<Integer>();
		rootNode.addItem("car", 50);
		rootNode.addItem("bot", 10);
		
		TrieNode<Integer> cNode = rootNode.getChild('c');
		TrieNode<Integer> aNode = cNode.getChild('a');
		TrieNode<Integer> rNode = aNode.getChild('r');
		assertTrue(rNode.getItems().contains(50));
		
		TrieNode<Integer> bNode = rootNode.getChild('b');
		TrieNode<Integer> oNode = bNode.getChild('o');
		TrieNode<Integer> tNode = oNode.getChild('t');
		assertTrue(tNode.getItems().contains(10));
	}
	
	@Test (expected = IOException.class)
	public void addItemToWrongNode() throws IOException {
		TrieNode<Integer> node = new TrieNode<Integer>("bot");
		node.addItem("cart", 100);
	}
	
	@Test
	public void findNoItems()  {
		TrieNode<Integer> node = new TrieNode<Integer>("bot");
		Collection<Integer> foundItems = node.findItems("bot");
		assertEquals(0, foundItems.size());
		foundItems = node.findItems("cart");
		assertEquals(0, foundItems.size());
	}
	
	@Test
	public void findItems() throws IOException  {
		TrieNode<Integer> node = new TrieNode<Integer>("bot", 10);
		
		Collection<Integer> foundItems = node.findItems("bot");
		assertTrue(foundItems.contains(10));
		assertEquals(1, foundItems.size());
		
		node.addItem("bot", 20);
		node.addItem("bot", 30);

	    foundItems = node.findItems("bot");
		assertTrue(foundItems.contains(10));
		assertTrue(foundItems.contains(20));
		assertTrue(foundItems.contains(30));
		assertEquals(3, foundItems.size());
	}
	
	@Test
	public void findItemsComplexTree() throws IOException {
		TrieNode<Integer> rootNode = new TrieNode<Integer>();
		
		rootNode.addItem("car", 10);
		rootNode.addItem("cart", 11);
		rootNode.addItem("cartoon", 12);
		
		rootNode.addItem("bot", 20);
		rootNode.addItem("bottle", 21);
		rootNode.addItem("bottle", 22);
		rootNode.addItem("bottleopener", 23);
		
		Collection<Integer>  foundItems = rootNode.findItems("cartoon");
		assertTrue(foundItems.contains(12));
		assertEquals(1, foundItems.size());
		
		foundItems = rootNode.findItems("bottle");
		assertTrue(foundItems.contains(21));
		assertTrue(foundItems.contains(22));
		assertEquals(2, foundItems.size());
		
		foundItems = rootNode.findItems("bottleopener");
		assertTrue(foundItems.contains(23));
		assertEquals(1, foundItems.size());
	}
	
	@Test
	public void removeNoItems() {
		TrieNode<Integer> rootNode = new TrieNode<Integer>();
		Collection<Integer> removedItems = rootNode.removeItems("bot");
		assertEquals(0, removedItems.size());
	}
	
	@Test
	public void removeItems() throws IOException {
		TrieNode<Integer> rootNode = new TrieNode<Integer>();
		rootNode.addItem("bottle", 21);
		rootNode.addItem("bottle", 22);
		
		Collection<Integer> foundItems = rootNode.findItems("bottle");
		assertEquals(2, foundItems.size());
		
		Collection<Integer> removedItems = rootNode.removeItems("bottle");
		
		assertEquals(2, removedItems.size());
		assertTrue(removedItems.contains(21));
		assertTrue(removedItems.contains(22));
		
		foundItems = rootNode.findItems("bottle");
		assertEquals(0, foundItems.size());
	}

	@Test
	public void rootParentOf() {
		TrieNode<Integer> rootNode = new TrieNode<Integer>();
		assertTrue(rootNode.parentOf("something"));
		assertTrue(rootNode.parentOf("another"));
	}

	@Test
	public void nodeParentOf() {
		TrieNode<Integer> rootNode = new TrieNode<Integer>("car");
		assertFalse(rootNode.parentOf("car"));
		assertFalse(rootNode.parentOf("ca"));
		assertFalse(rootNode.parentOf("something"));
		assertTrue(rootNode.parentOf("cart"));
	}

}
