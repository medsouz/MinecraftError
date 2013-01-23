package minecrafterror.analysis;

import java.util.HashMap;

/*
 * NoClassDefinitionFoundError analyzer
 */
public class AnalyzerNCDFE extends AnalyzerSimple {
	public java.util.Map<String, String> classes = new HashMap<String, String>();
	public static final String default1 = "There was a missing Java classfile named \"";
	public static final String default2 = "\", but I don't know what that is.";

	public AnalyzerNCDFE() {
		super("java.lang.NoClassDefFoundError", true);
	}

	public AnalyzerNCDFE addClass(String className, String response) {
		classes.put(className, response);
		return this;
	}

	@Override
	public AnalysisResult getResult(String output) {
		String missing = getMissingClass(output);
		for (String key : classes.keySet()) {
			if (key.contains(missing))
				return new AnalysisResult(classes.get(missing), true);
		}
		// else
		if (output.contains("wrong name:"))
		{
			return new AnalysisResult("MCP was not set up properly; you are mixing obfuscated and unobfuscated classes.");
		}
		return new AnalysisResult(new StringBuilder(default1).append(missing)
				.append(default2).toString(), false);
	}

	/**
	 * Uses substring magick to get the name of the class this NCDFE is about.
	 */
	public String getMissingClass(String output) {
		int pos = output.indexOf("java.lang.NoClassDefFoundError") + 32;
		int pos2 = output.indexOf("\n", pos);
		return output.substring(pos, pos2);
	}
}
