package inspien;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.code.Type;

import inspien.vo.DetailVo;
import inspien.vo.HeaderVo;
import inspien.vo.JoinVo;
import inspien.vo.RecordVo;
import inspien.vo.RequestDataVo;
import inspien.vo.xmlWrapper.DetailVoXmlWrapper;
import inspien.vo.xmlWrapper.HeaderVoXmlWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataHandler {
	private String xmlData;
	private String jsonData;

	private List<HeaderVo> headerVoList = new ArrayList<>();
	private List<DetailVo> detailVoList = new ArrayList<>();
	private List<JoinVo> joinVoList = new ArrayList<>();

	// 생성 비용이 비쌈
	private ObjectMapper objectMapper = new ObjectMapper();

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

	// jaxb 를 이용한 xml 파싱 후 각각의 VO 객체에 삽입
	public void deserializationXmlData() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(HeaderVoXmlWrapper.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		this.headerVoList = ((HeaderVoXmlWrapper) unmarshaller.unmarshal(new StringReader(this.xmlData)))
				.getHeaderVoList();

		jaxbContext = JAXBContext.newInstance(DetailVoXmlWrapper.class); // XML 파싱을 위한 JAXBContext 객체 생성
		unmarshaller = jaxbContext.createUnmarshaller(); // Unmarshaller 객체 생성
		this.detailVoList = ((DetailVoXmlWrapper) unmarshaller.unmarshal(new StringReader(this.xmlData)))
				.getDetailVoList(); // unmarshal 후 반환된 리스트

		// headerVo 와 detailVo 에서 orderNum 이같은 것들끼리 left join 하여 joinVo 에 매핑
		int detailCursor = 0;
		int count = 0;
		for (int i = 0; i < this.headerVoList.size(); i++) {
			HeaderVo headerVo = this.headerVoList.get(i);
			JoinVo joinVo = null;
			count = detailCursor;
			while (count < detailVoList.size()) {
				DetailVo detailVo = detailVoList.get(count);
				if (headerVo.getOrderNum() != detailVo.getOrderNum()) {
					detailCursor = count;
					break;
				} else {
					joinVo = JoinVo.builder().orderNum(headerVo.getOrderNum()).orderId(headerVo.getOrderId())
							.orderDate(headerVo.getOrderDate()).orderPrice(headerVo.getOrderPrice())
							.orderQty(headerVo.getOrderQty()).receiverNo(headerVo.getReceiverNo())
							.receiverName(headerVo.getReceiverName()).destination(headerVo.getDestination())
							.desciption(headerVo.getDesciption()).etaDate(headerVo.getEtaDate())
							.itemSeq(detailVo.getItemSeq()).itemName(detailVo.getItemName())
							.itemPrice(detailVo.getItemPrice()).itemQty(detailVo.getItemQty())
							.itemColor(detailVo.getItemColor()).build();
					count++;
				}
				this.joinVoList.add(joinVo);
			}
		}
	}
//제네릭 왜 안도
	public List<RecordVo> deserializationJsonData() throws JsonMappingException, JsonProcessingException {
		return this.objectMapper.readValue(this.jsonData, new TypeReference<List<RecordVo>>() {});
	}
	
	public String recordVoListToText(List<RecordVo> list) {
		StringBuilder stringBuilder = new StringBuilder();
		
		Field[] fieldArr = RecordVo.class.getDeclaredFields();
		
		int fieldCount = fieldArr.length;
		for (RecordVo vo : list) {
			stringBuilder.append(vo.getNames()+"^")
					.append(vo.getPhone()+"^")
					.append(vo.getEmail()+"^")
					.append(vo.getBirthDate()+"^")
					.append(vo.getCompany()+"^")
					.append(vo.getPersonalNumber()+"^")
					.append(vo.getCountry()+"^")
					.append(vo.getRegion()+"^")
					.append(vo.getCity()+"^")
					.append(vo.getStreet()+"^")
					.append(vo.getZipCode()+"^")
					.append(vo.getCreditCard()+"^")
					.append(vo.getGuid());
			stringBuilder.append("\n");
		}
		
		return stringBuilder.toString();
	}

	public String getSql(String sqlId) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document = builder.parse("src/main/resource/sql/xmlData_SQL.xml");// 이거 설명해야됨 (xml 파일말고 문자열 자체를

		Element root = document.getDocumentElement();// root 요소 가져오기
		NodeList rootNodeList = root.getChildNodes();// root 요소 확인 : 첫 태그 sql
		for (int i = 0; i < rootNodeList.getLength(); i++) {
			//공백이 아닐 경우
			if (rootNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element sqlElement = (Element) rootNodeList.item(i);
				if ((sqlId.equals(sqlElement.getAttribute("id")))) {
					return sqlElement.getTextContent();
				} 
			}
		}
		return null;
	}
	
	// 원래는 jaxb를 이용하여 header 와 detail vo에 따로 담은 후 join 한 vo 에 매핑을 하려고 했으나 map을 통해
	// xml을 핸들링하고 한번에 joinvo로
	// 변환하는 거
//	public void deserializationXmlData() throws ParserConfigurationException, SAXException, IOException {
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = factory.newDocumentBuilder();
//		
//		Document document = builder.parse(new InputSource(new StringReader(this.xmlData)));// 이거 설명해야됨 (xml 파일말고 문자열 자체를
//		
//		Element root = document.getDocumentElement();// root 요소 가져오기
//		NodeList purchaseOrder = root.getChildNodes();// root 요소 확인 : 첫 태그 PurchaseOrder
//		Node firstNode = root.getFirstChild();// root 요소의 첫번째 노드는 #Text >> 빈 공백
//		Node tableName = firstNode.getNextSibling();// 다음 노드는 HEADER, DETAIL
//
//		//데이터 타입에 따라 다르게 VO 에 넣는게 구현이 너무 어렵고 비 효율적이라고 생각해서 일단 List Map으로 변환함
//		
//		List<Map<String,String>> headerList = new ArrayList<>();
//		List<Map<String,String>> detailList = new ArrayList<>();
//		
//		for (int i = 0; i < purchaseOrder.getLength(); i++) {
//			Map<String, String> columns = new HashMap<>();
//			Map<String, String> headerCol = new HashMap<>();
//			Map<String, String> detailCol = new HashMap<>();
//			Element rowElement = (Element) purchaseOrder.item(i); // 루트 노드의 자식 노드들 중 하나씩 element로 설정
//			NodeList rowNodeList = rowElement.getChildNodes(); //각각의 element에서 하위 노드 리스트를 뽑음
//			
//			for (int j = 0; j < rowNodeList.getLength(); j++) {
//				Element columnElement = (Element) rowNodeList.item(j); // <HEADER>의 하위 요소들
//				String rowName = rowElement.getNodeName();
//				if ("HEADER".equals(rowName)) {
//					headerCol.put(columnElement.getNodeName(), columnElement.getTextContent());
//				} else if ("DETAIL".equals(rowName)) {
//					detailCol.put(columnElement.getNodeName(), columnElement.getTextContent());
//				}
////				columns.put(columnElement.getNodeName(), columnElement.getTextContent());
//			}
//			this.dataList.add(columns);
//		}
//	}
}
