const signUpBtn = document.getElementById('signUpButton')
signUpBtn.addEventListener('click', registerUser)

function registerUser() {
    const username = document.getElementById('username').value
    const email = document.getElementById('email').value
    const password = document.getElementById('password').value
    const passwordConfirm = document.getElementById('retypePassword').value

    if (username == null || username === '' ||
        email == null || email === '' ||
        password !== passwordConfirm ||
        password == null || password === '') {
        return
    }

    const csrfHeader = document.querySelector("meta[name=_csrf_header]").content
    const csrfToken = document.querySelector("meta[name=_csrf]").content

    const headers = {
        "Content-Type": "application/json"
    }
    headers[csrfHeader] = csrfToken

    fetch('/api/register',
        {
            method: "POST",
            headers,
            body: JSON.stringify({
                "username": username,
                "email": email,
                "password": password
            })
        })
        .then(response => {
            if (response.status === 201) {
                window.location.href = '/home'
            } else {
                alert("Sorry, we couldn't register you at the moment. PLease try again.")
            }
        })
        .catch(error => console.log(`Error occurred: ${error.message}`))
}
