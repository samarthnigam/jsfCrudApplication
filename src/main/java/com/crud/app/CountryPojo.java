package com.crud.app;

public class CountryPojo {
	
	private String country_name;
	private String sl_no;
	
	
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getSl_no() {
		return sl_no;
	}
	public void setSl_no(String sl_no) {
		this.sl_no = sl_no;
	}
	@Override
	public String toString() {
		return "CountryPojo [country_name=" + country_name + ", sl_no=" + sl_no + "]";
	}
	

}
