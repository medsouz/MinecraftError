package minecrafterror;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
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
		File launcher = mcopy.getLauncher();
		jTextArea1
				.setText("Checking for Minecraft launcher (minecrafterr.jar) at "
						+ launcher.getAbsolutePath() + "\n");
		if (!launcher.exists()) {
			jTextArea1.append("Error: Could not find launcher!\n");
			jTextArea1.append("Downloading from Minecraft.net...\n");
			try {
				BufferedInputStream in = new BufferedInputStream(
						new URL(
								"https://s3.amazonaws.com/MinecraftDownload/launcher/minecraft.jar")
								.openStream());
				FileOutputStream fos = new FileOutputStream(mcopy.getLauncher());
				BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
				byte data[] = new byte[1024];
				int x = 0;
				while ((x = in.read(data, 0, 1024)) >= 0) {
					bout.write(data, 0, x);
				}
				bout.close();
				in.close();
			} catch (IOException e) {
				jTextArea1.append("Download failed...\n");
				jTextArea1.append("Minecraft launch cancelled. Details:\n");
				// XXX This is the fastest way to get the exception stack trace
				// to a string
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				jTextArea1.append(sw.toString());
				return;
			}
			jTextArea1.append("Download successful!\n");
		}
		
		// Got launcher.
		jTextArea1.append("Minecraft launcher found!\n");
		jTextArea1.append("Starting launcher...\n");
		try {
			// Print mods folder contents
			StringBuilder SBmodsfolder = new StringBuilder(
					"-------------Contents of mods folder:-------------");
			SBmodsfolder.append("\nMods:\n");
			SBmodsfolder.append(modsFolderContents(mcopy
					.getMinecraftPath(false) + "/mods"));
			if (new File(mcopy.getMinecraftPath(false) + "/coremods").exists()) {
				SBmodsfolder.append("\nForge Coremods:\n");
				SBmodsfolder.append(modsFolderContents(mcopy
						.getMinecraftPath(false) + "/coremods"));
			}
			SBmodsfolder
					.append("\n--------------------------------------------------\n");
			jTextArea1.append(SBmodsfolder.toString());
			output = SBmodsfolder.toString();
			
			// Run launcher in new process
			Process pr = Runtime.getRuntime().exec(
					new String[] {
							System.getProperty("java.home") + "/bin/java",
							"-Ddebug=full", "-cp",
							mcopy.getLauncher().toString(),
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
					 * don't show the session id (can be used to temporarily
					 * hijack accounts and join servers with that user)
					 */
				}
				output = output + line + "\n";
				jTextArea1.append(line + "\n");
				jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
			}
		} catch (IOException e) {
			jTextArea1
					.append("[MCError: An exception has occured while retrieving Minecraft's output.]\n"
							+ e.getMessage() + "\n");
			jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
		}
		// set output
		Main.SPAMDETECT = false;
		jTextArea1.append("Error report complete.");
		jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
		mcopy.autoAnalyze(output); // Auto-analyze
	}

	public StringBuilder modsFolderContents(String path) {
		StringBuilder contents = new StringBuilder();
		File folder = new File(path);
		boolean docomma = false;
		for (int i = 0; i < folder.listFiles().length; i++) {
			File f = folder.listFiles()[i];
			if (f.isDirectory()) {
				if (docomma) {
					contents.append(", ");
				}
				contents.append(getDirContents(f, f.getName()));
				docomma = true;
			} else {
				if (docomma) {
					contents.append(", ");
				}
				contents.append(f.getName());
				docomma = true;
			}

		}
		return contents;
	}

	private StringBuilder getDirContents(File dir, String path) {
		StringBuilder contents = new StringBuilder();
		boolean docomma = false;
		for (int i = 0; i < dir.listFiles().length; i++) {
			File f = dir.listFiles()[i];
			if (f.isDirectory()) {
				if (docomma) {
					contents.append(", ");
				}
				contents.append(getDirContents(f, path + "/" + f.getName()));
				docomma = true;
			} else {
				if (docomma) {
					contents.append(", ");
				}
				contents.append(path).append("/").append(f.getName());
				docomma = true;
			}
		}
		return contents;
	}
}