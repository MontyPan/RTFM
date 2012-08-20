package org.zkoss.rtmf.vo;

public class Product {
	private static int SERIAL = 0;
	public synchronized static Product mock(){
		Product result = new Product();
		result.setId(""+SERIAL);
		result.setCategory("category "+SERIAL);
		result.setName("name "+SERIAL);
		SERIAL++;
		return result;
	}
	
	private String id;
	private String name;
	private String category;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
