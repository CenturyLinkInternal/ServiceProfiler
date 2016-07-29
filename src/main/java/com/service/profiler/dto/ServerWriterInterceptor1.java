package com.service.profiler.dto;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

@Priority(value = 2000)
public class ServerWriterInterceptor1 implements WriterInterceptor{
	
	   @Override
	    public void aroundWriteTo(WriterInterceptorContext interceptorContext)
	            throws IOException, WebApplicationException {
	        System.out.println("ServerWriterInterceptor1 invoked.");
	        OutputStream outputStream = interceptorContext.getOutputStream();
	        System.out.println("Class " + interceptorContext.getClass().getName().toString());
	        /*String responseContent = "\nResponse changed in ServerWriterInterceptor2.";
	        outputStream.write(responseContent.getBytes());*/
	        System.out.println("Response " + outputStream.toString());
	        interceptorContext.setOutputStream(outputStream);
	 
	        interceptorContext.proceed();
	    }

}
