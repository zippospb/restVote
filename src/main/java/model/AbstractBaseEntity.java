package model;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractBaseEntity {
    private static final int START_SEQ = 100_000;


    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    protected AbstractBaseEntity(){}

    AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public boolean isNew(){
        return id == null;
    }
}
