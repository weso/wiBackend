package es.weso.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.WeightSchemaManagement;

/**
 * Web services to retrieve {@link es.weso.model.WeightSchema}s
 * 
 * @author Alejandro Montes García
 * @since 13/09/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/weightSchema")
public class WeightSchemaServices {
	
	private static WeightSchemaManagement weightSchemaManager;

	public void setWeightSchemaManager(WeightSchemaManagement weightSchemaManager) {
		WeightSchemaServices.weightSchemaManager = weightSchemaManager;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAllWeightSchemas(ModelMap model) {
		model.addAttribute("weightSchema", weightSchemaManager.getAll());
		return "weightSchemas";
	}

	@RequestMapping(value = "/{uri}", method = RequestMethod.GET)
	public String getWeightSchemaByUri(@PathVariable String uri, ModelMap model) {
		model.addAttribute("weightSchema", weightSchemaManager.getOne(uri));
		return "weightSchema";
	}
}
