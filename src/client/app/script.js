const form = document.getElementById('form');
const clearButton = document.getElementById('clear-btn');
const textField = document.getElementById('textBox');
const errorMessage = document.getElementById('error');
const resultText = document.getElementById('mytext');
const warningIcon = document.getElementById('warning-icon'); 


const showElement = (el) => el && (el.style.display = 'block');
const hideElement = (el) => el && (el.style.display = 'none');

const getBackendUrl = () => {
  const hostname = window.location.hostname;
     
    if(window.location.hostname === 'frontend') {
      return 'http://backend:8080'; 
    } else {
      return 'http://localhost:8080';  
    }
};

async function searchFood() {
  const name = textField.value.trim();
  if (!name) {
    showError ('Please enter a food name');
    return;
  }

  try {
    const backendUrl = getBackendUrl();
    const url = `${backendUrl}/api/food/search/best?name=${encodeURIComponent(name)}`;

    console.log('Fetching from URL:', url);

    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'same-origin'  
    });
    
    if (!response.ok){
      console.error('Fetch failed with status:', response.status);
      throw new Error('Food not found (HTTP ${response.status})');
    } 

    const food = await response.json();

    document.querySelector('#firstRow .column2').textContent = `Protein: ${food.proteinValue ?? 'N/A'} g`;
    document.querySelector('#firstRow .column3').textContent = `Fat: ${food.fatValue ?? 'N/A'} g`;
    document.querySelector('#firstRow .column4').textContent = `Carbohydrates: ${food.carbohydrateValue ?? 'N/A'} g`;

    resultText.textContent = `Nutrition Facts: ${food.name ?? 'Unknown'}`;
    hideError();
  } catch (error) {
    console.error('Error fetching food:', error);
    errorMessage.textContent = 'Food not found or an error occurred';
    showElement(errorMessage);
  }
}


form.addEventListener('submit', (e) => {
  e.preventDefault();
  if (validateInput(textField.value)) {
    console.log('FORM SUBMITTED');
    searchFood();
  }
});



// Event: Clear button clicked
clearButton?.addEventListener('click', () => {
  hideElement(clearButton);
  textField.value = '';
  hideError();
  resultText.textContent = 'Nutrition Facts:';
  
});

    //Show error state
  function showError(msg = 'Required') {
    if (!errorMessage || !textField) return;
      errorMessage.textContent = msg;
      showElement(errorMessage);
      textField.classList.add('invalid');
    if(warningIcon) {
     showElement(warningIcon);
      warningIcon.classList.add('show-icon');
    }
}


// Hide error state
function hideError() {
  if (!errorMessage || !textField) return;
  hideElement(errorMessage);
  textField.classList.remove('invalid');
  if(warningIcon) {
    hideElement(warningIcon);
    warningIcon.classList.remove('show-icon');
  }
}

// Validation logic
function validateInput(inputValue) {
  if (!inputValue.trim()) {
    showError('Please enter a food name');
    hideElement(clearButton);
    return false;
  } 
  hideError();
  showElement(clearButton);
  return true;
}






  
