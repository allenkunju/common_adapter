package com.ericsson.edr.discoveryadapter.common;

import com.ericsson.edr.discoveryadapter.common.dto.CommonDTO;
import com.telcordia.discoveryadapter.common.DiscoveryException;
import com.telcordia.discoveryadapter.common.Request;
import com.telcordia.discoveryadapter.common.Response;
/**
 * populates the response with MTOSI formatted object hierarchy
 * @author ealljos
 *
 */
@FunctionalInterface
public interface ResponseBuilder {
	
	public Response populateResponse(Request request, Response response, CommonDTO commonDTO) throws DiscoveryException;

}
