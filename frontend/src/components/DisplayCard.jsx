import { useEffect, useState } from "react";
import {
    Button,
    Card,
    CardBody,
    Collapse,
    Dialog,
    DialogBody,
    DialogFooter,
    DialogHeader,
    Typography
} from "@material-tailwind/react";
import { ArrowRightIcon } from "@heroicons/react/16/solid/index.js";
import LayoverCollapse from "./LayoverCollapse.jsx";

export default function DisplayCard({ data, isDisplay }) {
    const [open, setOpen] = useState(false);
    const [detailsOpen, setDetailsOpen] = useState(false);
    const [layovers, setLayovers] = useState([]);
    const [layoverTimes, setLayoverTimes] = useState([]);
    const [endFlights, setEndFlights] = useState([]);

    const handleDetailsOpen = () => {
        setDetailsOpen((cur) => !cur);
    };

    const getTime = (date) => {
        const dateObj = new Date(date);
        const dateStr = dateObj.toLocaleDateString([], {
            month: "short",
            day: "numeric",
        });
        const timeStr = dateObj.toLocaleTimeString([], {
            hour: "2-digit",
            minute: "2-digit",
            hour12: true,
        });
        return `${dateStr}, ${timeStr}`;
    };

    useEffect(() => {
        if (data && data.flightPath) {
            setLayovers(parseLayovers());
            setEndFlights(parseEndFlight());
        }
    }, [data]);

    const formatDuration = (minutes) => {
        const h = Math.floor(minutes / 60);
        const m = minutes % 60;
        return `${h}h ${m}m`;
    };

    const parseLayovers = () => {
        let flightList = data?.flightPath || [];
        let layovers = [];
        if (flightList.length !== 1) {
            for (let i = 0; i < flightList.length - 1; i++) {
                layovers.push(flightList[i]);
            }
        }
        const timeList = data?.flightTimes;
        let layoverTimeList = [];
        for (let i = 1; i < timeList.length; i++) {
            layoverTimeList.push(timeList[i]);
        }
        setLayoverTimes(layoverTimeList);

        return layovers;
    };

    const parseEndFlight = () => {
        let flightList = data?.flightPath || [];
        let endFlights = [];
        endFlights.push(flightList[0]);
        if (flightList.length > 1) {
            endFlights.push(flightList[flightList.length - 1]);
        }
        return endFlights;
    };

    return (
        <div>
            <Card className="flightCardInformation hover">
                <CardBody className="text-text">
                    <div className="flex justify-between items-center gap-4">
                        <div className="flex gap-4 items-center">
                            <div className="min-w-[150px]">
                                <Typography>
                                    {endFlights[0]?.flightNumber}
                                </Typography>
                                <Typography className="text-lg border-b border-b-primary max-w-[175px]">
                                    {endFlights[0]?.departureLocation}
                                </Typography>
                                <Typography id="DepartureTimeValue">
                                    {getTime(endFlights[0]?.departureDateTime)}
                                </Typography>
                            </div>
                            <ArrowRightIcon className="w-7 h-7" />
                            <div className="min-w-[150px]">
                                <Typography>
                                    {endFlights.length > 1
                                        ? endFlights[1]?.flightNumber
                                        : endFlights[0]?.flightNumber}
                                </Typography>
                                <Typography className="text-lg border-b border-b-primary max-w-[175px]">
                                    {endFlights.length > 1
                                        ? endFlights[1]?.arrivalLocation
                                        : endFlights[0]?.arrivalLocation}
                                </Typography>
                                <Typography id="ArrivalTimeValue">
                                    {endFlights.length > 1
                                        ? getTime(endFlights[1]?.arrivalDateTime)
                                        : getTime(endFlights[0]?.arrivalDateTime)}
                                </Typography>
                            </div>
                        </div>
                        <div className="flex flex-col items-start gap-2 min-w-[150px]">
                            <Typography className="text-text text-lg">
                                {"Layovers:" + layovers.length}
                            </Typography>
                            <Button onClick={handleDetailsOpen}>View Details</Button>
                        </div>
                    </div>
                </CardBody>
            </Card>

            <Dialog open={detailsOpen} handler={handleDetailsOpen}>
                <DialogHeader>
                    Flight Details

                </DialogHeader>
                <DialogBody className="text-text">
                    <Typography variant="h5">
                        Total trip time: {formatDuration(data.flightTimes[0])}
                    </Typography>
                    {data.flightPath.map((flight, index) => (
                        <div key={index} className="border p-2 rounded-md mb-2">
                            <Typography variant="h6">Leg {index + 1}</Typography>
                            <Typography className="text-sm">
                                <strong>Date:</strong> {getTime(flight.departureDateTime)}
                            </Typography>
                            <Typography className="text-sm">
                                <strong>From:</strong> {flight.departureLocation} at{" "}
                                {getTime(flight.departureDateTime)}
                            </Typography>
                            <Typography className="text-sm">
                                <strong>To:</strong> {flight.arrivalLocation} at{" "}
                                {getTime(flight.arrivalDateTime)}
                            </Typography>
                            {index < data.flightPath.length - 1 && (
                                <Typography className="text-sm mt-1">
                                    <strong>Layover after this leg:</strong>{" "}
                                    {formatDuration(data.flightTimes[index + 1])}
                                </Typography>
                            )}
                        </div>
                    ))}
                </DialogBody>
                <DialogFooter>
                    <Button onClick={handleDetailsOpen} className="hover:bg-primary">
                        Close
                    </Button>
                </DialogFooter>
            </Dialog>
        </div>
    );
}
