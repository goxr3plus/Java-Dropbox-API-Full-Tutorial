package main.java.com.goxr3plus.javadropboxtutorial.application;

import java.io.IOException;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

public class Main {
	private static final String ACCESS_TOKEN = "<ACCESS-TOKEN HERE>";
	
	public static void main(String args[]) {
		System.out.println("Hi");
		
		try {
			
			// Create Dropbox client
			DbxRequestConfig config = new DbxRequestConfig("dropbox/APPLICATION-NAME-HERE");
			DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
			
			// Get current account info
			FullAccount account = client.users().getCurrentAccount();
			System.out.println(account.getName().getDisplayName());
			
			// Get files and folder metadata from Dropbox root directory
			ListFolderResult result = client.files().listFolder("");
			while (true) {
				for (Metadata metadata : result.getEntries()) {
					System.out.println(metadata.getPathLower());
				}
				
				if (!result.getHasMore()) {
					break;
				}
				
				result = client.files().listFolderContinue(result.getCursor());
			}
			
		} catch (DbxException ex1) {
			ex1.printStackTrace();
		}
		
		//        // Upload "test.txt" to Dropbox
		//        try (InputStream in = new FileInputStream("test.txt")) {
		//            FileMetadata metadata = client.files().uploadBuilder("/test.txt")
		//                .uploadAndFinish(in);
		//        }
		//
		//        DbxDownloader<FileMetadata> downloader = client.files().download("/test.txt");
		//        try {
		//            FileOutputStream out = new FileOutputStream("test.txt");
		//            downloader.download(out);
		//            out.close();
		//        } catch (DbxException ex) {
		//            System.out.println(ex.getMessage());
		//        }
	}
}
