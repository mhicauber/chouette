package fr.certu.chouette.exchange.gtfs.refactor.importer;

import java.io.IOException;

import fr.certu.chouette.exchange.gtfs.refactor.model.GtfsTrip;

public class TripsImporter extends ImporterImpl<GtfsTrip>
{

   public static enum FIELDS
   {
      route_id, service_id, trip_id, trip_headsign, trip_short_name, direction_id, shape_id;
   };

   public static final String FILENAME = "trips.txt";
   public static final String KEY = FIELDS.trip_id.name();

   private GtfsTrip bean = new GtfsTrip();
   
   public TripsImporter(String name) throws IOException
   {
      super(name, KEY);
   }

   @Override
   protected GtfsTrip build(GtfsIterator reader, int id)
   {
      return bean;
   }

   @Override
   public boolean validate(GtfsTrip bean, GtfsImporter dao)
   {
      return true;
   }

   public static class DefaultImporterFactory extends ImporterFactory
   {
      @Override
      protected Importer<GtfsTrip> create(String name) throws IOException
      {
         return new TripsImporter(name);
      }
   }

   static
   {
      ImporterFactory factory = new DefaultImporterFactory();
      ImporterFactory.factories.put(TripsImporter.class.getName(), factory);
   }
}