package cs414.a1.jcrivas;

import java.util.HashSet;
import java.util.Set;

public class Project
{
	private String _projectName;
	private ProjectSize _projectSize;
	private ProjectStatus _projectStatus;
	private Set<Worker> _assignedWorkers;
	private Set<Qualification> _missingQualifications;
	
	public Project(String name, ProjectSize size, ProjectStatus status)
	{
		_projectName = name;
		_projectSize = size;
		_projectStatus = status;
		_missingQualifications = new HashSet<Qualification>();
		_assignedWorkers = new HashSet<Worker>();
	}
	
	public String getName()
	{
		return _projectName;
	}
	
	public ProjectSize getSize()
	{
		return _projectSize;
	}
	
	public ProjectStatus getStatus()
	{
		return _projectStatus;
	}

	public void setStatus(ProjectStatus status)
	{
		_projectStatus = status;
	}
	
	//TODO
	public boolean equals(Object project)
	{
		if (project instanceof Project && ((Project) project).getName() == _projectName)
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
		String projectString = _projectName + ":" + _assignedWorkers.size() + ":" + _projectStatus.toString();
		return projectString;
	}
	
	//TODO
	public Set<Qualification> missingQualifications() 
	{
		Set<Qualification> tempMissingQualifications = _missingQualifications;
		for (Qualification temp: tempMissingQualifications) {
			for (Worker worker: _assignedWorkers) {
				for (Qualification temp2: worker.getQualifications()) {
					if (temp.equals(temp2)) {
						_missingQualifications.remove(temp);
					}
				}
			}
		}
		return _missingQualifications;
	}

	public boolean isHelpful(Worker worker) {
		for (Qualification temp: _missingQualifications) {
			for (Qualification temp2: worker.getQualifications()) {
				if (temp.equals(temp2)) {
					return true;
				}
			}
		}
		return false;
	}

	public void addWorker(Worker worker) {
		_assignedWorkers.add(worker);
		for (Qualification temp: _missingQualifications) {
			if (worker.getQualifications().contains(temp)) {
				_missingQualifications.remove(temp);
			}
		}
	}

	public void addRequiredQualifications(Set<Qualification> qualifications) {
		_missingQualifications.addAll(qualifications);		
	}

	public void removeWorker(Worker worker) {
		for (Worker temp: _assignedWorkers) {
			if (temp.getName().equals(worker)) {
				_assignedWorkers.remove(temp);
			}
		}		
	}
}
