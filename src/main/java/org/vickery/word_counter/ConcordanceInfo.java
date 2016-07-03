package org.vickery.word_counter;

import java.util.ArrayList;

/**
 * A simple data structure for keeping track of the information about a word in
 * an English text document as required by a concordance.
 * 
 * @author vickery
 *
 */
public class ConcordanceInfo 
{
	private int occurrenceCount = 0;
	private ArrayList<Integer> sentencesFoundIn = new ArrayList<Integer>();
	
	/**
	 * Add a new sentence index at which an associated word was discovered.  Automatically
	 * increments the internal word counter and appends to the internal list the associated
	 * sentence index.
	 *  
	 * @param sentenceIndex
	 */
	public void addOccurrence(int sentenceIndex)
	{
		occurrenceCount++;
		sentencesFoundIn.add(sentenceIndex);
	}
	
	@Override
	public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append(occurrenceCount);
		sb.append(":");
		for(int i=0; i<sentencesFoundIn.size(); i++)
		{
			sb.append(sentencesFoundIn.get(i));
			if(i<sentencesFoundIn.size()-1)
				sb.append(",");
		}
		sb.append("}");
		return sb.toString();
		
	}
}
