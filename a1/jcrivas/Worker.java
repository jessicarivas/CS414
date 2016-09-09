package cs414.a1.jcrivas;

import java.util.HashSet;
import java.util.Set;

public class Worker
{
	private String _workerName;
	private double _salary;
	private boolean _assignedToCompany;
	private Set<Qualification> _qualifications;
	private Set<Project> _projects;
	
	public Worker(String name, Set<Qualification> qualifications)
	{
		if (qualifications.size() > 0) {
			_workerName = name;
			_qualifications = qualifications;
			_salary = 0;
			_projects = new HashSet<Project>();	
			_assignedToCompany = false;
		}
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
	
	public boolean canBeAssignedToCompany() {
		if (_assignedToCompany == false) {
			_assignedToCompany = true;
			return true;
		} else {
			return false;
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
	
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + _workerName.hashCode();
        return result;
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

	private int getProjectLoadNumber(Project project) {
		if (project.getSize() == ProjectSize.SMALL) {
			return 1;
		} else if (project.getSize() == ProjectSize.MEDIUM) {
			return 2;
		} else if (project.getSize() == ProjectSize.BIG) {
			return 3;
		} else {
			return 0;
		}
	}
	public boolean willOverload(Project project) {
		int projectLoad = 0;
		for (Project temp: _projects) {
			if (temp.getStatus() == ProjectStatus.ACTIVE) {
				projectLoad += getProjectLoadNumber(temp);
			}
		} projectLoad += getProjectLoadNumber(project);
		if (projectLoad > 12) {
			return true;
		}
		return false;
	}

	public void assignTo(Project project) {
		_projects.add(project);
	}

	public void unassignFrom(Project project) {
		_projects.remove(project);
	}

	public void unassignAll() {
		_projects.clear();
	}
	
}