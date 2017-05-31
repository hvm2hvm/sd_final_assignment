package repositories;

import models.ModelWithID;

import java.util.List;

/**
 * @Author: Voicu Hodrea
 * @Date: 30.03.2017
 */
public interface Repository<ID, TYPE extends ModelWithID<ID>> {

    TYPE findById(ID id);

    TYPE findOneByColumn(String column, Object object);

    List<TYPE> findManyByColumn(String column, Object[] objects);

    TYPE addOrUpdate(TYPE object);

    void deleteById(ID id);

    List<TYPE> getAll();

}
