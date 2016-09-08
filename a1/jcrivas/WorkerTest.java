package cs414.a1.jcrivas;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WorkerTest {
	
	private Set<Qualification> qualifications;

	@Before
	public void initialize() {
		Qualification q1 = new Qualification("Cooking");
		Qualification q2 = new Qualification("Happy");
		qualifications = new HashSet<Qualification>();
		qualifications.add(q1);
		qualifications.add(q2);	
	}

	@Test
	public void testGetName() {		
		Worker w1 = new Worker("Jessica", qualifications);
		assertEquals("Jessica", w1.getName());
	}
	
	@Test(expected=NullPointerException.class)
	public void testNoQualifications() {	
		qualifications.clear();
		Worker w1 = new Worker("Jessica", qualifications);
		fail(w1.toString());
	}
	
	@Test
	public void testGetSalary() {
		Worker w1 = new Worker("Jessica", qualifications);
		w1.setSalary(1000000);
		assertEquals(1000000, w1.getSalary(), 0.001);
	}

	@Test
	public void testSetSalary() {
		Worker w1 = new Worker("Jessica", qualifications);
		w1.setSalary(1000000);
		assertEquals(1000000, w1.getSalary(), 0.001);
	}

	@Test
	public void testSetSalaryNegative() {
		Worker w1 = new Worker("Jessica", qualifications);
		w1.setSalary(-1000);
		assertEquals(0, w1.getSalary(), 0.001);

	}
	
	@Test
	public void testSetQualifications() {
		Worker w1 = new Worker("Jessica", qualifications);
		Qualification q3 = new Qualification("Painting");
		w1.addQualification(q3);
		qualifications.add(q3);
		assertEquals(true, qualifications.equals(w1.getQualifications()));
	}
	
	@Test
	public void testEquals() {
		Worker w1 = new Worker("Jessica", qualifications);
		Worker w2 = new Worker("Jessica", qualifications);
		assertEquals(true, w1.equals(w2));
	}

	@Test
	public void testToStringDefaults() {
		Worker w1 = new Worker("Jessica", qualifications);
		assertEquals("Jessica:0:2:0.0", w1.toString());
	}
	
	@Test
	public void testToString() {
		Qualification q3 = new Qualification("Angry");
		qualifications.add(q3);
		Worker w1 = new Worker("Dopey", qualifications);
		w1.setSalary(1000);
		assertEquals("Dopey:0:3:1000.0", w1.toString());
	}
	
	@After
	public void after() {
		qualifications = null;
	}
}
