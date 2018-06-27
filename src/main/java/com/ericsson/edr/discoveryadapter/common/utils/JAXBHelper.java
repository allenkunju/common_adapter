package com.ericsson.edr.discoveryadapter.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.xml.sax.InputSource;

/*
 * 
 * Class Name: JAXBHelper
 * 
 */

public class JAXBHelper {

	private static String XML_ENCODING = "UTF-8";

	public static String marshalToString(Object rootElement) throws JAXBException, UnsupportedEncodingException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final String packageName = rootElement.getClass().getPackage().getName();
		final Marshaller marshaller = JAXBContext.newInstance(packageName).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		marshaller.marshal(rootElement, baos);
		return baos.toString(XML_ENCODING);
	}

	public static Object unmarshall(String xml, Class<?> clazz) throws JAXBException {
		final String packageName = clazz.getPackage().getName();
		return JAXBContext.newInstance(packageName).createUnmarshaller()
				.unmarshal(new InputSource(new StringReader(xml)));
	}
}
