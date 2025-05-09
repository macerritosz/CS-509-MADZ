import {
    Alert,
    Button,
    Card,
    CardBody,
    Carousel,
    Checkbox,
    Input,
    List,
    ListItem,
    Radio,
    Tooltip,
    Typography,
} from "@material-tailwind/react";
import Calendar from "../components/Calendar.jsx";
import {BellAlertIcon, PaperAirplaneIcon,} from "@heroicons/react/16/solid/index.js";
import {useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import depLocation from "../assets/locations/ALL_airport_departure.json"
import arrLocation from "../assets/locations/ALL_airport_arrivals.json"
import Loading from "../components/Loading.jsx";


function Homepage() {
    const navigate = useNavigate();
    const [isSignedIn, setIsSignedIn] = useState(false);
    const [showSignInAlert, setShowSignInAlert] = useState(false);
    const [showInvalidDateAlert, setShowInvalidDateAlert] = useState(false);
    const [availableDepartureLocations, setAvailableDepartureLocations] = useState([]);
    const [availableArrivalLocations, setAvailableArrivalLocations] = useState([]);
    const [departureSuggestion, setDepartureSuggestion] = useState([]);
    const [arrivalSuggestion, setArrivalSuggestion] = useState([]);
    const [openMenu, setOpenMenu] = useState(false);
    const [invalidLocationsAlert, setInvalidLocationsAlert] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    /* User input states */
    const [formData, setFormData] = useState({
        departureAirport: "",
        arrivalAirport: "",
        departureDate: "",
        arrivalDate: "",
        isSameDay: false,
        isDirect: false,
    })

    const [isOneway, setOneway] = useState(true);

    //const [cookies, setCookies] = useCookies(["recentAirSearch"])

    useEffect(() => {
        setAvailableDepartureLocations(depLocation);
        setAvailableArrivalLocations(arrLocation);
    }, []);


    const getFilteredLocations = (input, type) => {
        if (type === "departure") {
            return availableDepartureLocations.filter(loc =>
                loc.DepartAirport.toLowerCase().includes(input.toLowerCase())
            );
        } else {
            return availableArrivalLocations.filter(loc =>
                loc.ArriveAirport.toLowerCase().includes(input.toLowerCase())
            );
        }
    };

    /** cookie to hold last user search */
    useEffect(() => {
        const lastSearch = JSON.parse(localStorage.getItem('recentSearch'));
        if (lastSearch) {
            setFormData({...formData, ...lastSearch});
        }
    }, []);


    /**
     * API Call Handler
     * Request: POST: Form contents for getting within database an associated functions
     * Response: OK to confirm redirect
     * @param e
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!checkValidDates()) return;
        if (!checkValidLocation()) return;
        if (localStorage.getItem("userID") == null) {
            setIsSignedIn(false);
            setShowSignInAlert(true);
        } else {
            try {
                setIsLoading(true);

                const jsonFormData = JSON.stringify(formData);
                const response = await fetch("/api/submit", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: jsonFormData,
                })
                if (response.ok) {
                    const result = await response.json();
                    sessionStorage.setItem("FlightDataResponse", JSON.stringify(result.allBookings));
                    sessionStorage.setItem("FlightFormData", JSON.stringify(formData));
                    navigate('/Flights');
                }
            } catch (error) {
                console.log("Form Submission Error: ", error);
            } finally {
                setIsLoading(false);
            }
        }
    }

    const handleChange = (e) => {
        if (e.target.name === "isSameDay" || e.target.name === "isDirect") {
            setFormData({...formData, [e.target.name]: e.target.checked});
        } else {
            setFormData({...formData, [e.target.name]: e.target.value});
        }
    }

    useEffect(() => {
        console.log(formData)
    }, [formData])


    const checkValidDates = () => {
        if (!isOneway && (!formData.arrivalDate || new Date(formData.arrivalDate) < new Date(formData.departureDate))) {
            setShowInvalidDateAlert(true)
            return false
        }
        setShowInvalidDateAlert(false); // Hide tooltip if valid
        return true;
    }

    const checkValidLocation = () => {
        if (formData.departureAirport === formData.arrivalAirport) {
            return false
        }
        setInvalidLocationsAlert(false);
        return true;
    }

    return (
        <section className="homepage flex flex-col justify-center items-center">
            {(isLoading || navigation.state === "loading") && <Loading />}
            <div className="content-start w-full h-full ">
                <div className=" relative w-full h-[30rem]">
                    <img src="/StockCake-Expansive%20Cloudy%20Sky_1746028727.jpg"
                         className="max-h-[30rem] w-full object-cover"/>
                    <div id="madz-home-form-holder" className="absolute inset-0 flex items-center justify-center">
                        {
                            showSignInAlert && (
                                <div
                                    className="absolute top-4 left-1/2 transform -translate-x-1/2 z-50 w-full max-w-[40rem]">
                                    <Alert open={showSignInAlert} onClose={() => setShowSignInAlert(false)} variant={"filled"}
                                           color="accent">
                                        <div className="flex gap-2 items-center">
                                            <BellAlertIcon className=" w-5 h-5"/>
                                            <Typography>
                                                Sign In to book your flights!
                                            </Typography>
                                        </div>
                                    </Alert>
                                </div>
                            )
                        }
                        {
                            invalidLocationsAlert && (
                                <div
                                    className="absolute top-4 left-1/2 transform -translate-x-1/2 z-50 w-full max-w-[40rem]">
                                    <Alert open={invalidLocationsAlert}
                                           onClose={() => setInvalidLocationsAlert(false)}
                                           variant={"filled"}
                                           color="accent">
                                        <div className="flex gap-2 items-center">
                                            <BellAlertIcon className=" w-5 h-5"/>
                                            <Typography>
                                                Departure and Arrival cannot be the same.
                                            </Typography>
                                        </div>
                                    </Alert>
                                </div>
                            )
                        }
                        <Card className="w-full max-w-[78rem] backdrop-blur-md rounded-lg m-auto " id="madz-form-card">
                            <CardBody className="p-4">
                                <Typography variant="h4" component="h2" className="mt-2 mb-2 pl-3 text-text">
                                    Book Flights through WPI
                                </Typography>
                                <form id="madz-main-flight-form" className="items-center h-full"
                                      onSubmit={handleSubmit}>
                                    <div className="flex justify-between pb-1">
                                        <div id="madz-radio-flight-type" className="flex gap-5">
                                            <Radio name="flight-type"
                                                   label="One-way"
                                                   onClick={() => {
                                                       setOneway(true)
                                                   }}
                                                   defaultChecked
                                            />
                                            <Radio name="flight-type"
                                                   label="Round-Trip"
                                                   onClick={() => {
                                                       setOneway(false)
                                                   }}
                                            />
                                        </div>
                                        <div>
                                            <Checkbox label="Same-Day Flights Only" color="secondary" name="isSameDay"
                                                      onChange={handleChange}/>
                                            <Checkbox label="Direct Flights Only" color="secondary" name="isDirect"
                                                      onChange={handleChange}/>
                                        </div>
                                    </div>
                                    <hr/>
                                    <div className="flex justify-evenly gap-2 p-3">
                                        <div className="relative w-full max-w-[20rem] ">
                                            <Input label="From"
                                                   name="departureAirport"
                                                   value={formData.departureAirport}
                                                   className="text-text"
                                                   size="lg"
                                                   onChange={(e) => {
                                                       handleChange(e)
                                                       setDepartureSuggestion(getFilteredLocations(e.target.value, "departure"))
                                                   }}
                                                   onFocus={() => setOpenMenu(true)}
                                                   onBlur={() => setTimeout(() => setOpenMenu(false), 150)}
                                                   required={true}
                                            />
                                            {departureSuggestion.length > 0 && openMenu && (
                                                <List
                                                    className="absolute z-15 w-full bg-white shadow-lg rounded-md mt-1 text-text">
                                                    {departureSuggestion.slice(0, 4).map((loc, idx) => (
                                                        <ListItem
                                                            key={loc.DepartAirport}
                                                            className="cursor-pointer text-sm p-2"
                                                            onClick={() => {
                                                                setFormData({
                                                                    ...formData,
                                                                    departureAirport: loc.DepartAirport
                                                                });
                                                                setDepartureSuggestion([]);
                                                                setOpenMenu(false)
                                                            }}
                                                        >
                                                            <Typography className="text-sm">
                                                                {loc.DepartAirport}
                                                            </Typography>
                                                        </ListItem>
                                                    ))}
                                                </List>
                                            )}
                                        </div>
                                        <div className="relative w-full max-w-[20rem]">
                                            <Input label="To"
                                                   name="arrivalAirport"
                                                   value={formData.arrivalAirport}
                                                   className="text-text"
                                                   size="lg"
                                                   onChange={(e) => {
                                                       handleChange(e)
                                                       setArrivalSuggestion(getFilteredLocations(e.target.value, "Arrival"))
                                                   }}
                                                   onFocus={() => setOpenMenu(true)}
                                                   onBlur={() => setTimeout(() => setOpenMenu(false), 150)}
                                                   required={true}
                                            />
                                            {arrivalSuggestion.length > 0 && openMenu && (
                                                <List
                                                    className="absolute z-15 w-full bg-white shadow-lg rounded-md mt-1 text-text">
                                                    {arrivalSuggestion.slice(0, 4).map((loc, idx) => (
                                                        <ListItem
                                                            key={loc.ArriveAirport}  // Use the name or unique attribute as key
                                                            className="cursor-pointer p-2"
                                                            onClick={() => {
                                                                setFormData({
                                                                    ...formData,
                                                                    arrivalAirport: loc.ArriveAirport
                                                                });
                                                                setArrivalSuggestion([]);
                                                                setOpenMenu(false)
                                                            }}
                                                        >
                                                            <Typography className="text-sm">
                                                                {loc.ArriveAirport}
                                                            </Typography>
                                                        </ListItem>
                                                    ))}
                                                </List>
                                            )}
                                        </div>
                                        <div className="w-full max-w-[16rem]">
                                            <Calendar type={"departure"} isDisabled={false}
                                                      handleChange={(e) => handleChange(e)}/>
                                        </div>
                                        <div className="w-full max-w-[16rem]">
                                            <Tooltip open={showInvalidDateAlert}
                                                     handler={() => {
                                                     }}
                                                     className="bg-red-400"
                                                     content={"Invalid Date"}
                                                     animate={{
                                                         mount: {scale: 1, y: 0},
                                                         unmount: {scale: 0, y: 25},
                                                     }}
                                                     placement="right">
                                                <span className="inline-block w-full">
                                                    <Calendar
                                                        type={"arrival"}
                                                        isDisabled={isOneway}
                                                        handleChange={(e) => handleChange(e)}
                                                    />
                                                </span>
                                            </Tooltip>
                                        </div>

                                    </div>
                                    <div className="pt-2 p-4">
                                        <Button type="submit"
                                                className="!bg-primary flex items-center gap-1 px-8">
                                        <span>
                                            Submit
                                        </span>
                                            <PaperAirplaneIcon className="w-5 h-5"/>
                                        </Button>
                                    </div>

                                </form>
                            </CardBody>
                        </Card>
                    </div>
                </div>
                <div className="w-full max-w-[78rem] p-4 m-auto">
                    <Typography variant="body2" component="p" className="">

                    </Typography>
                </div>
                <div className=" max-w-[78rem] p-4 m-auto">
                    <div className="grid lg:grid-cols-3 justify-items-center">
                        {[...Array(3)].map((_, index) => (
                            <Card key={index} className="w-3/4 h-[300px] shadow-lg">
                                <CardBody className="flex flex-col justify-center items-center">
                                    <Typography variant="h6" className="mb-2">
                                        Placeholder Title {index + 1}
                                    </Typography>
                                    <Typography variant="body2">
                                        This is a placeholder description for this card.
                                    </Typography>
                                </CardBody>
                            </Card>
                        ))}
                    </div>
                </div>
                <div className="w-full max-w-[78rem] p-4 m-auto">
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                        {[...Array(6)].map((_, index) => (
                            <Card key={index} className="w-full h-[200px] p-4 shadow-lg">
                                <CardBody className="flex flex-col justify-center items-center">
                                    <Typography variant="h6" className="mb-2">
                                        Placeholder Title {index + 1}
                                    </Typography>
                                    <Typography variant="body2">
                                        This is a placeholder description for this card.
                                    </Typography>
                                </CardBody>
                            </Card>
                        ))}
                    </div>
                </div>
            </div>
        </section>
    )
}

export default Homepage;
