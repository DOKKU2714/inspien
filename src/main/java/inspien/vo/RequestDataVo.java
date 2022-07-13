package inspien.vo;

import java.util.Map;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.UpperSnakeCaseStrategy.class) //json 데이터가 snake case 이기때문에 camelcase로 자동 맵핑하도록 함
public class RequestDataVo {
	private String xmlData;
	private String jsonData;
	private Map<String, String> dbConnInfo;
	private Map<String, String> ftpConnInfo;

	public String getXmlData() {
		return xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public Map<String, String> getDbConnInfo() {
		return dbConnInfo;
	}

	public void setDbConnInfo(Map<String, String> dbConnInfo) {
		this.dbConnInfo = dbConnInfo;
	}

	public Map<String, String> getFtpConnInfo() {
		return ftpConnInfo;
	}

	public void setFtpConnInfo(Map<String, String> ftpConnInfo) {
		this.ftpConnInfo = ftpConnInfo;
	}
	
	@Override
	public String toString() {
		return "RequestDataVo [xmlData=" + xmlData + ", jsonData=" + jsonData + ", dbConnInfo=" + dbConnInfo
				+ ", ftpConnInfo=" + ftpConnInfo + "]";
	}
	
	
}
