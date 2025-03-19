import {Button} from "@material-tailwind/react";
import '../styles/tailwind.css';

function Header() {
    return (
        <header className="madz-header">
            <div className="madz-header-container p-4 bg-accent/75  ">
                <div className="madz-header-nav-container pl-2 pr-2 flex ">
                    <div id="madz-header-logo" className="p-4 font-bold">
                      <a href={"/"}>
                          LOGO GOES HERE
                      </a>
                        {
                            /* Logo Idea: aerodynamics line in our colors around text: WPI, to higher places*/
                        }
                    </div>
                    <nav className="madz-header-nav flex items-center ms-auto">
                        <div id="madz-header-nav-pages">
                            <div id="madz-header-nav-booking">

                            </div>
                            <div id="madz-header-nav-mytrips">

                            </div>
                        </div>
                        <div id="madz-nav-authentication-items" className="flex gap-2 ">
                            <div id="madz-header-signup">
                                <Button className="text-sm">
                                    Signup
                                </Button>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </header>
    )
}

export default Header;
