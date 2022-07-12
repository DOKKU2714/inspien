package inspien;

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
	
	

}
