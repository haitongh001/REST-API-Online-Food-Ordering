package com.webService.onlineOrdering.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.*;

@Entity
@Table(name = "Restaurant")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Restaurant{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long restaurantId;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String address;
	
	@OneToMany(mappedBy="restaurant", orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Menu> menus;
	
	public Restaurant() {
		super();
		this.restaurantId=0L;
		this.name="";
		this.address="";
	}
	public Restaurant(Long restaurantId, String name, String address) {
		super();
		this.restaurantId=restaurantId;
		this.name = name;
		this.address = address;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantid) {
		this.restaurantId = restaurantid;
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
	/*public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}*/
}


