// Sorting logic
function merge(arr, left, right, strategy) {
    let result = [];
    let i = 0, j = 0;

    const compare = (a, b) => {
        if (strategy === Strategy.DEPARTURE) {
            return new Date(a.departureDateTime) < new Date(b.departureDateTime);
        } else if (strategy === Strategy.ARRIVAL) {
            return new Date(a.arrivalDateTime) < new Date(b.arrivalDateTime);
        } else if (strategy === Strategy.TIME) {
            return a.flightDuration < b.flightDuration;
        }
        return true;
    };

    while (i < left.length && j < right.length) {
        if (compare(left[i], right[j])) {
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
    return merge(arr, left, right, strategy);
}