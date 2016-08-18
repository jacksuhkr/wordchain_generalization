package statistics;

import java.util.ArrayList;

import data_base.Words;

/**
 * Created by user on 2016-08-02.
 **/

// 여기 필요없어보이는거 날림

// 단어에 대한 통계량을 가져오는 클래스. 일단은 가중치랑 사용횟수
public class NumbersForWordList {
    // 사용할 단어사전 어레이 선언
    public static ArrayList<Integer> numbersOfUsed = new ArrayList<Integer>();
    public static ArrayList<Integer> numbersOfWeight = new ArrayList<Integer>();

    // 단어의 사용횟수 증가시키기
    public static void addNumberOfTimesWordUsed(String wordUsed, ArrayList<Integer> numListOfWordUsed) {
        for(int i = 0; i<Words.NUM_OF_WORDS; i++) {
            if (wordUsed.equals(Words.words[i]))	             				// 현재의 단어랑 일치하는 단어번호 얻음
            { int newNum = numListOfWordUsed.get(i) + 1;                        // 원래 있던 횟수에 1 추가
                numListOfWordUsed.set(i, newNum);  }                            // 1 추가한 값 대입
        }
    }
}
