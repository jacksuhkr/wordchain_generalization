package statistics;

import java.util.ArrayList;

import data_base.Words;

/**
 * Created by user on 2016-08-02.
 **/

// ���� �ʿ����̴°� ����

// �ܾ ���� ��跮�� �������� Ŭ����. �ϴ��� ����ġ�� ���Ƚ��
public class NumbersForWordList {
    // ����� �ܾ���� ��� ����
    public static ArrayList<Integer> numbersOfUsed = new ArrayList<Integer>();
    public static ArrayList<Integer> numbersOfWeight = new ArrayList<Integer>();

    // �ܾ��� ���Ƚ�� ������Ű��
    public static void addNumberOfTimesWordUsed(String wordUsed, ArrayList<Integer> numListOfWordUsed) {
        for(int i = 0; i<Words.NUM_OF_WORDS; i++) {
            if (wordUsed.equals(Words.words[i]))	             				// ������ �ܾ�� ��ġ�ϴ� �ܾ��ȣ ����
            { int newNum = numListOfWordUsed.get(i) + 1;                        // ���� �ִ� Ƚ���� 1 �߰�
                numListOfWordUsed.set(i, newNum);  }                            // 1 �߰��� �� ����
        }
    }
}
