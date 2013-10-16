package es.weso.wiLodPortal.data.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import models.Component;
import models.Country;
import models.CountryForRegion;
import models.CountryGroup;
import models.Dataset;
import models.Indicator;
import models.NamedUri;
import models.Observation;
import models.Subindex;
import models.ValuedNamedUri;
import models.WeightSchema;
import es.weso.wiLodPortal.util.Conf;
import es.weso.wiLodPortal.util.JenaMemcachedClient;

/**
 * Abstract class with utility methods to get data from the
 * {@link JenaMemcachedClient}
 * 
 * @author Alejandro Montes García
 * @since 04/07/2013
 * @version 1.0
 */
public abstract class AbstractDataManager {

	protected JenaMemcachedClient client;

	public String toISO3Code(String ISO2Code) {
		return getString(client.executeQuery(Conf.getQuery("isos", ISO2Code))
				.next(), "code3");
	}

	protected Country querySolutionToCountry(QuerySolution qs) {
		Country country = new Country();
		country.setUri(getURI(qs, "country"));
		country.setBelongsTo(Collections
				.singleton(querySolutionToNamedUriRegion(qs)));
		country.setIsoCode2(getString(qs, "code2"));
		country.setIsoCode3(getString(qs, "code3"));
		country.setName(getString(qs, "countryLabel"));
		country.setLat(getDouble(qs, "lat"));
		country.setLon(getDouble(qs, "long"));
		return country;
	}

	protected CountryForRegion querySolutionToCountryForRegion(QuerySolution qs) {
		CountryForRegion country = new CountryForRegion();
		country.setUri(getURI(qs, "country"));
		country.setIsoCode2(getString(qs, "code2"));
		country.setIsoCode3(getString(qs, "code3"));
		country.setName(getString(qs, "countryLabel"));
		country.setLat(getDouble(qs, "lat"));
		country.setLon(getDouble(qs, "long"));
		return country;
	}

	protected Observation querySolutionToObservation(QuerySolution qs) {
		Observation obs = new Observation();
		obs.setUri(getURI(qs, "obs"));
		obs.setCountryCode(getString(qs, "countryCode"));
		obs.setCountryUri(getURI(qs, "country"));
		obs.setCountryName(getString(qs, "countryLabel"));
		obs.setIndicator(querySolutionToNamedUriIndicator(qs));
		obs.setName(getString(qs, "label"));
		obs.setValue(getDouble(qs, "value"));
		obs.setYear(getInt(qs, "year"));
		return obs;
	}

	protected Country resultSetToCountry(ResultSet rs) {
		Country country = new Country();
		QuerySolution qs = rs.next();
		country.setUri(getURI(qs, "country"));
		country.setBelongsTo(Collections
				.singleton(querySolutionToNamedUriRegion(qs)));
		country.setIsoCode2(getString(qs, "code2"));
		country.setIsoCode3(getString(qs, "code3"));
		country.setName(getString(qs, "countryLabel"));
		country.setLat(getDouble(qs, "lat"));
		country.setLon(getDouble(qs, "long"));
		Collection<ValuedNamedUri> observations = new HashSet<ValuedNamedUri>();
		observations.add(querySolutionToValuedNamedUri(qs));
		while (rs.hasNext()) {
			qs = rs.next();
			observations.add(querySolutionToValuedNamedUri(qs));
		}
		country.setObservations(observations);
		return country;
	}

	protected CountryGroup resultSetToCountryGroup(ResultSet rs) {
		CountryGroup cg = new CountryGroup();
		QuerySolution qs = rs.next();
		cg.setContinent(getURI(qs, "type").endsWith("Region"));
		cg.setUri(getURI(qs, "region"));
		cg.setName(getString(qs, "regionLabel"));
		Collection<CountryForRegion> countries = new HashSet<CountryForRegion>();
		countries.add(querySolutionToCountryForRegion(qs));
		while (rs.hasNext()) {
			qs = rs.next();
			countries.add(querySolutionToCountryForRegion(qs));
		}
		cg.setCountries(countries);
		return cg;
	}

