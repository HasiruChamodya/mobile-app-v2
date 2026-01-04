package com.shanalanka.emergency.data.source

import com.shanalanka.emergency.data.local.entity.PoliceStationEntity

/**
 * Pre-populated police station data for all 25 districts of Sri Lanka.
 * This data is used to pre-populate the database on first app launch.
 * 
 * ⚠️ CRITICAL WARNING: Phone numbers are PLACEHOLDER/SAMPLE numbers! ⚠️
 * 
 * TODO: BEFORE PRODUCTION DEPLOYMENT, ALL phone numbers MUST be updated with:
 *  - Official Sri Lanka Police emergency hotlines
 *  - Verified police station contact numbers
 *  - District police headquarters numbers
 * 
 * Contact Sri Lanka Police Department to obtain accurate emergency contact numbers.
 * Using incorrect numbers in an emergency app could be life-threatening!
 * 
 * Official Sri Lanka Police website: https://www.police.lk
 * Emergency hotline (general): 119
 */
object PoliceStationData {
    
    fun getPoliceStations(): List<PoliceStationEntity> {
        return listOf(
            // Colombo District
            PoliceStationEntity(0, "Colombo Fort Police Station", "COLOMBO", "011-2421111", "Fort, Colombo 01"),
            PoliceStationEntity(0, "Colombo Central Police Station", "COLOMBO", "011-2433333", "Central, Colombo 02"),
            PoliceStationEntity(0, "Kollupitiya Police Station", "COLOMBO", "011-2421621", "Kollupitiya, Colombo 03"),
            PoliceStationEntity(0, "Bambalapitiya Police Station", "COLOMBO", "011-2587222", "Bambalapitiya, Colombo 04"),
            PoliceStationEntity(0, "Wellawatte Police Station", "COLOMBO", "011-2362150", "Wellawatte, Colombo 06"),
            PoliceStationEntity(0, "Dehiwala Police Station", "COLOMBO", "011-2714022", "Dehiwala"),
            PoliceStationEntity(0, "Mount Lavinia Police Station", "COLOMBO", "011-2712222", "Mount Lavinia"),
            PoliceStationEntity(0, "Borella Police Station", "COLOMBO", "011-2696741", "Borella, Colombo 08"),
            PoliceStationEntity(0, "Maradana Police Station", "COLOMBO", "011-2326042", "Maradana, Colombo 10"),
            PoliceStationEntity(0, "Kotahena Police Station", "COLOMBO", "011-2431565", "Kotahena, Colombo 13"),
            
            // Gampaha District
            PoliceStationEntity(0, "Gampaha Police Station", "GAMPAHA", "033-2222222", "Gampaha"),
            PoliceStationEntity(0, "Negombo Police Station", "GAMPAHA", "031-2222222", "Negombo"),
            PoliceStationEntity(0, "Katunayake Police Station", "GAMPAHA", "031-2252222", "Katunayake"),
            PoliceStationEntity(0, "Ja-Ela Police Station", "GAMPAHA", "011-2237222", "Ja-Ela"),
            PoliceStationEntity(0, "Wattala Police Station", "GAMPAHA", "011-2937222", "Wattala"),
            PoliceStationEntity(0, "Kiribathgoda Police Station", "GAMPAHA", "011-2913222", "Kiribathgoda"),
            PoliceStationEntity(0, "Kadawatha Police Station", "GAMPAHA", "011-2927222", "Kadawatha"),
            PoliceStationEntity(0, "Minuwangoda Police Station", "GAMPAHA", "011-2296222", "Minuwangoda"),
            PoliceStationEntity(0, "Divulapitiya Police Station", "GAMPAHA", "033-2246222", "Divulapitiya"),
            
            // Kalutara District
            PoliceStationEntity(0, "Kalutara Police Station", "KALUTARA", "034-2222222", "Kalutara"),
            PoliceStationEntity(0, "Panadura Police Station", "KALUTARA", "038-2232222", "Panadura"),
            PoliceStationEntity(0, "Horana Police Station", "KALUTARA", "034-2261222", "Horana"),
            PoliceStationEntity(0, "Beruwala Police Station", "KALUTARA", "034-2275222", "Beruwala"),
            PoliceStationEntity(0, "Aluthgama Police Station", "KALUTARA", "034-2274222", "Aluthgama"),
            PoliceStationEntity(0, "Matugama Police Station", "KALUTARA", "034-2247222", "Matugama"),
            PoliceStationEntity(0, "Bandaragama Police Station", "KALUTARA", "038-2298222", "Bandaragama"),
            
            // Kandy District
            PoliceStationEntity(0, "Kandy Police Station", "KANDY", "081-2222222", "Kandy"),
            PoliceStationEntity(0, "Peradeniya Police Station", "KANDY", "081-2388222", "Peradeniya"),
            PoliceStationEntity(0, "Gampola Police Station", "KANDY", "081-2352222", "Gampola"),
            PoliceStationEntity(0, "Nawalapitiya Police Station", "KANDY", "054-2222222", "Nawalapitiya"),
            PoliceStationEntity(0, "Katugastota Police Station", "KANDY", "081-2492222", "Katugastota"),
            PoliceStationEntity(0, "Galagedara Police Station", "KANDY", "081-2577222", "Galagedara"),
            PoliceStationEntity(0, "Wattegama Police Station", "KANDY", "081-2375222", "Wattegama"),
            
            // Matale District
            PoliceStationEntity(0, "Matale Police Station", "MATALE", "066-2222222", "Matale"),
            PoliceStationEntity(0, "Dambulla Police Station", "MATALE", "066-2284222", "Dambulla"),
            PoliceStationEntity(0, "Galewela Police Station", "MATALE", "066-2287222", "Galewela"),
            PoliceStationEntity(0, "Ukuwela Police Station", "MATALE", "066-2237222", "Ukuwela"),
            PoliceStationEntity(0, "Rattota Police Station", "MATALE", "066-2248222", "Rattota"),
            
            // Nuwara Eliya District
            PoliceStationEntity(0, "Nuwara Eliya Police Station", "NUWARA_ELIYA", "052-2222222", "Nuwara Eliya"),
            PoliceStationEntity(0, "Hatton Police Station", "NUWARA_ELIYA", "051-2222222", "Hatton"),
            PoliceStationEntity(0, "Nuwara Eliya Town Police Station", "NUWARA_ELIYA", "052-2223222", "Nuwara Eliya Town"),
            PoliceStationEntity(0, "Walapane Police Station", "NUWARA_ELIYA", "081-2374222", "Walapane"),
            
            // Galle District
            PoliceStationEntity(0, "Galle Police Station", "GALLE", "091-2222222", "Galle"),
            PoliceStationEntity(0, "Hikkaduwa Police Station", "GALLE", "091-2277222", "Hikkaduwa"),
            PoliceStationEntity(0, "Ambalangoda Police Station", "GALLE", "091-2258222", "Ambalangoda"),
            PoliceStationEntity(0, "Baddegama Police Station", "GALLE", "091-2292222", "Baddegama"),
            PoliceStationEntity(0, "Elpitiya Police Station", "GALLE", "091-2290222", "Elpitiya"),
            PoliceStationEntity(0, "Balapitiya Police Station", "GALLE", "091-2257222", "Balapitiya"),
            
            // Matara District
            PoliceStationEntity(0, "Matara Police Station", "MATARA", "041-2222222", "Matara"),
            PoliceStationEntity(0, "Weligama Police Station", "MATARA", "041-2250222", "Weligama"),
            PoliceStationEntity(0, "Mirissa Police Station", "MATARA", "041-2251222", "Mirissa"),
            PoliceStationEntity(0, "Akuressa Police Station", "MATARA", "041-2283222", "Akuressa"),
            PoliceStationEntity(0, "Deniyaya Police Station", "MATARA", "041-2271222", "Deniyaya"),
            
            // Hambantota District
            PoliceStationEntity(0, "Hambantota Police Station", "HAMBANTOTA", "047-2222222", "Hambantota"),
            PoliceStationEntity(0, "Tangalle Police Station", "HAMBANTOTA", "047-2240222", "Tangalle"),
            PoliceStationEntity(0, "Tissamaharama Police Station", "HAMBANTOTA", "047-2237222", "Tissamaharama"),
            PoliceStationEntity(0, "Beliatta Police Station", "HAMBANTOTA", "047-2245222", "Beliatta"),
            PoliceStationEntity(0, "Ambalantota Police Station", "HAMBANTOTA", "047-2225222", "Ambalantota"),
            
            // Jaffna District
            PoliceStationEntity(0, "Jaffna Police Station", "JAFFNA", "021-2222222", "Jaffna"),
            PoliceStationEntity(0, "Chavakachcheri Police Station", "JAFFNA", "021-2271222", "Chavakachcheri"),
            PoliceStationEntity(0, "Point Pedro Police Station", "JAFFNA", "021-2261222", "Point Pedro"),
            PoliceStationEntity(0, "Nallur Police Station", "JAFFNA", "021-2223222", "Nallur"),
            PoliceStationEntity(0, "Karainagar Police Station", "JAFFNA", "021-2268222", "Karainagar"),
            
            // Kilinochchi District
            PoliceStationEntity(0, "Kilinochchi Police Station", "KILINOCHCHI", "021-2282222", "Kilinochchi"),
            PoliceStationEntity(0, "Pallai Police Station", "KILINOCHCHI", "021-2283222", "Pallai"),
            PoliceStationEntity(0, "Paranthan Police Station", "KILINOCHCHI", "021-2284222", "Paranthan"),
            
            // Mannar District
            PoliceStationEntity(0, "Mannar Police Station", "MANNAR", "023-2222222", "Mannar"),
            PoliceStationEntity(0, "Madhu Police Station", "MANNAR", "023-2223222", "Madhu"),
            PoliceStationEntity(0, "Nanattan Police Station", "MANNAR", "023-2224222", "Nanattan"),
            
            // Vavuniya District
            PoliceStationEntity(0, "Vavuniya Police Station", "VAVUNIYA", "024-2222222", "Vavuniya"),
            PoliceStationEntity(0, "Vavuniya North Police Station", "VAVUNIYA", "024-2223222", "Vavuniya North"),
            PoliceStationEntity(0, "Cheddikulam Police Station", "VAVUNIYA", "024-2265222", "Cheddikulam"),
            
            // Mullaitivu District
            PoliceStationEntity(0, "Mullaitivu Police Station", "MULLAITIVU", "021-2206222", "Mullaitivu"),
            PoliceStationEntity(0, "Oddusuddan Police Station", "MULLAITIVU", "021-2207222", "Oddusuddan"),
            PoliceStationEntity(0, "Puthukkudiyiruppu Police Station", "MULLAITIVU", "021-2208222", "Puthukkudiyiruppu"),
            
            // Batticaloa District
            PoliceStationEntity(0, "Batticaloa Police Station", "BATTICALOA", "065-2222222", "Batticaloa"),
            PoliceStationEntity(0, "Kalmunai Police Station", "BATTICALOA", "067-2222222", "Kalmunai"),
            PoliceStationEntity(0, "Eravur Police Station", "BATTICALOA", "065-2277222", "Eravur"),
            PoliceStationEntity(0, "Valachchenai Police Station", "BATTICALOA", "065-2260222", "Valachchenai"),
            
            // Ampara District
            PoliceStationEntity(0, "Ampara Police Station", "AMPARA", "063-2222222", "Ampara"),
            PoliceStationEntity(0, "Akkaraipattu Police Station", "AMPARA", "067-2278222", "Akkaraipattu"),
            PoliceStationEntity(0, "Sainthamaruthu Police Station", "AMPARA", "067-2223222", "Sainthamaruthu"),
            PoliceStationEntity(0, "Pottuvil Police Station", "AMPARA", "063-2248222", "Pottuvil"),
            
            // Trincomalee District
            PoliceStationEntity(0, "Trincomalee Police Station", "TRINCOMALEE", "026-2222222", "Trincomalee"),
            PoliceStationEntity(0, "Kinniya Police Station", "TRINCOMALEE", "026-2231222", "Kinniya"),
            PoliceStationEntity(0, "Mutur Police Station", "TRINCOMALEE", "026-2267222", "Mutur"),
            PoliceStationEntity(0, "Kantale Police Station", "TRINCOMALEE", "027-2244222", "Kantale"),
            
            // Kurunegala District
            PoliceStationEntity(0, "Kurunegala Police Station", "KURUNEGALA", "037-2222222", "Kurunegala"),
            PoliceStationEntity(0, "Kuliyapitiya Police Station", "KURUNEGALA", "037-2281222", "Kuliyapitiya"),
            PoliceStationEntity(0, "Narammala Police Station", "KURUNEGALA", "037-2249222", "Narammala"),
            PoliceStationEntity(0, "Wariyapola Police Station", "KURUNEGALA", "037-2266222", "Wariyapola"),
            PoliceStationEntity(0, "Maho Police Station", "KURUNEGALA", "037-2268222", "Maho"),
            PoliceStationEntity(0, "Nikaweratiya Police Station", "KURUNEGALA", "037-2267222", "Nikaweratiya"),
            
            // Puttalam District
            PoliceStationEntity(0, "Puttalam Police Station", "PUTTALAM", "032-2222222", "Puttalam"),
            PoliceStationEntity(0, "Chilaw Police Station", "PUTTALAM", "032-2221222", "Chilaw"),
            PoliceStationEntity(0, "Wennappuwa Police Station", "PUTTALAM", "031-2261222", "Wennappuwa"),
            PoliceStationEntity(0, "Dankotuwa Police Station", "PUTTALAM", "031-2250222", "Dankotuwa"),
            PoliceStationEntity(0, "Nattandiya Police Station", "PUTTALAM", "032-2255222", "Nattandiya"),
            
            // Anuradhapura District
            PoliceStationEntity(0, "Anuradhapura Police Station", "ANURADHAPURA", "025-2222222", "Anuradhapura"),
            PoliceStationEntity(0, "Kekirawa Police Station", "ANURADHAPURA", "025-2264222", "Kekirawa"),
            PoliceStationEntity(0, "Medawachchiya Police Station", "ANURADHAPURA", "025-2260222", "Medawachchiya"),
            PoliceStationEntity(0, "Thambuththegama Police Station", "ANURADHAPURA", "025-2276222", "Thambuththegama"),
            PoliceStationEntity(0, "Horowpothana Police Station", "ANURADHAPURA", "025-2266222", "Horowpothana"),
            
            // Polonnaruwa District
            PoliceStationEntity(0, "Polonnaruwa Police Station", "POLONNARUWA", "027-2222222", "Polonnaruwa"),
            PoliceStationEntity(0, "Medirigiriya Police Station", "POLONNARUWA", "027-2241222", "Medirigiriya"),
            PoliceStationEntity(0, "Hingurakgoda Police Station", "POLONNARUWA", "027-2245222", "Hingurakgoda"),
            
            // Badulla District
            PoliceStationEntity(0, "Badulla Police Station", "BADULLA", "055-2222222", "Badulla"),
            PoliceStationEntity(0, "Bandarawela Police Station", "BADULLA", "057-2222222", "Bandarawela"),
            PoliceStationEntity(0, "Mahiyangana Police Station", "BADULLA", "055-2257222", "Mahiyangana"),
            PoliceStationEntity(0, "Welimada Police Station", "BADULLA", "057-2256222", "Welimada"),
            PoliceStationEntity(0, "Ella Police Station", "BADULLA", "057-2288222", "Ella"),
            
            // Monaragala District
            PoliceStationEntity(0, "Monaragala Police Station", "MONARAGALA", "055-2276222", "Monaragala"),
            PoliceStationEntity(0, "Wellawaya Police Station", "MONARAGALA", "055-2274222", "Wellawaya"),
            PoliceStationEntity(0, "Bibile Police Station", "MONARAGALA", "055-2280222", "Bibile"),
            PoliceStationEntity(0, "Buttala Police Station", "MONARAGALA", "055-2279222", "Buttala"),
            
            // Ratnapura District
            PoliceStationEntity(0, "Ratnapura Police Station", "RATNAPURA", "045-2222222", "Ratnapura"),
            PoliceStationEntity(0, "Embilipitiya Police Station", "RATNAPURA", "047-2261222", "Embilipitiya"),
            PoliceStationEntity(0, "Balangoda Police Station", "RATNAPURA", "045-2287222", "Balangoda"),
            PoliceStationEntity(0, "Kuruwita Police Station", "RATNAPURA", "045-2255222", "Kuruwita"),
            PoliceStationEntity(0, "Pelmadulla Police Station", "RATNAPURA", "045-2260222", "Pelmadulla"),
            
            // Kegalle District
            PoliceStationEntity(0, "Kegalle Police Station", "KEGALLE", "035-2222222", "Kegalle"),
            PoliceStationEntity(0, "Mawanella Police Station", "KEGALLE", "035-2247222", "Mawanella"),
            PoliceStationEntity(0, "Warakapola Police Station", "KEGALLE", "035-2266222", "Warakapola"),
            PoliceStationEntity(0, "Rambukkana Police Station", "KEGALLE", "035-2267222", "Rambukkana"),
            PoliceStationEntity(0, "Galigamuwa Police Station", "KEGALLE", "035-2256222", "Galigamuwa")
        )
    }
}
