package info.amazon.models;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
public class Product {
	@XmlElement(name = "title")
	private String title;
	@XmlElement(name = "price")
	private String price;
	@XmlElement(name = "description")
	private List<String> description;
	StringBuilder theString;
	
	public Product setTitle(String title) {
		this.title = title;
		return this;
	}
	public Product setPrice(String price) {
		this.price = price;
		return this;
	}
	public Product setDescription(List<String> description) {
		this.description = description;
		return this;
	}
	
	@Override
	public String toString() {
		theString = new StringBuilder();
		theString.append(title + "\r\n");
		theString.append(price + "\r\n");
		for(String s: description) {
			theString.append(s + "\r\n");
		}
		return theString.toString();
	}
}
