import {Button} from "@material-tailwind/react";
import '../styles/tailwind.css';
import {useEffect, useState} from "react";
import {SignInModal} from "./SignInModal.jsx";
import {Profile} from "./Profile.jsx";

function Header() {
    const [open, setOpen] = useState(false);
    const [isLoggedIn, setIsLoggedIn] = useState(false);


    const handleModalOpen = () => {
        setOpen((cur) => !cur);
    }
    useEffect(() => {
        const userId = localStorage.getItem("userID");
        setIsLoggedIn(!!userId);
    }, []);

    // Add listener if needed when SignInModal updates state
    const handleLoginSuccess = () => {
        setIsLoggedIn(true);
        handleModalOpen();
    };

    const handleLogout = () => {
        localStorage.removeItem("userID");
        setIsLoggedIn(false);
    };

    return (
        <header className="madz-global-header">
            <div className="madz-global-header-container block">
                <div className="madz-header-container flex flex-col justify-center p-4 bg-primary">
                    <div className="madz-header-nav-container items-center m-auto w-full">
                        <div className=" flex flex-nowrap items-center justify-between max-w-[78rem] mx-auto px-4">
                            <div>
                                <a href={"/"} className="font-bold p-4">
                                    LOGO GOES HERE
                                </a>
                                {
                                    /* Logo Idea: aerodynamics line in our colors around text: WPI, to higher places*/
                                }
                            </div>
                            <div className="flex items-center gap-4">
                                <Button className="text-sm">
                                    My Flights
                                </Button>
                                {
                                    isLoggedIn ? (
                                        <Profile handleLogout={handleLogout} />
                                    ) : (
                                        <Button className="text-sm" onClick={handleModalOpen}>
                                            Sign in
                                        </Button>
                                    )
                                }
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {
                open && <SignInModal open={open} handleOpen={handleModalOpen} onClose={handleModalOpen} onLoginSuccess = {handleLoginSuccess}/>
            }
        </header>
    )
}

export default Header;
