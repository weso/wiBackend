package es.weso.wirouter.country;

import java.util.ArrayDeque;
import java.util.Collection;

import es.weso.business.CountryGroupManagement;
import es.weso.model.CountryForRegion;

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
		Collection<CountryForRegion> namedUris = countryGroupManager.getCountryGroup(
				name).getCountries();
		Collection<String> codes = new ArrayDeque<String>();
		for (CountryForRegion namedUri : namedUris) {
			codes.add(namedUri.getIsoCode3());
		}
		return codes;
	}
}
