import {useContext, useEffect, useState} from "react";
import SortSideBar from "../components/SortSideBar.jsx";
import {Button, Card, CardBody, Typography} from "@material-tailwind/react";
import { ArrowRightIcon} from "@heroicons/react/16/solid/index.js";
import FlightCard from "../components/FlightCard.jsx";
import Loading from "../components/Loading.jsx";

export default function FlightDisplay() {
    const [flightData, setFlightData] = useState(null);

    /**
     * On page Load:
     * Request: getResultingFlights will be called with the form data
     * Response: Payload will contain the found data and page contents will be loaded
     * Access of session storage: an hastable to access for data
     */
    useEffect(() => {
         setFlightData(parseData())
    },[])

    useEffect( () => {
        if(flightData === null){
            //response thats like error getting data, navigate to homepage and
        }
    }, [flightData])

    const parseData = () => {
        return JSON.parse(sessionStorage.getItem("FlightDataResponse"));
    }

    const createFlightCards = (flights, key) => {
        return (
            <div id={`flightCard-${key}`} className="p-2">
                <FlightCard flightData={flights} />
            </div>
        )
    }

    return (
        <section className=" flex relative min-h-screen mx-40">
            <aside className="sticky top-0 mt-8">
                <SortSideBar />
            </aside>
            <div className="mt-8 px-10 w-full ">
                { flightData &&
                    flightData.map((flight, index) => (
                        createFlightCards(flight, index)
                    ))
                }
            </div>
        </section>
    )
}