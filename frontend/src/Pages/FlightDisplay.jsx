import {useEffect, useState} from "react";
import SortSideBar from "../components/SortSideBar.jsx";
import FlightCard from "../components/FlightCard.jsx";

export default function FlightDisplay() {
    const [flightData, setFlightData] = useState(null);
    const [parsedFlights, setParsedFlights] = useState([]);
    const [sortByDeparture, setSortByDeparture] = useState([]);
    const [sortByArrival, setSortByArrival] = useState([]);
    const [sortByTime, setSortByTime] = useState([]);
    const [departureSortState, setDepartureSortState] = useState("NONE");
    const [arrivalSortState, setArrivalSortState] = useState("NONE");
    const [timeSortState, setTimeSortState] = useState("NONE");
    const [unsortedData, setUnsortedData] = useState([]);

    const Strategy = {
        DEPARTURE: "departure",
        ARRIVAL: "arrival",
    }

    const dummyFlightData = [
        {
            departureDateTime: "2025-04-12T08:30:00",
            arrivalDateTime: "2025-04-12T12:15:00",
            departAirport: "New York (JFK)",
            arrivalAirport: "Los Angeles (LAX)",
            layoverFlights: [],
            flightNumber: "AA100"
        },
        {
            departureDateTime: "2025-04-13T14:45:00",
            arrivalDateTime: "2025-04-13T22:00:00",
            departAirport: "ORD",
            arrivalAirport: "San Francisco (SFO)",
            layoverFlights: [
                {
                    departureDateTime: "2025-04-13T14:45:00",
                    arrivalDateTime: "2025-04-13T17:15:00",
                    departAirport: "ORD",
                    arrivalAirport: "DEN",
                    flightNumber: "UA300"
                },
                {
                    departureDateTime: "2025-04-13T18:00:00",
                    arrivalDateTime: "2025-04-13T22:00:00",
                    departAirport: "DEN",
                    arrivalAirport: "SFO",
                    flightNumber: "UA450"
                }
            ],
            flightNumber: "UA999"
        },
        {
            departureDateTime: "2025-04-14T06:20:00",
            arrivalDateTime: "2025-04-14T09:30:00",
            departAirport: "ATL",
            arrivalAirport: "MIA",
            layoverFlights: [],
            flightNumber: "DL404"
        }
    ];

    /**
     * On page Load:
     * Request: getResultingFlights will be called with the form data
     * Response: Payload will contain the found data and page contents will be loaded
     * Access of session storage: an hastable to access for data
     * if null, return to homepage
     */
    useEffect(() => {
        setFlightData(dummyFlightData)
    }, [])

    // useEffect(() => {
    //     if (flightData === null) {
    //         //response thats like error getting data, navigate to homepage and
    //     }
    // }, [flightData])

    const parseData = () => {
        return JSON.parse(sessionStorage.getItem("FlightDataResponse"));
    }
    // make the Departure Date Time string into Date objects
    useEffect(() => {
        //if theres no data in flightData / ParsedData, do nothing
        if (!flightData) return;

        const updatedFlights = flightData.map(flight => ({
            ...flight,
            departureDateTime: new Date(flight.departureDateTime),
            arrivalDateTime: new Date(flight.arrivalDateTime),
        }));

        setParsedFlights(updatedFlights);
        setUnsortedData(updatedFlights);
    }, [flightData])

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
                return a.departureDateTime < b.departureDateTime;
            } else if (strategy === Strategy.ARRIVAL) {
                return a.arrivalDateTime < b.arrivalDateTime;
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
        } else if ( arrivalSortState !== "NONE"){
            let sorted = mergeSort([...parsedFlights], Strategy.ARRIVAL);
            if(arrivalSortState === "UP") sorted.reverse();
            setParsedFlights(sorted);
        } else (
            setParsedFlights(unsortedData)
        )
    }, [departureSortState, arrivalSortState, unsortedData]);

    const createFlightCards = (flights, key) => {
        return (
            <div key={key} className="p-1">
                <FlightCard flightData={flights}/>
            </div>
        )
    }

    return (
        <section className="relative min-h-screen">
            <div className="w-full mx-auto">
                <div className="flex max-w-[78rem] m-auto p-4 align-top">
                    <aside className="sticky top-0 mt-8">
                        <SortSideBar departureSort={setDepartureSortState} arrivalSort={setArrivalSortState} timeSort={setTimeSortState}/>
                    </aside>
                    <div className="flex-col mt-8 w-full">
                        {parsedFlights &&
                            parsedFlights.map((flight, index) => (
                                createFlightCards(flight, `flightCard-${index}`)
                            ))
                        }
                    </div>
                </div>

            </div>
        </section>
    )
}