import React, {useState} from "react";
import {Input, Popover, PopoverContent, PopoverHandler,} from "@material-tailwind/react";
import {format} from "date-fns";
import {DayFlag, DayPicker, SelectionState, UI} from "react-day-picker";
import {ChevronLeftIcon} from "@heroicons/react/24/outline";

export default function Calendar(props) {
    const [date, setDate] = useState();

    const handleDateChange = (date) => {
        if(!date) return;

        setDate(date);
        props.handleChange({
            target: {
                name: `${props.type}Date`,
                value: format(date, "yyyy-MM-dd"),
            },
        });
    }

    return (
        <Popover placement="bottom">
            <PopoverHandler>
                <Input
                    size="lg"
                    label={`Select a ${props.type} Date`}
                    className= "text-text"
                    onChange={props.handleChange}
                    value={date ? format(date, "PPP") : ""}
                    disabled={props.isDisabled}
                    required
                />
            </PopoverHandler>
            <PopoverContent>
                <DayPicker
                    mode="single"
                    selected={date}
                    defaultMonth={new Date(2022, 11)}
                    startMonth={new Date(2022, 11)}
                    endMonth={new Date(2023, 0)}
                    disabled={{before: new Date(2022, 11, 26), after: new Date(2023, 0, 5)}}
                    onSelect={handleDateChange}
                    classNames={
                        {
                            [UI.CaptionLabel]: "flex justify-center py-1 items-center text-lg font-semibold text-text",
                            [UI.Nav]: "relative",
                            [UI.PreviousMonthButton]: "h-10 w-10 bg-transparent hover:bg-accent/30 p-2 rounded-md transition-colors duration-300 absolute left-1.5",
                            [UI.NextMonthButton]: "h-10 w-10 bg-transparent hover:bg-accent/30 p-2 rounded-md transition-colors duration-300 absolute right-1.5",
                            [UI.MonthGrid]: "w-full border-collapse",
                            [UI.Weekdays]: "flex font-medium text-text",
                            [UI.Weekday]: "m-2 w-9 font-normal text-sm",
                            [UI.Week]: "flex",
                            [UI.Day]: "text-text rounded-md h-9 w-9 p-0 text-center text-sm p-2 m-2 relative [&:has([aria-selected].day-range-end)]:rounded-r-md [&:has([aria-selected].day-outside)]:bg-gray-900/20 [&:has([aria-selected].day-outside)]:text-white [&:has([aria-selected])]:bg-gray-900/50 first:[&:has([aria-selected])]:rounded-l-md last:[&:has([aria-selected])]:rounded-r-md focus-within:relative focus-within:z-20",
                            [SelectionState.range_end]: "day-range-end",
                            [SelectionState.selected]: "rounded-md bg-gray-900 text-white hover:bg-gray-900 hover:text-white focus:bg-gray-900 focus:text-white",
                            [DayFlag.today]: "rounded-md bg-gray-200 text-gray-900",
                            [DayFlag.outside]: "day-outside text-gray-500 opacity-50 aria-selected:bg-gray-500 aria-selected:text-gray-900 aria-selected:bg-opacity-10",
                            [DayFlag.disabled]: "text-gray-500 opacity-50",
                            [DayFlag.hidden]: "invisible",
                        }
                    }
                    components={{
                        ChevronLeft: ({...props}) => (
                            <ChevronLeftIcon {...props} orientation="left" className="h-4 w-4 stroke-1"/>
                        ),
                        ChevronRight: ({...props}) => (
                            <ChevronLeftIcon {...props} orientation="right" className="h-4 w-4 stroke-1"/>
                        )
                    }}
                />
            </PopoverContent>
        </Popover>
    )
}