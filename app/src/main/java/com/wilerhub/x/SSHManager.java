package com.wilerhub.x;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.util.Properties;

public class SSHManager {

    private final String host;
    private final String user;
    private final String password;

    public SSHManager(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public String runCommand(String command) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, 22);
        session.setPassword(password);

        // Avoid "Unknown Host Key" prompts
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);

        InputStream in = channel.getInputStream();
        channel.connect();

        // Read output
        StringBuilder outputBuffer = new StringBuilder();
        byte[] tmp = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                outputBuffer.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) continue;
                break;
            }
            try { Thread.sleep(100); } catch (Exception ee) {}
        }

        channel.disconnect();
        session.disconnect();

        return outputBuffer.toString();
    }
}