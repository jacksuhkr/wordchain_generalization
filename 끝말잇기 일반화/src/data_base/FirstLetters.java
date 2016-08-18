package data_base;

public class FirstLetters {
	private static String firstLettersFile = "data_files/first_letters.txt";
	
	public static final int NUM_OF_LETTERS = 11172;
	public static final String[] firstLetters = new String[NUM_OF_LETTERS];

	// 첫글자들 모아놓은 String[] 생성
	public static void getFirstLetters() {
        FileReader.getWords(firstLettersFile, firstLetters);
	}
	
    // 단어 첫번째 글자의 글자번호를 출력하는 메소드
    public static int getFirstLetterNumber(String input) {
        char firstLetter = input.charAt(0);
        int unicode = firstLetter - '가';                   
        return unicode;
    }
}
