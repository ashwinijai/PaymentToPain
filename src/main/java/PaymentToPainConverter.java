public class PaymentToPainConverter {
    public static void main(String[] args) {
PaymentToPainService service =new PaymentToPainService();
service.convertToPain("PACS002","<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
        "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        "<FIToFIPmtStsRpt>\n" +
        "<GrpHdr>\n" +
        "<MsgId>RBC_PACS002_CAD_CT_20250421220617</MsgId>\n" +
        "<CreDtTm>2025-04-21T22:06:17.000000000</CreDtTm>\n" +
        "</GrpHdr>\n" +
        "<OrgnlGrpInfAndSts>\n" +
        "<OrgnlMsgId>2510401959401006</OrgnlMsgId>\n" +
        "<OrgnlMsgNmId>pacs.003.001.08</OrgnlMsgNmId>\n" +
        "<OrgnlNbOfTxs>7</OrgnlNbOfTxs>\n" +
        "<OrgnlCtrlSum>145.03</OrgnlCtrlSum>\n" +
        "<GrpSts>ACCP</GrpSts>\n" +
        "</OrgnlGrpInfAndSts>\n" +
        "</FIToFIPmtStsRpt>\n" +
        "</Document>");
    }
}
