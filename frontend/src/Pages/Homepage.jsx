import {Button, Card, CardBody, Carousel, Checkbox, Input, Radio,} from "@material-tailwind/react";
import Calendar from "../components/Calendar.jsx";
import {PaperAirplaneIcon} from "@heroicons/react/16/solid/index.js";
import {useNavigate} from "react-router-dom";


function Homepage() {
    const navigate = useNavigate();

    const carouselImages = [
        "/assets/istockphoto-1629109811-612x612.jpg",
        "/assets/pexels-sergei-a-1322276-2539430.jpg"
    ]
    /**
     * API Call Handler
     * Request: POST: Form contents for getting within database an associated functions
     * Response: OK to confirm redirect
     * @param e
     */
    const handleSubmit = (e) => {
        e.preventDefault();
        navigate('/Flights');
    }

    return (
        <section className="homepage flex flex-col justify-center items-center">
            <div className="container mx-auto">
                <div id="madz-home-form-holder" className=" p-4 m-10 rounded-lg">
                    <Card className="w-full" id="madz-form-card">
                        <CardBody>
                            <form id="madz-main-flight-form" className="items-center" onSubmit={handleSubmit}>
                                <div className="flex justify-between">
                                    <div id="madz-radio-flight-type" className="flex gap-5">
                                        <Radio name="flight-type"
                                               label="One-way"
                                               color="accent"
                                               defaultChecked/>
                                        <Radio name="flight-type"
                                               label="Round-Trip"
                                               color="accent"/>
                                    </div>
                                    <div>
                                        <Checkbox label="Direct Flights Only" color="accent"/>
                                    </div>
                                </div>
                                <div className="flex gap-2 mt-2 justify-evenly">
                                    <div className="w-full max-w-[16rem] ">
                                        <Input label="From"
                                               className="text-text"
                                               size="lg"/>
                                    </div>
                                    <div className="w-full max-w-[16rem]">
                                        <Input label="To"
                                               className="text-text"
                                               size="lg"/>
                                    </div>
                                    <div className="w-full max-w-[16rem]">
                                        <Calendar type={"Select a Departure Date"}/>
                                    </div>
                                    <div className="w-full max-w-[16rem]">
                                        <Calendar type={"Select a Arrival Date"}/>
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
                <div id="madz-main-carousel" className=" min-h-[600px] mb-8">
                    <Carousel transition={{ duration: 2 }} autoplay={true} autoplayDelay={7500} loop={true} className=" w-full rounded-xl">
                        {carouselImages.map((image, index) => (
                            <div key={index}>
                                <img src={image} alt={`slide-${index}`} className="w-full h-[600px] object-cover" />
                            </div>
                        ))}
                    </Carousel>
                </div>
            </div>
        </section>
    )
}

export default Homepage;
