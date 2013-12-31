package uk.co.geekonabicycle.indexing.reader;

import java.io.IOException;
import java.util.List;

public interface IndexReader<IndexType> {

	List<IndexType> readIndex () throws IOException;
	
}
