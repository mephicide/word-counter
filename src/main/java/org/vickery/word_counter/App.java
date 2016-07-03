package org.vickery.word_counter;

import java.io.File;
import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	if(args.length==0)
    	{
    		System.out.println("Must specify the location of the input text file."
    							+ "  Either specify an absolute path, or one relative to this jar.");
    		System.exit(1);
    	}
    	else
    	{
    		File inputFile = new File(args[0]); 
    		new ConcordanceBuilder(inputFile).printConcordance();
    	}
    }
}
