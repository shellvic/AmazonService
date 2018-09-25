package info.amazon.validate;

public class DataChecker {
	public static boolean checkFirstName(String name) {
		return name.matches("[A-Za-z]+");
	}
	
	public static boolean checkLastName(String name) {
		return name.matches("[A-Za-z]+");
	}

	public static boolean checkEmail(String email) {
		
		return email.matches("\\w{3,}@[a-z0-9]+\\.[a-z]{2,}");
	}
	
	public static boolean checkPassword(String password) {
		return password.matches("^[A-Za-z]{6,}$");
	}

	public static boolean checkURL(String url) {
		return url.matches("^((http)(s)?(://))?(www.)?([\\w]{2,253}.)([a-z0-9]{2,253}/)[A-Za-z0-9\\-\\._~:/\\?#%\\[\\]@!$&'()*\\+,;=]+$");
	}

	public static boolean checkASIN(String asin) {
		return asin.matches("^[A-Z0-9]{10}$");
	}
	
	public static boolean checkProduct(String product) {
		return (checkURL(product) || checkASIN(product));
	}
}
