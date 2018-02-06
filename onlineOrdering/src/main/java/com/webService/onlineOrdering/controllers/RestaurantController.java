package com.webService.onlineOrdering.controllers;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webService.onlineOrdering.models.Restaurant;
import com.webService.onlineOrdering.repository.RestaurantRepository;

import java.util.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	@Autowired
	private RestaurantRepository restrepo;
	
	// Get all restaurants
	@GetMapping("/all")
	//@Cacheable(value="restaurantList")
	public List<Restaurant> getAllRestaurants(){
		return restrepo.findAll();
	}
	
	// Get a restaurant using the id
	@GetMapping("/search/{id}")
	public ResponseEntity<Restaurant> searchRestaruant(@PathVariable(value="id") Long id){
		if(!restrepo.existsById(id)) return ResponseEntity.notFound().build();
		Restaurant restaurant=restrepo.getOne(id);
		return ResponseEntity.ok().body(restaurant);
	}
	
	// Register a new restaurant
	@PostMapping("/register")
	//@CachePut(value="restaurantList")
	public List<Restaurant> registerRestaurant(@Valid @RequestBody Restaurant restaurant){
		restrepo.save(restaurant);
		return restrepo.findAll();
	}
	
	// Update a restaurant's information
	// PUT method will store data entity under the supplied Id
	@PutMapping("/update/{id}")
	//@CachePut(value="restaurantList", key="#id")
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable(value="id") Long id, @Valid @RequestBody Restaurant info){
		if(!restrepo.existsById(id)) return ResponseEntity.notFound().build();
		Restaurant restaurant=restrepo.getOne(id);
		restaurant.setAddress(info.getAddress());
		restaurant.setName(info.getName());
		Restaurant update=restrepo.save(restaurant);
		return ResponseEntity.ok(update);
	}
	
	// Delete a restaurant
	@DeleteMapping("/delete/{id}")
	//@CacheEvict(value="restaurantList", key="#id")
	public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable(value="id") Long id){
		if(!restrepo.existsById(id)) return ResponseEntity.notFound().build();
		Restaurant restaurant=restrepo.getOne(id);
		restrepo.delete(restaurant);
		return ResponseEntity.ok().build();
	}

}
