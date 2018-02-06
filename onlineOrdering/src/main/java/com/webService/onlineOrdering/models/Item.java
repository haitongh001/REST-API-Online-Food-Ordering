package com.webService.onlineOrdering.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="Item")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Item {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long itemId;
	
	@NotBlank
	private String name;
	
	@NotNull
	private double price;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="menuid")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Menu menu;
		
	public Item() {
		super();
		this.itemId=0L;
		this.name="";
		this.price=0;
		this.menu=null;
		
	}
	public Item(Long itemId, String name, double price, Menu menu) {
		super();
		this.itemId=itemId;
		this.name = name;
		this.price = price;
		this.menu=menu;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemid) {
		this.itemId = itemid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Menu getMenu(){
		return menu;
	}
	public void setMenu(Menu menu){
		this.menu=menu;
	}
}
