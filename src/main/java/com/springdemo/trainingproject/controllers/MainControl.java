package com.springdemo.trainingproject.controllers;

import com.springdemo.trainingproject.model.Task;
import com.springdemo.trainingproject.model.User;
import com.springdemo.trainingproject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;


@Controller
public class MainControl {
    @Autowired
   private final UserServiceIMPL userService;
    @Autowired
   private final TaskService taskService;


   public MainControl(UserServiceIMPL userService, TaskService taskService) {
       this.userService = userService;

       this.taskService = taskService;

   }
    @PostMapping("/main/editTask")
    public String editTask(@RequestParam("id") Long id,
                           @RequestParam("status") String status,
                           RedirectAttributes redirectAttributes,
                           Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        Task task = taskService.findById(id);

        if (task == null || !task.getUser().getId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("message", "Task not found or access denied.");
            return "redirect:/main";
        }

        task.setStatus(status);
        taskService.save(task);

        redirectAttributes.addFlashAttribute("message", "Task updated successfully.");
        return "redirect:/main";
    }
    @GetMapping("/main/editTask/{id}")
    public String editTaskPage(@PathVariable("id") Long id, Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        Task task = taskService.findById(id);

        if (task == null || !task.getUser().getId().equals(user.getId())) {
            return "redirect:/main";
        }

        model.addAttribute("task", task);
        return "editTask";
    }

    @PostMapping("/main/uploadPhoto")
    public String uploadPhoto(@RequestParam("file") MultipartFile file,
                              RedirectAttributes redirectAttributes,
                              Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "User not found.");
            return "redirect:/main";
        }

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "No file selected.");
            return "redirect:/main";
        }

        try {
            // Абсолютный путь к папке static/images
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";
            Path path = Paths.get(uploadDir);

            // Создаём папку, если её нет
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // Проверяем существование файла
            String originalFileName = file.getOriginalFilename();

            Path targetPath = path.resolve(originalFileName);

            if (!Files.exists(targetPath)) {
                // Сохраняем файл, если он ещё не существует
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Устанавливаем путь к фото
            String photoLink = "/images/" + originalFileName;
            user.setPhoto(photoLink);
            userService.save(user);

            redirectAttributes.addFlashAttribute("message", "Photo updated successfully.");
            return "redirect:/main";
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload the photo.");
            return "redirect:/main";
        }
    }


    @GetMapping("/main")
    public String showTasks(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(defaultValue = "") String title,
                            @RequestParam(defaultValue = "") String status,
                            Model model, Principal principal) {

        String email = principal.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return "redirect:/login";
        }

        if (page < 0) {
            return "redirect:/main?page=0&size=" + size + "&title=" + title + "&status=" + status;
        }

        // Пагинация с поиском и фильтрацией
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskPage;

        if (!title.isEmpty() && !status.isEmpty()) {
            taskPage = taskService.findByUserIdAndTitleContainingAndStatus(user.getId(), title, status, pageable);
        } else if (!status.isEmpty()) {
            taskPage = taskService.findByUserIdAndStatus(user.getId(), status, pageable);
        } else if (!title.isEmpty()) {
            taskPage = taskService.findByUserIdAndTitleContaining(user.getId(), title, pageable);
        } else {
            taskPage = taskService.findTasksByUserId(user.getId(), pageable);
        }

        // Проверка: если пользователь запрашивает несуществующую страницу
        if (page >= taskPage.getTotalPages() && taskPage.getTotalPages() > 0) {
            return "redirect:/main?page=" + (taskPage.getTotalPages() - 1) + "&size=" + size + "&title=" + title + "&status=" + status;
        }

        // Добавление данных в модель
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", taskPage.getTotalPages());
        model.addAttribute("title", title);
        model.addAttribute("status", status);

        return "main";
    }










    @GetMapping("/logout")
    public String logoutPage() {
       return "redirect:/login";
    }






}
