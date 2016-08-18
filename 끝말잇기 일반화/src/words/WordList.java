package words;

import java.util.ArrayList;
import java.util.TreeSet;

import data_base.*;

public class WordList {
    // 사용할 단어사전 어레이 선언
	public static
		ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>();
	public static
		ArrayList<TreeSet<WordVector>> trainedWords1 = new ArrayList<TreeSet<WordVector>>();
	public static
		ArrayList<TreeSet<WordVector>> trainedWords2 = new ArrayList<TreeSet<WordVector>>();
	public static
		ArrayList<TreeSet<WordVector>> trainedWords3 = new ArrayList<TreeSet<WordVector>>();
	
	// 자체학습 단어장
	public static
		ArrayList<TreeSet<WordVector>> selfTrainedWords = new ArrayList<TreeSet<WordVector>>();

	public static void setWords() { 
		copyWords(words, Classified.words);
	}
	
	public static void setTrainedWords1() { 
		copyWordVectors(trainedWords1, Classified.trainedWords1);
	}
	
	public static void setTrainedWords2() { 
		copyWordVectors(trainedWords2, Classified.trainedWords2);
	}
	
	public static void setTrainedWords3() { 
		copyWordVectors(trainedWords3, Classified.trainedWords3);
	}
	
	// 자체학습
	public static void setSelfTrainedWords() { 
		copyWordVectors(selfTrainedWords, Classified.selfTrainedWords);
	}
	
    // 사용한 단어 지우는 메소드
    public static void removeWord(String wordUsed, ArrayList<ArrayList<String>> words) {
    	int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
    	for(int i=0; i<words.get(firstLetterNum).size(); i++) {
    		if(wordUsed.equals(words.get(firstLetterNum).get(i))) {
    			words.get(firstLetterNum).remove(i);
    		}
    	}
    }
    
    public static void removeWordVector1
    				(String wordUsed, ArrayList<TreeSet<WordVector>> trainedWords) {
    	int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
    	WordVector vectorToRemove = new WordVector(wordUsed, Maps.weightMap1.get(wordUsed));
    	trainedWords.get(firstLetterNum).remove(vectorToRemove);
    }
    
    public static void removeWordVector2
    				(String wordUsed, ArrayList<TreeSet<WordVector>> trainedWords) {
    	int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
    	WordVector vectorToRemove = new WordVector(wordUsed, Maps.weightMap2.get(wordUsed));
    	trainedWords.get(firstLetterNum).remove(vectorToRemove);
    }
    
    public static void removeWordVector3
					(String wordUsed, ArrayList<TreeSet<WordVector>> trainedWords) {
		int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
		WordVector vectorToRemove = new WordVector(wordUsed, Maps.weightMap3.get(wordUsed));
		trainedWords.get(firstLetterNum).remove(vectorToRemove);
	}
    
    // 자체학습
    public static void removeSelfWordVector
					(String wordUsed, ArrayList<TreeSet<WordVector>> trainedWords) {
		int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
		WordVector vectorToRemove = new WordVector(wordUsed, Maps.selfWeightMap.get(wordUsed));
		trainedWords.get(firstLetterNum).remove(vectorToRemove);
	}
    
    public static void copyWords
    	(ArrayList<ArrayList<String>> copy, ArrayList<ArrayList<String>> original) {
    	copy.clear();
		for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
			ArrayList<String> listToClone = (ArrayList<String>) original.get(i).clone();
			copy.add(listToClone);
		}
    }
    
    public static void copyWordVectors
    	(ArrayList<TreeSet<WordVector>> copy, ArrayList<TreeSet<WordVector>> original) {
    	copy.clear();
		for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
			TreeSet<WordVector> listToClone = (TreeSet<WordVector>) original.get(i).clone();
			copy.add(listToClone);
		}
    }
}
