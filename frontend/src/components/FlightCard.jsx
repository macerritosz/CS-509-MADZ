import {useEffect, useState} from "react";
import {Button, Card, CardBody, Collapse, Typography} from "@material-tailwind/react";
import {ArrowRightIcon} from "@heroicons/react/16/solid/index.js";
import LayoverCollapse from "./LayoverCollapse.jsx";

export default function FlightCard(props) {
    const [open, setOpen] = useState(false);
    const [layovers, setLayovers] = useState([]);
    const [allFlightData, setAllFlightData] = useState(props.data);
    const [endFlights, setEndFlights] = useState([]);

    const handleLayoversOpen = () => {
        setOpen((cur) => !cur);
    }

    const getTime = (date) => {
        let dateStr = new Date(date);
        return dateStr.toLocaleString([], {
            year: 'numeric',
            month: 'short',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            hour12: true
        });
    }
    
    useEffect(() => {
        setAllFlightData(props.data)
    }, [])

    const parseLayovers = () => {
        let flightList = allFlightData?.flightPath || [];
        let layovers = []
        if(flightList.length > 1) {
            layovers.push(flightList[0])
            layovers.push(flightList[1])
            if(flightList.length > 3) {
                layovers.push(flightList[2])
            }
        }
        return layovers;
    }
    const parseEndFlight = () => {
        let flightList = allFlightData?.flightPath || [];
        let endFlights = []
        endFlights.push(flightList[0])
        if(flightList.length > 1) {
            endFlights.push(flightList[flightList.length - 1])
        }
        return endFlights;
    }

    useEffect(() => {
        if (allFlightData && allFlightData.flightPath) {
            setLayovers(parseLayovers());
            setEndFlights(parseEndFlight());
        }
    }, [allFlightData]);

    return (
        <div>
            <div>
                <Card className="flightCardInformation hover">
                    <CardBody className="text-text">
                        <div className="flex justify-between items-center gap-4">
                            <div className="flex gap-4 items-center">
                                <div className="min-w-[150px]">
                                    <Typography>
                                        {
                                            endFlights[0]?.flightNumber
                                        }
                                    </Typography>
                                    <Typography className="text-lg border-b border-b-primary max-w-[150px]">
                                        {
                                            endFlights[0]?.departureLocation
                                        }
                                    </Typography>
                                    <Typography id="DepartureTimeValue">
                                        {
                                          getTime(endFlights[0]?.departureDateTime)
                                        }
                                    </Typography>
                                </div>
                                <ArrowRightIcon className="w-7 h-7"/>
                                <div className="min-w-[150px]">
                                    <Typography>
                                        {
                                            (endFlights.length > 1) ? endFlights[1]?.flightNumber : ( endFlights[0]?.flightNumber )
                                        }
                                    </Typography>
                                    <Typography className="text-lg border-b border-b-primary max-w-[150px]">
                                        {
                                           (endFlights.length > 1) ? endFlights[1]?.arrivalLocation : endFlights[0]?.arrivalLocation
                                        }
                                    </Typography>
                                    <Typography id="ArrivalTimeValue">
                                        {
                                            (endFlights.length > 1) ? getTime(endFlights[1]?.arrivalDateTime) : getTime(endFlights[0]?.arrivalDateTime)
                                        }
                                    </Typography>
                                </div>
                            </div>
                            <div  className="flex flex-col items-start gap-2 min-w-[150px]">
                                <Typography className="text-text text-lg">
                                    {
                                        "Layovers:" + layovers.length
                                    }
                                </Typography>
                                <Button onClick={handleLayoversOpen}
                                        disabled={layovers.length === 0}>
                                    View Layover Info
                                </Button>
                            </div>

                            <Button>
                                Flight Submit
                            </Button>
                        </div>
                    </CardBody>
                </Card>
            </div>
            {
                allFlightData &&
                allFlightData.flightPath.length > 0 ? (
                    <div>
                        <Collapse open={open}>
                            <LayoverCollapse layovers={allFlightData.flightPath}/>
                        </Collapse>
                    </div>
                ) : (<Typography></Typography>)
            }
        </div>
    )
}