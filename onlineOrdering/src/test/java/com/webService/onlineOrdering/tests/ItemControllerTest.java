package com.webService.onlineOrdering.tests;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.webService.onlineOrdering.controllers.ItemController;
import com.webService.onlineOrdering.models.Item;
import com.webService.onlineOrdering.models.Menu;
import com.webService.onlineOrdering.models.Restaurant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
@WebAppConfiguration
public class ItemControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	// Create a Mock for ItemController which can be used to bypass the call to the actual ItemController
	@MockBean
	private ItemController itemcontrollerMock;
	
	@Test
	public void testGetAllItems() throws Exception{
		Restaurant rest=new Restaurant(0L, "McDonald", "7th Avenue");
		Menu menu=new Menu(0L, "Drink", rest);
		List<Item> result=Arrays.asList(
				new Item(1L, "Coke", 3.00, menu),
				new Item(2L, "Pizza", 7.99, menu));
		given(itemcontrollerMock.getAllItems()).willReturn(result);
		mvc.perform(get("/item/all"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$", hasSize(2)))
		   .andExpect(jsonPath("$[0].itemId", is(1)))
		   .andExpect(jsonPath("$[0].name", is("Coke")))
		   .andExpect(jsonPath("$[0].price", is(3.00)))
		   .andExpect(jsonPath("$[0].menu.menuId", is(0)))
		   .andExpect(jsonPath("$[1].itemId", is(2)))
		   .andExpect(jsonPath("$[1].name", is("Pizza")))
		   .andExpect(jsonPath("$[1].price", is(7.99)))
		   .andExpect(jsonPath("$[1].menu.menuId", is(0)));
		verify(itemcontrollerMock, times(1)).getAllItems();
		verifyNoMoreInteractions(itemcontrollerMock);
	}

	@Test
	public void testSearchItemsOK() throws Exception{
		Restaurant rest=new Restaurant(0L, "McDonald", "7th Avenue");
		Menu menu=new Menu(0L, "Drink", rest);
		Item item=new Item(1L, "Coke", 3.00, menu);
		given(itemcontrollerMock.searchItems(1L)).willReturn(ResponseEntity.ok(item));
		mvc.perform(get("/item/search/{id}", 1))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("itemId", is(1)))
		   .andExpect(jsonPath("name", is("Coke")))
		   .andExpect(jsonPath("price", is(3.00)))
		   .andExpect(jsonPath("menu.menuId", is(0))); 
		verify(itemcontrollerMock, times(1)).searchItems(1L);
		verifyNoMoreInteractions(itemcontrollerMock);
	}
	
	@Test
	public void testSearchItemNotFound() throws Exception{
		given(itemcontrollerMock.searchItems(1L)).willReturn(ResponseEntity.notFound().build());
		mvc.perform(get("/item/search/{id}", 1))
		   .andExpect(status().isNotFound()); 
		verify(itemcontrollerMock, times(1)).searchItems(1L);
		verifyNoMoreInteractions(itemcontrollerMock);
	}
	
	@Test
	public void testAddItem() throws Exception{		
		String json="{\"itemId\": 1, \"name\":\"Coke\", \"price\":3.00, \"menuId\": 6}";
		mvc.perform(post("/item/add/{id}", 6)
		   .contentType(MediaType.APPLICATION_JSON_UTF8)
		   .content(json))
		   .andExpect(status().isOk());
	}

	@Test
	public void testUpdateItem() throws Exception{
		Restaurant rest=new Restaurant(0L, "McDonald", "7th Avenue");
		Menu menu=new Menu(0L, "Drink", rest);
		Item item=new Item(1L, "Coke", 3.00, menu);
		String json="{\"itemId\":1, \"name\":\"Coffee\", \"price\":4.00, \"menuId\":6}";
		mvc.perform(put("/item/update/{id}", item.getItemId())
		   .contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
		   .andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteItem() throws Exception{
		Restaurant rest=new Restaurant(0L, "McDonald", "7th Avenue");
		Menu menu=new Menu(0L, "Drink", rest);
		Item item=new Item(1L, "Coke", 3.00, menu);
		given(itemcontrollerMock.searchItems(item.getItemId())).willReturn(ResponseEntity.ok(item));
		mvc.perform(delete("/item/delete/{id}", item.getItemId()))
		   .andExpect(status().isOk());
		verify(itemcontrollerMock, times(1)).deleteItem(item.getItemId());
		verifyNoMoreInteractions(itemcontrollerMock);
	}
}

