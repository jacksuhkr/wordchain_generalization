package main;

import data_base.Maps;
import data_base.WordVectors;
import data_base.Words;
import intro.IntroActivity;
import static_variables.GameSetting;
import statistics.WriteTextFile;
import words.WordList;

public class GamePlayingActivity {
    public static boolean left = true;                          // 왼쪽 오른쪽에 대한 진리값
    public static int turnCounter = 0;         // 지금까지 진행된 턴 개수를 카운트
    public static ReplyMessage replyMessage = new ReplyMessage(true, null);   //replyAlgorithm을 쓰기 위해 ReplyMessage 객체 형성
    public static String startingWord;
    public static int numOfGamesToPlay;
    public int numOfGamesPlayedCounter;
    public static int[] numOfGamesWon = new int[Words.NUM_OF_WORDS];		// 단어별 이긴횟수
    
    public void startGame(boolean[] gameSetting) {
    	// 난이도 설정
        setDifficulty(gameSetting);

        // 게임이 시작될 때 초기화
        resetWordList();
        turnCounter=0;
        left=true;
        
        if (!GameSetting.startWithRandomWords) {
        	for (int i=0; i<Words.NUM_OF_WORDS; i++) {
            	// 첫단어 세팅
            	left = true;					// 처음은 무조건 왼쪽에서 시작하게
            	startingWord = Words.words[i];				//시작단어 선언
            	if(GameSetting.printStartingWord) {
            		System.out.println("\n첫 단어 " + startingWord + ", 게임을 시작합니다.");	// 무슨단어 하는지 출력
            	}
            	addMessage(startingWord);										// 첫단어 시작
                ReplyMessage.replyWord = startingWord;							// 대답도 그걸로 지정
                
                // 진행상황 출력
                if (GameSetting.printNumOfWords) {
                	if ((i+1)%100==0) 
                	{ System.out.println((i+1) + "개 단어 진행됨"); }	// 몇번째 단어 하는지 출력
                }
                
                // 시작단어가 바뀌었으므로, 게임판수, 게임이긴수 초기화
            	numOfGamesPlayedCounter = 0;								// 게임 판수 초기화
            	numOfGamesWon[i]=0;											// 게임 이긴수 초기화
            	
            	// 요구하는 판수만큼 해당 단어를 첫단어로 플레이하고 이긴수를 출력
            	numOfGamesWon[i] = playNumOfGames(numOfGamesWon[i]);
            }
        	printEachSidePlayer();
        	
            // 첫단어, 이긴횟수, 사용횟수 통계량 출력
            try {
            	WriteTextFile.writeText();
            } catch (Exception e) {}
        }
        
        if (GameSetting.startWithRandomWords) {
        	startingWord = getRandomStartingWord();
        	addMessage(startingWord);										// 첫단어 시작
            ReplyMessage.replyWord = startingWord;

        	// 게임을 입력한 판수만큼 돌리고 이긴횟수를 출력
        	int numOfGamesWon=0;
        	numOfGamesWon = playNumOfGames(numOfGamesWon);		
        	
        	// 결과 출력
        	printEachSidePlayer();
        	if(GameSetting.sideExchangerOn) { System.out.print("오른쪽이 "); }
        	else { System.out.print("왼쪽이 "); }
        	System.out.println("총 " + numOfGamesToPlay + "판 중에 " + numOfGamesWon + "판 이겼습니다.");
        	
        	if(GameSetting.sideExchangerOn) { System.out.print("오른쪽의 "); }
        	else { System.out.print("왼쪽의 "); }
        	float winningRate = (float)numOfGamesWon/(float)numOfGamesToPlay;
    		System.out.println("승률 : " + winningRate + "\n");
        }
    }


