package mobi.chouette.exchange.kml.exporter;

import java.sql.Date;

import lombok.extern.log4j.Log4j;
import mobi.chouette.exchange.exporter.DataCollector;
import mobi.chouette.model.Line;

@Log4j
public class KmlDataCollector extends DataCollector{
	public boolean collect(ExportableData collection, Line line, Date startDate, Date endDate) {
		return collect(collection,line,startDate,endDate,true,true);
			
	}

	


	
}
