package me.jhchoi.ontrack.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jhchoi.ontrack.dto.AddTaskRequest;
import me.jhchoi.ontrack.dto.LoginUser;
import me.jhchoi.ontrack.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;


    @PostMapping("/addTask")
    public RedirectView addTaskSubmit(@ModelAttribute AddTaskRequest addTaskRequest, BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            return new RedirectView("login/login");
//            return "redirect:/login/login";
        }
        log.info("=============from 할일추가 form==================");
        log.info("프로젝트아이디 = {}", addTaskRequest.getProjectId());
        log.info("작성자아이디 = {}", addTaskRequest.getTaskAuthorMid());
        log.info("작성자 이름 = {}", addTaskRequest.getAuthorName());
        log.info("할일 이름 = {}", addTaskRequest.getTaskTitle());
        log.info("전체 = {}", addTaskRequest);
//        log.info("파일 = {}", addTaskRequest.getTaskFile().get(0).getOriginalFilename());

        //전체 = AddTaskRequest(projectId=9, taskAuthorMid=14, taskTitle=할 일 추가 ing, taskPriority=vip, taskDueDate=2024-05-23, assigneesMid=null, nickname=null, taskFile=[org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@1b6ce1b0])
        Long newTaskId = taskService.addTask(addTaskRequest);

        String encodedName = URLEncoder.encode(addTaskRequest.getAuthorName(), StandardCharsets.UTF_8);
        log.info("컨트롤러에서 넘어가는 시점: {}", LocalDateTime.now()); // 컨트롤러에서 넘어가는 시점: 2024-06-04T17:50:39.535349900
        // fetch 에서 response 없애고 2024-06-05T21:45:00.923132600

        redirectAttributes.addAttribute("projectId", addTaskRequest.getProjectId());
        redirectAttributes.addAttribute("memberId", addTaskRequest.getTaskAuthorMid());
        redirectAttributes.addAttribute("nickname", addTaskRequest.getAuthorName());

//        return "redirect:/task/test";
        return new RedirectView("/project/{projectId}/{memberId}/{nickname}");

//        return """
//                redirect:/project/%s/%s/%s
//                """.formatted(addTaskRequest.getProjectId(), addTaskRequest.getTaskAuthorMid(), encodedName);
    }

    @GetMapping("/test")
    public String prgTest(@RequestParam("projectId") Long pId, @RequestParam("memberId") Long mId, @RequestParam("nickname") String nickname){
        log.info("prgTest 진입");
        String encodedName = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
        return """
                redirect:/project/%s/%s/%s""".formatted(pId, mId, encodedName);
    }

}
