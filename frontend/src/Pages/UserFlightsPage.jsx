import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import SortSideBar from "../components/SortSideBar.jsx";
import FlightCard from "../components/FlightCard.jsx";
import { IconButton, Typography } from "@material-tailwind/react";
import { ArrowLeftIcon, ArrowRightIcon } from "@heroicons/react/16/solid";
import {mergeSort} from "../utils/mergeSort.js";

export default function UserFlightsPage() {
    const navigate = useNavigate();
    const [bookingData, setBookingData] = useState([]);
    const [departureSortState, setDepartureSortState] = useState("NONE");
    const [arrivalSortState, setArrivalSortState] = useState("NONE");
    const [timeSortState, setTimeSortState] = useState("NONE");
    const [unsortedData, setUnsortedData] = useState([]);
    const [currentFlights, setCurrentFlights] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 10;

    const Strategy = {
        DEPARTURE: "departure",
        ARRIVAL: "arrival",
        TIME: "time",
    };

    useEffect(() => {
        const fetchUserBookings = async () => {
            const userId = localStorage.getItem("userID");
            if (!userId) {
                navigate("/");
                return;
            }
            try {
                const response = await fetch(`/api/userBookings/${userId}`, {
                    method: "GET",
                    headers: { "Content-Type": "application/json" },
                });
                if (response.ok) {
                    const result = await response.json();
                    setBookingData(result.flightData);
                    setUnsortedData(result.flightData);
                } else {
                    console.error("Failed to fetch user bookings");
                }
            } catch (error) {
                console.error("Error getting user data:", error);
            }
        };

        fetchUserBookings();
    }, [navigate]);


    useEffect(() => {
        let sorted = [...bookingData];
        if (departureSortState !== "NONE") {
            sorted = mergeSort(sorted, Strategy.DEPARTURE);
            if (departureSortState === "UP") sorted.reverse();
        } else if (arrivalSortState !== "NONE") {
            sorted = mergeSort(sorted, Strategy.ARRIVAL);
            if (arrivalSortState === "UP") sorted.reverse();
        } else if (timeSortState !== "NONE") {
            sorted = mergeSort(sorted, Strategy.TIME);
            if (timeSortState === "UP") sorted.reverse();
        } else {
            sorted = [...unsortedData];
        }
        setBookingData(sorted);
        setCurrentPage(1);
    }, [departureSortState, arrivalSortState, timeSortState, unsortedData]);

    useEffect(() => {
        const flightsToDisplay = bookingData.slice(
            (currentPage - 1) * itemsPerPage,
            currentPage * itemsPerPage
        );
        setCurrentFlights(flightsToDisplay);
    }, [bookingData, currentPage]);
    const totalPages = Math.ceil(bookingData.length / itemsPerPage);

    const handleNextPage = () => {
        if (currentPage < totalPages) setCurrentPage(currentPage + 1);
    };

    const handlePrevPage = () => {
        if (currentPage > 1) setCurrentPage(currentPage - 1);
    };

    const createFlightCards = (flight, key) => (
        <div key={key} className="p-1">
            <FlightCard data={flight} doReturn={false} isDisplay={true} />
        </div>
    );

    return (
        <section className="relative min-h-screen">
            <div className="w-full mx-auto">
                <div className="flex max-w-[78rem] m-auto p-4 align-top">
                    <aside className="sticky top-0 mt-8">
                        <SortSideBar
                            departureSort={setDepartureSortState}
                            arrivalSort={setArrivalSortState}
                            timeSort={setTimeSortState}
                        />
                    </aside>
                    <div className="flex-col justify-items-center w-full">
                        <div className="flex-col mt-8 w-full">
                            {currentFlights.length > 0 ? (
                                currentFlights.map((flight, index) =>
                                    createFlightCards(flight, `userFlight-${index}`)
                                )
                            ) : (
                                <div>No bookings to display.</div>
                            )}
                        </div>
                        <div className="flex items-center gap-8 mt-5">
                            <IconButton
                                size="sm"
                                variant="outlined"
                                onClick={handlePrevPage}
                                disabled={currentPage === 1}
                            >
                                <ArrowLeftIcon strokeWidth={2} className="h-4 w-4" />
                            </IconButton>
                            <Typography color="gray" className="font-normal">
                                Page <strong className="text-gray-900">{currentPage}</strong> of{" "}
                                <strong className="text-gray-900">{totalPages}</strong>
                            </Typography>
                            <IconButton
                                size="sm"
                                variant="outlined"
                                onClick={handleNextPage}
                                disabled={currentPage === totalPages}
                            >
                                <ArrowRightIcon strokeWidth={2} className="h-4 w-4" />
                            </IconButton>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
}
