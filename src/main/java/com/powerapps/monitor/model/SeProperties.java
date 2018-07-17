package com.powerapps.monitor.model;

public class SeProperties {

	private String seRootPath, seExceptionRegex, seErrorLog;

	
	public SeProperties(String seRootPath, String seExceptionRegex, String seErrorLog) {
		
		this.seRootPath = seRootPath;
		this.seExceptionRegex = seExceptionRegex;
		this.seErrorLog = seErrorLog;
	}

	public String getSeRootPath() {
		return seRootPath;
	}

	public void setSeRootPath(String seRootPath) {
		this.seRootPath = seRootPath;
	}

	public String getSeExceptionRegex() {
		return seExceptionRegex;
	}

	public void setSeExceptionRegex(String seExceptionRegex) {
		this.seExceptionRegex = seExceptionRegex;
	}

	public String getSeErrorLog() {
		return seErrorLog;
	}

	public void setSeErrorLog(String seErrorLog) {
		this.seErrorLog = seErrorLog;
	}

	@Override
	public String toString() {
		return "SeProperties [seRootPath=" + seRootPath + ", seExceptionRegex=" + seExceptionRegex + ", seErrorLog="
				+ seErrorLog + "]";
	}
	
	
	
}
