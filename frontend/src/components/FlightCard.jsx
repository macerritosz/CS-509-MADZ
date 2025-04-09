import {useContext, useEffect, useState} from "react";
import SortSideBar from "../components/SortSideBar.jsx";
import {Button, Card, CardBody, Typography} from "@material-tailwind/react";
import { ArrowRightIcon} from "@heroicons/react/16/solid/index.js";

export default function FlightCard() {
    return (
        <div >
            <Card>
                <CardBody className="text-text">
                    <Typography>
                        Flight Number
                    </Typography>
                    <div className="flex justify-between ">
                        <div className="flex gap-2">
                            <div className="">
                                <Typography className="text-xl border-b border-b-black">
                                    Departure Time
                                </Typography>
                                <Typography id="DepartureTimeValue">
                                    Departure Time
                                </Typography>
                            </div>
                            <ArrowRightIcon className="w-7 h-7" />
                            <div>
                                <Typography className="text-2xl border-b border-b-black">
                                    Arrival Time
                                </Typography>
                                <Typography id="ArrivalTimeValue" >
                                    Arrival Time
                                </Typography>
                            </div>
                        </div>
                        <div>
                            <Typography>
                                Layover Number
                            </Typography>
                            <Button>
                                View Layover Info
                            </Button>
                        </div>

                        <Button className = "block" id="full card size button">
                            Flight Submit
                        </Button>
                    </div>
                </CardBody>
            </Card>
        </div>
    )
}