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
                background: { DEFAULT: '#ffffff' },
                primary: '#076e5f',
                secondary: '#f3ec85',
                accent: '#78c593',
            },
        },
    },
    plugins: [],
});

module.exports = withMT(config);