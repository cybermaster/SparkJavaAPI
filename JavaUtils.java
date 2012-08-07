package spark.examples;

import spark.*;
import java.lang.*;
import java.io.*;
import java.util.*;
import scala.*;
import scala.runtime.*;
import scala.reflect.*;
import scala.collection.mutable.*;

class JavaUtils {
  public static abstract class GenFunction1 extends scala.runtime.AbstractFunction1<java.lang.Object, java.lang.Object> implements java.io.Serializable {
    public abstract java.lang.Object apply(java.lang.Object o);
  }
  
  public static ClassManifest getManifest(java.lang.Class c) {
    return ClassManifest$.MODULE$.fromClass(c);
  }
  
  public static abstract class GenFunction2 extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.Object, java.lang.Object> implements java.io.Serializable {
    public abstract java.lang.Object apply(java.lang.Object o1, java.lang.Object o2);
  }
  
  public static WrappedArray wrapArray(Object o) {
    return WrappedArray$.MODULE$.make(o);
  }
}