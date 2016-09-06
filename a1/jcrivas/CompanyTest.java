package cs414.a1.jcrivas;

import static org.junit.Assert.*;

import org.junit.Test;

public class CompanyTest {

	@Test
	public void testCompanyGetName() {
		Company c1 = new Company("Google");
		Company c2 = new Company("Apple");
		assertEquals("Google", c1.getName());
		assertEquals("Apple", c2.getName());
	}

	@Test
	public void testCompanyGetAvailableWorkers() {
		Company c1 = new Company("Google");
		assertEquals("Google", c1.getName());
	}
}
