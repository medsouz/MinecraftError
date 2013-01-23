package minecrafterror.analysis;

public class AnalyzerDefault implements IErrorAnalyzer {
	private static final AnalysisResult defaultResult = new AnalysisResult(
			"Hm, I can't seem to figure it out. If your client failed to load press Paste Error and show that link to #Risucraft on esper.net",
			false);

	@Override
	public boolean applies(String output) {
		return true;
	}

	@Override
	public AnalysisResult getResult(String output) {
		return defaultResult;
	}
}
