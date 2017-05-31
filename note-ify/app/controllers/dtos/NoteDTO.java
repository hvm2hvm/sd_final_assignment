package controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.Note;

import javax.persistence.Transient;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.05.2017
 */
public class NoteDTO {
    
    private Integer id;
    private Boolean isLeaf;
    private Boolean isTop;
    private String text;
    private List<Integer> childrenIds;
    
    public NoteDTO(Note note) {
        this.setId(note.getId());
        this.setText(note.getText());
        if (note.getParent() == null) {
            this.setTop(true);
        } else {
            this.setTop(false);
        }
        if (note.getChildren().size() > 0) {
            this.setLeaf(false);
            this.setChildrenIds(
                    note.getChildren().stream().map(Note::getId).collect(Collectors.toList()));
        } else {
            this.setLeaf(true);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public Boolean isTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Integer> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(List<Integer> childrenIds) {
        this.childrenIds = childrenIds;
    }

    @JsonIgnore
    public Note getNote() {
        Note note = new Note();
        note.setId(this.id);
        note.setText(this.text);
        return note;
    }
}