	protected Collection<CountryGroup> resultSetToContinentCollection(
			ResultSet rs) {
		List<CountryGroup> groups = new LinkedList<CountryGroup>();
		while (rs.hasNext()) {
			QuerySolution qs = rs.next();
			CountryGroup continent = querySolutionToContinent(qs);
			if (groups.contains(continent)) {
				groups.get(groups.indexOf(continent)).getCountries()
						.addAll(continent.getCountries());
			} else {
				groups.add(continent);
			}
		}
		return groups;
	}

	protected CountryGroup querySolutionToContinent(QuerySolution qs) {
		CountryGroup g = new CountryGroup();
		HashSet<CountryForRegion> s = new HashSet<CountryForRegion>();
		s.add(querySolutionToCountryForRegion(qs));
		g.setContinent(true);
		g.setName(getString(qs, "regionLabel"));
		g.setUri(getURI(qs, "region"));
		g.setCountries(s);
		return g;
	}

	protected Indicator querySolutionToIndicator(QuerySolution qs) {
		Indicator indicator = new Indicator();
		indicator.setComment(getString(qs, "comment"));
		indicator.setEnd(getInt(qs, "end"));
		indicator.setStart(getInt(qs, "start"));
		indicator.setHigherBetter(getURI(qs, "high").endsWith("igh"));
		indicator.setUri(getURI(qs, "indicator"));
		indicator.setLabel(getString(qs, "indicatorLabel"));
		indicator.setComponent(querySolutionToNamedUriComponent(qs));
		return indicator;
	}

	protected Component resultSetToComponent(ResultSet rs) {
		Component component = new Component();
		while (rs.hasNext()) {
			QuerySolution qs = rs.next();
			if (component.getIndicators() == null
					|| component.getIndicators().isEmpty()) {
				component.setUri(getURI(qs, "component"));
				component.setLabel(getString(qs, "componentLabel"));
			}
			component.addIndicator(querySolutionToNamedUriIndicator(qs));
		}
		return component;
	}

	protected Collection<Component> resultSetToComponentCollection(ResultSet rs) {
		List<Component> components = new LinkedList<Component>();
		String oldUri = "";
		while (rs.hasNext()) {
			QuerySolution qs = rs.next();
			String currentUri = getURI(qs, "component");
			if (oldUri.equalsIgnoreCase(currentUri)) {
				components.get(components.size() - 1).addIndicator(
						querySolutionToNamedUriIndicator(qs));
			} else {
				oldUri = currentUri;
				Component component = new Component();
				component.setUri(currentUri);
				component.setLabel(getString(qs, "componentLabel"));
				component.addIndicator(querySolutionToNamedUriIndicator(qs));
				components.add(component);
			}
		}
		return components;
	}

	protected Dataset querySolutionToDataset(QuerySolution qs) {
		Dataset dataset = new Dataset();
		dataset.setContributor(getURI(qs, "contributor"));
		dataset.setIssued(getString(qs, "issued"));
		dataset.setName(getString(qs, "label"));
		dataset.setPublisher(getURI(qs, "publisher"));
		dataset.setSlice(getURI(qs, "slice"));
		dataset.setStructure(getURI(qs, "structure"));
		dataset.setSubject(getURI(qs, "subject"));
		dataset.setUnitMeasure(getURI(qs, "unitMeasure"));
		dataset.setUri(getURI(qs, "dataset"));
		return dataset;
	}

	protected Collection<WeightSchema> resultSetToWeightSchemaCollection(
			ResultSet rs) {
		List<WeightSchema> weightSchemas = new LinkedList<WeightSchema>();
		String oldUri = "";
		while (rs.hasNext()) {
			QuerySolution qs = rs.next();
			String currentUri = getURI(qs, "weightSchema");
			if (oldUri.equalsIgnoreCase(currentUri)) {
				weightSchemas.get(weightSchemas.size() - 1).addWeight(
						getURI(qs, "element"), getDouble(qs, "value"));
			} else {
				oldUri = currentUri;
				WeightSchema weightSchema = new WeightSchema();
				weightSchema.setUri(currentUri);
				weightSchema.addWeight(getURI(qs, "element"),
						getDouble(qs, "value"));
				weightSchemas.add(weightSchema);
			}
		}
		return weightSchemas;
	}

