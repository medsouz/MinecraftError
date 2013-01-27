package minecrafterror;

//import minecrafterror.resources.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.text.DefaultCaret;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

	public Image imageBackground = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource(
					"/minecrafterror/resources/Background.png"));
	public Image imageButton1 = Toolkit.getDefaultToolkit()
			.getImage(
					this.getClass().getResource(
							"/minecrafterror/resources/Button.png"));
	public Image imageButton2 = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource(
					"/minecrafterror/resources/Selected.png"));
	public Image imageButton3 = Toolkit.getDefaultToolkit().getImage(
			this.getClass()
					.getResource("/minecrafterror/resources/Pressed.png"));
	public Image imageAbout = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/about.png"));
	public Image imageQuestion = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource(
					"/minecrafterror/resources/question.png"));
	public Image imageOptions = Toolkit.getDefaultToolkit().getImage(
			this.getClass()
					.getResource("/minecrafterror/resources/options.png"));
	public Image imagePlay = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/play.png"));

	public Image imageCopy = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/copy.png"));
	public ImageIcon iconCopy = new ImageIcon(imageCopy);
	public ImageIcon iconPlay = new ImageIcon(imagePlay);
	public ImageIcon iconOptions = new ImageIcon(imageOptions);
	public ImageIcon iconQuestion = new ImageIcon(imageQuestion);
	public ImageIcon iconAbout = new ImageIcon(imageAbout.getScaledInstance(60,
			60, Image.SCALE_SMOOTH));
	public ImageIcon iconButton1 = new ImageIcon(imageButton1);
	public ImageIcon iconButton2 = new ImageIcon(imageButton2);
	public ImageIcon iconButton3 = new ImageIcon(imageButton3);
	public ImageIcon iconButtonScaled1 = new ImageIcon(
			imageButton1.getScaledInstance(232, 40, Image.SCALE_SMOOTH));
	public ImageIcon iconButtonScaled2 = new ImageIcon(
			imageButton2.getScaledInstance(232, 40, Image.SCALE_SMOOTH));
	public ImageIcon iconButtonScaled3 = new ImageIcon(
			imageButton3.getScaledInstance(232, 40, Image.SCALE_SMOOTH));

	public JTextArea textBox = new JTextArea();
	public JFrame frame = new JFrame("MinecraftError");
	public JLabel buttonLaunch = new JLabel(iconButton1);
	public JLabel buttonPaste = new JLabel(iconButton1);
	public JLabel buttonReanalyze = new JLabel(iconButton1);
	public JLabel buttonPasteML = new JLabel(iconButtonScaled1);
	public JMenuBar menu = new JMenuBar();
	public Font Volt;

	public OSType currentOS;
	public Main instance;

	public String customMcPath = "";
	static String Output = "";
	static volatile boolean SPAMDETECT = false;

	public ArrayList<IErrorAnalyzer> analyzers = new ArrayList<IErrorAnalyzer>();

	public Main() {
		instance = this;
		currentOS = OSType.getOS();

		InputStream in = getClass().getResourceAsStream(
				"/minecrafterror/resources/VolterGoldfish.ttf");
		try {
			Volt = Font.createFont(Font.TRUETYPE_FONT, in);
		} catch (FontFormatException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Font VolterT = Volt.deriveFont(18F);
		@SuppressWarnings("unused")
		Font VolterS = Volt.deriveFont(26F);
		@SuppressWarnings("unused")
		Font VolterL = Volt.deriveFont(32F);

		buttonLaunch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if (buttonLaunch.isEnabled() == true) {
					buttonLaunch.setIcon(iconButton2);
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if (buttonLaunch.isEnabled() == true) {
					buttonLaunch.setIcon(iconButton1);
				}
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if (buttonLaunch.isEnabled() == true) {
					buttonLaunch.setIcon(iconButton3);
					ExecOutput mc = new ExecOutput(textBox, instance);
					Thread t = new Thread(mc);
					t.start();
					// Sound.CLICK.play();
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if (buttonLaunch.isEnabled() == true) {
					buttonLaunch.setIcon(iconButton2);
				}
			}
		});

		buttonPaste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if (buttonPaste.isEnabled() == true) {
					buttonPaste.setIcon(iconButton2);
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if (buttonPaste.isEnabled() == true) {
					buttonPaste.setIcon(iconButton1);
				}
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if (buttonPaste.isEnabled() == true) {
					buttonPaste.setIcon(iconButton3);
					pastebin();
					// Sound.CLICK.play();
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if (buttonPaste.isEnabled() == true) {
					buttonPaste.setIcon(iconButton2);
				}
			}
		});

		buttonPasteML.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if (buttonPasteML.isEnabled() == true) {
					buttonPasteML.setIcon(iconButtonScaled2);
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if (buttonPasteML.isEnabled() == true) {
					buttonPasteML.setIcon(iconButtonScaled1);
				}
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if (buttonPasteML.isEnabled() == true) {
					buttonPasteML.setIcon(iconButtonScaled3);
					pasteModLoaderOutput();
					// Sound.CLICK.play();
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if (buttonPaste.isEnabled() == true) {
					buttonPaste.setIcon(iconButtonScaled2);
				}
			}
		});
		buttonReanalyze.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				if (buttonReanalyze.isEnabled() == true) {
					buttonReanalyze.setIcon(iconButton2);
				}
			}

			@Override
			public void mouseExited(MouseEvent me) {
				if (buttonReanalyze.isEnabled() == true) {
					buttonReanalyze.setIcon(iconButton1);
				}
			}

			@Override
			public void mousePressed(MouseEvent me) {
				if (buttonReanalyze.isEnabled() == true) {
					buttonReanalyze.setIcon(iconButton3);
					analyze(Output);
					// Sound.CLICK.play();
				}
			}

			@Override
			public void mouseReleased(MouseEvent me) {
				if (buttonReanalyze.isEnabled() == true) {
					buttonReanalyze.setIcon(iconButton2);
				}
			}
		});

		JMenu File = new JMenu("File");

		JMenuItem Launch = new JMenuItem("Launch Minecraft", iconPlay);
		File.add(Launch);
		Launch.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				ExecOutput mc = new ExecOutput(textBox, instance);
				Thread t = new Thread(mc);
				t.start();
			}
		});

		JMenuItem Paste = new JMenuItem("Paste Error", iconCopy);
		File.add(Paste);

		JMenuItem FolderChange = new JMenuItem("Change Launcher", iconOptions);
		File.add(FolderChange);
		FolderChange.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				String newpath = JOptionPane
						.showInputDialog(
								frame,
								"Enter the new launcher path. Leave empty for default.\nNote: MCError does not work with some custom launchers.",
								getMinecraftPath());
				if (newpath == null || newpath.isEmpty()) {
					customMcPath = "";
					return;
				}
				File testf = new File(newpath);
				if (testf.exists() && testf.isFile()) {
					customMcPath = newpath;
				} else if (!testf.isFile()) {
					JOptionPane.showMessageDialog(frame,
							"The path given is not a file.", "Error",
							JOptionPane.WARNING_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame,
							"The path given does not exist.", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		File.addSeparator();

		JMenuItem Exit = new JMenuItem("Exit");
		File.add(Exit);
		Exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				frame.dispose();
			}
		});

		JMenu Help = new JMenu("Help");

		JMenuItem About = new JMenuItem("About", iconQuestion);
		Help.add(About);
		About.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				JOptionPane
						.showMessageDialog(
								frame,
								"Created by medsouz for the sole purpose of easily logging Minecraft's outputs and errors.\nGUI created by Malqua.\nAnalysis created by Riking for easy diagnosis.",
								"About", JOptionPane.QUESTION_MESSAGE,
								iconAbout);
			}
		});

		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0, 0, 580, 330);
		scroll.setViewportView(textBox);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setViewportBorder(BorderFactory.createLineBorder(Color.black));
		scroll.setLocation(8, 20);

		textBox.setWrapStyleWord(true);
		textBox.setLineWrap(true);
		textBox.setCaretColor(null);
		textBox.setForeground(Color.black);
		textBox.setMargin(new Insets(5, 5, 5, 0));
		textBox.setEditable(false);
		DefaultCaret caret = (DefaultCaret) textBox.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		buttonLaunch.setSize(212, 40);
		buttonLaunch.setLocation(30, 410);
		buttonLaunch.setHorizontalTextPosition(JLabel.CENTER);
		buttonLaunch.setText("Launch Minecraft");
		buttonLaunch.setForeground(Color.white);
		buttonLaunch.setFont(VolterT);

		buttonPaste.setSize(212, 40);
		buttonPaste.setLocation(350, 365);
		buttonPaste.setHorizontalTextPosition(JLabel.CENTER);
		buttonPaste.setText("Paste Error");
		buttonPaste.setForeground(Color.white);
		buttonPaste.setFont(VolterT);

		buttonPasteML.setSize(232, 40);
		buttonPasteML.setLocation(340, 410);
		buttonPasteML.setHorizontalTextPosition(JLabel.CENTER);
		buttonPasteML.setText("Paste ModLoader.txt");
		buttonPasteML.setForeground(Color.white);
		buttonPasteML.setFont(VolterT);

		buttonReanalyze.setSize(232, 40);
		buttonReanalyze.setLocation(340, 455);
		buttonReanalyze.setHorizontalTextPosition(JLabel.CENTER);
		buttonReanalyze.setText("Re-analyze Error");
		buttonReanalyze.setForeground(Color.white);
		buttonReanalyze.setFont(VolterT);

		menu.add(File);
		menu.add(Help);

		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource(
						"/minecrafterror/resources/icon.png")));
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new JLabel(new ImageIcon(imageBackground)));
		frame.setSize(600, 555);
		frame.setResizable(false);
		frame.add(buttonPaste);
		frame.add(buttonPasteML);
		frame.add(buttonLaunch);
		frame.add(scroll);
		frame.add(buttonReanalyze);
		frame.setJMenuBar(menu);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initializeAnalyzers();
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
		textBox.append("\n\n");
		textBox.append((result.isSilly()) ? "Well that one was easy."
				: "Here's what I think went wrong:");
		textBox.append("\n\n");
		textBox.append(result.getMessage());
		textBox.append("\n");
		textBox.setCaretPosition(textBox.getText().length()-1);
	}

	public String getMinecraftPath() {
		if (currentOS == null)
			currentOS = OSType.getOS();
		if (!customMcPath.isEmpty()) {
			return customMcPath;
		}
		if (currentOS.isMac()) {
			System.out.println("Mac user!");
			return System.getProperty("user.home")
					+ "/Library/Application\\ Support/minecraft/";
		}
		if (currentOS.isLinux()) {
			System.out.println("Linux/Unix/Solaris user!");
			return System.getProperty("user.home") + "/.minecraft/";
		}
		if (currentOS.isWindows()) {
			System.out.println("Windows user!");
			return System.getenv("APPDATA") + "/.minecraft/";
		}
		throw new RuntimeException("Unknown OS!");
	}

	public void pastebin() {
		if (!SPAMDETECT && !Output.isEmpty()) {
			SPAMDETECT = true;
			AnalysisResult result = analyze(Output);
			String paste = "Recorded by MinecraftError (https://github.com/medsouz/MinecraftError).\n\nAutomatic analysis: "
					+ result.getMessage() + "\n" + Output;
			textBox.setText(textBox.getText()
					+ "\nPosting to pastebin.com...\n");

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
				while ((line = reader.readLine()) != null) {
					answer.append(line);
				}
				writer.close();
				reader.close();

				// Output the URL
				textBox.append(answer.toString() + "\n");

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
				textBox.append("It appears that there was no output, this can be caused if you still have Minecraft open when you pressed \"Paste Error\". Try closing Minecraft then try again.\n");
			}
			if (SPAMDETECT) {
				textBox.append("Whoa! Calm down, it appears that you pressed \"Paste Error\" too many times! Please only press it once and then wait for the link to the pastebin to pop up. Thank you.\n");
			}
		}
	}

	public void pasteModLoaderOutput() {
		String modLoaderPath = getMinecraftPath() + "ModLoader.txt";
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
			while ((line = reader.readLine()) != null) {
				answer.append(line);
			}
			writer.close();
			reader.close();
			textBox.append("\n" + answer.toString() + "\n");
		} catch (Exception Err) {
			System.out.println(Err.getMessage());
			return;
		}
	}
}
