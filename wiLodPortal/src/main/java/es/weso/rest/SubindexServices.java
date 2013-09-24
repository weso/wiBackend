package es.weso.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.SubindexManagement;

/**
 * Web services to retrieve {@link es.weso.model.Subindex}s
 * 
 * @author Alejandro Montes García
 * @since 16/09/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/subindex")
public class SubindexServices {

	private static SubindexManagement subindexManager;

	public void setSubindexManager(SubindexManagement subindexManager) {
		SubindexServices.subindexManager = subindexManager;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAllSubindexs(ModelMap model) {
		model.addAttribute("subindexes", subindexManager.getAll());
		return "subindexes";
	}

	@RequestMapping(value = "/{uri}", method = RequestMethod.GET)
	public String getSubindexByUri(@PathVariable String uri, ModelMap model) {
		model.addAttribute("subindex", subindexManager.getOne(uri));
		return "subindex";
	}
}
