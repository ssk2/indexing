package uk.co.geekonabicycle.indexing.types;

public class ScorePair implements IndexKeyValue {

	private String name;
	private int score;

	public ScorePair(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getKey() {
		return this.name;
	}

	public int getValue() {
		return this.score;
	}
	
	public int compareTo(IndexKeyValue other) {
		if (this.getValue() > other.getValue()) {
			return 1;
		} else if (this.getValue() < other.getValue()) {
			return -1;
		}
		return 0;
	}

}
