package uk.co.geekonabicycle.indexing.types;

public interface IndexKeyValue extends Comparable<IndexKeyValue> {
	String getKey();
	int getValue();
}
