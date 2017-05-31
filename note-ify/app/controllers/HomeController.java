package controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @Author: Voicu Hodrea
 * @Date: 30.05.2017
 */
public class HomeController extends Controller {

    public Result index() {
        return ok(views.html.index.render());
    }
}
