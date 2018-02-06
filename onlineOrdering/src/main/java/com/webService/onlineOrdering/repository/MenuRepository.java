package com.webService.onlineOrdering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webService.onlineOrdering.models.*;
import java.util.*;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>{
	List<Item> findAllItemsByMenuId(Long menuId);
}
