package ru.zippospb.restvote.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zippospb.restvote.HasId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseTo implements HasId {
    protected Integer id;
}
