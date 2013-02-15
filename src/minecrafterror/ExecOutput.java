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

	private UI ui;
	private ProcessBuilder pbuild;

	public ExecOutput(UI paramGUI, Main paramMain) {
		ui = paramGUI;
		pbuild = new ProcessBuilder();
		// Redirects STDERR to STDIN for Forge
		pbuild.redirectErrorStream(true);
	}

	@Override
	public void run() {
		String output = "";

		// Get launcher jar
		File launcher = Main.getLauncher();
		ui.resetTextBox();
		ui.append("Checking for Minecraft launcher (minecrafterr.jar) at "
				+ launcher.getAbsolutePath() + "\n");
		if (!launcher.exists()) {
			ui.append("Error: Could not find launcher!\n");
			ui.append("Downloading from Minecraft.net...\n");
			try {
				BufferedInputStream in = new BufferedInputStream(
						new URL(
								"https://s3.amazonaws.com/MinecraftDownload/launcher/minecraft.jar")
								.openStream());
				FileOutputStream fos = new FileOutputStream(Main.getLauncher());
				BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
				byte data[] = new byte[1024];
				int x = 0;
				while ((x = in.read(data, 0, 1024)) >= 0) {
					bout.write(data, 0, x);
				}
				bout.close();
				in.close();
			} catch (IOException e) {
				ui.append("Download failed...\n");
				ui.append("Minecraft launch cancelled. Details:\n");
				// XXX This is the fastest way to get the exception stack trace
				// to a string
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				ui.append(sw.toString());
				ui.fixTextPointer();
				return;
			}
			ui.append("Download successful!\n");
		}

		// Got launcher.
		ui.append("Minecraft launcher found!\n");
		ui.append("Starting launcher...\n");
		try {
			// Print mods folder contents
			StringBuilder SBmodsfolder = new StringBuilder(
					"-------------Contents of mods folder:-------------\n");
			SBmodsfolder.append(modsFolderContents(Main.getMinecraftPath()
					+ "/mods"));
			if (new File(Main.getMinecraftPath() + "/coremods").exists()) {
				SBmodsfolder
						.append("-----------Contents of coremods folder:-----------\n");
				SBmodsfolder.append(modsFolderContents(Main.getMinecraftPath()
						+ "/coremods"));
			}
			SBmodsfolder
					.append("--------------------------------------------------\n");
			ui.append(SBmodsfolder.toString());
			output = SBmodsfolder.toString();

			// Run launcher in new process
			// Process pr = Runtime.getRuntime().exec(
			pbuild.command(System.getProperty("java.home") + "/bin/java",
					"-Ddebug=full", "-cp", Main.getLauncher().toString(),
					"net.minecraft.LauncherFrame");
			pbuild.directory(Main.getMinecraftPath());
			Process pr = pbuild.start();

			// Grab output
			// Note that STDERR has been redirected to STDOUT by the
			// ProcessBuilder
			// Despite the name of "input stream", this is the STDOUT.
			BufferedReader out = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));
			String line = "";

			while ((line = out.readLine()) != null) {
				if (line.contains("Setting user: ")) {
					line = "[MCError: Session ID censored]";
					/*
					 * don't show the session id (can be used to temporarily
					 * hijack accounts and join servers with that user)
					 */
				}
				output = output + line + "\n";
				ui.append(line + "\n");
				ui.fixTextPointer();
			}
		} catch (IOException e) {
			ui.append("[MCError: An exception has occured while retrieving Minecraft's output.]\n"
					+ e.getMessage() + "\n");
			ui.fixTextPointer();
		}
		// set output
		Main.SPAMDETECT = false;
		Main.instance.autoAnalyze(output); // Auto-analyze
		ui.append("\nError report complete.");
		ui.fixTextPointer();
	}

	public StringBuilder modsFolderContents(String path) {
		StringBuilder contents = new StringBuilder();
		File folder = new File(path);
		for (int i = 0; i < folder.listFiles().length; i++) {
			File f = folder.listFiles()[i];
			if (f.isDirectory()) {
				contents.append(getDirContents(f, f.getName())).append('\n');
			} else {
				contents.append(f.getName()).append('\n');
			}

		}
		return contents;
	}

	private StringBuilder getDirContents(File dir, String path) {
		StringBuilder contents = new StringBuilder();
		for (int i = 0; i < dir.listFiles().length; i++) {
			File f = dir.listFiles()[i];
			if (f.isDirectory()) {
				contents.append(getDirContents(f, path + "/" + f.getName()))
						.append('\n');
			} else {
				contents.append(path).append("/").append(f.getName())
						.append('\n');
			}
		}
		return contents;
	}
}