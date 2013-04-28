package cloudinary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.jersey.core.util.Base64;

public class CloudImageFactory {
	
	private static Cloudinary cloudinary = new Cloudinary("cloudinary://621928135242854:AX9oMIXuQuxdim0rA1wDa3Wercg@diuymnnec");

	public static void savePictureByBase64(String base64, String fileName) throws IOException{
		byte[] btDataFile = Base64.decode(base64.split(";base64,")[1]);  
		File file = new File(fileName);  
		FileOutputStream osf = new FileOutputStream(file);  
		osf.write(btDataFile);  
		osf.flush();
		osf.close();
		CloudImageFactory.cloudinary.uploader().upload(file, Cloudinary.asMap("public_id", fileName));
	}
	
}
