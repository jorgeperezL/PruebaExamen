package com.example.pruebaexamen;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdaptadorLista extends BaseAdapter {
    ArrayList<Usuario> lista_madrid;
    Context contexto;

    public AdaptadorLista(ArrayList<Usuario> lista_madrid, Context contexto) {
        this.lista_madrid= lista_madrid;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return lista_madrid.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_madrid.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(contexto);
        View vista= inflater.inflate(R.layout.celdas, parent, false);
        TextView tv_nombre=(TextView)vista.findViewById(R.id.tv_nombre);
        TextView tv_apellido=(TextView)vista.findViewById(R.id.tv_apellido);
        TextView tv_correo=(TextView)vista.findViewById(R.id.tv_correo);
        Button btn_borrar = (Button) vista.findViewById(R.id.bt_borrar);
        tv_nombre.setText(lista_madrid.get(position).getNombre());
        tv_apellido.setText(lista_madrid.get(position).getApellido());
        tv_correo.setText(lista_madrid.get(position).getCorreo());


        View.OnClickListener borrar = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference bbdd = FirebaseDatabase.getInstance().getReference("Usuarios").child(lista_madrid.get(position).getId());
                bbdd.removeValue();

            }
        };

        btn_borrar.setOnClickListener(borrar);

        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return vista;
    }
}
