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
    public static ArrayList<String> replyWords = new ArrayList<String>();     // ´ë´ä¿ë ´Ü¾îÀå »ı¼º

    public static String findWord(String input, int mode) {
        /* WordListÅ¸ÀÔº° ´Ü¾î¸®½ºÆ® ´ë´ä - Array»ı¼º, ÇØ´ç WordList¿¡¼­ ´Ü¾î ÀÔ·Â, ÇÑ°³¶óµµ ÀÔ·ÂµÇ¸é Ãâ·Â */
        last = lastLetter(input);
        lastNum = FirstLetters.getFirstLetterNumber(last);

//    	System.out.println("¿©±â´Â µµÂøÇÔ" + input + last + lastNum);

        // µ¥ÀÌÅÍ ¸ğÀ¸´Â¿ë
        if (mode==1) {
            foundWord = replyWithRandom(lastNum, WordList.words);
            if(wordListNotEmpty) { return foundWord; }
        }
        
        // testingMode°¡ ÄÑÁ®ÀÖÀ¸¸é, ¿ŞÂÊÀº replyWithWinningRate·Î ´ë´äÇÔ 
        if (mode==2) {
        	// ¿ŞÂÊÀÎÁö ÆÇ´ÜÇÏ´Â Áø¸®°ª. sideExchanger°¡ ÄÑÁ®ÀÖÀ¸¸é ¹İ´ë·Î Ãâ·ÂÇÑ´Ù.
        	boolean leftSide = GamePlayingActivity.sideExchanger(GamePlayingActivity.left);
        	// ±âº»ÀûÀ¸·Î ¿ŞÂÊÀÌ ÇĞ½À µ¥ÀÌÅÍ·Î ÇÏ°ÔÇÔ, sideExchanger°¡ ÄÑÁ®ÀÖÀ¸¸é, ¿À¸¥ÂÊÀÌ ÇĞ½À µ¥ÀÌÅÍ·Î ÇÏ°ÔÇÔ
        	if (leftSide) {
                foundWord = replyWithTrained(lastNum, getTrainedWords(GameSetting.leftDataNum));
        	} else if (!leftSide) {
                foundWord = replyWithRandom(lastNum, WordList.words);
        	} 
        	if(wordListNotEmpty) { return foundWord; }
        }
        
        // ÇĞ½À vs ÇĞ½À
        if (mode==3) {
        	ArrayList<TreeSet<WordVector>> trainedWords = new ArrayList<TreeSet<WordVector>>();
            if(GamePlayingActivity.left)
            	{	trainedWords = getTrainedWords(GameSetting.leftDataNum);	}
            if(!GamePlayingActivity.left)
        		{	trainedWords = getTrainedWords(GameSetting.rightDataNum);	}
            foundWord = replyWithTrained(lastNum, trainedWords);
            if(wordListNotEmpty) { return foundWord; }
        }
        
        // Å½»öÆ®¸®¸¦ °¡µ¿½ÃÅ°´Â °æ¿ì
        if (mode==4) {
        	boolean leftSide = GamePlayingActivity.sideExchanger(GamePlayingActivity.left);
        	if (leftSide) {
        		foundWord = SearchGameTree.getMaxValueWord(lastNum);
        	} else if (!leftSide) {
                foundWord = replyWithRandom(lastNum, WordList.words);
        	} 
        	if(wordListNotEmpty) { return foundWord; }
        }
        
        // ÀÚÃ¼ÇĞ½À
        if (mode==5) {
        	// ¿ŞÂÊÀÎÁö ÆÇ´ÜÇÏ´Â Áø¸®°ª. sideExchanger°¡ ÄÑÁ®ÀÖÀ¸¸é ¹İ´ë·Î Ãâ·ÂÇÑ´Ù.
        	boolean leftSide = GamePlayingActivity.sideExchanger(GamePlayingActivity.left);
        	// ±âº»ÀûÀ¸·Î ¿ŞÂÊÀÌ ÇĞ½À µ¥ÀÌÅÍ·Î ÇÏ°ÔÇÔ, sideExchanger°¡ ÄÑÁ®ÀÖÀ¸¸é, ¿À¸¥ÂÊÀÌ ÇĞ½À µ¥ÀÌÅÍ·Î ÇÏ°ÔÇÔ
        	if (leftSide) {
                foundWord = replyWithTrained(lastNum, getTrainedWords(GameSetting.leftDataNum));
        	} else if (!leftSide) {
                foundWord = replyWithRandom(lastNum, WordList.words);
        	} 
        	if(wordListNotEmpty) { return foundWord; }
        }
        
        /* ´Ü¾î¸¦ Ã£Áö ¸øÇÑ °æ¿ì */
            GamePlayingActivity.turnCounter=0;                // °ÔÀÓÀÌ ³¡³µÀ½À» Ç¥½Ã
            return "ÀÌÀ» ´Ü¾î°¡ ¾ø³×¿ä. Á¦°¡ Á³¾î¿ä";
    }
    // findWord ¸Ş¼Òµå Á¾·á


    // ´Ü¾î¿¡¼­ Ã¹ ±ÛÀÚ¸¦ »Ì¾Æ³¿
    public static String firstLetter(String input) {
        String first = input.substring(0, 1);
        return first;
    }

    // ´Ü¾î¿¡¼­ ¸¶Áö¸· ±ÛÀÚ¸¦ »Ì¾Æ³¿
    public static String lastLetter(String input) {
        int i = input.length();
        String last = input.substring(i-1, i);
        return last;
    }

    // Æ¯Á¤ ´Ü¾îÀå¿¡¼­ ´ë´äÀ» Ãâ·Â
    public static String replyWithRandom(int lastNum, ArrayList<ArrayList<String>> wordListType) {
        ArrayList<String> replyWords = new ArrayList<String>();     // ´ë´ä¿ë ´Ü¾îÀå »ı¼º
        replyWords.addAll(wordListType.get(lastNum));
        
//        System.out.println("¿©±â´Â µµÂøÇÔ");

        // ´ë´ä¿ë ´Ü¾îÀå¿¡ ´Ü¾î°¡ ÀÖÀ» °æ¿ì ´Ü¾î¸¦ Ã£¾Æ¼­ returnÇÔ
        if (replyWords.size()>GameSetting.resignLimitNumber) {
            GamePlayingActivity.turnCounter++;
        	wordListNotEmpty = true;
            return getReply(replyWords);
        } else {
            wordListNotEmpty = false;
            return null;
        }
    }
    
    // ÇĞ½Àµ¥ÀÌÅÍ·Î Ã£À½
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

    // ´Ü¾îÀå¿¡¼­ ´ë´äÀ¸·Î ¾µ ´Ü¾î »Ì¾Æ¿È
    public static String getReply(ArrayList<String> replyWordList) {
        // »ç¿ëÇÒ ´Ü¾î¸¦ ·£´ıÀ¸·Î ¼±ÅÃ
        int random = (int) (Math.random()*(replyWordList.size()));
    	
    	/*String word = "´ë´ä";
    	boolean properWord = false;
    	while(!properWord) {
            int random = (int) (Math.random()*(replyWordList.size()));
            word = replyWordList.get(random);
            int wordLastNum = FirstLetters.getFirstLetterNumber(lastLetter(word));
            if(0 <= wordLastNum && wordLastNum <= ('ÆR'-'°¡'))
            { properWord = true; }
    	}*/

        return replyWordList.get(random);
    }
    
    // »ç¿ëÇÒ ÇĞ½À ´Ü¾îÀå ¼±ÅÃ
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
