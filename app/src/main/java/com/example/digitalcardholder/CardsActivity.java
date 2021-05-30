package com.example.digitalcardholder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class CardsActivity extends AppCompatActivity {
    String databaseName = "DigitalCardHolder";
    int DATABASE_VERSION = 1;
    RecyclerView r;
    CardDetailsAdapter adapter;
    ArrayList<CardDetails> list = new ArrayList<>();
    ExtendedFloatingActionButton efab;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);
        getSupportActionBar().hide();
        r = findViewById(R.id.id_recycler_view_cards);
        efab = findViewById(R.id.id_btn_add_new_card);
        db = new DatabaseHelper(this,databaseName,null,DATABASE_VERSION);
        displayCards();
        efab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCardDetails();
            }
        });
    }
    public void displayCards(){
        list = db.getTableValues();
        adapter = new CardDetailsAdapter(list);
        r.setLayoutManager(new LinearLayoutManager(this));
        r.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");
        builder.setTitle("Alert!");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void addCardDetails(){
        View view = LayoutInflater.from(this).inflate(R.layout.card_details_entry,null);
        AlertDialog a = new AlertDialog.Builder(this).create();
        a.setView(view);
        a.setCancelable(false);
        a.show();
        view.findViewById(R.id.id_btn_ok_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cnameEd = view.findViewById(R.id.id_card_name_entry);
                EditText cnumberEd = view.findViewById(R.id.id_card_number_entry);
                EditText chnameEd = view.findViewById(R.id.id_card_holder_name_entry);
                EditText cexpiryEd = view.findViewById(R.id.id_card_expiry_entry);
                EditText cardcvvEd = view.findViewById(R.id.id_card_cvv_entry);
                String cardName = cnameEd.getText().toString();
                String cardNumber = cnumberEd.getText().toString();
                String cardHolderName = chnameEd.getText().toString();
                String cardExpiry = cexpiryEd.getText().toString();
                String cardCVV = cardcvvEd.getText().toString();
                int imageId = R.drawable.card_shape_1;
                if(cardName.isEmpty()){
                    cnameEd.setError("Name must be unique and not be empty");
                }
                if(cardNumber.isEmpty()){
                    cnumberEd.setError("Card Number cannot be empty");
                }
                if(cardExpiry.isEmpty()){
                    cexpiryEd.setError("Card Expiry cannot be empty");
                }
                if(cardCVV.isEmpty()){
                    cardcvvEd.setError("Card CVV cannot be empty");
                }
                if(cardNumber.length()<16){
                    cnumberEd.setError("Please enter correct card number");
                }
                if(cardCVV.length()<3){
                    cardcvvEd.setError("Please enter correct CVV");
                }
                if(cardExpiry.charAt(2)=='/' && cardExpiry.length()==5)
                {
                    cexpiryEd.setError("Please enter expiry in MM/YY format");
                }
                if(cardExpiry.length()<5){
                    cexpiryEd.setError("Please enter correct expiry date");
                }
                if(!(cardName.isEmpty()) && !(cardNumber).isEmpty() && !(cardExpiry).isEmpty() && !(cardCVV.isEmpty()) && cardNumber.length()==16 && cardExpiry.length() == 5 && cardCVV.length()==3)
                {
                    db.addValuesInTable(imageId,
                            cardName,
                            cardNumber,
                            cardHolderName,
                            cardExpiry,
                            cardCVV);
                    Toast.makeText(CardsActivity.this, "Card Added Successfully", Toast.LENGTH_LONG).show();
                    displayCards();
                    a.cancel();
                }
                else{
                    Toast.makeText(CardsActivity.this, "Card Not Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.id_btn_cancel_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.cancel();
            }
        });
    }
}