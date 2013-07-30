package es.weso.wirouter.country;

import java.util.ArrayDeque;
import java.util.Collection;

import es.weso.business.CountryGroupManagement;
import es.weso.model.NamedUri;

public class NamedRegion extends CountryExpr {
	private final String name;
	private static CountryGroupManagement countryGroupManager;

	public void setCountryGroupManager(
			CountryGroupManagement countryGroupManager) {
		NamedRegion.countryGroupManager = countryGroupManager;
	}

	public NamedRegion() {
		name = "";
	}

	public NamedRegion(String name) {
		this.name = name;
	}

	public String toString() {
		return "region(" + name + ")";
	}

	@Override
	public Collection<String> getCountryCodes() {
		Collection<NamedUri> namedUris = countryGroupManager.getCountryGroup(
				name).getCountries();
		Collection<String> codes = new ArrayDeque<String>();
		for (NamedUri namedUri : namedUris) {
			codes.add(namedUri.getName());
		}
		return codes;
	}
}
