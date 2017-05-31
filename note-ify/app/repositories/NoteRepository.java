package repositories;

import models.Note;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.05.2017
 */
public class NoteRepository extends GenericEbeanRepository<Integer, Note> {
    public NoteRepository() {
        this(Note.class);
    }
    public NoteRepository(Class<Note> clazz) {
        super(clazz);
    }

    public int getNumberOfRoots() {
        return this.finder.query().where().isNull("parent").findCount();
    }

    public int getNumberOfLeaves() {
//        return this.finder.query().where().notIn("id",
//                this.finder.query().fetch("parent").where().isNotNull("parent")).findCount();
        return 0;
    }

    public int getNumberOfNotes() {
        return this.finder.query().findCount();
    }

}
