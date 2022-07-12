package inspien;

public class Requester {
    private String requestURL;
    private String inputData;
    private String responseData;
  
    Requester(){}
    
    Requester(String requestURL, String inputData) {
        this.requestURL = requestURL;
        this.inputData = inputData;
    }
}
