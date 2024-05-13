package com.example.mission_.note.notebook;


import com.example.mission_.note.MainService;
import com.example.mission_.note.ParamHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class NotebookController {

    private final NotebookService notebookService;
    private final MainService mainService;


    @PostMapping("/books/write")
    public String write () {

        Notebook notebook = this.notebookService.saveDefault();
        this.mainService.addToNote(notebook);

        return "redirect:/";
    }

    @PostMapping("/groups/books/{notebookId}/write")
    public String groupWrite (@PathVariable Long notebookId) {

        Notebook parent = this.notebookService.getNotebook(notebookId);

        Notebook child = this.notebookService.saveDefault();
        this.mainService.addToNote(child);

        parent.addChild(child);
        this.notebookService.save(parent);

        return "redirect:/books/%d".formatted(child.getId());
    }

    @GetMapping("/books/{id}")
    public String detail (@PathVariable Long id,
                          ParamHandler paramHandler, Model model) {

        Notebook notebook = this.notebookService.getNotebook(id);
        if (notebook.getNoteList().isEmpty()) {
            this.mainService.addToNote(notebook);
        }
        Long noteId = notebook.getNoteList().getLast().getId();

        return "redirect:/books/%d/notes/%d?keyword=%s".formatted(id, noteId, URLEncoder.encode(paramHandler.getKeyword()));
    }
    @PostMapping("/books/{id}/update")
    public String update (@PathVariable Long id, Long noteId, String name) {
        Notebook notebook = this.notebookService.getNotebook(id);
        this.notebookService.update(notebook, name);

        return "redirect:/books/%d/notes/%d".formatted(id,noteId);
    }
    @PostMapping("/books/{id}/delete")
    public String delete (@PathVariable Long id) {
        Notebook notebook = this.notebookService.getNotebook(id);

        this.notebookService.delete(notebook);

        return "redirect:/";
    }
    @PostMapping("/books/{id}/move")
    public String move (@PathVariable Long id, Long destinationId, Long targetNoteId) {

        if (destinationId==null){
            return "redirect:/books/%d/notes/%d".formatted(id,targetNoteId);
        }

        Notebook notebook = this.notebookService.getNotebook(id);
        this.notebookService.move(notebook, destinationId);

        return "redirect:/books/%d/notes/%d".formatted(id,targetNoteId);
    }
}
