package com.service.profiler.dto;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

@Priority(value = 1000)
public class ServerReaderInterceptor1 implements ReaderInterceptor{
	
	@Override
    public Object aroundReadFrom(ReaderInterceptorContext interceptorContext)
            throws IOException, WebApplicationException {
        System.out.println("ServerReaderInterceptor1 invoked");
        System.out.println("Type " + interceptorContext.getType().toString());
        System.out.println("Class " + interceptorContext.getClass().getName().toString());
        System.out.println("Headers "+ interceptorContext.getHeaders());
        System.out.println("InputStream " +  interceptorContext.getInputStream());
        System.out.println("MediaType "+ interceptorContext.getMediaType());
        System.out.println("Property "+ interceptorContext.getPropertyNames().toString());
        InputStream inputStream = interceptorContext.getInputStream();
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String test = getStringFromInputStream(inputStream);
        System.out.println("****** "+ test);
        String requestContent = new String(bytes);
        requestContent = requestContent
                + "\nRequest changed in ServerReaderInterceptor1.";
        interceptorContext.setInputStream(new ByteArrayInputStream(
                requestContent.getBytes()));
        return interceptorContext.proceed();
    }
	
	// convert InputStream to String
		private static String getStringFromInputStream(InputStream is) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return sb.toString();

		}

}
