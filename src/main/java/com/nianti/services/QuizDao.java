package com.nianti.services;

import com.nianti.models.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuizDao
{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public QuizDao(DataSource dataSource)
    {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Quiz> getAllQuizzes()
    {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        String sql = """
            SELECT quiz_id
                , quiz_title
                , is_live
            FROM quiz;
        """;
        var row = jdbcTemplate.queryForRowSet(sql);
        while (row.next())
        {
            var quiz = mapRowToQuiz(row);
            quizzes.add(quiz);
        }

        return quizzes;
    }

    private Quiz mapRowToQuiz(SqlRowSet row)
    {
        int id = row.getInt("quiz_id");
        String title = row.getString("quiz_title");
        boolean isLive = row.getBoolean("is_live");

        return new Quiz(id, title, isLive);
    }

    public Quiz getQuizById(int quizId) {

        // string sql WHERE quiz_id = ?
        String sql = """
                SELECT quiz_id
                    , quiz_title
                    , is_live
                FROM quiz
                WHERE quiz_id = ?
                """;

        var row = jdbcTemplate.queryForRowSet(sql, quizId);
        if (row.next()) {
            return mapRowToQuiz(row);
        }

        return null;
    }

    // Add a new quiz
    public void addQuiz(Quiz quiz) {
        String sql = "INSERT INTO quiz (title, is_live) VALUES (?, ?)";
        jdbcTemplate.update(sql, quiz.getTitle(), quiz.isLive());
    }

    // Edit an existing quiz
    public void editQuiz(Quiz quiz) {
        String sql = "UPDATE quiz SET title = ?, is_live = ? WHERE quiz_id = ?";
        jdbcTemplate.update(sql, quiz.getTitle(), quiz.isLive(), quiz.getQuizId());
    }

    // Delete a quiz by id
    public void deleteQuiz(int quizId) {
        String sql = "DELETE FROM quiz WHERE quiz_id = ?";
        jdbcTemplate.update(sql, quizId);
    }

    // Toggle live status
    public void toggleLiveStatus(int quizId) {
        String sql = "UPDATE quiz SET is_live = NOT is_live WHERE quiz_id = ?";
        jdbcTemplate.update(sql, quizId);
    }

}
