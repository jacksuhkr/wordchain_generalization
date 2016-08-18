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
	
	// 랜덤상대로 최적의 수를 찾는데 사용하는 리스트
	public static 
		ArrayList<TreeSet<WordVector>> myListForSearching = new ArrayList<TreeSet<WordVector>>();
	public static 
		ArrayList<ArrayList<String>> randomOpponentList = new ArrayList<ArrayList<String>>();
	
	// 학습데이터로 최적의 수를 찾는데 사용하는 리스트
	public static 
		ArrayList<TreeSet<WordVector>> myTrainedList = new ArrayList<TreeSet<WordVector>>();
	public static 
		ArrayList<TreeSet<WordVector>> opponentTrainedList = new ArrayList<TreeSet<WordVector>>();
	
	public static String getMaxValueWord(int lastNum) {
		String maxValueWord = "이을 단어 없음";		// 디폴트 대답
		float winningRate;
		float maxWinningRate = 0.01f;
		int turnCounterSaved; 
		turnCounterSaved = GamePlayingActivity.turnCounter; 		// turnCounter저장	
		
		// 단어가 비어있을 수 있으므로 try, catch안에 넣어야함
		try {		
			ArrayList<String> firstNodes = new ArrayList<String>();
			firstNodes.addAll(WordList.words.get(lastNum));
//			System.out.println("검색할 단어들" + firstNodes);
			
			for (int i=0; i<firstNodes.size(); i++) {
				// 대답가능 리스트에 있는 단어 하나를 넣어서 승률을 계산
				winningRate = 0;
				winningRate += searchWithRandomOpponent(firstNodes.get(i));
//				winningRate += searchWithTrainedOpponent(firstNodes.get(i));
				winningRate += Maps.weightMap2.get(firstNodes.get(i));	
				winningRate += Maps.weightMap3.get(firstNodes.get(i));	
				
				// 앞선 max보다 승률이 크면 max를 바꾸고, 리턴할 단어를 바꿈
				if(winningRate>=maxWinningRate) {
					maxWinningRate = winningRate;
					maxValueWord = firstNodes.get(i);
//					System.out.println("바뀐 최고가치 단어와 가치 :" + 
//									maxValueWord + " " + winningRate);
					if (winningRate==3.0) { break; }
				}
			}
		} catch (Exception e) {}
		
		if (maxValueWord.equals("이을 단어 없음")) { WordFinder.wordListNotEmpty = false; }
		else { WordFinder.wordListNotEmpty = true; }
		
		GamePlayingActivity.turnCounter = turnCounterSaved;	
		GamePlayingActivity.turnCounter++;				// 여기서도 턴은 늘려줘야지

		return maxValueWord;
	}
	
	// 랜덤으로 몇판 돌려봄
	private static float searchWithRandomOpponent(String startingWord) {
		int numOfWinningTrees = 0;
		replyWordForSearching = startingWord; 	// 처음에 단어장에서 사용단어 삭제하는용
		
		// 검색할 트리수만큼 랜덤탐색
		for(int i=0; i<GameSetting.numOfTreesToSearch; i++) {
			WordList.copyWords(randomOpponentList, WordList.words);
			WordList.copyWordVectors(myListForSearching, WordList.trainedWords3);
			
			// 내가 startingWord를 말했다고 생각하고, 오른쪽부터 시작
			left = false; 
			gameDone = false;		// gameDone 초기화
			
			while(!gameDone) {
				// 사용단어 삭제
				WordList.removeWord(replyWordForSearching, randomOpponentList);
	        	WordList.removeWordVector3(replyWordForSearching, myListForSearching);
	        	
	        	// 끝글자 생성
	        	String last = WordFinder.lastLetter(replyWordForSearching);
	        	int lastNum = FirstLetters.getFirstLetterNumber(last);
	        	
	        	// 대답찾아옴. 내가 왼쪽, randomOpponent가 오른쪽.
	        	if(left) { foundWord = WordFinder.replyWithRandom(lastNum, randomOpponentList); }
	        	if(!left) { foundWord = WordFinder.replyWithTrained(lastNum, myListForSearching); }
	        	
	        	// 답할것이 있는 경우와 없는 경우
	        	if (WordFinder.wordListNotEmpty) { replyWordForSearching =  foundWord; }
	        	if (!WordFinder.wordListNotEmpty) { gameDone = true; }
	        	
	        	// 한턴이 돌았으니 사이드를 바꿈
	        	left = !left;
			}	
			// 빠져나오기 전에 사이드를 바꿈, 따라서 오른쪽에서 대답을 못하고 왼쪽으로 넘어왔으면 내가 이긴거
			if(left) { numOfWinningTrees++; }		 
		}
		
		// turn Counter 원상복구

		
		float winningRate =  (float)numOfWinningTrees/(float)GameSetting.numOfTreesToSearch;
		return winningRate;
	}
	
	public static int searchWithTrainedOpponent(String startingWord) {
		int win = 0;
		replyWordForSearching = startingWord; 	// 처음에 단어장에서 사용단어 삭제하는용
		
		// 아무튼 트리에서 쓸 어레이 생성
		WordList.copyWordVectors(myTrainedList, WordList.trainedWords3);
		WordList.copyWordVectors(opponentTrainedList, WordList.trainedWords3);
			
		left = false; 			// 내가 startingWord를 말했다고 생각하고, 오른쪽부터 시작
		gameDone = false;		// gameDone 초기화
			
		// 게임 한판 돌리기
		while(!gameDone) {
			// 사용단어 삭제
			WordList.removeWordVector3(replyWordForSearching, myTrainedList);
	       	WordList.removeWordVector3(replyWordForSearching, opponentTrainedList);
	       	
	       	// 끝글자 생성하고 대답 찾아옴
	       	String last = WordFinder.lastLetter(replyWordForSearching);	 
	       	int lastNum = FirstLetters.getFirstLetterNumber(last);
        	if(left) { foundWord = WordFinder.replyWithTrained(lastNum, opponentTrainedList); }
	        if(!left) { foundWord = WordFinder.replyWithTrained(lastNum, myTrainedList); }
	        	
	       	// 답할것이 있는 경우와 없는 경우
	       	if (WordFinder.wordListNotEmpty) { replyWordForSearching =  foundWord; }
	       	if (!WordFinder.wordListNotEmpty) { gameDone = true; }
	       	left = !left;	        	// 한턴이 돌았으니 사이드를 바꿈
		}	
		if(left) { win++; }		 

		return win;
	}
}
