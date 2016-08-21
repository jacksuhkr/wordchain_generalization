package intro;


import java.util.Scanner;
import java.util.TreeSet;

import data_base.*;
import main.GamePlayingActivity;
import static_variables.GameSetting;
import statistics.WriteTextFile;
import words.WordVector;

/**
 * Created by user on 2016-07-31.
 */
public class IntroActivity {
    private static Scanner scanner = new Scanner(System.in);
    public static int whatToDo;
    private static boolean gameDone;
    private static long startTime, endTime;
    private static float timeSpent;
    
    // 자체학습 반복할 횟수
    public static int numOfTraining;
    
    // 차례로 - AI, 쉬운 단어장, 킬러, 안전, 노말, 없는 단어 뺌, 마지막단어로 시작
    public static boolean easyAuto[] = { true, true, false, false, false, false, true };		// easyWord Activate
    
    public static void main(String args[]) {
    	// 건드리지 않을 단어장 생성
        Words.getWords();
//        Weight.getWeights1();
//        Weight.getWeights2();
//        Weight.getWeights3();
//        WordVectors.getWordVecteors1();
//        WordVectors.getWordVecteors2();
//        WordVectors.getWordVecteors3();
//        Maps.getWeightMap1();
//        Maps.getWeightMap2();
//        Maps.getWeightMap3();
        Classified.classifyWords();
//        Classified.classifyWeights(WordVectors.wordVectors1, Classified.trainedWords1);
//        Classified.classifyWeights(WordVectors.wordVectors2, Classified.trainedWords2);
//        Classified.classifyWeights(WordVectors.wordVectors3, Classified.trainedWords3);
//        
//        // 자체학습 단어장
        Weight.getSelfWeights();
        WordVectors.getSelfWordVecteors();
        Maps.getSelfWeightMap();
        Classified.classifyWeights(WordVectors.selfWordVectors, Classified.selfTrainedWords);
        
        // 단어랑 가중치랑 개수가 제대로 맞았나 확인
        System.out.println("단어개수 : " + Words.NUM_OF_WORDS);
        System.out.println("가중치개수 : " + Weight.selfWeights.length);
        
        WordVector test1 = new WordVector("시험", (float) 0.2);
        WordVector test2 = new WordVector("시험", (float) 0.2);
        System.out.println(test1.compareTo(test2));
        System.out.println(test1.equals(test2));
        
        TreeSet<WordVector> testSet = new TreeSet<WordVector>();
        testSet.add(test1);
        System.out.println("안에 들어 있어? " + testSet.contains(test2));
        

        // 통계량을 보기위한 세팅들
        GameSetting.killerWordsPeriod = 5;          // 킬러단어 주기
        GameSetting.resignLimitNumber = 0;          // 항복 선언하는 단어 한계

        // autoPlay만 남김   
        while (!gameDone) {
        	System.out.println("무엇을 하시겠습니까?");
            System.out.println("---------------------------------------------------");
            System.out.println("1. 기존vs기존	2. 학습vs기존	3. 학습vs학습	");
            System.out.println("4. 트리탐색vs기존	5. 자체학습	6. 파일 출력	");
            System.out.println("7. 세팅 바꾸기	8. 끝내기		");
            System.out.println("---------------------------------------------------");
            whatToDo = scanner.nextInt();
            startTime = System.nanoTime();
            
            if(whatToDo==1) {
            	printTitle("기존 vs 기존");
 
            	selectNumOfGames();
            	startGame();
            }
            if(whatToDo==2) {
            	printTitle("학습 vs 기존");

	       	   	System.out.println("사용할 데이터 번호를 입력해주세요.");
	       	   	GameSetting.leftDataNum = scanner.nextInt();			    	    
	       	   	scanner.nextLine();
                
            	selectNumOfGames();
            	startGame();
            }
            if(whatToDo==3) {
            	printTitle("학습 vs 학습");
            	
	       	   	System.out.println("왼쪽에서 쓸 데이터 번호를 입력해주세요.");
	       	   	GameSetting.leftDataNum = scanner.nextInt();	scanner.nextLine();
	       	   	System.out.println("오른쪽에서 쓸 데이터 번호를 입력해주세요.");
	       	   	GameSetting.rightDataNum = scanner.nextInt();	scanner.nextLine();		
	       	   	
            	selectNumOfGames();
            	startGame();
            }
            if(whatToDo==4) {
            	printTitle("트리탐색vs기존");
                
            	selectNumOfGames();
            	startGame();
            }
            
            if(whatToDo==5) {
            	printTitle("자체학습");
            	
            	GameSetting.leftDataNum = 0;	// 자체학습의 단어장 번호
            	selectNumOfGames();
            	
            	// 반복횟수 정하기
            	System.out.println();
	       	   	System.out.println("자체학습을 몇번 반복하실 건가요?");
	       	   	numOfTraining = scanner.nextInt();	scanner.nextLine();	
            	for(int i=1; i<=numOfTraining; i++) {
            		System.out.println("\n");
            		System.out.println(i + "번째 자체학습 시작합니다" +
            					" <단어당 " + GamePlayingActivity.numOfGamesToPlay + "판>");
                    long trainStartTime = System.nanoTime();		// 시간 측정 시작
            		startGame();
            		long trainEndTime = System.nanoTime();			// 끝난 시간
                	float trainTimeSpent = 
                			(float) (trainEndTime - trainStartTime) / (float) 1000000000;
                	System.out.println(i + "번째 학습 걸린시간 : " + trainTimeSpent + "초");
            		writeTrainedWeight();
            		cloneWeight(i);
            	}
            }
            if(whatToDo==6) {
            	printTitle("파일출력");
            	
            	try { WriteTextFile.writeText(); } 
            	catch (Exception e) { System.out.println("파일쓰기 실패 ㅜㅜ"); }
            }
            if(whatToDo==7) {
            	printTitle("세팅 바꾸기");
                
                System.out.println("어떤 것의 세팅을 바꾸실 건가요?");
	            System.out.println("1. 승패방향  	2.시작단어");
                int selectSetting = scanner.nextInt();	scanner.nextLine();	System.out.println();
                
            switch(selectSetting) {
	            case 1:
	              	System.out.println("어느쪽의 입장으로 바꾸실 건가요?");
	                System.out.println("1. 왼쪽	2. 오른쪽	");
	    	        int whichSide = scanner.nextInt();	scanner.nextLine();	System.out.println();
	    	   	    
	    	   	    if(whichSide==1) { 
	    	   	    	GameSetting.sideExchangerOn = false;
	    	    	    System.out.println("승패방향을 왼쪽을 선택하셨습니다. \n");
	   	    	    }
	   	    	    if(whichSide==2) { 
	   	    	    	GameSetting.sideExchangerOn = true; 
	   	    	    	System.out.println("승패방향을 오른쪽을 선택하셨습니다. \n");
	   	    	    }	
	   	    	    break;
               case 2: 
	                System.out.println("시작단어를 어떻게 바꾸실 건가요?");
	    	        System.out.println("1. 가나다순	2. 랜덤	");
	    	   	    int startingWord = scanner.nextInt();	scanner.nextLine();	System.out.println();

	    	   	    if(startingWord==1) { 
	   	    	    	GameSetting.startWithRandomWords = false;
	    	    	    System.out.println("가나다순을 선택하셨습니다. \n");
	   	    	    }
	   	    	    if(startingWord==2) { 
	   	    	    	GameSetting.startWithRandomWords = true; 
    	    	    	System.out.println("랜덤을 선택하셨습니다. \n");	    	    	    }
                	}
            }
            if(whatToDo==8) {
            	gameDone=true;
            }
        	System.out.println("\n");
        	
        	endTime = System.nanoTime();
        	timeSpent = (float) (endTime - startTime) / (float) 1000000000;
        	System.out.println("걸린시간 : " + timeSpent + "초");
        }
    }

