package com.message.base.jdbc.base;

import java.util.Random;

import javassist.ClassPool;
import javassist.LoaderClassPath;

public class DynamicBeanUtils
{
  public static final ClassPool classPool;
  public static final Random random;

  static
  {
    ClassPool parent = ClassPool.getDefault();

    ClassPool child = new ClassPool(parent);
    child.appendClassPath(new LoaderClassPath(DynamicRowMapper.class.getClassLoader()));
    //child.appendClassPath(new LoaderClassPath(ControllerProxy.class.getClassLoader()));
    child.appendSystemPath();
    child.childFirstLookup = true;
    classPool = child;

    random = new Random(System.currentTimeMillis());
  }

  public static String createMapperKey(Class _class, String sql)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(sql);
    buf.append("-");
    buf.append(_class.getName());

    return buf.toString();
  }

  public static String underscoreName(String name)
  {
    StringBuffer result = new StringBuffer();
    if ((name != null) && (name.length() > 0)) {
      result.append(name.substring(0, 1).toLowerCase());
      for (int i = 1; i < name.length(); ++i) {
        String s = name.substring(i, i + 1);
        if (s.equals(s.toUpperCase())) {
          result.append("_");
          result.append(s.toLowerCase());
        } else {
          result.append(s);
        }
      }
    }
    return result.toString();
  }

  public static String decodeUnderscoreName(String name) {
    StringBuffer result = new StringBuffer();
    boolean up = false;
    if ((name != null) && (name.length() > 0)) {
      name = name.toLowerCase();
      for (int i = 0; i < name.length(); ++i) {
        String s = name.substring(i, i + 1);
        if (s.equals("_")) {
          up = true;
        }
        else if (up) {
          result.append(s.toUpperCase());
          up = false;
        } else {
          result.append(s);
        }
      }
    }
    return result.toString();
  }

  public static String createMapperKey(Class[] _classes, String sql, boolean forMap)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(sql);
    buf.append((forMap) ? "true" : "fasle");
    buf.append("-");
    for (int i = 0; i < _classes.length; ++i) {
      buf.append(_classes[i].getName());
    }

    return buf.toString();
  }
}