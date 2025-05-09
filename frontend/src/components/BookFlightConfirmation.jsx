import {Button, Card, CardBody, CardFooter, Dialog, Typography} from "@material-tailwind/react";
import React from "react";
import {useNavigate} from "react-router-dom";

export default function BookFlightConfirmation({open, onClose, data, timeData, returnFlight, airline}) {
    const navigate = useNavigate();

    const getTime = (date) => {
        const dateObj = new Date(date);
        return dateObj.toLocaleString([], {
            hour: '2-digit',
            minute: '2-digit',
            hour12: true,
        });
    };

    const getDate = (date) => {
        const dateObj = new Date(date);
        return dateObj.toLocaleDateString([], {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
        });
    };

    const formatDuration = (minutes) => {
        const h = Math.floor(minutes / 60);
        const m = minutes % 60;
        return `${h}h ${m}m`;
    };

    const onConfirm = async () => {
        let userID = localStorage.getItem("userID");
        const params = new URLSearchParams(location.search);
        const isInbound = params.get('inbound');
        console.log(params.get('inbound'));
        console.log(returnFlight)
        if (userID === null) {
            navigate("/");
        } else {
            try {
                const bookingJSON = parseFlightInfoToJson(userID)
                const response = await fetch("/api/bookFlight", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(bookingJSON),
                });

                if (response.ok) {
                    const result = await response.json();

                    if ( (isInbound === null && returnFlight) && location.pathname === '/Flights') {
                        sessionStorage.setItem("LastBookedFlightID", result.bookingId || "");
                        navigate('/Flights?inbound=true');
                    } else {
                        sessionStorage.removeItem("FlightDataReturnResponse");
                        navigate('/UserDisplay');
                    }
                    onClose()
                } else {
                    console.error("Booking return flight failed.");
                }
            } catch (error) {
                console.error("Error booking return flight:", error);
            }
        }
    }


    const parseFlightInfoToJson = (userID) => {

        if (!data || data.length === 0) {
            console.error("No flight data available to parse.");
            return null;
        }

        const mainFlight = data[0];
        console.log(mainFlight);

        const tableId = mainFlight.id;
        const referenceIDs = data.slice(1).map(flight => flight.id);

        while (referenceIDs.length < 3) {
            referenceIDs.push(null);
        }

        return {
            tableId: tableId,
            departDateTime: mainFlight.departureDateTime,
            arriveDateTime: mainFlight.arrivalDateTime,
            departAirport: mainFlight.departureLocation,
            arriveAirport: mainFlight.arrivalLocation,
            flightNumber: mainFlight.flightNumber,
            referenceIDs: referenceIDs,
            userID: userID,
            airline: airline // expected to be "deltas" or "southwests"
        };
    }


    return (
        <Dialog
            size="lg"
            open={open}
            handler={onClose}
            className="bg-transparent shadow-none"
        >
            <Card className="mx-auto w-full max-w-[36rem]">
                <CardBody className="flex flex-col gap-4 text-text">
                    <Typography variant="h4" color="blue-gray">
                        Confirm Your Booking
                    </Typography>
                    <Typography variant="h5">
                        Total trip time: {formatDuration(timeData[0])}
                    </Typography>
                    {data.map((flight, index) => (
                        <div key={index} className="border p-2 rounded-md">
                            <Typography variant="h6">
                                Leg {index + 1}
                            </Typography>
                            <Typography className="text-sm">
                                <strong>Date:</strong> {getDate(flight.departureDateTime)}
                            </Typography>
                            <Typography className="text-sm">
                                <strong>From:</strong> {flight.departureLocation} at {getTime(flight.departureDateTime)}
                            </Typography>
                            <Typography className="text-sm">
                                <strong>To:</strong> {flight.arrivalLocation} at {getTime(flight.arrivalDateTime)}
                            </Typography>
                            <Typography className="text-sm">
                                {index < timeData.length - 1 && (
                                    <span className="text-sm mt-1">
                                        <strong>Layover after this leg:</strong>{" "}
                                        {formatDuration(timeData[index + 1])}
                                    </span>
                                )}
                            </Typography>
                        </div>
                    ))}
                </CardBody>
                <CardFooter className="flex justify-end gap-2">
                    <Button variant="outlined" color="red" onClick={onClose}>
                        Cancel
                    </Button>
                    <Button variant="gradient" color="green" onClick={onConfirm}>
                        Confirm Booking
                    </Button>
                </CardFooter>
            </Card>
        </Dialog>
    );
}
