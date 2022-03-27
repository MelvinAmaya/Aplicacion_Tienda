package com.example.aplicacion_tienda;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class pagina1 extends AppCompatActivity {
    EditText nombre,direccion,pedido;
    Button enviar,limpiar;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina1);
        nombre = (EditText) findViewById(R.id.txtnombre);
        direccion = (EditText) findViewById(R.id.txtDireccion);
        pedido = (EditText) findViewById(R.id.txtpedido);
        enviar=(Button) findViewById(R.id.btnEnviar);
        limpiar=(Button) findViewById(R.id.btnLimpiar);

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre.getText().clear();
                direccion.getText().clear();
                pedido.getText().clear();
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String nombres = nombre.getText().toString();
                    String direccionn = direccion.getText().toString();
                    String pedidoo = pedido.getText().toString();
                    new enviardatos(pagina1.this).execute(nombres,direccionn,pedidoo);
            }
        });
    }

    public static class enviardatos extends AsyncTask<String,Void,String>{

        private WeakReference<Context> context;

        public enviardatos(Context context){
            this.context = new WeakReference<>(context);
        }

       protected String doInBackground(String... params){
            String jsonurl = "https://melvinprueba100.000webhostapp.com/jsonprueba/Insert.php";
            String resultado;

            try{
                URL url = new URL(jsonurl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String nom = params[0];
                String direcc = params[1];
                String pedi = params[2];

                String data = URLEncoder.encode("cliente","UTF-8")+"="+URLEncoder.encode(nom,"UTF-8")+"&"+URLEncoder.encode("direccio","UTF-8")+"="+URLEncoder.encode(direcc,"UTF-8")+"&"+URLEncoder.encode("ped","UTF-8")+"="+URLEncoder.encode(pedi,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine())!=null){ stringBuilder.append(line);
                }
                resultado = stringBuilder.toString();

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                Log.d("MIAPP","Se esta utilizando una direccion de json incorrecta");
                resultado="Se a producido un error";
            } catch (IOException e) {
                Log.d("MIAPP","Error inesperado!, posible problema de conexion de red.");
                resultado="Se a producido un error, comprueba tu conexion";
            }

            return resultado;
       }
       protected void onPostExecute(String resultado){
           Toast.makeText(context.get(), resultado, Toast.LENGTH_SHORT).show();
       }
    }

}