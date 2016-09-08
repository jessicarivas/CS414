package cs414.a1.jcrivas;

import java.util.HashSet;
import java.util.Set;

public class Company
{
	private String _companyName;
	private Set<Worker> _availableWorkers;
	private Set<Worker> _unassignedWorkers;
	private Set<Worker> _assignedWorkers;
	private Set<Project> _projects;
	
	public Company(String name)
	{
		_companyName = name;
		_availableWorkers = new HashSet<Worker>();
		_unassignedWorkers = new HashSet<Worker>();
		_assignedWorkers = new HashSet<Worker>();
		_projects = new HashSet<Project>();
	}
	
	public String getName()
	{
		return _companyName;
	}
	
	public Set<Worker> getAvailableWorkers()
	{
		return _availableWorkers;
	}
	
	public Set<Worker> getAssignedWorkers()
	{
		return _assignedWorkers;
	}
	
	public Set<Worker> getUnassignedWorkers()
	{
		return _unassignedWorkers;
	}
	
	public boolean equals(Object project)
	{
		if (project instanceof Company && ((Company) project).getName() == _companyName)
		{
			return true;
		} 
		else {
			return false;			
		}
	}
	
//	public int hashCode() {
//		int result = 0;
//		result = (int) (value / 11);
//		return result;
//	}
	
	public String toString()
	{
		String companyString = _companyName + ":" + _availableWorkers.size() + ":" + _projects.size();
		return companyString;
	}
	
	private boolean containsWorker(Set<Worker> workerSet, Worker worker) {
		boolean containsWorker = false;
		for (Worker temp: workerSet) {
			if ((temp.equals(worker))) {
				containsWorker = true;
				break;
			}
		}
		return containsWorker;
	}
	
	private boolean containsProject(Set<Project> projectSet, Project project) {
		boolean containsProject = false;
		for (Project temp: projectSet) {
			if ((temp.equals(project))) {
				containsProject = true;
				break;
			}
		}
		return containsProject;
	}
	
	public void addToAvailableWorkerPool(Worker worker) 
	{
		if (!containsWorker(_availableWorkers, worker) && worker.canBeAssignedToCompany()) {
			_availableWorkers.add(worker);
			_unassignedWorkers.add(worker);
		}
	}
	
	public void assign(Worker worker, Project project)
	{
		if (containsWorker(_availableWorkers, worker) && project.getCompany() == _companyName) {
			if (!(containsProject(worker.getProjects(), project)) && !worker.willOverload(project) && project.isHelpful(worker)) {
				if ((project.getStatus() != ProjectStatus.ACTIVE) && (project.getStatus() != ProjectStatus.FINISHED)) {
					worker.assignTo(project);
					project.addWorker(worker);
					if (!containsWorker(_assignedWorkers, worker)) {
						_assignedWorkers.add(worker);
						_unassignedWorkers.remove(worker);
					}
				}
			}
		}
	}
	
	private void editProjectStatus(Project project)
	{
		if (project.missingQualifications().size() > 0 && project.getStatus() == ProjectStatus.ACTIVE) {
			project.setStatus(ProjectStatus.SUSPENDED);
		}
	}
	
	public void unassign(Worker worker, Project project)
	{
		if (containsProject(worker.getProjects(), project)) {
			worker.unassignFrom(project);
			project.removeWorker(worker);
			if (worker.getProjects().size() == 0) {
				_unassignedWorkers.add(worker);
				_assignedWorkers.remove(worker);
			} 
			editProjectStatus(project);
		}
	}
	
	public void unassignAll(Worker worker)
	{
		for (Project temp: worker.getProjects()) {
			temp.removeWorker(worker);
			editProjectStatus(temp);
		}
		worker.unassignAll();
		_unassignedWorkers.add(worker);
		_assignedWorkers.remove(worker);
	}
	
	public void start(Project project) {
		if (project.getCompany() == _companyName) {
			if (project.getStatus() == ProjectStatus.PLANNED || project.getStatus() == ProjectStatus.SUSPENDED) {
				if (project.missingQualifications().size() == 0) {
					project.setStatus(ProjectStatus.ACTIVE);
				}
			}
		}
	}
	
	//TODO
	public void finish(Project project) {
		if (project.getCompany() == _companyName) {
			if (project.getStatus() == ProjectStatus.ACTIVE) {
				project.setStatus(ProjectStatus.FINISHED);
				if (project.missingQualifications().size() == 0) {
				}
			}
		}
	}
	
	public Project createProject(String name, Set<Qualification> qualifications, ProjectSize size, ProjectStatus status)
	{
		if (qualifications.size() > 0) {
			Project project = new Project(name, size, ProjectStatus.PLANNED);
			project.addRequiredQualifications(qualifications);
			project.setCompany(_companyName);
			_projects.add(project);
			return project;
		}
		return null;
	}
	
	
	
}