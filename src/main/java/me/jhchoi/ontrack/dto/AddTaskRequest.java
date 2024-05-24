package me.jhchoi.ontrack.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.jhchoi.ontrack.domain.OnTrackTask;
import me.jhchoi.ontrack.domain.TaskAssignment;
import me.jhchoi.ontrack.domain.TaskFile;
import me.jhchoi.ontrack.domain.TaskHistory;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Data // Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor @Builder
public class AddTaskRequest {

    @NotNull
    private Long projectId;
    @NotNull
    private Long taskAuthorMid; // author의 member id
    @NotEmpty @Size(max=20)
    private String taskTitle;
    private String taskPriority; // 매우중요vip, 중요ip, 일반norm
    private LocalDate taskDueDate;

    // 담당자 배정
    private Long[] assigneesMid;
//    private List<Long> assigneesUserId;
    private String[] nickname;
    
    // 파일 첨부
    private List<MultipartFile> taskFile; // TaskFile로 변환해야 하..겠지?

    public OnTrackTask dtoToEntityTask(){
        LocalDateTime nowWithNano = LocalDateTime.now();
        int nanosec = nowWithNano.getNano();

        return OnTrackTask.builder()
                .projectId(projectId)
                .author(taskAuthorMid)
                .taskTitle(taskTitle)
                .taskPriority(taskPriority)
                .taskStatus("not yet")
                .taskDueDate(taskDueDate)
                .createdAt(nowWithNano.minusNanos(nanosec))
                .updatedAt(nowWithNano.minusNanos(nanosec))
                .updatedBy(taskAuthorMid)
                .build();
    }

    public List<TaskAssignment> dtoToEntityTaskAssignment(Long taskId, LocalDateTime createdAt){
        List<TaskAssignment> ta = new ArrayList<>();
        IntStream.range(0, assigneesMid.length).forEach(i -> ta.add(TaskAssignment.builder()
                .projectId(projectId)
                .taskId(taskId)
                .memberId(assigneesMid[i])
                .nickname(nickname[i])
                .role("assignee")
                .assignedAt(createdAt)
                .build()));
        return ta;
    }

}
