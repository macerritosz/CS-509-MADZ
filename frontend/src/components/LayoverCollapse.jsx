import LayoverCard from "./LayoverCard.jsx";


export default function LayoverCollapse(props) {



    return (
        <div className="p-2">
            <div className="flex gap-4">
                { props.layoverTimes.length > 0  &&
                    props.layovers.map((layovers, index) => (
                        <LayoverCard
                            key={`LayoverCard-${layovers.departureDateTime}-${layovers.id}`}
                            layoverInformation={layovers}
                            index = {index}
                            length = {props.layovers.length}
                            time = {props.layoverTimes[index]}
                        />
                    ))
                }
            </div>
        </div>
    )

}
