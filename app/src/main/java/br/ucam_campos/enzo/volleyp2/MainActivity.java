package br.ucam_campos.enzo.volleyp2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton sairFab = (FloatingActionButton) findViewById(R.id.sairFab);
        final TextView idTxt = (TextView) findViewById(R.id.idTxt);
        final TextView isSuTxt = (TextView) findViewById(R.id.isSuTxt);
        final TextView usernameTxt = (TextView) findViewById(R.id.reUsernameTxt);
        final TextView firstNameTxt = (TextView) findViewById(R.id.reFirstNameTxt);
        final TextView lastNameTxt = (TextView) findViewById(R.id.reLastNameTxt);
        final TextView emailTxt = (TextView) findViewById(R.id.reEmailTxt);
        final TextView nascimentoTxt = (TextView) findViewById(R.id.reNascimentoTxt);
        final TextView telefoneTxt = (TextView) findViewById(R.id.reTelefoneTxt);
        final TextView enderecoTxt = (TextView) findViewById(R.id.reEnderecoTxt);
        final TextView statusTxt = (TextView) findViewById(R.id.statusTxt);

        Intent i = getIntent();
        Usuario usuario = (Usuario) i.getSerializableExtra("usuario");

        idTxt.setText("Id: "+usuario.getId());
        isSuTxt.setText("Superuser: "+usuario.isSu());
        usernameTxt.setText("Username: "+usuario.getUsername());
        firstNameTxt.setText("Nome: "+usuario.getFirstName());
        lastNameTxt.setText("Sobrenome: "+usuario.getLastName());
        emailTxt.setText("Email: "+usuario.getEmail());
        nascimentoTxt.setText("Nascimento: "+usuario.getNascimento());
        telefoneTxt.setText("Telefone: "+usuario.getTelefone());
        enderecoTxt.setText("Endereço: "+usuario.getEndereco());
        statusTxt.setText("Status: "+usuario.getStatus());

        sairFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

