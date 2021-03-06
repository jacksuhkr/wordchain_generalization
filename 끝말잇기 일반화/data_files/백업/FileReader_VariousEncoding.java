package 백업;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader_VariousEncoding {

	// 파일을 읽어서 ArrayList를 채움
	public static void getWords(String wordFile, ArrayList<String> words) {
        words.clear();          // 단어장 리셋하기 전에 안에 있던것 포맷
        try {
        	BufferedReader in = new BufferedReader
//        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
//        			(new InputStreamReader(new FileInputStream(wordFile),"UTF-8"));
        			(new InputStreamReader(new FileInputStream(wordFile),"CP949"));
            String word;                                 //  단어장을 채울때 쓸 매개변수
            while (( word = in.readLine()) != null)     // 다음줄(word)이 비어있지 않을동안
            { if(removeNonKorean(word)) { words.add(word); } }                         // 해당 줄의 단어를 단어리스트에 넣음
            in.close();                                  // 파일 저장
        } catch(IOException e) { }
    }
	
	// 파일 읽어서 String[]를 채움
	public static void getWords(String wordFile, String[] words) {
        try {
        	BufferedReader in = new BufferedReader
//        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
//        			(new InputStreamReader(new FileInputStream(wordFile),"UTF-8"));
        			(new InputStreamReader(new FileInputStream(wordFile),"CP949"));
        	int wordNum=0;
            String word;                                 //  단어장을 채울때 쓸 매개변수
            while (( word = in.readLine()) != null)     // 다음줄(word)이 비어있지 않을동안
            { if(removeNonKorean(word)) { words[wordNum] = word; wordNum++; } }                         // 해당 줄의 단어를 단어리스트에 넣음
            in.close();                                  // 파일 저장
        } catch(IOException e) { }
    }
	
	// 파일 읽어서 float[]를 채움
	public static void getWeights(String weightFile, float[] weights) {
        try {
        	BufferedReader in = new BufferedReader
//        			(new InputStreamReader(new FileInputStream(weightFile),"EUC-KR"));
//        			(new InputStreamReader(new FileInputStream(weightFile),"UTF-8"));
        			(new InputStreamReader(new FileInputStream(weightFile),"CP949"));
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
//        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
//        			(new InputStreamReader(new FileInputStream(wordFile),"UTF-8"));
        			(new InputStreamReader(new FileInputStream(wordFile),"CP949"));
            String word;                                 //  단어장을 채울때 쓸 매개변수
            while (( word = in.readLine()) != null)     // 다음줄(word)이 비어있지 않을동안
            { if(removeNonKorean(word)) { instance.add(word); } }                         // 해당 줄의 단어를 단어리스트에 넣음
            in.close();                                  // 파일 저장
        } catch(IOException e) { }
        return instance.size();
	}
	
	// 한글이 아닌게 끼여있는 단어 제거
	public static boolean removeNonKorean(String input) {
		for(int i=0; i<input.length(); i++) {
			char subString = input.charAt(i);
			if('가' > subString || subString > '힣') {
				System.out.println(subString + "가 있어서 제거");
				return false;
			}
		}
		return true;
	}
}
