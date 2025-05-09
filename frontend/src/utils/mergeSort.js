// Sorting logic
const Strategy = {
    DEPARTURE: "departure",
    ARRIVAL: "arrival",
    TIME: "time",
}

function merge( left, right, strategy) {
    let result = [];
    let i = 0, j = 0;



    const compare = (a, b) => {

        const aFirstLeg = a.flightPath[0];
        const aLastLeg = a.flightPath[a.flightPath.length - 1];
        const bFirstLeg = b.flightPath[0];
        const bLastLeg = b.flightPath[b.flightPath.length - 1];
        const aFlightDuration = a.flightTimes[0]
        const bFlightDuration = b.flightTimes[0]

        if (strategy === Strategy.DEPARTURE) {
            return new Date(aFirstLeg.departureDateTime) - new Date(bFirstLeg.departureDateTime);
        } else if (strategy === Strategy.ARRIVAL) {
            return new Date(aLastLeg.arrivalDateTime) - new Date(bLastLeg.arrivalDateTime);
        } else if (strategy === Strategy.TIME) {
            return aFlightDuration - bFlightDuration;
        }
        return 0;
    };

    while (i < left.length && j < right.length) {
        if (compare(left[i], right[j]) <= 0) {
            result.push(left[i++]);
        } else {
            result.push(right[j++]);
        }
    }

    return result.concat(left.slice(i)).concat(right.slice(j));
}

export function mergeSort(arr, strategy) {
    if (arr.length <= 1) return arr;

    const mid = Math.floor(arr.length / 2);
    const left = mergeSort(arr.slice(0, mid), strategy);
    const right = mergeSort(arr.slice(mid), strategy);
    return merge(left, right, strategy);
}