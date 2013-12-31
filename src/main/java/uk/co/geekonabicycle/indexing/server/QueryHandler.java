package uk.co.geekonabicycle.indexing.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import uk.co.geekonabicycle.indexing.trie.TrieManager;
import uk.co.geekonabicycle.indexing.types.ScorePair;


public class QueryHandler extends AbstractHandler {

	private TrieManager<ScorePair> manager;


	public QueryHandler (TrieManager<ScorePair> manager) {
		this.manager = manager;
	}

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	    String query = request.getParameter("query");
		List<ScorePair> scores = manager.getItems(query, 10);
		
		response.setContentType("text/html;charset=utf-8");
	    response.setStatus(HttpServletResponse.SC_OK);
	    baseRequest.setHandled(true);
	    
	    for (ScorePair score : scores) {
	    	response.getWriter().println(score + "<br />");
	    }
	}
}
