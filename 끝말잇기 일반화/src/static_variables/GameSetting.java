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
    public static boolean killerWordsEveryNWords = false;       // �ѹ�ܾ��ֱ� ��� �ѱ�/����
    public static boolean sideExchangerOn = false;
    public static boolean startWithRandomWords = false;			// ���۴ܾ� ����?
//    public static boolean dataSetNowUsing[];			// �̰ɷ� ����ϰ� �ִ� �����ͼ¸� �ܾ� �����ϱ�?
    
    // �߰� ���� �μ�
    public static boolean printMessages = false;
    public static boolean printStartingWord = false;
    public static boolean printNumOfWords = true;

    public static int killerWordsPeriod;                // �ѹ�ܾ� �ֱ�
    public static int resignLimitNumber;                //
    public static Integer leftDataNum, rightDataNum;	// �н� vs �н� �����ͼ� ����
    public static int numOfTreesToSearch = 100;
}	
