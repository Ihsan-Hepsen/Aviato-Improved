const addButton = document.getElementById('addButton')
addButton.addEventListener('click', addPassenger)


function addPassenger() {
    const firstName = document.getElementById('firstName').value
    const lastName = document.getElementById('lastName').value
    const age = parseInt(document.getElementById('age').value)
    const gender = document.getElementById('gender').value
    const passengerStatus = document.getElementById('transit').checked

    // if (firstName == null || firstName === '' || lastName == null || lastName === '') {
    //     return
    // }

    const csrfHeader = document.querySelector("meta[name=_csrf_header]").content
    const csrfToken = document.querySelector("meta[name=_csrf]").content

    const headers ={
        "Content-Type": "application/json"
    }
    headers[csrfHeader] = csrfToken

    fetch('/api/passengers', {
        method: 'POST',
        headers,
        body: JSON.stringify({
            name: firstName + ' ' + lastName,
            age: age,
            gender: gender === 'male' ? 'M' : 'F',
            transitPassenger: passengerStatus
        })
    }).then(response => {
        console.log(response.status)
        if (response.status === 201) {
            let name = firstName + " " + lastName
            window.location.href = `/passengers/details?pn=${name}`
        } else {
            firstName.value = ''
            lastName.value = ''
            passengerStatus.checked = false
        }
    }).catch(error => alert(`Error occurred: ${error.message}`))
}
