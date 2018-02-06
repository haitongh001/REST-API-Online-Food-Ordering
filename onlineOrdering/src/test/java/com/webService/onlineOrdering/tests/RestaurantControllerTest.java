package com.webService.onlineOrdering.tests;

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
import com.webService.onlineOrdering.controllers.RestaurantController;
import com.webService.onlineOrdering.models.Restaurant;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
@WebAppConfiguration
public class RestaurantControllerTest {

	@Autowired
	private MockMvc mvc;
	
	// Create a Mock for RestaurantController which can be used to bypass the call to the actual RestaurantController
	@MockBean
	private RestaurantController restaurantcontrollerMock;
	
	@Test
	public void testGetAllRestaurants() throws Exception{
		List<Restaurant> result=Arrays.asList(
				new Restaurant(1L, "McDonald", "55th Street"),
				new Restaurant(2L, "PizzaHut", "7th Avenue"));
		given(restaurantcontrollerMock.getAllRestaurants()).willReturn(result);
		mvc.perform(get("/restaurant/all"))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("$", hasSize(2)))
		   .andExpect(jsonPath("$[0].restaurantId", is(1)))
		   .andExpect(jsonPath("$[0].name", is("McDonald")))
		   .andExpect(jsonPath("$[0].address", is("55th Street")))
		   .andExpect(jsonPath("$[1].restaurantId", is(2)))
		   .andExpect(jsonPath("$[1].name", is("PizzaHut")))
		   .andExpect(jsonPath("$[1].address", is("7th Avenue")));
		verify(restaurantcontrollerMock, times(1)).getAllRestaurants();
		verifyNoMoreInteractions(restaurantcontrollerMock);
	}

	@Test
	public void testSearchRestaurantOK() throws Exception{
		Restaurant rest=new Restaurant(1L, "McDonald", "55th Street");
		given(restaurantcontrollerMock.searchRestaruant(1L)).willReturn(ResponseEntity.ok(rest));
		mvc.perform(get("/restaurant/search/{id}", 1))
		   .andExpect(status().isOk())
		   .andExpect(jsonPath("restaurantId", is(1)))
		   .andExpect(jsonPath("name", is("McDonald")))
		   .andExpect(jsonPath("address", is("55th Street"))); 
		verify(restaurantcontrollerMock, times(1)).searchRestaruant(1L);
		verifyNoMoreInteractions(restaurantcontrollerMock);
	}
	
	@Test
	public void testSearchRestaurantNotFound() throws Exception{
		given(restaurantcontrollerMock.searchRestaruant(1L)).willReturn(ResponseEntity.notFound().build());
		mvc.perform(get("/restaurant/search/{id}", 1))
		   .andExpect(status().isNotFound()); 
		verify(restaurantcontrollerMock, times(1)).searchRestaruant(1L);
		verifyNoMoreInteractions(restaurantcontrollerMock);
	}
	
	@Test
	public void testRegisterRestaurant() throws Exception{		
		String json="{\"restaurantId\": 1, \"name\":\"McDonald\", \"address\":\"55th Street\"}";
		mvc.perform(post("/restaurant/register")
		   .contentType(MediaType.APPLICATION_JSON_UTF8)
		   .content(json))
		   .andExpect(status().isOk());
	}

	@Test
	public void testUpdateRestaurant() throws Exception{
		Restaurant rest=new Restaurant(1L, "McDonald", "55th Street");
		String json="{\"restaurantId\":1, \"name\":\"KFC\", \"address\":\"78th Street\"}";
		mvc.perform(put("/restaurant/update/{id}", rest.getRestaurantId())
		   .contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
		   .andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteRestaurant() throws Exception{
		Restaurant rest=new Restaurant(1L, "McDonald", "55th Street");
		given(restaurantcontrollerMock.searchRestaruant(rest.getRestaurantId())).willReturn(ResponseEntity.ok(rest));
		mvc.perform(delete("/restaurant/delete/{id}", rest.getRestaurantId()))
		   .andExpect(status().isOk());
		verify(restaurantcontrollerMock, times(1)).deleteRestaurant(rest.getRestaurantId());
		verifyNoMoreInteractions(restaurantcontrollerMock);
	}

}
