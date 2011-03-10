package fr.certu.chouette.validation.test;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.operation.distance.DistanceOp;

import fr.certu.chouette.model.neptune.PTLink;
import fr.certu.chouette.model.neptune.Route;
import fr.certu.chouette.model.neptune.StopPoint;
import fr.certu.chouette.plugin.report.Report;
import fr.certu.chouette.plugin.report.ReportItem;
import fr.certu.chouette.plugin.validation.IValidationPlugin;
import fr.certu.chouette.plugin.validation.ValidationClassReportItem;
import fr.certu.chouette.plugin.validation.ValidationParameters;
import fr.certu.chouette.plugin.validation.ValidationStepDescription;
import fr.certu.chouette.validation.report.DetailReportItem;
import fr.certu.chouette.validation.report.SheetReportItem;

/**
 * 
 * @author mamadou keira
 *
 */
public class ValidationStopPoint implements IValidationPlugin<StopPoint>{
	private ValidationStepDescription validationStepDescription;

	public void init(){
		//TODO
		validationStepDescription = new ValidationStepDescription("", ValidationClassReportItem.CLASS.TWO.ordinal());
	}

	@Override
	public ValidationStepDescription getDescription() {
		return validationStepDescription;
	}
	@Override
	public List<ValidationClassReportItem> doValidate(List<StopPoint> beans,ValidationParameters parameters) {	
		System.out.println("StopPointValidation "+beans.size());
		return validate(beans,parameters);	
	}

