package com.powerapps.monitor.controller;

import com.powerapps.monitor.model.LogSummary;
import com.powerapps.monitor.service.BatchManagerLogMetrics;
import com.powerapps.monitor.service.BatchManagerLogService;
import com.powerapps.monitor.service.ServiceEngineLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class HomeController {

    // Dependency Injection region
    private ServiceEngineLogService errorService;
    private BatchManagerLogService bmService;
    private BatchManagerLogMetrics bmMetrics;
    private String bmRootPath;
    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    public HomeController(@Value("${app.bmRootPath}") String bmRootPath, ServiceEngineLogService errorService,
                          BatchManagerLogService bmService, BatchManagerLogMetrics bmMetrics) {
        this.errorService = errorService;
        this.bmService = bmService;
        this.bmRootPath = bmRootPath;
        this.bmMetrics = bmMetrics;
    }

    @RequestMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboardView() {

        return "dashboard";
    }

    @RequestMapping(value = "/seerrorreport", method = RequestMethod.GET)
    public String serviceEngineErrorLogReport(Model model) {
        model.addAttribute("errorReport", errorService.execute());
        return "se-errorlogreport";
    }

    @RequestMapping(value = "/seerrorreportJSON", method = RequestMethod.GET)
    @ResponseBody
    public Object serviceEngineErrorLogReportJSON() {
        return errorService.execute();
    }

    @RequestMapping(value = "/seshowstacktrace", method = RequestMethod.GET)
    public String serviceEngineErrorShowStackTrace(@RequestParam int lineNumber, Model model) {
        model.addAttribute("stackTrace", errorService.getStackTrace(lineNumber));
        return "fragments/template_preview_se_error_report";
    }

    @RequestMapping(value = "/downloadstacktrace", produces = "text/plain")
    public String downloadStackTrace(@RequestParam int lineNumber, HttpServletResponse response)
            throws IOException {
        String stackTraceText = errorService.getStackTrace(lineNumber);

        // create file and write content into the file
        FileOutputStream out = new FileOutputStream("./stack_traces/stackTrace.txt");
        out.write(stackTraceText.getBytes());
        out.flush();
        out.close();

        // resource path
        Path path = Paths.get("./stack_traces/stackTrace.txt");

        response.setContentType("text/plain");
        response.setContentLength(stackTraceText.length());
        response.addHeader("Content-Disposition", "attachment; filename=" + "Stacktrace");

        ServletOutputStream outStream = response.getOutputStream();
        long numberOfBytesCopied = Files.copy(path, outStream);
        outStream.flush();
        LOG.debug("Number of bytes viewed/written: {} bytes", numberOfBytesCopied);
        // response.setHeader(name, value);
        return "se-errorlogreport";
    }

    @RequestMapping(value = "/dcerrorreport", method = RequestMethod.GET)
    public String dataConnectorErrorLogReport(Model model) {
        model.addAttribute("errorReport", errorService.execute());
        return "dash-errorlogreport";
    }

    // return json response
    @RequestMapping(value = "/bmerrorreportJSON", method = RequestMethod.GET)
    @ResponseBody
    public Object batchManagerErrorLogReportJSON(Model model) {
        List<LogSummary> summary = bmService.getAllLogSummary(bmService.getLogFiles(new File(bmRootPath)));
        model.addAttribute("summaryList", summary);
        return summary;
    }

    @RequestMapping(value = "/bmerrorreport", method = RequestMethod.GET)
    public Object batchManagerErrorLogReport(Model model) {
        List<LogSummary> summary = bmService.getAllLogSummary(bmService.getLogFiles(new File(bmRootPath)));
        model.addAttribute("summaryList", summary);
        return "batchManager-report";
    }

    @RequestMapping(value = "/downloadbatch", produces = "text/plain")
    public String downloadBatch(@RequestParam String logname, HttpServletResponse response) throws IOException {
        // resource path
        Path path = Paths.get(bmRootPath + "/" + logname);

        response.setContentType("text/plain");
        response.setContentLength(logname.length());
        response.addHeader("Content-Disposition", "attachment; filename=" + logname);

        ServletOutputStream outStream = response.getOutputStream();
        long numberOfBytesCopied = Files.copy(path, outStream);
        outStream.flush();
        System.out.println(numberOfBytesCopied);
        // response.setHeader(name, value);
        return "batchManager-report";
    }

    @RequestMapping("/batchdetailsJSON")
    @ResponseBody
    public Object getBatchDetails() throws IOException {
        File f = new File(
                "C:\\Users\\Chelsea\\workspace\\powerappslogmonitor\\batches_notifications\\BatchManager\\eCollect\\Batch-01.Jun.2018-07_48_02.log");

        return bmMetrics.extractFeatures(bmMetrics.getFile(f));

    }

    @RequestMapping(value = "/batchdetails", method = RequestMethod.GET)
    public Object getBatchDetails(Model model, @RequestParam String logname) throws IOException {
        model.addAttribute("batchDetails",
                bmMetrics.extractFeatures(bmMetrics.getFile(new File(bmRootPath + "/" + logname))));
        return "fragments/template-bm-logmetric-report";

    }

}
