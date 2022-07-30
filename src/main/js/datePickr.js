import flatpickr from "flatpickr"

// date
flatpickr('.flatpickr.js-flatpickr-date', {
    enableTime: false,
    altInput: true,
    minDate: 'today',
    altFormat: 'd M Y',
    dateFormat: 'Y-m-d'
});
