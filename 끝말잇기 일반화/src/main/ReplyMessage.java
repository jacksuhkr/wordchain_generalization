package main;

import intro.IntroActivity;

public class ReplyMessage extends Message {
    public static String replyWord;             // static으로 안 하면 사라져서 끝말이었는지 확인할때 에러가뜸

    // 생성자
    public ReplyMessage(boolean left , String message) {
        super(left, message);
    }

    // 대답을 형성하는 알고리즘
    public String replyAlgorithm(String input) {
        // 대답생성
        try {
            replyWord = WordFinder.findWord(input, IntroActivity.whatToDo);          // 위에서 얻은 끝글자에 이어지는 말을 받아옴
            return replyWord;
        } catch (Exception e) {
            return "예외 발생";
        }
    }
}
