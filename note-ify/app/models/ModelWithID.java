package models;

/**
 * @Author: Voicu Hodrea
 * @Date: 14.05.2017
 */
public interface ModelWithID<ID> {

    ID getId();
    void setId(ID id);

}
