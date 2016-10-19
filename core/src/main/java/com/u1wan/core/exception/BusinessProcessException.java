package com.u1wan.core.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class BusinessProcessException extends RuntimeException{
	
	private static final long serialVersionUID = 4744817839683721152L;

	public BusinessProcessException(String message) {
        super(message);
    }

    public BusinessProcessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static String getStackTraceMessage(Throwable e) throws IOException  {
        String exceptionMessage = null;
        StringWriter stringWriter = null;
        PrintWriter printWriter = null;
        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            stringWriter.flush();
            exceptionMessage = "\n" + stringWriter.toString();
        } finally {
            if (null != printWriter)  printWriter.close();
            if (null != stringWriter) stringWriter.close();
        }
        return exceptionMessage;
    }

}
