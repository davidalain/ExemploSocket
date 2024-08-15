import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Prof. David Alain do Nascimento - IFPE Campus Garanhuns
 */

public class TCPClient {

	public static void main(String argv[]) throws Exception {

		BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in)); 

		//Conectar ao servidor
		Socket clientSocket = new Socket("localhost", 6789);
		System.out.println("Cliente conectado ao server!");

		//Criar os objetos para enviar e receber dados via socket
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); //output
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //input 

		//Ler a String digitada no teclado
		String sentence = keyboardReader.readLine();

		//Enviar a String para o servidor
		outToServer.writeBytes(sentence + '\n'); 

		//Ler a resposta enviada pelo servidor
		String modifiedSentence = inFromServer.readLine(); 

		System.out.println("FROM SERVER: " + modifiedSentence); 

		clientSocket.close();

	} 

}
