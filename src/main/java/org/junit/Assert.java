package org.junit;

import java.util.Arrays;

public class Assert {
  public static void assertNull(Object o) {
    if (o != null) {
      throw new AssertFailure();
    }
  }
  
  public static void assertNotNull(Object o) {
    if (o == null) {
      throw new AssertFailure();
    }
  }
  
  public static void assertTrue(boolean val) {
    if (! val) {
      throw new AssertFailure();
    }
  }
  
  public static void assertFalse(boolean val) {
    if (val) {
      throw new AssertFailure();
    }
  }
  
  public static void assertEquals(Object o1, Object o2) {
    if ((o1 == null && o2 != null) || ! o1.equals(o2)) {
      throw new AssertFailure();
    }
  }
  
  public static void assertArrayEquals(Object[] a1, Object[] a2) {
    assertTrue(Arrays.deepEquals(a1, a2));
  }
  
  public static void assertEquals(int i1, int i2) {
    assertEquals(i1, i2, 0);
  }
  
  public static void assertEquals(int d1, int d2, int allowedDelta) {
    if (d1 - d2 > allowedDelta || d2 - d1 > allowedDelta) {
      throw new AssertFailure();
    }
  }
  
  public static void assertEquals(long i1, long i2) {
    assertEquals(i1, i2, 0);
  }
  
  public static void assertEquals(long d1, long d2, long allowedDelta) {
    if (d1 - d2 > allowedDelta || d2 - d1 > allowedDelta) {
      throw new AssertFailure();
    }
  }
  
  public static void assertEquals(double d1, double d2, double allowedDelta) {
    if (d1 - d2 > allowedDelta || d2 - d1 > allowedDelta) {
      throw new AssertFailure();
    }
  }
  
  public static void fail() {
    throw new AssertFailure();
  }
  
  public static void fail(String msg) {
    throw new AssertFailure(msg);
  }
}
