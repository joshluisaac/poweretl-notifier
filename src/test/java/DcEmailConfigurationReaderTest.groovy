import com.kollect.etl.util.CryptUtils
import com.powerapps.monitor.dataconnector.DcEmailConfigurationReader
import spock.lang.Specification

class DcEmailConfigurationReaderTest extends Specification {

def "DataConnector email config serialization test"() {

    given: "I use the configuration loader"
    def loader = new DcEmailConfigurationReader()

    when: "I serialize to JSON"
    def result = loader.fakeConfig();

    then: "The hash should be equal"
    CryptUtils.sha256HexHash(result) == TestConstants.DC_EMAIL_CONFIG_HASH
}
def "DataConnector email config reader test"(){

}



}