    // 각 메뉴 타이틀을 프린트
    private static void printTitle(String title) {
        System.out.println("-----------------------------------------");
        System.out.println("                    " + title);
        System.out.println("-----------------------------------------"); 
    }
    
    // 지우고 더하지 않아 단어번호가 일정한, 검색용 단어장 형성

    // 게임 판수를 선택
    private static void selectNumOfGames() {
    	if (!GameSetting.startWithRandomWords)
    		{ System.out.println("단어 하나당 게임을 몇판 돌리실 생각이신가요?"); }
    	else if (GameSetting.startWithRandomWords)
    		{ System.out.println("게임을 몇판 돌리실 생각이신가요?"); }
    	
	     GamePlayingActivity.numOfGamesToPlay = scanner.nextInt();
	     System.out.println(GamePlayingActivity.numOfGamesToPlay + "판을 선택하셨습니다.");
	     scanner.nextLine();
    }
    
    // 게임세팅값을 넣어서 GamePlayingActivity의 startGame메소드를 실행
    private static void startGame() {
        GamePlayingActivity autoPlay = new GamePlayingActivity();
        autoPlay.startGame(easyAuto);
    }
    
	// 자체학습을 위한 가중치 텍스트파일 출력
	private static void writeTrainedWeight() {
	    try {
	    	WriteTextFile.writeSelfWeightText("data_files/selfWeight.txt");
	    } catch (Exception e) {}
	}
    
    // 학습단어장 기록 남기기
	private static void cloneWeight(int fileNum) {
		String cloneFileName = 
				"data_files/output/selfTrainedWeight" + fileNum + ".txt";
		try {
			WriteTextFile.writeText(cloneFileName);
		} catch (Exception e) {}
	}
	

}
