package me.jhchoi.ontrack.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 할 일의 변경사항 저장
// 할 일 탭 중 '내역'에 출력
@Data
@NoArgsConstructor
public class TaskHistory {
    private Long id;
    private Long taskId;
    private Long projectId;
    @NotEmpty
    private String modItem; // 변경한 카테고리: 할일명(title), 중요도(priority), 진행상태(status), 마감일(dueDate), 세부할일분화(child), 담당자(assignee)
    @NotEmpty
    private String modType; // 등록(register), 변경(update), 삭제(delete)
    @NotEmpty
    private String modContent; // 변경한 내용
    private LocalDateTime updatedAt;
    private Long updatedBy; // member id

    @Builder
    public TaskHistory(Long taskId, Long projectId, String modItem, String modType, String modContent, LocalDateTime updatedAt, Long updatedBy) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.modItem = modItem;
        this.modType = modType;
        this.modContent = modContent;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    // 할 일 명 등록
    public static TaskHistory logNewTask(OnTrackTask newTask){
        return TaskHistory.builder()
                .projectId(newTask.getProjectId())
                .taskId(newTask.getId())
                .modItem("title")
                .modType("register")
                .modContent(newTask.getTaskTitle())
                .updatedAt(newTask.getCreatedAt())
                .updatedBy(newTask.getAuthorMid())
                .build();
    }

    public static TaskHistory logAssignment(TaskAssignment assignees, Long authorId) {
        return TaskHistory.builder()
                .projectId(assignees.getProjectId())
                .taskId(assignees.getTaskId())
                .modItem("assignee")
                .modType("register")
                .modContent(assignees.getNickname())
                .updatedAt(assignees.getAssignedAt())
                .updatedBy(authorId)
                .build();
    }
}
