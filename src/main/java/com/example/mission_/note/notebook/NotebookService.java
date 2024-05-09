package com.example.mission_.note.notebook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotebookService {

    private final NotebookRepository notebookRepository;

    public List<Notebook> getList () {
        return this.notebookRepository.findAll();
    }
    public Notebook getNotebook (Long id) {
        Optional<Notebook> notebook = this.notebookRepository.findById(id);
        if (notebook.isPresent()) {
            return notebook.get();
        }
        else {
            throw new RuntimeException("notebook not found");
        }
    }
    public Notebook saveDefault () {
        Notebook notebook = new Notebook();

        notebook.setName("새노트");
        return this.notebookRepository.save(notebook);
    }
    public Notebook save (Notebook notebook) {
        return this.notebookRepository.save(notebook);
    }
    public void update (Notebook notebook, String name) {

        if (name.trim().isEmpty()) {
            name = "새 노트북";
        }
        notebook.setName(name);
        this.save(notebook);
    }
    public List<Notebook> getTopNotebookList () {
        return this.notebookRepository.findByParentIsNull();
    }

    public void delete (Notebook notebook) {
        this.notebookRepository.delete(notebook);
    }

    public Notebook move (Notebook notebook, Long destinationId) {

        Notebook destination = getNotebook(destinationId);
        notebook.setParent(destination);
        return this.save(notebook);

    }
}
