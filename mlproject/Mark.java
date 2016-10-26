public class Mark{
	static final int x=1;
	static final int o=2;
	static final int blank=0;
	static final int getOpposite(int mark)
	{
		if(mark==blank)
			return mark;
		return mark==Mark.x ? Mark.o : Mark.x;
	}
}