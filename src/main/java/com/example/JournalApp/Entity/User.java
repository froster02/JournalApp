package com.example.JournalApp.Entity;

import com.mongodb.lang.NonNull;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();


    public String getUserName() {
        return this.username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    public void setUserPassword(String userPassword) {
        this.password = userPassword;
    }

    public void setPassword(String password) { this.password = password; }

    public String getPassword() {
        return this.password;
    }
    
    public List<JournalEntry> getJournalEntries() {
        return this.journalEntries;
    }
}
