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
    public static ArrayList<String> replyWords = new ArrayList<String>();     // ���� �ܾ��� ����

    public static String findWord(String input, int mode) {
        /* WordListŸ�Ժ� �ܾ��Ʈ ��� - Array����, �ش� WordList���� �ܾ� �Է�, �Ѱ��� �ԷµǸ� ��� */
        last = lastLetter(input);
        lastNum = FirstLetters.getFirstLetterNumber(last);

        // ������ �����¿�
        if (mode==1) {
            foundWord = replyWithRandom(lastNum, WordList.words);
            if(wordListNotEmpty) { return foundWord; }
        }
        
        // testingMode�� ����������, ������ replyWithWinningRate�� ����� 
        if (mode==2) {
        	// �������� �Ǵ��ϴ� ������. sideExchanger�� ���������� �ݴ�� ����Ѵ�.
        	boolean leftSide = GamePlayingActivity.sideExchanger(GamePlayingActivity.left);
        	// �⺻������ ������ �н� �����ͷ� �ϰ���, sideExchanger�� ����������, �������� �н� �����ͷ� �ϰ���
        	if (leftSide) {
                foundWord = replyWithTrained(lastNum, getTrainedWords(GameSetting.leftDataNum));
        	} else if (!leftSide) {
                foundWord = replyWithRandom(lastNum, WordList.words);
        	} 
        	if(wordListNotEmpty) { return foundWord; }
        }
        
        // �н� vs �н�
        if (mode==3) {
        	ArrayList<TreeSet<WordVector>> trainedWords = new ArrayList<TreeSet<WordVector>>();
            if(GamePlayingActivity.left)
            	{	trainedWords = getTrainedWords(GameSetting.leftDataNum);	}
            if(!GamePlayingActivity.left)
        		{	trainedWords = getTrainedWords(GameSetting.rightDataNum);	}
            foundWord = replyWithTrained(lastNum, trainedWords);
            if(wordListNotEmpty) { return foundWord; }
        }
        
        // Ž��Ʈ���� ������Ű�� ���
        if (mode==4) {
        	boolean leftSide = GamePlayingActivity.sideExchanger(GamePlayingActivity.left);
        	if (leftSide) {
        		foundWord = SearchGameTree.getMaxValueWord(lastNum);
        	} else if (!leftSide) {
                foundWord = replyWithRandom(lastNum, WordList.words);
        	} 
        	if(wordListNotEmpty) { return foundWord; }
        }
        
        // ��ü�н�
        if (mode==5) {
        	// �������� �Ǵ��ϴ� ������. sideExchanger�� ���������� �ݴ�� ����Ѵ�.
        	boolean leftSide = GamePlayingActivity.sideExchanger(GamePlayingActivity.left);
        	// �⺻������ ������ �н� �����ͷ� �ϰ���, sideExchanger�� ����������, �������� �н� �����ͷ� �ϰ���
        	if (leftSide) {
                foundWord = replyWithTrained(lastNum, getTrainedWords(GameSetting.leftDataNum));
        	} else if (!leftSide) {
                foundWord = replyWithRandom(lastNum, WordList.words);
        	} 
        	if(wordListNotEmpty) { return foundWord; }
        }
        
        /* �ܾ ã�� ���� ��� */
            GamePlayingActivity.turnCounter=0;                // ������ �������� ǥ��
            return "���� �ܾ ���׿�. ���� �����";
    }
    // findWord �޼ҵ� ����


    // �ܾ�� ù ���ڸ� �̾Ƴ�
    public static String firstLetter(String input) {
        String first = input.substring(0, 1);
        return first;
    }

    // �ܾ�� ������ ���ڸ� �̾Ƴ�
    public static String lastLetter(String input) {
        int i = input.length();
        String last = input.substring(i-1, i);
        return last;
    }

    // Ư�� �ܾ��忡�� ����� ���
    public static String replyWithRandom(int lastNum, ArrayList<ArrayList<String>> wordListType) {
        ArrayList<String> replyWords = new ArrayList<String>();     // ���� �ܾ��� ����
        
        replyWords.addAll(wordListType.get(lastNum));
        


        // ���� �ܾ��忡 �ܾ ���� ��� �ܾ ã�Ƽ� return��
        if (replyWords.size()>GameSetting.resignLimitNumber) {
            GamePlayingActivity.turnCounter++;
        	wordListNotEmpty = true;
            return getReply(replyWords);
        } else {
            wordListNotEmpty = false;
            return null;
        }
    }
    
    // �н������ͷ� ã��
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

    // �ܾ��忡�� ������� �� �ܾ� �̾ƿ�
    public static String getReply(ArrayList<String> replyWordList) {
        // ����� �ܾ �������� ����
        int random = (int) (Math.random()*(replyWordList.size()));
        return replyWordList.get(random);
    }
    
    // ����� �н� �ܾ��� ����
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
