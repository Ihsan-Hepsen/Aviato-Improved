const button = document.getElementById("searchButton");
button.addEventListener("click", passengerSearch);
const nameInput = document.getElementById("searchName");
const tableBody = document.getElementById("tableBody");

function passengerSearch() {
    const searchTerm = nameInput.value;

    fetch(`http://localhost:4506/api/passengers/${searchTerm}`,
        {
            method: "GET"
        })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else if (response.status === 204) {
                handlePassengers([]);
            } else {
                alert(`Search failed. Status: ${response.status}`);
            }
        })
        .then(passengers => {
            handlePassengers(passengers);
        })
        .catch(error => alert(`Error occurred: ${error.message}`))
}

function handlePassengers(passengerArray) {
    tableBody.innerHTML = '';
    for (let passenger of passengerArray) {
        console.log(passenger);
        tableBody.innerHTML += `
        <tr>
            <td>${passenger.name}</td>
            <td>${passenger.age}</td>
            <td>${passenger.gender}</td>
            <td>${passenger.transitPassenger}</td>
            <td>${passenger.flights.length}</td>
            <td><a href="../passengers/details?pn=${passenger.name}"></a>Details</td>
        </tr>`;
    }
}
