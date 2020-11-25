package com.eti.pg.board.entity;

import com.eti.pg.board.BoardScope;
import com.eti.pg.task.entity.Task;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name="boards")
public class Board implements Serializable {
    @Id
    private Long id;
    private String title;
    @Column(name = "board_scope")
    @Enumerated(EnumType.STRING)
    private BoardScope boardScope;
    @Column(name = "is_private")
    private boolean isPrivate;
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks;
}
