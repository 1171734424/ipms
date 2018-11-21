package com.ideassoft.portal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;

import com.ideassoft.core.constants.CommonConstants;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * <p>
 * An example of how this class may be used:
 * 
 * <pre>
 * DataDealPortalService service = new DataDealPortalService();
 * IDataDealPortal portType = service.getDataDealPortalPort();
 * portType.call(...);
 * </pre>
 * 
 * </p>
 * 
 */

@WebServiceClient(name = "DataDealPortalService", targetNamespace = "http://portal.ideassoft.com", wsdlLocation = "https://www.ideassoft.com/cdds/ws/call?wsdl")
public class DataDealPortalService extends Service {

	private final static URL DATADEALPORTALSERVICE_WSDL_LOCATION;
	private final static Logger logger = Logger
			.getLogger(com.ideassoft.portal.DataDealPortalService.class
					.getName());

	static {
		URL url = null;
		try {
			URL baseUrl;
			baseUrl = com.ideassoft.portal.DataDealPortalService.class
					.getResource(".");
			url = new URL(baseUrl, CommonConstants.Path.WSDLURL);
		} catch (MalformedURLException e) {
			logger.warning("Failed to create URL for the wsdl Location: '"+CommonConstants.Path.WSDLURL+"', retrying as a local file");
			logger.warning(e.getMessage());
		}
		DATADEALPORTALSERVICE_WSDL_LOCATION = url;
	}

	public DataDealPortalService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public DataDealPortalService() {
		super(DATADEALPORTALSERVICE_WSDL_LOCATION, new QName(
				"http://portal.ideassoft.com", "DataDealPortalService"));
	}

	/**
	 * 
	 * @return returns IDataDealPortal
	 */
	@WebEndpoint(name = "DataDealPortalPort")
	public IDataDealPortal getDataDealPortalPort() {
		return super.getPort(new QName("http://portal.ideassoft.com",
				"DataDealPortalPort"), IDataDealPortal.class);
	}

}
