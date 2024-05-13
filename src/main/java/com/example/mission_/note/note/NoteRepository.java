package com.example.mission_.note.note;

import com.example.mission_.note.notebook.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTitleContaining(String title);

    List<Note> findByNotebookOrderByCreateDateDesc(Notebook targetNotebook);

    List<Note> findByNotebookOrderByTitle(Notebook targetNotebook);
}
