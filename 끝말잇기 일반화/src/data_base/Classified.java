package data_base;

import java.util.ArrayList;
import java.util.TreeSet;

import words.WordVector;

public class Classified {
	public static final 
			ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>();
	public static final
			ArrayList<TreeSet<WordVector>> trainedWords1 = new ArrayList<TreeSet<WordVector>>();
	public static final
			ArrayList<TreeSet<WordVector>> trainedWords2 = new ArrayList<TreeSet<WordVector>>();
	public static final
			ArrayList<TreeSet<WordVector>> trainedWords3 = new ArrayList<TreeSet<WordVector>>();
	
	// ��ü �н��� ���� �ܾ���
	public static final
		ArrayList<TreeSet<WordVector>> selfTrainedWords = new ArrayList<TreeSet<WordVector>>();
	
	// ArrayList<String>�� �о ù���ڰ� ���� �ܾ�鳢�� �з�
    public static void classifyWords() {
        // firstLetters.length��ŭ ArrayList<ArrayList<String>>�� �ڸ��� ��������, ��, �ʱ�ȭ
        words.clear();
        for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
            ArrayList<String> emptyArray = new ArrayList<String>();     // �ʱ�ȭ�� �Ű��μ� ��̸���Ʈ ����
            emptyArray.add("");                 // ""�� �־ emptyArray�� �ʱ�ȭ
            words.add(emptyArray);             // emptyArray�� output�� ��ü�� �߰�
            words.get(i).remove(0);            // ""�� ����
        }

        // ù������ ���ڹ�ȣ�� ���ؼ�, �� �������ٰ� �ܾ �߰���.
        for(int i=0; i<Words.words.length; i++) {
            int firstLetterNum = FirstLetters.getFirstLetterNumber(Words.words[i]);        // �ܾ� ù������ ���ڹ�ȣ
            System.out.println(Words.words[i] + firstLetterNum);
            words.get(firstLetterNum).add(Words.words[i]);                   // �ش� �ּҿ� �ܾ� �߰�
        }
    }
    
	// WordVector[]�� �о ù���ڰ� ���� �ܾ�鳢�� �з�, ����ġ�� ū ������ ����
    public static void classifyWeights(WordVector[] WordVectors, ArrayList<TreeSet<WordVector>> output) {
        // firstLetters.length��ŭ ArrayList<TreeSet<WordVector>>�� �ڸ��� ��������, ��, �ʱ�ȭ
        output.clear();
        for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
            TreeSet<WordVector> emptySet = new TreeSet<WordVector>();     // �ʱ�ȭ�� �Ű��μ� ��̸���Ʈ ����
            emptySet.add(new WordVector("", 0));                 
            output.add(emptySet);             // emptySet�� output�� ��ü�� �߰�
            output.get(i).remove(new WordVector("", 0));           
        }

        // ù������ ���ڹ�ȣ�� ���ؼ�, �� �������ٰ� WordVector�� �߰���.
        for(int i=0; i<WordVectors.length; i++) {
            int firstLetterNum = FirstLetters.getFirstLetterNumber(WordVectors[i].word);        // �ܾ� ù������ ���ڹ�ȣ
            output.get(firstLetterNum).add(WordVectors[i]);                   // �ش� �ּҿ� �ܾ� �߰�
        }
    }

}
