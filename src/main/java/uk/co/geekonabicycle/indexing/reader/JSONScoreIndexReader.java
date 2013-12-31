package uk.co.geekonabicycle.indexing.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import uk.co.geekonabicycle.indexing.types.ScorePair;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class JSONScoreIndexReader implements IndexReader<ScorePair> {

	private InputStream inputStream;

	public JSONScoreIndexReader (File location) throws FileNotFoundException {
		inputStream = new FileInputStream(location);
	}
	
	public List<ScorePair> readIndex() throws IOException {
		Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        List<ScorePair> scorePairs = new ArrayList<ScorePair>();
        reader.beginArray();
        while (reader.hasNext()) {
        	ScorePair scorePair = gson.fromJson(reader,  ScorePair.class);
            scorePairs.add(scorePair);
        }
        reader.endArray();
        reader.close();
        return scorePairs;
	}
}
