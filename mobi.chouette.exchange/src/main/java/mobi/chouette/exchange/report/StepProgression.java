
package mobi.chouette.exchange.report;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepProgression {
	
	@XmlType(name="step")
	@XmlEnum
	public enum STEP {
		INITIALISATION,
		PROCESSING,
		FINALISATION
	};
    @XmlElement( name = "step",required=true)
    private STEP step;
	
    @XmlElement(name = "total",required=true)
	private int total = 0;

    @XmlElement(name = "realized",required=true)
	private int realized = 0;


}