    /* main에서 사용하는 메소드들 */
    // 입력한 판수만큼 게임을 플레이
    public int playNumOfGames(int numOfGamesWon) {
    	while (numOfGamesPlayedCounter < numOfGamesToPlay) {    		
    		// 사용한 단어 삭제, 여기서 지우지 않고 단어 찾을때 지우면 
    		WordList.removeWord(ReplyMessage.replyWord, WordList.words);
//    		WordList.removeWordVector1(ReplyMessage.replyWord, WordList.trainedWords1);
//    		WordList.removeWordVector2(ReplyMessage.replyWord, WordList.trainedWords2);
//    		WordList.removeWordVector3(ReplyMessage.replyWord, WordList.trainedWords3);
    		WordList.removeSelfWordVector(ReplyMessage.replyWord, WordList.selfTrainedWords);
    		
        	// 대답 생성
        	addReplyMessage(ReplyMessage.replyWord);        
        	
        	if (turnCounter==0) {
        		resetWordList();                        // 게임이 끝났을 경우 단어장 리셋
        		numOfGamesPlayedCounter++;				// 게임 판수 세는 숫자 증가
        		
        		// 시작단어 랜덤인 경우, 몇판째인지 알려줌
        		if (GameSetting.startWithRandomWords)
        		{	System.out.println(numOfGamesPlayedCounter + "판 째 진행됨 "); }
        		
        		// left가 트루이면 원래 왼쪽이 이긴거, playerSideExchanger가 켜져있다면 오른쪽이 이긴거
        		if(sideExchanger(left)) { numOfGamesWon++; }	// 이겼으면 이긴판수 증가
        		
        		// 아직 해당 단어로 판수를 충족하지 못했다면 플레이해야 경우에만 이 세팅을 함
        		if (numOfGamesPlayedCounter < numOfGamesToPlay) {
            		left = true;					// 처음은 무조건 왼쪽에서 시작하게
            		if (GameSetting.startWithRandomWords)		// 랜덤단어로 시작하게 해놨을 경우
            			{ startingWord = getRandomStartingWord(); }
                	addMessage(startingWord);										
            		ReplyMessage.replyWord=startingWord;
        		}
        	}
    	}
    	return numOfGamesWon;
    }
    
    // 입력한 글자를 프린트
    public void addMessage(String input){
        printMessage(left, input);
        left = !left;
    }

    // 입력한 단어에 대답을 얻음
    public void addReplyMessage(String input) {
        String reply = replyMessage.replyAlgorithm(input);          //input에 대해 replyAlgotirhm에 의해 답을 받음
        printMessage(left, reply);
        left = !left;               // 방향에 대한 진리값을 바꿈
    }
   
    // 전체 단어장 형성
    public void resetWordList() {
    	WordList.setWords();
//    	WordList.setTrainedWords1();
//    	WordList.setTrainedWords2();
//    	WordList.setTrainedWords3();
    	WordList.setSelfTrainedWords();		// 자체학습
    }
    
    // 주고 받는 단어를 프린트하는 메소드
    public void printMessage(boolean left, String message) {
    	// 왼쪽이면 왼쪽에, 오른쪽이면 오른쪽에 프린트
    	if(GameSetting.printMessages) {
            if (left) {
              	System.out.println("[" + message + "]");
            } else if (!left) {
                	System.out.println("\t" + "\t" + "\t" + "[" + message + "]");
            }
    	}
    }
    
    // sideExchangerOn이면, 오른쪽의 승리 횟수를 세고, 
    // 기존 vs 학습의 경우 학습을 오른쪽(후공)으로 바꿈 
    public static boolean sideExchanger(boolean left) {
    	if(GameSetting.sideExchangerOn) { return !left;}
    	return left;
    }

    // 난이도 설정
    public void setDifficulty(boolean[] gameSetting) {
        GameSetting.autoPlay = gameSetting[0];
        GameSetting.easyWordsOn = gameSetting[1];
        GameSetting.killerWordsOn = gameSetting[2];
        GameSetting.safeWordsOn = gameSetting[3];
        GameSetting.normalWordsOn = gameSetting[4];
        GameSetting.excludeWordsNotInList = gameSetting[5];
        GameSetting.startWithLastWord = gameSetting[6];
    }
    
    // 시작할 랜덤단어를 받아옴
    public String getRandomStartingWord() {
    	int random = (int) (Math.random() * Words.words.length);
    	return Words.words[random];
    }
    
    // 각 어떤 데이터셋, 알고리즘으로 했는지 출력
    public void printEachSidePlayer() {
    	switch(IntroActivity.whatToDo) {
    	case 1:
    		System.out.println("기존(왼) vs 기존(오)");
    		break;
    	case 2:
    		if (!GameSetting.sideExchangerOn)
    			{ System.out.println(GameSetting.leftDataNum + "번째(왼) vs 기존(오)"); }
    		if (GameSetting.sideExchangerOn)
    			{ System.out.println("기존(왼) vs " + GameSetting.leftDataNum + "번째(오)"); }
    		break;
    	case 3:
    		System.out.println
    		(GameSetting.leftDataNum + "번째(왼) vs " + GameSetting.rightDataNum + "번째(오)");
    		break;
		case 4:
			System.out.println("트리탐색(왼) vs " + "기존(오)");
			break;
    	}
    }
}