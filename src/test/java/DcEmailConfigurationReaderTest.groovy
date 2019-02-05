import com.kollect.etl.util.CryptUtils
import com.powerapps.monitor.dataconnector.DcEmailConfigurationReader
import spock.lang.Specification

class DcEmailConfigurationReaderTest extends Specification {

    def "DataConnector email config serialization test"() {

        given: "the configuration loader"
        def loader = new DcEmailConfigurationReader()

        when: "we serialize to JSON"
        def result = loader.fakeConfig();

        then: "The hash should be equal"
        CryptUtils.sha256HexHash(result) == TestConstants.DC_EMAIL_CONFIG_HASH
    }
}
