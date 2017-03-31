/* 	Contextual Query-Driven News Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
*/
package com.newsextraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TfIdf {
	//Regular Expression constants for End of Sentence detection
	static final String PREFIXES = "(Mt|Mr|St|Mrs|Ms|Dr|Col|Capt|Hon|Maj|Prof|Pres|Sr|Jr|Gen|Genl|Sgt|Lt|Maj|Ave|Jn)([.])";
	static final String WEBSITES = "([.])(edu|com|net|org|io|gov|sq|ft|m)";
	static final String CAPS = "([A-Z])";
	static final String SUFFIXES = "(etc|Inc|Ltd|Jr|Sr|Co)";
	static final String STARTERS = "(The|Mr|Mrs|Ms|Dr|He\\s|She\\s|It\\s|They\\s|Their\\s|Our\\s|We\\s|But\\s|However\\s|That\\s|This\\s|Wherever)";
	static final String ACRONYMS = "([A-Z][.][A-Z][.](?:[A-Z][.])?)";
	static final String DIGITS = "([0-9])";
	//IDF HashMap
	HashMap<String, Double> idfMap;
	
	//Function to split the input string into sentences
	static String[] getSentences(String input) {
		//The non-EOS periods are replaced by a stopper <prd>
		input = input.replaceAll(PREFIXES, "$1<prd>");
		input = input.replaceAll(WEBSITES, "<prd>$2");
		input = input.replaceAll("\\s" + CAPS + "([.]) "," $1<prd> ");
		input = input.replaceAll(ACRONYMS+" "+STARTERS,"$1<stop> $2");
		input = input.replaceAll(CAPS + "[.]" + CAPS + "[.]" + CAPS + "[.]","$1<prd>$2<prd>$3<prd>");
		input = input.replaceAll(CAPS + "[.]" + CAPS + "([.])","$1<prd>$2<prd>");
		input = input.replaceAll(" "+SUFFIXES+"[.] "+STARTERS," $1<stop> $2");
		input = input.replaceAll(" "+SUFFIXES+"([.])"," $1<prd>");
		input = input.replaceAll(DIGITS + "[.]" + DIGITS,"$1<prd>$2");
		input = input.replaceAll(" " + CAPS + "[.]"," $1<prd>");
		//Paragraph splits are replaced by a stopper <para>
		input = input.replaceAll("\n","<para>");
		//Periods, Exclamation and Question marks inside quotes are moved outside
		input = input.replaceAll("[.]\"","\".");
		input = input.replaceAll("\\?\"","\"?");
		input = input.replaceAll("!\"","\"!");
		//All the Periods, Exclamation and Question marks are replaced by a stopper <stop>
		input = input.replaceAll("[.]",".<stop>");
		input = input.replaceAll("<para>","<para>");
		input = input.replaceAll("\\?","?<stop>");
		input = input.replaceAll("!","!<stop>");
		//The <prd> stoppers are replaced by periods
		input = input.replaceAll("<prd>",".");
		input = input.replaceAll("<para>","");
		//The text is split using <prd> as the delimiter
		String sentences[] = input.split("<stop>");
		for(int i=0;i<sentences.length;i++)
			sentences[i] = sentences[i].trim();
		return sentences;
	}
	
	//Constructor to inititalize document frequencies 
	public TfIdf() throws IOException {
		//Read file containing document frequencies
		File  fileText = new File("C:\\Users\\User\\Desktop\\8th Semester\\Project\\NewsSearch\\Template\\tfidf.txt");
		String word;
		int df;
		int vocabularySize = 10798;
		idfMap = new HashMap<>();
		Stemmer stemmer = new Stemmer();
		//Create a map of word and its IDF
		if(fileText.exists()) {
			BufferedReader in = new BufferedReader(new FileReader(fileText));
			while((word = in.readLine()) != null) {
				df = Integer.parseInt(in.readLine());
				if(df == 0)
					df++;
				//Stem the word before inserting into map
				idfMap.put(stemmer.stem(word).toLowerCase(), Math.log(vocabularySize/df));
			}
			in.close();
		}
	}
	
	//Function to generate summary from the input text using the TF-IDF algorithm on the keyword-synset
	public String getSummary(int relevantAid, String[][] synset) {
		String text = RepoConnector.getResolvedTextGivenId(relevantAid);
		String originalText = RepoConnector.getTextGivenId(relevantAid);
		String[] sentences = getSentences(text);
		String[] originalSentences = getSentences(originalText);
		int[] count = new int[sentences.length];
		ArrayList<String> summary = new ArrayList<>();
		int i;
		int j;
		int s;
		Stemmer stemmer = new Stemmer();
		String words;
		int thresholdCount = 0;
		//Stem the keywords for better term frequency precision
		for(i=0;i<synset.length;i++) {
			for(j=0;j<synset[i].length;j++) {
				synset[i][j] = stemmer.stem(synset[i][j]).toString();
			}
		}
		//Initialize the frequency counts to 0
		for(i=0;i<count.length;i++)
			count[i]=0;
		double sumTerm = 0;
		//For each sentence, stem the words and compute TF-IDF as TF * IDF
		for(s=0;s<sentences.length;s++) {
			words = Stemmer.stemSentence(sentences[s]);
			for(i=0;i<synset.length;i++) {
				for(j=0;j<synset[i].length;j++) {
					if(words.toLowerCase().contains(synset[i][j].toLowerCase())) {
						//Initialize frequency value from IDF-map with edge case being 1
						if(idfMap.containsKey(synset[i][j]))
							sumTerm = idfMap.get(synset[i][j]);
						else
							sumTerm = 1;
						//TFIDF = TF * IDF
						//Since it is repeated for each word occurrence TF=1 for each iteration
						//IDF is obtained from IDF-map
						//TFIDF = TF * IDF = Sum ( 1*IDF, 1, TF)
						count[s] = count[s] + 1*(int)sumTerm;
						thresholdCount = thresholdCount + 1*(int)sumTerm;
					}
				}
			}
		}
		//Compute threshold count as the average of the term frequencies of the entire document
		thresholdCount /= sentences.length;
		for(i=0;i<sentences.length;i++) {
			//If the TF-IDF of the sentence is greater than the threshold, add it to the summary
			if(count[i]>thresholdCount) {
				//Last-minute noise removal
				if(!sentences[i].toLowerCase().contains("the times of india") &&
					!sentences[i].toLowerCase().contains("the hindu") &&
					!sentences[i].toLowerCase().contains("deccan chronicle")) {
					if(originalSentences.length>i)
						summary.add(originalSentences[i]);
				}
			}
		}
		return String.join(" ", summary).trim();
	}
}