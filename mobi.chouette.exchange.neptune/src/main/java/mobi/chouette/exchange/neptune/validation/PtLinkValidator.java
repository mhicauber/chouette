package mobi.chouette.exchange.neptune.validation;


import mobi.chouette.common.Context;
import mobi.chouette.exchange.neptune.Constant;
import mobi.chouette.exchange.neptune.model.PTLink;
import mobi.chouette.exchange.validation.ValidationConstraints;
import mobi.chouette.exchange.validation.ValidationException;
import mobi.chouette.exchange.validation.Validator;
import mobi.chouette.exchange.validation.ValidatorFactory;
import mobi.chouette.exchange.validation.report.FileLocation;
import mobi.chouette.model.ConnectionLink;
import mobi.chouette.model.util.Referential;

public class PtLinkValidator extends AbstractValidator implements Validator<PTLink> , Constant{

	public static final String END_OF_LINK_ID = "endOfLinkId";

	public static final String START_OF_LINK_ID = "startOfLinkId";

	public static String NAME = "PtLinkValidator";

	private static final String PT_LINK_1 = "2-NEPTUNE-PtLink-1";

	public static final String LOCAL_CONTEXT = "PTLink";


	public PtLinkValidator(Context context) 
	{
		addItemToValidation( context, prefix, "PtLink", 1, "E");

	}

	public void addLocation(Context context, String objectId, int lineNumber, int columnNumber)
	{
		Context objectContext = getObjectContext(context, LOCAL_CONTEXT, objectId);
		objectContext.put(LINE_NUMBER, Integer.valueOf(lineNumber));
		objectContext.put(COLUMN_NUMBER, Integer.valueOf(columnNumber));

	}
	public void addStartOfLinkId(Context  context, String objectId, String linkId)
	{
		Context objectContext = getObjectContext(context, LOCAL_CONTEXT, objectId);
		
			objectContext.put(START_OF_LINK_ID, linkId);
		
	}

	public void addEndOfLinkId(Context  context, String objectId, String linkId)
	{
		Context objectContext = getObjectContext(context, LOCAL_CONTEXT, objectId);
		
			objectContext.put(END_OF_LINK_ID, linkId);
	}


	@Override
	public ValidationConstraints validate(Context context, PTLink target) throws ValidationException
	{
		Context validationContext = (Context) context.get(VALIDATION_CONTEXT);
		Context localContext = (Context) validationContext.get(LOCAL_CONTEXT);
		Context stopAreaContext = (Context) validationContext.get(StopAreaValidator.LOCAL_CONTEXT);
		if (localContext == null || localContext.isEmpty()) return new ValidationConstraints();

		Referential referential = (Referential) context.get(REFERENTIAL);
		String fileName = (String) context.get(FILE_NAME);

		for (String objectId : localContext.keySet()) 
		{
			Context objectContext = (Context) localContext.get(objectId);
			ConnectionLink connectionLink = referential.getConnectionLinks().get(objectId);
			int lineNumber = ((Integer) objectContext.get(LINE_NUMBER)).intValue();
			int columnNumber = ((Integer) objectContext.get(COLUMN_NUMBER)).intValue();
			FileLocation sourceLocation = new FileLocation(fileName, lineNumber, columnNumber);


		}
		return new ValidationConstraints();
	}

	public static class DefaultValidatorFactory extends ValidatorFactory {



		@Override
		protected Validator<PTLink> create(Context context) {
			PtLinkValidator instance = (PtLinkValidator) context.get(NAME);
			if (instance == null) {
				instance = new PtLinkValidator(context);
				context.put(NAME, instance);
			}
			return instance;
		}

	}

	static {
		ValidatorFactory.factories
		.put(PtLinkValidator.class.getName(), new DefaultValidatorFactory());
	}



}
