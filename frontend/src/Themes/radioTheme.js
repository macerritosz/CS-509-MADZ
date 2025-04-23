export const radioTheme = {
    radio: {
        defaultProps: {},
        valid: {
            colors: [
                "primary",
                "secondary",
                "accent"
            ],
        },
        styles: {
            base: {
                root: {
                    display: "inline-flex",
                    alignItems: "items-center",
                },
                container: {},
                input: {},
                label: {
                    color: "text-gray-700",
                    fontWeight: "font-light",
                    userSelect: "select-none",
                    cursor: "cursor-pointer",
                    mt: "mt-px",

                },
                icon: {
                    color: {
                        primary: "text-accent",
                    }
                },
                disabled: {},
            },
            colors: {
                primary: {
                    color: "#076e5f",  // Directly using the hex value
                    border: "checked:border-[#076e5f]",
                    before: "checked:before:bg-[#076e5f]",
                },
                secondary: {
                    color: "#f3ec85",
                    border: "checked:border-[#f3ec85]",
                    before: "checked:before:bg-[#f3ec85]",
                },
                accent: {
                    color: "#78c593",
                    border: "checked:border-[#78c593]",
                    before: "checked:before:bg-[#78c593]",
                },

            }
        },
    },
}
