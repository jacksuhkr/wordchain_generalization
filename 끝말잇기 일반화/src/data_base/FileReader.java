package data_base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader {

	// ������ �о ArrayList�� ä��
	public static void getWords(String wordFile, ArrayList<String> words) {
        words.clear();          // �ܾ��� �����ϱ� ���� �ȿ� �ִ��� ����
        try {
        	BufferedReader in = new BufferedReader
        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
            String word;                                 //  �ܾ����� ä�ﶧ �� �Ű�����
            while (( word = in.readLine()) != null)     // ������(word)�� ������� ��������
            { words.add(word); }                         // �ش� ���� �ܾ �ܾ��Ʈ�� ����
            in.close();                                  // ���� ����
        } catch(IOException e) { }
    }
	
	// ���� �о String[]�� ä��
	public static void getWords(String wordFile, String[] words) {
        try {
        	BufferedReader in = new BufferedReader
        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
        	int wordNum=0;
            String word;                                 //  �ܾ����� ä�ﶧ �� �Ű�����
            while (( word = in.readLine()) != null)     // ������(word)�� ������� ��������
            { words[wordNum] = word; wordNum++; }                         // �ش� ���� �ܾ �ܾ��Ʈ�� ����
            in.close();                                  // ���� ����
        } catch(IOException e) { }
    }
	
	// ���� �о float[]�� ä��
	public static void getWeights(String weightFile, float[] weights) {
        try {
        	BufferedReader in = new BufferedReader
        			(new InputStreamReader(new FileInputStream(weightFile),"EUC-KR"));
        	int wordNum=0;
        	Float weight;
            String word;                                 //  �ܾ����� ä�ﶧ �� �Ű�����
            while (( word = in.readLine()) != null) {     // ������(word)�� ������� ��������
            	weight = Float.parseFloat(word);
            	weights[wordNum] = weight; 
            	wordNum++; 
            }                         // �ش� ���� �ܾ �ܾ��Ʈ�� ����
            in.close();                                  // ���� ����
        } catch(IOException e) { }
    }

	public static int getNumOfWords(String wordFile) {
		ArrayList<String> instance = new ArrayList<String>();
        try {
        	BufferedReader in = new BufferedReader
        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
            String word;                                 //  �ܾ����� ä�ﶧ �� �Ű�����
            while (( word = in.readLine()) != null)     // ������(word)�� ������� ��������
            { instance.add(word); }                         // �ش� ���� �ܾ �ܾ��Ʈ�� ����
            in.close();                                  // ���� ����
        } catch(IOException e) { }
        return instance.size();
	}
}
