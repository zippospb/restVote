package ru.zippospb.restvote;

public interface HasId {
    default boolean isNew(){
        return getId() == null;
    }

    Integer getId();

    void setId(Integer id);
}
