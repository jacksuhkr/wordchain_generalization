package static_variables;

/**
 * Created by user on 2016-07-30.
 */
public class GameSetting {
    public static boolean autoPlay;
    public static boolean easyWordsOn;
    public static boolean killerWordsOn;
    public static boolean safeWordsOn;
    public static boolean normalWordsOn;
    public static boolean excludeWordsNotInList;
    public static boolean startWithLastWord;
    public static boolean killerWordsEveryNWords = false;       // 한방단어주기 기능 켜기/끄기
    public static boolean sideExchangerOn = false;
    public static boolean startWithRandomWords = false;			// 시작단어 랜덤?
//    public static boolean dataSetNowUsing[];			// 이걸로 사용하고 있는 데이터셋만 단어 삭제하기?
    
    // 중간 과정 인쇄
    public static boolean printMessages = false;
    public static boolean printStartingWord = false;
    public static boolean printNumOfWords = true;

    public static int killerWordsPeriod;                // 한방단어 주기
    public static int resignLimitNumber;                //
    public static Integer leftDataNum, rightDataNum;	// 학습 vs 학습 데이터셋 선택
    public static int numOfTreesToSearch = 100;
}	
