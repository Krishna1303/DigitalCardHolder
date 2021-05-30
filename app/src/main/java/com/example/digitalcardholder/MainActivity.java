package com.example.digitalcardholder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String s = "";
    SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("AppPassword",MODE_PRIVATE);
        EditText e = findViewById(R.id.id_input_password);
        TextView t = findViewById(R.id.id_register_user);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        e.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction()==KeyEvent.ACTION_DOWN)&&(keyCode==KeyEvent.KEYCODE_ENTER))
                {
                  s = e.getText().toString();
                  if(s.length()<4)
                  {
                      e.setError("Password must be of 4 numbers");
                  }
                  else
                    checkUser(s);
                  return true;
                }
                else
                {
                    return false;
                }
            }
        });
    }
    public void checkUser(String s){
        Boolean existingUser = sharedPreferences.getBoolean("ExistingUser",false);
        if(existingUser){
            String password_from_sp = sharedPreferences.getString("Password","0");
            if(password_from_sp.equals(s)){
                startActivity(new Intent(this,CardsActivity.class));
            }
            else
                Toast.makeText(this, "Wrong Password", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "Please Register First", Toast.LENGTH_LONG).show();
        }
    }
    public void registerUser(){
        View view = LayoutInflater.from(this).inflate(R.layout.register_user,null);
        AlertDialog a = new AlertDialog.Builder(this).create();
        a.setView(view);
        a.setCancelable(false);
        a.show();
        Button b = view.findViewById(R.id.id_register_user_ok_btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e1 = view.findViewById(R.id.id_register_user_password);
                EditText e2 = view.findViewById(R.id.id_register_user_confirm_password);
                String password = e1.getText().toString();
                String confirmPassword = e2.getText().toString();
                if(password.isEmpty())
                    e1.setError("Password cannot be empty");
                if(confirmPassword.isEmpty())
                    e2.setError("Confirm Password cannot be empty");
                if (password.length() < 4 )
                    e1.setError("Password length must be 4");
                if(confirmPassword.length()<4)
                    e2.setError("Both passwords must match");
                if(password.equals(confirmPassword) && !(password.isEmpty()) && !(confirmPassword.isEmpty()) ) {
                    sharedPreferences.edit().putString("Password", password).apply();
                    sharedPreferences.edit().putBoolean("ExistingUser",true).apply();
                    Toast.makeText(MainActivity.this, "Password Created Successfully", Toast.LENGTH_LONG).show();
                    a.cancel();
                }
            }
        });
        view.findViewById(R.id.id_register_user_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.cancel();
            }
        });
    }
}