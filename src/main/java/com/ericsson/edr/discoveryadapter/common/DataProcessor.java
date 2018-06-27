/**
 * 
 */
package com.ericsson.edr.discoveryadapter.common;

import com.ericsson.edr.discoveryadapter.common.dto.CommonDTO;
import com.telcordia.discoveryadapter.common.DiscoveryException;

/**
 * To process the raw data read from network files, 
 * applying business logic to match the data with EAI 
 * and handle the relations between objects 
 */
@FunctionalInterface
public interface DataProcessor {
	
	/** 
	 * The generated hierarchy is different for multiple flows. 
It has to be handled in Response Builder.
	 * @param parserOutput
	 * @return Object hierarchy in CommonDTO format. 
	 * @throws HandlerException
	 */
	public CommonDTO processData(Object parserOutput) throws DiscoveryException;

}
