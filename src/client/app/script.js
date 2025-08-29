const searchButton = document.getElementById('search-btn');
const clearButton = document.getElementById('clear-btn');
const textField = document.getElementById('textBox');
const errorMessage = document.getElementById('error');
const resultText = document.getElementById('mytext');
const warningIcon = document.getElementById('warning-icon'); 


const showElement = (el) => el && (el.style.display = 'block');
const hideElement = (el) => el && (el.style.display = 'none');

//Show error state
function showError() {
  if (!errorMessage || !textField) return;
  errorMessage.textContent = 'Required!';
  errorMessage.style.display = 'block';
  textField.classList.add('invalid');
  warningIcon.style.display = 'block';//added
  warningIcon?.classList.add('show-icon');
}

// Hide error state
function hideError() {
  if (!errorMessage || !textField) return;
  errorMessage.textContent = '';
  errorMessage.style.display = 'none';
  textField.classList.remove('invalid');
  warningIcon.style.display = 'none'; //added
  warningIcon?.classList.remove('show-icon');
}

// Validation logic
function validateInput(inputValue) {
  const isEmpty = inputValue.trim() === '';
  if (isEmpty) {
    showError();
    hideElement(clearButton);
    return false;
  } else {
    hideError();
    showElement(clearButton);
    return true;
  }
}

// Event: Search button clicked
searchButton?.addEventListener('click', (e) => {
  e.preventDefault();

  const userInput = textField?.value ?? '';

  if (validateInput(userInput)) {
    if (resultText) {
      resultText.innerHTML = `Nutrition Facts: ${userInput}`;
    }
  }
});

// Event: Clear button clicked
clearButton?.addEventListener('click', () => {
  hideElement(clearButton);
  hideError();
  if (textField) {
    textField.value = '';
    textField.classList.remove('invalid');
  }
  if (resultText) {
    resultText.innerHTML = 'Nutrition Facts:';
  }
});
