package es.weso.data;

/**
 * Defines auxiliar operations to retrieve data
 * 
 * @author Alejandro Montes García
 * @since 09/07/2013
 * @version 1.0
 */
public interface AuxiliarDataManager {

	/**
	 * Gets the ISO3 code from the ISO2 code of a country
	 * 
	 * @param ISO2Code
	 *            The ISO2 code of the country
	 * @return The ISO3 code of the country
	 */
	public String toISO3Code(String ISO2Code);
}