	private List<ValidationClassReportItem> validate(List<StopPoint> stopPoints,ValidationParameters parameters){

		ValidationClassReportItem category2 = new ValidationClassReportItem(ValidationClassReportItem.CLASS.TWO);
		ValidationClassReportItem category3 = new ValidationClassReportItem(ValidationClassReportItem.CLASS.THREE);

		ReportItem sheet10 = new SheetReportItem("Test2_Sheet10",10);
		ReportItem sheet11 = new SheetReportItem("Test2_Sheet11",11);

		ReportItem sheet3_1 = new SheetReportItem("Test3_Sheet1",1);
		ReportItem sheet3_2 = new SheetReportItem("Test3_Sheet2",2);
		ReportItem sheet3_3 = new SheetReportItem("Test3_Sheet3",3);
		ReportItem sheet3_5 = new SheetReportItem("Test3_Sheet5",5);
		ReportItem sheet3_6 = new SheetReportItem("Test3_Sheet6",6);
		ReportItem sheet3_10 = new SheetReportItem("Test3_Sheet10",10);

		SheetReportItem report2_10_1 = new SheetReportItem("Test2_Sheet10_Step1",1);
		SheetReportItem report2_11_1 = new SheetReportItem("Test2_Sheet11_Step1",1);
		SheetReportItem report3_1_1 = new SheetReportItem("Test3_Sheet1_Step1",1);
		SheetReportItem report3_2_1 = new SheetReportItem("Test3_Sheet2_Step1",1);
		SheetReportItem report3_3_1 = new SheetReportItem("Test3_Sheet3_Step1",1);
		SheetReportItem report3_5_1 = new SheetReportItem("Test3_Sheet5_Step1",1);
		SheetReportItem report3_6_1 = new SheetReportItem("Test3_Sheet6_Step1",1);
		SheetReportItem report3_10_1 = new SheetReportItem("Test3_Sheet10_Step1",1);
		SheetReportItem report3_10_2 = new SheetReportItem("Test3_Sheet10_Step2",2);
		SheetReportItem report3_10_3 = new SheetReportItem("Test3_Sheet10_Step3",3);

		List<ValidationClassReportItem> result = new ArrayList<ValidationClassReportItem>();

		int size = stopPoints.size();
		for (int i=0;i<size;i++) {
			StopPoint stopPoint = stopPoints.get(i);

			//Test2.10.1
			String lineIdShortcut = stopPoint.getLineIdShortcut();
			if(lineIdShortcut != null){
				String lineObjectId = stopPoint.getLine().getObjectId();
				if(!lineIdShortcut.equals(lineObjectId))
				{
					ReportItem detailReportItem = new DetailReportItem("Test2_Sheet10_Step1_error", Report.STATE.ERROR, "");
					report2_10_1.addItem(detailReportItem);	
				}
				else
				{
					report2_10_1.updateStatus(Report.STATE.OK);	
				}
			}
			//Test2.11.1
			String ptNetworkIdShortcut = stopPoint.getPtNetworkIdShortcut();
			if(ptNetworkIdShortcut != null){
				String ptNetworkObjectId = stopPoint.getPtNetwork().getObjectId();
				if(!ptNetworkIdShortcut.equals(ptNetworkObjectId)){
					ReportItem detailReportItem = new DetailReportItem("Test2_Sheet11_Step1_error", Report.STATE.ERROR,"");
					report2_11_1.addItem(detailReportItem);	
				}else {
					report2_11_1.updateStatus(Report.STATE.OK);		
				}
			}
			//Category 3
			double x1 = (stopPoint.getLatitude()!=null) ? stopPoint.getLatitude().doubleValue():0;
			double y1 = (stopPoint.getLongitude()!=null) ? stopPoint.getLongitude().doubleValue():0;
			PrecisionModel precisionModel = new PrecisionModel(PrecisionModel.maximumPreciseValue);
			int SRID1 = (stopPoint.getLongLatType()!= null) ? stopPoint.getLongLatType().epsgCode() : 0;
			GeometryFactory factory1 = new GeometryFactory(precisionModel, SRID1);
			Coordinate coordinate = new Coordinate(x1, y1);
			Point point1 = factory1.createPoint(coordinate);

			for(int j=i+1;j<size;j++){
				StopPoint another = stopPoints.get(j);
				double x2 = (another.getLatitude() != null) ? another.getLatitude().doubleValue() : 0;
				double y2 = (another.getLongitude() != null) ? another.getLongitude().doubleValue() : 0;
				int SRID2 = (another.getLongLatType() != null) ? another.getLongLatType().epsgCode() : 0;
				GeometryFactory factory2 = new GeometryFactory(precisionModel, SRID2);
				Coordinate coordinate2 = new Coordinate(x2, y2);
				Point point2 = factory2.createPoint(coordinate2);
				DistanceOp distanceOp = new DistanceOp(point1, point2);
				double distance = distanceOp.distance();

				//Test 3.1.1
				float param = parameters.getTest3_1_MinimalDistance();
				if(distance < param){
					if(!stopPoint.getName().equals(another.getName())){
						ReportItem detailReportItem = new DetailReportItem("Test3_Sheet1_Step1_warning", Report.STATE.WARNING,String.valueOf(param), stopPoint.getObjectId(), another.getObjectId());
						report3_1_1.addItem(detailReportItem);	
					}else
						report3_1_1.updateStatus(Report.STATE.OK);
				}
				//Test 3.2.1
				float param2 = parameters.getTest3_2_MinimalDistance();
				if(distance < param2){
					if(!stopPoint.getContainedInStopAreaId().equals(another.getContainedInStopAreaId())){
						ReportItem detailReportItem = new DetailReportItem("Test3_Sheet2_Step1_warning", Report.STATE.WARNING,String.valueOf(param2), stopPoint.getObjectId(), another.getObjectId());
						report3_2_1.addItem(detailReportItem);	
					}else
						report3_2_1.updateStatus(Report.STATE.OK);
				}

				//Test 3.3.1
				if(stopPoint.getName().equals(another.getName()) && 
						(!stopPoint.getContainedInStopAreaId().equals(another.getContainedInStopAreaId()) || 
								stopPoint.getContainedInStopAreaId() == null || another.getContainedInStopAreaId() == null)){
					if(stopPoint.getAddress() != null && another.getAddress() != null){
						if(stopPoint.getAddress().equals(another.getAddress())){
							ReportItem detailReportItem = new DetailReportItem("Test3_Sheet3_Step1_warning", Report.STATE.WARNING,stopPoint.getObjectId(), another.getObjectId());
							report3_3_1.addItem(detailReportItem);	
						}else
							report3_3_1.updateStatus(Report.STATE.OK);	
					}
				}
			}	
			//Test 3.5.1 & Test 3.6.1 a
			StopPoint nextStopPoint = (i<stopPoints.size()-1) ? stopPoints.get(i+1) : stopPoint;
			if(!stopPoint.getObjectId().equals(nextStopPoint.getObjectId())){
				final int TEST =  99999;
				int refrencePJ1 = (stopPoint.getLongLatType() != null) ? stopPoint.getLongLatType().epsgCode() : TEST;
				int refrencePJ2 = (nextStopPoint.getLongLatType() != null) ? nextStopPoint.getLongLatType().epsgCode() : TEST;
				if(refrencePJ1 != TEST && refrencePJ2 != TEST){
					if(refrencePJ1 != refrencePJ2){
						ReportItem detailReportItem = new DetailReportItem("Test3_Sheet5_Step1_warning", Report.STATE.WARNING,stopPoint.getObjectId());
						report3_5_1.addItem(detailReportItem);	

						ReportItem detailReportItem6a = new DetailReportItem("Test3_Sheet6_Step1_warning_a", Report.STATE.WARNING,stopPoint.getObjectId());
						report3_6_1.addItem(detailReportItem6a);	
					}else {
						report3_5_1.updateStatus(Report.STATE.OK);
						report3_6_1.updateStatus(Report.STATE.OK);
					}	
				}else {
					ReportItem detailReportItem = new DetailReportItem("Test3_Sheet5_Step1_warning", Report.STATE.WARNING,stopPoint.getObjectId());
					report3_5_1.addItem(detailReportItem);

					ReportItem detailReportItem6a = new DetailReportItem("Test3_Sheet6_Step1_warning_a", Report.STATE.WARNING,stopPoint.getObjectId());
					report3_6_1.addItem(detailReportItem6a);	
				}
				//Test 3.6.1 b
				List<Coordinate> listCoordinates = parameters.getTest3_2_Polygon();
				Coordinate[] coordinates = listCoordinates.toArray(new Coordinate[0]);
				LinearRing shell = factory1.createLinearRing(coordinates);
				LinearRing[] holes = null;
				Polygon polygon = factory1.createPolygon(shell, holes);
				if(!polygon.intersects(point1)){
					ReportItem detailReportItem6b = new DetailReportItem("Test3_Sheet6_Step1_error_b", Report.STATE.ERROR,stopPoint.getObjectId());
					report3_6_1.addItem(detailReportItem6b);	
				}else	
					report3_6_1.updateStatus(Report.STATE.OK);				
			}
			//Test 3.10
			List<Route> routes = (stopPoint.getLine() != null) ? stopPoint.getLine().getRoutes(): null;
			if(routes != null){
				for (Route route : routes) {
					List<PTLink> ptLinks = route.getPtLinks();
					int count = 0;
					for (PTLink ptLink : ptLinks) {
						//Test 3.10.1 a
						if(stopPoint.getObjectId().equals(ptLink.getStartOfLink().getObjectId()) || 
								stopPoint.getObjectId().equals(ptLink.getEndOfLink().getObjectId())){
							count+=1;
						}
						//Test 3.10.2
						if(ptLink.getStartOfLink().getContainedInStopAreaId().equals(ptLink.getEndOfLink().getContainedInStopAreaId())){
							ReportItem detailReportItem = new DetailReportItem("Test3_Sheet10_Step2_warning", Report.STATE.WARNING);
							report3_10_2.addItem(detailReportItem);
						}else
							report3_10_2.updateStatus(Report.STATE.OK);
						//Test 3.10.3
						double distanceMin3_10 = parameters.getTest3_10_MinimalDistance();
						StopPoint start = ptLink.getStartOfLink();
						double xStart = (start != null && start.getLatitude()!=null) ? start.getLatitude().doubleValue():0;
						double ySart = (start != null && start.getLongitude()!=null) ? start.getLongitude().doubleValue():0;
						int SRIDStart = (start != null && start.getLongLatType()!= null) ? start.getLongLatType().epsgCode() : 0;
						GeometryFactory factoryStart = new GeometryFactory(precisionModel, SRIDStart);
						Point pointSart = factoryStart.createPoint(new Coordinate(xStart,ySart));
						
						StopPoint end = ptLink.getEndOfLink();
						double xEnd = (end != null && end.getLatitude()!=null) ? end.getLatitude().doubleValue():0;
						double yEnd = (end != null && end.getLongitude()!=null) ? end.getLongitude().doubleValue():0;
						int SRIDEnd = (end != null && end.getLongLatType()!= null) ? end.getLongLatType().epsgCode() : 0;
						GeometryFactory factoryEnd = new GeometryFactory(precisionModel, SRIDEnd);
						Point pointEnd = factoryEnd.createPoint(new Coordinate(xEnd,yEnd));
						
						DistanceOp distanceOp = new DistanceOp(pointSart, pointEnd);
						double distance = distanceOp.distance();
						if(distance < distanceMin3_10){
							ReportItem detailReportItem = new DetailReportItem("Test3_Sheet10_Step3_warning", Report.STATE.WARNING, String.valueOf(distanceMin3_10));
							report3_10_3.addItem(detailReportItem);	
						}else
							report3_10_3.updateStatus(Report.STATE.OK);	
					}
					if(count >= 2)
						report3_10_1.updateStatus(Report.STATE.OK);
					else{
						ReportItem detailReportItem = new DetailReportItem("Test3_Sheet10_Step1_error_a", Report.STATE.ERROR,stopPoint.getObjectId());
						report3_10_1.addItem(detailReportItem);		
					}
					//Test 3.10.1 b
					//TODO
				}
			}
		}
		report2_10_1.computeDetailItemCount();
		report2_11_1.computeDetailItemCount();
		report3_1_1.computeDetailItemCount();
		report3_2_1.computeDetailItemCount();
		report3_3_1.computeDetailItemCount();
		report3_5_1.computeDetailItemCount();
		report3_6_1.computeDetailItemCount();
		report3_10_1.computeDetailItemCount();
		report3_10_2.computeDetailItemCount();
		report3_10_3.computeDetailItemCount();

		sheet10.addItem(report2_10_1);
		sheet11.addItem(report2_11_1);		
		sheet3_1.addItem(report3_1_1);
		sheet3_2.addItem(report3_2_1);
		sheet3_3.addItem(report3_3_1);
		sheet3_5.addItem(report3_5_1);
		sheet3_6.addItem(report3_6_1);
		sheet3_10.addItem(report3_10_1);
		sheet3_10.addItem(report3_10_2);
		sheet3_10.addItem(report3_10_3);

		category2.addItem(sheet10);
		category2.addItem(sheet11);		
		category3.addItem(sheet3_1);
		category3.addItem(sheet3_2);
		category3.addItem(sheet3_3);
		category3.addItem(sheet3_5);
		category3.addItem(sheet3_6);
		category3.addItem(sheet3_10);

		result.add(category2);
		result.add(category3);
		return result;
	}

}