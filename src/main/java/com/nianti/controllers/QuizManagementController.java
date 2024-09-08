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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/quizzes")
public class QuizManagementController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

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

//    // Display the form to edit an existing quiz
//    @GetMapping("/edit/{quizId}")
//    public String showEditQuizForm(@PathVariable int quizId, Model model) {
//        Quiz quiz = quizDao.getQuizById(quizId);
//        model.addAttribute("quiz", quiz);
//        return "quiz-management/edit";  // View for the edit quiz form
//    }
//
//    // Process the form submission for editing a quiz
//    @PostMapping("/edit/{quizId}")
//    public String editQuiz(@PathVariable int quizId, @Valid @ModelAttribute("quiz") Quiz quiz, BindingResult result) {
//        if (result.hasErrors()) {
//            return "quiz-management/edit";  // Return form view if validation errors occur
//        }
//
//        quizDao.editQuiz(quiz);  // Update the quiz
//        return "redirect:/quizzes";  // Redirect back to the quiz management page
//    }

    @PostMapping("/toggle-live/{quizId}")
    public String toggleQuizLiveStatus(@PathVariable int quizId, RedirectAttributes redirectAttributes) {

        // Retrieve the quiz by its ID
        Quiz quiz = quizDao.getQuizById(quizId);

        if (quiz != null) {
            // Toggle the isLive status
            boolean isCurrentlyLive = quiz.isLive();
            quiz.setLive(!isCurrentlyLive);

            // Update the quiz in the database
            quizDao.editQuiz(quiz);

            // Add a flash message based on the new status
            String message = isCurrentlyLive ? "The quiz has been turned off." : "The quiz has been turned on.";
            redirectAttributes.addFlashAttribute("message", message);
        }

        // Redirect back to the quiz management page
        return "redirect:/quizzes";
    }

    @GetMapping("/{quizId}")
    public String detailsPage(@PathVariable int quizId, Model model) {
        // Fetch quiz and its questions
        Quiz quiz = quizDao.getQuizById(quizId);
        List<Question> questions = questionDao.getQuestionsByQuizId(quizId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);

        return "quiz-management/details";  // Return quiz details with associated questions
    }

    // Add Question
    @GetMapping("/{quizId}/add-question")
    public String showAddQuestionForm(@PathVariable int quizId, Model model) {
        // Create a new Question object and associate it with the current quiz
        Question question = new Question();
        question.setQuizId(quizId);

        // Add the question to the model for the form
        model.addAttribute("question", question);
        return "quiz-management/add-question";  // The view for the question form
    }

    @PostMapping("/{quizId}/add-question")
    public String addQuestion(@PathVariable int quizId, @Valid @ModelAttribute("question") Question question, BindingResult result) {
        if (result.hasErrors()) {
            return "quiz-management/add-question";  // Return form view if there are validation errors
        }

        // Save the new question to the database
        questionDao.addQuestion(question);
        return "redirect:/quizzes/{quizId}";  // Redirect to the quiz details page
    }


    // Delete a question by its ID
    @PostMapping("/{quizId}/questions/delete/{questionId}")
    public String deleteQuestion(@PathVariable int quizId, @PathVariable int questionId, RedirectAttributes redirectAttributes) {
        questionDao.deleteQuestion(questionId);  // Delete the question by ID
        redirectAttributes.addFlashAttribute("message", "Question deleted successfully.");
        return "redirect:/quizzes/";
    }



    }




