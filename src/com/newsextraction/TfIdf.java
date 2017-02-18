/* 	News Extraction and Summarization
		Final Year Project
		Authors:
			106113001 Abha Suman
			106113032 Hariprasad KR
			106113043 Kailash Karthik
*/
package com.newsextraction;

import java.util.ArrayList;

public class TfIdf {
	//Regular Expression constants for End of Sentence detection
	static final String PREFIXES = "(Mt|Mr|St|Mrs|Ms|Dr)([.])";
	static final String WEBSITES = "([.])(edu|com|net|org|io|gov)";
	static final String CAPS = "([A-Z])";
	static final String SUFFIXES = "(etc|Inc|Ltd|Jr|Sr|Co)";
	static final String STARTERS = "(The|Mr|Mrs|Ms|Dr|He\\s|She\\s|It\\s|They\\s|Their\\s|Our\\s|We\\s|But\\s|However\\s|That\\s|This\\s|Wherever)";
	static final String ACRONYMS = "([A-Z][.][A-Z][.](?:[A-Z][.])?)";
	static final String DIGITS = "([0-9])";
	
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
	
	//Function to generate summary from the input text using the TF-IDF algorithm on the keyword-synset
	public static String getSummary(String text, String[][] synset) {
		String[] sentences = getSentences(text);
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
		//For each sentence, stem the words and count the frequency of the keywords
		for(s=0;s<sentences.length;s++) {
			words = Stemmer.stemSentence(sentences[s]);
			for(i=0;i<synset.length;i++) {
				for(j=0;j<synset[i].length;j++) {
					if(words.contains(synset[i][j])) {
						count[s]++;
						thresholdCount++;
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
					summary.add(sentences[i]);
				}
			}
		}
		return String.join(" ", summary);
	}
}