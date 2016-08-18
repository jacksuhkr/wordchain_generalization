package data_base;

public class Weight {
	private static String weightFile1 = "data_files/weight1.txt";
	private static String weightFile2 = "data_files/weight2.txt";
	private static String weightFile3 = "data_files/weight3.txt";
	
	public static final float[] weights1 = new float[Words.NUM_OF_WORDS]; 
	public static final float[] weights2 = new float[Words.NUM_OF_WORDS]; 
	public static final float[] weights3 = new float[Words.NUM_OF_WORDS]; 
	
	//자체학습을 위한 단어장
	private static String selfWeightFile = "data_files/selfWeight.txt";
	public static final float[] selfWeights = new float[Words.NUM_OF_WORDS]; 
	
	public static void getWeights1() {
		FileReader.getWeights(weightFile1, weights1);
	}
	
	public static void getWeights2() {
		FileReader.getWeights(weightFile2, weights2);
	}

	public static void getWeights3() {
		FileReader.getWeights(weightFile3, weights3);
	}
	
	public static void getSelfWeights() {
		FileReader.getWeights(selfWeightFile, selfWeights);
	}
}
