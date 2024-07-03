package me.jhchoi.ontrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoAssigneeTask extends TaskList{
    private Long id;
    private String taskTitle;
    private Integer taskStatus;
    private LocalDate taskDueDate;
    private Long authorMid;
    private String authorName;
    private LocalDateTime createdAt;

}
