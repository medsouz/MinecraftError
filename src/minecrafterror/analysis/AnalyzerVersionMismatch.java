package minecrafterror.analysis;

import java.util.ArrayList;

public class AnalyzerVersionMismatch extends AnalyzerSimple {

	public static final String defmessage = "You installed mods with a minecraft version different than the one you're using.";
	public ArrayList<String> reasons;

	public AnalyzerVersionMismatch() {
		super(true);
		reasons = new ArrayList<String>();
		cached = new AnalysisResult(defmessage, true);
	}

	/**
	 * @return this, for chaining
	 */
	public AnalyzerVersionMismatch addTrigger(String trigger) {
		reasons.add(trigger);
		return this;
	}

	@Override
	public boolean applies(String output) {
		for (String search : reasons) {
			if (output.contains(search)) {
				return true;
			}
		}
		return false;
	}
}
