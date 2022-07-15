package inspien.vo;


import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import inspien.adapter.LocalDateAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "HEADER")
/*제공받은 XML 데이터 중 2개중 HEADER 라는 object 데이터를 매핑하는 VO 형태의 클래스
 * jaxb 라는 라이브러리를 이용해 자동으로 매핑된다.*/
public class HeaderVo {
	@XmlElement(name = "ORDER_NUM")
	private int orderNum;
	
	@XmlElement(name = "ORDER_ID")
	private String orderId;
	
	@XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateAdapter.class)
	@XmlElement(name = "ORDER_DATE")
	private LocalDate orderDate;
	
	@XmlElement(name = "ORDER_PRICE")
	private int orderPrice;
	
	@XmlElement(name = "ORDER_QTY")
	private int orderQty;
	
	@XmlElement(name = "RECEIVER_NAME")
	private String receiverName;
	
	@XmlElement(name = "RECEIVER_NO")
	private String receiverNo;
	@XmlJavaTypeAdapter(type=LocalDate.class, value = LocalDateAdapter.class)
	@XmlElement(name = "ETA_DATE")
	private LocalDate etaDate;
	
	@XmlElement(name = "DESTINATION")
	private String destination;
	
	@XmlElement(name = "DESCIPTION")
	private String desciption;
}
