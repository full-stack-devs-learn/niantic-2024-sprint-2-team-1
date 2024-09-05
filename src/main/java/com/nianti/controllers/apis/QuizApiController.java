package com.nianti.controllers.apis;


import com.nianti.models.Question;
import com.nianti.services.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import java.util.List;

@RestController
public class QuizApiController {

    @Autowired
    private QuestionDao questionDao;

    @GetMapping("/api/quiz/{quizId}/question/{questionId}")
    public ResponseEntity <Question> getQuestion(@PathVariable int quizId, @PathVariable int questionId) {

       // Question question = questionDao.getQuestionByQuizId(quizId);
      //  return ResponseEntity.ok(question);
        return null;

    }

}
