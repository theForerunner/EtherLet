package com.example.l.EtherLet.model;

public class Transaction {
    private String senderAddress;
    private String receiverAddress;
    private String value;
    private String timeStamp;
    private String status;

    public Transaction(){}

    public Transaction(boolean init){
        if(!init) return;
        senderAddress="jhdsbcscldjncsldnl327e8fsdnsd";
        receiverAddress="sdcskjcnlkjdnv3872sjdfv jsd";
        value="16.00";
        timeStamp="20181212";
        status="1";
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getValue() {
        return value;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getStatus() {
        return status;
    }
}
