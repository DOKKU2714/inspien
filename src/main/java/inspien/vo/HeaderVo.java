package inspien.vo;


import java.time.LocalDate;
public class HeaderVo {
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

	public HeaderVo() {
	}

	public HeaderVo(int orderNum, String orderId, LocalDate orderDate, int orderPrice, int orderQty, String receiverName,
			String receiverNo, LocalDate etaDate, String destination, String desciption) {
		this.orderNum = orderNum;
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.orderPrice = orderPrice;
		this.orderQty = orderQty;
		this.receiverName = receiverName;
		this.receiverNo = receiverNo;
		this.etaDate = etaDate;
		this.destination = destination;
		this.desciption = desciption;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverNo() {
		return receiverNo;
	}

	public void setReceiverNo(String receiverNo) {
		this.receiverNo = receiverNo;
	}

	public LocalDate getEtaDate() {
		return etaDate;
	}

	public void setEtaDate(LocalDate etaDate) {
		this.etaDate = etaDate;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	@Override
	public String toString() {
		return "HeaderVo [orderNum=" + orderNum + ", orderId=" + orderId + ", orderDate=" + orderDate + ", orderPrice="
				+ orderPrice + ", orderQty=" + orderQty + ", receiverName=" + receiverName + ", receiverNo="
				+ receiverNo + ", etaDate=" + etaDate + ", destination=" + destination + ", desciption=" + desciption
				+ "]";
	}
	
	//빌더패턴 취소
//	public static class HeaderVoBuilder {
//		private int orderNum;
//		private String orderId;
//		private LocalDate orderDate;
//		private int orderPrice;
//		private int orderQty;
//		private String receiverName;
//		private String receiverNo;
//		private LocalDate etaDate;
//		private String destination;
//		private String desciption;
//		
//		public HeaderVoBuilder() {
//			
//		}
//		
//		public HeaderVoBuilder setOrderNum(int orderNum) {
//			this.orderNum = orderNum;
//			return this;
//		}
//
//		public HeaderVoBuilder setOrderId(String orderId) {
//			this.orderId = orderId;
//			return this;
//		}
//
//		public HeaderVoBuilder setOrderDate(LocalDate orderDate) {
//			this.orderDate = orderDate;
//			return this;
//		}
//
//		public HeaderVoBuilder setOrderPrice(int orderPrice) {
//			this.orderPrice = orderPrice;
//			return this;
//		}
//
//		public HeaderVoBuilder setOrderQty(int orderQty) {
//			this.orderQty = orderQty;
//			return this;
//		}
//
//		public HeaderVoBuilder setReceiverName(String receiverName) {
//			this.receiverName = receiverName;
//			return this;
//		}
//
//		public HeaderVoBuilder setReceiverNo(String receiverNo) {
//			this.receiverNo = receiverNo;
//			return this;
//		}
//
//		public HeaderVoBuilder setEtaDate(LocalDate etaDate) {
//			this.etaDate = etaDate;
//			return this;
//		}
//
//		public HeaderVoBuilder setDestination(String destination) {
//			this.destination = destination;
//			return this;
//		}
//
//		public HeaderVoBuilder setDesciption(String desciption) {
//			this.desciption = desciption;
//			return this;
//		}
//		
//		public HeaderVo build() {
//			HeaderVo headerVo = new HeaderVo();
//			headerVo.orderNum = orderNum;
//			headerVo.orderId = orderId;
//			headerVo.orderDate = orderDate;
//			headerVo.orderPrice = orderPrice;
//			headerVo.orderQty = orderQty;
//			headerVo.receiverName = receiverName;
//			headerVo.receiverNo = receiverNo;
//			headerVo.etaDate = etaDate;
//			headerVo.destination = destination;
//			headerVo.desciption = desciption;
//			return headerVo;
//		}
//	}
}
