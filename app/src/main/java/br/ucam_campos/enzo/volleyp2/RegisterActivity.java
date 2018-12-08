package br.ucam_campos.enzo.volleyp2;
import org.apache.commons.lang3.StringUtils;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Declaração de variaveis 
        final EditText usernameTxt = (EditText) findViewById(R.id.reUsernameTxt);
        final EditText passwordTxt = (EditText) findViewById(R.id.rePasswordTxt);
        final EditText emailTxt = (EditText) findViewById(R.id.reEmailTxt);
        final EditText firstNameTxt = (EditText) findViewById(R.id.reFirstNameTxt);
        final EditText lastNameTxt = (EditText) findViewById(R.id.reLastNameTxt);
        final EditText nascimentoTxt = (EditText) findViewById(R.id.reNascimentoTxt);
        final EditText telefoneTxt = (EditText) findViewById(R.id.reTelefoneTxt);
        final EditText enderecoTxt = (EditText) findViewById(R.id.reEnderecoTxt);
        final EditText statusTxt = (EditText) findViewById(R.id.statusTxt);
        final ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.bounds);
        final Button btnRegistrar = findViewById(R.id.reBtnRegistrar);
        final String url = "https://djangorest-androidapi.herokuapp.com/api/usuarios/";
        final RequestQueue fila = Volley.newRequestQueue(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Valida o formulario antes de enviar a requisicao
                if(usernameTxt.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Nome de usuário é obrigatório", Toast.LENGTH_LONG).show();
                }
                else if(usernameTxt.getText().toString().length() < 4){
                    Toast.makeText(getApplicationContext(), "Nome de usuário deve ter no mínimo 4 car.", Toast.LENGTH_LONG).show();
                }
                else if (passwordTxt.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Senha é obrigatória", Toast.LENGTH_LONG).show();
                }
                else if (passwordTxt.getText().toString().length() < 8) {
                    Toast.makeText(getApplicationContext(), "Senha deve ter no mínimo 8 car.", Toast.LENGTH_LONG).show();
                }
                else if((statusTxt.getText().toString().equals("")) || ((Integer.parseInt(statusTxt.getText().toString())) < 0)  || (Integer.parseInt(statusTxt.getText().toString())) > 2){
                    Snackbar.make(v, "Campo status só aceita os valores 0, 1 ou 2.", Snackbar.LENGTH_LONG).show();
                }
                else if((nascimentoTxt.getText().toString().trim().length() > 0) && ((nascimentoTxt.getText().toString().trim().length() < 10) || (StringUtils.countMatches(nascimentoTxt.getText().toString(), "-")) != 2)){
                    Snackbar.make(v, "Valor incorreto para nascimento. \nFormato deve ser AAAA-MM-DD", Snackbar.LENGTH_LONG).show();
                }
                else{
                    //Constroi o JSON a partir dos campos que contém informações
                    JSONObject data = new JSONObject();
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View view = layout.getChildAt(i);
                        if (view instanceof EditText) {
                            if(!(((EditText) view).getText().toString().equals(""))){
                                try{
                                    data.put(view.getTag().toString(),((EditText) view).getText().toString());
                                } catch (JSONException je){
                                    Log.v("data put exception", je.toString());
                                }
                            }
                        }
                    }

                    //Requisição POST
                    JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            //Sucesso na requisicao
                            Toast.makeText(getApplicationContext(), "Registrado com sucesso!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Exibe o erro na forma de snackbar
                            Snackbar.make(v, error.toString(), Snackbar.LENGTH_LONG).show();
                        }
                    })

                    {
                        //Define headers
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            return headers;
                        }

                    };
                    fila.add(jsonReq);

                }
            }
        });
    }
}