import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import inspien.DAO;
import inspien.DataHandler;
import inspien.FTPController;
import inspien.Requester;
import inspien.vo.ConnectionInfoVo;
import inspien.vo.JoinVo;
import inspien.vo.RecordVo;

public class Client {
	public static void main(String[] args) {
		try {
			/*URL 요청*/
			Requester requester = new Requester();
			requester.request();
			requester.setConnectionInfoText();
			
			/*접속 정보를 VO 로 변환*/
			DataHandler dataHandler = new DataHandler();
			ConnectionInfoVo connectionInfoVo = dataHandler.connectionInfoTextToVo(requester.getConnectionInfoText());
			
			/*접속정보 VO 의 JSON, XML 데이터를 디코딩 후 contentType 변경*/
			String decodedXmlText = dataHandler.dataDecoding(connectionInfoVo.getXmlData(), "euc-kr");
			String decodedJsonText = dataHandler.dataDecoding(connectionInfoVo.getJsonData(), "utf-8");
			
			/*디코딩 한 xml 데이터를 List<VO> 형태로 변환*/
			List<JoinVo> joinVoList = dataHandler.xmlTextToVoList(decodedXmlText);
			
			/*db conntion 정보를 Map<String, String> 형태로 가져옴*/
			Map<String, String> dbConnectionInfoMap = connectionInfoVo.getDbConnInfo();
			
			/*db접속정보 를 매핑하고 객체 생성*/
			DAO dao = DAO.builder()
					//jdbc:oracle:thin:@211.106.171.36:11527:POS
					.dbUrl("jdbc:oracle:thin:@"+dbConnectionInfoMap.get("HOST") + ":"
								+dbConnectionInfoMap.get("PORT") + ":"
								+dbConnectionInfoMap.get("SID"))
					.dbId(dbConnectionInfoMap.get("USER"))
					.dbPw(dbConnectionInfoMap.get("PASSWORD"))
					.build();
			
			/*쿼리를 xml파일에서 읽어옴*/
			String insertQuery = dao.getQuery("insertXmlData");
			String selectQuery = dao.getQuery("selectXmlData");
			
			/*쿼리 실행 (insert, select)*/
			dao.insert(joinVoList, insertQuery);
			dao.select(selectQuery);
			
			/*text 형태의 디코딩된 json 데이터를 tree형태로 읽어서 jsonNode 객체로 변환*/
			JsonNode jsonNode = new ObjectMapper().readTree(decodedJsonText);
			/*record 라는 object 때문에 바로 List<VO>형태로 변환이 불가능하여 record의 내부 데이터를 따로 빼낸 후 List<Vo> 형태로 변환함*/
			List<RecordVo> recordVoList = dataHandler.jsonTextToVoList(jsonNode.get("record").toString());
			/*List<Vo> 형태로 빼낸 json 을 다시 문자열 형태로 변환*/
			String recordText = dataHandler.recordVoListToText(recordVoList);
			
			/*처음 request 이후 생성된 connectionInfo 객체를 통해 ftp 접속정보를 가져옴*/
			Map<String, String> ftpConnectionInfoMap = connectionInfoVo.getFtpConnInfo();
			/*ftp 접속정보 매핑*/
			FTPController ftpController = FTPController.builder()
					.ip(ftpConnectionInfoMap.get("HOST"))
					.port(Integer.parseInt(ftpConnectionInfoMap.get("PORT")))
					.id(ftpConnectionInfoMap.get("USER"))
					.pw(ftpConnectionInfoMap.get("PASSWORD"))
					.build();
			/*연결 후 로그인*/
			ftpController.connectAndLogin();
			/*문자열 형태의 json 데이터(record라는 start object)를 이용해 ftp 파일 서버로 파일 전송*/
			ftpController.sendFile(recordText, ftpConnectionInfoMap.get("FILE_PATH"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
