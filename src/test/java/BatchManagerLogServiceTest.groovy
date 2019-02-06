import com.powerapps.monitor.model.BmProperties
import com.powerapps.monitor.service.BatchManagerLogService
import groovy.transform.TypeChecked
import spock.lang.Specification

import java.sql.Time
import java.sql.Timestamp


class BatchManagerLogServiceTest extends Specification {

    //dependencies
    def config
    def logService

    def setup(){
        config = new BmProperties()
        config.setBmRootPath(TestConstants.BM_ROOT_PATH)
        config.setBatchStartRegex("^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (DEBUG) (--START--)")
        config.setBatchDoneRegex("^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (DEBUG) (--DONE--)")
        config.setBatchErrorRegex("^(\\d+ \\d+\\-\\d+-\\d+) (\\d+:\\d+:\\d+)(,)(\\d+) (ERROR)")
        config.setBmCache(TestConstants.BM_CACHE)

        logService = new BatchManagerLogService(config)
    }

    def 'BM log summarization and features extraction test'(){
        given: "a log file"
        def logFileName = "Batch-ctiExtraction-05.Feb.2019-06:54:41.log"

        when: "we ask for log summary"
        def logSummary = logService.summarizeLog(logFileName)

        then: "we should get the relevant features of that log file"
        println logSummary.toString()
    }

    def "BM log metrics batch status test"(){
        given: "a list of BM log lines"
        def lines = TestConstants.bmLogLines()

        when: "we ask for batch status"
        def logSummary = logService.summarizeLog(lines)
        String batchStatus = logSummary.getBatchStatus()

        then: "the status must be equal to"
        batchStatus == "2"
    }


    def "BM log metrics batch start-time test"(){
        given: "a list of BM log lines"
        def lines = TestConstants.bmLogLines()

        when: "we ask for batch start time"
        def logSummary = logService.summarizeLog(lines)
        def startTime = logSummary.getStartTime()

        then: "the start time must be equal to"
        startTime == Timestamp.valueOf("2019-02-05 06:54:46.585")
    }

    def "BM log metrics batch end-time test"(){
        given: "a list of BM log lines"
        def lines = TestConstants.bmLogLines()

        when: "we ask for batch end time"
        def logSummary = logService.summarizeLog(lines)
        def startTime = logSummary.getEndTime()

        then: "the end time must be equal to"
        startTime == Timestamp.valueOf("2019-02-05 06:54:48.2")
    }


    def "BM log metrics running-time test"(){
        given: "a list of BM log lines"
        def lines = TestConstants.bmLogLines()

        when: "we ask for batch running time"
        def logSummary = logService.summarizeLog(lines)
        double runningTime = logSummary.getRunningTime()

        then: "the running time must be equal to"
        runningTime >= 0.0166
    }


    def 'BM log status test'(){
        given: "a log file"
        def logFileName = "Batch-ctiExtraction-05.Feb.2019-06:54:41.log"

        when: "we ask for log status"
        def logSummary = logService.summarizeLog(logFileName)
        def batchStatus = logSummary.getBatchStatus()

        then: "the status must be equal to 2"
        batchStatus == 2
    }


}
