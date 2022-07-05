package com.sem9.bertolotto;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sem9.bertolotto.entidad.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ajustes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ajustes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    Persona persona;

    RecyclerView rvPersonas;
    List<Persona> listaPersonas = new ArrayList<Persona>();
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;
    String correo;
    TextView eliminarCuenta;
    TextView txtCerrarSesion;

    public ajustes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ajustes.
     */
    // TODO: Rename and change types and number of parameters
    public static ajustes newInstance(String param1, String param2) {
        ajustes fragment = new ajustes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void cargarDatos(){
        reference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaPersonas.clear();
                //listaPersonas=null;
                for(DataSnapshot item:snapshot.getChildren()){
                    Persona p = item.getValue(Persona.class);
                    //Toast.makeText(getContext(),p.getCorreo(),Toast.LENGTH_SHORT).show();
                    listaPersonas.add(p);
                    //listaPersonas.get(0).getCorreo();
                }
                //Toast.makeText(getContext(),"lista creada",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(getContext());
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
    }

//    private void buscarCor(){
//        for(int i = 0;i<listaPersonas.size();i++){
//            if (listaPersonas.get(i).getCorreo().equals(correo)) {
//                persona = listaPersonas.get(i);
//            }
//            else {
//                persona = null;
//            }
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ajustes, container, false);
        inicializarFirebase();
        user = FirebaseAuth.getInstance().getCurrentUser();
        correo = user.getEmail();
        cargarDatos();

        //FUNCIONALIDAD CERRAR SESION
        txtCerrarSesion=view.findViewById(R.id.txtCerrarSesion);
        txtCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),login.class);
                startActivity(intent);
            }
        });
//
//        if (listaPersonas!=null){
//            Toast.makeText(getContext(),listaPersonas.get(0).getCorreo(),Toast.LENGTH_SHORT).show();
//            for(int j=0;j<=listaPersonas.size();j++){
//                if(listaPersonas.get(j)!=null){
//                    Toast.makeText(getContext(),listaPersonas.get(j).getCorreo(),Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        }else{
//            Toast.makeText(getContext(),"wrong",Toast.LENGTH_SHORT).show();
//        }
//        String prueba = listaPersonas.get(0).getCorreo();
//        Toast.makeText(getContext(),prueba,Toast.LENGTH_SHORT).show();

        eliminarCuenta = view.findViewById(R.id.eliminar);
        eliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),correo,Toast.LENGTH_SHORT).show();
//                if(persona!=null){
//                    Toast.makeText(getContext(),"encontrado "+persona.getCorreo(),Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(getContext(),"ERROR",Toast.LENGTH_SHORT).show();
//
//                }
                //Toast.makeText(getContext(),"------"+buscarCor().getCorreo(),Toast.LENGTH_SHORT).show();
                //System.out.println("------------------------"+buscarCor().getCorreo()+"-----------");
                //persona = reference.child();
                //reference.child("Persona").child(buscarCor().getId()).removeValue();

                user.delete();
                Toast.makeText(getContext(),"Usuario eliminado",Toast.LENGTH_SHORT).show();
//
                Intent intent=new Intent(getContext(),login.class);
                startActivity(intent);

            }
        });

        return  view;

    }
}