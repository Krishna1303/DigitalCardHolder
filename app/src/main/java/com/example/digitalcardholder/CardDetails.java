package com.example.digitalcardholder;

public class CardDetails {
    int cardImageId;
    String cardName,cardNumber,cardHolderName,cardExpiryDate,cardCVV;

    public CardDetails(int cardImageId, String cardName, String cardNumber, String cardHolderName, String cardExpiryDate, String cardCVV) {
        this.cardImageId = cardImageId;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardExpiryDate = cardExpiryDate;
        this.cardCVV = cardCVV;
    }

    public int getCardImageId() {
        return cardImageId;
    }

    public void setCardImageId(int cardImageId) {
        this.cardImageId = cardImageId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }
}
