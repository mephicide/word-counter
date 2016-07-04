# word-counter
A simple utility for building a simple console-based concordance from an English text document.

<h3>Requirements</h3>
* Java 7 or higher
* Maven 2.0 or higher
* Git (hey, you're here, aren't you ;) )

<h3>Usage:</h3>
1. `git clone https://github.com/mephicide/word-counter.git vickery-concordance/`
2. `cd vickery-concordance/`
3. `mvn package`
4. `java -jar target/concordance.jar /path/to/sample/file.txt`

<h3>Assumptions</h3>
* The entire contents of the examplefile will fit in system memory.  No attempt has been made to use disk resources for swapping / buffering file contents.
* Code clarity is preferred over optimal performance: there are optimization opportunities, should application profiling determine performance is unacceptable.  Those opportunities are mostly called-out in the comments in the code, but no asymptotic-complexity related optimizations have been attempted.
* Apache OpenNLP sentence detection is sufficiently correct for most input documents.  Domain specific text documents (legal documents, medical journals, and even un-stripped HTML etc.) may not parse correctly.  
* Similarly, spelling mistakes, absent whitespace, and words that have multiple representations have not been corrected.  A complete English lexicon with knowledge of the target domain would be preferable for maximal accuracy. Stemming and mining heuristics for inferring whitespace may also be beneficial, depending on the application.
* Input text is a UTF-8 encoded text file
* The exact output format is not a requirement for the final product.
* This is a product of iterative development, so further iteration would be performed upon feedback from the end-user
* Note: The overall computational complexity is linear ~ O(n + m), where n = character count, and m = produced token count since each character needs to be visited for tokenization, and subsequent tokens and alphabetized, then printed.  
