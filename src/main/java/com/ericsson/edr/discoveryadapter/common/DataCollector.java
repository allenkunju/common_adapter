package com.ericsson.edr.discoveryadapter.common;

import com.telcordia.discoveryadapter.common.DiscoveryException;
import com.telcordia.discoveryadapter.common.Request;

/**
 * collect the data from NMS
 *
 */
@FunctionalInterface
public interface DataCollector {
	
	public Object collectData(Request request) throws DiscoveryException;

}
