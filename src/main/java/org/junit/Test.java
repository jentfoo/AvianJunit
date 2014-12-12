package org.junit;

public @interface Test {
  Class<? extends Throwable> expected() default FakeFailure.class;
}
