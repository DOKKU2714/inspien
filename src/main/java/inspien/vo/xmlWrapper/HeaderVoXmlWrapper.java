package inspien.vo.xmlWrapper;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import inspien.vo.HeaderVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PurchaseOrder")
/*제공받은 XML 데이터 중 HEADER 데이터를 jaxb라는 라이브러리로 자동 매핑하는데 있어서 Lisst<Vo> 형태로 매핑하기 위한 클래스*/
public class HeaderVoXmlWrapper {
	@XmlElement(name = "HEADER")
	private List<HeaderVo> headerVoList;
}

