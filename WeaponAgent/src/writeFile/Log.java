package writeFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Log {
	
	synchronized public void writeLog(String msg){
		
		try {
			File file = new File("Log.txt");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
			writer.write(msg);
			writer.newLine();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
