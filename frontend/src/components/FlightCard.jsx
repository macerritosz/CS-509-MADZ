import {useContext, useEffect, useState} from "react";
import SortSideBar from "../components/SortSideBar.jsx";
import {Button, Card, CardBody, Typography} from "@material-tailwind/react";
import { ArrowRightIcon} from "@heroicons/react/16/solid/index.js";

export default function FlightCard() {
    return (
        <div>
            <Card>
                <CardBody>
                    <Typography className = "relative">
                        Flight Number
                    </Typography>
                    <div className="flex gap-5 ">
                        <div>
                            <div className="flex gap-2 ">
                                <Typography>
                                    Departure Time
                                </Typography>
                                <ArrowRightIcon className="w-5 h-5" />
                                <Typography>
                                    Arrival Time
                                </Typography>
                            </div>
                            <div className="flex gap-6">
                                <Typography id="DepartureTimeValue">
                                    Departure Time
                                </Typography>
                                <Typography id="ArrivalTimeValue">
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