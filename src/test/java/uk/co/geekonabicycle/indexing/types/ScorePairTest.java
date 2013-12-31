package uk.co.geekonabicycle.indexing.types;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import org.junit.Test;

public class ScorePairTest {

	@Test
	public void constructor() {
		ScorePair pair = new ScorePair("test", 1);
		assertEquals("test", pair.getKey());
		assertEquals(1, pair.getValue());
	}

	@Test
	public void comparable() {
		ScorePair pair1 = new ScorePair("one", 1);
		ScorePair pair2 = new ScorePair("two", 2);

		assertEquals(1, pair2.compareTo(pair1));
		assertEquals(-1, pair1.compareTo(pair2));
		assertEquals(0, pair1.compareTo(pair1));
	}
}
