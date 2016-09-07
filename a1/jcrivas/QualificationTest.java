package cs414.a1.jcrivas;

import static org.junit.Assert.*;

import org.junit.Test;

public class QualificationTest {

	@Test
	public void testToString() {
		Qualification q1 = new Qualification("Coding");
		assertEquals("Coding", q1.toString());
	}
	
	@Test
	public void testEquals() {
		Qualification q1 = new Qualification("Dancing");
		Qualification q2 = new Qualification("Dancing");
		assertEquals(true, q1.equals(q2));
	}

	@Test
	public void testEqualsCase() {
		Qualification q1 = new Qualification("Dancing");
		Qualification q2 = new Qualification("dancing");
		assertEquals(false, q1.equals(q2));
	}

}
