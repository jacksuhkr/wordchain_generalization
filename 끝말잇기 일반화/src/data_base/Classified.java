package data_base;

import java.util.ArrayList;
import java.util.TreeSet;

import words.WordVector;

public class Classified {
	public static final 
			ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>();
	public static final
			ArrayList<TreeSet<WordVector>> trainedWords1 = new ArrayList<TreeSet<WordVector>>();
	public static final
			ArrayList<TreeSet<WordVector>> trainedWords2 = new ArrayList<TreeSet<WordVector>>();
	public static final
			ArrayList<TreeSet<WordVector>> trainedWords3 = new ArrayList<TreeSet<WordVector>>();
	
	// 자체 학습을 위한 단어장
	public static final
		ArrayList<TreeSet<WordVector>> selfTrainedWords = new ArrayList<TreeSet<WordVector>>();
	
	// ArrayList<String>을 읽어서 첫글자가 같은 단어들끼리 분류
    public static void classifyWords() {
        // firstLetters.length만큼 ArrayList<ArrayList<String>>에 자리를 만들어놓음, 즉, 초기화
        words.clear();
        for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
            ArrayList<String> emptyArray = new ArrayList<String>();     // 초기화의 매개로서 어레이리스트 만듦
            emptyArray.add("");                 // ""를 넣어서 emptyArray를 초기화
            words.add(emptyArray);             // emptyArray를 output의 객체로 추가
            words.get(i).remove(0);            // ""를 지움
        }

        // 첫글자의 글자번호를 구해서, 그 번지에다가 단어를 추가함.
        for(int i=0; i<Words.words.length; i++) {
            int firstLetterNum = FirstLetters.getFirstLetterNumber(Words.words[i]);        // 단어 첫글자의 글자번호
            System.out.println(Words.words[i] + firstLetterNum);
            words.get(firstLetterNum).add(Words.words[i]);                   // 해당 주소에 단어 추가
        }
    }
    
	// WordVector[]을 읽어서 첫글자가 같은 단어들끼리 분류, 가중치가 큰 순서로 넣음
    public static void classifyWeights(WordVector[] WordVectors, ArrayList<TreeSet<WordVector>> output) {
        // firstLetters.length만큼 ArrayList<TreeSet<WordVector>>에 자리를 만들어놓음, 즉, 초기화
        output.clear();
        for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
            TreeSet<WordVector> emptySet = new TreeSet<WordVector>();     // 초기화의 매개로서 어레이리스트 만듦
            emptySet.add(new WordVector("", 0));                 
            output.add(emptySet);             // emptySet를 output의 객체로 추가
            output.get(i).remove(new WordVector("", 0));           
        }

        // 첫글자의 글자번호를 구해서, 그 번지에다가 WordVector를 추가함.
        for(int i=0; i<WordVectors.length; i++) {
            int firstLetterNum = FirstLetters.getFirstLetterNumber(WordVectors[i].word);        // 단어 첫글자의 글자번호
            output.get(firstLetterNum).add(WordVectors[i]);                   // 해당 주소에 단어 추가
        }
    }

}
