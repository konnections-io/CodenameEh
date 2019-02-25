package com.example.codenameeh;

import org.junit.Test;

import classes.Geolocation;

import static org.junit.Assert.assertEquals;

public class geolocationUnitTest {
    @Test
    public void testCount() {
        //Initialization
        String country1 = "Canada";
        String city1 = "Edmonton";
        String address1 = "22 Awesome Pl.";
        Geolocation g = new Geolocation(country1, city1, address1);
        //assertEquals(g.returnLocation(), LOCATION); <-- cannot test this until
        //the function is completed and we have determined how the location is represented
        //on the map.
        assertEquals(g.getCity(), city1);
        assertEquals(g.getCountry(), country1);
        assertEquals(g.getAddress(), address1);
        String country2 = "United States";
        String city2 = "New York";
        String address2 = "33 American St.";
        g.setCountry(country2);
        g.setCity(city2);
        g.setAddress(address2);
        //assertEquals(g.returnLocation(), LOCATION2); <-- again, need further dev to test
        assertEquals(g.getCity(), city2);
        assertEquals(g.getCountry(), country2);
        assertEquals(g.getAddress(), address2);
    }
}
