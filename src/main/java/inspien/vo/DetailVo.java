package inspien.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

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
public class DetailVo {
	@XmlElement(name = "ORDER_NUM")
	private int orderNum;
	
	@XmlElement(name = "ITEM_SEQ")
	private int itemSeq;
	
	@XmlElement(name = "ITEM_NAME")
	private String itemName;
	
	@XmlElement(name = "ITEM_QTY")
	private int itemQty;
	
	@XmlElement(name = "ITEM_COLOR")
	private String itemColor;
	
	@XmlElement(name = "ITEM_PRICE")
	private int itemPrice;
}

