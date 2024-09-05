let correctAnswers = 0;
let totalQuestions = 0;
let currentQuestion = 0;

// document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("startButton").addEventListener("click", startQuiz);


    // create start quiz function
    function startQuiz() {
        fetch('/api/quiz/{quizId}/question/1')
        .then(response => response.json())
        .then(data => {
            totalQuestions = data.totalQuestions;
            displayQuestion(data);
        })
    }

    // on load
});