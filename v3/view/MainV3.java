package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainV3 {
	
	enum Card {
	    T(10),J(11),Q(12),K(13),A(14);
	 
	    public Integer cardValue;
	    Card(Integer value) {
	        cardValue = (Integer) value;
	    }
	    
	    public Integer getCard() {
	    	return this.cardValue;
	    }
	}

	public static void main(String[] args) {
//		Descomentar essa parte na hora de gerar vers�o
//		if (args.length < 3) {
//			System.out.println("Informe 3 arquivos para serem lidos.");
//			System.exit(1);
//		}
		
		// Tudo isso aqui � pra ser removido na hora de gerar vers�o,
		// ta aqui s� pra debug.
		args = new String[3];
		args[0] = "teste.txt";
		args[1] = "teste.txt";
		args[2] = "teste.txt";
		// At� aqui
		
		long four = 0;
		long nothing = 0;
		long differents = 0;
		long sequences = 0;
		long datePassed = 0;
		long dateBeggin = 0;
		String line = new String();

		// In�cio do corpo do m�todo readFileAndCalculate
		BufferedReader br = null;
		FileReader fr = null;
		List<String> hands = new ArrayList<>();

		// Esse for � pra executar pros 3 primeiros argumentos
		for (int i = 0; i < 3; i++) {
			four = 0;
			nothing = 0;
			differents = 0;
			sequences = 0;
			datePassed = 0;
			
			dateBeggin = System.currentTimeMillis();
			
			try {
				//br = new BufferedReader(new FileReader(FILENAME));
				fr = new FileReader(args[i]);
				br = new BufferedReader(fr);
	
				String sCurrentLine;
				int thousand = 0;
				
				String result = new String();
				boolean sequence = false;
				
				while ((sCurrentLine = br.readLine()) != null) {
					
					
					hands.add(sCurrentLine);
					
					// Com arquivos com menos de 5000 linhas, n�o ta entrando, CONFIRMAR EM CORRE��O
					if (thousand > 5000) {
						if (hands != null && hands.size() > 0) {
//							System.out.println("calculando");

							// In�cio do corpo do m�todo verifyHands
							for (String hand : hands) {
								String[] Hands = hand.split(("\\s+"));
								
								// In�cio do corpo do m�todo isFourEquals
								hand = hand.replaceAll("\\s+","");
								String tmp = new String(hand);
								short j = 0;
								
								// N�o entendi o que acontece aqui, ele substitui hand com o conte�do de tmp,
								// que � uma linha, menos 1 caractere, ou seja, � a linha com 4 "cartas".
								// Essa rotina foi retirada do m�todo isFourEquals.
								while(j < tmp.length()-1) {
									hand = hand.replace(tmp.charAt(j++) + "", "");
									
									if (hand.length() > 3)
										hand = tmp;
									else if(hand.length() > 1) //se encontrou pelo menos um par de cartas
										result = "nada";
									else
										result = "four";	
								}
								// Fim do corpo do m�todo isFourEquals
								
								// In�cio do corpo do m�todo isSequency
								int[] handsNumber = new int[5];
								
								j = 0;
								//transforma string em aray de n�meros
								while (j < Hands.length) {
									if(Hands[j].equalsIgnoreCase("T")) {
										handsNumber[j] = Card.T.getCard();
									} else if (Hands[j].equalsIgnoreCase("J")) {
										handsNumber[j] = Card.J.getCard();
									} else if (Hands[j].equalsIgnoreCase("Q")) {
										handsNumber[j] = Card.Q.getCard();
									} else if (Hands[j].equalsIgnoreCase("K")) {
										handsNumber[j] = Card.K.getCard();
									} else if (Hands[j].equalsIgnoreCase("A")) {
										handsNumber[j] = Card.A.getCard();
									} else {
										handsNumber[j] = Integer.parseInt(Hands[j]);
									}
									j++;
								}
								//ordena o array
								Arrays.sort(handsNumber);
								
								j = 0;
								short isSequence = 0;
								//conta os que est�o em sequencia
								while(j < (handsNumber.length - 1)) {
									
									if ((handsNumber[j] + 1) == handsNumber[j+1])
										isSequence++;
									j++;
								}
								//quer dizer que s�o sequencia
								if (isSequence > 3)
									sequence = true;
								else
									sequence = false;
								// Fim do corpo do m�todo isSequency
								
								//se existem quatro cartas iguais
								if(result.equalsIgnoreCase("four")) {
									four++;
								} else if (result.equalsIgnoreCase("nada")) { //se existe pares
									nothing++;
								} else if (sequence == true) { //sequ�ncia
									sequences++;
								}else {		//todos diferentes
									differents++;
								}
							}
							// Fim do corpo do m�todo verifyHands
							
							hands = null;
							hands = new ArrayList<>();
						}
						thousand = 0;
					} else
						thousand++;
					
				}
				
				if (hands != null && hands.size() > 0) {
					// In�cio do corpo do m�todo verifyHands
					for (String hand : hands) {
						
						//se existem quatro cartas iguais
						if(result.equalsIgnoreCase("four")) {
							four++;
						} else if (result.equalsIgnoreCase("nada")) { //se existe pares
							nothing++;
						} else if (sequence == true) { //sequ�ncia
							sequences++;
						}else {		//todos diferentes
							differents++;
						}
					}
					// Fim do corpo do m�todo verifyHands
				}
	
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
	
				try {
	
					if (br != null)
						br.close();
	
					if (fr != null)
						fr.close();
					
				} catch (IOException ex) {
	
					ex.printStackTrace();
	
				}
	
			}
			// Fim do corpo do m�todo readFileAndCalculate
			
			datePassed = System.currentTimeMillis() - dateBeggin;
		
			line += String.valueOf(datePassed) + " " + String.valueOf(four) +
				" " + String.valueOf(differents) + " " + String.valueOf(sequences) + "\n";
		}
		
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

		System.exit(0);
	}

}
