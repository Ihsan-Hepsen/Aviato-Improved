const button = document.getElementById("searchButton");
const input = document.getElementById("flightNumber");
const tableBody = document.getElementById("tableBody");

button?.addEventListener("click", flightSearch);


function flightSearch() {
    const searchTerm = String(input.value).toUpperCase();

    console.log("FN: ", searchTerm)
    if (searchTerm == null || searchTerm === "" || searchTerm === undefined) {
        return
    }

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
            {
                getFlightByFlightNumber(flightNumber: "${searchTerm}") {
                    airline {
                        airlineName
                    }
                    flightNumber
                    departure
                    arrival
                    flightSchedule
                    onTime
                }
            }`
        })
    })
        .then(response => response.json())
        .then(result => handleFlightSearch(result.data.getFlightByFlightNumber))
        .catch(error => console.log(`Error occurred: ${error.message}`))
}

function handleFlightSearch(flight) {
    tableBody.innerHTML = '';
    // The fetch will return ONLY one flight (flight number is unique for every flight),
    const flightStatus = flight.onTime ? 'On Time' : 'Delayed'
    tableBody.innerHTML += `
        <tr>
            <td>${flight.airline.airlineName}</td>
            <td>${flight.flightNumber}</td>
            <td>${flight.departure}</td>
            <td>${flight.arrival}</td>
            <td>${flight.flightSchedule}</td>
            <td>${flightStatus}</td>
            <td><a href="/flights/details?fn=${flight.flightNumber}"</a>Details</td>
        </tr>`;
}
