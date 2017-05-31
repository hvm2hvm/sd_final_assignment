package controllers;

import controllers.dtos.StatisticsDTO;
import controllers.security.SecurityTools;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ComponentFactory;
import services.NoteService;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.05.2017
 */
@SecurityTools.AuthenticatedWithPermission(permission = "stats")
public class StatisticsController extends Controller {

    private NoteService noteService = ComponentFactory.getNoteService();

    public Result get() {
        StatisticsDTO dto = new StatisticsDTO();
        dto.setNumberOfRoots(noteService.getNumberOfRoots());
        dto.setNumberOfLeaves(noteService.getNumberOfLeaves());
        dto.setNumberOfNotes(noteService.getNumberOfNotes());
        return ok(Json.toJson(dto));
    }

}
