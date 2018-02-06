package com.webService.onlineOrdering.controllers;

import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webService.onlineOrdering.models.Menu;
import com.webService.onlineOrdering.models.Restaurant;
import com.webService.onlineOrdering.repository.MenuRepository;
import com.webService.onlineOrdering.repository.RestaurantRepository;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuRepository menurepo;
	
	@Autowired
	private RestaurantRepository restrepo;
	
	// Get all menus
	@GetMapping("/all")
	//@Cacheable("menuList")
	public List<Menu> getAllMenus(){
		return menurepo.findAll();
	}
	
	// Get a menu using the id
	@GetMapping("/search/{id}")
	public ResponseEntity<Menu> searchMenus(@PathVariable(value="id") Long id){
		if(!menurepo.existsById(id)) return ResponseEntity.notFound().build();
		Menu menu=menurepo.getOne(id);
		return ResponseEntity.ok().body(menu);
	}
	
	// Add a new menu for a restaurant
	@PostMapping("/add/{rest_id}")
	//@CachePut(value="menuList")
	public ResponseEntity<Menu> addMenu(@PathVariable(value="rest_id") Long rest_id, @Valid @RequestBody Menu menu){
		if(!restrepo.existsById(rest_id)) return ResponseEntity.notFound().build();
		Restaurant rest=restrepo.getOne(rest_id);
		menu.setRestaurant(rest);
		Menu newmenu=menurepo.save(menu);
		return ResponseEntity.ok().body(newmenu);
	}
	
	// Update a menu's name
	// PUT method will store data entity under the supplied Id
	@PutMapping("/update/{id}")
	//@CachePut(value="menuList", key="#id")
	public ResponseEntity<Menu> updateMenu(@PathVariable(value="id") Long id, @Valid @RequestBody Menu info){
		if(!menurepo.existsById(id)) return ResponseEntity.notFound().build();
		Menu menu=menurepo.getOne(id);
		menu.setName(info.getName());
		Menu update=menurepo.save(menu);
		return ResponseEntity.ok(update);
	}
	
	// Delete a menu
	@DeleteMapping("/delete/{id}")
	//@CacheEvict(value="menuList", key="#id")
	public ResponseEntity<Menu> deleteMenu(@PathVariable(value="id") Long id){
		if(!menurepo.existsById(id)) return ResponseEntity.notFound().build();
		Menu menu=menurepo.getOne(id);
		menurepo.delete(menu);
		return ResponseEntity.ok().build();
	}
}
