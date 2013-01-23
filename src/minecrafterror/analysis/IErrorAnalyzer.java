package minecrafterror.analysis;

public interface IErrorAnalyzer {
	/**
	 * Return true if this Error Analyzer applies to the given Minecraft output.
	 * 
	 * @param output
	 *            The output produced by Minecraft
	 * @return boolean - true if this error analyzer has a result
	 */
	public boolean applies(String output);

	/**
	 * The AnalysisResult to be shown.
	 * 
	 * @param output
	 *            The output produced by Minecraft
	 * @return @link{AnalysisResult} - the result to show to the player
	 */
	public AnalysisResult getResult(String output);
}
