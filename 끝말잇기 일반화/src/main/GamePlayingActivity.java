package main;

import data_base.Maps;
import data_base.WordVectors;
import data_base.Words;
import intro.IntroActivity;
import static_variables.GameSetting;
import statistics.WriteTextFile;
import words.WordList;

public class GamePlayingActivity {
    public static boolean left = true;                          // ���� �����ʿ� ���� ������
    public static int turnCounter = 0;         // ���ݱ��� ����� �� ������ ī��Ʈ
    public static ReplyMessage replyMessage = new ReplyMessage(true, null);   //replyAlgorithm�� ���� ���� ReplyMessage ��ü ����
    public static String startingWord;
    public static int numOfGamesToPlay;
    public int numOfGamesPlayedCounter;
    public static int[] numOfGamesWon = new int[Words.NUM_OF_WORDS];		// �ܾ �̱�Ƚ��
    
    public void startGame(boolean[] gameSetting) {
    	// ���̵� ����
        setDifficulty(gameSetting);

        // ������ ���۵� �� �ʱ�ȭ
        resetWordList();
        turnCounter=0;
        left=true;
        
        if (!GameSetting.startWithRandomWords) {
        	for (int i=0; i<Words.NUM_OF_WORDS; i++) {
            	// ù�ܾ� ����
            	left = true;					// ó���� ������ ���ʿ��� �����ϰ�
            	startingWord = Words.words[i];				//���۴ܾ� ����
            	if(GameSetting.printStartingWord) {
            		System.out.println("\nù �ܾ� " + startingWord + ", ������ �����մϴ�.");	// �����ܾ� �ϴ��� ���
            	}
            	addMessage(startingWord);										// ù�ܾ� ����
                ReplyMessage.replyWord = startingWord;							// ��䵵 �װɷ� ����
                
                // �����Ȳ ���
                if (GameSetting.printNumOfWords) {
                	if ((i+1)%100==0) 
                	{ System.out.println((i+1) + "�� �ܾ� �����"); }	// ���° �ܾ� �ϴ��� ���
                }
                
                // ���۴ܾ �ٲ�����Ƿ�, �����Ǽ�, �����̱�� �ʱ�ȭ
            	numOfGamesPlayedCounter = 0;								// ���� �Ǽ� �ʱ�ȭ
            	numOfGamesWon[i]=0;											// ���� �̱�� �ʱ�ȭ
            	
            	// �䱸�ϴ� �Ǽ���ŭ �ش� �ܾ ù�ܾ�� �÷����ϰ� �̱���� ���
            	numOfGamesWon[i] = playNumOfGames(numOfGamesWon[i]);
            }
        	printEachSidePlayer();
        	
            // ù�ܾ�, �̱�Ƚ��, ���Ƚ�� ��跮 ���
            try {
            	WriteTextFile.writeText();
            } catch (Exception e) {}
        }
        
        if (GameSetting.startWithRandomWords) {
        	startingWord = getRandomStartingWord();
        	addMessage(startingWord);										// ù�ܾ� ����
            ReplyMessage.replyWord = startingWord;

        	// ������ �Է��� �Ǽ���ŭ ������ �̱�Ƚ���� ���
        	int numOfGamesWon=0;
        	numOfGamesWon = playNumOfGames(numOfGamesWon);		
        	
        	// ��� ���
        	printEachSidePlayer();
        	if(GameSetting.sideExchangerOn) { System.out.print("�������� "); }
        	else { System.out.print("������ "); }
        	System.out.println("�� " + numOfGamesToPlay + "�� �߿� " + numOfGamesWon + "�� �̰���ϴ�.");
        	
        	if(GameSetting.sideExchangerOn) { System.out.print("�������� "); }
        	else { System.out.print("������ "); }
        	float winningRate = (float)numOfGamesWon/(float)numOfGamesToPlay;
    		System.out.println("�·� : " + winningRate + "\n");
        }
    }


