package org.junit.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.AssertFailure;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FakeFailure;
import org.junit.Test;

public class JUnitCore {
  private static int passCount = 0;
  private static int failCount = 0;
  
  public static void main(String[] args) {
    for (String testClass : args) {
      try {
        runTest(testClass);
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }
    
    System.out.println("\nDONE!!");
    System.out.println("Tests passed: " + passCount + ", Tests failed: " + failCount);
  }

  private static void runTest(String testClassStr) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
    Class<?> testClass = Class.forName(testClassStr);
    Method beforeClassMethod = null;
    Method afterClassMethod = null;
    Method beforeTestMethod = null;
    Method afterTestMethod = null;
    List<Method> testMethods = new LinkedList<Method>();
    
    for (Method m : testClass.getMethods()) {
      Annotation a = m.getAnnotation(BeforeClass.class);
      if (a != null) {
        beforeClassMethod = m;
      }
      
      a = m.getAnnotation(AfterClass.class);
      if (a != null) {
        afterClassMethod = m;
      }
      
      a = m.getAnnotation(Before.class);
      if (a != null) {
        beforeTestMethod = m;
      }
      
      a = m.getAnnotation(After.class);
      if (a != null) {
        afterTestMethod = m;
      }
      
      a = m.getAnnotation(Test.class);
      if (a != null) {
        testMethods.add(m);
      }
    }
    
    if (testMethods.isEmpty()) {
      System.err.println("No tests in class: " + testClassStr);
      return;
    }
    
    if (beforeClassMethod != null) {
      beforeClassMethod.invoke(null);
    }
    Object instance = testClass.newInstance();
    for (Method test : testMethods) {
      Class<? extends Throwable> expected = null;
      Test tAnnotation = test.getAnnotation(Test.class);
      if (! tAnnotation.expected().equals(FakeFailure.class)) {
        expected = tAnnotation.expected();
      }
      
      if (beforeTestMethod != null) {
        beforeTestMethod.invoke(instance);
      }
      try {
        test.invoke(instance);
        if (expected != null) {
          fail(test, new AssertFailure("Expected thrown exception of type: " + expected));
        } else {
          pass();
        }
      } catch (InvocationTargetException ite) {
        Throwable t = ite.getCause();
        if (expected != null && expected.isInstance(t)) {
          // expected failure, yay
          pass();
        } else {
          fail(test, t);
        }
      } finally {
        if (afterTestMethod != null) {
          afterTestMethod.invoke(instance);
        }
      }
    }
    if (afterClassMethod != null) {
      afterClassMethod.invoke(null);
    }
  }
  
  private static void fail(Method test, Throwable t) {
    System.err.println("\nTest failure: " + test.getDeclaringClass() + "." + test.getName() + "()");
    t.printStackTrace();
    
    failCount++;
  }
  
  private static void pass() {
    passCount++;
  }
}
