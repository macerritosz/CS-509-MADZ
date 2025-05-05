import {Button, Card, CardBody, Collapse, Typography} from "@material-tailwind/react";
import {ArrowRightIcon} from "@heroicons/react/16/solid/index.js";
import LayoverCollapse from "./LayoverCollapse.jsx";
import {useState} from "react";

export default function BookingCard(props) {
    const [open, setOpen] = useState(false);
    const handleLayoversOpen = () => {
        setOpen((cur) => !cur);
    }

    const getTime = (date) => {
        return date.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit', hour12: true});
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
                        <div className="flex justify-between items-start gap-4">
                            <div className="flex gap-4 items-center">
                                <div className="min-w-[200px]">
                                    <Typography className="text-lg border-b border-b-black max-w-[180px]">
                                        {
                                            props.flightData.departureAirport
                                        }
                                    </Typography>
                                    <Typography id="DepartureTimeValue">
                                        {
                                            getTime(props.flightData.departureDateTime)
                                        }
                                    </Typography>
                                </div>
                                <ArrowRightIcon className="w-7 h-7"/>
                                <div className="min-w-[200px]">
                                    <Typography className="text-lg border-b border-b-black max-w-[180px]">
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
                            <div  className="flex flex-col items-start gap-2 min-w-[150px]">
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