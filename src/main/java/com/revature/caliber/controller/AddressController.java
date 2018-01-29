package com.revature.caliber.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.caliber.beans.Address;
import com.revature.caliber.data.AddressDAO;

/**
 * Used for assessment CRUD operations. Includes both Trainer and QC assessments
 * 
 * @author Emmanuel George
 *
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/AddressController")
public class AddressController {

	@Autowired
	AddressDAO adao;

	/**
	 * User gets all address objects from table
	 *
	 * @param addressId
	 * @return addressList
	 */
	@GetMapping(value = "/getAddresses", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Address> get_addresses() {
		List<Address> addressList = adao.findAll();
		return addressList;
	}

	/**
	 * User provides id# address table is searched, return object if found
	 *
	 * @param addressId
	 * @return address object
	 */
	@GetMapping(value = "/getAddress", produces = MediaType.APPLICATION_JSON_VALUE)
	public Address get_address(@RequestParam(value = "addressId", required = true) int addressId) {
		Address address = adao.findByAddressId(addressId);
		return address;
	}

	/**
	 * User provides all values for an address except for id#, new address is set to
	 * values provided and saved to table
	 *
	 * @param street,
	 *            city, state, zipcode, company, active
	 */
	@GetMapping(value = "/addAddress", produces = MediaType.APPLICATION_JSON_VALUE)
	public void add_address(@RequestParam(value = "street", required = true) String street,
			@RequestParam(value = "city", required = true) String city,
			@RequestParam(value = "state", required = true) String state,
			@RequestParam(value = "zipcode", required = true) String zipcode,
			@RequestParam(value = "company", required = true) String company,
			@RequestParam(value = "active", required = true) boolean active) {

		Address address = new Address();

		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setZipcode(zipcode);
		address.setCompany(company);
		address.setActive(active);

		adao.save(address);
	}

	/**
	 * User provides values, address is set to values provided and saved to table
	 *
	 * @param street,
	 *            city, state, zipcode, company, active
	 */
	@GetMapping(value = "/updateAddress", produces = MediaType.APPLICATION_JSON_VALUE)
	public void update_address(@RequestParam(value = "addressId", required = true) int addressId,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zipcode", required = false) String zipcode,
			@RequestParam(value = "company", required = false) String company,
			@RequestParam(value = "active", required = false) Boolean active) {

		Address address = adao.findByAddressId(addressId);

		if (street != null)
			address.setStreet(street);
		if (city != null)
			address.setCity(city);
		if (state != null)
			address.setState(state);
		if (zipcode != null)
			address.setZipcode(zipcode);
		if (company != null)
			address.setCompany(company);
		if (active != null)
			address.setActive(active);

		adao.save(address);
	}

	/**
	 * User provides id# address table is searched, delete address object if found
	 *
	 * @param addressId
	 */
	@GetMapping(value = "/deleteAddress", produces = MediaType.APPLICATION_JSON_VALUE)
	public void delete_address(@RequestParam(value = "addressId", required = true) int addressId) {

		Address address = adao.findByAddressId(addressId);
		adao.delete(address);
	}

}
