
package com.soap.ws.client.generated;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.soap.ws.client.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private static final QName _CalculateMensualite_QNAME = new QName("http://example.org/", "calculateMensualite");
    private static final QName _CalculateMensualiteResponse_QNAME = new QName("http://example.org/", "calculateMensualiteResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.soap.ws.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CalculateMensualite }
     * 
     * @return
     *     the new instance of {@link CalculateMensualite }
     */
    public CalculateMensualite createCalculateMensualite() {
        return new CalculateMensualite();
    }

    /**
     * Create an instance of {@link CalculateMensualiteResponse }
     * 
     * @return
     *     the new instance of {@link CalculateMensualiteResponse }
     */
    public CalculateMensualiteResponse createCalculateMensualiteResponse() {
        return new CalculateMensualiteResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateMensualite }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateMensualite }{@code >}
     */
    @XmlElementDecl(namespace = "http://example.org/", name = "calculateMensualite")
    public JAXBElement<CalculateMensualite> createCalculateMensualite(CalculateMensualite value) {
        return new JAXBElement<>(_CalculateMensualite_QNAME, CalculateMensualite.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateMensualiteResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateMensualiteResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://example.org/", name = "calculateMensualiteResponse")
    public JAXBElement<CalculateMensualiteResponse> createCalculateMensualiteResponse(CalculateMensualiteResponse value) {
        return new JAXBElement<>(_CalculateMensualiteResponse_QNAME, CalculateMensualiteResponse.class, null, value);
    }

}
