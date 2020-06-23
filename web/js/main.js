(() => {
    // ============================== variables ==============================

    let pollForm;
    let submitBtn

    // TODO: init showResultsBtn -> forward to results
    let showResultsBtn;

    // ============================== functions ==============================

    /**
     * init app
     */
    function init() {
        // get pointer to html elements
        pollForm = document.querySelector('#form-poll');
        submitBtn = pollForm && pollForm.querySelector('button[type="submit"]');

        // check if element exist in the DOM
        if (pollForm && submitBtn) {
            // listen for form's input changes
            pollForm.addEventListener('input', () => {
                let answer = getFieldValue(pollForm, 'answer');
                if (answer) {
                    submitBtn.removeAttribute('disabled');
                }
            });

            // listen for submit-form event
            pollForm.addEventListener('submit', (event) => {
                let answer = getFieldValue(pollForm, 'answer');
                if (!answer) {
                    event.preventDefault();
                }
            });

            // set initial submitBtn status
            let answer = getFieldValue(pollForm, 'answer');
            if (!answer) {
                submitBtn.setAttribute('disabled', '');
            }
        }
    }

    /**
     * get the value of field in form
     * @param {*} formElement
     * @param {*} inputName
     */
    function getFieldValue(formElement, inputName) {
        return (
            formElement && formElement[inputName] && formElement[inputName].value
        );
    }

    // ============================== handlers ==============================

    // run init when ready
    window.addEventListener('DOMContentLoaded', () => {
        init();
    });
})();
