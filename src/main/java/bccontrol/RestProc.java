package bccontrol;

public class RestProc{
	private final long id;
	private final String content;

	public RestProc(long id, String content){
		this.id = id;
		this.content = content;
	}

	public long getId(){
		return id;
	}

	public String getContent() {
		return content;
	}
}
