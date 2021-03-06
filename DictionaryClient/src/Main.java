import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static void checkStatus(String[] statuses) {
		String status = String.join(" => ", statuses);
		System.out.println(status);
	}

	public static void main(String[] args) {
		String statuses[] = {"EN", "KH"};
		
		try {
			// Connect to Server
			Socket connection = new Socket("localhost", 1111);
			
			InputStream inputStream = connection.getInputStream();
			Scanner streamReader = new Scanner(inputStream);
			
			OutputStream outputStream = connection.getOutputStream();
			PrintWriter streamWriter = new PrintWriter(outputStream, true);
			
			InputStream keyboardInputStream = System.in;
			Scanner keyboardReader = new Scanner(keyboardInputStream);
			
			
			while(true){
				// Read request from user keyboard
				System.out.print("Input your word: ");
				String request = keyboardReader.nextLine();
				
				// Send request to server
				streamWriter.println(request);
				
				// Read response from server
				String response = streamReader.nextLine();
				
				// Check response
				if(response.equals("//close")){
					System.out.println("Good bye!");
					break;
					
				}
				else if (response.equals("//status")){
					checkStatus(statuses);
				}
				else if (response.equals("//switch")){
					List<String> list = Arrays.asList(statuses);
					Collections.reverse(list);
					list.toArray(statuses);
					checkStatus(statuses);
				}
				else {
					System.out.println(response);
				}
			}
			
			// Close resources
			streamReader.close();
			keyboardReader.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}









