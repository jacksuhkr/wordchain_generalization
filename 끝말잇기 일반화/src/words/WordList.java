package words;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import data_base.*;

public class WordList {
    // ����� �ܾ���� ��� ����
	public static
		ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>();
	public static
		ArrayList<TreeSet<WordVector>> trainedWords1 = new ArrayList<TreeSet<WordVector>>();
	public static
		ArrayList<TreeSet<WordVector>> trainedWords2 = new ArrayList<TreeSet<WordVector>>();
	public static
		ArrayList<TreeSet<WordVector>> trainedWords3 = new ArrayList<TreeSet<WordVector>>();
	
	// ��ü�н� �ܾ���
	public static
		ArrayList<TreeSet<WordVector>> selfTrainedWords = new ArrayList<TreeSet<WordVector>>();

	public static void setWords() { 
		copyWords(words, Classified.words);
	}
	
	public static void setTrainedWords1() { 
		copyWordVectors(trainedWords1, Classified.trainedWords1);
	}
	
	public static void setTrainedWords2() { 
		copyWordVectors(trainedWords2, Classified.trainedWords2);
	}
	
	public static void setTrainedWords3() { 
		copyWordVectors(trainedWords3, Classified.trainedWords3);
	}
	
	// ��ü�н�
	public static void setSelfTrainedWords() { 
		copyWordVectors(selfTrainedWords, Classified.selfTrainedWords);
	}
	
    // ����� �ܾ� ����� �޼ҵ�
    public static void removeWord(String wordUsed, ArrayList<ArrayList<String>> words) {
    	int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
    	for(int i=0; i<words.get(firstLetterNum).size(); i++) {
    		if(wordUsed.equals(words.get(firstLetterNum).get(i))) {
    			words.get(firstLetterNum).remove(i);
    		}
    	}
    }
    
    public static void removeWordVector1
    				(String wordUsed, ArrayList<TreeSet<WordVector>> trainedWords) {
    	int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
    	WordVector vectorToRemove = new WordVector(wordUsed, Maps.weightMap1.get(wordUsed));
    	trainedWords.get(firstLetterNum).remove(vectorToRemove);
    }
    
    public static void removeWordVector2
    				(String wordUsed, ArrayList<TreeSet<WordVector>> trainedWords) {
    	int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
    	WordVector vectorToRemove = new WordVector(wordUsed, Maps.weightMap2.get(wordUsed));
    	trainedWords.get(firstLetterNum).remove(vectorToRemove);
    }
    
    public static void removeWordVector3
					(String wordUsed, ArrayList<TreeSet<WordVector>> trainedWords) {
		int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
		WordVector vectorToRemove = new WordVector(wordUsed, Maps.weightMap3.get(wordUsed));
		trainedWords.get(firstLetterNum).remove(vectorToRemove);
	}
    
