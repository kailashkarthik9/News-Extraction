/* 	Contextual Query-Driven News Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
*/
package com.newsextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class SynsetGenerator {
  
	//Function to generate the synset of the given word
	public static String[] generateSynset(String wordForm){
		ArrayList<String> al = new ArrayList<String>();
		//Setting path for the WordNet Directory
        File f = new File("C:\\Users\\User\\workspace\\NewsExtraction\\WordNet\\2.1\\dict");
        System.setProperty("wordnet.database.dir", f.toString());
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        //Fetch synset for the word
        Synset[] synsets = database.getSynsets(wordForm);
        if (synsets.length > 0) {        
	        HashSet<String> hs = new HashSet<String>();
	        for (int i = 0; i < synsets.length; i++) {
		        String[] wordForms = synsets[i].getWordForms();
		        for (int j = 0; j < wordForms.length; j++) {
		        	al.add(wordForms[j]);
		        }
	        }
	        //To remove redundancy, add the words to a HashMap and fetch it back
	        hs.addAll(al);
	        al.clear();
	        al.addAll(hs);        	        
        }
        //If no synset is available on WordNet, it is probably a proper noun. It's synset is itself only.
        else {
        	al.add(wordForm);
        }
        return al.toArray(new String[al.size()]);
	} 
	
	//Function to generate synsets for all words in a String
	public static  String[][] generateAllSynsets(String userQuery) {
		//Split the String into words
		String[] queryWords = userQuery.split(" ");
		String[][] synsets = new String[queryWords.length][];
		int i=0;
		//For each word, invoke the generateSynset method
		for(String word: queryWords) {
			String[] wordSynSet = generateSynset(word);
			synsets[i] = wordSynSet;
			i++;
		}
		return synsets;
	}
}