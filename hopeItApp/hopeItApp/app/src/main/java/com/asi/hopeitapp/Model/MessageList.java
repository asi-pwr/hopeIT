
package com.asi.hopeitapp.Model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageList implements Serializable
{

    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    private final static long serialVersionUID = -725809749578213993L;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

}
