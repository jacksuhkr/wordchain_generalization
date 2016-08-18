package ¹é¾÷;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader_VariousEncoding {

	// ÆÄÀÏÀ» ÀĞ¾î¼­ ArrayList¸¦ Ã¤¿ò
	public static void getWords(String wordFile, ArrayList<String> words) {
        words.clear();          // ´Ü¾îÀå ¸®¼ÂÇÏ±â Àü¿¡ ¾È¿¡ ÀÖ´ø°Í Æ÷¸Ë
        try {
        	BufferedReader in = new BufferedReader
//        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
//        			(new InputStreamReader(new FileInputStream(wordFile),"UTF-8"));
        			(new InputStreamReader(new FileInputStream(wordFile),"CP949"));
            String word;                                 //  ´Ü¾îÀåÀ» Ã¤¿ï¶§ ¾µ ¸Å°³º¯¼ö
            while (( word = in.readLine()) != null)     // ´ÙÀ½ÁÙ(word)ÀÌ ºñ¾îÀÖÁö ¾ÊÀ»µ¿¾È
            { if(removeNonKorean(word)) { words.add(word); } }                         // ÇØ´ç ÁÙÀÇ ´Ü¾î¸¦ ´Ü¾î¸®½ºÆ®¿¡ ³ÖÀ½
            in.close();                                  // ÆÄÀÏ ÀúÀå
        } catch(IOException e) { }
    }
	
	// ÆÄÀÏ ÀĞ¾î¼­ String[]¸¦ Ã¤¿ò
	public static void getWords(String wordFile, String[] words) {
        try {
        	BufferedReader in = new BufferedReader
//        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
//        			(new InputStreamReader(new FileInputStream(wordFile),"UTF-8"));
        			(new InputStreamReader(new FileInputStream(wordFile),"CP949"));
        	int wordNum=0;
            String word;                                 //  ´Ü¾îÀåÀ» Ã¤¿ï¶§ ¾µ ¸Å°³º¯¼ö
            while (( word = in.readLine()) != null)     // ´ÙÀ½ÁÙ(word)ÀÌ ºñ¾îÀÖÁö ¾ÊÀ»µ¿¾È
            { if(removeNonKorean(word)) { words[wordNum] = word; wordNum++; } }                         // ÇØ´ç ÁÙÀÇ ´Ü¾î¸¦ ´Ü¾î¸®½ºÆ®¿¡ ³ÖÀ½
            in.close();                                  // ÆÄÀÏ ÀúÀå
        } catch(IOException e) { }
    }
	
	// ÆÄÀÏ ÀĞ¾î¼­ float[]¸¦ Ã¤¿ò
	public static void getWeights(String weightFile, float[] weights) {
        try {
        	BufferedReader in = new BufferedReader
//        			(new InputStreamReader(new FileInputStream(weightFile),"EUC-KR"));
//        			(new InputStreamReader(new FileInputStream(weightFile),"UTF-8"));
        			(new InputStreamReader(new FileInputStream(weightFile),"CP949"));
        	int wordNum=0;
        	Float weight;
            String word;                                 //  ´Ü¾îÀåÀ» Ã¤¿ï¶§ ¾µ ¸Å°³º¯¼ö
            while (( word = in.readLine()) != null) {     // ´ÙÀ½ÁÙ(word)ÀÌ ºñ¾îÀÖÁö ¾ÊÀ»µ¿¾È
            	weight = Float.parseFloat(word);
            	weights[wordNum] = weight; 
            	wordNum++; 
            }                         // ÇØ´ç ÁÙÀÇ ´Ü¾î¸¦ ´Ü¾î¸®½ºÆ®¿¡ ³ÖÀ½
            in.close();                                  // ÆÄÀÏ ÀúÀå
        } catch(IOException e) { }
    }

	public static int getNumOfWords(String wordFile) {
		ArrayList<String> instance = new ArrayList<String>();
        try {
        	BufferedReader in = new BufferedReader
//        			(new InputStreamReader(new FileInputStream(wordFile),"EUC-KR"));
//        			(new InputStreamReader(new FileInputStream(wordFile),"UTF-8"));
        			(new InputStreamReader(new FileInputStream(wordFile),"CP949"));
            String word;                                 //  ´Ü¾îÀåÀ» Ã¤¿ï¶§ ¾µ ¸Å°³º¯¼ö
            while (( word = in.readLine()) != null)     // ´ÙÀ½ÁÙ(word)ÀÌ ºñ¾îÀÖÁö ¾ÊÀ»µ¿¾È
            { if(removeNonKorean(word)) { instance.add(word); } }                         // ÇØ´ç ÁÙÀÇ ´Ü¾î¸¦ ´Ü¾î¸®½ºÆ®¿¡ ³ÖÀ½
            in.close();                                  // ÆÄÀÏ ÀúÀå
        } catch(IOException e) { }
        return instance.size();
	}
	
	// ÇÑ±ÛÀÌ ¾Æ´Ñ°Ô ³¢¿©ÀÖ´Â ´Ü¾î Á¦°Å
	public static boolean removeNonKorean(String input) {
		for(int i=0; i<input.length(); i++) {
			char subString = input.charAt(i);
			if('°¡' > subString || subString > 'ÆR') {
				System.out.println(subString + "°¡ ÀÖ¾î¼­ Á¦°Å");
				return false;
			}
		}
		return true;
	}
}
