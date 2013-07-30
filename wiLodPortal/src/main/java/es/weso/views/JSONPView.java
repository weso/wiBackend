package es.weso.views;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

/**
 * A view that returns data in RDF
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @version 1.0
 * @since 12/04/2013
 */
public class JSONPView extends AbstractView {

	public static final String DEFAULT_CONTENT_TYPE = "application/javascript";

	public JSONPView() {
		setContentType(DEFAULT_CONTENT_TYPE);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Object jsonpObject = getJSONPObject(model);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write("jsonpCallback(".getBytes());
		ObjectMapper om = new ObjectMapper();
		om.writeValue(baos, jsonpObject);
		baos.write(")".getBytes());
		writeToResponse(response, baos);
	}

	/**
	 * Gets the object to be written from the model
	 * 
	 * @param model
	 *            Combined output {@link Map} (never null), with dynamic values
	 *            taking precedence over static attributes
	 * @return The object to be written
	 */
	private Object getJSONPObject(Map<String, Object> model) {
		Object jsonpObject = null;
		for (Object obj : model.values()) {
			if (obj instanceof BindingResult) {
				jsonpObject = ((BindingResult) obj).getTarget();
				break;
			}
		}
		return jsonpObject;
	}
}