const searchButton = document.getElementById('search-btn');
const clearButton = document.getElementById('clear-btn');
const textField = document.getElementById('textBox');
const error = document.getElementById('error');


if (searchButton) {
	searchButton.addEventListener('click', (event) => {
		event.preventDefault(); // Prevents page reload on form submit
		var userInput = textField.value;
		clearButton.style.display = 'block';
		checkTextArea(userInput);

		document.getElementById("mytext").innerHTML = "Nutrition Facts: " + userInput;

	});
}
if (clearButton) {
	clearButton.addEventListener('click', (event) => {
		clearButton.style.display = 'none';
	})
}

/*
Validates input field
*/

function hideMessage(error) {
	error.style.display = 'none';
}

function showMessage(error) {
	error.style.display = 'block';
	textField.classList.add("invalid");
	error.innerHTML = "Required!"
}

function checkTextArea(fieldValue) {

	if (fieldValue.trim() === '') {
		showMessage(error);
		clearButton.style.display = 'none';

	} else {
		clearButton.style.display = 'block';
		hideMessage(error);
		textField.classList.remove("invalid");
	}
}