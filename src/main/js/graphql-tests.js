const addButton = document.getElementById('gql-btn')
addButton.addEventListener('click', addFlight)

function addFlight() {
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
                addFlight(arrival: "Hamilton Island",
                departure: "Gold Coast",
                flightNumber: "AS45",
                flightType: PRIV,
                airline: "Golden Wings",
                onTime: false,
                scheduledOn: "2022-06-06") {
                    id
                    flightNumber
                    departure
                    arrival
                }
            }`
        })
    }).then(response => {
        if (response.status === 200) {
            console.log("Add flight: success")
        } else {
            console.log("Add flight: fail")
        }
    }).catch(error => console.log(`Error occurred: ${error.message}`))
}
