//package com.example.booking;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class FirstProjectApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(FirstProjectApplication.class, args);
//	}
//
//}

package com.example.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")  // 👈 scan everything under com.example
public class FirstProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(FirstProjectApplication.class, args);
	}
}