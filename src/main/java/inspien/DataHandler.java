package inspien;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import inspien.vo.DetailVo;
import inspien.vo.HeaderVo;
import inspien.vo.RequestDataVo;

public class DataHandler {
	// 생성 비용이 비쌈
	private ObjectMapper objectMapper = new ObjectMapper();
//	private ObjectMapper xmlMapper = new XmlMapper();
	
	private List<Map<String, String>> headerList = new ArrayList<>();
	private List<Map<String, String>> detailList = new ArrayList<>();
	
	public List<Map<String, String>> getHeaderList() {
		return this.headerList;
	}
	
	public List<Map<String, String>> geDetailList() {
		return this.detailList;
	}

	public RequestDataVo handlingRequestData(String data) throws JsonMappingException, JsonProcessingException {
		return this.objectMapper.readValue(data, RequestDataVo.class);
	}

	public String dataDecoding(String data, String contentType) throws UnsupportedEncodingException {
		byte[] decodedData = Base64.getDecoder().decode(data);
		return new String(decodedData, contentType);
	}

	public String snakeToCamelForMethodName(String data) {
		String[] strArr = data.toLowerCase().split("_");
		String camelStr = "";
		for (String str : strArr) {
			String firstStr = str.substring(0, 1).toUpperCase();
			str = firstStr + str.substring(1);
			camelStr += str;
		}

		return camelStr;
	}
	
	public String toCamel(String str) {
		   Pattern p = Pattern.compile("_(.)");
		   Matcher m = p.matcher(str);
		   StringBuffer sb = new StringBuffer();
		   while (m.find()) {
		       m.appendReplacement(sb, m.group(1).toUpperCase());
		   }
		   m.appendTail(sb);
		   return sb.toString();
		}

	public void deserializationXmlData(String xmlData) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		List<HeaderVo> headerVoList = new ArrayList<>();
		List<DetailVo> detailVoList = new ArrayList<>();
		
		Document document = builder.parse(new InputSource(new StringReader(xmlData)));// 이거 설명해야됨 (xml 파일말고 문자열 자체를
		
		Element root = document.getDocumentElement();// root 요소 가져오기
		NodeList purchaseOrder = root.getChildNodes();// root 요소 확인 : 첫 태그 PurchaseOrder
//		Node firstNode = root.getFirstChild();// root 요소의 첫번째 노드는 #Text >> 빈 공백
//		Node tableName = firstNode.getNextSibling();// 다음 노드는 HEADER, DETAIL

		//데이터 타입에 따라 다르게 VO 에 넣는게 구현이 너무 어렵고 비 효육적이라고 생각해서 일단 List Map으로 변환함
		
		for (int i = 0; i < purchaseOrder.getLength(); i++) {
//			Map<String, String> columns = new HashMap<>();
			HeaderVo headerVo = new HeaderVo();
			DetailVo detailVo = new DetailVo();
			Element rowElement = (Element) purchaseOrder.item(i); // 루트 노드의 자식 노드들 중 하나씩 element로 설정
			NodeList rowNodeList = rowElement.getChildNodes(); //각각의 element에서 하위 노드 리스트를 뽑음
			
			for (int j = 0; j < rowNodeList.getLength(); j++) {
				Element columnElement = (Element) rowNodeList.item(j); // <HEADER>의 하위 요소들
//				columns.put(columnElement.getNodeName(), columnElement.getTextContent());
				if ("HEADER".equals(rowElement.getNodeName())) {
					switch (columnElement.getNodeName()) {
						case "ORDER_NUM":
							headerVo.setOrderNum(Integer.parseInt(columnElement.getTextContent()));
							break;
						case "ORDER_ID":
							headerVo.setOrderId(columnElement.getTextContent());
							break;
						case "ORDER_DATE":
							headerVo.setOrderDate(LocalDate.parse(columnElement.getTextContent(), DateTimeFormatter.ISO_DATE)) ;
							break;
						case "ORDER_PRICE":
							headerVo.setOrderPrice(Integer.parseInt(columnElement.getTextContent()));
							break;
						case "ORDER_QTY":
							headerVo.setOrderQty(Integer.parseInt(columnElement.getTextContent()));
							break;
						case "RECEIVER_NAME":
							headerVo.setReceiverName(columnElement.getTextContent());
							break;
						case "RECEIVER_NO":
							headerVo.setReceiverNo(columnElement.getTextContent());
							break;
						case "ETA_DATE":
							headerVo.setEtaDate(LocalDate.parse(columnElement.getTextContent(), DateTimeFormatter.ISO_DATE));
							break;
						case "DESTINATION":
							headerVo.setDestination(columnElement.getTextContent());
							break;
						case "DESCIPTION":
							headerVo.setDesciption(columnElement.getTextContent());
							break;
						default:
							break;
					}
				} else if ("DETAIL".equals(rowElement.getNodeName())){
					switch (columnElement.getNodeName()) {
						case "ORDER_NUM":
							detailVo.setOrderNum(Integer.parseInt(columnElement.getTextContent()));
							break;
						case "ITEM_SEQ":
							detailVo.setItemSeq(Integer.parseInt(columnElement.getTextContent()));
							break;
						case "ITEM_NAME":
							detailVo.setItemName(columnElement.getTextContent());
							break;
						case "ITEM_QTY":
							detailVo.setItemQty(Integer.parseInt(columnElement.getTextContent()));
							break;
						case "ITEM_COLOR":
							detailVo.setItemColor(columnElement.getTextContent());
							break;
						case "ITEM_PRICE":
							detailVo.setItemPrice(Integer.parseInt(columnElement.getTextContent()));
							break;
						default:
							break;
					}
				}
			}
			System.out.println(headerVo);
			System.out.println(detailVo);
			
		}
		//원래는 vo에 담으려 했으나 for문이 3개 이상이 되거나 json 으로 변환 후 다시 vo로 변환하는게 비 효율적인 방법이라 생각함
	}
}
