package uk.co.geekonabicycle.indexing.builder;

import java.io.IOException;

public interface IndexBuilder<IndexType> {

	void buildIndex (Iterable<IndexType> indexElements) throws IOException;
	
}
