package es.weso.views;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

/**
 * A view that returns data in TTL
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @version 1.0
 * @since 27/08/2013
 */
public class TTLView extends AbstractView {

	public static final String DEFAULT_CONTENT_TYPE = "text/turtle";

	public TTLView() {
		setContentType(DEFAULT_CONTENT_TYPE);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String relativeUri = URLEncoder.encode(request.getRequestURI()
				.substring(request.getRequestURI().indexOf('/', 1)).replaceAll(".ttl", ""), "UTF-8");
		// TODO Externalizar path
		String url = "http://localhost:3030/computex/query?query=CONSTRUCT%7B%3Chttp%3A%2F%2Fdata.webfoundation.org%2Fwebindex%2Fv2013"
				+ relativeUri
				+ "%3E+%3Fp+%3Fl%7DWHERE%7B%3Chttp%3A%2F%2Fdata.webfoundation.org%2Fwebindex%2Fv2013"
				+ relativeUri + "%3E+%3Fp+%3Fl%7D&output=text";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((inputLine = in.readLine()) != null) {
			baos.write(inputLine.getBytes());
			baos.write("\n".getBytes());
		}
		in.close();
		writeToResponse(response, baos);
	}

}