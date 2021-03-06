package view;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainV1 {
	private static String FILENAME2k;
	private static String FILENAME2M;
	private static String FILENAME200M;

	private static long four;
	private static long differents;
	private static long sequences;
	private static long datePassed;
	private static String line;
	
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Informe 3 arquivos para serem lidos.");
			System.exit(1);
		}
				
		MainV1.FILENAME2k = args[0];
		MainV1.FILENAME2M = args[1];
		MainV1.FILENAME200M = args[2];
		
		calculate(MainV1.FILENAME2k);
		line = String.valueOf(datePassed) + " " + String.valueOf(four) +
				" " + String.valueOf(differents) + " " + String.valueOf(sequences) + "\n";
		
		calculate(MainV1.FILENAME2M);
		line += String.valueOf(datePassed) + " " + String.valueOf(four) +
				" " + String.valueOf(differents) + " " + String.valueOf(sequences) + "\n";
		
		calculate(MainV1.FILENAME200M);
		line += String.valueOf(datePassed) + " " + String.valueOf(four) +
				" " + String.valueOf(differents) + " " + String.valueOf(sequences) + "\n";
		
		writeFile(line);

		System.exit(0);
	}
	
	public enum Card {
	    T(10),J(11),Q(12),K(13),A(14);
	 
	    public Integer cardValue;
	    Card(Integer value) {
	        cardValue = (Integer) value;
	    }
	    
	    public Integer getCard() {
	    	return this.cardValue;
	    }
	}
	
	public static void calculate (String directory) {
		Long dateBeggin = System.currentTimeMillis();
		
		four = 0;
		differents = 0;
		sequences = 0;
		datePassed = 0;
		
		readFileAndCalculate(directory);
		
		datePassed = System.currentTimeMillis() - dateBeggin;
	}
	
	public static List<String> readFileAndCalculate(String fileName) {
		BufferedReader br = null;
		FileReader fr = null;
		List<String> hands = new ArrayList<>();

		try {

			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;
			int thousand = 0;
			
			while ((sCurrentLine = br.readLine()) != null) {
				hands.add(sCurrentLine);
				
				if (thousand > 5000) {
					if (hands != null && hands.size() > 0) {
//						System.out.println("calculando");
						verifyHands(hands);
						hands = null;
						hands = new ArrayList<>();
					}
					thousand = 0;
				} else
					thousand++;
				
			}
			
			if (hands != null && hands.size() > 0) {
				
				verifyHands(hands);
			}

		} catch (IOException e) {

			e.printStackTrace();
			return null;
		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();
				
				return hands;
			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return null;
	}

	public static void verifyHands(List<String> hands) {
		//percorre todos os conjuntos de cartas
		for (String hand : hands) {
			
			String result = isFourEquals(hand);
			
			//se existem quatro cartas iguais
			if(result.equalsIgnoreCase("four")) {
				four++;
			} else if (result.equalsIgnoreCase("nada")) { //se existe pares
				
			} else if (isSequency(hand)) { //sequ�ncia
				sequences++;
			}else {		//todos diferentes
				differents++;
			}
			
		}
	}
	
	public static String isFourEquals(String hand) {
		hand = hand.replaceAll("\\s+","");
		String tmp = new String(hand);
		short i = 0;
		while(i < tmp.length()-1) {
			hand = hand.replace(tmp.charAt(i++) + "", "");
			
			if (hand.length() > 3)
				hand = tmp;
			else if(hand.length() > 1) //se encontrou pelo menos um par de cartas
				return "nada";
			else
				return "four";	
		}
		
		return "";
	}
	
	public static boolean isSequency(String hand ) {
		/*sequencis
		 * TQJKA
		 * 23456
		 * */
		String[] hands = hand.split(("\\s+"));
		int[] handsNumber = new int[5];
		
		short i = 0;
		//transforma string em aray de n�meros
		while (i < hands.length) {
			if(hands[i].equalsIgnoreCase("T")) {
				handsNumber[i] = Card.T.getCard();
			} else if (hands[i].equalsIgnoreCase("J")) {
				handsNumber[i] = Card.J.getCard();
			} else if (hands[i].equalsIgnoreCase("Q")) {
				handsNumber[i] = Card.Q.getCard();
			} else if (hands[i].equalsIgnoreCase("K")) {
				handsNumber[i] = Card.K.getCard();
			} else if (hands[i].equalsIgnoreCase("A")) {
				handsNumber[i] = Card.A.getCard();
			} else {
				handsNumber[i] = Integer.parseInt(hands[i]);
			}
			i++;
		}
		//ordena o array
		Arrays.sort(handsNumber);
		
		i = 0;
		short isSequence = 0;
		//conta os que est�o em sequencia
		while(i < (handsNumber.length - 1)) {
			
			if ((handsNumber[i] + 1) == handsNumber[i+1])
				isSequence++;
			i++;
		}
		//quer dizer que s�o sequencia
		if (isSequence > 3)
			return true;
		else
			return false;	
	}
	
	public static void writeFile (String line) {
		try {
			File file = new File("saida.txt");
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(line);
			bw.close();
			fw.close();
		}
		
		catch (IOException e) {
			 e.printStackTrace();
		}
	}
	
}
	
