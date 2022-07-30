import Swal from "sweetalert2";

const updateButton = document.getElementById('editButton')
const deleteButton = document.getElementById('deleteButton')
const bookButton = document.getElementById('bookButton')

updateButton?.addEventListener('click', updatePassenger)
deleteButton?.addEventListener('click', deletePassenger)
bookButton?.addEventListener('click', bookFlight)


function updatePassenger() {
    /**
     * @type {HTMLButtonElement}
     */
    const clickedButton = event.target
    const hiddenInput = clickedButton.previousElementSibling
    const passengerId = hiddenInput.value

    const passengerAge = parseInt(document.getElementById('age').value)
    const updateAlertBox = document.getElementById('update-alert-box')

    const ageField = document.getElementById('pa')

    if (isNaN(passengerAge) || passengerAge < 0) {
        updateAlertBox.classList.remove('visually-hidden')
        updateAlertBox.innerText = "Invalid age."
        return
    }

    const csrfHeader = document.querySelector("meta[name=_csrf_header]").content
    const csrfToken = document.querySelector("meta[name=_csrf]").content

    const headers = {
        "Content-Type": "application/json"
    }
    headers[csrfHeader] = csrfToken

    fetch('/graphql', {
        method: "POST",
        headers,
        body: JSON.stringify({
            query: `mutation { updatePassenger(id: ${passengerId}, age: ${passengerAge}) }`
        })
        //redirect: "manual"
    }).then(response => {
        if (response.status === 200) {
            ageField.innerText = `${passengerAge}`
            new Swal("Done!", 'Passenger updated successfully.', "success")
            location.reload() // necessary to reset the modal
        } else {
            new Swal("Cannot update passenger!", 'Update failed. Please try again later.', "warning")
        }
    }).catch(error => new Swal("Cannot update passenger!", `Error: ${error.message}`, "error"))
}


function deletePassenger() {
    /**
     * @type {HTMLButtonElement}
     */
    const clickedButton = event.target
    const hiddenInput = clickedButton.previousElementSibling
    const passengerId = hiddenInput.value

    const csrfHeader = document.querySelector("meta[name=_csrf_header]").content
    const csrfToken = document.querySelector("meta[name=_csrf]").content

    const headers = {
        "Content-Type": "application/json"
    }
    headers[csrfHeader] = csrfToken

    fetch(`/graphql`, {
        method: "POST",
        headers,
        body: JSON.stringify({
            query: `mutation { deletePassenger(id: ${passengerId}) }`
        })
    }).then(response => {
        if (response.status === 200) {
            window.location.href = '/home'
        } else {
            new Swal("Cannot delete passenger!", `Cannot delete passengerID:${passengerId}.`, "error")
        }
    }).catch(error => new Swal("Cannot delete passenger!", `Error: ${error.message}`, "error"))
}


function bookFlight() {
    const passengerId = document.getElementById('pId').value
    // TODO: think of a better way to get the flight id
    const flightId = document.getElementById('flight').selectedOptions[0].id
    const alertBox = document.getElementById('alert-box')

    const csrfHeader = document.querySelector("meta[name=_csrf_header]").content
    const csrfToken = document.querySelector("meta[name=_csrf]").content

    const headers = {
        "Content-Type": "application/json"
    }
    headers[csrfHeader] = csrfToken

    // TODO: check if flight is selected, if not return
    if (flightId === "" || flightId === null) {
        alertBox.innerText = "You must select a flight to continue."
        alertBox.classList.remove('visually-hidden')
        return
    }

    fetch(`/graphql`, {
        method: "POST",
        headers,
        body: JSON.stringify({
            query: `mutation { bookFlight(fId: ${flightId}, pId: ${passengerId}) }`
        })
    }).then(response => {
        if (response.status === 200) {
            new Swal({
                title: "Great!",
                text: "Your flight's been booked. Have a nice flight!",
                icon: "success",
                buttons: true,
                dangerMode: false,
            }).then((clickedOK) => {
                if (clickedOK) {
                    location.reload()
                }
            })
        } else {
            new Swal("Error!", `Cannot book flight for passenger:${passengerId}.`, "error")
        }
    }).catch(error => new Swal("Error!", `Error: ${error.message}`, "error"))
}
