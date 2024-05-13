package com.example.mission_.note.note;


import com.example.mission_.note.MainDataDto;
import com.example.mission_.note.MainService;
import com.example.mission_.note.notebook.Notebook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{notebookId}/notes")
public class NoteController {

    private final NoteService noteService;
    private final MainService mainService;

    @PostMapping("/write")
    public String write(@PathVariable Long notebookId) {

        Notebook notebook = this.mainService.getNotebook(notebookId);
        this.mainService.addToNote(notebook);

        return "redirect:/books/%d".formatted(notebookId);
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long notebookId,
                         @PathVariable Long id,
                         String keyword, Model model) {

        MainDataDto mainDataDto = this.mainService.getMainData(notebookId, id, keyword);

        model.addAttribute("mainDataDto", mainDataDto);

        return "main";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long notebookId,
                         @PathVariable Long id, String title, String content) {

     Note note = this.noteService.getNote(id);
     this.noteService.update(note, title, content);

     return "redirect:/books/%d/notes/%d".formatted(notebookId, id);

    }

    @PostMapping("/{id}/delete")
    public String delete (@PathVariable Long notebookId,
                          @PathVariable Long id) {

        Note note = this.noteService.getNote(id);
        this.noteService.delete(note);

        return "redirect:/books/%d".formatted(notebookId);
    }
}
