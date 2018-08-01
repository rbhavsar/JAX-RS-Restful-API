package com.tutorialpoint.exception;

public class FaultInfo {
	private int errorCode; 
    private String errorMessage;
    
    public FaultInfo(int errorCode,String errorMessage) {
    	this.errorCode=errorCode;
    	this.errorMessage=errorMessage;
    }
    
    public FaultInfo() {
    	
    }
    
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	} 
}
