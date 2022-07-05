package com.sem9.bertolotto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

import java.util.Objects;

public class login extends AppCompatActivity {

    TextView txtLRegistrar;
    EditText txtLCorreo, txtLContrasena;
    String correo, contrasena;
    Button btnLogin;

    FirebaseUser user;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference reference;

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        database= FirebaseDatabase.getInstance();
        reference=database.getReference();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarFirebase();
        btnRegistrar();

        mAuth = FirebaseAuth.getInstance();

        btnLogin();
    }

    private void btnLogin(){
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asignarRef();
                if(!Objects.equals(correo, "") && !Objects.equals(contrasena, "")){
                    funcionLogin(correo, contrasena);

                } else {
                    Toast.makeText(getApplicationContext(),"Ingrese datos validos",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void updateUI(FirebaseUser user){
        if(user!=null){
            //Toast.makeText(getApplicationContext(),"INGRESO EXITOSO "+user.getEmail(),Toast.LENGTH_LONG).show();
            Intent intent=new Intent(login.this,MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
        }
    }

    private void btnRegistrar(){
        //TEXTO FUNCIONANDO DE REGISTRAR
        txtLRegistrar=findViewById(R.id.txtLRegistrar);
        txtLRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,registrarse.class);
                startActivity(intent);
            }
        });
    }

    public void asignarRef(){
        txtLCorreo=findViewById(R.id.txtLCorreo);
        txtLContrasena=findViewById(R.id.txtLContrasena);

        correo = txtLCorreo.getText().toString();
        contrasena = txtLContrasena.getText().toString();

        //Toast.makeText(getApplicationContext(),"Correo: "+correo+"Constraena: "+contrasena,Toast.LENGTH_SHORT).show();

    }

    private void funcionLogin(String Correo, String Contrasena) {
        mAuth.signInWithEmailAndPassword(Correo, Contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = mAuth.getCurrentUser();
                            //Toast.makeText(getApplicationContext(),"Inicio exitoso",Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            user = null;
                            Toast.makeText(getApplicationContext(), "Inicio de Sesión Fallida",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


}