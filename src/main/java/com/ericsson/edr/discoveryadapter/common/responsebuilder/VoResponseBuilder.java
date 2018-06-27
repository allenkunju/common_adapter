package com.ericsson.edr.discoveryadapter.common.responsebuilder;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import com.ericsson.edr.discoveryadapter.common.ResponseBuilder;
import com.ericsson.edr.discoveryadapter.common.dto.CommonDTO;
import com.ericsson.edr.discoveryadapter.common.utils.JAXBHelper;
import com.telcordia.discoveryadapter.common.DiscoveryException;
import com.telcordia.discoveryadapter.common.DiscoveryExceptionType;
import com.telcordia.discoveryadapter.common.Request;
import com.telcordia.discoveryadapter.common.Response;
import com.telcordia.discoveryadapter.common.VendorObject;

public abstract class VoResponseBuilder implements ResponseBuilder {
	
	private String name;
	
	protected VoResponseBuilder(String name) {
		this.name = name;
	}

	@Override
	public Response populateResponse(Request request, Response response, CommonDTO commonDTO) throws DiscoveryException {
		
		Object rootObject = this.generateRootObject(commonDTO);
		
		VendorObject vendorObject = new VendorObject(this.name);
		try {
			vendorObject.setVendorXmlString(JAXBHelper.marshalToString(rootObject));
		} catch (JAXBException | UnsupportedEncodingException e) {
			throw new DiscoveryException(DiscoveryExceptionType.EXCPT_MAKE_XML, e);
		} 
		response.getResponseData().setHighLevelObject(vendorObject);

		return response;
	}

	protected abstract Object generateRootObject(CommonDTO commonDTO) ;

}
