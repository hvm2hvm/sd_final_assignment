package models;

import io.ebean.Model;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.05.2017
 */
@Entity
public class Note extends Model implements ModelWithID<Integer> {

    @Id
    private Integer id;

    @ManyToOne
    private Account owner;

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    private Note parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Note> children;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Note getParent() {
        return parent;
    }

    public void setParent(Note parent) {
        this.parent = parent;
    }

    public List<Note> getChildren() {
        return children;
    }

    public void setChildren(List<Note> children) {
        this.children = children;
    }
}
