package com.spider.jshtmlparser;


import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;

/**
 * @author MyBeautiful
 * @Emal: zhangyu0182@sina.com
 * @date Mar 7, 2012
 */
@Component
public class RhinoScaper {

	private static final Logger log = LoggerFactory.getLogger(RhinoScaper.class);

	private String url;
	private String jsFile;

	private Context cx;
	private Scriptable scope;

	private static Map<String, String> jsmap;

	public String getUrl() {
		return url;
	}

	public String getJsFile() {
		return jsFile;
	}

	@Resource(name = "jsmap")
	public void setJsmap(Map<String, String> jsmap) {
		this.jsmap = jsmap;
	}

	public void setUrl(String url) {
		this.url = url;
		putObject("url", url);
	}

	public void setJsFile(String jsFile) {
		this.jsFile = jsFile;
	}

	public void init() {
		cx = ContextFactory.getGlobal().enterContext();
		scope = cx.initStandardObjects(null);
		cx.setOptimizationLevel(-1);
		cx.setLanguageVersion(Context.VERSION_1_5);

		for (String f: jsmap.values()) {
			evaluateJs(f);
		}

		try {
			ScriptableObject.defineClass(scope, ExtendUtil.class);
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		ExtendUtil util = (ExtendUtil) cx.newObject(scope, "util");
		scope.put("util", scope, util);
	}

	protected void evaluateJs(String f) {

		final URL fileURL = RhinoScaper.class.getResource(f);
		if(fileURL == null){
			log.error("评估 js ：" + f +" 失败");
		}
		try {
			FileReader in = null;
			in = new FileReader(fileURL.getPath());
			cx.evaluateReader(scope, in, f, 1, null);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void putObject(String name, Object o) {
		scope.put(name, scope, o);
	}

	public void run() {
		evaluateJs(this.jsFile);
	}

	/**
	 * 获取 js 中的元素Scriptable
	 * @param scriptStr
	 * @return
	 */
	public Object getScriptElement(String scriptStr){
		return this.scope.get(scriptStr, this.scope);
	}

	/**
	 * 调用js中的方法
	 * @return
	 */
	public Object callMethod(String methodName, Object[] args){
		return ScriptableObject.callMethod(cx, this.scope, methodName, args);
	}

	/**
	 * 删除js中注册的方法
	 * @param str
	 */
	public void removeActivationName(String str){
		this.cx.removeActivationName(str);
	}

	/**
	 * 退出
	 * @param
	 */
	public void exit(){
		Context.exit();
	}
}
