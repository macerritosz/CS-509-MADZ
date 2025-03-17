/** @type {(tailwindConfig: object) => object} */
const withMT = require("@material-tailwind/react/utils/withMT");
const config = ({
    content: [
        "./src/app/**/*.{js,ts,jsx,tsx}",
        "./src/components/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                text: '#120808',
                background: { DEFAULT: '#f5f5f5' },
                primary: '#076e5f',
                secondary: '#f3ec85',
                accent: '#78c593',
            },
        },
    },
    safelist: [
        "text-primary", "border-primary", "checked:border-primary",
        "text-secondary", "border-secondary", "checked:border-secondary",
        "text-accent", "border-accent", "checked:border-accent",
    ],
    plugins: [],
});

module.exports = withMT(config);