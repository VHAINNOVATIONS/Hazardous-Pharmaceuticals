/**
 * Source file created in 2007 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.common.utility;


import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.util.JAXBSource;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import gov.va.med.pharmacy.peps.common.exception.InterfaceException;
import gov.va.med.pharmacy.peps.common.exception.InterfaceValidationException;


/**
 * Provides common XML document functionality.
 * 
 * @param <T> Document classname
 */
public class XmlDocument<T> {

    private static final SchemaFactory SCHEMA_FACTORY = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

    private JAXBContext jaxbContext;
    private String[] cDataElements;
    private Schema schema;
    private Transformer[] xslTransformers;

    /**
     * Construct document.
     * 
     * @param className classname
     * @param cDataElements elements that require cdata tags
     * @param schemaFiles relative schema file URI(s)
     * @param xslFiles relative XSL file URI(s)
     */
    public XmlDocument(Class<T> className, String[] cDataElements, String[] schemaFiles, String[] xslFiles) {
        try {
            this.jaxbContext = JAXBContext.newInstance(className.getPackage().getName(), Thread.currentThread()
                .getContextClassLoader());
        } catch (JAXBException e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
        }

        this.cDataElements = cDataElements;

        if (schemaFiles.length > 0) {
            Source[] sources = new Source[schemaFiles.length];

            for (int i = 0; i < schemaFiles.length; i++) {
                sources[i] = new StreamSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                    schemaFiles[i]));
            }

