package com.nianti.controllers.apis;

import com.nianti.models.Question;
import com.nianti.services.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")  // Base path for API endpoints
public class QuizApiController {

    @Autowired
    private QuestionDao questionDao;

    // API to load the question for the given quiz and question number in JSON format
    @GetMapping("/quiz/{quizId}/question/{questionNumber}")
    public ResponseEntity<Question> getQuestion(@PathVariable int quizId, @PathVariable int questionNumber) {
        Question question = questionDao.getQuestion(quizId, questionNumber);

        if (question == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(question);  // Return the question as JSON
    }
}
