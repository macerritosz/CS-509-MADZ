import {useState} from "react";
import {Button, Card, CardBody, Collapse, Typography} from "@material-tailwind/react";
import {ArrowRightIcon} from "@heroicons/react/16/solid/index.js";
import LayoverCollapse from "./LayoverCollapse.jsx";

export default function FlightCard(props) {
    const [open, setOpen] = useState(false);
    const handleLayoversOpen = () => {
        setOpen((cur) => !cur);
    }

    const getTime = (date) => {
        let datestr = date.split("T");
        return datestr[1];
    }

    return (
        <div>
            <div>
                <Card className="flightCardInformation hover">
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
                                <ArrowRightIcon className="w-7 h-7"/>
                                <div>
                                    <Typography className="text-xl border-b border-b-black">
                                        {
                                            props.flightData.arrivalAirport
                                        }
                                    </Typography>
                                    <Typography id="ArrivalTimeValue">
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
                                <Button onClick={handleLayoversOpen}
                                        disabled={props.flightData.layoverFlights.length === 0}>
                                    View Layover Info
                                </Button>
                            </div>

                            <Button className="block" id="full card size button">
                                Flight Submit
                            </Button>
                        </div>
                    </CardBody>
                </Card>
            </div>
            {
                Array.isArray(props.flightData.layoverFlights)
                && props.flightData.layoverFlights.length > 0 ? (
                    <div>
                        <Collapse open={open}>
                            <LayoverCollapse layovers={props.flightData.layoverFlights}/>
                        </Collapse>
                    </div>
                ) : (<Typography></Typography>)
            }
        </div>
    )
}