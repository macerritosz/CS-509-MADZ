import {Avatar, Button, Menu, MenuHandler, MenuItem, MenuList, Typography} from "@material-tailwind/react";
import {
    ArrowRightStartOnRectangleIcon,
    Cog6ToothIcon,
    InboxArrowDownIcon,
    LifebuoyIcon,
    PowerIcon,
    UserCircleIcon,
} from "@heroicons/react/24/solid";
import React, {useState} from "react";

export function Profile({handleLogout}){

    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const closeMenu = () => setIsMenuOpen(false);

    const profileMenuItems = [
        {
            label: "My Profile",
            icon: UserCircleIcon,
        },
        {
            label: "Settings",
            icon: Cog6ToothIcon,
        },
        {
            label: "Sign Out",
            icon: ArrowRightStartOnRectangleIcon,
        },
    ];

   return  (
       <Menu open={isMenuOpen} handler={setIsMenuOpen} placement="bottom-end">
        <MenuHandler>
            <Button
                variant="text"
                color="blue-gray"
                className="flex items-center rounded-full p-0"
            >
                <Avatar
                    variant="circular"
                    size="md"
                    alt="user"
                    withBorder={true}
                    color="gray"
                    className=" p-0.5"
                    src="https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg"
                />
            </Button>
        </MenuHandler>
        <MenuList className="p-1">
            {profileMenuItems.map(({ label, icon }, key) => {
                const isLastItem = key === profileMenuItems.length - 1;
                return (
                    <MenuItem
                        key={label}
                        onClick={closeMenu}
                        className={`flex items-center gap-2 rounded ${
                            isLastItem
                                ? "hover:bg-red-500/10 focus:bg-red-500/10 active:bg-red-500/10"
                                : ""
                        }`}
                    >
                        {React.createElement(icon, {
                            className: `h-4 w-4 ${isLastItem ? "text-red-500" : ""}`,
                            strokeWidth: 2,
                        })}
                        {isLastItem ? (<Typography
                            as="span"
                            variant="small"
                            className="font-normal"
                            color={isLastItem ? "red" : "inherit"}
                            onClick={handleLogout}
                        >
                            {label}
                        </Typography>)
                            :(<Typography
                            as="span"
                            variant="small"
                            className="font-normal"
                            color={isLastItem ? "red" : "inherit"}
                        >
                            {label}
                        </Typography>)}
                    </MenuItem>
                );
            })}
        </MenuList>
    </Menu>
    )
}