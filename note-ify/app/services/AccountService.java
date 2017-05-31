package services;

import models.Account;
import repositories.Repository;

/**
 * @Author: Voicu Hodrea
 * @Date: 28.05.2017
 */
public class AccountService<TYPE extends Account> extends GenericService<Integer, TYPE> {
    public AccountService(Repository<Integer, TYPE> repository) {
        super(repository);
    }

    public TYPE tryToLogIn(String username, String password) {
        String passwordHash = Tools.hashString(password);
        TYPE account = repository.findOneByColumn("username", username);
        if (account == null || account.getPasswordHash() == null) {
            return null;
        }
        if (account.getPasswordHash().equals(passwordHash)) {
            String token = Tools.hashString(
                    String.format("%s/%s/%s",
                            account.getUsername(), account.getPasswordHash(), System.currentTimeMillis()));
            account.setToken(token);
            return repository.addOrUpdate(account);
        }
        return null;
    }

    public TYPE findLoggedIn(String token) {
        return repository.findOneByColumn("token", token);
    }

    @Override
    public TYPE addOrUpdate(TYPE account) {
        if (account.getPassword() != null) {
            account.setPasswordHash(Tools.hashString(account.getPassword()));
        }
        return repository.addOrUpdate(account);
    }

}
