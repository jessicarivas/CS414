package cs414.a1.jcrivas;

import static org.junit.Assert.*;

import org.junit.Test;

public class QualificationTest {

	@Test
	public void testToString() {
		Qualification q1 = new Qualification("Coding");
		assertEquals("Coding", q1.toString());
	}

}
