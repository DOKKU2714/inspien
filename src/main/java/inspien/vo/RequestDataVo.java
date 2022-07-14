package inspien.vo;

import java.util.Map;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.UpperSnakeCaseStrategy.class) //json 데이터가 snake case 이기때문에 camelcase로 자동 맵핑하도록 함
public class RequestDataVo {
	private String xmlData;
	private String jsonData;
	private Map<String, String> dbConnInfo;
	private Map<String, String> ftpConnInfo;
}
