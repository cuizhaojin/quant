package com.hexun.quant.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * ModelAndView封装类
 */
public class ModelAndViewUtil {
	/**
	 * 返回JSP
	 * */
	public static ModelAndView Jsp(String jspPath){
		return new ModelAndView(jspPath);
	}
	
	/**
	 * result = 1  为正确结果
	 * result = 0  为错误结果
	 * result = 2  存在级联等情况
	 * */
	public static ModelAndView Json_ok(){
		ModelAndView mav = new ModelAndView( new MappingJacksonJsonView());
		mav.addObject("result", "1");
		return mav;
	}


	public static ModelAndView Json_ok(String message){
		ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
		mav.addObject("result", "1");
		mav.addObject("mess", message);
		return mav ;
	}
	
	public static ModelAndView Json_ok(String key, Object obj){
		ModelAndView mav = new ModelAndView( new MappingJacksonJsonView());
		mav.addObject("result", "1");
		mav.addObject(key, obj);
		return mav ;
	}

	public static ModelAndView Json_ok(List<?> list){
		ModelAndView mav = new ModelAndView( new MappingJacksonJsonView());
		mav.addObject("result", "1");
		mav.addObject("list", list);
		return mav ;
	}
	public static ModelAndView Json_ok(Map<String ,Object> map ){
		ModelAndView mav = new ModelAndView(new MappingJacksonJsonView());
		mav.addObject("result", "1");
		Set<String> keySet = map.keySet();
		for( String key : keySet){
			mav.addObject(key, map.get(key));
		}
		return mav ;
	}
	
	public static ModelAndView Json_ok( GridDataModel<?> gdm ){
		ModelAndView mav = new ModelAndView( new MappingJacksonJsonView());
		mav.addObject("result", "1");
		mav.addObject("total", gdm.getTotal());
		mav.addObject("rows", gdm.getRows());
		return mav ;
	}
	
	public static ModelAndView Json_error(){
		ModelAndView mav = new ModelAndView( new MappingJacksonJsonView());
		mav.addObject("result", "0");
		return mav ;
	}
	
	public static ModelAndView Json_error(String message ){
		ModelAndView mav = new ModelAndView( new MappingJacksonJsonView());
		mav.addObject("result", "0");
		mav.addObject("mess", message);
		return mav ;
	}
	
	public static ModelAndView Json_error(Map<String ,Object> map ){
		ModelAndView mav = new ModelAndView( new MappingJacksonJsonView());
		mav.addObject("result", "2");
		Set<String> keySet = map.keySet();
		for( String key : keySet){
			mav.addObject(key, map.get(key));
		}
		return mav ;
	}
	
	public static ModelAndView Json_error(String key, Object obj){
		ModelAndView mav = new ModelAndView( new MappingJacksonJsonView());
		mav.addObject("result", "0");
		mav.addObject(key, obj);
		return mav ;
	}
	
	public static JSONPObject JsonP_Ok(String functionName,Map<Object, Object> obj){
		obj.put("result","1");
		return new JSONPObject(functionName, obj);
	}
	
	public static JSONPObject JsonP_error(String functionName,String message){
		Map<Object, Object> obj = new HashMap<Object,Object>();
		obj.put("result","0");
		obj.put("mess", message);
		return new JSONPObject(functionName, obj);
	}

}
