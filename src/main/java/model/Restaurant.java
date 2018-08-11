package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {
    public Restaurant() {}

    public Restaurant(Integer id, @NotBlank @Size(min = 2, max = 100) String name) {
        super(id, name);
    }
}
