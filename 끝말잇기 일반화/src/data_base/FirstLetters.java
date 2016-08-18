package data_base;

public class FirstLetters {
	private static String firstLettersFile = "data_files/first_letters.txt";
	
	public static final int NUM_OF_LETTERS = 11172;
	public static final String[] firstLetters = new String[NUM_OF_LETTERS];

	// ù���ڵ� ��Ƴ��� String[] ����
	public static void getFirstLetters() {
        FileReader.getWords(firstLettersFile, firstLetters);
	}
	
    // �ܾ� ù��° ������ ���ڹ�ȣ�� ����ϴ� �޼ҵ�
    public static int getFirstLetterNumber(String input) {
        char firstLetter = input.charAt(0);
        int unicode = firstLetter - '��';                   
        return unicode;
    }
}
