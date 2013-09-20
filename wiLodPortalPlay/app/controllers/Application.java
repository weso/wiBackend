package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result components() {
    	return TODO;
    }
    
    public static Result component(String id) {
    	return TODO;
    }
    
    public static Result countries() {
    	return TODO;
    }
    
    public static Result country(String code) {
    	return TODO;
    }
    
    public static Result datasets() {
    	return TODO;
    }
    
    public static Result dataset(String id) {
    	return TODO;
    }
    
	public static Result indicators() {
		return TODO;
	}

	public static Result indicator(String id) {
		return TODO;
	}
    
    public static Result observations() {
    	return TODO;
    }
    
    public static Result observation(String id) {
    	return TODO;
    }
    
    public static Result observationByCountry(String country, int year, String indicator) {
    	return TODO;
    }
    
    public static Result regions() {
    	return TODO;
    }
    
    public static Result region(String name) {
    	return TODO;
    }
    
    public static Result subindexes() {
    	return TODO;
    }
    
    public static Result subindex(String id) {
    	return TODO;
    }
    
    public static Result weightSchemas() {
    	return TODO;
    }
    
    public static Result weightSchema(String id) {
    	return TODO;
    }

}
