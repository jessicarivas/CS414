package cs414.a1.jcrivas;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Project
{
	private String _projectName;
	private String _assignedToCompany;
	private ProjectSize _projectSize;
	private ProjectStatus _projectStatus;
	private Set<Worker> _assignedWorkers;
	private Set<Qualification> _missingQualifications;
	
	public Project(String name, ProjectSize size, ProjectStatus status) {
		_projectName = name;
		_projectSize = size;
		_projectStatus = status;
		_missingQualifications = new HashSet<Qualification>();
		_assignedWorkers = new HashSet<Worker>();
		_assignedToCompany = "";
	}
	
	public String getName() {
		return _projectName;
	}
	
	public ProjectSize getSize() {
		return _projectSize;
	}
	
	public ProjectStatus getStatus() {
		return _projectStatus;
	}
	
	public void setCompany(String companyName) {
		_assignedToCompany = companyName;
	}
	
	public String getCompany() {
		return _assignedToCompany;
	}

	public void setStatus(ProjectStatus status) {
		_projectStatus = status;
	}
	
	public boolean equals(Object project) {
		if (project instanceof Project && ((Project) project).getName() == _projectName)
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
        result = 31 * result + _projectName.hashCode();
        return result;
    }
		
	public String toString() {
		String projectString = _projectName + ":" + _assignedWorkers.size() + ":" + _projectStatus.toString();
		return projectString;
	}
	
	//TODO
	public Set<Qualification> missingQualifications() {
		Set<Qualification> remainingQualifications = new HashSet<Qualification>();
		remainingQualifications.addAll(_missingQualifications);
		for (Iterator<Qualification> i = remainingQualifications.iterator(); i.hasNext();) {
		    Qualification missingQualification = i.next();
			for (Iterator<Worker> j = _assignedWorkers.iterator(); j.hasNext();) {
				Worker worker = j.next();
				for (Iterator<Qualification> k = worker.getQualifications().iterator(); k.hasNext();) {
					Qualification workerQualification = k.next();
					if (missingQualification.equals(workerQualification)) {
						i.remove();
					}
				}
		    }
		}
		return remainingQualifications;
	}

	public boolean isHelpful(Worker worker) {
		for (Qualification temp: missingQualifications()) {
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
		for (Iterator<Qualification> i = missingQualifications().iterator(); i.hasNext();) {
			Qualification missingQualification = i.next();
			for (Iterator<Qualification> j = worker.getQualifications().iterator(); j.hasNext();) {
				Qualification workerQualification = j.next();
				if (missingQualification.equals(workerQualification)) {
					i.remove();
				}
			}
		}
	}

	public void addRequiredQualifications(Set<Qualification> qualifications) {
		_missingQualifications.addAll(qualifications);		
	}
	
	public Set<Worker> getAssignedWorkers() {
		return _assignedWorkers;		
	}

	public void removeWorker(Worker worker) {
		_assignedWorkers.remove(worker);	
	}
}
