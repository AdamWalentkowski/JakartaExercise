package com.eti.pg;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Board {
    String title;
    BoardScope boardScope;
    boolean isPrivate;
}
