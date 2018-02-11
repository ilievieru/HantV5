package test.java.crypto.utils;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(RSATest.class);
      
      startTests(result);

      System.out.println(result.wasSuccessful());
   }
   
   public static void startTests(Result result)
   {
	   for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      } 
   }
}  	