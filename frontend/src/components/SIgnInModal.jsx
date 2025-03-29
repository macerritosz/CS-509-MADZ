import React, {useState} from "react";
import {Button, Card, CardBody, CardFooter, Checkbox, Dialog, Input, Typography,} from "@material-tailwind/react";

export function SIgnInModal(props) {
    const [isSignUp, setIsSignUp] = useState(false);
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    async function signIn() {
        try {
            const response = await fetch("/signin", {
                method: "POST",
            })
            if(response.ok) {
                return await response.json();
            }
        } catch (error) {
            console.error('Invalid Signin: ', error);
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
                            className="mb-3 font-normal"
                            variant="paragraph"
                            color="gray"
                        >
                            {isSignUp ? "Create your account." : "Enter your Username and Password to Sign In."}
                        </Typography>
                        <Typography className="-mb-2" variant="h6">
                            Your Username
                        </Typography>
                        <Input label="Username" size="lg"/>
                        <Typography className="-mb-2" variant="h6">
                            Your Password
                        </Typography>
                        <Input label="Password" size="lg"/>
                        <div className="-ml-2.5 -mt-3">
                            <Checkbox label="Remember Me"/>
                        </div>
                    </CardBody>
                    <CardFooter className="pt-0">
                        <Button onClick={props.handleOpen} fullWidth className="!bg-accent">
                            Sign In
                        </Button>
                        <Typography variant="small" className="mt-4 flex justify-center">
                            Don&apos;t have an account?
                            <Typography
                                as="a"
                                href="#signup"
                                variant="small"
                                color="blue-gray"
                                className="ml-1 font-bold"
                                onClick={() => setIsSignUp(!isSignUp)}
                            >
                                Sign up
                            </Typography>
                        </Typography>
                        <div className="mt-4 mb-4 text-center">
                            <button
                                    className="py-2 p-4 bg-black text-white font-semibold border border-black rounded-md hover:bg-gray-900 hover:border-gray-900 cursor-pointer active:bg-gray-600">
                              <span className="flex justify-center items-center gap-2">
                                  <img src="/github.svg" alt="GitHub" width="30" height="30"/>
                                  Continue with GitHub
                              </span>
                            </button>
                        </div>
                        <div className="mt-4 mb-4 text-center">
                            <button
                                    className="py-2 p-4 bg-black text-white font-semibold border border-black rounded-md hover:bg-gray-900 hover:border-gray-900 cursor-pointer active:bg-gray-600">
                              <span className="flex justify-center items-center gap-2">
                                <img src="/google.svg" alt="Google" width="30" height="30"/>
                                Continue with Google
                              </span>
                            </button>
                        </div>
                    </CardFooter>
                </Card>
            </Dialog>
        </>
    );
}