
public class Graph_Key extends Graph_Object {

	public Graph_Key(int id,String name) {
		super(id,name);
	
	}
	

	@Override
	public boolean equals(Object obj) {
		 if(obj instanceof Graph_Key){
			 Graph_Key temp= (Graph_Key)obj;
			if(this.id==temp.id &&  this.name.equals(temp.name)){

						return true;
					}
				}

		    return false;
	}

	
	
	
	
}
