import com.pacs.pain.processor.CustomerPaymentStatusReportV03;
import com.pacs.pain.processor.GroupHeader36;
import com.pacs.pain.processor.OriginalGroupInformation20;
import com.pacs.pain.processor.TransactionGroupStatus3Code;
import com.pacs.payments.processor.Document;
import com.pacs.payments.processor.OriginalGroupHeader17;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;

public class PaymentToPainService {
    private static Logger logger = LoggerFactory.getLogger(PaymentToPainService.class);

    public String convertToPain(String paymentType, String paymentMessage) {
        if (paymentType.equals("PACS002")) {
            try {
                return convertToPain(paymentMessage);
            } catch (JAXBException e) {
                logger.error(e.getMessage());
            }
        }
        return null;
    }

    private String convertToPain(String paymentMessage) throws JAXBException {
        String painXml = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Document> pacsDocumentJaxb = (JAXBElement<Document>) jaxbUnmarshaller.unmarshal(new StringReader(paymentMessage));
        Document pacsDocument =pacsDocumentJaxb.getValue();
        if (null != pacsDocument && null != pacsDocument.getFIToFIPmtStsRpt()) {
            com.pacs.pain.processor.Document painDocument = new com.pacs.pain.processor.Document();
            CustomerPaymentStatusReportV03 customerPaymentStatusReportV03 = new CustomerPaymentStatusReportV03();
            painDocument.setCstmrPmtStsRpt(customerPaymentStatusReportV03);
            if (null != pacsDocument.getFIToFIPmtStsRpt().getGrpHdr()) {
                GroupHeader36 groupHeader = new GroupHeader36();
                groupHeader.setMsgId(pacsDocument.getFIToFIPmtStsRpt().getGrpHdr().getMsgId());
                groupHeader.setCreDtTm(pacsDocument.getFIToFIPmtStsRpt().getGrpHdr().getCreDtTm());
                customerPaymentStatusReportV03.setGrpHdr(groupHeader);
            }
            if (null != pacsDocument.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts() && !pacsDocument.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().isEmpty()) {
                OriginalGroupHeader17 pacsOrgGroupInfo = pacsDocument.getFIToFIPmtStsRpt().getOrgnlGrpInfAndSts().get(0);
                OriginalGroupInformation20 originalGroupInfo = new OriginalGroupInformation20();
                originalGroupInfo.setOrgnlMsgId(pacsOrgGroupInfo.getOrgnlMsgId());
                originalGroupInfo.setOrgnlMsgNmId(pacsOrgGroupInfo.getOrgnlMsgNmId());
                originalGroupInfo.setOrgnlNbOfTxs(pacsOrgGroupInfo.getOrgnlNbOfTxs());
                originalGroupInfo.setOrgnlCtrlSum(pacsOrgGroupInfo.getOrgnlCtrlSum());
                originalGroupInfo.setGrpSts(TransactionGroupStatus3Code.valueOf(pacsOrgGroupInfo.getGrpSts()));
                customerPaymentStatusReportV03.setOrgnlGrpInfAndSts(originalGroupInfo);
            }
            painXml = convertToString(painDocument);
            logger.info("Converted PAIN xml - {}", painXml);
            System.out.println("Converted PAIN xml - "+ painXml);
        }
        return painXml;
    }

    public static String convertToString(Object jaxbObject) throws JAXBException {
        JAXBElement<?> jaxbElement = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(jaxbObject.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        if(jaxbObject instanceof com.pacs.pain.processor.Document painDocument) {
            jaxbElement
                    = new JAXBElement<>(new QName("", "document"), com.pacs.pain.processor.Document.class, painDocument);
        }
        if(null!=jaxbElement) {
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(jaxbElement, sw);
            return sw.toString();
        }
        return null;
    }

}

