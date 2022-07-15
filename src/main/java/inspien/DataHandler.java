package inspien;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import inspien.vo.ConnectionInfoVo;
import inspien.vo.DetailVo;
import inspien.vo.HeaderVo;
import inspien.vo.JoinVo;
import inspien.vo.RecordVo;
import inspien.vo.xmlWrapper.DetailVoXmlWrapper;
import inspien.vo.xmlWrapper.HeaderVoXmlWrapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
/*각종 데이터들을 변환하고 핸들링 해주는 클래스*/
public class DataHandler {
	/*jackson에서 제공하는 매퍼 객체*/
	private ObjectMapper objectMapper = new ObjectMapper();

	/*jackson 라이브러리에서 제공하는 객체를 이용해 문자열 형태의 JSON 데이터를(연결정보) ConnectionInfoVo 형태로 변환해주는 메소드*/
	public ConnectionInfoVo connectionInfoTextToVo(String data) throws JsonMappingException, JsonProcessingException {
		return this.objectMapper.readValue(data, ConnectionInfoVo.class);
	}

	/* request요청(서비스 호출) 후 전달받은 데이터 중 JSON 과 XML 데이터를 디코딩 및 컨텐츠타입을 변환해주는 메소드 */
	public String dataDecoding(String data, String contentType) throws UnsupportedEncodingException {
		byte[] decodedData = Base64.getDecoder().decode(data);
		return new String(decodedData, contentType);
	}

	/* jaxb 라이브러리를 이용해 문자열 형태의 xml 데이터를 두 개의 List<VO> 형태로 변환 후 다시 하나의 List<VO> 객체에 삽입 (left join)하는 메소드*/
	public List<JoinVo> xmlTextToVoList(String xmlText) throws JAXBException {
		List<JoinVo> joinVoList = new ArrayList<>();

		JAXBContext jaxbContext = JAXBContext.newInstance(HeaderVoXmlWrapper.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		List<HeaderVo> headerVoList = ((HeaderVoXmlWrapper) unmarshaller.unmarshal(new StringReader(xmlText)))
				.getHeaderVoList();

		jaxbContext = JAXBContext.newInstance(DetailVoXmlWrapper.class); 
		unmarshaller = jaxbContext.createUnmarshaller(); 
		List<DetailVo> detailVoList = ((DetailVoXmlWrapper) unmarshaller.unmarshal(new StringReader(xmlText)))
				.getDetailVoList(); // unmarshal 후 반환된 리스트

		// headerVo 와 detailVo 에서 orderNum 이같은 것들끼리 left join 하여 joinVo 에 매핑
		int detailCursor = 0;
		int count = 0;
		for (int i = 0; i < headerVoList.size(); i++) {
			HeaderVo headerVo = headerVoList.get(i);
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
				joinVoList.add(joinVo);
			}
		}

		return joinVoList;
	}

	/*jackson 라이브러리에서 제공하는 객체를 이용해 connection info 데이터 중 문자열 형태의 json 데이터를 List<VO> 형태로 변환하는 메소드*/
	public List<RecordVo> jsonTextToVoList(String jsontext) throws JsonMappingException, JsonProcessingException {
		return this.objectMapper.readValue(jsontext, new TypeReference<List<RecordVo>>() {
		});
	}

	/*list<VO> 자료구조 형태의 데이터를 파일에 쓰기 위해 문자열로 변환하는 메소드*/
	public String recordVoListToText(List<RecordVo> list) {
		StringBuilder stringBuilder = new StringBuilder();

		Field[] fieldArr = RecordVo.class.getDeclaredFields();

		int fieldCount = fieldArr.length;
		for (RecordVo vo : list) {
			stringBuilder.append(vo.getNames() + "^").append(vo.getPhone() + "^").append(vo.getEmail() + "^")
					.append(vo.getBirthDate() + "^").append(vo.getCompany() + "^").append(vo.getPersonalNumber() + "^")
					.append(vo.getCountry() + "^").append(vo.getRegion() + "^").append(vo.getCity() + "^")
					.append(vo.getStreet() + "^").append(vo.getZipCode() + "^").append(vo.getCreditCard() + "^")
					.append(vo.getGuid());
			stringBuilder.append("\n");
		}

		return stringBuilder.toString();
	}

	// 원래는 jaxb를 이용하지 않고 각각의 Map 이나 Vo에 담으려 했었음
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
