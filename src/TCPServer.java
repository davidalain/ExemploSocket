import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Prof. David Alain do Nascimento - IFPE Campus Garanhuns
 */

public class TCPServer {

	public static void main(String argv[]) throws Exception {

		ServerSocket welcomeSocket = new ServerSocket(6789);
		System.out.println("TCP server rodando!");

		while(true) {

			Socket connectionSocket = welcomeSocket.accept(); //Esperando nova conexão de algum cliente
			System.out.println("Cliente conectado ao TCP server");

			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 

			String clientSentence = inFromClient.readLine(); //Esperando o cliente enviar dados

			String capitalizedSentence = clientSentence.toUpperCase() + '\n'; 

			outToClient.writeBytes(capitalizedSentence); 
		} 
	} 

}
