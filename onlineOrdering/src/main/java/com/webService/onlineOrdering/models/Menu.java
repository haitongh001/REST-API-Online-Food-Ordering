package com.webService.onlineOrdering.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Entity
@Table(name="Menu")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Menu {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long menuId;
	
	@NotBlank
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="restaurantid")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Restaurant restaurant;
	
	@OneToMany(mappedBy="menu", orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Item> items;
	
	public Menu() {
		super();
		this.menuId=0L;
		this.name="";
		this.restaurant=null;
	}
	public Menu(Long menuId, String name, Restaurant restaurant) {
		super();
		this.menuId=menuId;
		this.name = name;
		this.restaurant=restaurant;
		
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuid) {
		this.menuId = menuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant=restaurant;
	}
}
