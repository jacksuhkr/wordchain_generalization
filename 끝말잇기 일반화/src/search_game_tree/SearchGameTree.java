package search_game_tree;

import words.WordList;
import words.WordVector;

import java.util.ArrayList;
import java.util.TreeSet;

import data_base.*;
import main.*;
import static_variables.GameSetting;

public class SearchGameTree {
	public static String replyWordForSearching;
	private static boolean gameDone;
	private static boolean left;
	private static String foundWord;
	
	// �������� ������ ���� ã�µ� ����ϴ� ����Ʈ
	public static 
		ArrayList<TreeSet<WordVector>> myListForSearching = new ArrayList<TreeSet<WordVector>>();
	public static 
		ArrayList<ArrayList<String>> randomOpponentList = new ArrayList<ArrayList<String>>();
	
	// �н������ͷ� ������ ���� ã�µ� ����ϴ� ����Ʈ
	public static 
		ArrayList<TreeSet<WordVector>> myTrainedList = new ArrayList<TreeSet<WordVector>>();
	public static 
		ArrayList<TreeSet<WordVector>> opponentTrainedList = new ArrayList<TreeSet<WordVector>>();
	
	public static String getMaxValueWord(int lastNum) {
		String maxValueWord = "���� �ܾ� ����";		// ����Ʈ ���
		float winningRate;
		float maxWinningRate = 0.01f;
		int turnCounterSaved; 
		turnCounterSaved = GamePlayingActivity.turnCounter; 		// turnCounter����	
		
		// �ܾ ������� �� �����Ƿ� try, catch�ȿ� �־����
		try {		
			ArrayList<String> firstNodes = new ArrayList<String>();
			firstNodes.addAll(WordList.words.get(lastNum));
//			System.out.println("�˻��� �ܾ��" + firstNodes);
			
			for (int i=0; i<firstNodes.size(); i++) {
				// ��䰡�� ����Ʈ�� �ִ� �ܾ� �ϳ��� �־ �·��� ���
				winningRate = 0;
				winningRate += searchWithRandomOpponent(firstNodes.get(i));
//				winningRate += searchWithTrainedOpponent(firstNodes.get(i));
				winningRate += Maps.weightMap2.get(firstNodes.get(i));	
				winningRate += Maps.weightMap3.get(firstNodes.get(i));	
				
				// �ռ� max���� �·��� ũ�� max�� �ٲٰ�, ������ �ܾ �ٲ�
				if(winningRate>=maxWinningRate) {
					maxWinningRate = winningRate;
					maxValueWord = firstNodes.get(i);
//					System.out.println("�ٲ� �ְ�ġ �ܾ�� ��ġ :" + 
//									maxValueWord + " " + winningRate);
					if (winningRate==3.0) { break; }
				}
			}
		} catch (Exception e) {}
		
		if (maxValueWord.equals("���� �ܾ� ����")) { WordFinder.wordListNotEmpty = false; }
		else { WordFinder.wordListNotEmpty = true; }
		
		GamePlayingActivity.turnCounter = turnCounterSaved;	
		GamePlayingActivity.turnCounter++;				// ���⼭�� ���� �÷������

		return maxValueWord;
	}
	
	// �������� ���� ������
	private static float searchWithRandomOpponent(String startingWord) {
		int numOfWinningTrees = 0;
		replyWordForSearching = startingWord; 	// ó���� �ܾ��忡�� ���ܾ� �����ϴ¿�
		
		// �˻��� Ʈ������ŭ ����Ž��
		for(int i=0; i<GameSetting.numOfTreesToSearch; i++) {
			WordList.copyWords(randomOpponentList, WordList.words);
			WordList.copyWordVectors(myListForSearching, WordList.trainedWords3);
			
			// ���� startingWord�� ���ߴٰ� �����ϰ�, �����ʺ��� ����
			left = false; 
			gameDone = false;		// gameDone �ʱ�ȭ
			
			while(!gameDone) {
				// ���ܾ� ����
				WordList.removeWord(replyWordForSearching, randomOpponentList);
	        	WordList.removeWordVector3(replyWordForSearching, myListForSearching);
	        	
	        	// ������ ����
	        	String last = WordFinder.lastLetter(replyWordForSearching);
	        	int lastNum = FirstLetters.getFirstLetterNumber(last);
	        	
	        	// ���ã�ƿ�. ���� ����, randomOpponent�� ������.
	        	if(left) { foundWord = WordFinder.replyWithRandom(lastNum, randomOpponentList); }
	        	if(!left) { foundWord = WordFinder.replyWithTrained(lastNum, myListForSearching); }
	        	
	        	// ���Ұ��� �ִ� ���� ���� ���
	        	if (WordFinder.wordListNotEmpty) { replyWordForSearching =  foundWord; }
	        	if (!WordFinder.wordListNotEmpty) { gameDone = true; }
	        	
	        	// ������ �������� ���̵带 �ٲ�
	        	left = !left;
			}	
			// ���������� ���� ���̵带 �ٲ�, ���� �����ʿ��� ����� ���ϰ� �������� �Ѿ������ ���� �̱��
			if(left) { numOfWinningTrees++; }		 
		}
		
		// turn Counter ���󺹱�

		
		float winningRate =  (float)numOfWinningTrees/(float)GameSetting.numOfTreesToSearch;
		return winningRate;
	}
	
	public static int searchWithTrainedOpponent(String startingWord) {
		int win = 0;
		replyWordForSearching = startingWord; 	// ó���� �ܾ��忡�� ���ܾ� �����ϴ¿�
		
		// �ƹ�ư Ʈ������ �� ��� ����
		WordList.copyWordVectors(myTrainedList, WordList.trainedWords3);
		WordList.copyWordVectors(opponentTrainedList, WordList.trainedWords3);
			
		left = false; 			// ���� startingWord�� ���ߴٰ� �����ϰ�, �����ʺ��� ����
		gameDone = false;		// gameDone �ʱ�ȭ
			
		// ���� ���� ������
		while(!gameDone) {
			// ���ܾ� ����
			WordList.removeWordVector3(replyWordForSearching, myTrainedList);
	       	WordList.removeWordVector3(replyWordForSearching, opponentTrainedList);
	       	
	       	// ������ �����ϰ� ��� ã�ƿ�
	       	String last = WordFinder.lastLetter(replyWordForSearching);	 
	       	int lastNum = FirstLetters.getFirstLetterNumber(last);
        	if(left) { foundWord = WordFinder.replyWithTrained(lastNum, opponentTrainedList); }
	        if(!left) { foundWord = WordFinder.replyWithTrained(lastNum, myTrainedList); }
	        	
	       	// ���Ұ��� �ִ� ���� ���� ���
	       	if (WordFinder.wordListNotEmpty) { replyWordForSearching =  foundWord; }
	       	if (!WordFinder.wordListNotEmpty) { gameDone = true; }
	       	left = !left;	        	// ������ �������� ���̵带 �ٲ�
		}	
		if(left) { win++; }		 

		return win;
	}
}
