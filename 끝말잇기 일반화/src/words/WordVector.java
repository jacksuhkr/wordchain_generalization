package words;

/**
 * Created by user on 2016-08-02.
 */
public class WordVector implements Cloneable, Comparable<WordVector> {
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
    	if(o.word.equals(this.word)) { /*if (weight==o.weight)*/ { 
//    		System.out.println("비교중 : " + o.word + o.weight);
    		return 0; } }
    	// 단어가 다르고 가중치가 같으면 1을 리턴
    	else if(this.weight<=o.weight) { return 1; }
    	return -1;
    }
    
    
    // equal 함수를 override해서 equal을 재정의
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
    
    
    // clone 함수를 override해서 재정의
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
