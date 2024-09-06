package com.nianti.controllers;

import com.nianti.models.Question;
import com.nianti.services.QuestionDao;
import com.nianti.models.Quiz;
import com.nianti.services.QuizDao;
import com.nianti.services.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

@Controller
public class QuizController {

    @Autowired
    private QuizDao quizDao;

    @Autowired QuestionDao questionDao;

    // display the quiz page
    @GetMapping("/quiz/{quizId}")
    public String displayQuizById(@PathVariable int quizId, Model model) {

        // get details by quiz id
        Quiz quiz = quizDao.getQuizById(quizId);
        model.addAttribute("quiz", quiz);

        return "quiz/index";

    }

    // Load the first question for the given quiz
    @GetMapping("/quiz/{quizId}/question/{questionId}")
    public String loadQuestion(@PathVariable int quizId, @PathVariable int questionId, Model model) {
        // Get the question based on the quizId and questionId

        Question question = questionDao.getQuestion(quizId, questionId);
        model.addAttribute("question", question);
        return "quiz/fragments/currentQuestion";
    }

}
