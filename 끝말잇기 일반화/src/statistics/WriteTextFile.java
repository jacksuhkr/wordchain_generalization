package statistics;

import java.io.*;

import data_base.Words;
import main.GamePlayingActivity;

public class WriteTextFile {
	private static String outputDirectory = "data_files/output/statistics.txt";
	
	public static void writeText() throws IOException { 			
		writeStatistics(outputDirectory);
		System.out.println("��� ������ ������ ������ ��µǾ����ϴ�");
	}
	
	public static void writeText(String fileDirectory) throws IOException { 			
		writeStatistics(fileDirectory);
		System.out.println("��������� ������ ��µǾ����ϴ�");
	}
	
	public static void writeSelfWeightText(String weightFileDirectory) throws IOException { 			
		try {
			BufferedWriter out = new BufferedWriter (new FileWriter(weightFileDirectory));
			
			// �ܾ� ��跮 ���
			for (int i=0; i<Words.NUM_OF_WORDS; i++) {
				// �·� ���
				Float winningRate = ((float) GamePlayingActivity.numOfGamesWon[i])/
						((float) GamePlayingActivity.numOfGamesToPlay);
				// ���ܾ�, �¸�Ƚ��, ���Ƚ��, �·� ���ʷ� ���
				out.write(Float.toString(winningRate)); 
				if(i<Words.NUM_OF_WORDS) { out.newLine(); }		// ������ ���� ����Ʈ �� ����
				}
			out.close();
		} catch (IOException e) { }
		
		System.out.println("����ġ �ؽ�Ʈ ������ ������ ��µǾ����ϴ�");
	}
	
	// �̰� �޼ҵ�� ���� ���� ������
	private static void writeStatistics(String fileDirectory) {
		try {
			BufferedWriter out = new BufferedWriter
					(new FileWriter(fileDirectory));
			// �� ���
			out.write("���ܾ�" + "\t" + "����ġ" + "\t"+ "���Ƚ��" + "\t" + "�ܾ�·�"); 
			out.newLine();
			
			// �ܾ� ��跮 ���
			for (int i=0; i<Words.NUM_OF_WORDS; i++) {
				// �·� ���
				float winningRate = ((float) GamePlayingActivity.numOfGamesWon[i])/
						((float) GamePlayingActivity.numOfGamesToPlay);
				// ���ܾ�, �¸�Ƚ��, ���Ƚ��, �·� ���ʷ� ���
				out.write(Words.words[i] + "\t" 
                		+ GamePlayingActivity.numOfGamesWon[i] + "\t"
                		+ GamePlayingActivity.numOfGamesToPlay + "\t"
                		+ winningRate); 
				out.newLine();
				}
			out.close();
		} catch (IOException e) { }
	}
}
