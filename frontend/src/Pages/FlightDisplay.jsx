import {useEffect, useState} from "react";
import SortSideBar from "../components/SortSideBar.jsx";
import FlightCard from "../components/FlightCard.jsx";

export default function FlightDisplay() {
    const [flightData, setFlightData] = useState(null);

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

    useEffect(() => {
        if (flightData === null) {
            //response thats like error getting data, navigate to homepage and
        }
    }, [flightData])

    const parseData = () => {
        return JSON.parse(sessionStorage.getItem("FlightDataResponse"));
    }

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
                        <SortSideBar/>
                    </aside>
                    <div className="flex-col mt-8 w-full">
                        {flightData &&
                            flightData.map((flight, index) => (
                                createFlightCards(flight, `flightCard-${index}`)
                            ))
                        }
                    </div>
                </div>

            </div>
        </section>
    )
}