package es.weso.wirouter.country;

import java.util.ArrayDeque;
import java.util.Collection;

import models.CountryForRegion;
import es.weso.wiLodPortal.business.CountryGroupManagement;

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
		Collection<CountryForRegion> countries = countryGroupManager.getCountryGroup(
				name).getCountries();
		Collection<String> codes = new ArrayDeque<String>();
		for (CountryForRegion country : countries) {
			codes.add(country.getIsoCode3());
		}
		return codes;
	}
}
