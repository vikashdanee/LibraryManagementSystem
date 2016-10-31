package librarysystem.util;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;




public class FileOperation {
	public enum StorageType {
		BOOKS, PERIODICALS, MEMBERS, USERS, CHECKOUT;
	}
	public static final String OUTPUT_DIR = System.getProperty("user.dir")
			+ "\\storage\\";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	public static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR,type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static Object readFromStorage(StorageType type) throws Exception{

		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR,type.toString());
			
			File file = new File(path.toString());  
			if(file.length()!=0){ //Check Empty File Or Not
				in = new ObjectInputStream(Files.newInputStream(path));
				retVal = in.readObject();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

}
