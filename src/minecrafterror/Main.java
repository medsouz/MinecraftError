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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main{
	
	//IMAGES//
	public Image bg = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/Background.png"));
	public Image bu1 = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/Button.png"));
	public Image bu2 = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/Selected.png"));
	public Image bu3 = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/Pressed.png"));
	public Image about = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/about.png"));
	public Image q = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/question.png"));
	public Image o = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/options.png"));
	public Image p = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/play.png"));
	public Image c = Toolkit.getDefaultToolkit().getImage(
			this.getClass().getResource("/minecrafterror/resources/copy.png"));

	public ImageIcon copy = new ImageIcon(c);
	public ImageIcon play = new ImageIcon(p);
	public ImageIcon options = new ImageIcon(o);
	public ImageIcon question = new ImageIcon(q);
	public ImageIcon aboat = new ImageIcon(about.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
	public ImageIcon but1 = new ImageIcon(bu1);
	public ImageIcon but2 = new ImageIcon(bu2);
	public ImageIcon but3 = new ImageIcon(bu3);
	public ImageIcon but1b = new ImageIcon(bu1.getScaledInstance(232, 40, Image.SCALE_SMOOTH));
	public ImageIcon but2b = new ImageIcon(bu2.getScaledInstance(232, 40, Image.SCALE_SMOOTH));
	public ImageIcon but3b = new ImageIcon(bu3.getScaledInstance(232, 40, Image.SCALE_SMOOTH));
	static String Output = "";
	static boolean SPAMDETECT = false;
	
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
	public JFrame frame = new JFrame("MinecraftError");
	public JLabel launch = new JLabel(but1);
	public JLabel paste = new JLabel(but1);
	public JLabel pasteML = new JLabel(but1b);
	public JMenuBar menu = new JMenuBar();
	public Font Volt;
			
	public Main(){
		
		InputStream in = getClass().getResourceAsStream("/minecrafterror/resources/VolterGoldfish.ttf");
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
			        ExecOutput mc = new ExecOutput(textBox);
			        Thread t = new Thread(mc);
			        t.start();
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
					pastebin();
					//Sound.CLICK.play();
				}
			}

			public void mouseReleased(MouseEvent me) {
				if (paste.isEnabled() == true) {
					paste.setIcon(but2);
				}
			}
		});
		
		pasteML.addMouseListener(new MouseAdapter(){
			public void mouseExited(MouseEvent me){
				if (pasteML.isEnabled() == true) {
					pasteML.setIcon(but1b);
				}
			}		
			
			public void mouseEntered(MouseEvent me) {
				if (pasteML.isEnabled() == true) {
					pasteML.setIcon(but2b);
					}
			}

			public void mousePressed(MouseEvent me) {
				if (pasteML.isEnabled() == true) {
					pasteML.setIcon(but3b);
					pasteModLoaderOutput();
					//Sound.CLICK.play();
				}
			}

			public void mouseReleased(MouseEvent me) {
				if (paste.isEnabled() == true) {
					paste.setIcon(but2b);
				}
			}
		});
		
		JMenu File = new JMenu("File");
		
		JMenuItem Launch = new JMenuItem("Launch Minecraft", play);
		File.add(Launch);
		Launch.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me) {
		        ExecOutput mc = new ExecOutput(textBox);
		        Thread t = new Thread(mc);
		        t.start();
			}
		});
		
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
					    "Created by medsouz for the sole purpose of easily logging Minecraft's outputs and errors.\nGUI created by Malqua.",
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
		textBox.setCaretColor(null);
		textBox.setForeground(Color.black);
		textBox.setMargin(new Insets(5,5,5,0));
		textBox.setEditable(false);
		
		launch.setSize(212, 40);
		launch.setLocation(30, 420);
		launch.setHorizontalTextPosition(JLabel.CENTER);
		launch.setText("Launch Minecraft");
		launch.setForeground(Color.white);
		launch.setFont(VolterT);
		
		paste.setSize(212, 40);
		paste.setLocation(350, 400);
		paste.setHorizontalTextPosition(JLabel.CENTER);
		paste.setText("Paste Error");
		paste.setForeground(Color.white);
		paste.setFont(VolterT);
		
		pasteML.setSize(232, 40);
		pasteML.setLocation(340, 450);
		pasteML.setHorizontalTextPosition(JLabel.CENTER);
		pasteML.setText("Paste ModLoader.txt");
		pasteML.setForeground(Color.white);
		pasteML.setFont(VolterT);
		
		menu.add(File);
		menu.add(Help);
		
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/icon.png")));
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new JLabel(new ImageIcon(bg)));
		frame.setSize(600,555);
		frame.setResizable(false);
		frame.add(paste);
		frame.add(pasteML);
		frame.add(launch);
		frame.add(scroll);
		frame.setJMenuBar(menu);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    public void pastebin() {
        if(!SPAMDETECT && !Output.isEmpty()){
        SPAMDETECT = true;
        Output = "Recorded by MinecraftError (https://github.com/medsouz/MinecraftError):\n"+Output;
        textBox.setText(textBox.getText()+"Posting to pastebin.com...\n");

        //Build parameter string
        String data = "api_dev_key=00ee7bd5d711b33ec4c1386b32f8e945&api_option=paste&api_paste_code="+Output;
        try {
            // Send the request
            URL url = new URL("http://pastebin.com/api/api_post.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            //write parameters
            writer.write(data);
            writer.flush();

            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();

            //Output the response
            textBox.setText(textBox.getText()+answer.toString()+"\n");

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        }else{
        	if(Output.isEmpty()){
        		textBox.setText(textBox.getText()+"It appears that there was no output, this can be caused if you still have Minecraft open when you pressed \"Paste Error\". Try closing Minecraft then try again.\n");
        	}
        	if(SPAMDETECT){
        		textBox.setText(textBox.getText()+"Whoa! Calm down, it appears that you pressed \"Paste Error\" too many times! Please only press it once and then wait for the link to the pastebin to pop up. Thank you.\n");
        	}
        	}
    }
    
    public void pasteModLoaderOutput(){
    	String os = System.getProperty("os.name").toLowerCase();
    	String modLoaderPath = "";
    	String contents = "Recorded by MinecraftError (https://github.com/medsouz/MinecraftError):\n";
    	if(os.startsWith("mac")){
    		System.out.println("Mac user!");
    		modLoaderPath = System.getProperty("user.home")+"/Library/Application Support/minecraft/ModLoader.txt";
    	}
    	if(os.contains("nix") || os.contains("nux") || os.contains("solaris")){
    		System.out.println("Linux/Unix/Solaris user!");
    		modLoaderPath = System.getProperty("user.home")+"/.minecraft/ModLoader.txt";
    	}
    	if(os.startsWith("win")){
    		System.out.println("Windows user!");
    		modLoaderPath = System.getenv("APPDATA")+"/.minecraft/ModLoader.txt";
    	}
    	try{
    		byte[] b = new byte[(int) new File(modLoaderPath).length()];
    		BufferedInputStream f = new BufferedInputStream(new FileInputStream(modLoaderPath));
    		f.read(b);
    		contents = contents + new String(b);
    		f.close();
    		//one last check to make sure everything worked
    		if(modLoaderPath.equals("") || contents.equals("")){
    			System.out.println("failed");
    			return;
    		}
        	URL url = new URL("http://pastebin.com/api/api_post.php");
        	URLConnection conn = url.openConnection();
        	conn.setDoOutput(true);
        	OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write("api_dev_key=00ee7bd5d711b33ec4c1386b32f8e945&api_option=paste&api_paste_code="+contents);
            writer.flush();
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();
            textBox.setText(textBox.getText()+answer.toString()+"\n");
    	}catch(Exception Err){
    		System.out.println(Err.getMessage());
    		return;
    	}
    }
}