package br.ucam_campos.enzo.volleyp2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Declaração de elementos
        final EditText usernameTxt = (EditText) findViewById(R.id.username);
        final EditText passwordTxt = (EditText) findViewById(R.id.password);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final RequestQueue fila = Volley.newRequestQueue(this);
        final String url = "https://djangorest-androidapi.herokuapp.com/";

        //Listener para evento de clique
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                //Toast.makeText(getApplicationContext(), usernameTxt.getText().toString(), Toast.LENGTH_LONG).show();
                if (usernameTxt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Nome de usuário é obrigatório", Toast.LENGTH_LONG).show();
                } else if (passwordTxt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Senha é obrigatória", Toast.LENGTH_LONG).show();
                } else {
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                    JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String mensagem = "";
                            int status = -1;

                            try {
                                mensagem = response.getString("mensagem");
                                status = response.getInt("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //Caso login retorne status
                            if(status != -1){

                                if(status == 1){
                                    Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                                else{
                                    Snackbar.make(v, "Status: "+status+", "+"\n"+mensagem, Snackbar.LENGTH_LONG).show();
                                }
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Exibe o erro na forma de toast
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<>();
                            String credentials = usernameTxt.getText().toString().trim()+":"+passwordTxt.getText().toString();
                            String auth = "Basic "
                                    + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                            headers.put("Content-Type", "application/json");
                            headers.put("Authorization", auth);
                            return headers;
                        }
                    };
                    fila.add(jsonReq);
                }
            }
        });

    }
}






