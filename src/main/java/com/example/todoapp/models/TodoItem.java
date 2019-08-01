package com.example.todoapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection="todoitems")
public class TodoItem {
    @Id
    private String id;

    @NotBlank
    @Size(max=100)
    private String title;

    private String desc;
    private String deadline;
    private Boolean completed = false;
    private String parentListId;
    private String linkedItemId;
    private String linkedItemName;


    public TodoItem() {
        super();
    }

    public TodoItem(String title) {
        this.title = title;
    }

    public TodoItem(@NotBlank @Size(max = 100) String title, String desc, String deadline, Boolean completed,
                    String parentListId, String linkedItemId, String linkedItemName) {
        this.title = title;
        this.desc = desc;
        this.deadline = deadline;
        this.completed = completed;
        this.parentListId = parentListId;
        this.linkedItemId = linkedItemId;
        this.linkedItemName = linkedItemName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getParentListId() {
        return parentListId;
    }

    public void setParentListId(String parentListId) {
        this.parentListId = parentListId;
    }

    public String getLinkedItemId() {
        return linkedItemId;
    }

    public void setLinkedItemId(String linkedItemId) {
        this.linkedItemId = linkedItemId;
    }

    public String getLinkedItemName() {
        return linkedItemName;
    }

    public void setLinkedItemName(String linkedItemName) {
        this.linkedItemName = linkedItemName;
    }

    @Override
    public String toString() {
        return String.format(
                "Todo[id=%s, title='%s', completed='%s']",
                id, title, completed);
    }
}