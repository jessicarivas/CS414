package cs414.a1.jcrivas;

public class Qualification
{
	private String _description;

	public Qualification(String description)
	{
		_description = description;
	}
	
	//TODO
	public boolean equals(Object o)
	{
		if (o instanceof Qualification && ((Qualification) o).toString() == _description)
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
		return _description;
	}
}