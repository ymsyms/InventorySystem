package sg.edu.iss.inventory.service;

public class UtilitiesService {

	 public static boolean isInt(String forParsing) {
	        try {
	            Integer.parseInt(forParsing);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
}
