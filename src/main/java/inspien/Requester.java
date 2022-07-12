package inspien;

public class Requester {
    private String requestURL;
    private String inputData;
  
    Requester(){}
    
    Requester(String requestURL, String inputData) {
        this.requestURL = requestURL;
        this.inputData = inputData;
    }
    
    public String sendRequest() throws IOException {
        URL url = null; // url객체를 통해 httpUrlConnection 객체를 얻음
		HttpURLConnection httpUrlConnection = null; //http 통신을 할 수 있게 해주는 객체
		url = new URL(this.requestURL);	//url 객체 생성
		httpUrlConnection = (HttpURLConnection)url.openConnection();	//httpUrlConnection 객체 받아옴
		httpUrlConnection.setRequestMethod("POST");	//http method 설정
//		httpUrlConnection.setConnectTimeout(CONNECTION_TIMEOUT);	//연결 최대 시간 설정
//		httpUrlConnection.setReadTimeout(READ_TIMEOUT);	//
		httpUrlConnection.setRequestProperty("Content-type", "application/json;utf-8");	//http request message 의 header에서 컨텐츠 타입 설정
		httpUrlConnection.setRequestProperty("Accept", "application/json;");	//json 형태의 응답 ??
		httpUrlConnection.setDoOutput(true);	//??
		
		//try-catch-finally 를 통한 writer close 를 하지않고 try-with-resources 를 통해 간단하게 구현
		try (PrintWriter printWriter = new PrintWriter(	//printWriter 스트림객체를 통해 한 줄씩 읽어서 connection 의 outputstream을 통해 json 형태의 데이터 전송
				httpUrlConnection.getOutputStream())) {
			printWriter.print(inputData);
			printWriter.flush();
		}    
        
        String readLine = null; //json 형태의 데이터를 한 줄 씩 읽기 위한 변수
	StringBuilder buffer = null; //readLine 변수로 한 줄씩 읽은 데이터를 저장하기위한 버퍼
	buffer = new StringBuilder();
	//try-catch-finally 를 통한 writer close 를 하지않고 try-with-resources 를 통해 간단하게 구현
	try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
			httpUrlConnection.getInputStream(), "UTF-8"))) {
		if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) { //응답 코드가 200일 경우

			while((readLine = bufferedReader.readLine()) != null) {
				buffer.append(readLine)
						.append("\n");
			}
		} else {
			buffer.append("code : ")
					.append(httpUrlConnection.getResponseCode())
					.append("\n")
					.append("message : ")
					.append(httpUrlConnection.getResponseMessage())
					.append("\n");
		}
	}
	return buffer.toString();
    }
}
