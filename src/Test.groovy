import groovy.io.FileType
import groovy.transform.Field
import ipworksssl.Rests

Map<String, FileInputStream> inputFilesMap = new HashMap<String, FileInputStream>()
@Field Map<String, FileOutputStream> outputFilesMap = new HashMap<String, FileOutputStream>()
def dir = new File("C:/Users/Asus/IdeaProjects/Arvind_1test/Arvind/Input/")
dir.eachFileRecurse(FileType.FILES) { f ->
    if (!f.hidden) {
        println f.absolutePath
        inputFilesMap.put(f.name, new FileInputStream(f))
    }
}

@Field final Long transactionId = 1L

for (Map.Entry<String, InputStream> inputfile : inputFilesMap.entrySet()) {
    FileInputStream fis = inputfile.getValue();

    String UTF8 = "utf8";
    int BUFFER_SIZE = 8192;

    BufferedReader br = new BufferedReader(new InputStreamReader(fis, UTF8), BUFFER_SIZE);
    def str = ""
    def s =""
    while ((str = br.readLine()) != null) {
        s <<= str
    }

    String out = s.toString()
    println(out)
    JTUtil.logSuccessEvent("hello", transactionId, "")

// Rests rest1 = new Rests();
// rest1.setTimeout(120)
// rest1.setHTTPMethod("GET");
// rest1.setUser("jtu")
// rest1.setPassword("jtu")
// rest1.post("C:/Users/Asus/Downloads/nsoftware_lic")
// println(new String(rest1.getTransferredData()))

    InputStream is = new ByteArrayInputStream(out.getBytes("UTF-8"));
    outputFilesMap.put("outputfile" + JTUtil.getRandom(9), is);
}