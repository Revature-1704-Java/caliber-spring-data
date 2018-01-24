package com.revature.caliber.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.caliber.beans.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	public List<Address> findAll();
}
