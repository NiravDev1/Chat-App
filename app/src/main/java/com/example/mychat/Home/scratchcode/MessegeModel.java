package com.example.mychat.Home.scratchcode;

public class MessegeModel {
    private String MsgId,senderid,messeg;

    public MessegeModel(String msgId, String senderid, String messeg) {
        MsgId = msgId;
        this.senderid = senderid;
        this.messeg = messeg;
    }

    public MessegeModel() {
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getMesseg() {
        return messeg;
    }

    public void setMesseg(String messeg) {
        this.messeg = messeg;
    }
}
