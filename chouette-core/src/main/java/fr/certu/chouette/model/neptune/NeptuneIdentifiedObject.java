/**
 * Projet CHOUETTE
 *
 * ce projet est sous license libre
 * voir LICENSE.txt pour plus de details
 *
 */
package fr.certu.chouette.model.neptune;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
public abstract class NeptuneIdentifiedObject extends NeptuneObject
{
	@Getter @Setter private String objectId;
	@Getter @Setter private int objectVersion = 1; // TODO verifier la valeur par defaut
	@Getter @Setter private Date creationTime;
	@Getter @Setter private String creatorId;
	@Getter @Setter private String name;

	/* (non-Javadoc)
	 * @see fr.certu.chouette.model.neptune.NeptuneObject#toString(java.lang.String, int)
	 */
	@Override
	public String toString(String indent, int level)
	{
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
		String s = super.toString(indent,level);
		s += "\n"+indent+"  objectId = "+objectId;
		s += "\n"+indent+"  objectVersion = "+objectVersion;
		if (creationTime != null)
			s += "\n"+indent+"  creationTime = "+f.format(creationTime);
		if (creatorId != null)
			s += "\n"+indent+"  creatorId = "+creatorId;
		if (name != null)
			s += "\n"+indent+"  name = "+name;

		return s;
	}

	public static List<String> extractObjectIds(List<? extends NeptuneIdentifiedObject> neptuneObjects){
		List<String> objectIds = new ArrayList<String>();
			if(neptuneObjects != null){
			for (NeptuneIdentifiedObject neptuneObject : neptuneObjects) {
				if(neptuneObject != null){
					String objectId = neptuneObject.getObjectId();
					if(objectId != null){
						objectIds.add(objectId);
					}
				}
			}
		}
			
		return objectIds;
	}
	
}
