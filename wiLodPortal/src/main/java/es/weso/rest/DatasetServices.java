package es.weso.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.DatasetManagement;

/**
 * Web services to retrieve {@link es.weso.model.Dataset}s
 * 
 * @author Alejandro Montes García
 * @since 12/09/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/dataset")
public class DatasetServices {
	
	private static DatasetManagement datasetManager;

	public void setDatasetManager(DatasetManagement datasetManager) {
		DatasetServices.datasetManager = datasetManager;
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAllDatasets(ModelMap model) {
		model.addAttribute("dataset", datasetManager.getAll());
		return "datasets";
	}

	@RequestMapping(value = "/{uri}", method = RequestMethod.GET)
	public String getDatasetByUri(@PathVariable String uri, ModelMap model) {
		model.addAttribute("dataset", datasetManager.getOne(uri));
		return "dataset";
	}
}
