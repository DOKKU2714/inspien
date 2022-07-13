package inspien.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PurchaseOrder")
public class DetailVoXmlWrapper {
	@XmlElement(name = "DETAIL")
	private List<DetailVo> detailVoList;

	public List<DetailVo> getDetailVoList() {
		return detailVoList;
	}

	public void setDetailVoList(List<DetailVo> detailVoList) {
		this.detailVoList = detailVoList;
	}
}

