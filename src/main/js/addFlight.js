import Swal from 'sweetalert2'

const addButton = document.getElementById('addButton')
addButton.addEventListener('click', addFlight)

function addFlight() {
    const airline = document.getElementById('airline').value
    const flightNumber = document.getElementById('flightNumber').value
    const flightType = document.getElementById('flightType').value
    const departure = document.getElementById('departure').value
    const arrival = document.getElementById('arrival').value
    const date = document.getElementById('date').value
    const status = document.getElementById('flightStatus').checked

    if (airline == null || airline === '' || flightNumber == null || flightNumber === '' || departure == null ||
        departure === '' || arrival == null || arrival === '' || date == null || date === '') {
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
            query: `
            mutation {
                addFlight(arrival: "${arrival}",
                departure: "${departure}",
                flightNumber: "${flightNumber}",
                flightType: ${flightType},
                airline: "${airline}",
                onTime: ${status},
                scheduledOn: "${date}") {
                    flightNumber
                }
            }`
        })
    }).then(response => {
        if (response.status === 200) {
            new Swal("Done!", `Flight '${flightNumber}' successfully added to the system.`, "success")
            window.location.href = `/flights/airline?an=${airline}`
        } else {
            console.log(`Cannot add flight: ${response.status}`)
        }
    }).catch(error => new Swal("Cannot add flight!", `Error: ${error.message}`, "error"))
}
