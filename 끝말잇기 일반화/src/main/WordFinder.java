package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import data_base.FirstLetters;
import search_game_tree.SearchGameTree;
import static_variables.GameSetting;
import words.WordList;
import words.WordVector;

/**
 * Created by user on 2016-07-29.
 */
public class WordFinder {
    public static boolean wordListNotEmpty;
    public static String foundWord;
    public static String last;
    public static int lastNum;
    public static ArrayList<String> replyWords = new ArrayList<String>();     // 대답용 단어장 생성

    public static String findWord(String input, int mode) {
        /* WordList타입별 단어리스트 대답 - Array생성, 해당 WordList에서 단어 입력, 한개라도 입력되면 출력 */
        last = lastLetter(input);
        lastNum = FirstLetters.getFirstLetterNumber(last);

//    	System.out.println("여기는 도착함" + input + last + lastNum);

        // 데이터 모으는용
        if (mode==1) {
            foundWord = replyWithRandom(lastNum, WordList.words);
            if(wordListNotEmpty) { return foundWord; }
        }
        
        // testingMode가 켜져있으면, 왼쪽은 replyWithWinningRate로 대답함 
        if (mode==2) {
        	// 왼쪽인지 판단하는 진리값. sideExchanger가 켜져있으면 반대로 출력한다.
        	boolean leftSide = GamePlayingActivity.sideExchanger(GamePlayingActivity.left);
        	// 기본적으로 왼쪽이 학습 데이터로 하게함, sideExchanger가 켜져있으면, 오른쪽이 학습 데이터로 하게함
        	if (leftSide) {
                foundWord = replyWithTrained(lastNum, getTrainedWords(GameSetting.leftDataNum));
        	} else if (!leftSide) {
                foundWord = replyWithRandom(lastNum, WordList.words);
        	} 
        	if(wordListNotEmpty) { return foundWord; }
        }
        
        // 학습 vs 학습
        if (mode==3) {
        	ArrayList<TreeSet<WordVector>> trainedWords = new ArrayList<TreeSet<WordVector>>();
            if(GamePlayingActivity.left)
            	{	trainedWords = getTrainedWords(GameSetting.leftDataNum);	}
            if(!GamePlayingActivity.left)
        		{	trainedWords = getTrainedWords(GameSetting.rightDataNum);	}
            foundWord = replyWithTrained(lastNum, trainedWords);
            if(wordListNotEmpty) { return foundWord; }
        }
        
        // 탐색트리를 가동시키는 경우
        if (mode==4) {
        	boolean leftSide = GamePlayingActivity.sideExchanger(GamePlayingActivity.left);
        	if (leftSide) {
        		foundWord = SearchGameTree.getMaxValueWord(lastNum);
        	} else if (!leftSide) {
                foundWord = replyWithRandom(lastNum, WordList.words);
        	} 
        	if(wordListNotEmpty) { return foundWord; }
        }
        
        // 자체학습
        if (mode==5) {
        	// 왼쪽인지 판단하는 진리값. sideExchanger가 켜져있으면 반대로 출력한다.
        	boolean leftSide = GamePlayingActivity.sideExchanger(GamePlayingActivity.left);
        	// 기본적으로 왼쪽이 학습 데이터로 하게함, sideExchanger가 켜져있으면, 오른쪽이 학습 데이터로 하게함
        	if (leftSide) {
                foundWord = replyWithTrained(lastNum, getTrainedWords(GameSetting.leftDataNum));
        	} else if (!leftSide) {
                foundWord = replyWithRandom(lastNum, WordList.words);
        	} 
        	if(wordListNotEmpty) { return foundWord; }
        }
        
        /* 단어를 찾지 못한 경우 */
            GamePlayingActivity.turnCounter=0;                // 게임이 끝났음을 표시
            return "이을 단어가 없네요. 제가 졌어요";
    }
    // findWord 메소드 종료


    // 단어에서 첫 글자를 뽑아냄
    public static String firstLetter(String input) {
        String first = input.substring(0, 1);
        return first;
    }

    // 단어에서 마지막 글자를 뽑아냄
    public static String lastLetter(String input) {
        int i = input.length();
        String last = input.substring(i-1, i);
        return last;
    }

    // 특정 단어장에서 대답을 출력
    public static String replyWithRandom(int lastNum, ArrayList<ArrayList<String>> wordListType) {
        ArrayList<String> replyWords = new ArrayList<String>();     // 대답용 단어장 생성
        replyWords.addAll(wordListType.get(lastNum));

        // 대답용 단어장에 단어가 있을 경우 단어를 찾아서 return함
        if (replyWords.size()>GameSetting.resignLimitNumber) {
            GamePlayingActivity.turnCounter++;
        	wordListNotEmpty = true;
            return getReply(replyWords);
        } else {
            wordListNotEmpty = false;
            return null;
        }
    }
    
    // 학습데이터로 찾음
    public static String replyWithTrained(int lastNum, ArrayList<TreeSet<WordVector>> trainedWords) {
    	Iterator<WordVector> getMostValuableWord = trainedWords.get(lastNum).iterator();
    	
    	if(getMostValuableWord.hasNext()) {
			GamePlayingActivity.turnCounter++;
			wordListNotEmpty = true;
    		return getMostValuableWord.next().word;
    	}
    	
    	wordListNotEmpty = false;
    	return null;
    }

    // 단어장에서 대답으로 쓸 단어 뽑아옴
    public static String getReply(ArrayList<String> replyWordList) {
        // 사용할 단어를 랜덤으로 선택
        int random = (int) (Math.random()*(replyWordList.size()));
        return replyWordList.get(random);
    }
    
    // 사용할 학습 단어장 선택
    public static ArrayList<TreeSet<WordVector>> getTrainedWords(int listNum) {
    	switch(listNum) {
    		case 0: return WordList.selfTrainedWords;
			case 1: return WordList.trainedWords1;
			case 2: return WordList.trainedWords2;
			case 3: return WordList.trainedWords3;
			default: return null;
    	}
    }
}
