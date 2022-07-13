package inspien.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PurchaseOrder")
public class HeaderVoXmlWrapper {
	@XmlElement(name = "HEADER")
	private List<HeaderVo> headerVoList;

	public List<HeaderVo> getHeaderVoList() {
		return headerVoList;
	}

	public void setHeaderVoList(List<HeaderVo> headerVoList) {
		this.headerVoList = headerVoList;
	}
}

