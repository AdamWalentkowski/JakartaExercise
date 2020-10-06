package com.eti.pg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Board {
    String title;
    BoardScope boardScope;
    boolean isPrivate;
}
