import {useContext, useEffect, useState} from "react";
import SortSideBar from "../components/SortSideBar.jsx";
import {Button, Card, CardBody, Typography} from "@material-tailwind/react";
import { ArrowRightIcon} from "@heroicons/react/16/solid/index.js";
import FlightCard from "../components/FlightCard.jsx";

export default function FlightDisplay() {
    /**
     * On page Load:
     * Request: getResultingFlights will be called with the form data
     * Response: Payload will contain the found data and page contents will be loaded
     */
    /*
    useEffect(() => {
        getResultingFlights()
    })
    const getResultingFlights = () => (
    )
    */

    /*
    const
     */

    function createFlightDisplay() {
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

    return (
        <section className="box-border flex relative">
            <SortSideBar />
            <div className="m-auto">
                <div id="mt-8">
                   <FlightCard />
                </div>
            </div>
        </section>
    )
}