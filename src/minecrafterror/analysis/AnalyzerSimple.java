package minecrafterror.analysis;

public class AnalyzerSimple implements IErrorAnalyzer {
	protected String search = null;
	protected String message = null;
	protected boolean silly;
	protected AnalysisResult cached = null;

	public AnalyzerSimple(String search, String message, boolean silly) {
		this.search = search;
		this.message = message;
		this.silly = silly;
	}

	protected AnalyzerSimple(String search, boolean silly) {
		this.search = search;
		this.silly = silly;
	}

	protected AnalyzerSimple(boolean silly) {
		this.silly = silly;
	}

	protected AnalyzerSimple() {
		silly = false;
	}

	@Override
	public boolean applies(String output) {
		if (search == null)
			throw new IllegalArgumentException(
					"AnalyzerSimple has no search string");
		return output.contains(search);
	}

	@Override
	public AnalysisResult getResult(String output) {
		if (cached == null) {
			if (message == null) {
				throw new IllegalArgumentException(
						"AnalyzerSimple has no message string");
			}
			cached = new AnalysisResult(message, silly);
		}
		return cached;
	}

}
