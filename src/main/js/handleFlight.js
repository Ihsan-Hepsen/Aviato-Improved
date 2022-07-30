import Swal from 'sweetalert2'

const deleteButton = document.getElementById('deleteButton')
const editButton = document.getElementById('editButton')
const removeFromFlightBtn = document.getElementById('removeFromFlight')

deleteButton.addEventListener('click', deleteFlight)
editButton.addEventListener('click', updateFlight)
removeFromFlightBtn.addEventListener('click', editPassengerList)

function deleteFlight() {
    /**
     * @type {HTMLButtonElement}
     */
    const clickedButton = event.target
    const hiddenInput = clickedButton.previousElementSibling
    const flightId = parseInt(hiddenInput.value)

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
            query: `
            mutation {
                deleteFlight(id: ${flightId})
            }`
        })
    }).then(response => {
        if (response.status === 200) {
            window.location.href = '/flights'
        } else {
            new Swal("Cannot delete flight!", `Cannot delete Flight ID:${flightId}.`, "error")
        }
    }).catch(error => new Swal("Cannot delete flight!", `Error: ${error.message}`, "error"))
}

function updateFlight() {
    /**
     * @type {HTMLButtonElement}
     */
    const newSchedule = document.getElementById('flightSchedule').value
    const newStatus = document.getElementById('flightStatus').value

    if (newSchedule == null || newStatus == null) {
        return
    }
    const clickedButton = event.target
    const hiddenInput = clickedButton.previousElementSibling
    const flightId = parseInt(hiddenInput.value)

    const csrfHeader = document.querySelector("meta[name=_csrf_header]").content
    const csrfToken = document.querySelector("meta[name=_csrf]").content

    const statusField = document.getElementById('status')
    const scheduleField = document.getElementById('schedule')

    const headers = {
        "Content-Type": "application/json"
    }
    headers[csrfHeader] = csrfToken

    fetch('/graphql', {
        method: "POST",
        headers,
        body: JSON.stringify({
            query: `mutation {
                updateFlight(id: ${flightId}, 
                scheduledOn: "${newSchedule}",
                onTime: ${newStatus})
                }`
        })
        //redirect: "manual"
    }).then(response => {
        if (response.status === 200) {
            let myModalEl = document.getElementById('staticBackdrop');
            let modal = bootstrap.Modal.getInstance(myModalEl)
            modal.hide()
            scheduleField.innerText = `${newSchedule}`
            statusField.innerText = newStatus ? ' (On Time)' : ' (Delayed)'
            new Swal("Done!", 'Flight updated successfully.', "success")
            location.reload() // necessary to reset the modal
        } else {
            new Swal("Cannot update flight!", 'Update failed. Please try again later.', "warning")
        }
    }).catch(error => new Swal("Cannot update flight!", `Error: ${error.message}`, "error"))
}

function editPassengerList() {
    const passengerId = parseInt(document.getElementById('pId').value)
    const flightId = parseInt(document.getElementById('fId').value)

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
            query: `mutation { removePassengerFromFlight(fId: ${flightId}, pId: ${passengerId}) }`
        })
    }).then(response => {
        if (response.status === 200) {
            location.reload() // necessary
        } else {
            new Swal("Cannot modify passenger list!", 'Operation failed. Please try again later.', "warning")
        }
    }).catch(error => new Swal("Cannot modify passenger list!", `Error: ${error.message}`, "error"))
}
