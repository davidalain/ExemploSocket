import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Prof. David Alain do Nascimento - IFPE Campus Garanhuns
 */

public class TCPClient {

	public static void main(String argv[]) throws Exception {
		
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		//Cria o leitor do teclado
		BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in)); 

		//Conecta ao servidor
		String serverAddress = "localhost";
		int serverPort = 6789;
		Socket clientSocket = new Socket(serverAddress, serverPort);
		System.out.println("[" + dtf.format(LocalDateTime.now()) + "] Cliente conectado ao TCP server " + serverAddress+":"+serverPort);

		//Cria os objetos para enviar e receber dados via socket
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); //output
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //input 

		//Lê a String digitada no teclado e retorna o objeto quando receber o caractere de quebra de linha ('\n' ou '\r')
		System.out.println("[" + dtf.format(LocalDateTime.now()) + "] Digite o texto a ser enviada para o servidor e pressione ENTER");
		String sentence = keyboardReader.readLine();

		//Envia a String para o servidor com o caractere de quebra de linha '\n'
		outToServer.writeBytes(sentence + '\n');

		//Lê a resposta enviada pelo servidor e imprime no console
		String modifiedSentence = inFromServer.readLine(); 
		System.out.println("[" + dtf.format(LocalDateTime.now()) + "] FROM TCP SERVER: " + modifiedSentence);

		//Fecha o socket
		clientSocket.close();
		System.out.println("[" + dtf.format(LocalDateTime.now()) + "] Conexão encerrada!");

	}

}
