package com.newsextraction;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.jdt.internal.compiler.batch.Main;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class SynsetGenerator {
              
	public static ArrayList<String> generateSynset(String wordForm){
		ArrayList<String> al = new ArrayList<String>();
        File f = new File("C:\\Users\\User\\workspace\\NewsExtraction\\WordNet\\2.1\\dict");
        System.setProperty("wordnet.database.dir", f.toString());
        
        //setting path for the WordNet Directory
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(wordForm);
        
        if (synsets.length > 0) {        
	        HashSet<String> hs = new HashSet<String>();
	        for (int i = 0; i < synsets.length; i++) {
		        String[] wordForms = synsets[i].getWordForms();
		        for (int j = 0; j < wordForms.length; j++) {
		        	al.add(wordForms[j]);
		        }
	        }

	        //To remove duplicates, add to a hash set
	        hs.addAll(al);
	        al.clear();
	        al.addAll(hs);        	        
        }
        else {
        	System.err.println("No synsets exist that contain the word form '" + wordForm + "'");
        }
        return al;
	} 
	
	public static  String[] generateAllSynsets(String userQuery) {
		ArrayList<String> arrayList = new ArrayList<String>();
		String[] queryWords = userQuery.split(" ");
		for(String word: queryWords) {
			arrayList.addAll(generateSynset(word));
		}
		return arrayList.toArray(new String[arrayList.size()]);
	}
}