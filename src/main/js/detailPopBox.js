import { createPopper } from '@popperjs/core';

const button = document.querySelector('#button');
const tooltip = document.querySelector('#tooltip');

const popperInstance = createPopper(button, tooltip, {
    modifiers: [
        {
            name: 'offset',
            options: {
                offset: [0, 8],
            },
        },
    ],
});

function show() {
    tooltip.setAttribute('data-show', '');
    popperInstance.update();
}

function hide() {
    tooltip.removeAttribute('data-show');
}

const showEvents = ['mouseenter', 'focus'];
const hideEvents = ['mouseleave', 'blur'];

showEvents.forEach((event) => {
    button.addEventListener(event, show);
});

hideEvents.forEach((event) => {
    button.addEventListener(event, hide);
});
