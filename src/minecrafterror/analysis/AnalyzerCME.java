package minecrafterror.analysis;

public class AnalyzerCME extends AnalyzerSimple {

	public AnalyzerCME() {
		super("java.util.ConcurrentModificationException", false);
	}
	// ModLoader.onTick"
	// "One of the mods is acting up. Tell the author to never remove their mod from the tick list except by returning false."
}
