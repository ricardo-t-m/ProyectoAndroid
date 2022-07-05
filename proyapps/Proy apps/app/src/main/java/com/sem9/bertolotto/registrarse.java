package com.sem9.bertolotto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.sem9.bertolotto.entidad.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class registrarse extends AppCompatActivity {

    TextView txtIngrese;
    EditText txtRNombre,txtRDepartamento,txtRCelular,txtRCorreo,txtRContraseña;
    Button btnRContinuar;



    Persona persona;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        btnIngresar();
        mAuth = FirebaseAuth.getInstance();
        asignarReferencias();
        inicializarFirebase();
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
    }



    private void asignarReferencias() {
        txtRNombre = findViewById(R.id.txtRNombre);
        txtRDepartamento=findViewById(R.id.txtRDepartamento);
        txtRCelular=findViewById(R.id.txtRCelular);
        txtRCorreo=findViewById(R.id.txtRCorreo);
        txtRContraseña=findViewById(R.id.txtRContraseña);
        btnRContinuar=findViewById(R.id.btnRContinuar);


        btnRContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturarDatos();
                reference.child("Persona").child(persona.getId()).setValue(persona);

                String mensaje= "Persona Registrada";
                AlertDialog.Builder ventana=new AlertDialog.Builder(registrarse.this);
                ventana.setTitle("Mensaje Informativo");
                ventana.setMessage(mensaje);
                ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(registrarse.this,login.class);
                    }
                });
                ventana.create().show();
            }
        });
    }

    private void btnIngresar(){
        txtIngrese = findViewById(R.id.txtIngrese);
        txtIngrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registrarse.this,login.class);
                startActivity(intent);
            }
        });
    }



    private void capturarDatos(){
        String nombre= txtRNombre.getText().toString();
        String departamento= txtRDepartamento.getText().toString();
        String celular= txtRCelular.getText().toString();
        String correo=txtRCorreo.getText().toString();
        String password=txtRContraseña.getText().toString();

        persona = new Persona(UUID.randomUUID().toString(),nombre, Integer.parseInt(departamento),Integer.parseInt(celular),correo,password);
        mAuth.createUserWithEmailAndPassword(persona.getCorreo(), persona.getPassword());

    }


}