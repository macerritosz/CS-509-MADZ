import {useContext, useEffect, useState} from "react";
import SortSideBar from "../components/SortSideBar.jsx";
import {Button, Card, CardBody, Typography} from "@material-tailwind/react";
import { ArrowRightIcon} from "@heroicons/react/16/solid/index.js";
import FlightCard from "../components/FlightCard.jsx";

export default function FlightDisplay() {
    /**
     * On page Load:
     * Request: getResultingFlights will be called with the form data
     * Response: Payload will contain the found data and page contents will be loaded
     */
    /*
    useEffect(() => {
        getResultingFlights()
    })
    const getResultingFlights = () => (
    )
    */

    /*
    const
     */

    return (
        <section className="box-border flex relative">
            <SortSideBar />
            <div className="m-auto">
                <div id="mt-8">
                   <FlightCard />
                </div>
            </div>
        </section>
    )
}