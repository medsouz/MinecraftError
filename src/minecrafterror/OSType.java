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
			2), LION(2), UBUNTU(3), SOLARIS(3);

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
}
