package de.fuberlin.wiwiss.pubby.servlets;
import java.util.Collection;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;

import de.fuberlin.wiwiss.pubby.MappedResource;

/**
 * A servlet for serving an RDF document describing the blank nodes
 * related to a given resource via a given property.
 * 
 * @author Richard Cyganiak (richard@cyganiak.de)
 * @version $Id$
 */
public class PathDataURLServlet extends BasePathDataServlet {

	@Override
	public Model getData(Collection<MappedResource> resources,
			Property property, boolean isInverse) {
		return listPropertyValues(resources, property, isInverse, true);
	}

	@Override
	public String getDocumentTitle(String resourceLabel, String propertyLabel,
			boolean isInverse) {
		if (isInverse) {
			return "RDF description of blank nodes whose " + propertyLabel + " is " + resourceLabel;
		} else {
			return "RDF description of blank nodes that are " + propertyLabel + " of " + resourceLabel;
		}
	}

	private static final long serialVersionUID = -7927775670218866340L;
}