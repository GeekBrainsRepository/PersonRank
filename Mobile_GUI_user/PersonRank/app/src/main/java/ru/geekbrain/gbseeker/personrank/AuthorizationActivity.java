package ru.geekbrain.gbseeker.personrank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.geekbrain.gbseeker.personrank.net.RestAPI;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;


public class AuthorizationActivity extends AppCompatActivity {
    EditText loginText;
    EditText passwdText;
    Button enterButton;
    Button regisrationButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorisation_activity);

        loginText = (EditText) findViewById(R.id.login);
        passwdText = (EditText) findViewById(R.id.password);
        enterButton = (Button) findViewById(R.id.enter);
        //regisrationButton = (Button) findViewById(R.id.registration);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runAuthentication();
            }
        });
        /*regisrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRegistration();
            }
        });*/
    }

    public void runAuthentication() {
        String login = loginText.getText().toString();
        String pass = passwdText.getText().toString();
        RestAPI.authentication(new Authorization(this), login, pass);
    }
    public void runRegistration() {
        String login = loginText.getText().toString();
        String pass = passwdText.getText().toString();
        RestAPI.registration(new Authorization(this), login, pass);
    }
    public void clean() {
        loginText.setText("");
        passwdText.setText("");
    }

    public void successfulEnter(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

class Authorization implements iNet2SQL {
    Boolean isAuthorizationOk=false;
    Activity activity;

    public Authorization(Activity activity) {
        this.activity=activity;
    }

    @Override
    public void init() {
    }

    @Override
    public String getInfo() {
        return "Authorization";
    }

    @Override
    public void updateUI() {
        if(isAuthorizationOk){
            ((AuthorizationActivity)activity).successfulEnter();
        }
        else{
            Toast.makeText(activity,"Неправильный логин или пароль. Попробуйте еще раз.",Toast.LENGTH_SHORT).show();
            ((AuthorizationActivity)activity).clean();
        }
    }

    @Override
    public void updateDB(String json, String param) {
        if(json.equalsIgnoreCase("true")){
            isAuthorizationOk=true;
        }
    }
}
