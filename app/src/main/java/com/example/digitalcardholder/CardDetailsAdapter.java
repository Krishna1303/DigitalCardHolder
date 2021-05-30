package com.example.digitalcardholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardDetailsAdapter extends RecyclerView.Adapter<CardDetailsAdapter.CardDetailsViewHolder>{
    String databaseName = "DigitalCardHolder";
    int DATABASE_VERSION = 1;
    DatabaseHelper db;
    @NonNull
    ArrayList<CardDetails> mList = new ArrayList<>();

    public CardDetailsAdapter(@NonNull ArrayList<CardDetails> mList) {
        this.mList = mList;
    }

    public CardDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_view,parent,false);
        return new CardDetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardDetailsViewHolder holder, int position) {
        holder.cardImage.setImageResource(mList.get(position).getCardImageId());
        holder.cardName.setText(mList.get(position).getCardName());
        holder.cardNumber.setText(createCardNumberFormat(mList.get(position).getCardNumber()));
        holder.cardHolderName.setText(mList.get(position).getCardHolderName());
        holder.cardName.setText(mList.get(position).getCardName());
        holder.cardExpiryDate.setText(mList.get(position).getCardExpiryDate());
        holder.cardCVV.setText(mList.get(position).getCardCVV());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteCard(mList.get(position).getCardNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CardDetailsViewHolder extends RecyclerView.ViewHolder{
        ImageView cardImage;
        TextView cardName;
        TextView cardNumber;
        TextView cardHolderName;
        TextView cardExpiryDate;
        TextView cardCVV;
        Button btn_delete;
        public CardDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.id_image_view_card);
            cardName = itemView.findViewById(R.id.id_card_name);
            cardNumber = itemView.findViewById(R.id.id_card_number);
            cardHolderName = itemView.findViewById(R.id.id_card_holder_name);
            cardExpiryDate = itemView.findViewById(R.id.id_expiry_date);
            cardCVV = itemView.findViewById(R.id.id_card_cvv);
            btn_delete =itemView.findViewById(R.id.id_delete_btn);
            db = new DatabaseHelper(itemView.getContext(),databaseName,null,DATABASE_VERSION);
        }
    }
    public String createCardNumberFormat(String s){
        String res = s.substring(0,4) + " " + s.substring(4,8) + " " + s.substring(8,12) + " "+ s.substring(12);
        return res;
    }
}
