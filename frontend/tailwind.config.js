/** @type {(tailwindConfig: object) => object} */
import withMT from "@material-tailwind/react/utils/withMT";

const config = ({
    mode: "jit",
    content: [
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                text: '#120808',
                background: {DEFAULT: '#f5f5f5'},
                primary: '#AC2B37',
                secondary: '#A9B0B7',
                accent: '#000000',
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

export default withMT(config);