package words;

import java.util.ArrayList;
import java.util.Iterator;
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

		// 테스트
		System.out.print("단어 지우기전, 창고 단어장에 " + vectorToRemove.word + 
				vectorToRemove.word.hashCode() + "," + vectorToRemove.weight + "가 있니?  ");
//		System.out.println(trainedWords.get(firstLetterNum).contains(vectorToRemove));	// 단어가 있는지 없는지
		System.out.println(Classified.selfTrainedWords.get(firstLetterNum).contains(vectorToRemove));
		
		Iterator<WordVector> getMostValuableWord = Classified.selfTrainedWords.get(firstLetterNum).iterator();
//		Iterator<WordVector> getMostValuableWord = trainedWords.get(firstLetterNum).iterator();
		while(getMostValuableWord.hasNext()) {
//			WordVector checkContents = getMostValuableWord.next();
			WordVector checkContents = getMostValuableWord.next();
			System.out.println
				(checkContents.word + checkContents.word.hashCode() + 
						"," + checkContents.weight);
			if(checkContents.word.equals(wordUsed)) {
				System.out.println("<<<<여기있네 찾았다, 지워야지>>>>");
				System.out.print("같은 단어라는데, 이 둘은 같은 객체인가?  ");
				System.out.println(vectorToRemove.equals(checkContents));
				// 해시코드를 확인해봤으나, 두 객체의 해시코드가 달라도, 단어장에서 잘 찾아서 지우는 경우가 많았다
				System.out.println(vectorToRemove.hashCode());
				System.out.println(checkContents.hashCode());
				System.out.print("단어는 같아?  ");
				System.out.println(vectorToRemove.word.equals(checkContents.word));
				System.out.print("가중치는 같아?  ");
				System.out.println(vectorToRemove.weight==checkContents.weight);
//				trainedWords.get(firstLetterNum).remove(checkContents);
				break;
			}
    	}
   
    	
    	
		trainedWords.get(firstLetterNum).remove(vectorToRemove);		// 단어를 지움
		System.out.print("단어 지운 후, " + vectorToRemove.word + 
				vectorToRemove.word.hashCode() + "," + vectorToRemove.weight + "가 있니?  ");
		System.out.println(trainedWords.get(firstLetterNum).contains(vectorToRemove));	// 단어가 있는지 없는지
	}
    
    @SuppressWarnings("unchecked")
	public static void copyWords
    	(ArrayList<ArrayList<String>> copy, ArrayList<ArrayList<String>> original) {
    	copy.clear();
		for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
			ArrayList<String> listToClone = (ArrayList<String>) original.get(i).clone();
			copy.add(listToClone);
		}
    }
    
    @SuppressWarnings("unchecked")
	public static void copyWordVectors
    	(ArrayList<TreeSet<WordVector>> copy, ArrayList<TreeSet<WordVector>> original) {
    	copy.clear();
		for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
			TreeSet<WordVector> listToClone = (TreeSet<WordVector>) original.get(i).clone();
//			copy.add(listToClone);
			
			// 초기화
			TreeSet<WordVector> emptySet = new TreeSet<WordVector>();     // 초기화의 매개로서 어레이리스트 만듦
            emptySet.add(new WordVector("", 0));                 
            copy.add(emptySet);             // emptySet를 output의 객체로 추가
            copy.get(i).remove(new WordVector("", 0));  
			
			Iterator<WordVector> iterator = listToClone.iterator();
			while(iterator.hasNext()) {
				WordVector wordVector = (WordVector) iterator.next();
				copy.get(i).add(wordVector);
			}
		}
    }
}
