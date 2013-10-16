package es.weso.wiLodPortal.business;

import java.util.Collection;

import models.CountryGroup;

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
	 * Gets all the {@link CountryGroup}s
	 * 
	 * @return A {@link Collection} with all the {@link CountryGroup}s
	 */
	public Collection<CountryGroup> getAllCountryGroups();


	/**
	 * Gets the data of a specific {@link CountryGroup}
	 * 
	 * @param name
	 *            The name of the desired {@link CountryGroup}
	 * @return The {@link CountryGroup} with the given name
	 */
	public CountryGroup getCountryGroup(String name);
}
