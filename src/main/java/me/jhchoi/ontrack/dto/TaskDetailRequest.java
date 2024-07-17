package me.jhchoi.ontrack.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.jhchoi.ontrack.domain.TaskComment;
import me.jhchoi.ontrack.domain.TaskFile;
import me.jhchoi.ontrack.domain.TaskHistory;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailRequest extends TaskList {

    private Long projectId;
    private Long taskId;
    private Long authorMid;
    private String authorName;
    private String comment;
    private String commentType;
    private String fileName;

}
