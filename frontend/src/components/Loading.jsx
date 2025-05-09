export default function Loading() {
    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
            <div className="bg-white p-20 rounded-lg shadow-lg relative flex flex-col items-center justify-center">

                <div className="svg-bounce w-[75px] h-[75px] mb-2">
                    <img src="/airplane-in-flight-thin-svgrepo-com.svg" alt="airplane spin" />
                </div>

                <div className="svg-container w-[150px] h-[150px]">
                    <img src="/planet-earth-svgrepo-com.svg" alt="earth spin" />
                </div>
            </div>
        </div>
    )
}