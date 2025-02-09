import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Prof. David Alain do Nascimento - IFPE Campus Garanhuns
 */

public class UDPClient {

	public static void main(String args[]) throws Exception { 

		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		
		//Cria o leitor do teclado
		BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in)); 

		//Cria o Socket UDP/IP
		DatagramSocket clientSocket = new DatagramSocket();

		//IP e porta de destino
		InetAddress serverAddress = InetAddress.getByName("localhost");
		int serverPort = 9876;

		//Lê do teclado a String a ser enviada
		System.out.println("[" + dtf.format(LocalDateTime.now()) + "] "
				+ "Digite o texto a ser enviada para o servidor e pressione ENTER");
		String sentence = keyboardReader.readLine();
		
		//Cria o segmento UDP com a String como payload (campo de dados)
		byte[] sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

		//Envia o segmento UDP para o servidor
		clientSocket.send(sendPacket);

		//Cria o objeto que armazenará o segmento UDP de resposta vinda do servidor
		byte[] receivedData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length); 

		//Recebe o segmento UDP
		clientSocket.receive(receivePacket);

		String modifiedSentence = new String(receivePacket.getData()); 
		System.out.println("[" + dtf.format(LocalDateTime.now()) + "] "
				+ "FROM UDP SERVER:" + modifiedSentence);
		
		clientSocket.close(); 
	} 

}
