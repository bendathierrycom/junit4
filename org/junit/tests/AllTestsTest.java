package org.junit.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;

public class AllTestsTest {
	
	private static boolean run;
	
	public static class OneTest extends TestCase {
		public void testSomething() {
			run= true;
		}
	}
	
	@RunWith(AllTests.class)
	public static class All {
		static public junit.framework.Test suite() {
			TestSuite suite= new TestSuite();
			suite.addTestSuite(OneTest.class);
			return suite;
		}
	}
	
	@org.junit.Test public void ensureTestIsRun() {
		JUnitCore runner= new JUnitCore();
		run= false; // Have to explicitly set run here because the runner might independently run OneTest above
		runner.run(All.class);
		assertTrue(run);
	}
	
	@org.junit.Test public void correctTestCount() throws Throwable {
		AllTests tests= new AllTests(All.class);
		assertEquals(1, tests.testCount());
	}
	
	public static class JUnit4Test {
		@org.junit.Test public void testSomething() {
			run= true;
		}
	}
	
	@RunWith(AllTests.class)
	public static class AllJUnit4 {
		static public junit.framework.Test suite() {
			TestSuite suite= new TestSuite();
			suite.addTest(new JUnit4TestAdapter(JUnit4Test.class));
			return suite;
		}
	}

	@org.junit.Test public void correctTestCountAdapted() throws Throwable {
		AllTests tests= new AllTests(AllJUnit4.class);
		assertEquals(1, tests.testCount());
	}

	static public junit.framework.Test suite() {
		return new JUnit4TestAdapter(AllTestsTest.class);
	}

}