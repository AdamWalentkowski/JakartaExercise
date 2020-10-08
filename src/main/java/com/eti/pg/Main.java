package com.eti.pg;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Task task = Task.builder()
                .title("sth")
                .description("sth else")
                .priority(2)
                .creationDate(LocalDate.now())
                .build();

        task.setUser(new User(
                "smone",
                LocalDate.now().minusYears(1),
                Role.ADMIN));

        System.out.println(task.toString());
    }
}
