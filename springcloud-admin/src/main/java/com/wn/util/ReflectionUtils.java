package com.wn.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

public class ReflectionUtils {
	  
	  public static Object getFieldValue(Object object, String fieldName)
	  {
	    Field field = getDeclaredField(object, fieldName);
	    if (field == null) {
	      throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
	    }
	    makeAccessible(field);
	    
	    Object result = null;
	    try
	    {
	      result = field.get(object);
	    }
	    catch (IllegalAccessException e)
	    {
	    }
	    return result;
	  }
	  
	  public static void setFieldValue(Object object, String fieldName, Object value)
	  {
	    Field field = getDeclaredField(object, fieldName);
	    if (field == null) {
	      throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
	    }
	    makeAccessible(field);
	    try
	    {
	      field.set(object, value);
	    }
	    catch (IllegalAccessException e)
	    {
	    	e.printStackTrace();
	    }
	  }
	  
	  
	  protected static Field getDeclaredField(Object object, String fieldName)
	  {
	    Assert.notNull(object, "object不能为空");
	    Assert.hasText(fieldName, "fieldName");
	    for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
	          .getSuperclass()) {
	      try
	      {
	        return superClass.getDeclaredField(fieldName);
	      }
	      catch (NoSuchFieldException localNoSuchFieldException) {}
	    }
	    return null;
	  }
	  
	  protected static void makeAccessible(Field field)
	  {
	    if ((!Modifier.isPublic(field.getModifiers())) || (!Modifier.isPublic(field.getDeclaringClass().getModifiers()))) {
	      field.setAccessible(true);
	    }
	  }
	  
	  public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes)
	  {
	    Assert.notNull(object, "object不能为空");
	    for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
	          .getSuperclass()) {
	      try
	      {
	        return superClass.getDeclaredMethod(methodName, parameterTypes);
	      }
	      catch (NoSuchMethodException localNoSuchMethodException) {}
	    }
	    return null;
	  }
	  
	  public static <T> Class<T> getSuperClassGenricType(Class clazz)
	  {
	    return getSuperClassGenricType(clazz, 0);
	  }
	  
	  public static Class getSuperClassGenricType(Class clazz, int index)
	  {
	    Type genType = clazz.getGenericSuperclass();
	    if (!(genType instanceof ParameterizedType))
	    {
	      return Object.class;
	    }
	    Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
	    if ((index >= params.length) || (index < 0))
	    {
	      return Object.class;
	    }
	    if (!(params[index] instanceof Class))
	    {
	      return Object.class;
	    }
	    return (Class)params[index];
	  }
	  
	  
	  
	  
	  public static <T> T create(Class<T> clazz)
	  {
	    T t = null;
	    try
	    {
	      t = clazz.newInstance();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    return t;
	  }

}
