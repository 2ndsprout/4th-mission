package com.example.mission_.note.note;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByNotebookId (Long notebookId);
    List<Note> findByNotebookIdOrderByCreateDateDesc(Long notebookId);
    List<Note> findByNotebookIdOrderByTitleAsc(Long notebookId);

}
