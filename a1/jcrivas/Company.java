package cs414.a1.jcrivas;

import java.util.Set;

public class Company
{
	private String _companyName;
	private Set<Worker> _availableWorkers;
	private Set<Worker> _unassignedWorkers;
	
	public Company(String name)
	{
		_companyName = name;
	}
	
	public String getName()
	{
		return _companyName;
	}
	
	public Set<Worker> getAvailableWorkers()
	{
		return _availableWorkers;
	}
	
	public Set<Worker> getUnassignedWorkers()
	{
		return _unassignedWorkers;
	}
	
	//TODO
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
		String companyString = _companyName + ":" + _availableWorkers + ":" + "2";
		return companyString;
	}
	
	public void addToAvailableWorkerPool(Worker worker) 
	{
		if (!_availableWorkers.contains(worker)) {
			_availableWorkers.add(worker);
		}	
	}
	
	
	
}