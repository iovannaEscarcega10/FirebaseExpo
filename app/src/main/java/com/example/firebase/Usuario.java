package com.example.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Usuario extends AppCompatActivity implements View.OnClickListener{

    EditText edt1,edt2;
    Button save,show;
    FirebaseDatabase fbd;
    DatabaseReference db;

    ListView lista;


    List<String> listauser = new ArrayList<String>();
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        edt1 = findViewById(R.id.user);
        edt2= findViewById(R.id.correo);

        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.show).setOnClickListener(this);
        lista = findViewById(R.id.list);

        FirebaseApp.initializeApp(this);
        fbd= FirebaseDatabase.getInstance();
        db = fbd.getReference();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.save:

                String email = edt1.getText().toString();
                String username = edt2.getText().toString();

                User u = new User();
                u.setId((UUID.randomUUID().toString()));
                u.setEmail(email);
                u.setUsername(username);




                db.child("users").child(u.getId()).setValue(u);
                Toast.makeText(this, "agregado", Toast.LENGTH_SHORT).show();
                break;


            case R.id.show:
                db.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                            User user = snapshot.getValue(User.class);
                            String email =user.getEmail();
                            String username = user.getUsername();
                            listauser.add(email);
                            listauser.add(username);




                        }
                        adapter = new ArrayAdapter<String>(Usuario.this,android.R.layout.simple_list_item_1,listauser);
                        lista.setAdapter(adapter);




                        //adapter = new ArrayAdapter<String>(Usuario.this,android.R.layout.simple_list_item_1,lista);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        }


    }
}
