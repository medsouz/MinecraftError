package minecrafterror;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
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
import javax.swing.text.DefaultCaret;

public class UI {

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

	public JLabel buttonLaunch = new JLabel(iconButton1);
	public JLabel buttonPaste = new JLabel(iconButton1);
	public JLabel buttonReanalyze = new JLabel(iconButton1);
	public JLabel buttonPasteML = new JLabel(iconButtonScaled1);

	public JFrame frame = new JFrame("MinecraftError");
	public JTextArea textBox = new JTextArea();
	public JMenuBar menu = new JMenuBar();

	public Font Volt;

	public UI() {

	}

	/**
	 * Append text to the TextBox.
	 * 
	 * @return The UI instance itself, for chaining.
	 */
	public UI append(String arg0) {
		textBox.append(arg0);
		return this;
	}

	public void resetTextBox() {
		textBox.setText("");
	}

	/**
	 * Moves the Cursor in the JTextArea to the end, because it should
	 * "auto-scroll".
	 */
	public void fixTextPointer() {
		// XXX length - 1 because throws an exception if you pass the length
		// XXX there is no method to get the length of its text, just row count
		textBox.setCaretPosition(textBox.getText().length() - 1);
	}

	public void setUp() {
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
					Main.onClickLaunch();
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
					Main.onClickPastebin();
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
					Main.onClickPastebinML();
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
					Main.onClickReanalyze();
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
				Main.onClickLaunch();
			}
		});

		JMenuItem menuPaste = new JMenuItem("Paste Error", iconCopy);
		File.add(menuPaste);

		JMenuItem menuChangeLauncher = new JMenuItem("Change Launcher",
				iconOptions);
		File.add(menuChangeLauncher);
		menuChangeLauncher.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				Main.onClickChangeLauncher();
			}
		});

		JMenuItem menuChangePath = new JMenuItem("Change Minecraft directory",
				iconOptions);
		File.add(menuChangePath);
		menuChangePath.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				Main.onClickChangePath();
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
	}
}
