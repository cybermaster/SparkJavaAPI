package spark.examples;

import spark.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import scala.*;
import scala.runtime.*;
import scala.reflect.*;
import spark.examples.JavaUtils;

class JavaTest {
		
  public static class MyFunction1 extends JavaUtils.GenFunction1 {
    public java.lang.Object apply(java.lang.Object o) {
        String s = (String) o;
        return java.lang.Double.parseDouble(s);
    }
    
  }
  public static void main(String[] args) throws Exception {
    
    SparkContext ctx = new SparkContext("local", "JavaTest");
    RDD lines = ctx.textFile("numbers.txt", 1).cache();
    String[] lineArr = (String[])lines.collect();
    
    for (int i = 0; i < lineArr.length; i++) {
      System.out.println(lineArr[i]);
    }
    MyFunction1 f = new MyFunction1();
    
    ClassManifest c = JavaUtils.getManifest(java.lang.Object.class);
    
    System.out.println(c.erasure());
    RDD data = lines.map(f, c).cache();
    
    System.out.println(data.collect());
    
    System.out.println("output");
    java.lang.Object[] output = (java.lang.Object[])data.collect();
    for (int i = 0; i < output.length; i++) {
     System.out.println(output[i]);
    }
  }
}
