package com.eti.pg.user.entity;

import com.eti.pg.board.Role;
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
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "employment_date")
    private LocalDate employmentDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "avatar_path")
    private String avatarPath;
}
