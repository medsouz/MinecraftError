package minecrafterror;

//import minecrafterror.resources.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import minecrafterror.analysis.AnalysisResult;
import minecrafterror.analysis.AnalyzerDefault;
import minecrafterror.analysis.AnalyzerNCDFE;
import minecrafterror.analysis.AnalyzerSegfault;
import minecrafterror.analysis.AnalyzerSimple;
import minecrafterror.analysis.AnalyzerVersionMismatch;
import minecrafterror.analysis.IErrorAnalyzer;

public class Main {

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main();
			}
		});
	}

	public static OSType currentOS;
	public static Main instance;
	public static UI GuiInstance;

	@Deprecated
	public String customMcPath = "";
	public static File launcher;
	public static File mcPath;
	public static String Output = "";
	static volatile boolean SPAMDETECT = false;

	public static ArrayList<IErrorAnalyzer> analyzers = new ArrayList<IErrorAnalyzer>();

	private Main() {
		instance = this;
		currentOS = OSType.getOS();

		launcher = new File(getMinecraftPath(true), "minecrafterr-new.jar");
		mcPath = getMinecraftPath(true);

		initializeAnalyzers();

		GuiInstance = new UI();
		GuiInstance.setUp();
	}

	public static void onClickLaunch() {
		ExecOutput mc = new ExecOutput(GuiInstance, instance);
		Thread t = new Thread(mc);
		t.start();
		// Sound.CLICK.play();
	}

	public static void onClickPastebin() {
		instance.pastebin();
		// Sound.CLICK.play();
	}

	public static void onClickPastebinML() {
		instance.pasteModLoaderOutput();
		// Sound.CLICK.play();
	}

	public static void onClickReanalyze() {
		instance.analyze(Output);
		// Sound.CLICK.play();
	}

	public static void onClickChangeLauncher() {
		File retVal = GuiInstance.doChangeLauncherDialog(getLauncher());
		launcher = retVal;
	}

	private void initializeAnalyzers() {
		analyzers.add(new AnalyzerDefault());
		// Make sure Default is always first!
		analyzers.add(new AnalyzerVersionMismatch()
				.addTrigger("java.lang.VerifyError")
				.addTrigger("java.lang.IncompatibleClassChangeError")
				.addTrigger("java.lang.NoSuchMethodError")
				.addTrigger("java.lang.NoSuchFieldError"));
		{
			AnalyzerNCDFE a = new AnalyzerNCDFE();
			a.addClass("ModLoader",
					"You need to install ModLoader or Minecraft Forge.");
			a.addClass("EntityRendererProxy",
					"You need to install ModLoader or Minecraft Forge.");
			a.addClass("forge", "You need to install Minecraft Forge.");
			a.addClass("PlayerBase", "You need to install PlayerAPI.");
			a.addClass("de.matthiasmann.twl", "You need to install GuiApi.");
			a.addClass("buildcraft",
					"An addon mod was missing a dependency: BuildCraft.");
			a.addClass("ic2",
					"An addon mod was missing a dependency: IndustrialCraft 2.");
			analyzers.add(a);
		}
		analyzers.add(new AnalyzerSimple("java.lang.SecurityException: SHA",
				"Failure to delete META-INF.", true));
		analyzers
				.add(new AnalyzerSegfault(
						"Segmentation fault. Try disabling the FGLRX drivers.",
						"Segmentation fault. I can't really help on this one, but try checking your graphics drivers."));
		analyzers
				.add(new AnalyzerSimple(
						"java.lang.IllegalStateException: Only one LWJGL context may be instantiated at any one time.",
						"Something went wrong with the rendering. Unknown cause.",
						false));
		analyzers.add(new AnalyzerSimple(
				"org.lwjgl.LWJGLException: Could not create context",
				"Something went wrong with the rendering. Unknown cause.",
				false));
		analyzers.add(new AnalyzerSimple(
				"java.util.zip.ZipException: invalid entry",
				"You used a bad zip archiver. Use 7zip or WinRAR.", true));

		analyzers
				.add(new AnalyzerSimple(
						"java.io.IOException: Bad packet id 230",
						"You attempted to connect to a ModloaderMP server without MLMP installed.",
						true));
		analyzers.add(new AnalyzerSimple("Starting minecraft server",
				"Client mods do not work on a server!", true));
		// OSOnly(Windows)
		analyzers
				.add(new AnalyzerSimple(
						"Unable to initialize OpenAL.  Probable cause: OpenAL not supported",
						"OpenAL is not installed correctly.\nTo fix it, please download and run the official OpenAL installer from http://connect.creativelabs.com/openal/Downloads/oalinst.zip .",
						false));
		analyzers
				.add(new AnalyzerSimple(
						"java.io.FileNotFoundException",
						"Unknown. Maybe you missed a few config files when installing the mod.",
						false));
		analyzers
				.add(new AnalyzerSimple(
						"insufficient memory",
						"Java ran out of memory. Upgrade your RAM, or increase your paging file size.",
						false));
		analyzers
				.add(new AnalyzerSimple(
						"java.lang.UnsatisfiedLinkError",
						"You have to switch back to Java 6; Java 7 does not include a required library.",
						false));
		analyzers
				.add(new AnalyzerSimple(
						"java.lang.StackOverflowError",
						"Minecraft had an infinite loop. Please check the above output for hints as to the cause, and contact the relevant mod authors.",
						false));
		// TODO: Modloader.txt checking
		/*
		 * try { String modLoaderPath = getMinecraftPath() + "ModLoader.txt";
		 * byte[] b = new byte[(int) new File(modLoaderPath).length()];
		 * BufferedInputStream f = new BufferedInputStream( new
		 * FileInputStream(modLoaderPath)); f.read(b); String content = new
		 * String(b); f.close(); // one last check to make sure everything
		 * worked if (modLoaderPath.equals("") || content.equals("")) {
		 * System.out.println("failed"); unknown = true; }
		 * 
		 * if (content.contains("java.lang.VerifyError")) { analysis =
		 * "You installed mods with a minecraft version different than the one you're using."
		 * ; sillyMistake = true; } if (Output.contains("Failed to load mod") ||
		 * Output.contains("Exception in thread")) { if
		 * (content.contains("java.lang.NoClassDefFoundError")) { if (content
		 * .contains("java.lang.NoClassDefFoundError: forge" { analysis =
		 * "Missing forge! Download: http://www.minecraftforum.net/topic/514000-"
		 * ; } else if (content
		 * .contains("java.lang.NoClassDefFoundError: BaseModMp")) { analysis =
		 * "Missing ModLoader Multiplayer! Download: http://www.minecraftforum.net/topic/86765-/"
		 * ; } else if (content
		 * .contains("java.lang.NoClassDefFoundError: buildcraft")) { analysis =
		 * "Missing Buildcraft, or, the load order was incorrect. Download: http://www.mod-buildcraft.com/ \nTo the mod author: Look into BaseMod.getPriorities()."
		 * ; } } else { analysis =
		 * "Hm, I can't seem to figure it out. If your client failed to load press Paste ModLoader.txt and show that link to #Risucraft on esper.net"
		 * ; // unknown=true; } }
		 * 
		 * } catch (java.io.IOException e) { unknown = true; }
		 */
		// TODO: Forge log checking
	}

	/**
	 * Analyzes the given Minecraft output's Java exceptions and reports its
	 * guess on the problem.
	 * 
	 * @param output
	 * @return
	 */
	public AnalysisResult analyze(String output) {
		ArrayList<AnalysisResult> results = new ArrayList<AnalysisResult>();
		for (IErrorAnalyzer an : analyzers) {
			if (an.applies(output)) {
				results.add(an.getResult(output));
			}
		}
		// Debug
		for (AnalysisResult res : results) {
			System.out.println("Debug: " + Boolean.toString(res.isSilly())
					+ ": " + res.getMessage());
		}
		// If no other results (size = 1), get the first one
		// Otherwise, get the second one
		AnalysisResult conclusion = (results.size() > 1) ? (results.get(1))
				: (results.get(0));
		return conclusion;
	}

	/**
	 * Called from ExecOutput, performs analysis then writes results to the text
	 * box
	 * 
	 * @param output
	 */
	public void autoAnalyze(String output) {
		Output = output; // TODO, temporary measure for pastebin()
		AnalysisResult result = analyze(output);
		GuiInstance
				.append("\n\n")
				.append((result.isSilly()) ? "Well, that one was easy."
						: "Here's what I think went wrong:").append("\n\n")
				.append(result.getMessage()).append("\n");
		GuiInstance.fixTextPointer();
	}

	/**
	 * Gets the path to theMinecraft launcher. May not exist yet.
	 * 
	 * @return Path to launcher .jar file
	 */
	public static File getLauncher() {
		return getLauncher(false);
	}

	/**
	 * Gets the path to theMinecraft launcher. May not exist yet.
	 * 
	 * @return Path to launcher .jar file
	 */
	public static File getLauncher(boolean defaultOnly) {
		if (launcher != null) {
			return launcher;
		} else {
			resetLauncher();
			return launcher;
		}
	}

	/**
	 * Reset the launcher file to the default.
	 */
	public static void resetLauncher() {
		launcher = new File(getMinecraftPath(false), "minecrafterr.jar");
	}

	/**
	 * Get default path to the Minecraft folder on this system.
	 * 
	 * @return Directory for Minecraft
	 */
	public static File getMinecraftPath() {
		return getMinecraftPath(false);
	}

	/**
	 * Get default path to the Minecraft folder on this system.
	 * 
	 * @param defaultOnly
	 *            Do not return a custom path
	 * @return Directory for Minecraft
	 */
	public static File getMinecraftPath(boolean defaultOnly) {
		if (currentOS == null)
			currentOS = OSType.getOS();
		if ((!defaultOnly) && (mcPath != null))
			return mcPath;
		if (currentOS.isMac()) {
			System.out.println("Mac user!");
			return new File(System.getProperty("user.home"),
					"/Library/Application\\ Support/minecraft");
		}
		if (currentOS.isLinux()) {
			System.out.println("Linux/Unix/Solaris user!");
			return new File(System.getProperty("user.home"), "/.minecraft");
		}
		if (currentOS.isWindows()) {
			System.out.println("Windows user!");
			return new File(System.getenv("APPDATA"), "/.minecraft/");
		}
		throw new RuntimeException("Unknown OS!");
	}

	/**
	 * Reset the Minecraft path to the default.
	 */
	public static void resetMinecraftPath() {
		mcPath = getMinecraftPath(true);
	}

	public void pastebin() {
		if (!SPAMDETECT && !Output.isEmpty()) {
			SPAMDETECT = true;
			AnalysisResult result = analyze(Output);
			String paste = "Recorded by MinecraftError (https://github.com/medsouz/MinecraftError).\n\nAutomatic analysis: "
					+ result.getMessage() + "\n" + Output;
			GuiInstance.append("\nPosting to pastebin.com...\n");

			// Build parameter string
			String data = "api_dev_key=00ee7bd5d711b33ec4c1386b32f8e945&api_option=paste&api_paste_code="
					+ paste;
			try {
				// Send the request
				URL url = new URL("http://pastebin.com/api/api_post.php");
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(
						conn.getOutputStream());

				// write parameters
				writer.write(data);
				writer.flush();

				// Get the response
				StringBuilder answer = new StringBuilder();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream()));
				String line;

				answer.append("\n");

				while ((line = reader.readLine()) != null) {
					answer.append(line);
				}
				writer.close();
				reader.close();

				// Output the URL
				answer.append("\n");
				GuiInstance.append(answer.toString());
				GuiInstance.fixTextPointer();

			} catch (MalformedURLException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(650);
					} catch (InterruptedException e) {
						// Do nothing
					}
					SPAMDETECT = false;
				}
			}.start();
		} else {
			if (Output.isEmpty()) {
				GuiInstance
						.append("It appears that there was no output, this can be caused if you still have Minecraft open when you pressed \"Paste Error\". Try closing Minecraft then try again.\n");
				GuiInstance.fixTextPointer();
			} else if (SPAMDETECT) {
				GuiInstance
						.append("Whoa! Calm down, it appears that you pressed \"Paste Error\" too many times! Please only press it once and then wait for the link to the pastebin to pop up. Thank you.\n");
				GuiInstance.fixTextPointer();
			}
		}
	}

	public void pasteModLoaderOutput() {
		String modLoaderPath = getMinecraftPath(false) + "ModLoader.txt";
		String contents = "Recorded by MinecraftError v2.4 (https://github.com/medsouz/MinecraftError):\n";
		try {
			byte[] b = new byte[(int) new File(modLoaderPath).length()];
			BufferedInputStream f = new BufferedInputStream(
					new FileInputStream(modLoaderPath));
			f.read(b);
			contents = contents + new String(b);
			f.close();
			// one last check to make sure everything worked
			if (modLoaderPath.equals("") || contents.equals("")) {
				System.out.println("failed");
				// ** TODO: popup box, maybe?
				return;
			}
			URL url = new URL("http://pastebin.com/api/api_post.php");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream());
			writer.write("api_dev_key=00ee7bd5d711b33ec4c1386b32f8e945&api_option=paste&api_paste_code="
					+ contents);
			writer.flush();
			StringBuilder answer = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			answer.append("\n");
			while ((line = reader.readLine()) != null) {
				answer.append(line);
			}
			writer.close();
			reader.close();
			answer.append("\n");
			GuiInstance.append(answer.toString());
		} catch (Exception Err) {
			System.out.println(Err.getMessage());
			return;
		}
	}
}
