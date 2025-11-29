package com.example.JournalApp.contoller;

import com.example.JournalApp.Entity.JournalEntry;
import com.example.JournalApp.Entity.User;
import com.example.JournalApp.service.JournalEntryService;
import com.example.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        List<JournalEntry> entries = user.getJournalEntries();
        if (entries != null && !entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    @PostMapping({"{userName}"})
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable("myId") ObjectId id, @RequestBody JournalEntry newEntry) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        if(journalEntry.isPresent()) {
            JournalEntry existingEntry = journalEntry.get();
            if (newEntry.getTitle() != null && !newEntry.getTitle().trim().isEmpty()) {
                existingEntry.setTitle(newEntry.getTitle().trim());
            }
            if (newEntry.getContent() != null && !newEntry.getContent().toString().trim().isEmpty()) {
                existingEntry.setContent(newEntry.getContent());
            }
            String userName = existingEntry.getUser() != null ? existingEntry.getUser().getUsername() : "";
            JournalEntry updatedEntry = journalEntryService.saveEntry(existingEntry, userName);
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
