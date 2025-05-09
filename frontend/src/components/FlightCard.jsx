import {useEffect, useState} from "react";
import {Button, Card, CardBody, Collapse, Typography} from "@material-tailwind/react";
import {ArrowRightIcon} from "@heroicons/react/16/solid/index.js";
import LayoverCollapse from "./LayoverCollapse.jsx";
import BookFlightConfirmation from "./BookFlightConfirmation.jsx";

export default function FlightCard(props) {
    const [open, setOpen] = useState(false);
    const [confirmOpen, setConfirmOpen] = useState(false);
    const [layovers, setLayovers] = useState([]);
    const [allFlightData, setAllFlightData] = useState(props.data);
    const [layoverTimes, setLayoverTimes] = useState([]);
    const [endFlights, setEndFlights] = useState([]);

    const handleLayoversOpen = () => {
        setOpen((cur) => !cur);
    }

    const getTime = (date) => {
        const dateObj = new Date(date);
        const dateStr = dateObj.toLocaleDateString([], {
            month: 'short',
            day: 'numeric',
        });
        const timeStr = dateObj.toLocaleTimeString([], {
            hour: '2-digit',
            minute: '2-digit',
            hour12: true,
        });
        return `${dateStr}, ${timeStr}`;
    };
    
    useEffect(() => {
        setAllFlightData(props.data)
    }, [])

    const parseLayovers = () => {
        let flightList = allFlightData?.flightPath || [];
        let layovers = []
        // Check that it isn't a direct flight, and if is not, push all the flights to be made into cards for display
        if(flightList.length !== 1) {
            for (let i = 0; i < flightList.length - 1; i++) {
                layovers.push(flightList[i]);
            }
        }
        //get all the times from the list
        const timeList = allFlightData?.flightTimes
        let layoverTimeList = []

        for (let i = 1; i < timeList.length; i++) {
            layoverTimeList.push(timeList[i])
        }
        setLayoverTimes(layoverTimeList)

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
                                    <Typography className="text-lg border-b border-b-primary max-w-[175px]">
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
                                    <Typography className="text-lg border-b border-b-primary max-w-[175px]">
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
                            {
                                props.isDisplay ? (<> </>
                                    ): 
                                (<Button onClick={() => setConfirmOpen(true)}>
                                    Book Flight
                                </Button>)
                            }
                        </div>
                    </CardBody>
                </Card>
            </div>
            {
                allFlightData && layoverTimes && layoverTimes.length > 0 &&
                allFlightData.flightPath.length > 0 ? (
                    <div>
                        <Collapse open={open}>
                            <LayoverCollapse layovers={allFlightData.flightPath} layoverTimes = {layoverTimes} />
                        </Collapse>
                    </div>
                ) : (<Typography></Typography>)
            }
            {allFlightData && (
                <BookFlightConfirmation
                    open={confirmOpen}
                    onClose={() => setConfirmOpen(false)}
                    airline = {allFlightData.airline}
                    data={allFlightData.flightPath}
                    timeData={allFlightData.flightTimes}
                    returnFlight = {props.doReturn}
                />
            )}
        </div>
    )
}