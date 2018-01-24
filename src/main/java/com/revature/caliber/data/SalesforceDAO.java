package com.revature.caliber.data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.caliber.Salesforce;
import com.revature.caliber.beans.Batch;
import com.revature.caliber.beans.Trainee;
import com.revature.caliber.exceptions.ServiceNotAvailableException;
import com.revature.caliber.salesforce.SalesforceTransformerToCaliber;
import com.revature.caliber.security.models.SalesforceUser;
import com.revature.salesforce.beans.SalesforceBatch;
import com.revature.salesforce.beans.SalesforceBatchResponse;
import com.revature.salesforce.beans.SalesforceTrainee;
import com.revature.salesforce.beans.SalesforceTraineeResponse;

/**
 * Interacts with Salesforce REST API and transforms data into Caliber beans
 * 
 * @author Patrick Walsh
 * @author
 * @author
 *
 */
@Repository
public interface SalesforceDAO extends JpaRepository<Salesforce, Integer>{

	static final Logger log = Logger.getLogger(SalesforceDAO.class);

	/*

	@Value("#{systemEnvironment['CALIBER_DEV_MODE']}") 
	boolean debug;

	@Value("#{systemEnvironment['SALESFORCE_INSTANCE_URL']}") 
	String salesforceInstanceUrl;
	
	@Value("/services/data/v39.0/query/")
	String salesforceApiUrl;

	//////////// SOQL - Salesforce Object Query Language //////////////

	/**
	 * Will change as of version 2.0 Salesforce API in August/September 2017
	 * timeframe Used to populate the dropdown list of importable batches.
	 */
	
	/*
	@Value("select id, name, batch_start_date__c, batch_end_date__c, "
			+ "batch_trainer__r.name, batch_trainer__r.email, Co_Trainer__r.name, Co_Trainer__r.email, "
			+ "Skill_Type__c, Location__c, Type__c from training__c "
			+ "where batch_trainer__r.name != null and batch_start_date__c >= THIS_YEAR") 
	String relevantBatches;
	*/

	/**
	 * Will change as of version 2.0 Salesforce API in August/September 2017
	 * timeframe Once user selects a batch to import, use this to load all the
	 * Trainee details. ResourceId *MUST* be surrounded in single quotes to
	 * function properly
	 */
	
	/*
	@Value("select id, name, training_status__c, phone, email, MobilePhone, Training_Batch__c ,"
			+ " Training_Batch__r.name, Training_Batch__r.batch_start_date__c, Training_Batch__r.batch_end_date__c,"
			+ " Training_Batch__r.batch_trainer__r.name, rnm__Recruiter__r.name, account.name, "
			+ "Training_Batch__r.Co_Trainer__r.name, eintern_current_project_completion_pct__c , "
			+ "Training_Batch__r.Skill_Type__c, Training_Batch__r.Type__c, Screener__c, Screen_Feedback__c, "
			+ "Associates_Degree__c, Bachelors_Degree__c, Masters_Degree__c from Contact where training_batch__c =")
	String batchDetails;

	*/
	
	//////////// REST Consumer Methods -- Salesforce REST API //////////////


	/**
	 * Get all the trainees for a single batch. Access data using the Salesforce
	 * REST API
	 * 
	 * @return
	 */
	public List<Trainee> getBatchDetails(String resourceId);

	//////////// API Helper Methods //////////////


	/**
	 * Get all the batches in the current year and future years. Access data
	 * using the Salesforce REST API. Returns as String in case the result is
	 * not actually a batch. Used to debug environment issues.
	 * 
	 * @return
	 */
	public String getSalesforceResponseString();

	/**
	 * Get batch details. Access data using the Salesforce REST API. Returns as
	 * String in case the result is not actually a batch. Used to debug
	 * environment issues.
	 * 
	 * @return
	 */
	public String getSalesforceTraineeResponseString(String resourceId);

	public void setTransformer(SalesforceTransformerToCaliber transformer);
}
