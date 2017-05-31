package controllers.security;

import com.google.inject.Inject;
import models.Account;
import services.AccountService;
import services.ComponentFactory;
import play.inject.Injector;
import play.mvc.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @Author: Voicu Hodrea
 * @Date: 23.05.2017
 */
public class SecurityTools {

    public static final String TOKEN_COOKIE_NAME = "AUTH_TOKEN";

    @With(AuthenticatedAction.class)
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AuthenticatedWithPermission {
        Class<? extends Authenticator> authenticator() default Authenticator.class;
        String permission();
    }

    public static class AuthenticatedAction extends Action<AuthenticatedWithPermission> {

        private final Injector injector;

        @Inject
        public AuthenticatedAction(Injector injector) {
            this.injector = injector;
        }

        public CompletionStage<Result> call(final Http.Context ctx) {
            Authenticator authenticator = injector.instanceOf(configuration.authenticator());
            Account account = authenticator.getAccount(ctx);
            if(account == null || !account.hasPermission(configuration.permission())) {
                Result unauthorized = authenticator.onUnauthorized(ctx);
                return CompletableFuture.completedFuture(unauthorized);
            } else {
                try {
                    return delegate.call(ctx).whenComplete(
                        (result, error) -> ctx.request()
                    );
                } catch (Exception e) {
                    ctx.request();
                    throw e;
                }
            }
        }

    }

    public static class Authenticator extends Results {

        private AccountService accountService = ComponentFactory.getAccountService();

        public Account getAccount(Http.Context ctx) {
            String token = getToken(ctx);
            if (token != null) {
                return accountService.findLoggedIn(token);
            }
            return null;
        }

        public Result onUnauthorized(Http.Context context) {
            return unauthorized();
        }

        public static String getToken(Http.Context ctx) {
            Http.Cookie cookie = ctx.request().cookies().get(TOKEN_COOKIE_NAME);
            if (cookie == null) {
                return null;
            }
            return cookie.value();
        }

        public static void setToken(Http.Context ctx, String token) {
            ctx.response().setCookie(
                    Http.Cookie.builder(TOKEN_COOKIE_NAME, token)
                        .withMaxAge(365 * 86400)
                        .build()
            );
        }
    }
}
