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
                    color: "text-text",
                    fontWeight: "font-normal",
                    userSelect: "select-none",
                    cursor: "cursor-pointer",
                    mt: "mt-px",

                },
                icon: {
                    color: {
                        primary: "text-primary",
                    }
                },
                disabled: {},
            },
            colors: {
                primary: {
                    color: "#AC2B37",  // Directly using the hex value
                    border: "checked:border-[#AC2B37]",
                    before: "checked:before:bg-[#AC2B37]",
                },
                secondary: {
                    color: "#A9B0B7",
                    border: "checked:border-[#A9B0B7]",
                    before: "checked:before:bg-[#A9B0B7]",
                },
                accent: {
                    color: "#000000",
                    border: "checked:border-[#000000]",
                    before: "checked:before:bg-[#000000]",
                },

            }
        },
    },
}
