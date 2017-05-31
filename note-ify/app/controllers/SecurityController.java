package controllers;

import controllers.security.LoginCredentials;
import controllers.security.SecurityTools;
import models.Account;
import services.AccountService;
import services.ComponentFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @Author: Voicu Hodrea
 * @Date: 29.05.2017
 */
public class SecurityController extends Controller {

    private AccountService accountService = ComponentFactory.getAccountService();

    public Result login() {
        LoginCredentials credentials = Json.fromJson(request().body().asJson(), LoginCredentials.class);
        Account account = accountService.tryToLogIn(credentials.getUsername(), credentials.getPassword());
        if (account != null) {
            SecurityTools.Authenticator.setToken(ctx(), account.getToken());
            return ok(Json.toJson(account));
        }
        return ok();
    }

    public Result getLoggedIn() {
        String token = SecurityTools.Authenticator.getToken(ctx());
        if (token == null) {
            return unauthorized();
        }
        Account account = accountService.findLoggedIn(token);
        if (account != null) {
            return ok(Json.toJson(account));
        } else {
            return unauthorized();
        }
    }

    public Result logout() {
        SecurityTools.Authenticator.setToken(ctx(), "");
        return ok();
    }

}
