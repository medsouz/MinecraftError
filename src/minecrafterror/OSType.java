/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minecrafterror;

/**
 * 
 * @author kane
 */
public enum OSType {
	WINDOWS(1), MAC(2), LINUX(3), WINDOWS_XP(1), WINDOWS_VISTA(1), WINDOWS_7(1), OS_X(
			2), LION(2), UBUNTU(3), SOLARIS(3), UNKNOWN(0);

	// The category is so that we can do generic comparisons
	int category;

	private OSType(int i) {
		category = i;
	}

	public boolean equals(OSType other) {
		return category == other.category;
	}

	public boolean isWindows() {
		return category == 1;
	}

	public boolean isMac() {
		return category == 2;
	}

	public boolean isLinux() {
		return category == 3;
	}
	
	public static OSType getOS() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.startsWith("win"))
			return WINDOWS;
		else if (os.startsWith("mac"))
			return MAC;
		else if (os.contains("nix") || os.contains("nux"))
			return LINUX;
		else if (os.contains("solaris"))
			return SOLARIS;
		return UNKNOWN;
	}
}
