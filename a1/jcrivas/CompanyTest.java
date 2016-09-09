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
		assertEquals(true, exampleWorkerSet.equals(c1.getUnassignedWorkers()));
	}
	
	@Test
	public void testCompanyGetAvailableWorkersDuplicate() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		c1.addToAvailableWorkerPool(w2);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);

		assertEquals(1, c1.getAvailableWorkers().size());
		assertEquals(1, c1.getUnassignedWorkers().size());
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(true, exampleWorkerSet.equals(c1.getUnassignedWorkers()));
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
	public void testCompanyToStringDefault() {
		Company c1 = new Company("Google");
		assertEquals(true, c1.toString().equals("Google:0:0"));
	}
	
	@Test
	public void testCompanyToString() {
		Company c1 = new Company("Google");
		c1.addToAvailableWorkerPool(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Sand", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		assertEquals(true, c1.toString().equals("Google:1:2"));
	}
	
	@Test
	public void testCompanyToStringDuplicateWorker() {
		Company c1 = new Company("Google");
		c1.addToAvailableWorkerPool(w1);
		c1.addToAvailableWorkerPool(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Sand", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		assertEquals(true, c1.toString().equals("Google:1:2"));
	}
	
	@Test
	public void testCompanyToStringWorkerOtherCompany() {
		Company c1 = new Company("Google");
		Company c2 = new Company("Apple");
		c2.addToAvailableWorkerPool(w1);
		c1.addToAvailableWorkerPool(w1);
		Project project2 = c1.createProject("Sand", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		assertEquals(true, c1.toString().equals("Google:0:1"));
	}
	
	@Test
	public void testCompanyAssignUnqualifiedWorker() {
		Company c1 = new Company("Google");
		Set<Qualification> qualifications3 = new HashSet<Qualification>();
		qualifications3.add(q1);
		Worker w3 = new Worker("Unqualified", qualifications3);
		
		c1.addToAvailableWorkerPool(w3);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w3);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		
		c1.assign(w3, project);

		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(false, exampleWorkerSet.equals(c1.getAssignedWorkers()));
		
		//now make worker qualified
		w3.addQualification(q3);
		c1.assign(w3, project);
		
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(true, exampleWorkerSet.equals(c1.getAssignedWorkers()));
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
		Set<Qualification>qualifications3 = new HashSet<Qualification>();
		qualifications3.add(q1);
		
		Set<Qualification> qualifications4 = new HashSet<Qualification>();
		qualifications4.add(q1);
		Worker w3 = new Worker("Unqualified", qualifications4);
		
		c1.addToAvailableWorkerPool(w1);
		c1.addToAvailableWorkerPool(w3);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		exampleWorkerSet.add(w3);
		Project project = c1.createProject("Paint", qualifications3, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		c1.assign(w1, project);
		c1.start(project);
		
		w3.assignTo(project);

		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(false, exampleWorkerSet.equals(c1.getAssignedWorkers()));
	}
	
	@Test
	public void testCompanyAssignTwoProjects() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		c1.assign(w1, project);
		c1.assign(w1, project2);
		
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(true, exampleWorkerSet.equals(c1.getAssignedWorkers()));
	}
	
	@Test
	public void testCompanyAssignOverloadBig() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		qualifications2.remove(q3);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications2, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project3 = c1.createProject("Sing", qualifications2, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project4 = c1.createProject("Draw", qualifications2, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project5 = c1.createProject("Write", qualifications2, ProjectSize.BIG, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);
		projects.add(project2);
		projects.add(project3);
		projects.add(project4);

		c1.assign(w1, project);
		c1.start(project);
		c1.assign(w1, project2);
		c1.start(project2);
		c1.assign(w1, project3);
		c1.start(project3);
		c1.assign(w1, project4);
		c1.start(project4);
		c1.assign(w1, project5);

		assertEquals(4, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));
	}
	
	@Test
	public void testCompanyAssignOverloadMedium() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		qualifications2.remove(q3);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Project project3 = c1.createProject("Sing", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Project project4 = c1.createProject("Draw", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Project project5 = c1.createProject("Write", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Project project6 = c1.createProject("Sketch", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Project project7 = c1.createProject("Seven", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);
		projects.add(project2);
		projects.add(project3);
		projects.add(project4);
		projects.add(project5);
		projects.add(project6);

		c1.assign(w1, project);
		c1.start(project);
		c1.assign(w1, project2);
		c1.start(project2);
		c1.assign(w1, project3);
		c1.start(project3);
		c1.assign(w1, project4);
		c1.start(project4);
		c1.assign(w1, project5);
		c1.start(project5);
		c1.assign(w1, project6);
		c1.start(project6);
		c1.assign(w1, project7);
		
		assertEquals(6, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));
	}
	
	@Test
	public void testCompanyAssignOverloadSmall() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		qualifications2.remove(q3);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project3 = c1.createProject("Sing", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project4 = c1.createProject("Draw", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project5 = c1.createProject("Write", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project6 = c1.createProject("Sketch", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project7 = c1.createProject("Seven", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project8 = c1.createProject("Eight", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project9 = c1.createProject("Nine", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project10 = c1.createProject("Ten", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project11 = c1.createProject("Eleven", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project12 = c1.createProject("Twelve", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Project project13 = c1.createProject("Thirteen", qualifications2, ProjectSize.SMALL, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);
		projects.add(project2);
		projects.add(project3);
		projects.add(project4);
		projects.add(project5);
		projects.add(project6);
		projects.add(project7);
		projects.add(project8);
		projects.add(project9);
		projects.add(project10);
		projects.add(project11);
		projects.add(project12);

		c1.assign(w1, project);
		c1.start(project);
		c1.assign(w1, project2);
		c1.start(project2);
		c1.assign(w1, project3);
		c1.start(project3);
		c1.assign(w1, project4);
		c1.start(project4);
		c1.assign(w1, project5);
		c1.start(project5);
		c1.assign(w1, project6);
		c1.start(project6);
		c1.assign(w1, project7);
		c1.start(project7);
		c1.assign(w1, project8);
		c1.start(project8);
		c1.assign(w1, project9);
		c1.start(project9);
		c1.assign(w1, project10);
		c1.start(project10);
		c1.assign(w1, project11);
		c1.start(project11);
		c1.assign(w1, project12);
		c1.start(project12);
		c1.assign(w1, project13);
		assertEquals(12, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));
	}
	
	@Test
	public void testCompanyAssignTwoWorkers() {
		Company c1 = new Company("Google");
		Set<Qualification> qualifications3 = new HashSet<Qualification>();
		qualifications3.add(q1);
		qualifications3.add(q3);
		Worker w2 = new Worker("Second", qualifications3);
		
		c1.addToAvailableWorkerPool(w1);
		c1.addToAvailableWorkerPool(w2);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w2);
		exampleWorkerSet.add(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		c1.assign(w1, project);
		c1.assign(w2, project);
		
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(true, exampleWorkerSet.equals(c1.getAssignedWorkers()));
	}
	
	@Test
	public void testCompanyAssignTwoWorkersQualificationOverlap() {
		Company c1 = new Company("Google");
		Set<Qualification> qualifications3 = new HashSet<Qualification>();
		qualifications3.add(q2);
		Worker w2 = new Worker("Second", qualifications3);
		
		c1.addToAvailableWorkerPool(w1);
		c1.addToAvailableWorkerPool(w2);
		exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w2);
		exampleWorkerSet.add(w1);
		Project project = c1.createProject("Paint", qualifications2, ProjectSize.MEDIUM, ProjectStatus.PLANNED);
		c1.assign(w1, project);
		c1.assign(w2, project);
		
		assertEquals(true, exampleWorkerSet.equals(c1.getAvailableWorkers()));
		assertEquals(false, exampleWorkerSet.equals(c1.getAssignedWorkers()));
	}
	

	
}
