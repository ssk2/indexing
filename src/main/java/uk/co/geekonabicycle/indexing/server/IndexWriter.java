package uk.co.geekonabicycle.indexing.server;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.co.geekonabicycle.indexing.builder.IndexBuilder;
import uk.co.geekonabicycle.indexing.builder.JSONScoreIndexBuilder;
import uk.co.geekonabicycle.indexing.types.ScorePair;

public class IndexWriter {
	
	public static void main(String[] args) throws Exception{
		File serializedIndex = new File(args[0]);
		IndexBuilder<ScorePair> builder = new JSONScoreIndexBuilder(serializedIndex);
		
		List<ScorePair> scores = new ArrayList<ScorePair>();
		
		scores.add(new ScorePair("revenue", 200));
		scores.add(new ScorePair("monthly_revenue", 100));
		scores.add(new ScorePair("yearly_revenue", 300));
		
		builder.buildIndex(scores);
	}
	
}
