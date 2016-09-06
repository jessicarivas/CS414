package cs414.a1.jcrivas;

import java.util.HashSet;
import java.util.Set;

public class Worker
{
	private String _workerName;
	private double _salary;
	private Set<Qualification> _qualifications;
	
	public Worker(String name, Set<Qualification> qs)
	{
		_workerName = name;
		_qualifications = qs;
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
		} else {
			try {
				throw new WorkerException("Did not work.");
			} catch (WorkerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	public Set<Qualification> getQualifications()
	{
		return _qualifications;
	}
	
	public void addQualification(Qualification q)
	{
		_qualifications.add(q);
	}
	
	//TODO
	public boolean equals(Object o)
	{
		if (o instanceof Worker && ((Worker) o).getName() == _workerName)
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
	
	//TODO
	public String toString()
	{
		String companyString = _salary + ":" + "2";
		return companyString;
	}
	
	
	
	
}