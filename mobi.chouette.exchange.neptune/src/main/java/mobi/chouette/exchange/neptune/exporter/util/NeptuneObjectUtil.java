package mobi.chouette.exchange.neptune.exporter.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobi.chouette.exchange.neptune.model.PTLink;
import mobi.chouette.model.NeptuneIdentifiedObject;
import mobi.chouette.model.Route;
import mobi.chouette.model.StopPoint;
import mobi.chouette.model.util.NeptuneUtil;

public class NeptuneObjectUtil extends NeptuneUtil {


	/**
	 * build PTLink list of route
	 */
	public static List<PTLink> getPtLinks(Route route)
	{

		List<PTLink>   ptLinks = new ArrayList<PTLink>();
		ptLinks.clear();
		String baseId = route.getObjectId().split(":")[0] + ":"
				+ NeptuneIdentifiedObject.PTLINK_KEY + ":";
		for (int rank = 1; rank < route.getStopPoints().size(); rank++)
		{
			StopPoint start = route.getStopPoints().get(rank - 1);
			StopPoint end = route.getStopPoints().get(rank);
			PTLink link = new PTLink();

			link = new PTLink();
			link.setStartOfLink(start);
			link.setEndOfLink(end);
			String startId = start.getObjectId().split(":")[2];
			String endId = end.getObjectId().split(":")[2];
			String objectId = baseId + startId + "A" + endId;
			link.setObjectId(objectId);
			link.setCreationTime(new Date());
			link.setRoute(route);
			ptLinks.add(link);
		}
		return ptLinks;
	}
	

}