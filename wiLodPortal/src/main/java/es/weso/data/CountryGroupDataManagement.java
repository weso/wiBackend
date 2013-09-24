package es.weso.data;

import java.util.Collection;

import es.weso.model.CountryGroup;

/**
 * Defines the operations that can be performed related to {@link CountryGroup}
 * retrieval in the data layer
 * 
 * @author Alejandro Montes García
 * @since 02/07/2013
 * @version 1.0
 */
public interface CountryGroupDataManagement {

	/**
	 * Gets the information of a {@link CountryGroup} with the given name
	 * 
	 * @param name
	 *            The name of the {@link CountryGroup}
	 * @return The {@link CountryGroup} with the given name
	 */
	public CountryGroup getCountryGroup(String name);

	/**
	 * Gets all the continents available
	 * 
	 * @return A {@link Collection} containing all the continents available
	 */
	public Collection<CountryGroup> getContinents();

}
