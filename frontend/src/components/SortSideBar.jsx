import {useContext, useEffect, useState} from "react";
import {
    Card, ListItem, ListItemSuffix,
    Typography, List, Button
} from "@material-tailwind/react";
import {ChevronDownIcon, ChevronUpIcon, ChevronUpDownIcon} from "@heroicons/react/24/outline";
import React from "react";


export default function SortSideBar(props) {
    const FilterState = {
        UP:<ChevronUpIcon  className="w-5 h-5" />,
        DOWN:<ChevronDownIcon className="w-5 h-5" />,
        NONE:<ChevronUpDownIcon className="w-5 h-5" />,
    }

    const [departureFilter, setDepartureFilter] = useState("NONE");
    const [arrivalFilter, setArrivalFilter] = useState("NONE");
    const [timeFilter, setTimeFilter] = useState("NONE");

    const handleFilterChange = (filterType) => {
        if (filterType === "departure") {
            setDepartureFilter(departureFilter === "NONE" ? "UP" : departureFilter === "UP" ? "DOWN" : "NONE");
            setArrivalFilter("NONE");
            setTimeFilter("NONE");
        } else if (filterType === "arrival") {
            setArrivalFilter(arrivalFilter === "NONE" ? "UP" : arrivalFilter === "UP" ? "DOWN" : "NONE");
            setDepartureFilter("NONE");
            setTimeFilter("NONE");
        } else if (filterType === "time") {
            setTimeFilter(timeFilter === "NONE" ? "UP" : timeFilter === "UP" ? "DOWN" : "NONE");
            setDepartureFilter("NONE");
            setArrivalFilter("NONE");
        }
    }
    return (
        <aside className="flex sticky top-0 m-4">
            <Card className="p-4">
                <div className="mb-2 p-4">
                    <Typography variant="h6">
                        Filter By:
                    </Typography>
                </div>
                <hr className="my-2" />
                <List>
                    <ListItem onClick={() => handleFilterChange("departure")}>
                        Departure Time
                        <ListItemSuffix>
                            {FilterState[departureFilter]}
                        </ListItemSuffix>
                    </ListItem>
                    <ListItem onClick={() => handleFilterChange("arrival")}>
                        Arrival Time
                        <ListItemSuffix >
                            {FilterState[arrivalFilter]}
                        </ListItemSuffix>
                    </ListItem>
                    <ListItem onClick={() => handleFilterChange("time")}>
                        Travel Time
                        <ListItemSuffix>
                            {FilterState[timeFilter]}
                        </ListItemSuffix>
                    </ListItem>
                </List>
            </Card>
        </aside>
    )
}