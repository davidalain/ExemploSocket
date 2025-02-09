import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * @author Prof. David Alain do Nascimento - IFPE Campus Garanhuns
 */

public class UDPServer {

	public static void main(String args[]) throws Exception {
		
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		//Cria um Socket UDP na porta especificada
		int serverPort = 9876;
		DatagramSocket serverSocket = new DatagramSocket(serverPort);
		System.out.println("[" + dtf.format(LocalDateTime.now()) + "] "
				+ "UDP server rodando na porta " + serverPort);

		//Cria um buffer para armazenar os dados do pacote a ser recebido
		byte[] receivedData = new byte[1024];

		while(true) {

			//Zera o buffer para evitar usar lixo de pacotes anteriores
			Arrays.fill(receivedData, (byte)0);

			//Cria um pacote UDP/IP para receber os dados nesse pacote e guardar no buffer criado
			DatagramPacket receivePacket = new DatagramPacket(receivedData, receivedData.length);

			//Recebe o segmento UDP. Este método fica bloqueado esperando até que algum segmento UDP chegue no socket
			serverSocket.receive(receivePacket);
			

			//Cria uma String com os dados recebidos. Assume-se que o payload (dados) do segmento UDP é uma String. 
			String sentence = new String(receivePacket.getData());

			//Pega o IP e porta de origem para saber para quem responder
			InetAddress remoteAddress = receivePacket.getAddress();
			int remotePort = receivePacket.getPort();
			
			InetAddress localAddress = serverSocket.getLocalAddress();
			int localPort = serverSocket.getLocalPort();
			System.out.println("[" + dtf.format(LocalDateTime.now()) + "] "
					+ "Recebido pacote UDP do cliente: "
					+ "(Cliente: "+remoteAddress.getHostAddress()+":"+remotePort+") -> (Servidor: "+localAddress.getHostAddress()+":"+localPort+")");

			//Converte a String para maiúscula (upper case)
			String capitalizedSentence = sentence.toUpperCase();

			//Cria o payload (dados) do segmento a ser enviado como resposta 
			byte[] sendData = capitalizedSentence.getBytes();

			//Cria o pacote UDP/IP com a String em maiúscula e envia de volta 
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, remoteAddress, remotePort); 
			serverSocket.send(sendPacket);
			System.out.println("[" + dtf.format(LocalDateTime.now()) + "] "
					+ "Enviado pacote UDP para o cliente: "
					+ "(Servidor: "+localAddress.getHostAddress()+":"+localPort+") -> (Cliente: "+remoteAddress.getHostAddress()+":"+remotePort+")");

		}
	}
}