package org.yaukie.servlet;

import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.StringUtil;
import org.yaukie.annotation.Rest;
import org.yaukie.constant.RestConstant;
import org.yaukie.helper.PublishRestServiceHelper;

/**
 * 发布soap服务工具
 * 容器启动的时候,自动发布相应服务
 * @author yaukie
 */
@WebServlet(urlPatterns=RestConstant.SERVLET_URL,loadOnStartup=1)
public class PublishRestServiceServlet extends CXFNonSpringServlet{
	  private static final long serialVersionUID = 1L;  
	  private static final Logger log = LoggerFactory.getLogger(PublishRestServiceServlet.class);
	  
	  /**
	   * 使用cxf环境发布ws服务,脱离spring,
	   * 简单粗暴
	   */
	  @Override
	protected void loadBus(ServletConfig sc) {
		  super.loadBus(sc);
		  Bus bus = this.getBus();
		  BusFactory.setDefaultBus(bus);
		  /**发布rest服务*/
		  publishRestService();
		  
	}
	  
	  
	  private static void publishRestService(){
		  Set<Class<?>> targetClassSet = ClassHelper.getClassSetByAnnotation(Rest.class);
		  if(CollectionUtil.isNotEmpty(targetClassSet)){
			  for(Class<?>  restClass : targetClassSet ){
				  Rest soap = restClass.getAnnotation(Rest.class);
				  String baseAddress = soap.value();
				  PublishRestServiceHelper.publishRestService(getBaseAddress(restClass,baseAddress), restClass);
			  }
		  }
	  }
	  
	  
	  private static String getBaseAddress(Class<?> cls ,String baseAddress ){
		   String  address = "";
		   
		  if(StringUtil.isEmpty(baseAddress)){
			  //接口名作为服务地址
			  address = cls.getSimpleName();
		  }else {
			  address=baseAddress;
		  }
		  
		  if(!address.startsWith("/")){
			  address = "/"+address;
		  } 
		  address= address.replaceAll("\\/", "/");
		  
		  return address;
	  }
	  
	  
	  
	
}
