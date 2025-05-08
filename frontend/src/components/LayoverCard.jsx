import {Card, CardBody, Typography} from "@material-tailwind/react";


export default function LayoverCard(props) {

    const getTime = (date) => {
        let dateStr = new Date(date);
        return dateStr.toLocaleString([], {
            hour: '2-digit',
            minute: '2-digit',
            hour12: true
        });
    }

    const timeToHours = (minutesTotal) => {
        const hours = Math.floor(minutesTotal / 60);
        const minutes = minutesTotal % 60;
        let parts = [];
        if (hours > 0) parts.push(`${hours}h`);
        if (minutes > 0) parts.push(`${minutes}m`);
        return parts.join(" ") || "0m";
    };

    return (
        <Card className="mb-4 shadow-md">
            <CardBody className="text-text">
                <div className="flex">
                    <div className="relative bg-primary w-[4px] m-2">
                        <div className="absolute top-0 left-1/2 transform -translate-x-1/2 w-3 h-3 bg-primary rounded-full"></div>
                        <div className="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-3 h-3 bg-primary rounded-full"></div>
                    </div>

                    <div className="flex flex-col w-full gap-y-6">
                        <div className="flex items-center">
                            <Typography className="ml-2 min-w-[120px] max-h-[25px]">
                                {getTime(props.layoverInformation.departureDateTime) + " | " + props.layoverInformation.departureLocation}
                            </Typography>
                        </div>
                        <div >

                        </div>
                        <div className="flex items-end">
                            <Typography className="ml-2 min-w-[120px] max-h-[50px]">
                                {getTime(props.layoverInformation.arrivalDateTime)+ " | " + props.layoverInformation.arrivalLocation}
                            </Typography>
                        </div>
                    </div>

                </div>
                <hr className="my-2" />

                <div className="flex">
                    {
                        !(props.index === props.length - 1 ) ? (
                            <div className="flex justify-start" >
                                <Typography className="ml-2 min-w-[120px] max-h-[50px]">
                                   Layover: {timeToHours(props.time)}
                                </Typography>
                            </div>
                        ) : (<></>)
                    }
                </div>
            </CardBody>
        </Card>
    )
}
