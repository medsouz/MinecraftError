package minecrafterror;
import java.io.*;
import java.net.*;
public class ExecOutput implements Runnable {

    private javax.swing.JTextArea jTextArea1;

    public ExecOutput(javax.swing.JTextArea Output) {
        jTextArea1 = Output;
    }

    public void run() {
        String output = "";
        jTextArea1.setText("Checking for Minecraft launcher (minecrafterr.jar) in "+System.getProperty("user.dir").toString()+"\n");
        File Launcher = new File("minecrafterr.jar");
        if(!Launcher.exists()){
            jTextArea1.setText(jTextArea1.getText()+"Error: Could not find launcher!\n");
            jTextArea1.setText(jTextArea1.getText()+"Downloading from Minecraft.net...\n");
            try{
            BufferedInputStream in = new BufferedInputStream(new URL("https://s3.amazonaws.com/MinecraftDownload/launcher/minecraft.jar").openStream());
            FileOutputStream fos = new FileOutputStream("minecrafterr.jar");
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
        jTextArea1.setText(jTextArea1.getText()+"Minecraft launcher found!"+"\n");
        jTextArea1.setText(jTextArea1.getText()+"Starting launcher..."+"\n");
        try{
        System.out.println(System.getProperty("os.name"));
        Process pr = Runtime.getRuntime().exec(System.getProperty("java.home")+"/bin/java -Ddebug=full -cp minecrafterr.jar net.minecraft.LauncherFrame");
        BufferedReader out = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        BufferedReader outERR = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        String line = "";
        while ((line = out.readLine()) != null || (line = outERR.readLine()) != null){
        output = output+line+"\n";
        jTextArea1.setText(jTextArea1.getText()+line+"\n");}
        }catch(IOException e){
            jTextArea1.setText(jTextArea1.getText()+e.getMessage()+"\n");
        }
        Main.Output = output;
        Main.SPAMDETECT = false;
        jTextArea1.setText(jTextArea1.getText()+"Error report complete, if your client failed to load press Paste Error and show that link to #Risucraft on esper.net.\n");
    }
}