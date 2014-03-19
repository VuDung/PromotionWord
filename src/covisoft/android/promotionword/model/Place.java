package covisoft.android.promotionword.model;

import java.io.Serializable;

public class Place implements Serializable {
	private String id;
	private String name;
	private String address;
	private String city;
	private String district;
	private String category;
	private String promotionPercentage;
	private String phoneNumber;
	private String image;
	private String expiryDate;
	private String features;
	private String lat;
	private String lng;
	private String conditions;
	private String description;
	
	
	public Place(String name, String address, String city, String district,
			String category, String promotionPercentage, String phoneNumber,
			String image, String expiryDate, String features, String lat,
			String lng, String conditions, String description) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.district = district;
		this.category = category;
		this.promotionPercentage = promotionPercentage;
		this.phoneNumber = phoneNumber;
		this.image = image;
		this.expiryDate = expiryDate;
		this.features = features;
		this.lat = lat;
		this.lng = lng;
		this.conditions = conditions;
		this.description = description;
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPromotionPercentage() {
		return promotionPercentage;
	}
	public void setPromotionPercentage(String promotionPercentage) {
		this.promotionPercentage = promotionPercentage;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
