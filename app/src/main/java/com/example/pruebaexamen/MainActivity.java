package com.example.pruebaexamen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_modificar,btn_buscar;
    AdaptadorLista adaptador;
    ListView l;
    Context context;
    EditText et_nombre,et_apellido,et_correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;


        btn_modificar = findViewById(R.id.bt_modificar);
        btn_buscar=findViewById(R.id.btn_buscar);
        et_nombre=findViewById(R.id.et_nombre);
        et_apellido=findViewById(R.id.et_apellido);
        et_correo=findViewById(R.id.et_correo);
        l =(ListView)findViewById(R.id.lista);
        //si falla algo pues ya sabeis
        //FirebaseApp.initializeApp(this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sacarDialog();
            }
        });




        btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarDatos();
            }
        });

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarDatos();
            }
        });
        //METODOS XAXIS sacar datos al listview

        sacarDatos();

    }

    private void buscarDatos() {
        DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        Query q=bbdd.orderByChild("nombre").equalTo(et_nombre.getText().toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Usuario u = d.getValue(Usuario.class);
                    et_apellido.setText(u.getApellido());
                    et_correo.setText(u.getCorreo());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void modificarDatos() {
        DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        Query q=bbdd.orderByChild("nombre").equalTo(et_nombre.getText().toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()) {

                    //creamos un nuevo usuario para suplantar a el otro

                    String nombre = et_nombre.getText().toString();
                    String apellido = et_apellido.getText().toString();
                    String correo = et_correo.getText().toString();

                    // cogemos el id que ya existia
                    String id =d.getKey();
                    //

                    Usuario u=new Usuario(nombre,apellido,correo,id);


                    //creamos un usuario con la misma id para que el otro se borre
                    DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
                    bbdd.child(id).setValue(u);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sacarDatos() {
        DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Usuario> lista_usuarios = new ArrayList<>();
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    Usuario u = d.getValue(Usuario.class);
                    Log.v("datos",u.getNombre());
                    lista_usuarios.add(u);

                }
                adaptador=new AdaptadorLista(lista_usuarios,context);
                l.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sacarDialog() {
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setTitle("Añade los puntos");
        constructor.setMessage("Quieres añadir esta localizacion");
        LayoutInflater inflador=LayoutInflater.from(this);

        final View vista=inflador.inflate(R.layout.alert,null);
        constructor.setView(vista);

        final EditText et_nombre = vista.findViewById(R.id.et_nombre);
        final EditText et_apellido = vista.findViewById(R.id.et_apellido);
        final EditText et_correo = vista.findViewById(R.id.et_correo);



        constructor.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //referencia de la base de datos
                DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
                //generamos el id
                String id = bbdd.push().getKey(); //Generar clave


                String nombre = et_nombre.getText().toString();
                String apellido = et_apellido.getText().toString();
                String correo = et_correo.getText().toString();

                Usuario U = new Usuario(nombre,apellido, correo,id);


                bbdd.child(id).setValue(U);

            }
        });
        constructor.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = constructor.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
