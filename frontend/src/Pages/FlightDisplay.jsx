import {useEffect, useState} from "react";
import SortSideBar from "../components/SortSideBar.jsx";
import FlightCard from "../components/FlightCard.jsx";
import {useNavigate} from "react-router-dom";
import {IconButton, Typography} from "@material-tailwind/react";
import {ArrowLeftIcon, ArrowRightIcon} from "@heroicons/react/16/solid/index.js";

export default function FlightDisplay() {
    const navigate = useNavigate();
    const [flightData, setFlightData] = useState(null);
    const [parsedFlights, setParsedFlights] = useState([]);
    const [departureSortState, setDepartureSortState] = useState("NONE");
    const [arrivalSortState, setArrivalSortState] = useState("NONE");
    const [timeSortState, setTimeSortState] = useState("NONE");
    const [unsortedData, setUnsortedData] = useState([]);
    const [currentFlights, setCurrentFlights] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 10; // or whatever number you want per page


    const Strategy = {
        DEPARTURE: "departure",
        ARRIVAL: "arrival",
    }

    /**
     * On page Load:
     * Request: getResultingFlights will be called with the form data
     * Response: Payload will contain the found data and page contents will be loaded
     * if null, return to homepage
     */
    useEffect(() => {
        setFlightData(parseData())
    }, [])

    const parseData = () => {
        const data = sessionStorage.getItem("FlightDataResponse");
        if (!data) return null;
        return JSON.parse(data);
    }
    // make the Departure Date Time string into Date objects
    useEffect(() => {
        //if theres no data in flightData / ParsedData, do nothing
        if (!flightData) return;

        const updatedFlights = parseFlightData(flightData);

        setParsedFlights(updatedFlights);
        setUnsortedData(updatedFlights);
    }, [flightData])

    function parseFlightData(rawData) {
        const allGroups = [];

        if (rawData.southwestFlights) {
            allGroups.push(...rawData.southwestFlights);
        }
        if (rawData.deltaFlights) {
            allGroups.push(...rawData.deltaFlights);
        }

        return allGroups;
    }

    // Do Merge sort when user changes sort strategy default is
    function merge(arr, left, right, strategy) {
        let result = []
        let i = 0;
        let j = 0

        /*
        Compare takes in two Flights and compares if the departure time in element L[i] is less than the one on the
        right and adds to corresponding list
         */
        const compare = (a, b) => {
            if (strategy === Strategy.DEPARTURE) {
                return a.flightPath[0].departureDateTime < b.flightPath[0].departureDateTime;
            } else if (strategy === Strategy.ARRIVAL) {
                return a.flightPath[a.flightPath.length - 1 ].arrivalDateTime < b.flightPath[a.flightPath.length - 1 ].arrivalDateTime;
            } else if (strategy === Strategy.PRICE) {
                return a.price < b.price;
            }
            return true; // default to keep order
        };

        while (i < left.length && j < right.length) {
            if (compare(left[i], right[j])) {
                result.push(left[i]);
                i++;
            } else {
                result.push(right[j])
                j++;
            }
        }

        return result.concat(left.slice(i)).concat(right.slice(j));
    }

    function mergeSort(arr, strategy) {
        if(arr.length <= 1) return arr;

        const mid = Math.floor(arr.length / 2);
        const left = mergeSort(arr.slice(0, mid), strategy);
        const right = mergeSort(arr.slice(mid), strategy);
        return merge(arr, left, right, strategy);
    }


    useEffect(() => {
        if(departureSortState !== "NONE") {
            let sorted = mergeSort([...parsedFlights], Strategy.DEPARTURE);
            if(departureSortState === "UP") sorted.reverse();
            setParsedFlights(sorted);
            setCurrentPage(1);
        } else if ( arrivalSortState !== "NONE"){
            let sorted = mergeSort([...parsedFlights], Strategy.ARRIVAL);
            if(arrivalSortState === "UP") sorted.reverse();
            setParsedFlights(sorted);
            setCurrentPage(1);
        } else {
            setParsedFlights(unsortedData);
            setCurrentPage(1);
        }
    }, [departureSortState, arrivalSortState, unsortedData]);

    useEffect(() => {
        console.log("Current page changed:", currentPage);
        const flightsToDisplay = parsedFlights.slice(
            (currentPage - 1) * itemsPerPage,
            currentPage * itemsPerPage
        );
        setCurrentFlights(flightsToDisplay);
    }, [parsedFlights, currentPage]);

    const totalPages = Math.ceil(parsedFlights.length / itemsPerPage);

    const handleNextPage = () => {
        if (currentPage < totalPages) setCurrentPage(currentPage + 1);
    };

    const handlePrevPage = () => {
        if (currentPage > 1) setCurrentPage(currentPage - 1);
    };

    const createFlightCards = (data, key) => {
        return (
            <div key={key} className="p-1">
                <FlightCard data={data}/>
            </div>
        )
    }

    useEffect(() => {
        console.log(currentFlights);
    }, [currentFlights]);

    return (
        <section className="relative min-h-screen">
            <div className="w-full mx-auto">
                <div className="flex max-w-[78rem] m-auto p-4 align-top">
                    <aside className="sticky top-0 mt-8">
                        <SortSideBar departureSort={setDepartureSortState} arrivalSort={setArrivalSortState} timeSort={setTimeSortState}/>
                    </aside>
                    <div className="flex-col justify-items-center">
                        <div className="flex-col mt-8 w-full">
                            {currentFlights.length > 0 ?
                                (currentFlights.map((flightGroup, index) => (
                                    createFlightCards(flightGroup, `flightCard-${flightGroup.flightPath[0].departureDateTime}-${flightGroup.flightTimes[0]}-${(totalPages*itemsPerPage) - (index * currentPage)}`)
                                ))) :
                                (
                                    <div>No flights to display.</div>
                                )
                            }
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
                                disabled={currentPage === totalPages - 1}
                            >
                                <ArrowRightIcon strokeWidth={2} className="h-4 w-4" />
                            </IconButton>
                        </div>
                    </div>
                </div>

            </div>
        </section>
    )
}