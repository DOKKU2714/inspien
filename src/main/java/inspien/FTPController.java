package inspien;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

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
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.connect("211.106.171.36", 20421);
		
		ftpClient.login("inspien01", "inspien01");
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		File file = new File("C:\\Users\\forkb\\Desktop\\INSPIEN_JSON_[김도현]_["+now+"].txt");
		PrintWriter pw = new PrintWriter(file, "UTF-8");
		file.createNewFile();
		pw.println(jsonData);
		pw.flush();
		try {
			boolean isSuccess = ftpClient.storeFile("/INSPIEN_JSON_[김도현]_["+now+"].txt", new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ftpClient.logout();
	}
	
	

}
