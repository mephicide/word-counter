package org.vickery.word_counter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * Turns a string representing an arbitrary English text document into a list of Strings which are
 * the English sentences found within the input document.  Offloads sentence parsing to
 * Apache's OpenNLP library (SentenceDetector) which uses a supervised-learning-produced classifier as a 
 * basis for which to determine where sentence boundaries are. The incorporated sample model was trained
 * using simple plain-english training data found here: http://opennlp.sourceforge.net/models-1.5/
 * Extensions to this code might use either:
 *  - a model produced from training data more applicable to the intended domain
 *  - A completely different sentence-detection approach (perhaps a formal grammar parser
 *  	enumerating all possible sentence boundary cases for the input format, etc.)  
 * 
 * @author vickery
 *
 */
public class SentenceTokenizer 
{
	private SentenceDetectorME detector;
	private TokenizerME wordDetector;
	
	/**
	 * Initializes the SentenceDetector delegate class with embedded training data.
	 *  
	 * @throws IOException
	 */
	public SentenceTokenizer() throws IOException
	{
		InputStream modelIn = this.getClass().getResourceAsStream("en-sent.bin");
		InputStream wordModelIn = this.getClass().getResourceAsStream("en-token.bin");

		try {
		 SentenceModel  model = new SentenceModel(modelIn);
		 TokenizerModel wordModel = new TokenizerModel(wordModelIn);
		 detector = new SentenceDetectorME(model);
		 wordDetector = new TokenizerME(wordModel);
		}
		finally {
		  if (modelIn != null)
		      modelIn.close();
		  if(wordModelIn!=null)
			  wordModelIn.close();
		}
	}
	
	/**
	 * Tokenize a document string into its discreet member English sentences.
	 * 
	 * @param input
	 * @return
	 */
	public ArrayList<String> tokenizeSentences(String input)
	{
		ArrayList<String> answer = new ArrayList<String>();
		String[] sentences = detector.sentDetect(input);
		for(int i=0; i<sentences.length; i++)
		{
			answer.add(sentences[i]);
		}
		return answer;
	}
	
	public String[] tokenizeWords(String input)
	{
		return wordDetector.tokenize(input);
	}
}
