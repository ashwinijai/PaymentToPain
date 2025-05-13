import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

public class XsdValidator {
    public static boolean validateXMLSchema(String xsdVal, String xmlVal) throws IOException, SAXException {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new StringReader(xsdVal)));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlVal)));
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            throw e;
        } catch (SAXException e1) {
            System.out.println("SAX Exception: " + e1.getMessage());
            throw e1;
        }
        return true;

    }
}
