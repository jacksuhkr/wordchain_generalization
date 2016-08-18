package data_base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader {

	// 파일을 읽어서 ArrayList를 채움
	public static void getWords(String wordFile, ArrayList<String> words) {
        words.clear();          // 단어장 리셋하기 전에 안에 있던것 포맷
        try {
        	BufferedReader in = new BufferedReader
        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
            String word;                                 //  단어장을 채울때 쓸 매개변수
            while (( word = in.readLine()) != null)     // 다음줄(word)이 비어있지 않을동안
            { words.add(word); }                         // 해당 줄의 단어를 단어리스트에 넣음
            in.close();                                  // 파일 저장
        } catch(IOException e) { }
    }
	
	// 파일 읽어서 String[]를 채움
	public static void getWords(String wordFile, String[] words) {
        try {
        	BufferedReader in = new BufferedReader
        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
        	int wordNum=0;
            String word;                                 //  단어장을 채울때 쓸 매개변수
            while (( word = in.readLine()) != null)     // 다음줄(word)이 비어있지 않을동안
            { words[wordNum] = word; wordNum++; }                         // 해당 줄의 단어를 단어리스트에 넣음
            in.close();                                  // 파일 저장
        } catch(IOException e) { }
    }
	
	// 파일 읽어서 float[]를 채움
	public static void getWeights(String weightFile, float[] weights) {
        try {
        	BufferedReader in = new BufferedReader
        			(new InputStreamReader(new FileInputStream(weightFile),"EUC-KR"));
        	int wordNum=0;
        	Float weight;
            String word;                                 //  단어장을 채울때 쓸 매개변수
            while (( word = in.readLine()) != null) {     // 다음줄(word)이 비어있지 않을동안
            	weight = Float.parseFloat(word);
            	weights[wordNum] = weight; 
            	wordNum++; 
            }                         // 해당 줄의 단어를 단어리스트에 넣음
            in.close();                                  // 파일 저장
        } catch(IOException e) { }
    }

	public static int getNumOfWords(String wordFile) {
		ArrayList<String> instance = new ArrayList<String>();
        try {
        	BufferedReader in = new BufferedReader
        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
            String word;                                 //  단어장을 채울때 쓸 매개변수
            while (( word = in.readLine()) != null)     // 다음줄(word)이 비어있지 않을동안
            { instance.add(word); }                         // 해당 줄의 단어를 단어리스트에 넣음
            in.close();                                  // 파일 저장
        } catch(IOException e) { }
        return instance.size();
	}
}
