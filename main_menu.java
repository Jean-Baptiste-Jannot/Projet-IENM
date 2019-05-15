package fr.stjolorient.snir2.navyseaexplorator;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class main_menu extends AppCompatActivity {

    Handler UIHandler;
    Thread Thread_1;
    Thread Thread_2;

    private TextView TEXTVIEW;

    public static final int SERVERPORT = 8888;
    public static final String SERVERIP = "192.168.1.1";

    private Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Bouton parametres

        b1 = findViewById(R.id.b1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_menu.this, settings.class);
                startActivity(intent);
            }
        });

        //Déclaration thread Socket

        UIHandler = new Handler();

        this.Thread_1 = new Thread(new Thread_1());
        this.Thread_1.start();

    }



        // Interruption Thread (Socket) si fermeture Application
    @Override
    protected void onDestroy () {
        super.onDestroy();
        Thread_1.interrupt();
    }
        // Ouverture Socket

    class Thread_1 implements Runnable {

        public void run() {
            Socket socket = null;

            try {

                InetAddress serverAddr = InetAddress.getByName(SERVERIP);
                socket = new Socket(serverAddr, SERVERPORT);

                Thread_2 commThread = new Thread_2(socket);
                new Thread(commThread).start();
                return;

            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class Thread_2 implements Runnable {

        //Déclaration de la socket, client et des flux d'entrée et de sortie

        private Socket clientSocket;
        private DataOutputStream output;
        private BufferedReader input;

        public Thread_2(Socket clientSocket) {

            this.clientSocket = clientSocket;

                  //  Ouverture canal d'entrée

            try {
                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

                //  Ouverture canal de sortie

            try {
                this.output = new DataOutputStream(this.clientSocket.getOutputStream());
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        public void run() {

            while (!Thread.currentThread().isInterrupted()) {

                try {

                    Log.e("lol","ça marche 3");
                    String read = input.readLine();
                    if(read != null) {
                        UIHandler.post(new updateUIThread(read));
                    }
                    else {
                        Thread_1 = new Thread(new Thread_1());
                        Thread_1.start();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Envoi des codes a la Raspberry

                try {



                    String donne_env = "Message TEST";
                    //Envoi de requête et reception

                            output.writeChars(donne_env);
                            output.flush();

                            TimeUnit.SECONDS.sleep(1);
                            Log.e("lol","ça marche 4");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        class updateUIThread implements Runnable {
            private String msg;

            public updateUIThread(String str) { this.msg = str; }


            @Override
            public void run() {
                TEXTVIEW.setText(TEXTVIEW.getText().toString()+"cap"+ msg + "\n");
            }
        }

    }
}
