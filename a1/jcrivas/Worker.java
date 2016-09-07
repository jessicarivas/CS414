package cs414.a1.jcrivas;

import java.util.HashSet;
import java.util.Set;

public class Worker
{
	private String _workerName;
	private double _salary;
	private Set<Qualification> _qualifications;
	private Set<Project> _projects;
	private String _companyName;
	
	public Worker(String name, Set<Qualification> qs)
	{
		_workerName = name;
		_qualifications = qs;
		_salary = 0;
		_projects = new HashSet<Project>();
	}
	
	public String getName()
	{
		return _workerName;
	}
	
	public double getSalary()
	{
		return _salary;
	}
	
	public void setSalary(double salary)
	{
		if (salary > 0) {
			_salary = salary;
		}
	}
	
	public Set<Qualification> getQualifications()
	{
		return _qualifications;
	}
	
	public void addQualification(Qualification qualification)
	{
		_qualifications.add(qualification);
	}
	
	//TODO
	public boolean equals(Object object)
	{
		if (object instanceof Worker && ((Worker) object).getName() == _workerName)
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

	public void addToCompany(String company)
	{
		_companyName = company;
	}
	
	//TODO
	public String toString()
	{
		String workerString = _workerName + ":" + _projects.size() + ":" + _qualifications.size() + ":" + _salary;
		return workerString;
	}

	public Set<Project> getProjects() {
		return _projects;
	}

	public boolean willOverload(Project project) {
		Set<Project> allProjects = _projects;
		int projectLoad = 0;
		allProjects.add(project);
		for (Project temp: allProjects) {
			if (temp.getStatus() == ProjectStatus.ACTIVE) {
				if (temp.getSize() == ProjectSize.SMALL) {
					projectLoad += 1;
				} else if (temp.getSize() == ProjectSize.MEDIUM) {
					projectLoad += 2;
				} else if (temp.getSize() == ProjectSize.BIG) {
					projectLoad += 3;
				}
			}
		}
		if (projectLoad > 12) {
			return true;
		}
		return false;
	}

	public void assignTo(Project project) {
		_projects.add(project);
	}

	public void unassignFrom(Project project) {
		for (Project temp: _projects) {
			if (temp.getName().equals(project)) {
				_projects.remove(temp);
			}
		}
	}

	public void unassignAll() {
		_projects.clear();
	}
	
}