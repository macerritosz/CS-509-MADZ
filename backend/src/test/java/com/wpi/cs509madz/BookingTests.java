package com.wpi.cs509madz;

import com.wpi.cs509madz.service.bookingService.Booking;
import com.wpi.cs509madz.service.bookingService.IBooking;
import com.wpi.cs509madz.service.utils.DateTime;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BookingTests {
    List<Booking> database = new ArrayList<>(Arrays.asList(
            new Booking("DL2316", new DateTime("2023-01-05 20:39:00"), "Atlanta (ATL)", new DateTime("2023-01-05 05:22:00"), "Tucson (TUS)"),
            new Booking("DL2355", new DateTime("2023-01-04 20:39:00"), "Phoenix (PHX)", new DateTime("2023-01-04 00:08:00"), "Atlanta (ATL)"),
            new Booking("DL2355", new DateTime("2023-01-04 15:10:00"), "Atlanta (ATL)", new DateTime("2023-01-04 19:29:00"), "Phoenix (PHX)"),
            new Booking("DL2115", new DateTime("2023-01-04 11:45:00"), "San Antonio (SAT)", new DateTime("2023-01-04 14:00:00"), "Atlanta (ATL)"),
            new Booking("DL2247", new DateTime("2023-01-04 00:20:00"), "Atlanta (ATL)", new DateTime("2023-01-04 03:00:00"), "San Antonio (SAT)"),
            new Booking("EXTRA", new DateTime("2023-01-04 04:50:00"), "San Antonio (SAT)", new DateTime("2023-01-04 08:00:00"), "Boston (BOS)"),
            new Booking("DL597", new DateTime("2023-01-03 19:55:00"), "Cancun (CUN)", new DateTime("2023-01-03 22:38:00"), "Atlanta (ATL)"),
            new Booking("DL598", new DateTime("2023-01-03 16:14:00"), "Atlanta (ATL)", new DateTime("2023-01-03 18:55:00"), "Cancun (CUN)"),
            new Booking("DL2218", new DateTime("2023-01-03 12:15:00"), "Cleveland (CLE)", new DateTime("2023-01-03 14:16:00"), "Atlanta (ATL)"),
            new Booking("DL343", new DateTime("2023-01-03 00:59:00"), "Atlanta (ATL)", new DateTime("2023-01-03 02:44:00"), "Cleveland (CLE)"),
            new Booking("DL490", new DateTime("2023-01-02 21:10:00"), "New York (LGA)", new DateTime("2023-01-02 23:53:00"), "Atlanta (ATL)"),
            new Booking("DL2759", new DateTime("2023-01-02 17:31:00"), "Orlando (MCO)", new DateTime("2023-01-02 19:59:00"), "New York (LGA)"),
            new Booking("DL835", new DateTime("2023-01-02 13:00:00"), "Minneapolis (MSP)", new DateTime("2023-01-02 16:21:00"), "Orlando (MCO)"),
            new Booking("DL1412", new DateTime("2023-01-02 01:18:00"), "Phoenix (PHX)", new DateTime("2023-01-02 04:20:00"), "Minneapolis (MSP)"),
            new Booking("DL1412", new DateTime("2023-01-01 20:44:00"), "Minneapolis (MSP)", new DateTime("2023-01-01 00:08:00"), "Phoenix (PHX)"),
            new Booking("DL1739", new DateTime("2023-01-01 16:03:00"), "Orlando (MCO)", new DateTime("2023-01-01 19:34:00"), "Minneapolis (MSP)"),
            new Booking("DL2375", new DateTime("2023-01-01 12:05:00"), "New York (LGA)", new DateTime("2023-01-01 14:53:00"), "Orlando (MCO)"),
            new Booking("DL453", new DateTime("2023-01-01 01:45:00"), "Detroit (DTW)", new DateTime("2023-01-01 03:34:00"), "New York (LGA)"),
            new Booking("DL2958", new DateTime("2022-12-31 19:54:00"), "Seattle (SEA)", new DateTime("2022-12-31 00:15:00"), "Detroit (DTW)"),
            new Booking("DL1251", new DateTime("2022-12-31 13:25:00"), "Detroit (DTW)", new DateTime("2022-12-31 18:44:00"), "Seattle (SEA)"),
            new Booking("DL2683", new DateTime("2022-12-31 06:05:00"), "Los Angeles (LAX)", new DateTime("2022-12-31 10:29:00"), "Detroit (DTW)"),
            new Booking("DL2584", new DateTime("2022-12-31 00:50:00"), "Minneapolis (MSP)", new DateTime("2022-12-31 04:55:00"), "Los Angeles (LAX)"),
            new Booking("DL749", new DateTime("2022-12-30 19:55:00"), "Fort Myers (RSW)", new DateTime("2022-12-30 23:35:00"), "Minneapolis (MSP)"),
            new Booking("DL749", new DateTime("2022-12-30 15:10:00"), "Minneapolis (MSP)", new DateTime("2022-12-30 18:45:00"), "Fort Myers (RSW)"),
            new Booking("DL2192", new DateTime("2022-12-30 11:00:00"), "Baltimore (BWI)", new DateTime("2022-12-30 13:57:00"), "Minneapolis (MSP)"),
            new Booking("DL2058", new DateTime("2022-12-30 02:15:00"), "Minneapolis (MSP)", new DateTime("2022-12-30 04:47:00"), "Baltimore (BWI)"),
            new Booking("DL1878", new DateTime("2022-12-29 20:18:00"), "Cancun (CUN)", new DateTime("2022-12-29 00:45:00"), "Minneapolis (MSP)"),
            new Booking("DL1881", new DateTime("2022-12-29 15:01:00"), "Minneapolis (MSP)", new DateTime("2022-12-29 19:18:00"), "Cancun (CUN)"),
            new Booking("DL2690", new DateTime("2022-12-29 08:45:00"), "San Francisco (SFO)", new DateTime("2022-12-29 12:28:00"), "Minneapolis (MSP)"),
            new Booking("DL968", new DateTime("2022-12-29 03:15:00"), "Minneapolis (MSP)", new DateTime("2022-12-29 07:34:00"), "San Francisco (SFO)"),
            new Booking("DL2414", new DateTime("2022-12-28 22:29:00"), "Orlando (MCO)", new DateTime("2022-12-28 02:05:00"), "Minneapolis (MSP)"),
            new Booking("DL2872", new DateTime("2022-12-28 17:27:00"), "New York (JFK)", new DateTime("2022-12-28 20:27:00"), "Orlando (MCO)"),
            new Booking("DL2738", new DateTime("2022-12-28 13:45:00"), "Orlando (MCO)", new DateTime("2022-12-28 16:16:00"), "New York (JFK)"),
            new Booking("DL1016", new DateTime("2022-12-28 00:50:00"), "Salt Lake City (SLC)", new DateTime("2022-12-28 05:01:00"), "Orlando (MCO)"),
            new Booking("DL1504", new DateTime("2022-12-27 18:42:00"), "Orlando (MCO)", new DateTime("2022-12-27 23:40:00"), "Salt Lake City (SLC)"),
            new Booking("DL933", new DateTime("2022-12-27 14:30:00"), "New York (LGA)", new DateTime("2022-12-27 17:32:00"), "Orlando (MCO)"),
            new Booking("DL2376", new DateTime("2022-12-26 23:35:00"), "Orlando (MCO)", new DateTime("2022-12-26 02:10:00"), "New York (LGA)"),
            new Booking("DL1063", new DateTime("2023-01-05 00:59:00"), "Orlando (MCO)", new DateTime("2023-01-05 03:54:00"), "Boston (BOS)"),
            new Booking("DL2652", new DateTime("2023-01-04 20:36:00"), "New York (JFK)", new DateTime("2023-01-04 23:49:00"), "Orlando (MCO)"),
            new Booking("DL812", new DateTime("2023-01-04 16:54:00"), "Orlando (MCO)", new DateTime("2023-01-04 19:26:00"), "New York (JFK)"),
            new Booking("DL812", new DateTime("2023-01-04 12:37:00"), "New York (JFK)", new DateTime("2023-01-04 15:44:00"), "Orlando (MCO)"),
            new Booking("DL399", new DateTime("2023-01-04 06:55:00"), "Salt Lake City (SLC)", new DateTime("2023-01-04 11:22:00"), "New York (JFK)"),
            new Booking("DL948", new DateTime("2023-01-04 01:23:00"), "New Orleans (MSY)", new DateTime("2023-01-04 05:11:00"), "Salt Lake City (SLC)"),
            new Booking("DL948", new DateTime("2023-01-03 20:57:00"), "Salt Lake City (SLC)", new DateTime("2023-01-03 00:13:00"), "New Orleans (MSY)"),
            new Booking("DL723", new DateTime("2023-01-03 16:45:00"), "Minneapolis (MSP)", new DateTime("2023-01-03 19:47:00"), "Salt Lake City (SLC)"),
            new Booking("DL2344", new DateTime("2023-01-03 12:50:00"), "Bozeman (BZN)", new DateTime("2023-01-03 15:20:00"), "Minneapolis (MSP)"),
            new Booking("DL2915", new DateTime("2023-01-03 03:55:00"), "Minneapolis (MSP)", new DateTime("2023-01-03 06:30:00"), "Bozeman (BZN)"),
            new Booking("DL1261", new DateTime("2023-01-02 21:15:00"), "Los Angeles (LAX)", new DateTime("2023-01-02 00:49:00"), "Minneapolis (MSP)"),
            new Booking("DL516", new DateTime("2023-01-02 14:57:00"), "Atlanta (ATL)", new DateTime("2023-01-02 20:05:00"), "Los Angeles (LAX)"),
            new Booking("DL727", new DateTime("2023-01-02 11:39:00"), "Kansas City (MCI)", new DateTime("2023-01-02 13:44:00"), "Atlanta (ATL)"),
            new Booking("DL1217", new DateTime("2023-01-05 00:35:00"), "Salt Lake City (SLC)", new DateTime("2023-01-05 04:15:00"), "Atlanta (ATL)"),
            new Booking("DL2599", new DateTime("2023-01-04 19:10:00"), "Atlanta (ATL)", new DateTime("2023-01-04 23:25:00"), "Salt Lake City (SLC)"),
            new Booking("DL1379", new DateTime("2023-01-04 16:07:00"), "West Palm Beach (PBI)", new DateTime("2023-01-04 18:00:00"), "Atlanta (ATL)"),
            new Booking("DL1379", new DateTime("2023-01-04 13:10:00"), "Atlanta (ATL)", new DateTime("2023-01-04 14:57:00"), "West Palm Beach (PBI)"),
            new Booking("DL309", new DateTime("2023-01-04 06:29:00"), "Los Angeles (LAX)", new DateTime("2023-01-04 10:40:00"), "Atlanta (ATL)"),
            new Booking("DL2737", new DateTime("2023-01-03 23:25:00"), "Detroit (DTW)", new DateTime("2023-01-03 04:31:00"), "Los Angeles (LAX)"),
            new Booking("DL1321", new DateTime("2023-01-03 18:30:00"), "Miami (MIA)", new DateTime("2023-01-03 21:40:00"), "Detroit (DTW)"),
            new Booking("DL1321", new DateTime("2023-01-03 14:03:00"), "Detroit (DTW)", new DateTime("2023-01-03 17:20:00"), "Miami (MIA)"),
            new Booking("DL826", new DateTime("2023-01-03 10:30:00"), "Boston (BOS)", new DateTime("2023-01-03 12:53:00"), "Detroit (DTW)"),
            new Booking("DL1452", new DateTime("2023-01-03 03:25:00"), "Atlanta (ATL)", new DateTime("2023-01-03 05:56:00"), "Boston (BOS)"),
            new Booking("DL1377", new DateTime("2023-01-02 23:34:00"), "West Palm Beach (PBI)", new DateTime("2023-01-02 01:30:00"), "Atlanta (ATL)"),
            new Booking("DL1377", new DateTime("2023-01-02 20:38:00"), "Atlanta (ATL)", new DateTime("2023-01-02 22:24:00"), "West Palm Beach (PBI)"),
            new Booking("DL405", new DateTime("2023-01-02 15:34:00"), "Salt Lake City (SLC)", new DateTime("2023-01-02 19:28:00"), "Atlanta (ATL)"),
            new Booking("DL1718", new DateTime("2023-01-02 13:00:00"), "Boise (BOI)", new DateTime("2023-01-02 14:24:00"), "Salt Lake City (SLC)"),
            new Booking("DL770", new DateTime("2023-01-02 11:40:00"), "Orlando (MCO)", new DateTime("2023-01-02 16:45:00"), "Salt Lake City (SLC)"),
            new Booking("DL2668", new DateTime("2023-01-02 03:45:00"), "Salt Lake City (SLC)", new DateTime("2023-01-02 05:10:00"), "Boise (BOI)"),
            new Booking("DL1016", new DateTime("2023-01-02 00:50:00"), "Salt Lake City (SLC)", new DateTime("2023-01-02 05:01:00"), "Orlando (MCO)"),
            new Booking("DL1504", new DateTime("2023-01-01 18:42:00"), "Orlando (MCO)", new DateTime("2023-01-01 23:40:00"), "Salt Lake City (SLC)"),
            new Booking("DL933", new DateTime("2023-01-01 14:30:00"), "New York (LGA)", new DateTime("2023-01-01 17:32:00"), "Orlando (MCO)"),
            new Booking("DL2817", new DateTime("2022-12-31 19:34:00"), "Orlando (MCO)", new DateTime("2022-12-31 22:10:00"), "New York (LGA)"),
            new Booking("DL2821", new DateTime("2022-12-31 14:00:00"), "Salt Lake City (SLC)", new DateTime("2022-12-31 18:22:00"), "Orlando (MCO)"),
            new Booking("DL476", new DateTime("2022-12-31 03:14:00"), "Atlanta (ATL)", new DateTime("2022-12-31 07:30:00"), "Salt Lake City (SLC)"),
            new Booking("DL1507", new DateTime("2022-12-30 23:22:00"), "Buffalo (BUF)", new DateTime("2022-12-30 01:44:00"), "Atlanta (ATL)"),
            new Booking("DL1507", new DateTime("2022-12-30 20:14:00"), "Atlanta (ATL)", new DateTime("2022-12-30 22:12:00"), "Buffalo (BUF)"),
            new Booking("DL405", new DateTime("2022-12-30 15:34:00"), "Salt Lake City (SLC)", new DateTime("2022-12-30 19:28:00"), "Atlanta (ATL)"),
            new Booking("DL1718", new DateTime("2022-12-30 13:00:00"), "Boise (BOI)", new DateTime("2022-12-30 14:24:00"), "Salt Lake City (SLC)"),
            new Booking("DL2668", new DateTime("2022-12-30 06:15:00"), "Salt Lake City (SLC)", new DateTime("2022-12-30 07:40:00"), "Boise (BOI)"),
            new Booking("DL1065", new DateTime("2022-12-30 00:03:00"), "Orlando (MCO)", new DateTime("2022-12-30 05:05:00"), "Salt Lake City (SLC)"),
            new Booking("DL1065", new DateTime("2022-12-29 18:40:00"), "Salt Lake City (SLC)", new DateTime("2022-12-29 22:53:00"), "Orlando (MCO)"),
            new Booking("DL1009", new DateTime("2022-12-29 14:20:00"), "Minneapolis (MSP)", new DateTime("2022-12-29 17:24:00"), "Salt Lake City (SLC)"),
            new Booking("DL1312", new DateTime("2022-12-29 12:00:00"), "Kansas City (MCI)", new DateTime("2022-12-29 13:32:00"), "Minneapolis (MSP)"),
            new Booking("DL743", new DateTime("2022-12-29 04:00:00"), "Atlanta (ATL)", new DateTime("2022-12-29 06:12:00"), "Kansas City (MCI)"),
            new Booking("DL2728", new DateTime("2022-12-28 23:53:00"), "Albuquerque (ABQ)", new DateTime("2022-12-28 02:49:00"), "Atlanta (ATL)"),
            new Booking("DL2728", new DateTime("2022-12-28 19:21:00"), "Atlanta (ATL)", new DateTime("2022-12-28 22:43:00"), "Albuquerque (ABQ)"),
            new Booking("DL1284", new DateTime("2022-12-28 15:00:00"), "Denver (DEN)", new DateTime("2022-12-28 18:02:00"), "Atlanta (ATL)"),
            new Booking("DL2122", new DateTime("2022-12-28 02:15:00"), "Detroit (DTW)", new DateTime("2022-12-28 05:32:00"), "Denver (DEN)"),
            new Booking("DL1282", new DateTime("2022-12-27 22:20:00"), "Denver (DEN)", new DateTime("2022-12-27 01:01:00"), "Detroit (DTW)"),
            new Booking("DL1206", new DateTime("2022-12-27 17:47:00"), "Detroit (DTW)", new DateTime("2022-12-27 21:08:00"), "Denver (DEN)"),
            new Booking("DL2663", new DateTime("2022-12-27 13:40:00"), "Denver (DEN)", new DateTime("2022-12-27 16:35:00"), "Detroit (DTW)"),
            new Booking("DL2249", new DateTime("2022-12-26 23:40:00"), "Atlanta (ATL)", new DateTime("2022-12-26 03:05:00"), "Denver (DEN)"),
            new Booking("DL2325", new DateTime("2023-01-05 01:30:00"), "Orlando (MCO)", new DateTime("2023-01-05 03:59:00"), "New York (JFK)"),
            new Booking("DL1381", new DateTime("2023-01-04 20:55:00"), "Boston (BOS)", new DateTime("2023-01-04 00:20:00"), "Orlando (MCO)"),
            new Booking("DL2911", new DateTime("2023-01-04 16:37:00"), "Miami (MIA)", new DateTime("2023-01-04 19:45:00"), "Boston (BOS)"),
            new Booking("DL2911", new DateTime("2023-01-04 12:10:00"), "Boston (BOS)", new DateTime("2023-01-04 15:27:00"), "Miami (MIA)"),
            new Booking("DL2990", new DateTime("2023-01-04 01:00:00"), "Atlanta (ATL)", new DateTime("2023-01-04 03:31:00"), "Boston (BOS)"),
            new Booking("EXTRA", new DateTime("2023-01-04 03:00:00"), "Atlanta (ATL)", new DateTime("2023-01-04 04:31:00"), "Boston (BOS)"),
            new Booking("DL474", new DateTime("2023-01-03 22:05:00"), "Tampa (TPA)", new DateTime("2023-01-03 23:48:00"), "Atlanta (ATL)"),
            new Booking("DL474", new DateTime("2023-01-03 19:28:00"), "Atlanta (ATL)", new DateTime("2023-01-03 20:54:00"), "Tampa (TPA)"),
            new Booking("DL1284", new DateTime("2023-01-03 15:00:00"), "Denver (DEN)", new DateTime("2023-01-03 18:02:00"), "Atlanta (ATL)"),
            new Booking("DL2777", new DateTime("2023-01-03 00:45:00"), "Minneapolis (MSP)", new DateTime("2023-01-03 03:06:00"), "Denver (DEN)"),
            new Booking("DL712", new DateTime("2023-01-02 19:35:00"), "Fort Lauderdale (FLL)", new DateTime("2023-01-02 23:33:00"), "Minneapolis (MSP)"),
            new Booking("DL712", new DateTime("2023-01-02 14:50:00"), "Minneapolis (MSP)", new DateTime("2023-01-02 18:25:00"), "Fort Lauderdale (FLL)"),
            new Booking("DL1312", new DateTime("2023-01-02 12:00:00"), "Kansas City (MCI)", new DateTime("2023-01-02 13:32:00"), "Minneapolis (MSP)"),
            new Booking("DL2527", new DateTime("2023-01-02 03:25:00"), "Minneapolis (MSP)", new DateTime("2023-01-02 04:58:00"), "Kansas City (MCI)"),
            new Booking("DL1878", new DateTime("2023-01-01 20:18:00"), "Cancun (CUN)", new DateTime("2023-01-01 00:45:00"), "Minneapolis (MSP)"),
            new Booking("DL1881", new DateTime("2023-01-01 15:01:00"), "Minneapolis (MSP)", new DateTime("2023-01-01 19:18:00"), "Cancun (CUN)"),
            new Booking("DL924", new DateTime("2023-01-01 00:10:00"), "Salt Lake City (SLC)", new DateTime("2023-01-01 02:45:00"), "Minneapolis (MSP)"),
            new Booking("DL891", new DateTime("2022-12-31 21:20:00"), "Phoenix (PHX)", new DateTime("2022-12-31 23:00:00"), "Salt Lake City (SLC)"),
            new Booking("DL891", new DateTime("2022-12-31 18:25:00"), "Salt Lake City (SLC)", new DateTime("2022-12-31 20:09:00"), "Phoenix (PHX)"),
            new Booking("DL1009", new DateTime("2022-12-31 14:05:00"), "Minneapolis (MSP)", new DateTime("2022-12-31 17:14:00"), "Salt Lake City (SLC)"),
            new Booking("DL2707", new DateTime("2022-12-31 11:00:00"), "Fargo (FAR)", new DateTime("2022-12-31 12:21:00"), "Minneapolis (MSP)"),
            new Booking("DL1730", new DateTime("2022-12-31 05:25:00"), "Minneapolis (MSP)", new DateTime("2022-12-31 06:38:00"), "Fargo (FAR)"),
            new Booking("DL2067", new DateTime("2022-12-31 01:07:00"), "Las Vegas (LAS)", new DateTime("2022-12-31 04:12:00"), "Minneapolis (MSP)"),
            new Booking("DL2696", new DateTime("2022-12-30 22:33:00"), "Salt Lake City (SLC)", new DateTime("2022-12-30 23:57:00"), "Las Vegas (LAS)"),
            new Booking("DL2167", new DateTime("2022-12-30 17:15:00"), "Detroit (DTW)", new DateTime("2022-12-30 21:23:00"), "Salt Lake City (SLC)"),
            new Booking("DL352", new DateTime("2022-12-30 14:14:00"), "Atlanta (ATL)", new DateTime("2022-12-30 16:10:00"), "Detroit (DTW)"),
            new Booking("DL2161", new DateTime("2022-12-30 11:00:00"), "Pittsburgh (PIT)", new DateTime("2022-12-30 12:57:00"), "Atlanta (ATL)"),
            new Booking("DL2689", new DateTime("2022-12-30 04:00:00"), "Atlanta (ATL)", new DateTime("2022-12-30 05:36:00"), "Pittsburgh (PIT)"),
            new Booking("DL1230", new DateTime("2022-12-30 00:55:00"), "West Palm Beach (PBI)", new DateTime("2022-12-30 02:49:00"), "Atlanta (ATL)"),
            new Booking("DL1230", new DateTime("2022-12-29 22:02:00"), "Atlanta (ATL)", new DateTime("2022-12-29 23:45:00"), "West Palm Beach (PBI)"),
            new Booking("DL641", new DateTime("2022-12-29 18:25:00"), "Cancun (CUN)", new DateTime("2022-12-29 21:07:00"), "Atlanta (ATL)"),
            new Booking("DL586", new DateTime("2022-12-29 14:46:00"), "Atlanta (ATL)", new DateTime("2022-12-29 17:25:00"), "Cancun (CUN)"),
            new Booking("DL1444", new DateTime("2022-12-29 11:00:00"), "Cleveland (CLE)", new DateTime("2022-12-29 12:59:00"), "Atlanta (ATL)"),
            new Booking("DL960", new DateTime("2022-12-29 03:37:00"), "Atlanta (ATL)", new DateTime("2022-12-29 05:23:00"), "Cleveland (CLE)"),
            new Booking("DL2464", new DateTime("2022-12-28 22:49:00"), "Phoenix (PHX)", new DateTime("2022-12-28 02:21:00"), "Atlanta (ATL)"),
            new Booking("DL2464", new DateTime("2022-12-28 17:25:00"), "Atlanta (ATL)", new DateTime("2022-12-28 21:39:00"), "Phoenix (PHX)"),
            new Booking("DL408", new DateTime("2022-12-28 13:25:00"), "Minneapolis (MSP)", new DateTime("2022-12-28 16:09:00"), "Atlanta (ATL)"),
            new Booking("DL1073", new DateTime("2022-12-28 03:45:00"), "Salt Lake City (SLC)", new DateTime("2022-12-28 06:27:00"), "Minneapolis (MSP)"),
            new Booking("DL1951", new DateTime("2022-12-27 23:05:00"), "San Jose del Cabo (SJD)", new DateTime("2022-12-27 02:13:00"), "Salt Lake City (SLC)"),
            new Booking("DL1952", new DateTime("2022-12-27 18:38:00"), "Salt Lake City (SLC)", new DateTime("2022-12-27 22:00:00"), "San Jose del Cabo (SJD)"),
            new Booking("DL956", new DateTime("2022-12-27 12:00:00"), "Tampa (TPA)", new DateTime("2022-12-27 17:06:00"), "Salt Lake City (SLC)"),
            new Booking("DL1050", new DateTime("2022-12-27 06:29:00"), "Salt Lake City (SLC)", new DateTime("2022-12-27 10:45:00"), "Tampa (TPA)"),
            new Booking("DL2626", new DateTime("2022-12-27 01:18:00"), "Los Angeles (LAX)", new DateTime("2022-12-27 03:13:00"), "Salt Lake City (SLC)"),
            new Booking("DL399", new DateTime("2023-01-05 06:55:00"), "Salt Lake City (SLC)", new DateTime("2023-01-05 11:22:00"), "New York (JFK)"),
            new Booking("DL1036", new DateTime("2023-01-05 00:59:00"), "Detroit (DTW)", new DateTime("2023-01-05 05:04:00"), "Salt Lake City (SLC)"),
            new Booking("DL2472", new DateTime("2023-01-04 20:05:00"), "Fort Myers (RSW)", new DateTime("2023-01-04 23:03:00"), "Detroit (DTW)"),
            new Booking("DL2472", new DateTime("2023-01-04 15:30:00"), "Detroit (DTW)", new DateTime("2023-01-04 18:31:00"), "Fort Myers (RSW)"),
            new Booking("DL389", new DateTime("2023-01-04 12:15:00"), "Atlanta (ATL)", new DateTime("2023-01-04 14:12:00"), "Detroit (DTW)"),
            new Booking("DL1068", new DateTime("2023-01-04 06:30:00"), "San Diego (SAN)", new DateTime("2023-01-04 10:34:00"), "Atlanta (ATL)"),
            new Booking("DL744", new DateTime("2023-01-03 22:01:00"), "Detroit (DTW)", new DateTime("2023-01-03 02:57:00"), "San Diego (SAN)"),
            new Booking("DL913", new DateTime("2023-01-03 18:10:00"), "Denver (DEN)", new DateTime("2023-01-03 20:51:00"), "Detroit (DTW)"),
            new Booking("DL913", new DateTime("2023-01-03 13:30:00"), "Detroit (DTW)", new DateTime("2023-01-03 17:00:00"), "Denver (DEN)")
    ));

    @Test
    void testDirect() {
        Booking direct = new Booking(database, new DateTime("2022-12-27"), "Denver (DEN)", "Detroit (DTW)");

        List<List<IBooking>> directFlights = direct.findLayoverOptions(Optional.of(true), Optional.of(false));

        boolean valid = true;
        for (List<IBooking> bookings : directFlights) {
            IBooking cur = bookings.get(0);

            if (!Objects.equals(cur.getDepartureDate().toString(), direct.getDepartureDate().toString()) ||
                    !Objects.equals(cur.getDepartureLocation(), direct.getDepartureLocation()) ||
                    !Objects.equals(cur.getArrivalLocation(), direct.getArrivalLocation())) {
                valid = false;
            }
        }

        assertTrue(valid, "Not all the flights are direct");
    }

    @Test
    void testSameDay() {
        Booking sameDay = new Booking(database, new DateTime("2023-01-04"), "Atlanta (ATL)", "Boston (BOS)");

        List<List<IBooking>> sameDayFlights = sameDay.findLayoverOptions(Optional.of(false), Optional.of(true));

        boolean valid = true;
        for (List<IBooking> bookings : sameDayFlights) {
            IBooking cur = bookings.get(0);

            if (!cur.getDepartureDate().toString().equals(sameDay.getDepartureDate().toString()) &&
                    !cur.getArrivalDate().toString().equals(sameDay.getArrivalDate().toString())) {
                valid = false;
            }
        }

        assertTrue(valid, "Not all the flights are same day");
    }
}
