package mobi.chouette.exchange.report;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"code","description"})
@AllArgsConstructor
@Data
public class LineError {
	
	@XmlType(name="LineCode")
	@XmlEnum
	public enum CODE 
	{
		DATA_NOT_FOUND,
		NO_DATA_ON_PERIOD,
		READ_ERROR,
		WRITE_ERROR,
		INVALID_FORMAT,
		INTERNAL_ERROR
	};
	
	@XmlElement(name="code",required=true)
	@NonNull
	private CODE code;
	
	@XmlElement(name="description",required=true)
	@NonNull
	private String description;
	
}