    /* main���� ����ϴ� �޼ҵ�� */
    // �Է��� �Ǽ���ŭ ������ �÷���
    public int playNumOfGames(int numOfGamesWon) {
    	while (numOfGamesPlayedCounter < numOfGamesToPlay) {    		
    		// ����� �ܾ� ����, ���⼭ ������ �ʰ� �ܾ� ã���� ����� 
    		WordList.removeWord(ReplyMessage.replyWord, WordList.words);
//    		WordList.removeWordVector1(ReplyMessage.replyWord, WordList.trainedWords1);
//    		WordList.removeWordVector2(ReplyMessage.replyWord, WordList.trainedWords2);
//    		WordList.removeWordVector3(ReplyMessage.replyWord, WordList.trainedWords3);
    		WordList.removeSelfWordVector(ReplyMessage.replyWord, WordList.selfTrainedWords);
    		
        	// ��� ����
        	addReplyMessage(ReplyMessage.replyWord);        
        	
        	if (turnCounter==0) {
        		resetWordList();                        // ������ ������ ��� �ܾ��� ����
        		numOfGamesPlayedCounter++;				// ���� �Ǽ� ���� ���� ����
        		
        		// ���۴ܾ� ������ ���, ����°���� �˷���
        		if (GameSetting.startWithRandomWords)
        		{	System.out.println(numOfGamesPlayedCounter + "�� ° ����� "); }
        		
        		// left�� Ʈ���̸� ���� ������ �̱��, playerSideExchanger�� �����ִٸ� �������� �̱��
        		if(sideExchanger(left)) { numOfGamesWon++; }	// �̰����� �̱��Ǽ� ����
        		
        		// ���� �ش� �ܾ�� �Ǽ��� �������� ���ߴٸ� �÷����ؾ� ��쿡�� �� ������ ��
        		if (numOfGamesPlayedCounter < numOfGamesToPlay) {
            		left = true;					// ó���� ������ ���ʿ��� �����ϰ�
            		if (GameSetting.startWithRandomWords)		// �����ܾ�� �����ϰ� �س��� ���
            			{ startingWord = getRandomStartingWord(); }
                	addMessage(startingWord);										
            		ReplyMessage.replyWord=startingWord;
        		}
        	}
    	}
    	return numOfGamesWon;
    }
    
    // �Է��� ���ڸ� ����Ʈ
    public void addMessage(String input){
        printMessage(left, input);
        left = !left;
    }

    // �Է��� �ܾ ����� ����
    public void addReplyMessage(String input) {
        String reply = replyMessage.replyAlgorithm(input);          //input�� ���� replyAlgotirhm�� ���� ���� ����
        printMessage(left, reply);
        left = !left;               // ���⿡ ���� �������� �ٲ�
    }
   
    // ��ü �ܾ��� ����
    public void resetWordList() {
    	WordList.setWords();
//    	WordList.setTrainedWords1();
//    	WordList.setTrainedWords2();
//    	WordList.setTrainedWords3();
    	WordList.setSelfTrainedWords();		// ��ü�н�
    }
    
    // �ְ� �޴� �ܾ ����Ʈ�ϴ� �޼ҵ�
    public void printMessage(boolean left, String message) {
    	// �����̸� ���ʿ�, �������̸� �����ʿ� ����Ʈ
    	if(GameSetting.printMessages) {
            if (left) {
              	System.out.println("[" + message + "]");
            } else if (!left) {
                	System.out.println("\t" + "\t" + "\t" + "[" + message + "]");
            }
    	}
    }
    
    // sideExchangerOn�̸�, �������� �¸� Ƚ���� ����, 
    // ���� vs �н��� ��� �н��� ������(�İ�)���� �ٲ� 
    public static boolean sideExchanger(boolean left) {
    	if(GameSetting.sideExchangerOn) { return !left;}
    	return left;
    }

    // ���̵� ����
    public void setDifficulty(boolean[] gameSetting) {
        GameSetting.autoPlay = gameSetting[0];
        GameSetting.easyWordsOn = gameSetting[1];
        GameSetting.killerWordsOn = gameSetting[2];
        GameSetting.safeWordsOn = gameSetting[3];
        GameSetting.normalWordsOn = gameSetting[4];
        GameSetting.excludeWordsNotInList = gameSetting[5];
        GameSetting.startWithLastWord = gameSetting[6];
    }
    
    // ������ �����ܾ �޾ƿ�
    public String getRandomStartingWord() {
    	int random = (int) (Math.random() * Words.words.length);
    	return Words.words[random];
    }
    
    // �� � �����ͼ�, �˰������� �ߴ��� ���
    public void printEachSidePlayer() {
    	switch(IntroActivity.whatToDo) {
    	case 1:
    		System.out.println("����(��) vs ����(��)");
    		break;
    	case 2:
    		if (!GameSetting.sideExchangerOn)
    			{ System.out.println(GameSetting.leftDataNum + "��°(��) vs ����(��)"); }
    		if (GameSetting.sideExchangerOn)
    			{ System.out.println("����(��) vs " + GameSetting.leftDataNum + "��°(��)"); }
    		break;
    	case 3:
    		System.out.println
    		(GameSetting.leftDataNum + "��°(��) vs " + GameSetting.rightDataNum + "��°(��)");
    		break;
		case 4:
			System.out.println("Ʈ��Ž��(��) vs " + "����(��)");
			break;
    	}
    }
}