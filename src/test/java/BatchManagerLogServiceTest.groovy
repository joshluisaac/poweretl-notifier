import com.powerapps.monitor.model.BmProperties
import com.powerapps.monitor.service.BatchManagerLogService
import spock.lang.Specification

class BatchManagerLogServiceTest extends Specification {

    //dependencies
    def config
    def logService

    def setup(){
        config = new BmProperties()
        config.setBmRootPath("/media/joshua/martian/ptrworkspace/MBSB/MBSB-6.1.370-N20190129/MBSB-Linux/BatchManager/logs/")
        config.setBatchStartRegex("^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (DEBUG) (--START--)")
        config.setBatchDoneRegex("^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (DEBUG) (--DONE--)")
        config.setBatchErrorRegex("^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (ERROR)")
        config.setBmCache("/media/joshua/martian/linked_projects/notifier/out/bmcache.csv")

        logService = new BatchManagerLogService(config)
    }

    def 'Batch Manager log summarization and features extraction test'(){
        given: "a log file"
        def logFileName = "Batch-ctiExtraction-05.Feb.2019-06:54:41.log"

        when: "we ask for log summary"
        def logSummary = logService.summarizeLog(logFileName)

        then: "we should get the relevant features of that log file"
        println logSummary.toString()
    }


    def 'Batch Manager log status test'(){
        given: "a log file"
        def logFileName = "Batch-ctiExtraction-05.Feb.2019-06:54:41.log"

        when: "we ask for log status"
        def logSummary = logService.summarizeLog(logFileName)
        def batchStatus = logSummary.getBatchStatus()

        then: "the status must be equal to 2"
        batchStatus == 2
    }


}
