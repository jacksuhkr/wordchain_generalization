package data_base;

import words.WordVector;

public class WordVectors {
	public static final WordVector[] wordVectors1 = new WordVector[Words.NUM_OF_WORDS];
	public static final WordVector[] wordVectors2 = new WordVector[Words.NUM_OF_WORDS];
	public static final WordVector[] wordVectors3 = new WordVector[Words.NUM_OF_WORDS];
	
	// 자체 학습을 위한
	public static final WordVector[] selfWordVectors = new WordVector[Words.NUM_OF_WORDS];
	
	public static void getWordVecteors1() {
		for(int i=0; i<Words.NUM_OF_WORDS; i++) {
			wordVectors1[i] = new WordVector(Words.words[i], Weight.weights1[i]);
		}
	}
	
	public static void getWordVecteors2() {
		for(int i=0; i<Words.NUM_OF_WORDS; i++) {
			wordVectors2[i] = new WordVector(Words.words[i], Weight.weights2[i]);
		}
	}
	
	public static void getWordVecteors3() {
		for(int i=0; i<Words.NUM_OF_WORDS; i++) {
			wordVectors3[i] = new WordVector(Words.words[i], Weight.weights3[i]);
		}
	}
	
	public static void getSelfWordVecteors() {
		for(int i=0; i<Words.NUM_OF_WORDS; i++) {
			selfWordVectors[i] = new WordVector(Words.words[i], Weight.selfWeights[i]);
		}
	}
}
