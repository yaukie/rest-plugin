package org.yaukie.helper;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.core.ReflectUtils;

import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.ClientProxyImpl;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpInInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPostStreamInterceptor;
import org.apache.cxf.jaxrs.provider.jsonp.JsonpPreStreamInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.reflect.ReflectionUtil;
import org.yaukie.constant.RestConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * 通用soap服务发布工具
 * 主要用来发布soap服务
 * @author yaukie
 */
public class PublishRestServiceHelper {
	
	private static final Logger log = LoggerFactory.getLogger(PublishRestServiceHelper.class);
	   //添加provider  
    private static final List<Object> providerList = new ArrayList<Object>();  
	private static final List<Interceptor< ? extends Message>> inInterceptorList = new ArrayList<Interceptor <? extends Message>>();
	private static final List<Interceptor< ? extends Message>> outInterceptorList = new ArrayList<Interceptor <? extends Message>>();
	
	static {
	    providerList.add( new JacksonJsonProvider());
		/**开启rest服务调用日志*/
		if(RestConfig.isLog()){
			LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
			inInterceptorList.add(loggingInInterceptor);
			LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
			outInterceptorList.add(loggingOutInterceptor);
		}
		
	      // 添加 JSONP Interceptor
        if (RestConfig.isJsonp()) {
            JsonpInInterceptor jsonpInInterceptor = new JsonpInInterceptor();
            jsonpInInterceptor.setCallbackParam(RestConfig.getJsonpFunction());
            inInterceptorList.add(jsonpInInterceptor);
            JsonpPreStreamInterceptor jsonpPreStreamInterceptor = new JsonpPreStreamInterceptor();
            outInterceptorList.add(jsonpPreStreamInterceptor);
            JsonpPostStreamInterceptor jsonpPostStreamInterceptor = new JsonpPostStreamInterceptor();
            outInterceptorList.add(jsonpPostStreamInterceptor);
        }
        // 添加 CORS Provider
        if (RestConfig.isCors()) {
            CrossOriginResourceSharingFilter corsProvider = new CrossOriginResourceSharingFilter();
            corsProvider.setAllowOrigins(RestConfig.getCorsOriginList());
            providerList.add(corsProvider);
        }
		
	}
	
	public static void publishRestService(String baseAddress ,Class<?> resourceClass){
        // 添加ResourceClass  
        List<Class<?>> resourceClassList = new ArrayList<Class<?>>();  
        resourceClassList.add(resourceClass); 
  
        // 添加ResourceProvider  
        List<ResourceProvider> resourceProviderList = new ArrayList<ResourceProvider>();  
        resourceProviderList.add(new SingletonResourceProvider( BeanHelper.getBean(resourceClass)));  
          
        //发布REST任务  
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();  
        factory.setAddress(baseAddress);  
        factory.setResourceClasses(resourceClassList);  
        factory.setInInterceptors(inInterceptorList);
        factory.setOutInterceptors(outInterceptorList);
        factory.setResourceProviders(resourceProviderList);  
        factory.setProviders(providerList);  
        factory.create();  
		
	}
	
	public static <T> T publishRestClient(String baseAddress,Class< ? extends T> resourceClass){
		return  JAXRSClientFactory.create(baseAddress, resourceClass);
	}
	
	
}
