/**
 * 
 */
package com.ericsson.edr.discoveryadapter.common;

import org.apache.log4j.Logger;

import com.ericsson.edr.discoveryadapter.common.dto.CommonDTO;
import com.telcordia.discoveryadapter.common.DiscoveryException;
import com.telcordia.discoveryadapter.common.Request;
import com.telcordia.discoveryadapter.common.Response;

/**
 * Adapter handles the life cycle of parsing network files and generating
 * reports for DDS <p>
 *  Adapter can be extended for implementing any flows which
 * follows the pattern of reading the network files, parsing it, applying business logic and generating Response. <p>
 * Adapter can be initialized by specifying the FileParser implementation and DataProcessor implementation <p>
 * Any class extending adapter must specify Response generation logic
 * 
 */
public class Adapter{
    private static final Logger LOGGER = Logger.getLogger(Adapter.class);
	
    private DataCollector dataCollector;
    private DataProcessor dataProcessor;
    private ResponseBuilder responseBuilder;
	
	public Adapter(DataCollector dataCollector, DataProcessor dataProcessor, ResponseBuilder responseBuilder){
		this.dataCollector = dataCollector;
		this.dataProcessor = dataProcessor;
		this.responseBuilder = responseBuilder;
	}

	/**
	 * executes below steps 
	 * 1. collecting data from NMS
	 * 2. processing raw data and applying business logics
	 * 3. generating MTOSI response
	 * @param request 
	 * @param response
	 * @return Response
	 * @throws DiscoveryException
	 */
	public Response execute(Request request, Response response) throws DiscoveryException{
		try {
			/* parse the required information from network files */
			Object parserOutput = dataCollector.collectData(request);
						
			/* apply business logic and generate objects hierarchy with required attributes */
			CommonDTO commonDTO = dataProcessor.processData(parserOutput);
		
			/* convert the processed data to MTOSI response and associate with response */
			responseBuilder.populateResponse(request, response, commonDTO);
			
		} catch (DiscoveryException ex) {
			LOGGER.error(ex);
			throw ex;//look for error codes starting with "ENM." from AdapterMessages.properties
        }
		
		return response;
	}

}
