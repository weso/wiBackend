package es.weso.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.weso.business.impl.ComponentManager;

/**
 * Web services to retrieve {@link es.weso.model.Component}s
 * 
 * @author Alejandro Montes García
 * @since 26/08/2013
 * @version 1.0
 */
@Controller
@RequestMapping("/component")
public class ComponentServices {

	private static ComponentManager componentManager;

	public void setComponentManager(ComponentManager componentManager) {
		ComponentServices.componentManager = componentManager;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getAllComponents(ModelMap model) {
		model.addAttribute("components", componentManager.getAll());
		return "components";
	}

	@RequestMapping(value = "/{uri}", method = RequestMethod.GET)
	public String getComponentByUri(@PathVariable String uri, ModelMap model) {
		model.addAttribute("component", componentManager.getOne(uri));
		return "component";
	}
}
