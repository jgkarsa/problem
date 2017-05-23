package readFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadAddress {

	private BufferedReader br;
	private String[] addressTable;

	public ReadAddress() {

		String line;
		try {
			br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/readFile/addressTable.txt"));

			line = br.readLine();
			String[] str = line.split(" ");
			addressTable = new String[Integer.parseInt(str[1])];

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		readFile();
	}

	public void readFile() {

		String line;

		try {

			while ((line = br.readLine()) != null) {

				String[] str = line.split(" ");

				switch (str[0]) {

				case "peerAddress:":

					addressTable[Integer.parseInt(str[1])-1] = str[2];

					break;

				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String[] getAddressTable(){
		
		return addressTable;
		
	}

}
