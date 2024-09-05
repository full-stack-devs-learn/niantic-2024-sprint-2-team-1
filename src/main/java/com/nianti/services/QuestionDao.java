package com.nianti.services;

import com.nianti.models.Question;
import com.nianti.models.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDao
{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionDao(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Question> getQuestionByQuizId(int quizId)
    {
        List<Question> questions = new ArrayList<>();
        String sql = """
            SELECT question_id
                , quiz_id
                , question_number
                , question_text
            FROM question
            WHERE quiz_id = ?;
        """;
        var row = jdbcTemplate.queryForRowSet(sql, quizId);

        while (row.next())
        {
            Question question = new Question();
            question = mapRowToQuestion(row);
            questions.add(question);
        }
        return questions;
    }

    public Question getQuestionByQuizAndQuestion(int quizId ,int questionNumber)
    {
        Question question = new Question();
        String sql = """
            SELECT question_id
                , quiz_id
                , question_number
                , question_text
            FROM question
            WHERE quiz_id = ? AND question_number = ?;
        """;
        var row = jdbcTemplate.queryForRowSet(sql, quizId, questionNumber);

        while (row.next())
        {
            question = mapRowToQuestion(row);
        }
        return question;
    }

    private Question mapRowToQuestion(SqlRowSet row)
    {
        int id      = row.getInt("question_id");
        int quiz    = row.getInt("quiz_id");
        int number  = row.getInt("question_number");
        String text = row.getString("question_text");

        return new Question(id, quiz, number, text);
    }
}
