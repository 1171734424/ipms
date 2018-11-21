package com.ideassoft.portal;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.ideassoft.portal package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _Call_QNAME = new QName(
			"http://portal.ideassoft.com", "call");
	private final static QName _CallResponse_QNAME = new QName(
			"http://portal.ideassoft.com", "callResponse");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: com.ideassoft.portal
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link Call }
	 * 
	 */
	public Call createCall() {
		return new Call();
	}

	/**
	 * Create an instance of {@link CallResponse }
	 * 
	 */
	public CallResponse createCallResponse() {
		return new CallResponse();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link Call }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://portal.ideassoft.com", name = "call")
	public JAXBElement<Call> createCall(Call value) {
		return new JAXBElement<Call>(_Call_QNAME, Call.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CallResponse }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://portal.ideassoft.com", name = "callResponse")
	public JAXBElement<CallResponse> createCallResponse(CallResponse value) {
		return new JAXBElement<CallResponse>(_CallResponse_QNAME,
				CallResponse.class, null, value);
	}

}
