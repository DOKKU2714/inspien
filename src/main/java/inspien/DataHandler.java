package inspien;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import inspien.vo.DetailVo;
import inspien.vo.DetailVoXmlWrapper;
import inspien.vo.HeaderVo;
import inspien.vo.HeaderVoXmlWrapper;
import inspien.vo.RequestDataVo;

public class DataHandler {
	private String xmlData;
	private String jsonData;
	
	private List<HeaderVo> headerVoList;
	private List<DetailVo> detailVoList;
	
	// 생성 비용이 비쌈
	private ObjectMapper objectMapper = new ObjectMapper();
	
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

	public List<HeaderVo> getHeaderVoList() {
		return headerVoList;
	}

	public void setHeaderVoList(List<HeaderVo> headerVoList) {
		this.headerVoList = headerVoList;
	}

	public List<DetailVo> getDetailVoList() {
		return detailVoList;
	}

	public void setDetailVoList(List<DetailVo> detailVoList) {
		this.detailVoList = detailVoList;
	}

	public RequestDataVo handlingRequestData(String data) throws JsonMappingException, JsonProcessingException {
		return this.objectMapper.readValue(data, RequestDataVo.class);
	}

	public String dataDecoding(String data, String contentType) throws UnsupportedEncodingException {
		byte[] decodedData = Base64.getDecoder().decode(data);
		return new String(decodedData, contentType);
	}

//	public String toCamel(String str) {
//		   Pattern p = Pattern.compile("_(.)");
//		   Matcher m = p.matcher(str);
//		   StringBuffer sb = new StringBuffer();
//		   while (m.find()) {
//		       m.appendReplacement(sb, m.group(1).toUpperCase());
//		   }
//		   m.appendTail(sb);
//		   return sb.toString();
//	}

	//jaxb 를 이용한 xml 파싱 후 VO 객체에 삽입
	public void deserializationXmlData() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(HeaderVoXmlWrapper.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		this.headerVoList = ((HeaderVoXmlWrapper) unmarshaller.unmarshal(new StringReader(this.xmlData))).getHeaderVoList();
		
		jaxbContext = JAXBContext.newInstance(DetailVoXmlWrapper.class); //XML 파싱을 위한 JAXBContext 객체 생성
		unmarshaller = jaxbContext.createUnmarshaller(); // Unmarshaller 객체 생성
		this.detailVoList = ((DetailVoXmlWrapper) unmarshaller.unmarshal(new StringReader(this.xmlData))).getDetailVoList(); //unmarshal 후 반환된 리스트
	}
	
	public void deserializationJsonData(){
		//TODO
	}
	
//	public void deserializationXmlData(String xmlData) throws ParserConfigurationException, SAXException, IOException {
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = factory.newDocumentBuilder();
//
//		List<HeaderVo> headerVoList = new ArrayList<>();
//		List<DetailVo> detailVoList = new ArrayList<>();
//		
//		Document document = builder.parse(new InputSource(new StringReader(xmlData)));// 이거 설명해야됨 (xml 파일말고 문자열 자체를
//		
//		Element root = document.getDocumentElement();// root 요소 가져오기
//		NodeList purchaseOrder = root.getChildNodes();// root 요소 확인 : 첫 태그 PurchaseOrder
////		Node firstNode = root.getFirstChild();// root 요소의 첫번째 노드는 #Text >> 빈 공백
////		Node tableName = firstNode.getNextSibling();// 다음 노드는 HEADER, DETAIL
//
//		//데이터 타입에 따라 다르게 VO 에 넣는게 구현이 너무 어렵고 비 효율적이라고 생각해서 일단 List Map으로 변환함
//		
//		for (int i = 0; i < purchaseOrder.getLength(); i++) {
////			Map<String, String> columns = new HashMap<>();
//			HeaderVo headerVo = new HeaderVo();
//			DetailVo detailVo = new DetailVo();
//			Element rowElement = (Element) purchaseOrder.item(i); // 루트 노드의 자식 노드들 중 하나씩 element로 설정
//			NodeList rowNodeList = rowElement.getChildNodes(); //각각의 element에서 하위 노드 리스트를 뽑음
//			
//			for (int j = 0; j < rowNodeList.getLength(); j++) {
//				Element columnElement = (Element) rowNodeList.item(j); // <HEADER>의 하위 요소들
//				String rowName = rowElement.getNodeName();
//				if ("HEADER".equals(rowName)) {
//					switch (columnElement.getNodeName()) {
//						case "ORDER_NUM":
//							headerVo.setOrderNum(Integer.parseInt(columnElement.getTextContent()));
//							break;
//						case "ORDER_ID":
//							headerVo.setOrderId(columnElement.getTextContent());
//							break;
//						case "ORDER_DATE":
//							headerVo.setOrderDate(LocalDate.parse(columnElement.getTextContent(), DateTimeFormatter.ISO_DATE)) ;
//							break;
//						case "ORDER_PRICE":
//							headerVo.setOrderPrice(Integer.parseInt(columnElement.getTextContent()));
//							break;
//						case "ORDER_QTY":
//							headerVo.setOrderQty(Integer.parseInt(columnElement.getTextContent()));
//							break;
//						case "RECEIVER_NAME":
//							headerVo.setReceiverName(columnElement.getTextContent());
//							break;
//						case "RECEIVER_NO":
//							headerVo.setReceiverNo(columnElement.getTextContent());
//							break;
//						case "ETA_DATE":
//							headerVo.setEtaDate(LocalDate.parse(columnElement.getTextContent(), DateTimeFormatter.ISO_DATE));
//							break;
//						case "DESTINATION":
//							headerVo.setDestination(columnElement.getTextContent());
//							break;
//						case "DESCIPTION":
//							headerVo.setDesciption(columnElement.getTextContent());
//							break;
//						default:
//							break;
//					}
//				} else if ("DETAIL".equals(rowName)){
//					switch (columnElement.getNodeName()) {
//						case "ORDER_NUM":
//							detailVo.setOrderNum(Integer.parseInt(columnElement.getTextContent()));
//							System.out.println(columnElement.getTextContent());
//							break;
//						case "ITEM_SEQ":
//							detailVo.setItemSeq(Integer.parseInt(columnElement.getTextContent()));
//							break;
//						case "ITEM_NAME":
//							detailVo.setItemName(columnElement.getTextContent());
//							break;
//						case "ITEM_QTY":
//							detailVo.setItemQty(Integer.parseInt(columnElement.getTextContent()));
//							break;
//						case "ITEM_COLOR":
//							detailVo.setItemColor(columnElement.getTextContent());
//							break;
//						case "ITEM_PRICE":
//							detailVo.setItemPrice(Integer.parseInt(columnElement.getTextContent()));
//							break;
//						default:
//							break;
//					}
//				}
//			}
//		}
//	}
}
