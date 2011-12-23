package minecrafterror;

import minecrafterror.resources.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class Main{
	
	//IMAGES//
	public Image bg = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/Background.png"));
	public Image bu1 = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/Button.png"));
	public Image bu2 = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/Selected.png"));
	public Image bu3 = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/Pressed.png"));
	public Image about = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/about.png"));
	public Image q = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/question.png"));
	public Image o = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/options.png"));
	public Image p = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/play.png"));
	public Image c = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/copy.png"));

	public ImageIcon copy = new ImageIcon(c);
	public ImageIcon play = new ImageIcon(p);
	public ImageIcon options = new ImageIcon(o);
	public ImageIcon question = new ImageIcon(q);
	public ImageIcon aboat = new ImageIcon(about);
	public ImageIcon but1 = new ImageIcon(bu1);
	public ImageIcon but2 = new ImageIcon(bu2);
	public ImageIcon but3 = new ImageIcon(bu3);
	
	//

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
			public void run() {
				new Main();
			}
		});
	}
	
	public JTextArea textBox = new JTextArea()	;
	public JFrame frame = new JFrame("Minecraft Error");
	public JLabel launch = new JLabel(but1);
	public JLabel paste = new JLabel(but1);
	public JMenuBar menu = new JMenuBar();
	public Font Volt;
			
	public Main(){
		InputStream in = getClass().getResourceAsStream("/VolterGoldfish.ttf");
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
		
		
		launch.addMouseListener(new MouseAdapter(){
			public void mouseExited(MouseEvent me){
				if (launch.isEnabled() == true) {
					launch.setIcon(but1);
				}
			}		
			
			public void mouseEntered(MouseEvent me) {
				if (launch.isEnabled() == true) {
					launch.setIcon(but2);
					}
			}

			public void mousePressed(MouseEvent me) {
				if (launch.isEnabled() == true) {
					launch.setIcon(but3);
					//Sound.CLICK.play();
				}
			}

			public void mouseReleased(MouseEvent me) {
				if (launch.isEnabled() == true) {
					launch.setIcon(but2);
				}
			}
		});
		
		paste.addMouseListener(new MouseAdapter(){
			public void mouseExited(MouseEvent me){
				if (paste.isEnabled() == true) {
					paste.setIcon(but1);
				}
			}		
			
			public void mouseEntered(MouseEvent me) {
				if (paste.isEnabled() == true) {
					paste.setIcon(but2);
					}
			}

			public void mousePressed(MouseEvent me) {
				if (paste.isEnabled() == true) {
					paste.setIcon(but3);
					//Sound.CLICK.play();
				}
			}

			public void mouseReleased(MouseEvent me) {
				if (paste.isEnabled() == true) {
					paste.setIcon(but2);
				}
			}
		});
		
		JMenu File = new JMenu("File");
		
		JMenuItem Launch = new JMenuItem("Launch Minecraft", play);
		File.add(Launch);
		
		JMenuItem Paste = new JMenuItem("Paste Error", copy);
		File.add(Paste);
		
		JMenuItem Options = new JMenuItem("Options", options);
		File.add(Options);
		File.addSeparator();
		
		JMenuItem Exit = new JMenuItem("Exit");
		File.add(Exit);
		Exit.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me) {
				frame.dispose();
			}
		});
		
		JMenu Help = new JMenu("Help");
		
		JMenuItem About = new JMenuItem("About", question);
		Help.add(About);
		About.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me) {
				JOptionPane.showMessageDialog(frame,
					    "Created by Medsouz for the sole purpose of easily logging Minecraft's outputs and errors.",
					    "About",
					    JOptionPane.QUESTION_MESSAGE,
					    aboat);
			}
		});
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(0,0,580,380);
		scroll.setViewportView(textBox);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setViewportBorder(BorderFactory.createLineBorder(Color.black));
		scroll.setLocation(8,20);

		textBox.setWrapStyleWord(true);
		textBox.setLineWrap(true);
		textBox.setHighlighter(null);
		textBox.setCaretColor(null);
		textBox.setForeground(Color.black);
		textBox.setMargin(new Insets(5,5,5,0));
		
		//textBox.setEditable(false);
		
		launch.setSize(212, 40);
		launch.setLocation(30, 420);
		launch.setHorizontalTextPosition(JLabel.CENTER);
		launch.setText("Launch Minecraft");
		launch.setForeground(Color.white);
		launch.setFont(VolterT);
		
		paste.setSize(212, 40);
		paste.setLocation(350, 420);
		paste.setHorizontalTextPosition(JLabel.CENTER);
		paste.setText("Paste Error");
		paste.setForeground(Color.white);
		paste.setFont(VolterT);
		
		menu.add(File);
		menu.add(Help);
		
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new JLabel(new ImageIcon(bg)));
		frame.setSize(600,555);
		frame.setResizable(false);
		frame.add(paste);
		frame.add(launch);
		frame.add(scroll);
		frame.setJMenuBar(menu);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}