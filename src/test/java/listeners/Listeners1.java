package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IClassListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



public class Listeners1 implements ITestListener, IClassListener {
  
	

	private static final Logger log = LogManager.getLogger(Listeners1.class.getName());
	
	 
	 @Override
		public void onBeforeClass(ITestClass testClass) {
			IClassListener.super.onBeforeClass(testClass);
			log.info(testClass.getName() + " class is starting.");		
		}

		@Override
		public void onAfterClass(ITestClass testClass) {
			IClassListener.super.onAfterClass(testClass);
			log.info(testClass.getName() + " class is finished.");
		}
	 
	@Override
	public void onTestStart(ITestResult result) {
		log.info(result.getName() + " test method is starting.");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.info(result.getName() + " test method passed.");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.error(result.getName() + " test method failed.");
		if(result.getThrowable() != null) {
		log.error(result.getThrowable().getMessage());
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.warn(result.getName() + " test method skipped.");
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		
		log.error("Test method " + result.getName() + " failed with time out of "  + result.getMethod().getTimeOut());
	}

	@Override
	public void onStart(ITestContext context) {
		
		log.info(context.getName() + " test starting.");
		
	}


	@Override
	public void onFinish(ITestContext context) {
		log.info(context.getName() + " test finished.");
	}
	
}
