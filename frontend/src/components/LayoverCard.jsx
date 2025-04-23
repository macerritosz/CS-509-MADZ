import {Card, CardBody, Typography} from "@material-tailwind/react";


export default function LayoverCard(props) {

    return (
        <Card>
            <CardBody className="text-text">
                <Typography className="p-2">
                    {props.layoverInformation.departAirport}
                </Typography>
                <hr/>
                <div className="flex">
                    <Typography className="p-2">
                        Vertical Line
                    </Typography>
                    <Typography className="p-2">
                       Time taken for the Layover
                    </Typography>
                </div>

                <hr/>
                <Typography className="p-2">
                    {props.layoverInformation.arrivalAirport}
                </Typography>
            </CardBody>
        </Card>
    )

}
