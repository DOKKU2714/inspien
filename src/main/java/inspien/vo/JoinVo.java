package inspien.vo;

import java.time.LocalDate;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

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
public class JoinVo {
	private int orderNum;
	private String orderId;
	private LocalDate orderDate;
	private int orderPrice;
	private int orderQty;
	private String receiverName;
	private String receiverNo;
	private LocalDate etaDate;
	private String destination;
	private String desciption;
	private int itemSeq;
	private String itemName;
	private int itemQty;
	private String itemColor;
	private int itemPrice;
}
