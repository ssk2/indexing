package uk.co.geekonabicycle.indexing.server;

import java.io.File;
import java.util.List;

import org.eclipse.jetty.server.Server;

import uk.co.geekonabicycle.indexing.reader.IndexReader;
import uk.co.geekonabicycle.indexing.reader.JSONScoreIndexReader;
import uk.co.geekonabicycle.indexing.trie.TrieManager;
import uk.co.geekonabicycle.indexing.types.ScorePair;

public class IndexServer {

	public static void main(String[] args) throws Exception{
		Server server = new Server(8080);
		
		File serializedIndex = new File(args[0]);
		IndexReader<ScorePair> reader = new JSONScoreIndexReader(serializedIndex);
		List<ScorePair> scoreIndex = reader.readIndex();
		TrieManager<ScorePair> trieManager = new TrieManager<ScorePair>(scoreIndex);
				
		server.setHandler(new QueryHandler(trieManager));
		server.start();
		server.join();
	}
	
}
