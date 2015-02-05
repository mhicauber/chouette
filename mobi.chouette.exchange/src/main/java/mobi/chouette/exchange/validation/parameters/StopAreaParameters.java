package mobi.chouette.exchange.validation.parameters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class StopAreaParameters {

	@XmlElement(name = "objectid")
	private FieldParameters objectid;

	@XmlElement(name = "name")
	private FieldParameters name;

	@XmlElement(name = "registration_number")
	private FieldParameters registrationNumber;

	@XmlElement(name = "city_name")
	private FieldParameters cityName;

	@XmlElement(name = "country_code")
	private FieldParameters countryCode;

	@XmlElement(name = "zip_code")
	private FieldParameters zipCode;

}