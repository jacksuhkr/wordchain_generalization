package main;

import intro.IntroActivity;

public class ReplyMessage extends Message {
    public static String replyWord;             // static���� �� �ϸ� ������� �����̾����� Ȯ���Ҷ� ��������

    // ������
    public ReplyMessage(boolean left , String message) {
        super(left, message);
    }

    // ����� �����ϴ� �˰���
    public String replyAlgorithm(String input) {
        // ������
        try {
            replyWord = WordFinder.findWord(input, IntroActivity.whatToDo);          // ������ ���� �����ڿ� �̾����� ���� �޾ƿ�
            return replyWord;
        } catch (Exception e) {
            return "���� �߻�";
        }
    }
}
