package com.webService.onlineOrdering.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ItemControllerTest.class, MenuControllerTest.class,
		RestaurantControllerTest.class })
public class AllTests {

}
