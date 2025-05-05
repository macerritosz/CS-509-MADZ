import SortSideBar from "../components/SortSideBar.jsx";
import {useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";

export default function UserFlightsPage() {
    const navigate = useNavigate();
    const [bookingData, setBookingData] = useState(null);


    useEffect(async () => {
        const userId = localStorage.getItem("userId");
        if (userId === null) {
            navigate("/")
        } else {
            try {
                const response = await fetch(`/api/userBookings/${id}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                })
                if (response.ok) {
                    const result = await response.json();
                    setBookingData(result.flightData)
                }
            } catch (error) {
                console.log("Error getting user data: ", error);
            }
        }
    })


    return (
        <section className="relative min-h-screen">
            <div className="w-full mx-auto">
                <div className="flex max-w-[78rem] m-auto p-4 align-top">
                    <aside className="sticky top-0 mt-8">
                        <SortSideBar departureSort={setDepartureSortState} arrivalSort={setArrivalSortState} timeSort={setTimeSortState}/>
                    </aside>
                    <div className="flex-col mt-8 w-full">
                        {bookingData &&
                            bookingData.map((flight, index) => (
                                createFlightCards(flight, `flightCard-${index}`)
                            ))
                        }
                    </div>
                </div>

            </div>
        </section>
    )
}