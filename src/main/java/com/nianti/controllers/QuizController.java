package com.nianti.controllers;

import com.nianti.models.Quiz;
import com.nianti.services.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

public class QuizController {

    @Autowired
    private QuizDao quizDao;

    // display the quiz page
    @GetMapping("/quiz/{quizId}")
    public String displayQuizById(@PathVariable int quizId, Model model) {

        // get details by quiz id
        Quiz quiz = quizDao.getQuizById(quizId);
        model.addAttribute("quiz", quiz);

        return "quiz/index";

    }

}
