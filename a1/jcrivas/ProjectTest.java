package cs414.a1.jcrivas;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ProjectTest {

	private Qualification q1;
	private Qualification q2;
	private Qualification q3;
	private Worker w1;
	private Worker w2;
	private Set<Qualification> qualifications;
	
	@Before
	public void initialize() {	
		q1 = new Qualification("Angry");
		q2 = new Qualification("Bashful");
		q2 = new Qualification("Dopey");
		
		qualifications = new HashSet<Qualification>();
		qualifications.add(q1);
		qualifications.add(q2);
		
		Set<Qualification> qualifications2 = new HashSet<Qualification>();
		qualifications2.add(q2);
		qualifications2.add(q3);
		w1 = new Worker("Huntsman", qualifications2);
		
		Set<Qualification> qualifications3 = new HashSet<Qualification>();
		qualifications3.add(q3);
		w2 = new Worker("Queen", qualifications3);
	}
	@Test
	public void testGetName() {		
		Project p1 = new Project("Oklahoma", ProjectSize.BIG, ProjectStatus.ACTIVE);
		assertEquals("Oklahoma", p1.getName());
	}
	
	@Test
	public void testGetSize() {		
		Project p1 = new Project("Oklahoma", ProjectSize.BIG, ProjectStatus.ACTIVE);
		assertEquals(ProjectSize.BIG, p1.getSize());
	}

	@Test
	public void testGetStatus() {		
		Project p1 = new Project("Oklahoma", ProjectSize.BIG, ProjectStatus.ACTIVE);
		assertEquals(ProjectStatus.ACTIVE, p1.getStatus());
	}
	
	@Test
	public void testCreateProject() {
		Company c1 = new Company("Google");	
		Project p1 = c1.createProject("Ohio", qualifications, ProjectSize.BIG, ProjectStatus.ACTIVE);
		
		assertEquals(true, p1.getSize().equals(ProjectSize.BIG));
		assertEquals(true, p1.getStatus().equals(ProjectStatus.PLANNED));
		assertEquals(true, p1.getName().equals("Ohio"));
		assertEquals(true, p1.missingQualifications().equals(qualifications));
	}
	
	@Test
	public void testCreateProjectNoQualifications() {
		Company c1 = new Company("Google");	
		qualifications.clear();
		Project p1 = c1.createProject("Ohio", qualifications, ProjectSize.BIG, ProjectStatus.ACTIVE);
		
		assertEquals(null, p1);
	}
	
	@Test
	public void testWorkerIsHelpful() {
		Company c1 = new Company("Google");
		Project p1 = c1.createProject("Ohio", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		
		assertEquals(true, p1.isHelpful(w1));		
	}

	@Test
	public void testWorkerIsHelpfulFalse() {
		Company c1 = new Company("Google");
		Project p1 = c1.createProject("Ohio", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		
		assertEquals(false, p1.isHelpful(w2));		
	}

}
