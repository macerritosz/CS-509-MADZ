import {
    Alert,
    Button,
    Card,
    CardBody,
    Carousel,
    Checkbox,
    Input, List, ListItem,
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

    const carouselImages = [
        "./istockphoto-1629109811-612x612.jpg",
        "./pexels-sergei-a-1322276-2539430.jpg"
    ]
    /*
    TODO
    On selecting one way, set the arrival date to
    Same-day flight option

    Display 5 from all flight locaions in drop down
    Use cookies to save the last search and auto fill everything, except return date
     */
    const getFilteredLocations = (input, type) => {
        if(type === "departure") {
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
        if(!checkValidDates()) return;
        if(localStorage.getItem("userID") == null) {
            setIsSignedIn(false);
            setShowSignInAlert(true);
        } else {
            try {
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
                    navigate('/Flights');
                }
            } catch (error) {
                console.log("Form Submission Error: ", error);
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
        if(!isOneway && (!formData.returnDate || new Date(formData.returnDate) < new Date(formData.departureDate))){
            setShowInvalidDateAlert(true)
            return false
        }
        setShowInvalidDateAlert(false); // Hide tooltip if valid
        return true;
    }

    useEffect(() => {
        console.log(showInvalidDateAlert)
    }, [showInvalidDateAlert]);

    useEffect(() => {
        console.log(departureSuggestion);
    }, [departureSuggestion]);
    return (
        <section className="homepage flex flex-col justify-center items-center">
            <div className="content-start w-full h-full ">
                <div className=" relative w-full h-[30rem]">
                    <img src="/StockCake-Expansive%20Cloudy%20Sky_1746028727.jpg"
                         className="max-h-[30rem] w-full object-cover"/>
                    <div id="madz-home-form-holder" className="absolute inset-0 flex items-center justify-center">
                        {
                            showSignInAlert && (
                                <div className="absolute top-4 left-1/2 transform -translate-x-1/2 z-50 w-full max-w-[40rem]">
                                    <Alert open={showSignInAlert} onClose={() => setShowSignInAlert(false)} color={"green"}>
                                        <div className="flex gap-2 items-center">
                                            <BellAlertIcon className=" w-5 h-5" />
                                            <Typography>
                                                Sign In to book your flights!
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
                                <form id="madz-main-flight-form" className="items-center h-full" onSubmit={handleSubmit}>
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
                                            />
                                            {departureSuggestion.length > 0 && openMenu && (
                                                <List className="absolute z-15 w-full bg-white shadow-lg rounded-md mt-1 text-text">
                                                    {departureSuggestion.slice(0, 4).map((loc, idx) => (
                                                        <ListItem
                                                            key={loc.DepartAirport}  // Use the name or unique attribute as key
                                                            className="cursor-pointer text-sm p-2"
                                                            onClick={() => {
                                                                setFormData({...formData, departureAirport: loc.DepartAirport});
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
                                            />
                                            {arrivalSuggestion.length > 0 && openMenu && (
                                                <List className="absolute z-15 w-full bg-white shadow-lg rounded-md mt-1 text-text">
                                                    {arrivalSuggestion.slice(0, 4).map((loc, idx) => (
                                                        <ListItem
                                                            key={loc.ArriveAirport}  // Use the name or unique attribute as key
                                                            className="cursor-pointer p-2"
                                                            onClick={() => {
                                                                setFormData({...formData, arrivalAirport: loc.ArriveAirport});
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
                                                     handler={() => {}}
                                                     className="bg-red-400"
                                                     content={"Invalid Date"}
                                                     animate={{
                                                         mount: {scale: 1, y: 0},
                                                         unmount: {scale: 0, y: 25},
                                                     }}
                                                     placement="right">
                                                <span className="inline-block w-full">
                                                    <Calendar
                                                        type={"return"}
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
                <div id="madz-main-carousel" className="min-h-[600px] m-auto max-w-[78rem] px-4 z-10">
                    <Carousel transition={{duration: 2}} autoplay={true} autoplayDelay={7500} loop={true}
                              className=" w-full rounded-xl">
                        {carouselImages.map((image, index) => (
                            <div key={index}>
                                <img src={image} alt={`slide-${index}`} className="w-full h-[600px] object-cover"/>
                            </div>
                        ))}
                    </Carousel>
                </div>
            </div>
        </section>
    )
}

export default Homepage;
