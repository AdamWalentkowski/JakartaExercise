package com.eti.pg.task.entity;

import com.eti.pg.board.entity.Board;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Integer priority;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;
    //TODO: next exercise
//    @ManyToOne
//    @JoinColumn(name = "user")
//    private User user;
}
