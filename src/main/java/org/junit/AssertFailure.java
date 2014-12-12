package org.junit;

public class AssertFailure extends RuntimeException {
  private static final long serialVersionUID = -1298364044601144538L;
  
  public AssertFailure() {
    super();
  }
  
  public AssertFailure(String msg) {
    super(msg);
  }
  
  public AssertFailure(Throwable t) {
    super(t);
  }
}