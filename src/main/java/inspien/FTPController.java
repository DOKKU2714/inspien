package inspien;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import lombok.Builder;

@Builder
/*FTP 서버에 접근하고 파일을 전송하기 위한 클래스*/
public class FTPController {
	private FTPClient ftpClient;
	private String ip;
	private int port;
	private String id;
	private String pw;
	
	/*ftp 파일 서버에 접속 후 로그인*/
	public void connectAndLogin() throws SocketException, IOException {
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.connect(this.ip, this.port);
		ftpClient.login(this.id, this.pw);
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	}
	
	/*접속 및 로그인 한 ftp 파일서버에 파일을 전송 (로컬 컴퓨터에는 바탕화면에 파일 생성)*/
	public void sendFile(String jsonText, String filePath) throws IOException {
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		File file = new File("C:\\Users\\forkb\\Desktop\\INSPIEN_JSON_[김도현]_["+now+"].txt");
		PrintWriter printWriter = new PrintWriter(file, "UTF-8");
		file.createNewFile();
		printWriter.println(jsonText);
		printWriter.flush();
		boolean isSuccess = ftpClient.storeFile(filePath+"INSPIEN_JSON_[김도현]_["+now+"].txt", new FileInputStream(file));
		if (!isSuccess) {
			
		} else {
			ftpClient.logout();
		}
	}
}
