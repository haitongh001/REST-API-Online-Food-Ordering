package com.webService.onlineOrdering.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.webService.onlineOrdering.models.Item;
import com.webService.onlineOrdering.models.Menu;
import com.webService.onlineOrdering.repository.*;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemRepository itemrepo;
	
	@Autowired
	private MenuRepository menurepo;
	
	// Get all items
	@GetMapping("/all")
	//@Cacheable("itemList")
	public List<Item> getAllItems(){
		return itemrepo.findAll();
	}
	
	// Get an item using the id
	@GetMapping("/search/{id}")
	public ResponseEntity<Item> searchItems(@PathVariable(value="id") Long id){
		if(!itemrepo.existsById(id)) return ResponseEntity.notFound().build();
		Item item=itemrepo.getOne(id);
		return ResponseEntity.ok().body(item);
	}
	
	// Add a new item into a particular menu
	@PostMapping("/add/{menu_id}")
	//@CachePut(value="itemList")
	public ResponseEntity<Item> addItem(@PathVariable(value="menu_id") Long id, @Valid @RequestBody Item item){
		if(!menurepo.existsById(id)) return ResponseEntity.notFound().build();
		Menu menu=menurepo.getOne(id);
		item.setMenu(menu);
		Item newitem=itemrepo.save(item);
		return ResponseEntity.ok().body(newitem);
	}
	
	// Update a item's information
	// PUT method will store data entity under the supplied Id
	@PutMapping("/update/{id}")
	//@CachePut(value="itemList", key="#id")
	public ResponseEntity<Item> updateItem(@PathVariable(value="id") Long id, @Valid @RequestBody Item info){
		if(!itemrepo.existsById(id)) return ResponseEntity.notFound().build();
		Item item=itemrepo.getOne(id);
		item.setName(info.getName());
		item.setPrice(info.getPrice());
		Item update=itemrepo.save(item);
		return ResponseEntity.ok(update);
	}
	
	// Delete an item
	@DeleteMapping("/delete/{id}")
	//@CacheEvict(value="itemList", key="#id")
	public ResponseEntity<Item> deleteItem(@PathVariable(value="id") Long id){
		if(!itemrepo.existsById(id)) return ResponseEntity.notFound().build();
		Item item=itemrepo.getOne(id);
		itemrepo.delete(item);
		return ResponseEntity.ok().build();
	}
}
