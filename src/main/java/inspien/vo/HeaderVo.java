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
