package minecrafterror.analysis;

public class AnalysisResult {
	private boolean silly;
	private String message;
	
	public AnalysisResult(String msg, boolean sillymistake)
	{
		this.silly = sillymistake;
		this.message = msg;
	}
	public AnalysisResult(String msg)
	{
		this(msg, false);
	}
	public boolean isSilly()
	{
		return silly;
	}
	public void setSilly(boolean sillymistake)
	{
		this.silly = sillymistake;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String newmsg)
	{
		message = newmsg;
	}
}
