import React, {useEffect, useState} from "react";
import {Button, Card, CardBody, CardFooter, Checkbox, Dialog, Input, Typography,} from "@material-tailwind/react";

export function SignInModal(props) {
    const [isSignUp, setIsSignUp] = useState(false);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [invalidPassword, setInvalidPassword] = useState(false);
    const [checkPasswordReqs, setCheckPasswordReqs] = useState({
        isLength: false,
        upperChar: false,
        numberChar: false,
        specialChar: false
    });

    const handleUsernameChange = (e) => {
        setUsername(e.target.value);
    }
    const handlePasswordChange = (e) => {
        if (isSignUp) {
            setCheckPasswordReqs({
                isLength: e.target.value.length >= 8,
                upperChar: /[A-Z]/.test(e.target.value),
                numberChar: /[0-9]/.test(e.target.value),
                specialChar: /[!@#$%^&*()]/.test(e.target.value),
            });
        }
        setPassword(e.target.value);
    }

    useEffect(() => {
        setInvalidPassword(false);
    }, [isSignUp])

    async function signIn() {
        try {
            console.log(username, password);
            const payload = JSON.stringify({"username": username, "password": password});
            const response = await fetch("/api/signIn", {
                method: "POST",
                body: payload,
                headers: {"Content-Type": "application/json"}
            })
            if (response.ok) {
                const result = await response.json();
                localStorage.setItem("userID", result.UserID)
            }
        } catch (error) {
            console.error('Invalid Signin: ', error);
        }
    }

    async function signUp() {
        try {
            console.log(username, password);
            const payload = JSON.stringify({"username": username, "password": password});
            const response = await fetch("/api/signUp", {
                method: "POST",
                body: payload,
                headers: {"Content-Type": "application/json"}
            })
            if (response.ok) {
                await response.json();
                setIsSignUp(false);
            } else {

            }
        } catch (error) {
            console.error('Invalid Signin: ', error);
        }
    }

    const handleSubmit = async () => {
        if (isSignUp) {
            if (validPassword()) {
                await signUp()
            } else {
                setInvalidPassword(true)
            }
        } else {
            await signIn()
            props.handleOpen()
        }
    }
    const validPassword = () => {
        return checkPasswordReqs.isLength && checkPasswordReqs.upperChar && checkPasswordReqs.numberChar && checkPasswordReqs.specialChar;
    }

    const updateVisualPasswordChecks = () => {
        return (
            <div>
                <Typography className="flex">
                    {
                        changeValidationSvg(checkPasswordReqs.isLength)
                    }
                    At least 8 Characters
                </Typography>
                <Typography className="flex">
                    {
                        changeValidationSvg(checkPasswordReqs.upperChar)
                    }
                    At least one uppercase letter
                </Typography>
                <Typography className="flex">
                    {
                        changeValidationSvg(checkPasswordReqs.numberChar)
                    }
                    At least one number (0-9)
                </Typography>
                <Typography className="flex">
                    {
                        changeValidationSvg(checkPasswordReqs.specialChar)
                    }
                    At least one special character: !@#$%^&*()
                </Typography>
            </div>
        )
    }

    const changeValidationSvg = (target) => {
        if (target) {
            return <img src="/checkmark-svgrepo-com.svg" className="w-[20px]" alt="CheckMark"/>
        } else {
            return <img src="/cross-svgrepo-com.svg" className="w-[20px]" alt="Cross"/>
        }
    }

    return (
        <>
            <Dialog
                size="xs"
                open={props.open}
                handler={props.handleOpen}
                className="bg-transparent shadow-none"
            >
                <Card className="mx-auto w-full max-w-[24rem]">
                    <CardBody className="flex flex-col gap-4">
                        <Typography variant="h4" color="blue-gray">
                            {isSignUp ? "Sign Up" : "Sign In"}
                        </Typography>
                        <Typography
                            className="mb-2 text-text font-extralight"
                            variant="paragraph"
                            color="gray"
                        >
                            {isSignUp ? "Create your account." : "Enter your Username and Password to Sign In."}
                        </Typography>
                        <Typography className="-mb-2 text-text" variant="h6">
                            Your Username
                        </Typography>
                        <Input label="Username" size="lg" onChange={handleUsernameChange} value={username}/>
                        <Typography className="-mb-2 text-text" variant="h6">
                            Your Password
                        </Typography>
                        <Input label="Password" size="lg" type="password" onChange={handlePasswordChange}
                               value={password}/>
                        {
                            isSignUp && invalidPassword ? (
                                <Typography className="text-red">
                                    &#9940; Invalid Password
                                </Typography>
                            ) : <></>
                        }
                        {
                            isSignUp ? (updateVisualPasswordChecks()) :
                                (
                                    <div className="-ml-2.5 -mt-3">
                                        <Checkbox label="Remember Me"/>
                                    </div>
                                )
                        }
                    </CardBody>
                    <CardFooter className="pt-0">
                        <Button onClick={handleSubmit} fullWidth className="hover:bg-primary  ">
                            {isSignUp ? "Sign Up" : "Sign In"}
                        </Button>
                        <Typography variant="small" className="flex justify-center mt-4 text-text/80">
                            {
                                isSignUp ? "Already have an account?" : "Don't have an account?"
                            }
                            <Typography
                                as="a"
                                href="#signup"
                                variant="small"
                                color="blue-gray"
                                className="ml-1 font-bold hover: hover:text-primary"
                                onClick={() => setIsSignUp(!isSignUp)}
                            >
                                {isSignUp ? "Sign In" : "Sign Up"}
                            </Typography>
                        </Typography>
                    </CardFooter>
                </Card>
            </Dialog>
        </>
    );
}
