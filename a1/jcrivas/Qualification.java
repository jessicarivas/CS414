package cs414.a1.jcrivas;

public class Qualification
{
	private String _description;

	public Qualification(String description)
	{
		_description = description;
	}
	
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
	
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + _description.hashCode();
        return result;
    }
	
	public String toString()
	{
		return _description;
	}
}