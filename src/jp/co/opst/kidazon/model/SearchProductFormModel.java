package jp.co.opst.kidazon.model;

public class SearchProductFormModel {
	private String category;
	private String name;
	private String maker;
	
	
	private String lowerPrice;
	
	private String higherPrice;
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getLowerPrice() {
		return lowerPrice;
	}
	public void setLowerPrice(String lowerPrice) {
		this.lowerPrice = lowerPrice;
	}
	public String getHigherPrice() {
		return higherPrice;
	}
	public void setHigherPrice(String higherPrice) {
		this.higherPrice = higherPrice;
	}
	
	
}
