package minecrafterror.analysis;

import minecrafterror.OSType;

public class AnalyzerSegfault extends AnalyzerSimple {
	String msg_other, msg_linux;
	final AnalysisResult other, linux;

	public AnalyzerSegfault(String message_other, String message_linux) {
		super(false);
		msg_other = message_other;
		msg_linux = message_linux;
		other = new AnalysisResult(msg_other, false);
		linux = new AnalysisResult(msg_linux, false);
	}

	public boolean applies(String output) {
		return output.contains("EXCEPTION_ACCESS_VIOLATION")
				|| output.contains("SIGSEGV");
	}

	@Override
	public AnalysisResult getResult(String output) {
		if (OSType.getOS().isLinux()) {
			return linux;
		}
		return other;
	}
}
