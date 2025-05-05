import LayoverCard from "./LayoverCard.jsx";


export default function LayoverCollapse(props) {



    return (
        <div className="p-2">
            <div className="flex flex-wrap gap-4">
                {
                    props.layovers.map((layovers, index) => (
                        <LayoverCard
                            key={`LayoverCard-${layovers.departureDateTime}-${layovers.id}`}
                            layoverInformation={layovers}
                        />
                    ))
                }
            </div>
        </div>
    )

}
