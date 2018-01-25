package com.revature.caliber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import com.revature.caliber.beans.Address;
import com.revature.caliber.beans.Category;
import com.revature.caliber.data.AddressDAO;
import com.revature.caliber.data.CategoryDAO;
import com.revature.caliber.repository.AddressRepository;

@SpringBootApplication
public class CaliberSpringDataApplication {
	
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private CategoryDAO catDao;
	
	@Autowired
	private AddressDAO aDao;

	public static void main(String[] args) {
		SpringApplication.run(CaliberSpringDataApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner runner() {
		return args -> {
			List<Address> addresses = addressRepository.findAll();
			System.out.println(addresses);
			
			Address a = aDao.findByAddressId(1);
			System.out.println(a);
			
			List<Category> categorys = catDao.findAll();
			System.out.println(categorys);
			
			List<Category> activeCategorys = catDao.findAllActive();
			System.out.println(activeCategorys);
		};
	}
}
