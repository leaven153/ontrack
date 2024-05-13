package me.jhchoi.ontrack.domain;

import me.jhchoi.ontrack.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void newTask(){
        OnTrackTask task = OnTrackTask.builder()
                .projectId(1L)
                .taskTitle("first task test")
                .author(1L)
                .taskPriority("VIP")
                .taskStatus("not yet")
                .taskDueDate(null)
                .taskParentId(null)
                .createdAt(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .updatedAt(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .updatedBy(1L)
                .build();
        OnTrackTask savedTask = taskRepository.newTask(task);
        OnTrackTask findTask = taskRepository.findById(task.getId()).get();
        assertThat(findTask.getTaskTitle()).isEqualTo(savedTask.getTaskTitle());
    }

    @Test
    void assign(){
        List<TaskAssignment> assignees = new ArrayList<>();

        TaskAssignment ta = TaskAssignment.builder()
                .projectId(1L)
                .taskId(1L)
                .userId(1L)
                .memberId(1L)
                .role("assignee")
                .assignedAt(LocalDate.now())
                .build();
        assignees.add(ta);
        taskRepository.assign(assignees);
        assertThat(ta.getId()).isEqualTo(1L);
    }
}
