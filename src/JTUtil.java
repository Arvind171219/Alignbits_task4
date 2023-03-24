import com.aspose.cells.TxtSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;
import ipworksssl.IPWorksSSLException;
import ipworksssl.Rests;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Created by rgaurava on 4/6/17.
 */
public final class JTUtil {

    static Long resourceID = 1L;

    public static void logSuccessEvent(String message, Long transactionId, String payload) {
        System.out.println(message + ": " + payload);
    }

    public static void logFailureEvent(String message, Long transactionId, String payload) {
        System.out.println(message + ": " + payload);
    }

    public static void logWarningEvent(String message, Long transactionId, String payload) {
        System.out.println(message + ": " + payload);
    }

    // - Method to upload the file
    public static Long uploadFile(InputStream stream, String extension) {
        return resourceID;

    }

    // - Method to download            /≥≤the file based on resourceId
    public static InputStream downloadFile(Long resourceId) {

        try {
            byte[] byteArray = Files.readAllBytes(Paths.get("/home/vincomind/Desktop/pdf.pdf"));
            return new ByteArrayInputStream(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }



    public static String convertPDFToXML(FileInputStream fis) throws Exception {
//        com.aspose.pdf.License pdfLicense = new com.aspose.pdf.License();
//        pdfLicense.setLicense(Objects.requireNonNull(getClassLoader()
//                .getResourceAsStream("Aspose.Total.Java.lic")));

        Document doc = new Document(fis);
        String outXml = "/share/outXML_" + getRandom(9) + "_"+ getRandom(9) + ".xml";
        doc.save(outXml, SaveFormat.MobiXml);

        String text = new String(Files.readAllBytes(Paths.get(outXml)));
        text = text.substring(text.indexOf("<"), text.length());
        return text;
    }

    public static void uploadFile(String message, Long transactionId, String payload) {
        System.out.println(message + ": " + payload);
    }

    public static String getID(Long transactionId, String fname) {
        return "IF13.xlsx";
    }


    public static Transaction setBusinessIds( Transaction transaction, String RRSales , String ShipmentRequest,  String salesOrderList,   String cTransactionTypeCodeList,  String cBatch_nbrList) {

        System.out.println(ShipmentRequest);

        return new Transaction();
    }

    public static Transaction setDataTypesInOut(Transaction transaction, String str1, String str2) {
        System.out.println("Data type in" + str1);
        System.out.println("Data type out" + str2);

        return new Transaction();
    }

    private static Connection initializeSFDC(String username, String password, String token, String useSanbBox) throws Exception {
        Statement statement = null;
        Class.forName("cdata.jdbc.salesforce.SalesforceDriver");

//        System.out.println(cdata.jdbc.salesforce.SalesforceDriver.getRTK());
//        java.sql.Connection conn = DriverManager.getConnection("jdbc:salesforce:User=" + username + ";Password=" + password + ";Security Token=" + token + ";UseSandbox=" + useSanbBox+ ";RTK='524652425641535552425641454E545042354639343135360000000000000000000000000000000046545A4535444330000050475442444153544A3934330000'");
//        java.sql.Connection conn = DriverManager.getConnection("jdbc:salesforce:User=" + username + ";Password=" + password + ";Security Token=" + token + ";UseSandbox=" + useSanbBox+ ";RTK='524652435641535552425641454E545042354639343135360000000000000000000000000000000046545A453544433000003559345A30453743415844570000'");
        Connection conn = DriverManager.getConnection("jdbc:salesforce:User=" + username + ";Password=" + password + ";Security Token=" + token + ";UseSandbox=" + useSanbBox+ ";RTK='524652435641535552425641454E545042354639343135360000000000000000000000000000000056564E465545455700003734304B5544584A444233360000'");
        return conn;

    }

    public static String getGlobalData(Transaction transaction, String cRunningthreadCount) {
        return "2";
    }

    public static void setGlobalData(Transaction transaction, String variable, String value) {
    }
    public static void main(String[] args) {
        System.out.printf("Testing...");
    }

    public  static String publishToNetsuite(Long transaction, String netsuiteURL, String payload, String soapAction) throws IPWorksSSLException, InterruptedException {
        return postToNetsuite(transaction, payload, soapAction);
    }


    // function: postToNetsuite with check for number of threads
    public static String postToNetsuite(Long transactionId, String payload, String soapAction) throws IPWorksSSLException, InterruptedException {



        // JT the creds:
        String netsuiteURL = "https://webservices.netsuite.com/services/NetSuitePort_2016_1";

        String restResponse = "";
        while (true) {
            // check to see if there are any threads available to use
            String cNumOfRunningthreadsValue = getNextCount(transactionId);
            JTUtil.logSuccessEvent("Received thread index: " + cNumOfRunningthreadsValue , transactionId, "");

            if (!cNumOfRunningthreadsValue.equals("-1")) {

                Rests rest = new Rests();
                rest.reset();
                rest.config("ReuseSSLSession=false");
                rest.setContentType("text/xml;charset=UTF-8");
                rest.setOtherHeaders("SOAPAction: \"" + soapAction  + "\"");
                rest.setHTTPMethod("POST");

                JTUtil.logSuccessEvent("Netsuite Shipment Status Request", transactionId, payload);
                rest.setPostData(payload.getBytes());

                JTUtil.logSuccessEvent("Assigned thread with index: " + cNumOfRunningthreadsValue, transactionId, "");
                try {
                    rest.post(netsuiteURL);
                } catch (Exception e) {

                    JTUtil.logWarningEvent("Netsuite Shipment Error Response Received current count " + cNumOfRunningthreadsValue, transactionId, e.getMessage() + new String(rest.getTransferredData()));
                    // All done release the thread
                    String cCount = releaseCurrentCount(transactionId);
                    JTUtil.logSuccessEvent("Released current thread with index: " + cNumOfRunningthreadsValue + " to: " + cCount, transactionId, "");
                    rest.reset();
                }

                System.out.println(rest.getStatusLine());
                System.out.println(new String(rest.getTransferredData()));

                String restStatus = rest.getStatusLine();
                restResponse = new String(rest.getTransferredData());

                if (restStatus.equals("HTTP/1.1 200 OK")) {
                    JTUtil.logSuccessEvent("Successfully Posted request to netsuite" , transactionId, rest.getTransferredHeaders() + "\n\n\n" + new String(rest.getTransferredData()));
                    // All done release the thread
                    String cCount = releaseCurrentCount(transactionId);
                    JTUtil.logSuccessEvent("Released current thread with index: " + cNumOfRunningthreadsValue + " to: " + cCount, transactionId, "");
                    // println "Status : " + status
                    restResponse = new String(rest.getTransferredData());
//                    println XmlUtil.serialize(restResponse);
                    rest.reset();
                } else {
                    String cCount = releaseCurrentCount(transactionId);
                    JTUtil.logSuccessEvent("Released current thread with index: " + cNumOfRunningthreadsValue + " to: " + cCount, transactionId, "");
                    rest.reset();
                    JTUtil.logWarningEvent("Error posting data to NetSuite", transactionId, new String(rest.getTransferredData())); ;
                }
                break;
            } else {
                // Sleep for 5 sec and try
                JTUtil.logSuccessEvent("Waiting Received current : " + cNumOfRunningthreadsValue + " waiting ", transactionId, "");
                Thread.sleep(5000);
                continue;
            }
        }

        return restResponse ;

    }

    public static String getNextCount(Long transactionId) {

//    Rests rest = new Rests();
//    rest.reset();
//    rest.setHTTPMethod("GET");
//    try {
//        rest.post("https://apps.justransform.com/jtc/lockNextID?transactionId=" + transactionId);
//    } catch (Exception e ) {
//        ;
//    }
//
//    def reurnCount = new String(rest.getTransferredData());
//    rest.reset();
        return "1";
    }

    public static String releaseCurrentCount(Long transactionId) {

//    Rests rest = new Rests();
//    rest.reset();
//    rest.setHTTPMethod("GET");
//    try {
//        rest.post("https://apps.justransform.com/jtc/releaseID?transactionId=" + transactionId);
//    } catch (Exception e ) {
//        ;
//    }
//
//    def reurnCount = new String(rest.getTransferredData());
//    rest.reset();
        return "0";
    }


    public static Transaction setFileNamesInOut(Transaction transaction, String inFileName, String outFileName) {
        System.out.println("In File Name : " + inFileName + "Out File Name : " + outFileName);
        return new Transaction();
    }

    public static String  ediValidator(String input, Long transactionId) {



        input = "<ediRequest>\n<data>\n<![CDATA[\n"+ input + "]]>\n</data>\n</ediRequest>";
        Rests rest = new Rests();
        try {
            rest.setContentType("application/xml");
            rest.setPostData(input.getBytes());
            rest.post("https://apps.justransform.com/api/xmlRequest");
        } catch (Exception e) {

        }

        return new String(rest.getTransferredData());
    }

    public static void publishToConnection(Transaction transaction, Long targetConnectionId, String payload) {
        System.out.println(payload);
    }

    public static Integer getRandomNumber(Integer pSize) {

        if(pSize<=0) {
            return null;
        }
        Double min_d = Math.pow(10, pSize.doubleValue()-1D);
        Double max_d = (Math.pow(10, (pSize).doubleValue()))-1D;
        int min = min_d.intValue();
        int max = max_d.intValue();
        return (new Random()).nextInt(max-min) + min;
    }

    public static String getValue(Long transactionId, String tableName, String key){
        Map<String, String> lookupTable = new HashMap<>();
        lookupTable.put("key1", "value1");
        return lookupTable.get(key);
    }

    /**
     * Only for personal use
     */
    public static String convertExcelToCSV(FileInputStream fis, String delim) throws Exception {

        com.aspose.cells.License cellsLicense = new com.aspose.cells.License();
        cellsLicense.setLicense(new FileInputStream("/home/vincomind/user/justransform/Aspose2019/Aspose.Total.Java.lic"));

        //// Text save options. You can use any type of separator
        TxtSaveOptions opts = new TxtSaveOptions();
        opts.setSeparatorString(delim);
        opts.setKeepSeparatorsForBlankRow(true);

        Workbook workbook = new Workbook(fis);

        // 0-byte array
        byte[] workbookData = new byte[0];
// Copy each worksheet data in text format inside workbook data array
        for (int idx = 0; idx < workbook.getWorksheets().getCount(); idx++) {
            // Save the active worksheet into text format
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            workbook.getWorksheets().setActiveSheetIndex(idx);

            String sSheetName =  workbook.getWorksheets().get(idx).getName();
            workbook.save(bout, opts);

            // Save the worksheet data into sheet data array
            byte[] sheetData = bout.toByteArray();

            // Combine this worksheet data into workbook data array
            byte[] combinedArray = new byte[workbookData.length + sheetData.length];
            System.arraycopy(workbookData, 0, combinedArray, 0, workbookData.length);
            System.arraycopy(sheetData, 0, combinedArray, workbookData.length, sheetData.length);

            workbookData = combinedArray;
        }
// Save entire workbook data into file
        String outCsv = "/share/outCSV_" + getRandom(9) + getRandom(9) + ".csv";
        FileOutputStream fout = new FileOutputStream(outCsv);
        fout.write(workbookData);
        fout.close();

        String text = new String(Files.readAllBytes(Paths.get(outCsv)));
        return text;
    }

    public static Integer getRandom(Integer pSize) {

        if (pSize <= 0) {
            return null;
        }
        Double min_d = Math.pow(10, pSize.doubleValue() - 1D);
        Double max_d = (Math.pow(10, (pSize).doubleValue())) - 1D;
        int min = min_d.intValue();
        int max = max_d.intValue();
        return (new Random()).nextInt(max - min) + min;
    }
}
