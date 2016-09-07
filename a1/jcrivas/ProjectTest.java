package cs414.a1.jcrivas;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProjectTest {

	@Test
	public void testGetName() {		
		Project p1 = new Project("Oklahoma", ProjectSize.BIG, ProjectStatus.ACTIVE);
		assertEquals("Oklahoma", p1.getName());
	}

}
