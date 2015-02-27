package mobi.chouette.exchange.report;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ZipInfo {
	
	@XmlElement(name = "status")
	private String status;
	
	@XmlElement(name="name")
	private String name;
	
	@XmlElement(name="error")
	private List<String> errors;
	
	public void addError(String error)
	{
		if (errors == null) errors = new ArrayList<>();
		errors.add(error);
	}


}