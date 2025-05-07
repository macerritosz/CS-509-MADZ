import {Card, CardBody, Typography} from "@material-tailwind/react";


export default function LayoverCard(props) {

    const getTime = (date) => {
        let dateStr = new Date(date);
        return dateStr.toLocaleString([], {
            month: 'short',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            hour12: true
        });
    }

    return (
        <Card>
            <CardBody className="text-text">
                <div className="flex">

                    <div className="relative bg-primary w-[3px] m-2">
                        <div className="absolute top-0 left-1/2 transform -translate-x-1/2 w-3 h-3 bg-primary rounded-full"></div>

                        <div className="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-3 h-3 bg-primary rounded-full"></div>
                    </div>

                    <div className="flex flex-col w-full">
                        <div className="flex items-center">
                            <Typography className="min-w-[120px]">
                                {props.layoverInformation.departureLocation}
                            </Typography>
                            <Typography className="p-2">
                                {getTime(props.layoverInformation.departureDateTime)}
                            </Typography>
                        </div>

                        <hr />
                        <div className="flex">
                            <Typography className="p-2">

                            </Typography>
                        </div>

                        <hr />
                        <div className="flex items-center">
                            <Typography className="min-w-[120px]">
                                {props.layoverInformation.arrivalLocation}
                            </Typography>
                            <Typography className="p-2">
                                {getTime(props.layoverInformation.arrivalDateTime)}
                            </Typography>
                        </div>
                    </div>
                </div>
            </CardBody>
        </Card>
    )

}
