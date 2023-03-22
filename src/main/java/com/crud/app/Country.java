package com.crud.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="country",eager=false)
@RequestScoped
public class Country {

	private CountryPojo countryPojo;
	private List<CountryPojo> listOfCountries;
	/*
	 * private String country_name;
	 * 
	 * private String sl_no;
	 * 
	 * public String getSl_no() { return sl_no; }
	 * 
	 * public void setSl_no(String sl_no) { this.sl_no = sl_no; }
	 * 
	 * public String getCountry_name() { return country_name; }
	 * 
	 * public void setCountry_name(String country_name) { this.country_name =
	 * country_name; }
	 */

	//private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	@PreDestroy
	public void destroy() {
		if(listOfCountries!=null) {
			listOfCountries.clear();
		}
	}
	
	@PostConstruct
	public void init() {
		try {
			Statement st = DBConnection.getInstance().getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from country");
			this.listOfCountries = new ArrayList<CountryPojo>();
			while (rs.next()) {
				CountryPojo obj_Country = new CountryPojo();
				obj_Country.setCountry_name(rs.getString("country_name"));
				obj_Country.setSl_no(rs.getString("sl_no"));
				listOfCountries.add(obj_Country);
			}
			countryPojo = new CountryPojo();
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}
	
	public List getGet_all_country() throws Exception {
		try {
			
			Statement st = DBConnection.getInstance().getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from country");
			while (rs.next()) {
				CountryPojo obj_Country = new CountryPojo();
				obj_Country.setCountry_name(rs.getString("country_name"));
				obj_Country.setSl_no(rs.getString("sl_no"));
				listOfCountries.add(obj_Country);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			/*
			 * if (DBConnection.getInstance().getConnection() != null) {
			 * DBConnection.getInstance().getConnection().close(); }
			 */
		}
		return listOfCountries;
	}

	public String goToEdit() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		System.out.println(params);
		//params.forEach((i,j) -> System.out.println(i+"::"+j));
		String field_sl_no = params.get("action");
		try {
			
			Connection connection = DBConnection.getInstance().getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("select * from country where sl_no=" + field_sl_no);
			CountryPojo obj_Category = new CountryPojo();
			rs.next();
			//obj_Category.setCountry_name(rs.getString("country_name"));
			//obj_Category.setSl_no(rs.getString("sl_no"));
			//sessionMap.put("editcategory", obj_Category);
			this.countryPojo.setSl_no(rs.getString("sl_no"));
			this.countryPojo.setCountry_name(rs.getString("country_name"));
			
			//FacesContext.getCurrentInstance().getExternalContext().getse
		} catch (Exception e) {
			System.out.println(e);
		}
		return "edit";
	}

	public String delete_Country() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String field_sl_no = params.get("action");
		try {
			
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement("delete from country where sl_no=?");
			ps.setString(1, field_sl_no);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/index.xhtml?faces-redirect=true";
		
	}

	public String update_Country() {
		System.out.println(this.countryPojo);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String update_sl_no = params.get("update_sl_no");
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement("update country set country_name=? where sl_no=?");
			ps.setString(1, this.countryPojo.getCountry_name());
			ps.setString(2, this.countryPojo.getSl_no());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/index.xhtml?faces-redirect=true";
	}

	public void add_Country() {
		try {
			Connection connection = null;
			
			connection = DBConnection.getInstance().getConnection();
			PreparedStatement ps = connection
					.prepareStatement("insert into country(country_name) values('" + countryPojo.getCountry_name() + "')");
			ps.executeUpdate();
		} catch (Exception e) {
			System.err.println(e);
		}
		//return "index.xhtml";
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CountryPojo getCountryPojo() {
		return countryPojo;
	}

	public void setCountryPojo(CountryPojo countries) {
		this.countryPojo = countries;
	}

	public List<CountryPojo> getListOfCountries() {
		return listOfCountries;
	}

	public void setListOfCountries(List<CountryPojo> listOfCountries) {
		this.listOfCountries = listOfCountries;
	}

	/*
	 * public String navigateToAdd() { return "add.xhtml"; }
	 */

}
