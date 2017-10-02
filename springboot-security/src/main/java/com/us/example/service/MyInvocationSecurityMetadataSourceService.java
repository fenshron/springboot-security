package com.us.example.service;

import com.us.example.dao.PermissionDao;
import com.us.example.domain.Permission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by yangyibo on 17/1/19.
 */
@Service
public class MyInvocationSecurityMetadataSourceService  implements
        FilterInvocationSecurityMetadataSource,InitializingBean {

	protected final Log logger = LogFactory.getLog(getClass());
	
    @Autowired
    private PermissionDao permissionDao;

    private HashMap<String, Collection<ConfigAttribute>> map;

    /**
     * 加载资源，初始化资源变量
     * @return 
     */
    public HashMap<String, Collection<ConfigAttribute>> loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<Permission> permissions = (List<Permission>) permissionDao.findAll();
        for(Permission permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            array.add(cfg);
            map.put(permission.getUrl(), array);
        }

        return map;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
    	final HttpServletRequest request = ((FilterInvocation) object).getRequest();  
        if(map ==null) loadResourceDefine();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
    	Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();  
        for (Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {  
            allAttributes.addAll(entry.getValue());  
        }  
        return allAttributes;  
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		this.map = this.loadResourceDefine();  
        logger.info("资源权限列表"+this.map);
		
	}
}
