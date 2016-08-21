package words;

/**
 * Created by user on 2016-08-02.
 */
public class WordVector implements Cloneable, Comparable<WordVector> {
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
    	if(o.word.equals(this.word)) { /*if (weight==o.weight)*/ { 
//    		System.out.println("���� : " + o.word + o.weight);
    		return 0; } }
    	// �ܾ �ٸ��� ����ġ�� ������ 1�� ����
    	else if(this.weight<=o.weight) { return 1; }
    	return -1;
    }
    
    
    // equal �Լ��� override�ؼ� equal�� ������
    @Override
    public boolean equals(Object o) {
    	if (super.equals(o)) {
			return true;
		}
		if (o instanceof WordVector) {
			if (this.word.equals(((WordVector) o).word)) {
				return true;
			}
		}
		return false;
    }
    
    
    // clone �Լ��� override�ؼ� ������
    @Override 
    public WordVector clone(){ 
       Object obj = null; 
       WordVector wordVector = null; 
       try { 
          obj = super.clone(); 
          wordVector = (WordVector) obj; 
          wordVector.word = this.word; 
          wordVector.weight = this.weight; 
          } catch (CloneNotSupportedException e) { 
          e.printStackTrace(); 
       } 
       return (WordVector) obj; 
    } 
}
