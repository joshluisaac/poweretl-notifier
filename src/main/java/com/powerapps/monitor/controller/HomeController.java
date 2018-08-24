package com.powerapps.monitor.controller;

import com.kollect.etl.util.FileUtils;
import com.powerapps.monitor.config.JsonWriter;
import com.powerapps.monitor.model.LogSummary;
import com.powerapps.monitor.service.BatchManagerLogMetrics;
import com.powerapps.monitor.service.BatchManagerLogService;
import com.powerapps.monitor.service.ServiceEngineLogService;
import com.powerapps.monitor.util.JsonToHashMap;
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
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class HomeController {
	private ServiceEngineLogService errorService;
	private BatchManagerLogService bmService;
	private BatchManagerLogMetrics bmMetrics;
	private JsonToHashMap jsonToHashMap;
	@Value("${app.bmJson}")
	private String bmJson;
	private JsonWriter jsonWriter;
	private FileUtils fileUtils = new FileUtils();
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	public HomeController(ServiceEngineLogService errorService, BatchManagerLogService bmService,
			BatchManagerLogMetrics bmMetrics, JsonToHashMap jsonToHashMap,
                          JsonWriter jsonWriter) {
		this.errorService = errorService;
		this.bmService = bmService;
		this.bmMetrics = bmMetrics;
		this.jsonToHashMap = jsonToHashMap;
		this.jsonWriter=jsonWriter;
	}

	private String getRootPath() {
		return jsonToHashMap.toHashMap(
                fileUtils.getFileFromClasspath(bmJson).toString()).get("bmRootPath");
	}

	@RequestMapping("/")
	public String index() {
		return "redirect:/seerrorreport";
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

	@RequestMapping(value = "/seerrorreportpreview", method = RequestMethod.GET)
	@ResponseBody
	public String serviceEngineErrorShowStackTrace(@RequestParam String input) {
		return errorService.getStackTrace(Integer.parseInt(input));
	}

	@RequestMapping(value = "/downloadstacktrace", produces = "text/plain")
	public void downloadStackTrace(@RequestParam int lineNumber, HttpServletResponse response) throws IOException {
		String stackTraceText = errorService.getStackTrace(lineNumber);
		byte[] numberOfBytesCopied = stackTraceText.getBytes(StandardCharsets.UTF_8);

		response.setContentType("text/plain; charset=UTF-8");
		response.setContentLength(numberOfBytesCopied.length);
		response.addHeader("Content-Disposition", "attachment; filename=" + "Stacktrace(" + lineNumber + ")");

		ServletOutputStream outStream = response.getOutputStream();

		outStream.write(numberOfBytesCopied);

		outStream.flush();
		LOG.debug("Number of bytes viewed/written: {} bytes", numberOfBytesCopied.length);
	}

	@RequestMapping(value = "/dcerrorreport", method = RequestMethod.GET)
	public String dataConnectorErrorLogReport(Model model) {
		model.addAttribute("errorReport", errorService.execute());
		return "dash-errorlogreport";
	}

	@RequestMapping(value = "/bmerrorreportJSON", method = RequestMethod.GET)
	@ResponseBody
	public Object batchManagerErrorLogReportJSON(Model model) {
		List<LogSummary> summary = bmService.getAllLogSummary(bmService.getLogFiles(fileUtils.getFileFromClasspath(getRootPath())));
		model.addAttribute("summaryList", summary);
		return summary;
	}

	@RequestMapping(value = "/bmerrorreport", method = RequestMethod.GET)
	public Object batchManagerErrorLogReport(Model model) {
		List<LogSummary> summary = bmService.getAllLogSummary(bmService.getLogFiles(new File(getRootPath())));
		model.addAttribute("summaryList", summary);
		return "batchManager-report";
	}

	@RequestMapping(value = "/downloadbatch", produces = "text/plain")
	public void downloadBatch(@RequestParam String logname, HttpServletResponse response) throws IOException {
		/* resource path */
		Path path = Paths.get(getRootPath() + "/" + logname);

		response.setContentType("text/plain");
		response.setContentLength((int) path.toFile().length());
		response.addHeader("Content-Disposition", "attachment; filename=" + logname);

		ServletOutputStream outStream = response.getOutputStream();
		long numberOfBytesCopied = Files.copy(path, outStream);
		outStream.flush();
		System.out.println(numberOfBytesCopied);
	}

	@RequestMapping(value = "/bmerrorreportpreview", method = RequestMethod.GET)
	@ResponseBody
	public String getBatchDetails(@RequestParam String input) throws IOException {
		return jsonWriter.
                generateJson(bmMetrics.extractFeatures(bmMetrics.getFile(
                        fileUtils.getFileFromClasspath(getRootPath() + "/" + input))));

	}

}
