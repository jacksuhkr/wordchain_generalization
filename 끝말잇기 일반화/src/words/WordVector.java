package words;

/**
 * Created by user on 2016-08-02.
 */
public class WordVector implements Comparable<WordVector> {
    // 단어, 가중치, 사용횟수를 가지고 있는 
	public String word;
    public float weight;
    public int numOfTimesUsed;

    public WordVector(String word) {
        this.word = word;
    }
    
    public WordVector(String word, float weight) {
        this.word = word;
        this.weight = weight;
    }

    public WordVector(String word, float weight, int numOfTimesUsed) {
        this.word = word;
        this.weight = weight;
        this.numOfTimesUsed = numOfTimesUsed;
    }
    
    @Override
    public int compareTo(WordVector o) {
    	// 단어도 같고 가중치도 같을때만 같은거임
    	if(word==o.word) { if (weight==o.weight) { return 0; } }
    	// 단어가 다르고 가중치가 같으면 1을 리턴
    	if(weight<=o.weight) { return 1; }
    	else { return -1; }
    }
}
