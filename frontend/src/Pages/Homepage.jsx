import {Button, Card, CardBody, Carousel, Checkbox, Input, Radio,} from "@material-tailwind/react";
import Calendar from "../components/Calendar.jsx";
import {PaperAirplaneIcon} from "@heroicons/react/16/solid/index.js";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";


function Homepage() {
    const navigate = useNavigate();
    /* User input states */
    const [formData, setFormData] = useState({
        departAirport: "",
        arrivalAirport: "",
        departureDate: "",
        returnDate: "",
        isSameDay: false,
        isDirect: false,
    })

    const [isOneway, setOneway] = useState(true);

    //const [cookies, setCookies] = useCookies(["recentAirSearch"])

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

    /**
     * API Call Handler
     * Request: POST: Form contents for getting within database an associated functions
     * Response: OK to confirm redirect
     * @param e
     */
    const handleSubmit = async (e) => {
        navigate('/Flights');
        // e.preventDefault();
        // try {
        //     appendSearchType();
        //     const jsonFormData = JSON.stringify(formData);
        //     const response = await fetch("/submit", {
        //         method: "POST",
        //         headers: {
        //             ContentType: "application/json",
        //         },
        //         body: jsonFormData,
        //     })
        //     if (response.ok) {
        //         navigate('/Flights');
        //     }
        // } catch (error) {
        //     console.log( "Form Submission Error: ", error);
        // }
    }

    const handleChange = (e) => {
        if (e.target.name === "isSameDay" || e.target.name === "isDirect") {
            setFormData({...formData, [e.target.name]: e.target.checked});
        } else {
            setFormData({...formData, [e.target.name]: e.target.value});
        }
    }

    const appendSearchType = () => {
        setFormData({...formData, isOneway: isOneway});
    }

    useEffect(() => {
        console.log(formData)
    }, [formData])


    return (
        <section className="homepage flex flex-col justify-center items-center">
            <div className="content-start w-full h-screen">
                <div id="madz-home-form-holder" className="rounded-lg max-w-[78rem] m-auto p-4">
                    <Card className="w-full" id="madz-form-card">
                        <CardBody>
                            <form id="madz-main-flight-form" className="items-center" onSubmit={handleSubmit}>
                                <div className="flex justify-between">
                                    <div id="madz-radio-flight-type" className="flex gap-5">
                                        <Radio name="flight-type"
                                               label="One-way"
                                               color="accent"
                                               onClick={() => {
                                                   setOneway(true)
                                               }}
                                               defaultChecked
                                        />
                                        <Radio name="flight-type"
                                               label="Round-Trip"
                                               color="accent"
                                               onClick={() => {
                                                   setOneway(false)
                                               }}
                                        />
                                    </div>
                                    <div>
                                        <Checkbox label="Same-Day Flights Only" color="accent" name="isSameDay"
                                                  onChange={handleChange}/>
                                        <Checkbox label="Direct Flights Only" color="accent" name="isDirect"
                                                  onChange={handleChange}/>
                                    </div>
                                </div>
                                <div className="flex gap-2 mt-2 justify-evenly">
                                    <div className="w-full max-w-[16rem] ">
                                        <Input label="From"
                                               name="departAirport"
                                               className="text-text"
                                               size="lg"
                                               onChange={(e) => {
                                                   handleChange(e)
                                               }}
                                        />
                                    </div>
                                    <div className="w-full max-w-[16rem]">
                                        <Input label="To"
                                               name="arrivalAirport"
                                               className="text-text"
                                               size="lg"
                                               onChange={(e) => {
                                                   handleChange(e)
                                               }}
                                        />
                                    </div>
                                    <div className="w-full max-w-[16rem]">
                                        <Calendar type={"departure"} isDisabled={false}
                                                  handleChange={(e) => handleChange(e)}/>
                                    </div>
                                    <div className="w-full max-w-[16rem]">
                                        <Calendar type={"return"} isDisabled={isOneway}
                                                  handleChange={(e) => handleChange(e)}/>
                                    </div>

                                    <Button type="submit" className="!bg-accent flex items-center gap-1 px-4 py-2">
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
                <div id="madz-main-carousel" className=" min-h-[600px] m-auto max-w-[78rem] px-4">
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
