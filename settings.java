package fr.stjolorient.snir2.navyseaexplorator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class settings extends AppCompatActivity {

    private settings PopModif;
    private Button button;
    EditText T_pro;
    FileOutputStream output = null;
    private  EditText editText;
    private Button b2;
    SharedPreferences SPRE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);

        // Préparation des préférences personnelles

        SPRE = this.getPreferences(Context.MODE_PRIVATE);

        T_pro = (EditText)findViewById(R.id.T_Pro);
        int n = SPRE.getInt("Profondeur_mini", 0);
        T_pro.setText(""+n);

        // Bouton retour

        b2 = findViewById(R.id.b2);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                    Intent intent = new Intent(settings.this, main_menu.class );
                    startActivity(intent);
                    finish();
                }});

        this.PopModif = this;

    button = findViewById(R.id.mod);
    button.setOnClickListener(new View.OnClickListener() {
        @Override

                public void onClick(View view) {

            // Ouverture Popup a choix multiples

            AlertDialog.Builder PopModif = new AlertDialog.Builder(settings.this);
            PopModif.setTitle("Modifier la hateur d'eau critique");
            PopModif.setMessage("Nouvelle hauteur d'eau critique a définir :");

            PopModif.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(getApplicationContext(), "Nouvelle modification enregistrée", Toast.LENGTH_SHORT).show();

                    editText = (EditText) findViewById(R.id.T_Pro);

                    SharedPreferences.Editor editor = SPRE.edit();

                    editor.putInt("Profondeur_mini", Integer.parseInt(T_pro.getText().toString()));

                    editor.commit();

                }
            });
            PopModif.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText((getApplicationContext()), "Modification annulée", Toast.LENGTH_SHORT).show();

                }
            });

            AlertDialog dialog = PopModif.create();
            dialog.show();
        }

    });

}

}