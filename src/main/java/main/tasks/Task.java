package main.tasks;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Task {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Getter
    @Setter
    @Column(name = "creation_date")
    private LocalDateTime creationTime;

    @Getter
    @Setter
    @Column(name = "is_done", nullable = true)
    private Boolean isDone;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;


}
