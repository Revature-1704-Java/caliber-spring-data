package com.revature.caliber.data;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.model.Address;

/**
 *
 * @author Christian Acosta
 * 
 */
@Repository
public interface AddressDAO extends JpaRepository<Address, Integer> {

	/**
	 * Save location
	 *
	 * @param id
	 * @return the address with the specified id
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Address findByAddressId(int id);
}