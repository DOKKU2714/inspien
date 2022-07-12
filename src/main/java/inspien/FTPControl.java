package inspien;
//https://haenny.tistory.com/259 참고
public class FTPControl {
    private FTPClient ftpClient;
    private String ip;
    private int port;
    private String id;
    private String pw;
    private String dir;
    
    public FTPControl() {
        this.ftpClient = new FTPClient();
    }
    //ftpClient 객체 생성
    //ftp 연결 (ip 와 port 번호)
    //ftp 인코딩 설정
    //ftp 서버로부터 응답 코드를 받음
    public void connect() {
        boolean result = false;
        ftpClient.connect(this.ip, this.port);
        ftpClient.setControlEncoding("UTF-8");

        int replyCode = ftpClient.getReplyCode();

        if (!FTPReply.isPositiveCompletion(replyCode)) {
            ftpClient.disconnect();
        }
        
        ftpClient.setSoTimeout(1000*10); //10초
        ftpClient.login(this.id, this.pw);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode(); // 수동모드 (20번 포트가 아닌 임의의 포트 사용)
        result = ftpClient.changeWorkingDirectory(dir); // 저장 파일 경로
        
        if (!result) {
            ftpClient.makeDirectory(dir);
            ftpClient.changeWorkingDirectory(dir);
        }
    }
    

}
