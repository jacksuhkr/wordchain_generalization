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
    
    // ��ü�н� �ݺ��� Ƚ��
    public static int numOfTraining;
    
    // ���ʷ� - AI, ���� �ܾ���, ų��, ����, �븻, ���� �ܾ� ��, �������ܾ�� ����
    public static boolean easyAuto[] = { true, true, false, false, false, false, true };		// easyWord Activate
    
    public static void main(String args[]) {
    	// �ǵ帮�� ���� �ܾ��� ����
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
//        // ��ü�н� �ܾ���
        Weight.getSelfWeights();
        WordVectors.getSelfWordVecteors();
        Maps.getSelfWeightMap();
        Classified.classifyWeights(WordVectors.selfWordVectors, Classified.selfTrainedWords);
        
        // �ܾ�� ����ġ�� ������ ����� �¾ҳ� Ȯ��
        System.out.println("�ܾ�� : " + Words.NUM_OF_WORDS);
        System.out.println("����ġ���� : " + Weight.selfWeights.length);
        
        WordVector test1 = new WordVector("����", (float) 0.2);
        WordVector test2 = new WordVector("����", (float) 0.2);
        System.out.println(test1.compareTo(test2));
        System.out.println(test1.equals(test2));
        
        TreeSet<WordVector> testSet = new TreeSet<WordVector>();
        testSet.add(test1);
        System.out.println("�ȿ� ��� �־�? " + testSet.contains(test2));
        

        // ��跮�� �������� ���õ�
        GameSetting.killerWordsPeriod = 5;          // ų���ܾ� �ֱ�
        GameSetting.resignLimitNumber = 0;          // �׺� �����ϴ� �ܾ� �Ѱ�

        // autoPlay�� ����   
        while (!gameDone) {
        	System.out.println("������ �Ͻðڽ��ϱ�?");
            System.out.println("---------------------------------------------------");
            System.out.println("1. ����vs����	2. �н�vs����	3. �н�vs�н�	");
            System.out.println("4. Ʈ��Ž��vs����	5. ��ü�н�	6. ���� ���	");
            System.out.println("7. ���� �ٲٱ�	8. ������		");
            System.out.println("---------------------------------------------------");
            whatToDo = scanner.nextInt();
            startTime = System.nanoTime();
            
            if(whatToDo==1) {
            	printTitle("���� vs ����");
 
            	selectNumOfGames();
            	startGame();
            }
            if(whatToDo==2) {
            	printTitle("�н� vs ����");

	       	   	System.out.println("����� ������ ��ȣ�� �Է����ּ���.");
	       	   	GameSetting.leftDataNum = scanner.nextInt();			    	    
	       	   	scanner.nextLine();
                
            	selectNumOfGames();
            	startGame();
            }
            if(whatToDo==3) {
            	printTitle("�н� vs �н�");
            	
	       	   	System.out.println("���ʿ��� �� ������ ��ȣ�� �Է����ּ���.");
	       	   	GameSetting.leftDataNum = scanner.nextInt();	scanner.nextLine();
	       	   	System.out.println("�����ʿ��� �� ������ ��ȣ�� �Է����ּ���.");
	       	   	GameSetting.rightDataNum = scanner.nextInt();	scanner.nextLine();		
	       	   	
            	selectNumOfGames();
            	startGame();
            }
            if(whatToDo==4) {
            	printTitle("Ʈ��Ž��vs����");
                
            	selectNumOfGames();
            	startGame();
            }
            
            if(whatToDo==5) {
            	printTitle("��ü�н�");
            	
            	GameSetting.leftDataNum = 0;	// ��ü�н��� �ܾ��� ��ȣ
            	selectNumOfGames();
            	
            	// �ݺ�Ƚ�� ���ϱ�
            	System.out.println();
	       	   	System.out.println("��ü�н��� ��� �ݺ��Ͻ� �ǰ���?");
	       	   	numOfTraining = scanner.nextInt();	scanner.nextLine();	
            	for(int i=1; i<=numOfTraining; i++) {
            		System.out.println("\n");
            		System.out.println(i + "��° ��ü�н� �����մϴ�" +
            					" <�ܾ�� " + GamePlayingActivity.numOfGamesToPlay + "��>");
                    long trainStartTime = System.nanoTime();		// �ð� ���� ����
            		startGame();
            		long trainEndTime = System.nanoTime();			// ���� �ð�
                	float trainTimeSpent = 
                			(float) (trainEndTime - trainStartTime) / (float) 1000000000;
                	System.out.println(i + "��° �н� �ɸ��ð� : " + trainTimeSpent + "��");
            		writeTrainedWeight();
            		cloneWeight(i);
            	}
            }
            if(whatToDo==6) {
            	printTitle("�������");
            	
            	try { WriteTextFile.writeText(); } 
            	catch (Exception e) { System.out.println("���Ͼ��� ���� �̤�"); }
            }
            if(whatToDo==7) {
            	printTitle("���� �ٲٱ�");
                
                System.out.println("� ���� ������ �ٲٽ� �ǰ���?");
	            System.out.println("1. ���й���  	2.���۴ܾ�");
                int selectSetting = scanner.nextInt();	scanner.nextLine();	System.out.println();
                
            switch(selectSetting) {
	            case 1:
	              	System.out.println("������� �������� �ٲٽ� �ǰ���?");
	                System.out.println("1. ����	2. ������	");
	    	        int whichSide = scanner.nextInt();	scanner.nextLine();	System.out.println();
	    	   	    
	    	   	    if(whichSide==1) { 
	    	   	    	GameSetting.sideExchangerOn = false;
	    	    	    System.out.println("���й����� ������ �����ϼ̽��ϴ�. \n");
	   	    	    }
	   	    	    if(whichSide==2) { 
	   	    	    	GameSetting.sideExchangerOn = true; 
	   	    	    	System.out.println("���й����� �������� �����ϼ̽��ϴ�. \n");
	   	    	    }	
	   	    	    break;
               case 2: 
	                System.out.println("���۴ܾ ��� �ٲٽ� �ǰ���?");
	    	        System.out.println("1. �����ټ�	2. ����	");
	    	   	    int startingWord = scanner.nextInt();	scanner.nextLine();	System.out.println();

	    	   	    if(startingWord==1) { 
	   	    	    	GameSetting.startWithRandomWords = false;
	    	    	    System.out.println("�����ټ��� �����ϼ̽��ϴ�. \n");
	   	    	    }
	   	    	    if(startingWord==2) { 
	   	    	    	GameSetting.startWithRandomWords = true; 
    	    	    	System.out.println("������ �����ϼ̽��ϴ�. \n");	    	    	    }
                	}
            }
            if(whatToDo==8) {
            	gameDone=true;
            }
        	System.out.println("\n");
        	
        	endTime = System.nanoTime();
        	timeSpent = (float) (endTime - startTime) / (float) 1000000000;
        	System.out.println("�ɸ��ð� : " + timeSpent + "��");
        }
    }

    // �� �޴� Ÿ��Ʋ�� ����Ʈ
    private static void printTitle(String title) {
        System.out.println("-----------------------------------------");
        System.out.println("                    " + title);
        System.out.println("-----------------------------------------"); 
    }
    
    // ����� ������ �ʾ� �ܾ��ȣ�� ������, �˻��� �ܾ��� ����

    // ���� �Ǽ��� ����
    private static void selectNumOfGames() {
    	if (!GameSetting.startWithRandomWords)
    		{ System.out.println("�ܾ� �ϳ��� ������ ���� ������ �����̽Ű���?"); }
    	else if (GameSetting.startWithRandomWords)
    		{ System.out.println("������ ���� ������ �����̽Ű���?"); }
    	
	     GamePlayingActivity.numOfGamesToPlay = scanner.nextInt();
	     System.out.println(GamePlayingActivity.numOfGamesToPlay + "���� �����ϼ̽��ϴ�.");
	     scanner.nextLine();
    }
    
    // ���Ӽ��ð��� �־ GamePlayingActivity�� startGame�޼ҵ带 ����
    private static void startGame() {
        GamePlayingActivity autoPlay = new GamePlayingActivity();
        autoPlay.startGame(easyAuto);
    }
    
	// ��ü�н��� ���� ����ġ �ؽ�Ʈ���� ���
	private static void writeTrainedWeight() {
	    try {
	    	WriteTextFile.writeSelfWeightText("data_files/selfWeight.txt");
	    } catch (Exception e) {}
	}
    
    // �н��ܾ��� ��� �����
	private static void cloneWeight(int fileNum) {
		String cloneFileName = 
				"data_files/output/selfTrainedWeight" + fileNum + ".txt";
		try {
			WriteTextFile.writeText(cloneFileName);
		} catch (Exception e) {}
	}
	

}
