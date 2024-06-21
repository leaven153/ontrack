package me.jhchoi.ontrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssigneeTaskList extends TaskList{

    private Long id;
    private String taskTitle;
    private Integer taskStatus;
    private LocalDate taskDueDate;
    private Integer assigneeNum;

}
