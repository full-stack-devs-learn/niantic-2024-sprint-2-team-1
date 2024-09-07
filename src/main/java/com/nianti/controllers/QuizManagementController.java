package com.nianti.controllers;

import com.nianti.models.Question;
import com.nianti.models.Quiz;
import com.nianti.services.QuestionDao;
import com.nianti.services.QuizDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/quizzes")
public class QuizManagementController {

    @Autowired
    private QuizDao quizDao;

    // Display the quiz management page
    @GetMapping
    public String showQuizManagementPage(Model model) {
        // Fetch all quizzes and pass them to the model
        List<Quiz> quizzes = quizDao.getAllQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "quiz-management/index";  // View for quiz management
    }

    // Display the form to add a new quiz
    @GetMapping("/add")
    public String showNewQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());  // Pass an empty Quiz object to the form
        return "quiz-management/new";  // View for the new quiz form
    }

    // Process the form submission for adding a new quiz
    @PostMapping("/add")
    public String addNewQuiz(@Valid @ModelAttribute("quiz") Quiz quiz, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "quiz-management/new";  // Return form view if validation errors occur
        }

        quizDao.addQuiz(quiz);  // Save the new quiz
        return "quiz-management/add_success";  // Redirect back to the quiz management page
    }

    // Display the form to edit an existing quiz
    @GetMapping("/edit/{quizId}")
    public String showEditQuizForm(@PathVariable int quizId, Model model) {
        Quiz quiz = quizDao.getQuizById(quizId);
        model.addAttribute("quiz", quiz);
        return "quiz/edit";  // View for the edit quiz form
    }

    // Process the form submission for editing a quiz
    @PostMapping("/edit/{quizId}")
    public String editQuiz(@PathVariable int quizId, @Valid @ModelAttribute("quiz") Quiz quiz, BindingResult result) {
        if (result.hasErrors()) {
            return "quiz/edit";  // Return form view if validation errors occur
        }

        quizDao.editQuiz(quiz);  // Update the quiz
        return "redirect:/quizzes";  // Redirect back to the quiz management page
    }

    // Handle enabling/disabling a quiz by updating its isLive status
    @PostMapping("/toggleLive/{quizId}")
    public String toggleQuizLiveStatus(@PathVariable int quizId) {
        quizDao.toggleLiveStatus(quizId);  // Enable or disable the quiz
        return "redirect:/quizzes";
    }
}