	protected WeightSchema resultSetToWeightSchema(ResultSet rs) {
		WeightSchema weightSchema = new WeightSchema();
		while (rs.hasNext()) {
			QuerySolution qs = rs.next();
			if (weightSchema.getSchema() == null
					|| weightSchema.getSchema().isEmpty()) {
				weightSchema.setUri(getURI(qs, "weightSchema"));
			}
			weightSchema.addWeight(getURI(qs, "element"),
					getDouble(qs, "value"));
		}
		return weightSchema;
	}

	protected Subindex resultSetToSubindex(ResultSet rs) {
		Subindex subindex = new Subindex();
		while (rs.hasNext()) {
			QuerySolution qs = rs.next();
			if (subindex.getComponents() == null
					|| subindex.getComponents().isEmpty()) {
				subindex.setUri(getURI(qs, "subindex"));
				subindex.setLabel(getString(qs, "label"));
				subindex.setComment(getString(qs, "comment"));
			}
			subindex.addComponent(querySolutionToNamedUriComponent(qs));
		}
		return subindex;
	}

	protected Collection<Subindex> resultSetToSubindexCollection(ResultSet rs) {
		List<Subindex> subindexes = new LinkedList<Subindex>();
		String oldUri = "";
		while (rs.hasNext()) {
			QuerySolution qs = rs.next();
			String currentUri = getURI(qs, "subindex");
			if (oldUri.equalsIgnoreCase(currentUri)) {
				subindexes.get(subindexes.size() - 1).addComponent(
						querySolutionToNamedUriComponent(qs));
			} else {
				oldUri = currentUri;
				Subindex subindex = new Subindex();
				subindex.setUri(currentUri);
				subindex.setLabel(getString(qs, "label"));
				subindex.setComment(getString(qs, "comment"));
				subindex.addComponent(querySolutionToNamedUriComponent(qs));
				subindexes.add(subindex);
			}
		}
		return subindexes;
	}

	private ValuedNamedUri querySolutionToValuedNamedUri(QuerySolution qs) {
		ValuedNamedUri vnu = new ValuedNamedUri();
		vnu.setName(getString(qs, "obsLabel"));
		vnu.setUri(getURI(qs, "obs"));
		vnu.setValue(getDouble(qs, "value"));
		return vnu;
	}

	private NamedUri querySolutionToNamedUriRegion(QuerySolution qs) {
		return querySolutionToNamedUri(qs, "region");
	}

	private NamedUri querySolutionToNamedUriIndicator(QuerySolution qs) {
		return querySolutionToNamedUri(qs, "indicator");
	}

	private NamedUri querySolutionToNamedUriComponent(QuerySolution qs) {
		return querySolutionToNamedUri(qs, "component");
	}

	private NamedUri querySolutionToNamedUri(QuerySolution qs, String type) {
		NamedUri nu = new NamedUri();
		nu.setUri(getURI(qs, type));
		nu.setName(getString(qs, type + "Label"));
		return nu;
	}

	private String getURI(QuerySolution qs, String resource) {
		try {
			return qs.getResource(resource).getURI();
		} catch (NullPointerException npe) {
			return "";
		}
	}

	protected String getString(QuerySolution qs, String literal) {
		try {
			return qs.getLiteral(literal).getString();
		} catch (NullPointerException npe) {
			return "";
		}
	}

	private Double getDouble(QuerySolution qs, String literal) {
		try {
			return qs.getLiteral(literal).getDouble();
		} catch (NullPointerException npe) {
			return 0.0;
		}
	}

	private Integer getInt(QuerySolution qs, String literal) {
		try {
			return qs.getLiteral(literal).getInt();
		} catch (NullPointerException npe) {
			return 0;
		}
	}

}
