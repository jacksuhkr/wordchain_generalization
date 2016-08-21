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

	// 테스트, 복사과정에서 문제가 있나 싶어서 창고 단어장에서 검색함 ㅋ 
		System.out.print("단어 지우기전, " + vectorToRemove.word + 
				vectorToRemove.word.hashCode() + "," + vectorToRemove.weight + "가 있니?  ");
//		System.out.println(Classified.selfTrainedWords.get(firstLetterNum).contains(vectorToRemove));
		System.out.println(trainedWords.get(firstLetterNum).contains(vectorToRemove));	// 단어가 있는지 없는지
		System.out.print("혹시 뭔가가 null인가? "); System.out.println(vectorToRemove==null);
		
		// 단어장의 단어들을 다 확인해보자, 정말 없는지
//		Iterator<WordVector> getMostValuableWord = Classified.selfTrainedWords.get(firstLetterNum).iterator();
		Iterator<WordVector> getMostValuableWord = trainedWords.get(firstLetterNum).iterator();
		while(getMostValuableWord.hasNext()) {
			WordVector checkContents = getMostValuableWord.next();
			System.out.println
				(checkContents.word + checkContents.word.hashCode() + 
						"," + checkContents.weight);
			if(checkContents.word.equals(wordUsed)) {
				System.out.println("<<< 여기있네 찾았다, 지워야지 >>>");
				System.out.print("같은 단어라는데, compareTo로 비교해볼까?  ");
				System.out.println(vectorToRemove.compareTo(checkContents));
			// 해시코드를 확인해봤으나, 두 객체의 해시코드가 달라도, 단어장에서 잘 찾아서 지우는 경우가 많았다
				System.out.println("둘의 해시코드를 비교해볼까? " + 
						vectorToRemove.hashCode() + " , " + checkContents.hashCode());
				System.out.print("단어는 같아?  ");
				System.out.println(vectorToRemove.word.equals(checkContents.word));
				System.out.print("가중치는 같아?  ");
				System.out.println(vectorToRemove.weight==checkContents.weight);
//				System.out.println("단어도, 가중치도, 객체도 같은데 지웠어?" + 
//						trainedWords.get(firstLetterNum).remove(checkContents));
				break;
			}
    	}

    // 지우는걸 실패하면 맨 앞 단어와 비교하고 같으면 지움
    	if(!trainedWords.get(firstLetterNum).remove(vectorToRemove)) {
    		System.out.println("지울단어 " + vectorToRemove.word + vectorToRemove.weight);
    		System.out.println("Set단어 " + trainedWords.get(firstLetterNum).first().word 
    				+ trainedWords.get(firstLetterNum).first().weight);
//    		if(vectorToRemove.equals(trainedWords.get(firstLetterNum).first())) {
    		if(vectorToRemove.compareTo(trainedWords.get(firstLetterNum).first())==0) {	
    			trainedWords.get(firstLetterNum).pollFirst();
    		}	
    	}
    	
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
