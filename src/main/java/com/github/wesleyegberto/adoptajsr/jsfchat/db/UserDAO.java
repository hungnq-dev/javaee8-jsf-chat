package com.github.wesleyegberto.adoptajsr.jsfchat.db;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.faces.annotation.ApplicationMap;
import javax.faces.annotation.RequestParameterMap;
import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import com.github.wesleyegberto.adoptajsr.jsfchat.model.User;

@RequestScoped
public class UserDAO {

    // @Inject // WELD-001408: Unsatisfied dependencies
    private ExternalContext context;
    
    // @Inject // WELD-001408: Unsatisfied dependencies
    @ApplicationMap
    private Map<String, Object> applicationMap;
    
    // NPE at ManagedPropertyProducer.java:110
    // @Inject @ManagedProperty("#{applicationScope}")
    private Map<String, Object> applicationMapEL;
    
    // @Inject // WELD-001408: Unsatisfied dependencies
    @RequestParameterMap
    private Map<String, String> requestMap;

    // @Inject // WELD-001408: Unsatisfied dependencies
    private Flash flash;
    
    @Inject @ApplicationMap
    private Instance<Map> rawMap;

    @Inject @ApplicationMap
    private Instance<Map<String, Object>> map;
    
    public UserDAO() {
    }
    
    @PostConstruct
    public void postConstruct() {
	System.out.println("[UserDAO] Map is resolvable: " + rawMap.isResolvable());
	System.out.println("[UserDAO] Map is ambiguous: " + rawMap.isAmbiguous());
	System.out.println("[UserDAO] Map<String, Object> is resolvable: " + map.isResolvable());
	System.out.println("[UserDAO] Map<String, Object> is ambiguous: " + map.isAmbiguous());
    }
    
    public boolean isNicknameUsed(String nickname) {
	return applicationMap.containsKey(nickname);
    }
    
    public void regiterUser(User user) {
	if(applicationMap.putIfAbsent(user.getNickname(), user) != user) {
	    throw new IllegalArgumentException("Nickname is already in use!");
	}
    }
}