    // ��ü�н�
    public static void removeSelfWordVector
					(String wordUsed, ArrayList<TreeSet<WordVector>> trainedWords) {
		int firstLetterNum = FirstLetters.getFirstLetterNumber(wordUsed);
		WordVector vectorToRemove = new WordVector(wordUsed, Maps.selfWeightMap.get(wordUsed));

	// �׽�Ʈ, ����������� ������ �ֳ� �; â�� �ܾ��忡�� �˻��� �� 
		System.out.print("�ܾ� �������, " + vectorToRemove.word + 
				vectorToRemove.word.hashCode() + "," + vectorToRemove.weight + "�� �ִ�?  ");
//		System.out.println(Classified.selfTrainedWords.get(firstLetterNum).contains(vectorToRemove));
		System.out.println(trainedWords.get(firstLetterNum).contains(vectorToRemove));	// �ܾ �ִ��� ������
		System.out.print("Ȥ�� ������ null�ΰ�? "); System.out.println(vectorToRemove==null);
		
		// �ܾ����� �ܾ���� �� Ȯ���غ���, ���� ������
//		Iterator<WordVector> getMostValuableWord = Classified.selfTrainedWords.get(firstLetterNum).iterator();
		Iterator<WordVector> getMostValuableWord = trainedWords.get(firstLetterNum).iterator();
		while(getMostValuableWord.hasNext()) {
			WordVector checkContents = getMostValuableWord.next();
			System.out.println
				(checkContents.word + checkContents.word.hashCode() + 
						"," + checkContents.weight);
			if(checkContents.word.equals(wordUsed)) {
				System.out.println("<<< �����ֳ� ã�Ҵ�, �������� >>>");
				System.out.print("���� �ܾ��µ�, compareTo�� ���غ���?  ");
				System.out.println(vectorToRemove.compareTo(checkContents));
			// �ؽ��ڵ带 Ȯ���غ�����, �� ��ü�� �ؽ��ڵ尡 �޶�, �ܾ��忡�� �� ã�Ƽ� ����� ��찡 ���Ҵ�
				System.out.println("���� �ؽ��ڵ带 ���غ���? " + 
						vectorToRemove.hashCode() + " , " + checkContents.hashCode());
				System.out.print("�ܾ�� ����?  ");
				System.out.println(vectorToRemove.word.equals(checkContents.word));
				System.out.print("����ġ�� ����?  ");
				System.out.println(vectorToRemove.weight==checkContents.weight);
//				System.out.println("�ܾ, ����ġ��, ��ü�� ������ ������?" + 
//						trainedWords.get(firstLetterNum).remove(checkContents));
				break;
			}
    	}

    // ����°� �����ϸ� �� �� �ܾ�� ���ϰ� ������ ����
    	if(!trainedWords.get(firstLetterNum).remove(vectorToRemove)) {
    		System.out.println("����ܾ� " + vectorToRemove.word + vectorToRemove.weight);
    		System.out.println("Set�ܾ� " + trainedWords.get(firstLetterNum).first().word 
    				+ trainedWords.get(firstLetterNum).first().weight);
//    		if(vectorToRemove.equals(trainedWords.get(firstLetterNum).first())) {
    		if(vectorToRemove.compareTo(trainedWords.get(firstLetterNum).first())==0) {	
    			trainedWords.get(firstLetterNum).pollFirst();
    		}	
    	}
    	
		System.out.print("�ܾ� ���� ��, " + vectorToRemove.word + 
				vectorToRemove.word.hashCode() + "," + vectorToRemove.weight + "�� �ִ�?  ");
		System.out.println(trainedWords.get(firstLetterNum).contains(vectorToRemove));	// �ܾ �ִ��� ������
	}
    
    @SuppressWarnings("unchecked")
	public static void copyWords
    	(ArrayList<ArrayList<String>> copy, ArrayList<ArrayList<String>> original) {
    	copy.clear();
		for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
			ArrayList<String> listToClone = (ArrayList<String>) original.get(i).clone();
			copy.add(listToClone);
		}
    }
    
    @SuppressWarnings("unchecked")
	public static void copyWordVectors
    	(ArrayList<TreeSet<WordVector>> copy, ArrayList<TreeSet<WordVector>> original) {
    	copy.clear();
		for(int i=0; i<FirstLetters.NUM_OF_LETTERS; i++) {
			TreeSet<WordVector> listToClone = (TreeSet<WordVector>) original.get(i).clone();
//			copy.add(listToClone);
			
			// �ʱ�ȭ
			TreeSet<WordVector> emptySet = new TreeSet<WordVector>();     // �ʱ�ȭ�� �Ű��μ� ��̸���Ʈ ����
            emptySet.add(new WordVector("", 0));                 
            copy.add(emptySet);             // emptySet�� output�� ��ü�� �߰�
            copy.get(i).remove(new WordVector("", 0));  
			
			Iterator<WordVector> iterator = listToClone.iterator();
			while(iterator.hasNext()) {
				WordVector wordVector = (WordVector) iterator.next();
				copy.get(i).add(wordVector);
			}
		}
    }
}
