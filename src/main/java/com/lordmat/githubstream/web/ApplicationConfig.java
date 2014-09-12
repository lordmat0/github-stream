package com.lordmat.githubstream.web;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author mat
 */
@javax.ws.rs.ApplicationPath("res")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.lordmat.githubstream.web.GitHubAPIRest.class);
    }
}
