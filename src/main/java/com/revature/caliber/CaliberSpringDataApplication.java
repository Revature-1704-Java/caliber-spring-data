package com.revature.caliber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.revature.caliber.beans.Address;
import com.revature.caliber.beans.Note;
import com.revature.caliber.data.AddressDAO;
import com.revature.caliber.data.NoteDAO;
import com.revature.caliber.beans.Category;
import com.revature.caliber.data.CategoryDAO;

@SpringBootApplication
public class CaliberSpringDataApplication {
	
	@Autowired
	private CategoryDAO catDao;
	@Autowired
	private AddressDAO aDao;
	@Autowired
	private NoteDAO nDao;

	public static void main(String[] args) {
		SpringApplication.run(CaliberSpringDataApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner runner() {
		return args -> {
			List<Address> addresses = aDao.findAll();
			Address a = aDao.findByAddressId(1);
			List<Note> notes = nDao.findAllPublicIndividualNotes(5503);
			
			System.out.println(addresses);
			System.out.println(a);
			System.out.println(notes);
			
			List<Category> categorys = catDao.findAll();
			System.out.println(categorys);
			
			List<Category> activeCategorys = catDao.findAllActive();
			System.out.println(activeCategorys);
		};
	}
}
