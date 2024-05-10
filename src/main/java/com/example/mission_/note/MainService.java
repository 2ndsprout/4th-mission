package com.example.mission_.note;


import com.example.mission_.note.note.Note;
import com.example.mission_.note.note.NoteService;
import com.example.mission_.note.notebook.Notebook;
import com.example.mission_.note.notebook.NotebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final NoteService noteService;
    private final NotebookService notebookService;

    public Notebook getNotebook (Long notebookId) {
        return this.notebookService.getNotebook(notebookId);
    }

    public void notebookSetNote (Notebook notebook) {

        Note note = this.noteService.saveDefault();
        notebook.addNote(note);
        this.notebookService.save(notebook);
    }

    public List<Notebook> getNotebookList () {
        return this.notebookService.getList();
    }
    public List<Notebook> getTopNotebookList () {
        return this.notebookService.getTopNotebookList();
    }

    public MainDataDto getDefaultMainData () {

        List<Notebook> notebookList = this.getNotebookList();

        if (notebookList.isEmpty()) {

            Notebook notebook = this.notebookService.saveDefault();
            this.notebookSetNote(notebook);
        }

        List<Notebook> topNotebookList = this.getTopNotebookList();
        Notebook targetNotebook = notebookList.getLast();
        List<Note> noteList = targetNotebook.getNoteList();
        Note targetNote = noteList.getLast();


        return new MainDataDto(topNotebookList, targetNotebook, noteList, targetNote);
    }

    public MainDataDto getMainData (Long notebookId, Long noteId) {

        MainDataDto mainDataDto = this.getDefaultMainData();

        Notebook targetNotebook = this.getNotebook(notebookId);
        List<Note> noteList = targetNotebook.getNoteList();
        Note targetNote = this.noteService.getNote(noteId);

        mainDataDto.setTargetNotebook(targetNotebook);
        mainDataDto.setNoteList(noteList);
        mainDataDto.setTargetNote(targetNote);

        return mainDataDto;

    }


    public Notebook addToChild (Notebook parent) {

        Notebook child = this.notebookService.saveDefault();
        this.notebookSetNote(child);

        parent.addChild(child);
        return this.notebookService.save(parent);

    }

}
