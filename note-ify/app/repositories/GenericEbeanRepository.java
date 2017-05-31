package repositories;

import io.ebean.Finder;
import models.ModelWithID;

import java.util.List;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.03.2017
 */
public class GenericEbeanRepository<ID, TYPE extends ModelWithID<ID>> implements
        Repository<ID, TYPE> {

    protected Finder<ID, TYPE> finder;

    public GenericEbeanRepository(Class<TYPE> clazz) {
        this.finder = new Finder<>(clazz);
    }

    @Override
    public TYPE findById(ID id) {
        return finder.byId(id);
    }

    @Override
    public TYPE findOneByColumn(String column, Object value) {
        return finder.query().where().eq(column, value).findUnique();
    }

    @Override
    public List<TYPE> findManyByColumn(String column, Object[] objects) {
        return finder.query().where().in(column, objects).findList();
    }

    @Override
    public TYPE addOrUpdate(TYPE object) {
        if (object.getId() != null) {
            finder.db().update(object);
        } else {
            finder.db().save(object);
        }
        return object;
    }

    @Override
    public void deleteById(ID id) {
        finder.deleteById(id);
    }

    @Override
    public List<TYPE> getAll() {
        return finder.all();
    }

}
