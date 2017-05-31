package controllers;

import controllers.dtos.NoteDTO;
import models.Note;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ComponentFactory;
import services.GenericService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.05.2017
 */
public class NoteController extends Controller {

    private GenericService<Integer, Note> noteService =
            ComponentFactory.getGenericFactory(Note.class).getService();

    public Result getAll() {
        List<Note> notes = noteService.getAll();
        List<NoteDTO> dtos = new ArrayList<>();
        for (Note note : notes) {
            dtos.add(new NoteDTO(note));
        }
        return ok(Json.toJson(dtos));
    }

    public Result get(Integer id) {
        return ok(Json.toJson(new NoteDTO(noteService.findById(id))));
    }

    public Result add() {
        Note note = Json.fromJson(request().body().asJson(), Note.class);
        return ok(Json.toJson(new NoteDTO(noteService.addOrUpdate(note))));
    }

    public Result update(Integer id) {
        Note note = Json.fromJson(request().body().asJson(), Note.class);
        note.setId(id);
        return ok(Json.toJson(new NoteDTO(noteService.addOrUpdate(note))));
    }

    public Result delete(Integer id) {
        noteService.deleteById(id);
        return ok();
    }

}
