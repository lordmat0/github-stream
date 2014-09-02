package com.lordmat.githubstream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * REST Web Service
 *
 * @author mat
 */
@Path("githubapi")
@Produces("application/json")
@Consumes("application/json")
public class GitHubAPI {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GitHubAPI
     */
    public GitHubAPI() {
        System.out.println("GitHubAPI created");
    }

    @GET
    public String defaultPath(){
        return "default path";
    }
    
    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    public void putJson(A content) {
        System.out.println(content);
    }
}

@XmlRootElement
class A{
    @XmlElement
    private String content;
    
    public A(){
        
    }
    @Override
    public String toString(){
        return content;
    }
}
