package words;

/**
 * Created by user on 2016-08-02.
 */
public class WordVector implements Comparable<WordVector> {
    // �ܾ�, ����ġ, ���Ƚ���� ������ �ִ� 
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
    	// �ܾ ���� ����ġ�� �������� ��������
    	if(word==o.word) { if (weight==o.weight) { return 0; } }
    	// �ܾ �ٸ��� ����ġ�� ������ 1�� ����
    	if(weight<=o.weight) { return 1; }
    	else { return -1; }
    }
}
