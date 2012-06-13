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

public class Main{
        
        //IMAGES//
        public Image bg = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/Background.png"));
        public Image bu1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/Button.png"));
        public Image bu2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/Selected.png"));
        public Image bu3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/Pressed.png"));
        public Image about = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/about.png"));
        public Image q = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/question.png"));
        public Image o = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/options.png"));
        public Image p = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/play.png"));
        public Image c = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/minecrafterror/resources/copy.png"));

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
                @Override public void run() {
                    new Main();
                }
            });
        }
        
        public JTextArea textBox = new JTextArea();
        public JFrame frame = new JFrame("MinecraftError");
        public JLabel launch = new JLabel(but1);
        public JLabel paste = new JLabel(but1);
        public JLabel pasteML = new JLabel(but1b);
        public JLabel analyze = new JLabel(but1);
        public JMenuBar menu = new JMenuBar();
        public Font Volt;
        
        public OSType currentOS;
        public Main inst;
        public String analysis;
        
        public String customPath = "";
                        
        public Main()
        {
            inst = this;
            setOS();
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
                @Override
                public void mouseExited(MouseEvent me){
                    if (launch.isEnabled() == true) {
                        launch.setIcon(but1);
                    }
                }                
                @Override
                public void mouseEntered(MouseEvent me) {
                    if (launch.isEnabled() == true) {
                        launch.setIcon(but2);
                    }
                }
                @Override
                public void mousePressed(MouseEvent me) {
                    if (launch.isEnabled() == true) {
                        launch.setIcon(but3);
                    ExecOutput mc = new ExecOutput(textBox,inst);
                    Thread t = new Thread(mc);
                    t.start();
                            //Sound.CLICK.play();
                    }
                }
                @Override
                public void mouseReleased(MouseEvent me) {
                    if (launch.isEnabled() == true) {
                        launch.setIcon(but2);
                    }
                }
            });

            paste.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseExited(MouseEvent me){
                    if (paste.isEnabled() == true) {
                        paste.setIcon(but1);
                    }
                }                

                @Override
                public void mouseEntered(MouseEvent me) {
                    if (paste.isEnabled() == true) {
                        paste.setIcon(but2);
                    }
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    if (paste.isEnabled() == true) {
                        paste.setIcon(but3);
                        pastebin();
                        //Sound.CLICK.play();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    if (paste.isEnabled() == true) {
                        paste.setIcon(but2);
                    }
                }
            });

            pasteML.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseExited(MouseEvent me){
                    if (pasteML.isEnabled() == true) {
                        pasteML.setIcon(but1b);
                    }
                }                

                @Override
                public void mouseEntered(MouseEvent me) {
                    if (pasteML.isEnabled() == true) {
                        pasteML.setIcon(but2b);
                    }
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    if (pasteML.isEnabled() == true) {
                        pasteML.setIcon(but3b);
                        pasteModLoaderOutput();
                        //Sound.CLICK.play();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    if (paste.isEnabled() == true) {
                        paste.setIcon(but2b);
                    }
                }
            });
            analyze.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseExited(MouseEvent me){
                    if (analyze.isEnabled() == true) {
                        analyze.setIcon(but1);
                    }
                }                

                @Override
                public void mouseEntered(MouseEvent me) {
                    if (analyze.isEnabled() == true) {
                        analyze.setIcon(but2);
                    }
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    if (analyze.isEnabled() == true) {
                        analyze.setIcon(but3);
                        analyze();
                        //Sound.CLICK.play();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    if (analyze.isEnabled() == true) {
                        analyze.setIcon(but2);
                    }
                }
            });

            JMenu File = new JMenu("File");

            JMenuItem Launch = new JMenuItem("Launch Minecraft", play);
            File.add(Launch);
            Launch.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent me) {
                    ExecOutput mc = new ExecOutput(textBox,inst);
                    Thread t = new Thread(mc);
                    t.start();
                }
            });

            JMenuItem Paste = new JMenuItem("Paste Error", copy);
            File.add(Paste);

            JMenuItem FolderChange = new JMenuItem("Change Launcher", options);
            File.add(FolderChange);
            FolderChange.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent me) {
                    String newpath = JOptionPane.showInputDialog(frame,
                        "Enter the new launcher path. Leave empty for default.\nNote: MCError does not work with some custom launchers.",
                        getMinecraftPath()); 
                    if(newpath.isEmpty())
                    {
                        customPath = "";
                        return;
                    }
                    File testf = new File(newpath);
                    if(testf.exists() && testf.isFile())
                    {
                        customPath = newpath;
                    }
                    else if(!testf.isFile())
                    {
                        JOptionPane.showMessageDialog(frame,
                            "The path given is not a file.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame,
                            "The path given does not exist.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            File.addSeparator();

            JMenuItem Exit = new JMenuItem("Exit");
            File.add(Exit);
            Exit.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent me) {
                    frame.dispose();
                }
            });

            JMenu Help = new JMenu("Help");

            JMenuItem About = new JMenuItem("About", question);
            Help.add(About);
            About.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent me) {
                    JOptionPane.showMessageDialog(frame,
                        "Created by medsouz for the sole purpose of easily logging Minecraft's outputs and errors.\nGUI created by Malqua.\nAnalysis created by Riking for easy diagnosis.",
                        "About",
                        JOptionPane.QUESTION_MESSAGE,
                        aboat);
                }
            });

            JScrollPane scroll = new JScrollPane();
            scroll.setBounds(0,0,580,330);
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
            DefaultCaret caret = (DefaultCaret)textBox.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            launch.setSize(212, 40);
            launch.setLocation(30, 410);
            launch.setHorizontalTextPosition(JLabel.CENTER);
            launch.setText("Launch Minecraft");
            launch.setForeground(Color.white);
            launch.setFont(VolterT);

            paste.setSize(212, 40);
            paste.setLocation(350, 365);
            paste.setHorizontalTextPosition(JLabel.CENTER);
            paste.setText("Paste Error");
            paste.setForeground(Color.white);
            paste.setFont(VolterT);

            pasteML.setSize(232, 40);
            pasteML.setLocation(340, 410);
            pasteML.setHorizontalTextPosition(JLabel.CENTER);
            pasteML.setText("Paste ModLoader.txt");
            pasteML.setForeground(Color.white);
            pasteML.setFont(VolterT);

            analyze.setSize(232, 40);
            analyze.setLocation(340, 455);
            analyze.setHorizontalTextPosition(JLabel.CENTER);
            analyze.setText("Re-analyze Error");
            analyze.setForeground(Color.white);
            analyze.setFont(VolterT);

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
            frame.add(analyze);
            frame.setJMenuBar(menu);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        public void setOS()
        {
            String os = System.getProperty("os.name").toLowerCase();
            if(os.startsWith("win"))
                currentOS = OSType.WINDOWS;
            else if(os.startsWith("mac"))
                currentOS = OSType.MAC;
            else if(os.contains("nix") || os.contains("nux"))
                currentOS = OSType.LINUX;
            else if(os.contains("solaris"))
                currentOS = OSType.SOLARIS;
        }
    public void pastebin() {
        if(!SPAMDETECT && !Output.isEmpty())
        {
            SPAMDETECT = true;
            analyzePartOne();
            Output = "Recorded by MinecraftError (https://github.com/medsouz/MinecraftError).\n\nAutomatic analysis: "+analysis+"\n"+Output;
            textBox.setText(textBox.getText()+"\nPosting to pastebin.com...\n");

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
                StringBuilder answer = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    answer.append(line);
                }
                writer.close();
                reader.close();

                //Output the URL
                textBox.append(answer.toString()+"\n");

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }else{
            if(Output.isEmpty()){
                textBox.append("It appears that there was no output, this can be caused if you still have Minecraft open when you pressed \"Paste Error\". Try closing Minecraft then try again.\n");
            }
            if(SPAMDETECT){
                textBox.append("Whoa! Calm down, it appears that you pressed \"Paste Error\" too many times! Please only press it once and then wait for the link to the pastebin to pop up. Thank you.\n");
            }
        }
    }

    public String getMinecraftPath()
    {
        if(currentOS == null) setOS();
        if(!customPath.isEmpty())
        {
            return customPath;
        }
        if(currentOS.isMac()){
            System.out.println("Mac user!");
            return System.getProperty("user.home")+"/Library/Application\\ Support/minecraft/";
        }
        if(currentOS.isLinux()){
            System.out.println("Linux/Unix/Solaris user!");
            return System.getProperty("user.home")+"/.minecraft/";
        }
        if(currentOS.isWindows()){
            System.out.println("Windows user!");
            return System.getenv("APPDATA")+"/.minecraft/";
        }
        throw new RuntimeException("Unknown OS!");
    }

    public void pasteModLoaderOutput()
    {
        String modLoaderPath = getMinecraftPath()+"ModLoader.txt";
        String contents = "Recorded by MinecraftError v2.4 (https://github.com/medsouz/MinecraftError):\n";
        try
        {
            byte[] b = new byte[(int) new File(modLoaderPath).length()];
            BufferedInputStream f = new BufferedInputStream(new FileInputStream(modLoaderPath));
            f.read(b);
            contents = contents + new String(b);
            f.close();
            //one last check to make sure everything worked
            if(modLoaderPath.equals("") || contents.equals("")){
                System.out.println("failed");
                //** TODO: popup box, maybe?
                return;
            }
            URL url = new URL("http://pastebin.com/api/api_post.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write("api_dev_key=00ee7bd5d711b33ec4c1386b32f8e945&api_option=paste&api_paste_code="+contents);
                writer.flush();
                StringBuilder answer = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    answer.append(line);
                }
                writer.close();
                reader.close();
                textBox.append("\n"+answer.toString()+"\n");
        }catch(Exception Err){
            System.out.println(Err.getMessage());
            return;
        }
    }
    
    
    public void analyze()
    {
        boolean assKicking = analyzePartOne();
        if(!SPAMDETECT && !Output.isEmpty())
        {
            if(!assKicking)
            {
                textBox.append("\n\nHere's my guess as to what went wrong:\n\n" + analysis);
            }
            else
            {
                textBox.append("\n\nWell that one was easy.\n\n"+analysis);
            }
        }
        else
        {
            if(Output.isEmpty())
            {
                textBox.append("\nThe output's empty. You need to run Minecraft first, and have it close as well.\n");
            }
            else if(SPAMDETECT)
            {
                textBox.append("\nWhoa, now. Don't be clicking buttons like a madman.\n");
            }
        }
    }
    private boolean analyzePartOne()
    {
        analysis = "Hm, I can't seem to figure it out.\nIf your client failed to load press Paste Error and show that link to #Risucraft on esper.net";
        boolean swiftKickInTheAss = false;
	boolean unknown=false;
        /// SECTION: OUTPUT
        if(Output.contains("java.lang.VerifyError")
                || Output.contains("java.lang.IncompatibleClassChangeError")
                || Output.contains("java.lang.NoSuchFieldError")
                )
        {
            analysis = "You installed mods with a minecraft version different than the one you're using.";
            swiftKickInTheAss = true;
			// TODO: improve checking for bad mods folder
            if(Output.contains("mod_Arrows.load()")
				|| false
				)
            {
            	analysis = "Do not use mods folder for mods that modify base classes.";
            }
        }
        else if(Output.contains("java.lang.NoSuchMethodError"))
        {
            analysis = "You installed mods with a minecraft version different than the one you're using.";
            swiftKickInTheAss = true;
            if(Output.contains("mod_MinecraftForge") && Output.contains("getSaveFolder"))
            {
                analysis = "You need to install Forge **AFTER** you install ModLoader.";
                swiftKickInTheAss = false;
            }
        }
        else if(Output.contains("java.lang.SecurityException: SHA"))
        {
            analysis = "Failure to delete META-INF.";
            swiftKickInTheAss = true;
        }
        else if(Output.contains("java.lang.StackOverflowError"))
        {
            analysis = "Minecraft had an infinite loop. If you were not testing a mod...god help you.";
        }
        else if(Output.contains("EXCEPTION_ACCESS_VIOLATION") || Output.contains("SIGSEGV"))
        {

            if(currentOS.isLinux())
            {
                analysis = "Segmentation fault. Try disabling the FGLRX drivers if this is a problem.";
            }
            else
            {
                analysis = "Segmentation fault. Check your graphics drivers.";
            }
        }
        else if(Output.contains("java.lang.NoClassDefFoundError"))
        {
            int pos =Output.indexOf("java.lang.NoClassDefFoundError") + 32;
            int pos2 = Output.indexOf("\n", pos);
            String missing = Output.substring(pos, pos2);

            if(missing.contains("ModLoader"))
            {
                analysis = "ModLoader was not installed.";
                swiftKickInTheAss = true;
            }
            else if(missing.contains("EntityRendererProxy"))
            {
            	analysis = "ModLoader was not installed. Please install Risugami's ModLoader.";
                swiftKickInTheAss = true;
            }
            else if(missing.contains("forge"))
            {
            	analysis = "Forge was not installed";
                swiftKickInTheAss = true;
            }
            else if(missing.contains("buildcraft"))
            {
            	analysis = "Buildcraft was not installed";
            	swiftKickInTheAss = true;
            }
            else if(missing.contains("PlayerBase"))
            {
                analysis = "PlayerAPI was not installed. Download link: http://www.minecraftforum.net/topic/738498-/";
            }
            else if(Output.contains("wrong name:"))
            {
                    analysis = "MCP recompilation error";
            }
        }
        else if(Output.contains("java.lang.UnsatisfiedLinkError"))
        {
            analysis = "You have to switch back to Java 6; Java 7 does not include a required library.";
        }

        else if(Output.contains("insufficient memory"))
        {
            analysis = "Java ran out of memory. Get more RAM. Sorry about that.";
        }
        else if(Output.contains("java.lang.IllegalStateException: Only one LWJGL context may be instantiated at any one time.")
            || Output.contains("org.lwjgl.LWJGLException: Could not create context")
            )
        {
            analysis = "Something went wrong with the rendering. Unknown cause.";
        }
        else if(Output.contains("java.io.FileNotFoundException"))
        {
            analysis = "Unknown. Maybe you missed a few config files when installing the mod.";
        }
        else if(Output.contains("Starting minecraft server"))
        {
            analysis = "Client mods DO NOT WORK on a server!!!";
            swiftKickInTheAss = true;
        }
        else if(Output.contains("java.io.IOException: Bad packet id 230"))
        {
            analysis = "You forgot to install ModLoaderMP. Failure.";
            swiftKickInTheAss = true;
        }
        else if(Output.contains("java.util.zip.ZipException: invalid entry"))
        {
            analysis = "You used a bad zip archiver. Use 7zip or WinRAR.";
            swiftKickInTheAss = true;
        }
        else if(Output.contains("java.util.ConcurrentModificationException"))
        {
            
            if(Output.contains("ModLoader.onTick"))
            {
            	analysis = "One of the mods is acting up. Tell the author to never remove their mod from the tick list except by returning false.";
            }
        }
	else
	{
	    //Modloader.txt
	    try
	    {
		String modLoaderPath = getMinecraftPath()+"ModLoader.txt";
		byte[] b = new byte[(int) new File(modLoaderPath).length()];
		BufferedInputStream f = new BufferedInputStream(new FileInputStream(modLoaderPath));
		f.read(b);
		String content = new String(b);
		f.close();
		//one last check to make sure everything worked
		if(modLoaderPath.equals("") || content.equals(""))
		{
			System.out.println("failed");
			//** TODO: popup box, maybe?
			unknown=true;
		}
		
		if(content.contains("java.lang.VerifyError"))
		{
			analysis = "You installed mods with a minecraft version different than the one you're using.";
			swiftKickInTheAss = true;
		}
		if(   Output.contains("Failed to load mod")
		   || Output.contains("Exception in thread"))
		{
			if(content.contains("java.lang.NoClassDefFoundError"))
			{
				if(content.contains("java.lang.NoClassDefFoundError: forge"/*.ITextureProvider*/))
				{
					analysis = "Missing forge! Download: http://www.minecraftforum.net/topic/514000-";
				}
				else if(content.contains("java.lang.NoClassDefFoundError: BaseModMp"))
				{
					analysis = "Missing ModLoader Multiplayer! Download: http://www.minecraftforum.net/topic/86765-/";
				}
				else if(content.contains("java.lang.NoClassDefFoundError: buildcraft"))
				{
					analysis = "Missing Buildcraft, or, the load order was incorrect. Download: http://www.mod-buildcraft.com/ \nTo the mod author: Look into BaseMod.getPriorities().";
				}
			}
			else
			{
				analysis = "Hm, I can't seem to figure it out. If your client failed to load press Paste ModLoader.txt and show that link to #Risucraft on esper.net";
				// unknown=true;
			}
		} 

	    }catch(java.io.IOException e)
	    {
		unknown = true;
	    }
	}
	if(unknown || analysis.isEmpty())
        {
            analysis = "Hm, I can't seem to figure it out. If your client failed to load press Paste Error and show that link to #Risucraft on esper.net";
        }
        return swiftKickInTheAss;
    }
}
