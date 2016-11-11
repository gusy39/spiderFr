package com.spider.jshtmlparser;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;


public class ExtendUtil extends ScriptableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return "util";
	}
	
	public void jsFunction_log(Scriptable host)
	{
		if(host instanceof NativeObject)
		{
			Object result=ScriptableObject.callMethod(host, "attr", new Object[]{"href"});
			System.out.println(result);
		}
		
		System.out.println(host);
	}

}
