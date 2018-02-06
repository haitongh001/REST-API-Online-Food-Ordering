package com.webService.onlineOrdering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.webService.onlineOrdering.models.*;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	List<Restaurant> findAllMenusByRestaurantId(Long restaurantId);
}