            synchronized (SCHEMA_FACTORY) {
                try {
                    this.schema = SCHEMA_FACTORY.newSchema(sources);
                } catch (java.lang.Exception e) {
                    throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION);
                }
            }
        }

        if (xslFiles.length > 0) {
            this.xslTransformers = new Transformer[xslFiles.length];

            for (int i = 0; i < xslFiles.length; i++) {
                synchronized (TRANSFORMER_FACTORY) {
                    try {
                        xslTransformers[i] = TRANSFORMER_FACTORY.newTransformer(new StreamSource(Thread.currentThread()
                            .getContextClassLoader().getResourceAsStream(xslFiles[i])));
                    } catch (java.lang.Exception e) {
                        throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR,
                            InterfaceException.PRE_ENCAPSULATION);
                    }
                }
            }
        }
    }

    /**
     * Get the XML namespace for a class.
     * 
     * @param c Class
     * @return XML namespace
     */
    public static String toNamespace(Class c) {
        return c.getPackage().getName().replace(".", "/");
    }

    /**
     * Unmarshal the XML object into the given type.
     * 
     * @param xml String XML request
     * @return DrugData created from the XML request
     */
    public T unmarshal(String xml) {
        try {
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setEventHandler(new ValidationEventCollector());

            if (schema != null) {
                unmarshaller.setSchema(schema);
            }

            T request = (T) unmarshaller.unmarshal(new StreamSource(new BufferedReader(new StringReader(xml))));

            handleValidationEvents((ValidationEventCollector) unmarshaller.getEventHandler(), getClassName(request
                .getClass()));

            if (xslTransformers != null) {
                validateStylesheet(request);
            }

            return request;
        } catch (JAXBException e) {
            String message = e.getLocalizedMessage();

            if (message == null && e.getLinkedException() != null) {
                message = e.getLinkedException().getLocalizedMessage();
            }

            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION,
                message);
        } catch (java.lang.Exception e) {
            throw new InterfaceException(e, InterfaceException.INTERFACE_ERROR, InterfaceException.PRE_ENCAPSULATION, e
                .getLocalizedMessage());
        }
    }

    /**
     * Marshal an object into an XML String.
     * 
     * @param xmlObject JAXB XML object to marshal
     * @return XML String representing given Exception
     */
    public String marshal(T xmlObject) {
        return marshal(xmlObject, false, true);
    }

    /**
     * Return a String formatted with indents to make System.out or Log4j output easier to read.
     * 
     * @param xmlObject JAXB XML object to marshal
     * @return XML String representing given Exception
     */
    public String toIndentedString(T xmlObject) {
        return marshal(xmlObject, true, true);
    }

    /**
     * Return a String formatted with indents to make System.out or Log4j output easier to read.
     * 
     * @param xml String XML to pretty print
     * @return String formatted XML
     */
    public String toIndentedString(String xml) {
        return toIndentedString(unmarshal(xml));
    }

    /**
     * Marshal an object into an XML String.
     * 
     * @param xmlObject JAXB XML object to marshal
     * @param forceIndentXml boolean true if indenting/pretty printing should be forced
     * @param validate true to perform schema and XSL validation
     * @return XML String representing given Exception
     */
    private String marshal(T xmlObject, boolean forceIndentXml, boolean validate) {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setEventHandler(new ValidationEventCollector());

            if (validate && (schema != null)) {
                marshaller.setSchema(schema);
            }

            StringWriter xmlResponse = new StringWriter();
            marshaller.marshal(xmlObject, getXmlSerializer(xmlResponse, cDataElements, forceIndentXml));

            handleValidationEvents((ValidationEventCollector) marshaller.getEventHandler(), getClassName(xmlObject
                .getClass()));

            if (validate && (xslTransformers != null)) {
                validateStylesheet(xmlObject);
            }

            return xmlResponse.toString();
        } catch (java.lang.Exception e) {

            // log XML that failed to marshal
            throw new InterfaceException(e, InterfaceException.INTERFACE_MARSHAL_FAILURE, marshal(xmlObject, true, false));
        }
    }

    /**
     * If there are any events, convert the events into an error message string and throw a new InterfaceValidationException.
     * 
     * @param handler ValidationEventController with any validation events
     * @param messageType String describing what type of message caused the error (Request, Response, or exception message)
     */
    private void handleValidationEvents(ValidationEventCollector handler, String messageType) {
        if (handler.hasEvents()) {
            ValidationEvent[] events = handler.getEvents();
            StringBuffer errorMessage = new StringBuffer();

            for (int i = 0; i < events.length; i++) {
                if (i > 0) {
                    errorMessage.append("\n");
                }

                ValidationEventLocator locator = events[i].getLocator();

                if (locator.getNode() != null) {
                    errorMessage.append(locator.getNode().getNamespaceURI()).append(":").append(
                        locator.getNode().getNodeName());
                }

                errorMessage.append(" [").append(locator.getLineNumber()).append("] - ").append(events[i].getMessage());
            }

            throw new InterfaceValidationException(InterfaceValidationException.VALIDATION_ERROR,
                InterfaceException.PRE_ENCAPSULATION, messageType, errorMessage);
        }
    }

    /**
     * Validate an object against an XSL stylesheet.
     * 
     * @param request Object to validate
     * @throws JAXBException exception
     * @throws TransformerException exception
     */
    private void validateStylesheet(T request) throws JAXBException, TransformerException {

        JAXBSource source = new JAXBSource(jaxbContext, request);
        StringWriter writer = new StringWriter();

        for (int i = 0; i < xslTransformers.length; i++) {
            synchronized (TRANSFORMER_FACTORY) {
                Transformer t = xslTransformers[i];

                synchronized (t) {
                    t.transform(source, new StreamResult(writer));
                }
            }
        }

        String errorMessages = writer.toString();

        if (errorMessages.length() > 0) {
            throw new TransformerException("The following XML validation rules fired:\n" + errorMessages);
        }
    }

    /**
     * Set String fields to be wrapped in CDATA blocks.
     * 
     * @param writer Writer to send output to
     * @param cdataElements String array of XML elements to wrap in CDATA blocks
     * @param forceIndent boolean true if output should be indented, otherwise use the current system default
     * @return XMLSerializer to handle wrapping String fields in CDATA blocks
     */
    private XMLSerializer getXmlSerializer(Writer writer, String[] cdataElements, boolean forceIndent) {
        OutputFormat of = new OutputFormat();
        of.setCDataElements(cdataElements);
        of.setIndenting(forceIndent ? true : Boolean.getBoolean("pre.interface.xml.indent"));
        of.setLineWidth(Integer.parseInt(System.getProperty("pre.interface.xml.line.width", "80")));
        of.setOmitXMLDeclaration(Boolean.getBoolean("pre.interface.xml.declaration"));
        of.setLineSeparator(System.getProperty("pre.interface.xml.line.separator", ""));

        // should be using US-ASCII but breaks VistA Parser
        of.setEncoding(System.getProperty("pre.interface.xml.encoding", "UTF-8"));

        return new XMLSerializer(writer, of);
    }

    /**
     * Get just the class name from a fully qualified class
     * 
     * @param clazz Class
     * @return String name of the class without its package
     */
    private String getClassName(Class clazz) {
        int beginIndex = clazz.getName().lastIndexOf(".") + 1;

        return clazz.getName().substring(beginIndex);
    }
}
