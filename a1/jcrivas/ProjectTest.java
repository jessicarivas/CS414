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
		q3 = new Qualification("Dopey");
		
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

	@Test
	public void testProjectStatus() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);
		Set<Worker> exampleWorkerSet = new HashSet<Worker>();
		exampleWorkerSet.add(w1);
		qualifications.remove(q1);
		Project project = c1.createProject("Paint", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);
		projects.add(project2);

		c1.assign(w1, project);
		c1.assign(w1, project2);
		c1.start(project);
		assertEquals(ProjectStatus.ACTIVE, project.getStatus());
		assertEquals(ProjectStatus.PLANNED, project2.getStatus());
		assertEquals(2, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));

		c1.finish(project);
		projects.remove(project);
		assertEquals(ProjectStatus.FINISHED, project.getStatus());
		assertEquals(1, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));
	}
	
	@Test
	public void testProjectStatusSuspended() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);

		qualifications.remove(q1);
		Project project = c1.createProject("Paint", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project2);

		c1.assign(w1, project);
		c1.assign(w1, project2);
		c1.start(project);

		c1.unassign(w1, project);
		assertEquals(ProjectStatus.SUSPENDED, project.getStatus());
		assertEquals(1, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));
	}
	
	@Test
	public void testProjectStatusSuspendedFalse() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);

		qualifications.remove(q1);
		Project project = c1.createProject("Paint", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);

		c1.assign(w1, project);
		c1.assign(w1, project2);

		c1.unassign(w1, project2);
		assertEquals(ProjectStatus.PLANNED, project2.getStatus());
		assertEquals(1, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));
	}
	
	@Test
	public void testProjectStatusFinished() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);

		qualifications.remove(q1);
		Project project = c1.createProject("Paint", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project2);

		c1.assign(w1, project);
		c1.assign(w1, project2);
		c1.start(project);
		c1.finish(project);

		assertEquals(ProjectStatus.FINISHED, project.getStatus());
		assertEquals(1, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));
	
		c1.assign(w1, project);	
		c1.start(project);
		assertEquals(ProjectStatus.FINISHED, project.getStatus());
		assertEquals(1, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));
	}
	
	@Test
	public void testProjectStatusFinishedFalse() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);

		qualifications.remove(q1);
		Project project = c1.createProject("Paint", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);
		projects.add(project2);

		c1.assign(w1, project);
		c1.assign(w1, project2);
		c1.start(project);
		c1.finish(project2);

		assertEquals(ProjectStatus.ACTIVE, project.getStatus());
		assertEquals(ProjectStatus.PLANNED, project2.getStatus());
		assertEquals(2, w1.getProjects().size());
		assertEquals(true, projects.equals(w1.getProjects()));
	}
	
	@Test
	public void testProjectStatusUnassignMultiple() {
		Company c1 = new Company("Google");
		Set<Qualification> qualifications3 = new HashSet<Qualification>();
		qualifications3.add(q1);
		Worker w5 = new Worker("Snow", qualifications3);
		
		c1.addToAvailableWorkerPool(w1);
		c1.addToAvailableWorkerPool(w5);
		
		Set<Worker> workers = new HashSet<Worker>();
		workers.add(w1);
		workers.add(w5);

		Project project = c1.createProject("Paint", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);

		c1.assign(w1, project);
		c1.start(project);
		assertEquals(ProjectStatus.PLANNED, project.getStatus());

		c1.assign(w5, project);
		c1.start(project);
		
		assertEquals(ProjectStatus.ACTIVE, project.getStatus());
		assertEquals(true, projects.equals(w1.getProjects()));
		assertEquals(true, projects.equals(w5.getProjects()));
		assertEquals(2, c1.getAssignedWorkers().size());
		assertEquals(0, c1.getUnassignedWorkers().size());
		assertEquals(true, workers.equals(c1.getAssignedWorkers()));
	}
	
	@Test
	public void testProjectStatusUnassignAll() {
		Company c1 = new Company("Google");
		
		c1.addToAvailableWorkerPool(w1);

		qualifications.remove(q1);
		Project project = c1.createProject("Paint", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Project project2 = c1.createProject("Fish", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);
		projects.add(project2);

		c1.assign(w1, project);
		c1.assign(w1, project2);
		c1.start(project);
		c1.unassignAll(w1);
		assertEquals(ProjectStatus.SUSPENDED, project.getStatus());
		assertEquals(ProjectStatus.PLANNED, project2.getStatus());
		assertEquals(0, w1.getProjects().size());
		assertEquals(false, projects.equals(w1.getProjects()));
	}
	
	@Test
	public void testProjectStatusToString() {
		Company c1 = new Company("Google");
		Set<Qualification> qualifications3 = new HashSet<Qualification>();
		qualifications3.add(q1);
		Worker w5 = new Worker("Snow", qualifications3);
		
		c1.addToAvailableWorkerPool(w1);
		c1.addToAvailableWorkerPool(w5);
		
		Set<Worker> workers = new HashSet<Worker>();
		workers.add(w1);
		workers.add(w5);

		Project project = c1.createProject("Paint", qualifications, ProjectSize.BIG, ProjectStatus.PLANNED);
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);

		c1.assign(w1, project);
		c1.start(project);
		assertEquals("Paint:1:PLANNED", project.toString());

		c1.assign(w5, project);
		c1.start(project);
		assertEquals("Paint:2:ACTIVE", project.toString());
		
		c1.unassign(w5, project);
		assertEquals("Paint:1:SUSPENDED", project.toString());
	
		c1.assign(w5, project);
		c1.start(project);
		assertEquals("Paint:2:ACTIVE", project.toString());

		c1.finish(project);
		assertEquals("Paint:0:FINISHED", project.toString());
	}
	
}
