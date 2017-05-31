package services;

import models.ModelWithID;
import repositories.Repository;

import java.util.List;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.03.2017
 */
public class GenericService<ID, TYPE extends ModelWithID<ID>> {

    protected Repository<ID, TYPE> repository;

    public GenericService(Repository<ID, TYPE> repository) {
        this.repository = repository;
    }

    public List<TYPE> getAll() {
        return repository.getAll();
    }

    public TYPE findById(ID id) {
        return repository.findById(id);
    }

    public TYPE addOrUpdate(TYPE object) {
        return repository.addOrUpdate(object);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public List<TYPE> queryByColumn(String property, Object[] values) {
        return repository.findManyByColumn(property, values);
    }

}
