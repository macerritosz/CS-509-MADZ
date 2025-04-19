import {useContext, useEffect, useState} from "react";
import SortSideBar from "../components/SortSideBar.jsx";
import {Button, Card, CardBody, Typography} from "@material-tailwind/react";
import { ArrowRightIcon} from "@heroicons/react/16/solid/index.js";

export default function FlightCard(props) {
    const [date, setDate] = useState({departDate: "", arrivalDate: ""});
    const [time, setTime] = useState({departTime:"", arrivalTime:""});

    const getDate = (date) => {
        let datestr = date.split("T");
        return datestr[0];
    }

    const getTime = (date) => {
        let datestr = date.split("T");
        return datestr[1];
    }

    return (
        <div>
            <Card>
                <CardBody className="text-text">
                    <Typography>
                        {
                            props.flightData.flightNumber
                        }
                    </Typography>
                    <div className="flex justify-between ">
                        <div className="flex gap-2">
                            <div className="">
                                <Typography className="text-xl border-b border-b-black">
                                    {
                                        props.flightData.departAirport
                                    }
                                </Typography>
                                <Typography id="DepartureTimeValue">
                                    {
                                        getTime(props.flightData.departureDateTime)
                                    }
                                </Typography>
                            </div>
                            <ArrowRightIcon className="w-7 h-7" />
                            <div>
                                <Typography className="text-xl border-b border-b-black">
                                    {
                                        props.flightData.arrivalAirport
                                    }
                                </Typography>
                                <Typography id="ArrivalTimeValue" >
                                    {
                                        getTime(props.flightData.arrivalDateTime)
                                    }
                                </Typography>
                            </div>
                        </div>
                        <div>
                            <Typography className="text-text text-lg">
                                {
                                    "Layovers:" + props.flightData.layoverFlights.length
                                }
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