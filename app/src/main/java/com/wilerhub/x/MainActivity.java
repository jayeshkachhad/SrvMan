package com.wilerhub.x;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Button btn, start, stop, ping;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultText = findViewById(R.id.resultText);
        btn = findViewById(R.id.btn);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Clicked");

                // Example usage inside an Activity
                new Thread(() -> {
                    try {
                        SSHManager ssh = new SSHManager("31.97.61.125", "root", "password");
                        String result = ssh.runCommand("sudo systemctl status code-server");

                        // Update UI back on the main thread
                        runOnUiThread(() -> {
                            // textView.setText(result);
                            System.out.println("Output: " + result);
                            String status = extractServiceStatus(result);
                            resultText.setText("Status: " + status);
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                Toast.makeText(getApplicationContext(), "Got", Toast.LENGTH_LONG);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Clicked");

                // Example usage inside an Activity
                new Thread(() -> {
                    try {
                        SSHManager ssh = new SSHManager("31.97.61.125", "root", "password");
                        String result = ssh.runCommand("sudo systemctl status code-server");

                        // Update UI back on the main thread
                        runOnUiThread(() -> {
                            // textView.setText(result);
                            System.out.println("Output: " + result);
                            String status = extractServiceStatus(result);
                            resultText.setText("Status: " + status);
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                Toast.makeText(getApplicationContext(), "Got", Toast.LENGTH_LONG);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Start Clicked");

                // Example usage inside an Activity
                new Thread(() -> {
                    try {
                        SSHManager ssh = new SSHManager("31.97.61.125", "root", "password");
                            String result = ssh.runCommand("sudo systemctl start code-server");

                        // Update UI back on the main thread
                        runOnUiThread(() -> {
                            // textView.setText(result);
                            System.out.println("Output: " + result);
                            resultText.setText(result);
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                Toast.makeText(getApplicationContext(), "Got", Toast.LENGTH_LONG);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Clicked");

                // Example usage inside an Activity
                new Thread(() -> {
                    try {
                        SSHManager ssh = new SSHManager("31.97.61.125", "root", "password");
                        String result = ssh.runCommand("sudo systemctl stop code-server");

                        // Update UI back on the main thread
                        runOnUiThread(() -> {
                            // textView.setText(result);
                            System.out.println("Output: " + result);
                            resultText.setText(result);
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                Toast.makeText(getApplicationContext(), "Got", Toast.LENGTH_LONG);
            }
        });

    }


    public static String extractServiceStatus(String sshOutput) {
        // Define the regex pattern to match the service status
        String statusPattern = "Active: ([\\w\\s\\(\\)]+)";  // Matches the "Active: <status>"

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(statusPattern);
        Matcher matcher = pattern.matcher(sshOutput);

        // If the pattern is found in the output
        if (matcher.find()) {
            return matcher.group(1);  // Group 1 contains the status value
        } else {
            return "Status not found";  // Return default message if no match
        }
    }

}