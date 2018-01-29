package com.revature.caliber.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;

import oracle.jdbc.pool.OracleDataSource;

//@Configuration
public class CaliberSpringDataConfig {
	@Bean
	public DataSource dataSource() throws Exception {
		OracleDataSource ods = new OracleDataSource();
		
		ods.setUser(System.getenv("CALIBER_DB_USER"));
		ods.setPassword(System.getenv("CALIBER_DB_PASS"));
		ods.setURL(System.getenv("CALIBER_DB_URL"));
		ods.setDriverType("oracle.jdbc.driver.OracleDriver");
		return ods;
	}
}
