package spark.examples;

import spark.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import scala.*;
import scala.runtime.*;
import scala.reflect.*;
import scala.collection.immutable.StringOps;
import scala.Tuple2;
import spark.examples.JavaUtils;

class JavaWordCount {

  public static class SplitFunction1 extends JavaUtils.GenFunction1 {
    public java.lang.Object apply(java.lang.Object o) {
        String s = (String) o;
        StringOps op = new StringOps(s);
        return JavaUtils.wrapArray(op.split(' '));
    }
  }
  
  public static class MyFunction1 extends JavaUtils.GenFunction1 {
    public java.lang.Object apply(java.lang.Object o) {
      return new Tuple2((String)o, 1);
    }
  }
	
  public static class MyFunction2 extends JavaUtils.GenFunction2 {
    public java.lang.Object apply(java.lang.Object o1, java.lang.Object o2) {
        java.lang.Integer i1 = (java.lang.Integer) o1;
        java.lang.Integer i2 = (java.lang.Integer) o2;
        return i1 + i2;
    }
  }
  public static void main(String[] args) throws Exception {
    SparkContext ctx = new SparkContext("local", "JavaWordCount");
    RDD lines = ctx.textFile("numbers.txt", 1).cache();
    String[] lineArr = (String[])lines.collect();

    for (int i = 0; i < lineArr.length; i++) {
      System.out.println(lineArr[i]);
    }

    SplitFunction1 sFunc = new SplitFunction1();
    
    MyFunction1 f1 = new MyFunction1();

    MyFunction2 f = new MyFunction2();

    ClassManifest c = JavaUtils.getManifest(java.lang.Object.class);

    System.out.println(c.erasure());
    RDD words = lines.flatMap(sFunc, c);
    
    RDD splits = words.map(f1, c);
    
    PairRDDFunctions applier = new PairRDDFunctions(splits, c, JavaUtils.getManifest(java.lang.Integer.class));
    
    RDD counts = applier.reduceByKey(f);
    
    System.out.println("output");
    java.lang.Object[] output = (java.lang.Object[])counts.collect();
    for (int i = 0; i < output.length; i++) {
      System.out.print(((Tuple2)output[i])._1 + ": ");
      System.out.println(((Tuple2)output[i])._2);
    }
  }
}
