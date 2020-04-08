package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

public class GeoIPServiceTests {

    @Test
    public  void testMyIP() {
        String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("109.252.58.142");
        System.out.println(ipLocation);
    }
}


