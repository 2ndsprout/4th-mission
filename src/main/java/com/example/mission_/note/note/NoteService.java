package com.example.mission_.note.note;

import com.example.mission_.note.notebook.Notebook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public Note saveDefault () {

        Note note = new Note();

        note.setTitle("newTitle...");
        note.setContent("");
        note.setCreateDate(LocalDateTime.now());
        return this.noteRepository.save(note);
    }
    public List<Note> getList () {
        return this.noteRepository.findAll();
    }
    public Note getNote (Long id) {
        Optional<Note> note = this.noteRepository.findById(id);
        if (note.isPresent()) {
            return note.get();
        }
        else {
            throw new RuntimeException("note not found");
        }
    }
    public void update (Note note, String title, String content) {

        if (title.trim().isEmpty()) {
            title = "제목없음";
        }
        if (content.trim().isEmpty()) {
            content = "내용없음";
        }
        note.setTitle(title);
        note.setContent(content);
        this.noteRepository.save(note);
    }
    public void delete (Note note) {
        this.noteRepository.delete(note);
    }

    public List<Note> getSearchedNoteList(String keyword) {
        return this.noteRepository.findByTitleContaining(keyword);
    }

    public List<Note> getSortedListByCreateDate(Notebook targetNotebook) {

        return this.noteRepository.findByNotebookOrderByCreateDateDesc(targetNotebook);
    }

    public List<Note> getSortedNoteListByTitle(Notebook targetNotebook) {

        return this.noteRepository.findByNotebookOrderByTitle(targetNotebook);
    }
}
