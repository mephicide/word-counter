package org.vickery.word_counter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import junit.framework.TestCase;

public class ConcordanceBuilderTest extends TestCase
{
	public void testConcordanceExample() throws IOException, URISyntaxException
	{
		ConcordanceBuilder builder = new ConcordanceBuilder(getInputFile());
		HashMap<String, ConcordanceInfo> concordance = builder.prepareConcordance();
		
		assertTrue("Concordance should contain 34 elements, but contains " + concordance.size(), concordance.size()==34);
		ConcordanceInfo wordInfo = concordance.get("word");
		assertTrue("Concordance entry for 'word' should be {3:1,1,2} but is " + wordInfo, wordInfo.toString().equals("{3:1,1,2}"));
	}

	private File getInputFile() throws URISyntaxException 
	{
		return new File(this.getClass().getResource("test-concordance.txt").toURI().getPath());
	}
}
