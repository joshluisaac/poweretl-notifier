import com.kollect.etl.util.CryptUtils

class TestConstants {


    public static final String DC_EMAIL_CONFIG = '''[{"recipients":"1","operator":"2","isEnabled":"true","cronExpression":"* * 90","serverLogPath":"","serverLogDir":"","title":"","context":"","additionalMsg":""},{"recipients":"4","operator":"6","isEnabled":"false","cronExpression":"* 45 *","serverLogPath":"Server.log","serverLogDir":"/home/joshua","title":"9","context":"br","additionalMsg":""}]'''
    public static final String DC_EMAIL_CONFIG_HASH = '661f1237362a12386f5b18d157f0676a8c092bbdcd6ecd3e8725b3d93e12a55c'



    public static final String BM_ROOT_PATH = "/Users/joshua/Dropbox/bmlogs/"
    public static final String BM_CACHE = "/Users/joshua/projects/powerappslogmonitor/out/bmcache.csv"


    static List<String> bmLogLines (){
        def lines = new ArrayList<String>()
        lines.add("1 2019-02-05 06:54:41,609 INFO  HTTP_INIT_CLIENT: Starting HTTP interface connecting to server http://localhost:8765/")
        lines.add("2 LOGIN SUCCESSFUL")
        lines.add("3 2019-02-05 06:54:46,585 DEBUG --START--")
        lines.add("4 2019-02-05 06:54:46,587 INFO  Starting - PROCESS - ctiExtraction from /media/joshua/martian/ptrworkspace/MBSB/MBSB-6.1.370-N20190129/MBSB-Linux/BatchManager/bin/ctiExtraction.batch")
        lines.add("5 2019-02-05 06:54:46,590 INFO  Execute - PROCESS - ctiExtraction")
        lines.add("6 2019-02-05 06:54:48,200 INFO  Completed - PROCESS - ctiExtraction from /media/joshua/martian/ptrworkspace/MBSB/MBSB-6.1.370-N20190129/MBSB-Linux/BatchManager/bin/ctiExtraction.batch")
        lines.add("7 2019-02-05 06:54:48,200 INFO  Finished - PROCESS - ctiExtraction")
        lines.add("8 2019-02-05 06:54:48,200 DEBUG --DONE--")
        return lines
    }

}
