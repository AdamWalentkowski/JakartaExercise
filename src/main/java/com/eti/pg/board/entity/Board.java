package com.eti.pg.board.entity;

import com.eti.pg.board.BoardScope;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Board implements Serializable {
    private Long id;
    private String title;
    private BoardScope boardScope;
    private boolean isPrivate;
}
