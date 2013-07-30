package es.weso.business;

import java.util.Collection;

import es.weso.model.CountryGroup;

/**
 * Defines the operations that can be performed related to {@link CountryGroup}
 * retrieval
 * 
 * @author Alejandro Montes García
 * @since 01/07/2013
 * @version 1.0
 */
public interface CountryGroupManagement {

	/**
	 * Gets all the {@link CountryGroup}s (continents and organizations)
	 * 
	 * @return A {@link Collection} with all the {@link CountryGroup}s
	 */
	public Collection<CountryGroup> getAllCountryGroups();

	/**
	 * Gets all the {@link CountryGroup}s that are continents XOR organizations
	 * 
	 * @param isContinent
	 *            <code>true</code> if the method is wanted to return
	 *            continents, <code>false</code> if it is wanted to return
	 *            organizations
	 * @return All the {@link CountryGroup}s that are continents XOR
	 *         organizations
	 */
	public Collection<CountryGroup> getAllCountryGroupsByType(
			boolean isContinent);

	/**
	 * Gets the data of a specific {@link CountryGroup}
	 * 
	 * @param name
	 *            The name of the desired {@link CountryGroup}
	 * @return The {@link CountryGroup} with the given name
	 */
	public CountryGroup getCountryGroup(String name);
}
