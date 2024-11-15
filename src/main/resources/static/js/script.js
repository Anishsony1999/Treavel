$(document).ready(function() {
    $('#registrationForm').on('submit', function(event) {
        var passwordValue = $('#password').val();
        var confirmPasswordValue = $('#confirmPassword').val();
        var isValid = true;

        if (passwordValue !== confirmPasswordValue) {
            isValid = false;
            $('#password, #confirmPassword').addClass('is-invalid'); // Add red border for mismatch
            alert('Passwords do not match.');
        } else {
            $('#password, #confirmPassword').removeClass('is-invalid'); // Remove red border if match
        }

        // Check password length
        if (passwordValue.length < 6) {
            isValid = false;
            $('#password').addClass('is-invalid'); // Add red border for short password
            alert('Password must be at least 6 characters long.');
        } else {
            $('#password').removeClass('is-invalid'); // Remove red border for valid password length
        }

        // If any validation fails, prevent form submission
        if (!isValid) {
            event.preventDefault();
        }

        return isValid;
    });
});
