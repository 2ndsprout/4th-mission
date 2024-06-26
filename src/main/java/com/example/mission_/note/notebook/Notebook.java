package com.example.mission_.note.notebook;


import com.example.mission_.note.note.Note;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Notebook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Notebook parent;

    @OneToMany(mappedBy = "notebook",cascade = CascadeType.REMOVE)
    private List<Note> noteList = new ArrayList<>();

    @OneToMany(mappedBy = "parent",cascade = CascadeType.REMOVE)
    private List<Notebook> children = new ArrayList<>();

    public void addNote (Note note) {
        note.setNotebook(this);
        noteList.add(note);
    }
    public void addChild (Notebook child) {
        child.setParent(this);
        children.add(child);
    }
}
