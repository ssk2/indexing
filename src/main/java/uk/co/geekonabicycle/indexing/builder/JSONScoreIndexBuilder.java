package uk.co.geekonabicycle.indexing.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import uk.co.geekonabicycle.indexing.types.ScorePair;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class JSONScoreIndexBuilder implements IndexBuilder<ScorePair> {
	
	private FileOutputStream outputStream;

	public JSONScoreIndexBuilder (File location) throws FileNotFoundException {
		outputStream = new FileOutputStream(location);
	}

	public void buildIndex(Iterable<ScorePair> scorePairs) throws IOException {
		Gson gson = new Gson();
		JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.setIndent("  ");
        writer.beginArray();
        for (ScorePair scorePair : scorePairs) {
            gson.toJson(scorePair, ScorePair.class, writer);
        }
        writer.endArray();
        writer.close();
	}
	
}
