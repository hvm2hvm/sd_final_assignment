package services;

import models.Note;
import repositories.NoteRepository;
import repositories.Repository;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.05.2017
 */
public class NoteService extends GenericService<Integer, Note> {

    public NoteService(Repository<Integer, Note> repository) {
        super(repository);
    }

    public int getNumberOfRoots() {
        return ((NoteRepository)repository).getNumberOfRoots();
    }

    public int getNumberOfLeaves() {
        return ((NoteRepository)repository).getNumberOfLeaves();
    }

    public int getNumberOfNotes() {
        return ((NoteRepository)repository).getNumberOfNotes();
    }

}
