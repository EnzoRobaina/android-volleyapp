package br.ucam_campos.enzo.volleyp2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements VolleyResponseListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Declaração de elementos
        final EditText usernameTxt = (EditText) findViewById(R.id.username);
        final EditText passwordTxt = (EditText) findViewById(R.id.password);
        final Button btnLogin = findViewById(R.id.btnLogin);
        final RequestQueue fila = Volley.newRequestQueue(this);

        //Listener para evento de clique
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), usernameTxt.getText().toString(), Toast.LENGTH_LONG).show();
                if (usernameTxt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Nome de usuário é obrigatório", Toast.LENGTH_LONG).show();
                } else if (passwordTxt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Senha é obrigatória", Toast.LENGTH_LONG).show();
                } else {
                    indexReq(usernameTxt.getText().toString().trim(), passwordTxt.getText().toString());
                }
            }
        });

    }

    private void indexReq(final String user, final String passwd) {
        String url = "https://djangorest-androidapi.herokuapp.com/";

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("OnResponse", "OnResponse"+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("OnError", "OnError"+error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = user+":"+passwd;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }
        };
        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(jsonReq);
    }


    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onSuccess(String response) {

    }
}






//JSONObject(String json)

/*

String url = "https://djangorest-androidapi.herokuapp.com/";
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                    fila.add(jsonObjectRequest);

 */