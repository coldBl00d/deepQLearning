public class Position{
	int i,j;
	public Position(int i,int j){
		this.i = i;
		this.j = j;
	}
	public Position(){
		i=0;
		j=0;
	}
	public Position(Position p)
	{
		this.i = p.i;
		this.j = p.j;
	}
}