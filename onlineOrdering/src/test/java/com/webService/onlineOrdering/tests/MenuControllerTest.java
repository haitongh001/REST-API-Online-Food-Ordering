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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.webService.onlineOrdering.controllers.MenuController;
import com.webService.onlineOrdering.models.Menu;
import com.webService.onlineOrdering.models.Restaurant;

@RunWith(SpringRunner.class)
@WebMvcTest(MenuController.class)
@WebAppConfiguration
public class MenuControllerTest {
	@Autowired
	private MockMvc mvc;
	
	// Create a Mock for MenuController which can be used to bypass the call to the actual MenuController
	@MockBean
	private MenuController menucontrollerMock;
	
	@Test
	public void testGetAllMenus() throws Exception{
		Restaurant restaurant=new Restaurant(0L, "McDonald", "7th Avenue");
		List<Menu> result=Arrays.asList(
				new Menu(1L, "Drink", restaurant),
				new Menu(2L, "Lunch", restaurant));
		given(menucontrollerMock.getAllMenus()).willReturn(result);
		mvc.perform(get("/menu/all"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$", hasSize(2)))
		   .andExpect(jsonPath("$[0].menuId", is(1)))
		   .andExpect(jsonPath("$[0].name", is("Drink")))
		   .andExpect(jsonPath("$[0].restaurant.restaurantId", is(0)))
		   .andExpect(jsonPath("$[1].menuId", is(2)))
		   .andExpect(jsonPath("$[1].name", is("Lunch")))
		   .andExpect(jsonPath("$[1].restaurant.restaurantId", is(0)));
		verify(menucontrollerMock, times(1)).getAllMenus();
		verifyNoMoreInteractions(menucontrollerMock);
	}

	@Test
	public void testSearchMenusOK() throws Exception{
		Restaurant rest=new Restaurant(0L, "McDonald", "7th Avenue");
		Menu menu=new Menu(1L, "Drink", rest);
		given(menucontrollerMock.searchMenus(1L)).willReturn(ResponseEntity.ok(menu));
		mvc.perform(get("/menu/search/{id}", 1))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("menuId", is(1)))
		   .andExpect(jsonPath("name", is("Drink")))
		   .andExpect(jsonPath("restaurant.restaurantId", is(0)));
		verify(menucontrollerMock, times(1)).searchMenus(1L);
		verifyNoMoreInteractions(menucontrollerMock);
	}
	
	@Test
	public void testSearchMenuNotFound() throws Exception{
		given(menucontrollerMock.searchMenus(1L)).willReturn(ResponseEntity.notFound().build());
		mvc.perform(get("/menu/search/{id}", 1))
		   .andExpect(status().isNotFound()); 
		verify(menucontrollerMock, times(1)).searchMenus(1L);
		verifyNoMoreInteractions(menucontrollerMock);
	}
	
	@Test
	public void testAddMenu() throws Exception{		
		String json="{\"menuId\": 1, \"name\":\"Drink\", \"restaurantId\": 2}";
		mvc.perform(post("/menu/add/{id}", 2)
		   .contentType(MediaType.APPLICATION_JSON_UTF8)
		   .content(json))
		   .andExpect(status().isOk());
	}

	@Test
	public void testUpdateMenu() throws Exception{
		Restaurant rest=new Restaurant(0L, "McDonald", "7th Avenue");
		Menu menu=new Menu(1L, "Drink", rest);
		String json="{\"name\":\"Hot Drink\"}";
		mvc.perform(put("/menu/update/{id}", menu.getMenuId())
		   .contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
		   .andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteMenu() throws Exception{
		Restaurant rest=new Restaurant(0L, "McDonald", "7th Avenue");
		Menu menu=new Menu(1L, "Drink", rest);
		given(menucontrollerMock.searchMenus(menu.getMenuId())).willReturn(ResponseEntity.ok(menu));
		mvc.perform(delete("/menu/delete/{id}", menu.getMenuId()))
		   .andExpect(status().isOk());
		verify(menucontrollerMock, times(1)).deleteMenu(menu.getMenuId());
		verifyNoMoreInteractions(menucontrollerMock);
	}
}
