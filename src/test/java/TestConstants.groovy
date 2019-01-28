import com.kollect.etl.util.CryptUtils

class TestConstants {


    public static final String DC_EMAIL_CONFIG = '''[{"recipients":"1","operator":"2","isEnabled":"true","cronExpression":"* * 90","serverLogPath":"","serverLogDir":"","title":"","context":"","additionalMsg":""},{"recipients":"4","operator":"6","isEnabled":"false","cronExpression":"* 45 *","serverLogPath":"Server.log","serverLogDir":"/home/joshua","title":"9","context":"br","additionalMsg":""}]'''
    public static final String DC_EMAIL_CONFIG_HASH = '661f1237362a12386f5b18d157f0676a8c092bbdcd6ecd3e8725b3d93e12a55c'


}
