package inspien;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import inspien.vo.RecordVo;

public class FTPController {
	private FTPClient ftpClient;
	private String ip;
	private int port;
	private String id;
	private String pw;
	private String dir;

	public FTPController() {
		this.ftpClient = new FTPClient();
	}

	public void connect() {
		try {
			boolean result = false;
			ftpClient.connect(this.ip, this.port);
			ftpClient.setControlEncoding("UTF-8");

			int replyCode = ftpClient.getReplyCode();

			if (!FTPReply.isPositiveCompletion(replyCode)) {
				ftpClient.disconnect();
			}
			ftpClient.setSoTimeout(1000 * 10);
			ftpClient.login(this.id, this.pw);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			result = ftpClient.changeWorkingDirectory(dir);

			if (!result) {
				ftpClient.makeDirectory(dir);
				ftpClient.changeWorkingDirectory(dir);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendFile(String fileName, InputStream inputStream) throws IOException {
		this.ftpClient.storeFile(fileName, inputStream);
	}
	
	public void go(String jsonData) throws SocketException, IOException {
		////////////////////////////
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.connect("", 1000);
		
		ftpClient.login("", "");
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		DataHandler dh = new DataHandler();
		RecordVo recordVo = dh.deserializationJsonData();
		File file = new File("C:\test.txt");
		file.createNewFile();
		try {
			boolean isSuccess = ftpClient.storeFile("INSPIEN_JSON_김도현_20220714121212.txt", new FileInputStream(file));
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		ftpClient.logout();
		////////////////////////////////////
	}
	
	

}
