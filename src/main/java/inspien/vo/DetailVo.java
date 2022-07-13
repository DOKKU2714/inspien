package inspien.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

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

	public DetailVo() {
	}
	
	public DetailVo(int orderNum, int itemSeq, String itemName, int itemQty, String itemColor, int itemPrice) {
		this.orderNum = orderNum;
		this.itemSeq = itemSeq;
		this.itemName = itemName;
		this.itemQty = itemQty;
		this.itemColor = itemColor;
		this.itemPrice = itemPrice;
	}
	
	public int getOrderNum() {
		return orderNum;
	}
	
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getItemSeq() {
		return itemSeq;
	}

	public void setItemSeq(int itemSeq) {
		this.itemSeq = itemSeq;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}

	public String getItemColor() {
		return itemColor;
	}

	public void setItemColor(String itemColor) {
		this.itemColor = itemColor;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	@Override
	public String toString() {
		return "DetailVo [orderNum=" + orderNum + ", itemSeq=" + itemSeq + ", itemName=" + itemName + ", itemQty="
				+ itemQty + ", itemColor=" + itemColor + ", itemPrice=" + itemPrice + "]";
	}
	

}

