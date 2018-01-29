package com.revature.caliber.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.caliber.beans.Address;

/**
 *
 * @author Christian Acosta
 * @author Emmanuel George
 */
@Repository
public interface AddressDAO extends JpaRepository<Address, Integer> {

	/**
	 * Find address by a given Id
	 *
	 * @param id
	 * @return Address
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Address findByAddressId(int id);
}