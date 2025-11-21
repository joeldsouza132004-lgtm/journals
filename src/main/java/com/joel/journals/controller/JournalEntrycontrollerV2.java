package com.joel.journals.controller;

import com.joel.journals.entity.JornalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntrycontrollerV2 {

    private Map<Long, JornalEntry> jornalentries =new HashMap<>();

    @GetMapping
    public List<JornalEntry> getAll() {
        return null;
    }

    @PostMapping
    public boolean createEntry(@RequestBody JornalEntry myentry) {
        jornalentries.put(myentry.getId(), myentry);
        return true;
    }

    @GetMapping("/id/{myid}")
    public JornalEntry getByid(@PathVariable Long myid){
        return null;
    }

    @DeleteMapping("/{myid}")
    public boolean deleteEntry(@PathVariable Long myid){
        return true;
    }

    @PutMapping("/{id}")
    public JornalEntry upadteEntry(@PathVariable Long id,@RequestBody JornalEntry myentry){
        return null;

    }

}
