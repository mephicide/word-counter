package org.vickery.word_counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * The entry way for the concordance-building exercise.  Given a File handle, printConcordance can be
 * invoked to print out an alphabetized list of words contained within the supplied document.  Each
 * word is accompanied with a printout of {<WORD_COUNT>:<COMMA-SEPARATED-LIST-OF-SENTENCE-INDICES>}
 * where <WORD_COUNT> is the count of the number of occurrences of the word , and 
 * <COMMA-SEPARATED-LIST-OF-SENTENCE-INDICES> is a comma-separated list of the sentences indices 
 * at which the associated word was discovered. Sentence parsing is done by a SentenceTokenizer, the
 * exact implementation of which can be determined and / or swapped out based on the domain-specific sentence
 * parsing requirements.
 * 
 * @author vickery
 *
 */
public class ConcordanceBuilder {
	
	private File inputFile;
	private SentenceTokenizer sentenceTokenizer;
	
	/**
	 * Initializes the sentence tokenizer and throw an IOException if there is an issue accessing the 
	 * sentence model training data.
	 * 
	 * @param inputFile
	 * @throws IOException
	 */
	public ConcordanceBuilder(File inputFile) throws IOException
	{
		this.inputFile = inputFile;
		this.sentenceTokenizer = new SentenceTokenizer();
	}

	/**
	 * Prints a concordance associated with the file specified in this class' constructor.
	 * 
	 * @throws IOException
	 */
	public void printConcordance() throws IOException
	{
		HashMap<String, ConcordanceInfo> concordance = new HashMap<String, ConcordanceInfo>();
		
		String entireFileContents = getFileContents();
		
		ArrayList<String> allSentences = sentenceTokenizer.tokenizeSentences(entireFileContents);
		
		concordance = buildConcordance(concordance, allSentences);
		
		TreeSet<String> alphabetical = alphabetize(concordance);
		
		int maxLength = findMaxWordLength(concordance);
		
		int maxCardLength = concordance.size();
		maxCardLength = (int)Math.log10(maxCardLength) + 2;
		
		printConcordanceToConsole(concordance, alphabetical, maxLength, maxCardLength);
	}

	/**
	 * Prints the contents of the concordance to the console.  Accepts length-related information in order
	 * to format the concordance printout for readability.
	 * 
	 * Also leverages TreeSet data structure for alphabetizing the concordance.
	 * 
	 * @param concordance
	 * @param alphabetical
	 * @param maxLength
	 * @param maxCardLength
	 */
	private void printConcordanceToConsole(HashMap<String, ConcordanceInfo> concordance, 
											TreeSet<String> alphabetical, 
											int maxLength, 
											int maxCardLength)
	{
		int wordIdx = 1;
		for(String oneWord : alphabetical)
		{
			StringBuffer oneLine = new StringBuffer();
			String prefix = wordIdx + ".";//mapToLetterIndex(wordIdx) + ".";
			oneLine.append(prefix);
			for(int i=0; i< (maxCardLength - prefix.length()); i++)
			{
				oneLine.append(" ");
			}
			oneLine.append(" ");
			oneLine.append(oneWord);
			for(int i=0; i< (maxLength - oneWord.length()); i++)
			{
				oneLine.append(" ");
			}
			oneLine.append(" ");
			oneLine.append(concordance.get(oneWord));
			
			System.out.println(oneLine);
			wordIdx++;
		}
		
	}

	/**
	 * A mapping function to attempt to make the concordance output adhere to the implied spec.
	 * imposed by the example.
	 * 
	 * @param wordIdx
	 * @return
	 */
	private String mapToLetterIndex(int wordIdx) 
	{
		int numRepeats = (int)(wordIdx / 27) + 1;
		int charValue = wordIdx % 26;
		if(charValue ==0 ) 
			charValue = 26;
		StringBuffer answer = new StringBuffer();
		char ourChar = (char)(charValue + 96);
		for(int i=0; i<numRepeats; i++)
		{
			answer.append(ourChar);
		}
		return answer.toString();
	}

	/**
	 * Finds the maximum word length in a concordance for output cleanliness. Can
	 * be moved to the concordance-building process should there be a need for increased performance.
	 * Exists in a separately called function for code modularity.
	 * 
	 * @param concordance
	 * @return
	 */
	private int findMaxWordLength(HashMap<String, ConcordanceInfo> concordance) 
	{
		int maxLength = 0;
		for(String oneWord : concordance.keySet())
		{
			if(oneWord.length() > maxLength)
				maxLength = oneWord.length();
		}
		return maxLength;
	}

	/**
	 * Alphabetizes the concordance for output.  Can be built during the initial pass over the input data
	 * if performance is a concern.  Is in its own separately called function for modularity.
	 * 
	 * @param concordance
	 * @return
	 */
	private TreeSet<String> alphabetize(
			HashMap<String, ConcordanceInfo> concordance) 
	{
		TreeSet<String> alphabetical = new TreeSet<String>();
		
		for(String oneWord : concordance.keySet())
		{
			alphabetical.add(oneWord);
		}

		return alphabetical;
	}

	private HashMap<String, ConcordanceInfo> buildConcordance(
			HashMap<String, ConcordanceInfo> concordance,
			ArrayList<String> allSentences) 
	{
		for(int sentenceIndex=0; sentenceIndex<allSentences.size(); sentenceIndex++ )
		{
			String oneSentence = allSentences.get(sentenceIndex);
			String[] words = oneSentence.split(" ");
			for(int i=0; i< words.length; i++)
			{
				String normalized = normalize(words[i]);
				if(normalized.isEmpty())
					continue;
				ConcordanceInfo thisInfo = concordance.get(normalized);
				if(thisInfo==null)
					thisInfo = new ConcordanceInfo();
				thisInfo.addOccurrence(sentenceIndex+1);
				concordance.put(normalized, thisInfo);
			}
		}
		
		return concordance;
	}

	private String normalize(String word) 
	{
		word = word.trim().toLowerCase();
		word = word.replaceAll("[^a-z]$", "");
		return word;
	}

	private String getFileContents() throws IOException 
	{
		StringBuffer sb = new StringBuffer();
		
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(inputFile),
					Charset.forName("UTF-8")));
		String oneLine = "";
		while((oneLine=br.readLine())!=null)
		{
			sb.append(oneLine);
		}
		
		return sb.toString();
	}
}
