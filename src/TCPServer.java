import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Prof. David Alain do Nascimento - IFPE Campus Garanhuns
 */

public class TCPServer {

	public static void main(String argv[]) throws Exception {
		
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		//Cria o Socket TCP na porta especificada
		int serverPort = 6789;
		ServerSocket welcomeSocket = new ServerSocket(serverPort);
		System.out.println("[" + dtf.format(LocalDateTime.now()) + "] "
				+ "TCP server rodando na porta " + serverPort);

		while(true) {

			//Espera uma nova conexão de um cliente e retorna o objeto Socket com a conexão TCP estabelecida
			//O método accept() fica bloqueado até uqe uma nova conexão seja feita
			System.out.println("[" + dtf.format(LocalDateTime.now()) + "] "
					+ "Aguardando nova conexão de um cliente...");
			Socket connectionSocket = welcomeSocket.accept();

			String remoteAddress = connectionSocket.getInetAddress().getHostAddress();
			int remotePort = connectionSocket.getPort();
			String localAddress = connectionSocket.getLocalAddress().getHostAddress();
			int localPort = connectionSocket.getLocalPort();
			System.out.println("[" + dtf.format(LocalDateTime.now()) + "] "
					+ "Cliente TCP conectado (Cliente: "+remoteAddress+":"+remotePort+") -> (Servidor: "+localAddress+":"+localPort+")");

			//Instancia um objeto onde serão lidos os dados recebidos do cliente (InputStream)
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			//Instancia um objeto onde serão escritos os dados a serem enviados para o cliente (OutputStream)
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 

			//Lê uma linha da String recebida.
			//O método readLine() fica bloqueado aguardando a String conter o caractere que simboliza quebra de linha ('\r' ou '\n') ou fim do arquivo (EOF)
			String clientSentence = inFromClient.readLine();

			//Converte a String recebida para maiúscula (upper case) e coloca o caractere de quebra de linha ('\n')
			String capitalizedSentence = clientSentence.toUpperCase() + '\n'; 

			//Envia a String em maiúscula para o cliente
			outToClient.writeBytes(capitalizedSentence);
		} 
	} 

}
