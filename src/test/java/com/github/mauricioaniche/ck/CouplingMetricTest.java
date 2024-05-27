package com.github.mauricioaniche.ck;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CouplingMetricTest extends BaseTest {

	@BeforeAll
	public void setUp() {
		report = run(fixturesDir() + "/cbo");
	}

	@ParameterizedTest
	@CsvSource({
			"cbo.Coupling0, 0, 0, 0",
			"cbo.Coupling1, 6, 0, 6",
			"cbo.Coupling3, 1, 0, 1",
			"cbo.Coupling2, 5, 0, 5",
			"cbo.Coupling4, 3, 0, 3",
			"cbo.MethodParams, 2, 0, 2",
			"cbo.NotResolved, 5, 0, 5"
	})

	//this parameterized test replaced "ignoreJavaTypes", "countDifferentPossibilitiesOfDependencies", "countEvenWhenNotResolved"
	//"countInterfacesAndInheritances", "countClassCreations", "countMethodParameters", "fullOfNonResolvedTypes"
	public void countCoupling(String className, int expectedCboModified, int expectedFanin, int expectedFanout) {
		CKClassResult result = report.get(className);
		Assertions.assertEquals(expectedCboModified, result.getCboModified());
		Assertions.assertEquals(expectedFanin, result.getFanin());
		Assertions.assertEquals(expectedFanout, result.getFanout());
	}

	@Test
	public void methodLevel() {
		CKClassResult a = report.get("cbo.A");
		CKClassResult b = report.get("cbo.B");
		CKClassResult c = report.get("cbo.Coupling5");
		
		Assertions.assertEquals(0, c.getMethod("am1/0").get().getCboModified());
		Assertions.assertEquals(1, c.getMethod("am2/0").get().getCboModified());
		Assertions.assertEquals(2, c.getMethod("am3/0").get().getCboModified());
		Assertions.assertEquals(3, c.getMethod("am4/0").get().getCboModified());
		
		Assertions.assertEquals(2, a.getMethod("method/0").get().getFanin());
		Assertions.assertEquals(2, b.getMethod("method/0").get().getFanin());
		
		Assertions.assertEquals(0, c.getMethod("am1/0").get().getFanout());
		Assertions.assertEquals(1, c.getMethod("am2/0").get().getFanout());
		Assertions.assertEquals(2, c.getMethod("am3/0").get().getFanout());
		Assertions.assertEquals(3, c.getMethod("am4/0").get().getFanout());
	}


	@Test
	public void countFanIn() {
		CKClassResult a = report.get("cbo.Coupling8");
		Assertions.assertEquals(2, a.getFanin());
		
		CKClassResult b = report.get("cbo.Coupling81");
		Assertions.assertEquals(1, b.getFanin());
		
		CKClassResult c = report.get("cbo.Coupling82");
		Assertions.assertEquals(0, c.getFanin());
		
		CKClassResult d = report.get("cbo.Coupling83");
		Assertions.assertEquals(1, d.getFanin());
	}
	
	@Test
	public void countFanOut() {
		CKClassResult a = report.get("cbo.Coupling8");
		Assertions.assertEquals(5, a.getFanout());
		
		CKClassResult b = report.get("cbo.Coupling81");
		Assertions.assertEquals(4, b.getFanout());
		
		CKClassResult c = report.get("cbo.Coupling82");
		Assertions.assertEquals(3, c.getFanout());
		
		CKClassResult d = report.get("cbo.Coupling83");
		Assertions.assertEquals(1, d.getFanout());
	}
	
	@Test
	public void countCBOModified() {
		CKClassResult a = report.get("cbo.Coupling8");
		Assertions.assertEquals(7, a.getCboModified());
		
		CKClassResult b = report.get("cbo.Coupling81");
		Assertions.assertEquals(5, b.getCboModified());
		
		CKClassResult c = report.get("cbo.Coupling82");
		Assertions.assertEquals(3, c.getCboModified());
		
		CKClassResult d = report.get("cbo.Coupling83");
		Assertions.assertEquals(2, d.getCboModified());
	}
	
}
