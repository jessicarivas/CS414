package cs414.a1.jcrivas;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CompanyTest {

	private Qualification q1;
	private Qualification q2;
	private Qualification q3;
	private Qualification q4;
	private Set<Qualification> qualifications;
	private Set<Qualification> qualifications2;
	private Worker w1;
	private Worker w2;
	private Project p1;
	private Set<Worker> exampleWorkerSet;
	
	@Before
	public void initialize() {
		q1 = new Qualification("Cooking");
		q2 = new Qualification("Happy");
		qualifications = new HashSet<Qualification>();
		qualifications.add(q1);
		qualifications.add(q2);	
		
		q3 = new Qualification("Painting");
		q4 = new Qualification("Happy");
		qualifications2 = new HashSet<Qualification>();
		qualifications2.add(q3);
		qualifications2.add(q4);
		
		w1 = new Worker("Person", qualifications);
		w2 = new Worker("Person", qualifications);
		exampleWorkerSet = new HashSet<Worker>();

		p1 = new Project("Painting", ProjectSize.MEDIUM, ProjectStatus.PLANNED);

	}
	
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
		
		Worker w1 = new Worker("Person", qualifications);
		exampleWorkerSet = new HashSet<Worker>();
		
		c1.addToAvailableWorkerPool(w1);
		exampleWorkerSet.add(w1);
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
	}
	
	@Test
	public void testCompanyGetAvailableWorkersDuplicate() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		c1.addToAvailableWorkerPool(w2);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		exampleWorkerSet.add(w2);
		assertEquals(false, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		
		exampleWorkerSet.remove(w2);
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
	}
	
	@Test
	public void testCompanyAssign() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		c1.assign(w1, project);
		
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(true, exampleWorkerSet.equals(c1.getAssignedWorkers()));

	}
	
	@Test
	public void testCompanyAssignUnqualifiedWorker() {
		Company c1 = new Company("Google");
		Set<Qualification> qualifications3 = new HashSet<Qualification>();
		qualifications3.add(q1);
		Worker w2 = new Worker("Unqualified", qualifications3);
		
		c1.addToAvailableWorkerPool(w2);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w2);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		c1.assign(w2, project);
		
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(false, exampleWorkerSet.equals(c1.getAssignedWorkers()));
	}
	
	@Test
	public void testCompanyAssignNotAvailableWorker() {
		Company c1 = new Company("Google");
		
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		c1.assign(w1, project);
		
		assertEquals(false, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(false, exampleWorkerSet.equals(c1.getAssignedWorkers()));

	}
	
	@Test
	public void testCompanyAssignActiveProject() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.ACTIVE);
		c1.assign(w1, project);
		
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(false, exampleWorkerSet.equals(c1.getAssignedWorkers()));
	}
	
	@Test
	public void testCompanyAssignFinishedProject() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.FINISHED);
		c1.assign(w1, project);
		
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(false, exampleWorkerSet.equals(c1.getAssignedWorkers()));
	}
	
	@Test
	public void testCompanyAssignTwoProjects() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.FINISHED);
		Project project2 = c1.createProject("Fish", qualifications2, ProjectSize.MEDIUM, ProjectStatus.FINISHED);
		c1.assign(w1, project);
		c1.assign(w1, project2);
		
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		System.out.println(c1.getAssignedWorkers());
		assertEquals(true, exampleWorkerSet.equals(c1.getAssignedWorkers()));
	}
}
