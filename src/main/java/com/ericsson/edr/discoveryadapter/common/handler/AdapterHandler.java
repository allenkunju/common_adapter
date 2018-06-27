package com.ericsson.edr.discoveryadapter.common.handler;
import org.apache.log4j.Logger;

import com.ericsson.edr.discoveryadapter.common.Adapter;
import com.telcordia.discoveryadapter.common.DiscoveryException;
import com.telcordia.discoveryadapter.common.Request;
import com.telcordia.discoveryadapter.common.RequestHandler;
import com.telcordia.discoveryadapter.common.Response;

/**
 * this is a parent class for all Handlers. 
 * Handlers are factories for Adapters to be executed.
 * It follows factory method design pattern, where child classes handles creation of Adapter objects
 */
public abstract class AdapterHandler extends RequestHandler {

    private static final Logger LOGGER = Logger.getLogger(AdapterHandler.class);
    
	@Override
	protected void processRequest(Request request, Response response) throws DiscoveryException {
        LOGGER.debug("In AdapterHandler.processRequest(Request req, Response response) method.");
      
        try {
        	Adapter adapter = getAdapter(request);
            
            adapter.execute(request, response);
           
        } catch (DiscoveryException ex) {
            LOGGER.error(ex);
            throw ex;
        }
    }

	protected abstract Adapter getAdapter(Request request) throws DiscoveryException;

}