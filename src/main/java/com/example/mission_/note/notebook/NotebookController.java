package com.example.mission_.note.notebook;


import com.example.mission_.note.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NotebookController {

    private final MainService mainService;
    private final NotebookService notebookService;

    @PostMapping("/books/write")
    public String write () {

        Notebook notebook = this.notebookService.saveDefault();
        this.mainService.notebookSetNote(notebook);

        return "redirect:/";
    }

    @PostMapping("/groups/books/{notebookId}/write")
    public String groupWrite (@PathVariable Long notebookId) {

        Notebook parent = this.notebookService.getNotebook(notebookId);

        this.mainService.addToChild(parent);

        return "redirect:/books/%d".formatted(parent.getChildren().getLast().getId());
    }

    @GetMapping("/books/{id}")
    public String detail (@PathVariable Long id, Model model) {

        Notebook notebook = this.notebookService.getNotebook(id);
        if (notebook.getNoteList().isEmpty()) {
            this.mainService.notebookSetNote(notebook);
        }
        Long noteId = notebook.getNoteList().getLast().getId();

        return "redirect:/books/%d/notes/%d".formatted(id, noteId);
    }

    @GetMapping("/books/{id}/update")
    public String update (@PathVariable Long id, Long targetNoteId, String name) {

        Notebook notebook = this.notebookService.getNotebook(id);
        this.notebookService.update(notebook, name);

        return "redirect:/books/%d/notes/%d".formatted(id,targetNoteId);
    }

    @PostMapping("/books/{id}/delete")
    public String delete (@PathVariable Long id) {

        Notebook notebook = this.notebookService.getNotebook(id);
        this.notebookService.delete(notebook);

        return "redirect:/";
    }
    @PostMapping("/books/{id}/move")
    public String move (@PathVariable Long id, Long destinationId, Long targetNoteId) {

        if (destinationId == null) {
            return "redirect:/books/%d/notes/%d".formatted(id,targetNoteId);
        }

        Notebook notebook = this.notebookService.getNotebook(id);
        this.notebookService.move(notebook, destinationId);

        return "redirect:/books/%d/notes/%d".formatted(id,targetNoteId);
    }
}
