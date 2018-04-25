package org.yaukie.constant;
/**
 * 常量参数
 * @author yaukie
 */
public interface RestConstant {
	/**定义servlet拦截url*/
		String SERVLET_URL = "/rest/*";
		/**定义是否启用调用服务日志监控*/
	    String LOG = "rest.plugin.yaukie.log";
	    String JSONP = "yaukie.plugin.rest.jsonp";
	    /**设定回调函数名称*/
	    String JSONP_FUNCTION = "yaukie.plugin.rest.jsonp.function";
	    String CORS = "yaukie.plugin.rest.cors";
	    /**允许访问的方法限*/
	    String CORS_ORIGIN = "yaukie.plugin.rest.cors.origin";
}
