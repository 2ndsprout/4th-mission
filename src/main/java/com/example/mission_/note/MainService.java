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

    private final NotebookService notebookService;
    private final NoteService noteService;

    public Notebook getNotebook (Long notebookId) {

        return this.notebookService.getNotebook(notebookId);

    }

    public Notebook addToNote (Notebook notebook) {

        Note note = this.noteService.saveDefault();
        notebook.addNote(note);
        return this.notebookService.save(notebook);
    }

    public MainDataDto getDefaultMainData (String keyword) {

        List<Notebook> notebookList = this.notebookService.getTopNotebookList();
        if (notebookList.isEmpty()) {

            Notebook notebook = this.notebookService.saveDefault();

            this.addToNote(notebook);
        }
        Notebook targetNotebook = notebookList.getLast();
        List<Note> noteList = targetNotebook.getNoteList();
        Note targetNote = noteList.getLast();

        List<Notebook> searchedNotebookList = this.notebookService.getSearchedNotebookList(keyword);
        List<Note> searchedNoteList = this.noteService.getSearchedNoteList(keyword);

        return new MainDataDto(notebookList,targetNotebook, noteList, targetNote, searchedNotebookList, searchedNoteList);

    }

    public MainDataDto getMainData (Long notebookId, Long noteId, String keyword, String sort) {

        MainDataDto mainDataDto = this.getDefaultMainData(keyword);

        Notebook targetNotebook = this.notebookService.getNotebook(notebookId);
        Note targetNote = this.noteService.getNote(noteId);

        mainDataDto.setTargetNotebook(targetNotebook);
        // mainDataDto.setNoteList(targetNotebook.getNoteList());
        mainDataDto.setTargetNote(targetNote);

        List<Note> sortedNoteList;

        if (sort.equals("date")) {
            sortedNoteList = this.noteService.getSortedListByCreateDate(targetNotebook);
        }
        else if (sort.equals("title")) {
            sortedNoteList = this.noteService.getSortedNoteListByTitle(targetNotebook);
        }
        else {
            sortedNoteList = targetNotebook.getNoteList();
        }
        mainDataDto.setNoteList(sortedNoteList);

        return mainDataDto;
    }


}
