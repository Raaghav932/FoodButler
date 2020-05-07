package org.foodbutler;

public class StoreInfo {
	private String name;
	private String address;
	private String phonenumber;
	private String image;
	public StoreInfo(String n,String a,String p, String i) {
		name = n;
		address = a;
		phonenumber = p;
		image = i;
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
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

}
