let currentQuestionId = 1;  // Start with question 1
let totalQuestions = 5;

document.addEventListener("DOMContentLoaded", () => {
    startQuiz();
});

// Function to start the quiz
function startQuiz() {
    // Get DOM elements inside the function
    const startButton = document.getElementById("startButton");
    const quizId = startButton.getAttribute("data-quiz-id");  // Extract quizId from the button

    // Add event listener to start button
    startButton.addEventListener("click", () => {
        loadQuestion(quizId, currentQuestionId);  // Load the first question when the button is clicked
    });
}

function loadQuiz(quizId)
{
    fetch(`/api/quiz/${quizId}/question`)
    .then(response => {
    if (!response.ok) {
        throw new Error('Failed to load quiz');
    }
    return response.text();
    })
    .then(data => {
    totalQuestions = data
    loadQuestion(quizId, totalQuestions[currentQuestionId].questionId)
    })
    console.log("Here is the total amount of questions:", totalQuestions)
    //.catch(error => console.error('Error:', error));
}

function loadQuestion(quizId, questionId) {
    // Fetch the current question Thymeleaf fragment
    fetch(`/quiz/${quizId}/question/${questionId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to load question');
            }
            return response.text();  // We expect HTML, not JSON
        })
        .then(html => {
            const quizText = document.getElementById("quizText");
            quizText.innerHTML = html;  // Inject the question fragment into the DOM

            // Re-attach event listeners for answer buttons and the next button
            attachAnswerListeners(quizId);
        })
        .catch(error => console.error('Error:', error));
}



function attachAnswerListeners(quizId) {
    // Get all answer buttons
    const answerButtons = document.querySelectorAll('#quizContent button');

    // Add event listener to each answer button
    answerButtons.forEach(button => {
        button.addEventListener('click', () => {
            const selectedAnswerId = button.getAttribute('data-answer-id');
            const correctAnswerId = button.getAttribute('data-correct-answer-id');

            // Call function to handle the selected answer
            selectAnswer(selectedAnswerId, correctAnswerId);

            // Show "Next" button
            document.getElementById("nextButton").style.display = "block";
        });
    });

    // Add event listener to the "Next" button
    document.getElementById("nextButton").addEventListener("click", () => {
        loadNextQuestion(quizId);
    });
}

function loadNextQuestion(quizId)
{
        console.log("Current question ID: ", currentQuestionId);
        console.log("Total questions length: ", totalQuestions.length)
        // Load the next question if there are more
        if (currentQuestionId < totalQuestions) {
            currentQuestionId++;
            console.log(totalQuestions);
            loadQuestion(quizId, currentQuestionId);
        } else {
            // Show final message or score when the quiz is finished
            showFinalMessage();
        }
}

function selectAnswer(selectedAnswerId, correctAnswerId) {
    // Compare selected answer with the correct answer
    if (selectedAnswerId === correctAnswerId) {
        console.log("Correct answer!");
        // Optionally: highlight the correct answer in the UI
    } else {
        console.log("Incorrect answer.");
        // Optionally: highlight the incorrect answer in the UI
    }
}

function showFinalMessage() {
    const quizText = document.getElementById("quizText");
    quizText.innerHTML = "<h2>Quiz Completed! Thank you for participating.</h2>";
}