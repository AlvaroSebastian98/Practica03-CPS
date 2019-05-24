package com.tecsup.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tecsup.petclinic.domain.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OwnerServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(OwnerServiceTest.class);

	@Autowired
	private OwnerService ownerService;

	/**
	 * 
	 */
	@Test
	public void testFindById() {

		long ID = 1;
		String NAME = "George";
		Owner owner = null;
		
		try {
			owner = ownerService.findById(ID);
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
		logger.info("" + owner);

		assertEquals(NAME, owner.getFirstname());

	}

	/**
	 * 
	 */
	@Test
	public void testFindByFirstname() {

		String FIND_NAME = "Peter";
		int SIZE_EXPECTED = 1;

		List<Owner> owners = ownerService.findByFirstname(FIND_NAME);

		assertEquals(SIZE_EXPECTED, owners.size());
	}


	/**
	 * 
	 */
	@Test
	public void testFindByLastname() {

		String LAST_NAME = "Franklin";
		int SIZE_EXPECTED = 1;

		List<Owner> owners = ownerService.findByLastname(LAST_NAME);

		assertEquals(SIZE_EXPECTED, owners.size());
	}
	
	
	/**
	 * 
	 */
	@Test
	public void testFindByCity() {

		String CITY = "Madison";
		int SIZE_EXPECTED = 4;

		List<Owner> owners = ownerService.findByCity(CITY);

		assertEquals(SIZE_EXPECTED, owners.size());
	}

	/**
	 * 
	 */
	@Test
	public void testDeleteOwner() {

		String OWNER_FIRSTNAME = "Alvaro";
		String OWNER_LASTNAME = "Manuico";
		String OWNER_ADDRESS = "Av. Los Faisanes";
		String OWNER_CITY = "Lima";
		String OWNER_TELEPHONE = "987253426";		

		Owner owner = new Owner(OWNER_FIRSTNAME, OWNER_LASTNAME, OWNER_ADDRESS, OWNER_CITY, OWNER_TELEPHONE);
		owner = ownerService.create(owner);
		logger.info("" + owner);

		try {
			ownerService.delete(owner.getId());
		} catch (OwnerNotFoundException e) {
			fail(e.getMessage());
		}
			
		try {
			ownerService.findById(owner.getId());
			assertTrue(false);
		} catch (OwnerNotFoundException e) {
			assertTrue(true);
		} 
				
	}
	
	
    @Test
	public void testCreate() {
		String FIRSTNAME = "Julio";
		String LASTNAME = "Quiroz"; 
		String CITY = "Lima";
				
		List<Owner> res1 = ownerService.findByLastname(FIRSTNAME);		
				
		List<Owner> res2 = ownerService.findByLastname(LASTNAME);		
								
		List<Owner> res3 = ownerService.findByCity(CITY);		
		
		if(res1.size() > 0 && res2.size() > 0 && res3.size() > 0) {
			logger.info("Owner " + FIRSTNAME + " " + LASTNAME + " exists.\n"
					+ "You need to create other owner");
			return;
		}
		
		Owner owner = new Owner(FIRSTNAME, LASTNAME, CITY);
		
		Owner newOwner = ownerService.create(owner);
		
		try {
			Owner resOwner = ownerService.findById(newOwner.getId());
			logger.info("Owner created.");
		}catch (OwnerNotFoundException e) {
			logger.info("Owner not created.");
		}
		
		Iterable<Owner> listOwner = ownerService.findAll();
		
		while(listOwner.iterator().hasNext()) {
			try {
				Owner ownerFound= ownerService.findById(newOwner.getId());
				logger.info("Owner found successfully");
				break;
			}catch (OwnerNotFoundException e) {
				logger.info("Owner not found");
			}
		}
	}
}
