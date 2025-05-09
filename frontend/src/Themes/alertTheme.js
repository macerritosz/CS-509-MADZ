export const alertTheme = {
    alert: {
        defaultProps: {},
        valid: {
            colors: ["primary", "secondary", "accent"],
        },
        styles: {
            base: {
                alert: {
                    position: "relative",
                    display: "block",
                    width: "w-full",
                    fontFamily: "font-sans",
                    fontSize: "text-base",
                    fontWeight: "font-regular",
                    px: "px-4",
                    py: "py-4",
                    borderRadius: "rounded-lg",
                },
                action: {
                    position: "!absolute",
                    top: "top-3",
                    right: "right-3",
                },
            },
            variants: {
                filled: {
                    primary: {
                        color: "text-white",
                        background: "bg-[#AC2B37]/70",
                    },
                    secondary: {
                        color: "text-white",
                        background: "bg-[#A9B0B7]",
                    },
                    accent: {
                        color: "text-white",
                        background: "bg-[#000000]",
                    },
                },
            },
        },
    },
};
