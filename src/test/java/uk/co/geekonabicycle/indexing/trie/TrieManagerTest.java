package uk.co.geekonabicycle.indexing.trie;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Before;
import org.junit.Test;

import uk.co.geekonabicycle.indexing.types.IndexKeyValue;
import uk.co.geekonabicycle.indexing.types.ScorePair;

public class TrieManagerTest {
	private TrieManager<ScorePair> manager;
	private ScorePair revenuePair;
	private ScorePair yearlyRevenuePair;
	private ScorePair monthlyRevenuePair;

	@Before
	public void setup() {
		revenuePair = new ScorePair("revenue", 8);
		yearlyRevenuePair = new ScorePair("yearly_revenue", 9);
		monthlyRevenuePair = new ScorePair("monthly_revenue", 10);
		
		List<ScorePair> pairs = new ArrayList<ScorePair>();
		pairs.add(revenuePair);
		pairs.add(yearlyRevenuePair);
		pairs.add(monthlyRevenuePair);
		
		manager = new TrieManager<ScorePair>(pairs);
	}

	@Test
	public void buildTokenizedTrie() {
		TrieNode<ScorePair> rootNode = manager.getRootNode();
		
		assertTrue(rootNode.isRoot());
		Collection<ScorePair> foundRevenueItems = rootNode.findItems("revenue");
		assertEquals(3, foundRevenueItems.size());
		assertTrue(foundRevenueItems.contains(revenuePair));
		assertTrue(foundRevenueItems.contains(yearlyRevenuePair));
		assertTrue(foundRevenueItems.contains(monthlyRevenuePair));
		
		Collection<ScorePair> foundYearlyRevenueItems = rootNode.findItems("yearly_revenue");
		assertEquals(1, foundYearlyRevenueItems.size());
		assertTrue(foundRevenueItems.contains(yearlyRevenuePair));		
		
		Collection<ScorePair> foundMonthlyRevenueItems = rootNode.findItems("monthly_revenue");
		assertEquals(1, foundMonthlyRevenueItems.size());
		assertTrue(foundRevenueItems.contains(monthlyRevenuePair));
	}
	
	@Test
	public void getItems() {
		List<ScorePair> foundItems = manager.getItems("revenue", 10);
		
		assertEquals(3, foundItems.size());
		assertEquals(monthlyRevenuePair, foundItems.get(0));
		assertEquals(yearlyRevenuePair, foundItems.get(1));
		assertEquals(revenuePair, foundItems.get(2));
		
		foundItems = manager.getItems("yearly_revenue", 10);
		
		assertEquals(1, foundItems.size());
		assertEquals(yearlyRevenuePair, foundItems.get(0));
		
		foundItems = manager.getItems("monthly_revenue", 10);
		
		assertEquals(1, foundItems.size());
		assertEquals(monthlyRevenuePair, foundItems.get(0));
	}
	
	@Test
	public void getTwoItems() {
		List<ScorePair> foundItems = manager.getItems("revenue", 2);
		
		assertEquals(2, foundItems.size());
		assertEquals(monthlyRevenuePair, foundItems.get(0));
		assertEquals(yearlyRevenuePair, foundItems.get(1));
	}
	
	@Test
	public void comparablePriorityQueue() {
		IndexKeyValue value1 = new ScorePair("one", 1);
		IndexKeyValue value2 = new ScorePair("two", 2);

		PriorityQueue<IndexKeyValue> pairs = new PriorityQueue<IndexKeyValue>(
				2, Collections.reverseOrder());
		pairs.add(value1);
		pairs.add(value2);

		assertEquals(value2, pairs.poll());
		assertEquals(value1, pairs.poll());
	}

}
