package minecrafterror;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ExecOutput implements Runnable {

	private javax.swing.JTextArea jTextArea1;
	private Main mcopy;

	public ExecOutput(javax.swing.JTextArea Output, Main m) {
		jTextArea1 = Output;
		mcopy = m;
	}

	@Override
	public void run() {
		String output = "";
		// Get launcher jar
		File Launcher = new File(mcopy.getMinecraftPath() + "minecrafterr.jar");
		jTextArea1
				.setText("Checking for Minecraft launcher (minecrafterr.jar) in "
						+ Launcher.getAbsolutePath() + "\n");
		if (!Launcher.exists()) {
			jTextArea1.setText(jTextArea1.getText()
					+ "Error: Could not find launcher!\n");
			jTextArea1.setText(jTextArea1.getText()
					+ "Downloading from Minecraft.net...\n");
			try {
				BufferedInputStream in = new BufferedInputStream(
						new URL(
								"https://s3.amazonaws.com/MinecraftDownload/launcher/minecraft.jar")
								.openStream());
				FileOutputStream fos = new FileOutputStream(
						mcopy.getMinecraftPath() + "minecrafterr.jar");
				BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
				byte data[] = new byte[1024];
				int x = 0;
				while ((x = in.read(data, 0, 1024)) >= 0) {
					bout.write(data, 0, x);
				}
				bout.close();
				in.close();
			} catch (IOException e) {
				jTextArea1.setText(jTextArea1.getText() + "Download failed..."
						+ "\n");
			}
			jTextArea1.setText(jTextArea1.getText() + "Download successful!"
					+ "\n");
		}
		// Got launcher.
		jTextArea1.setText(jTextArea1.getText() + "Minecraft launcher found!"
				+ "\n");
		jTextArea1
				.setText(jTextArea1.getText() + "Starting launcher..." + "\n");
		try {
			output = output
					+ "-------------Contents of mods folder:-------------"
					+ "\n";
			jTextArea1.setText(jTextArea1.getText()
					+ "-------------Contents of mods folder:-------------"
					+ "\n");
			output = output
					+ modsFolderContents(mcopy.getMinecraftPath() + "/mods")
					+ "\n";
			jTextArea1.setText(jTextArea1.getText()
					+ modsFolderContents(mcopy.getMinecraftPath() + "/mods")
					+ "\n");
			output = output
					+ "--------------------------------------------------"
					+ "\n";
			jTextArea1.setText(jTextArea1.getText()
					+ "--------------------------------------------------"
					+ "\n");
			System.out.println(System.getProperty("os.name"));
			// Run launcher in new process
			// Process pr =
			// Runtime.getRuntime().exec(System.getProperty("java.home")+"/bin/java -Ddebug=full -cp "+mcopy.getMinecraftPath()+"minecrafterr.jar net.minecraft.LauncherFrame");
			Process pr = Runtime.getRuntime().exec(
					new String[] {
							System.getProperty("java.home") + "/bin/java",
							"-Ddebug=full", "-cp",
							mcopy.getMinecraftPath() + "minecrafterr.jar",
							"net.minecraft.LauncherFrame" });
			// Grab output
			BufferedReader out = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));
			BufferedReader outERR = new BufferedReader(new InputStreamReader(
					pr.getErrorStream()));
			String line = "";
			// TODO: Grab only stderr when Forge is going
			while ((line = out.readLine()) != null
					|| (line = outERR.readLine()) != null) {
				if (line.contains("Setting user: ")) {
					line = "[MCError: Session ID censored]";
					/*
					 * don't show the session id (can be used to temporarily hijack
					 * accounts and join servers with that user)
					 */
				}
				output = output + line + "\n";
				jTextArea1.append(line + "\n");
				jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
			}
		} catch (IOException e) {
			jTextArea1.append("[MCError: An exception has occured while retrieving Minecraft's output.]\n" + e.getMessage() + "\n");
			jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
		}
		// set output
		Main.SPAMDETECT = false;
		jTextArea1.setText(jTextArea1.getText() + "Error report complete.");
		jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
		mcopy.autoAnalyze(output); // Auto-analyze
	}

	public String modsFolderContents(String path) {
		String contents = "";
		File folder = new File(path);
		for (int i = 0; i < folder.listFiles().length; i++) {
			File f = folder.listFiles()[i];
			if (f.isDirectory()) {
				if (!contents.equals("")) {
					contents = contents + ", " + getDirContents(f, f.getName());// new
																				// method
																				// for
																				// recursion
				} else {
					contents = getDirContents(f, f.getName());
				}
			} else {
				if (!contents.equals("")) {
					contents = contents + ", " + f.getName();
				} else {
					contents = f.getName();
				}
			}

		}
		return contents;
	}

	private String getDirContents(File dir, String path) {
		String contents = "";
		for (int i = 0; i < dir.listFiles().length; i++) {
			File f = dir.listFiles()[i];
			if (f.isDirectory()) {
				if (!contents.equals("")) {
					contents = contents + ", "
							+ getDirContents(f, path + "/" + f.getName());
				} else {
					contents = getDirContents(f, path + "/" + f.getName());
				}
			} else {
				if (!contents.equals("")) {
					contents = contents + ", " + path + "/" + f.getName();
				} else {
					contents = path + "/" + f.getName();
				}
			}
		}
		return contents;
	}
}