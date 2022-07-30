import Swal from "sweetalert2";

const updateButton = document.getElementById('editButton')
const deleteButton = document.getElementById('deleteButton')

updateButton.addEventListener('click', updateAirline)
deleteButton.addEventListener('click', deleteAirlines)


function updateAirline() {
    const fleetSizeField = document.getElementById('fleetSizeField')
    const fleetSize = parseInt(document.getElementById('fleetSize').value)
    if (isNaN(fleetSize) || fleetSize <= 0 || fleetSize == null) {
        return
    }
    /**
     * @type {HTMLButtonElement}
     */
    const clickedButton = event.target
    const hiddenInput = clickedButton.previousElementSibling
    const airlineId = parseInt(hiddenInput.value)

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
            query: `mutation { updateAirline(id: ${airlineId}, fleetSize: ${fleetSize}) }`
        })
    }).then(response => {
        if (response.status === 200) {
            fleetSizeField.innerText = `${fleetSize}`
            location.reload() // necessary to fully reset the bootstrap modal
        } else {
            new Swal("Error!", `Cannot update airline ID:${airlineId}.`, "error")
        }
    }).catch(error => new Swal("Error!", `Error: ${error.message}`, "error"))
}


function deleteAirlines() {
    /**
     * @type {HTMLButtonElement}
     */
    const clickedButton = event.target
    const hiddenInput = clickedButton.previousElementSibling
    const airlineId = hiddenInput.value

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
            query: `mutation { deleteAirline(id: ${airlineId}) }`
        })
    }).then(response => {
        if (response.status === 200) {
            window.location.href = '/home'
        } else {
            new Swal("Cannot delete airline!", `Cannot delete airline ID:${airlineId}.`, "error")
        }
    }).catch(error => new Swal("Cannot delete airline!", `Error: ${error.message}`, "error"))
}
