package inspien.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class RecordVo {
	private String names;
	private String phone;
	private String email;
	private String birthDate;
	private String company;
	private String personalNumber;
	private String organisationNumber;
	private String country;
	private String region;
	private String city;
	private String street;
	private String zipCode;
	private String creditCard;
	private String guid;
}
