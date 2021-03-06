package es.weso.wiLodPortal.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import play.Play;

/**
 * Loads externalized data like queries or configurations from the file that
 * apply
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @version 1.0
 * @since 27/03/2013
 */
public class Conf {

	private static Properties config, queries;

	private static final String QUERIES_FILE = "queries.properties";
	private static final String CONFIG_FILE = "config.properties";

	/**
	 * Gets properties from the configuration file
	 * 
	 * @param propertyName
	 *            The name of the property
	 * @return The value of the property
	 */
	public static String getConfig(String propertyName) {
		return getPropertyFromFile(CONFIG_FILE, config, propertyName);
	}

	/**
	 * Gets a query from the queries file. The queries are stored in a
	 * properties file, if they require any argument, those are specified in the
	 * file with {i}, being i a number from zero to the maximum number of
	 * arguments
	 * 
	 * @param queryName
	 *            The name of the query
	 * @param args
	 *            (Optional) The arguments that the query is taking
	 * @return The query with the {i} arguments replaced for the actual ones
	 */
	public static String getQuery(String queryName, String... args) {
		String query = getPropertyFromFile(QUERIES_FILE, queries, queryName);
		int i = 0;
		for (String arg : args) {
			query = query.replace("{" + i + "}", arg.trim());
			i++;
		}
		return query;
	}

	/**
	 * Gets a query from the queries file. The queries are stored in a
	 * properties file, the argument is specified in the file with {i}
	 * 
	 * @param queryName
	 *            The name of the query
	 * @param argsList
	 *            (Optional) The arguments that the query is taking that will be
	 *            joined to a specific filter
	 * @return The query with the {i} filters replaced with the possible values
	 *         they might take
	 */
	public static String getQueryWithFilters(String queryName,
			Collection<String>... argsList) {
		String query = getPropertyFromFile(QUERIES_FILE, queries, queryName);
		int i = 0;
		for (Collection<String> arg : argsList) {
			String[] clauses = query.split(" \\.");
			for (String clause : clauses) {
				if (clause.contains("{" + i + "}")) {
					String filterCondition = clause.substring(
							clause.indexOf('(') + 1, clause.indexOf(')'));
					String expandedFilterCondition = "";
					for (String value : arg) {
						if (expandedFilterCondition.length() > 0) {
							expandedFilterCondition += " || ";
						}
						expandedFilterCondition += filterCondition.replace("{"
								+ i + "}", value);
					}
					if(expandedFilterCondition.equals("")) {
						query = query.replace(clause + " .", "");
					} else {
						query = query.replace(filterCondition,
								expandedFilterCondition);
					}
				}
			}
			i++;
		}
		return query;
	}

	/**
	 * Gets a query from the queries file. The queries are stored in a
	 * properties file, the argument is specified in the file with {0}
	 * 
	 * @param queryName
	 *            The name of the query
	 * @param argsList
	 *            The possible values of the argument, those will be joint with
	 *            an <tt>UNION</tt> clause, that works like an <tt>OR</tt>.
	 * @return The query with the {0} argument replaced with the possible values
	 *         it might take
	 */
	public static String getQuery(String queryName, Collection<String> argsList) {
		String query = getPropertyFromFile(QUERIES_FILE, queries, queryName);
		if (argsList.isEmpty()) {
			return query.replace("{0}", "0");
		}
		String[] clauses = query.split("\\.");
		for (String clause : clauses) {
			if (clause.contains("{0}")) {
				StringBuilder union = new StringBuilder();
				String condition = clause.subSequence(0, clause.indexOf("'"))
						.toString();
				boolean first = true;
				for (String arg : argsList) {
					if (!first) {
						union.append(" UNION ");
					} else {
						first = false;
					}
					union = union.append("{ ").append(condition).append("'")
							.append(arg).append("' }");
				}
				query = query.replace(clause, union);
				break;
			}
		}
		return query;
	}

	/**
	 * Gets a property from a file
	 * 
	 * @param fileName
	 *            The name of the properties file
	 * @param prop
	 *            The properties file
	 * @param propertyName
	 *            The name of the property
	 * @return The value of the property
	 */
	private static String getPropertyFromFile(String fileName, Properties prop,
			String propertyName) {
		if (prop == null) {
			prop = new Properties();
			try {
				prop.load(Play.application().resourceAsStream(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop.getProperty(propertyName);
	}

}
