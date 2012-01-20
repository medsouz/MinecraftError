package minecrafterror;
import java.io.*;
import java.net.*;
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
        File Launcher = new File(mcopy.getMinecraftPath()+"minecrafterr.jar");
        jTextArea1.setText("Checking for Minecraft launcher (minecrafterr.jar) in "+Launcher.getAbsolutePath()+"\n");
        if(!Launcher.exists()){
            jTextArea1.setText(jTextArea1.getText()+"Error: Could not find launcher!\n");
            jTextArea1.setText(jTextArea1.getText()+"Downloading from Minecraft.net...\n");
            try{
            BufferedInputStream in = new BufferedInputStream(new URL("https://s3.amazonaws.com/MinecraftDownload/launcher/minecraft.jar").openStream());
            FileOutputStream fos = new FileOutputStream(mcopy.getMinecraftPath()+"minecrafterr.jar");
            BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
            byte data[] = new byte[1024];
            int x=0;
            while((x=in.read(data,0,1024))>=0)
            {
            bout.write(data,0,x);
            }
            bout.close();
            in.close();
            }catch(IOException e){
            jTextArea1.setText(jTextArea1.getText()+"Download failed..."+"\n");
            }
            jTextArea1.setText(jTextArea1.getText()+"Download successful!"+"\n");
        }
        // Got launcher. 
        jTextArea1.setText(jTextArea1.getText()+"Minecraft launcher found!"+"\n");
        jTextArea1.setText(jTextArea1.getText()+"Starting launcher..."+"\n");
        try{
            System.out.println(System.getProperty("os.name"));
            // Run launcher in new process
            Process pr = Runtime.getRuntime().exec(System.getProperty("java.home")+"/bin/java -Ddebug=full -cp \""+mcopy.getMinecraftPath()+"minecrafterr.jar\" net.minecraft.LauncherFrame");
            // Grab output
            BufferedReader out = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            BufferedReader outERR = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
            String line = "";
            while ((line = out.readLine()) != null || (line = outERR.readLine()) != null)
            {
                if(!line.startsWith("Setting user: "))
                {//doesn't show sessionid (can be used to temporarily hijack accounts and join servers with that user)
                    output = output+line+"\n";
                    jTextArea1.setText(jTextArea1.getText()+line+"\n");
                    jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
                }
            }
        }catch(IOException e){
            jTextArea1.setText(jTextArea1.getText()+e.getMessage()+"\n");
            jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
        }
        // set output
        Main.Output = output;
        Main.SPAMDETECT = false;
        jTextArea1.setText(jTextArea1.getText()+"Error report complete.");
        jTextArea1.setCaretPosition(jTextArea1.getText().length() - 1);
        mcopy.analyze(); // Auto-analyze
    }
}