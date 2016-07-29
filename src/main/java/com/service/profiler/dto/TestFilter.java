package com.service.profiler.dto;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;


@Provider
public class TestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
             System.out.println("Method - " + requestContext.getMethod());
             System.out.println("URI path - " + requestContext.getUriInfo().getPath());
             System.out.println("URI absolute path - " + requestContext.getUriInfo().getAbsolutePath());
             System.out.println("URI matched uri - " + requestContext.getUriInfo().getMatchedURIs() );
             System.out.println("URI base uri - " + requestContext.getUriInfo().getBaseUri());
             System.out.println("Headers - " + requestContext.getHeaders());
             System.out.println(requestContext.hasEntity());
	}

}
