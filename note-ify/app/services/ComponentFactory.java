package services;

import models.Account;
import models.ModelWithID;
import repositories.GenericEbeanRepository;
import repositories.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.03.2017
 */
public class ComponentFactory {

    private static AccountService accountService;
    private static Map<Class<? extends ModelWithID>, GenericFactory> genericFactories;

    static {
        genericFactories = new HashMap<>();
    }

    public static class GenericFactory<ID, TYPE extends ModelWithID<ID>> {

        private Repository<ID, TYPE> repository;
        private GenericService<ID, TYPE> service;

        private Class<TYPE> clazz;

        public GenericFactory(Class<TYPE> clazz) {
            this.clazz = clazz;
        }

        private Repository<ID, TYPE> getRepository() {
            if (repository == null) {
                repository = new GenericEbeanRepository<>(clazz);
            }
            return repository;
        }

        public GenericService<ID, TYPE> getService() {
            if (service == null) {
                service = new GenericService<>(getRepository());
            }
            return service;
        }
    }

    public static <ID, TYPE extends ModelWithID<ID>> GenericFactory<ID, TYPE>
    getGenericFactory(Class<TYPE> clazz) {
        if (!genericFactories.containsKey(clazz)) {
            genericFactories.put(clazz, new GenericFactory<>(clazz));
        }
        return genericFactories.get(clazz);
    }

    public static AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService(
                getGenericFactory(Account.class).getRepository());
        }
        return accountService;
    }

}
