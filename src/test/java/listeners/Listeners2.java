package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class Listeners2 extends TestListenerAdapter {

	private static final Logger log = LogManager.getLogger(Listeners2.class.getName());
	
	@Override
	public void onStart(ITestContext testContext) {
		super.onStart(testContext);
		log.info(testContext.getName() + " test is starting.");
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		super.onTestStart(result);
		log.info(result.getName() + " test method is staring.");
	}

	@Override
	public void onFinish(ITestContext context) {
		super.onFinish(context);
		log.info(context.getName() + " test is finished.");
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		log.info(tr.getName() + " test method passed.");
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		log.info(tr.getName() + " test method failed.");
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		log.info(tr.getName() + " test method skipped.");
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult tr) {
		super.onTestFailedWithTimeout(tr);
		log.info(tr.getName() + " test method failed due to timeout.");
	}

	

	@Override
	public void onConfigurationFailure(ITestResult itr) {
		super.onConfigurationFailure(itr);
		log.error(itr.getName() + " failed due to configuration failure.");
	}

}
