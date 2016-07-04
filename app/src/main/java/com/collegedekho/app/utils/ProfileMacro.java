package com.collegedekho.app.utils;

import com.collegedekho.app.entities.ProfileSpinnerItem;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 23/5/16.
 *
 */
public class ProfileMacro {

    public static String PROFILE_CITIES = "[\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"Andaman & Nicobar\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2,\n" +
            "        \"name\": \"Andhra Pradesh\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 3,\n" +
            "        \"name\": \"Arunachal Pradesh\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 4,\n" +
            "        \"name\": \"Assam\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 5,\n" +
            "        \"name\": \"Bihar\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 6,\n" +
            "        \"name\": \"Chandigarh\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 7,\n" +
            "        \"name\": \"Chhattisgarh\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 8,\n" +
            "        \"name\": \"Dadra and Nagar Haveli\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9,\n" +
            "        \"name\": \"Daman and Diu\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10,\n" +
            "        \"name\": \"Delhi\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 11,\n" +
            "        \"name\": \"Goa\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 12,\n" +
            "        \"name\": \"Gujarat\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 13,\n" +
            "        \"name\": \"Haryana\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 14,\n" +
            "        \"name\": \"Himachal Pradesh\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 15,\n" +
            "        \"name\": \"Jammu & Kashmir\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 16,\n" +
            "        \"name\": \"Jharkhand\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 17,\n" +
            "        \"name\": \"Karnataka\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 18,\n" +
            "        \"name\": \"Kerala\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 19,\n" +
            "        \"name\": \"Lakshadweep\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 20,\n" +
            "        \"name\": \"Madhya Pradesh\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 21,\n" +
            "        \"name\": \"Maharashtra\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 22,\n" +
            "        \"name\": \"Manipur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 23,\n" +
            "        \"name\": \"Meghalaya\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 24,\n" +
            "        \"name\": \"Mizoram\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 25,\n" +
            "        \"name\": \"Nagaland\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 26,\n" +
            "        \"name\": \"Orissa\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 27,\n" +
            "        \"name\": \"Pondicherry\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 28,\n" +
            "        \"name\": \"Punjab\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 29,\n" +
            "        \"name\": \"Rajasthan\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 30,\n" +
            "        \"name\": \"Sikkim\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 31,\n" +
            "        \"name\": \"Tamil Nadu\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 32,\n" +
            "        \"name\": \"Tripura\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 33,\n" +
            "        \"name\": \"Uttaranchal\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 34,\n" +
            "        \"name\": \"Uttar Pradesh\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 35,\n" +
            "        \"name\": \"West Bengal\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 36,\n" +
            "        \"name\": \"Telangana\"\n" +
            "    }\n" +
            "]";
    private static String ANDMAN_CITIES = "[\n" +
            "    {\n" +
            "        \"id\": 960,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/960/\",\n" +
            "        \"name\": \"Diglipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/1/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1565,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1565/\",\n" +
            "        \"name\": \"Mayabunder\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/1/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 390,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/390/\",\n" +
            "        \"name\": \"Nicobar Island\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/1/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1/\",\n" +
            "        \"name\": \"Port Blair\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/1/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 661,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/661/\",\n" +
            "        \"name\": \"rangat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/1/\"\n" +
            "    }\n" +
            "]";

    private static String ANDRA_PRADESH = "\n" +
            "[\n" +
            "    {\n" +
            "        \"id\": 8,\n" +
            "        \"name\": \"Hyderabad\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1003,\n" +
            "        \"name\": \"Addanki\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1018,\n" +
            "        \"name\": \"Adoni\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1022,\n" +
            "        \"name\": \"Allagadda\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1024,\n" +
            "        \"name\": \"Amadalavalasa\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 985,\n" +
            "        \"name\": \"Amalapuram\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1648,\n" +
            "        \"name\": \"Amlapuram\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1019,\n" +
            "        \"name\": \"Anakapalli\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 3,\n" +
            "        \"name\": \"Anantapur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1025,\n" +
            "        \"name\": \"Ananthapur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 971,\n" +
            "        \"name\": \"Armoor\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1005,\n" +
            "        \"name\": \"Attili\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 981,\n" +
            "        \"name\": \"Avanigadda\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 987,\n" +
            "        \"name\": \"Bapatla\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 977,\n" +
            "        \"name\": \"Bhadrachalam\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 481,\n" +
            "        \"name\": \"bhimavaram\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 978,\n" +
            "        \"name\": \"Bhongir\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1017,\n" +
            "        \"name\": \"Bobbili\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1023,\n" +
            "        \"name\": \"Buckinghampet\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1008,\n" +
            "        \"name\": \"Chandragiri\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 999,\n" +
            "        \"name\": \"Chilakaluripeta\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 606,\n" +
            "        \"name\": \"chirala\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 4,\n" +
            "        \"name\": \"Chittoor\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1020,\n" +
            "        \"name\": \"Chodavaram\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 5,\n" +
            "        \"name\": \"Cuddapah\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1010,\n" +
            "        \"name\": \"Dargamitta\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 992,\n" +
            "        \"name\": \"Dharmavaram\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 6,\n" +
            "        \"name\": \"East Godavari\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 550,\n" +
            "        \"name\": \"elura\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 691,\n" +
            "        \"name\": \"eluru\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 983,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/983/\",\n" +
            "        \"name\": \"Gadwal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1558,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1558/\",\n" +
            "        \"name\": \"Gajuwaka\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 641,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/641/\",\n" +
            "        \"name\": \"gudivada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1011,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1011/\",\n" +
            "        \"name\": \"Gudur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 996,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/996/\",\n" +
            "        \"name\": \"Guntakal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 7,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/7/\",\n" +
            "        \"name\": \"Guntur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 973,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/973/\",\n" +
            "        \"name\": \"Hanamkonda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 948,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/948/\",\n" +
            "        \"name\": \"Hindupur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 972,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/972/\",\n" +
            "        \"name\": \"Huzurabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 984,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/984/\",\n" +
            "        \"name\": \"Jadcherla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 736,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/736/\",\n" +
            "        \"name\": \"jaggaiahpet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 665,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/665/\",\n" +
            "        \"name\": \"jagtial\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 976,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/976/\",\n" +
            "        \"name\": \"Jangaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 811,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/811/\",\n" +
            "        \"name\": \"jangareddygudam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1638,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1638/\",\n" +
            "        \"name\": \"Kadapa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 547,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/547/\",\n" +
            "        \"name\": \"kakinada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 970,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/970/\",\n" +
            "        \"name\": \"Kamareddy\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1002,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1002/\",\n" +
            "        \"name\": \"Kandukuru\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1004,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1004/\",\n" +
            "        \"name\": \"Kanigiri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 903,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/903/\",\n" +
            "        \"name\": \"Kavali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 800,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/800/\",\n" +
            "        \"name\": \"kothagudem\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 986,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/986/\",\n" +
            "        \"name\": \"Kothapeta\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 11,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/11/\",\n" +
            "        \"name\": \"Krishna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 12,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/12/\",\n" +
            "        \"name\": \"Kurnool\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 907,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/907/\",\n" +
            "        \"name\": \"Machilipatnam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1012,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1012/\",\n" +
            "        \"name\": \"Madanapalle\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1027,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1027/\",\n" +
            "        \"name\": \"Madhapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 974,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/974/\",\n" +
            "        \"name\": \"Mahabubabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 792,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/792/\",\n" +
            "        \"name\": \"mancherial\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 991,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/991/\",\n" +
            "        \"name\": \"Mandapeta\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 998,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/998/\",\n" +
            "        \"name\": \"Mangalagiri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1009,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1009/\",\n" +
            "        \"name\": \"Markapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 969,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/969/\",\n" +
            "        \"name\": \"Medak Dist\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1552,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1552/\",\n" +
            "        \"name\": \"Metpally\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1524,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1524/\",\n" +
            "        \"name\": \"Mettapalli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 635,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/635/\",\n" +
            "        \"name\": \"moosapet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 838,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/838/\",\n" +
            "        \"name\": \"Nagole\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1021,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1021/\",\n" +
            "        \"name\": \"Nandyal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 789,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/789/\",\n" +
            "        \"name\": \"narasaraopet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1367,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1367/\",\n" +
            "        \"name\": \"Narsapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1014,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1014/\",\n" +
            "        \"name\": \"Narsipatnam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 16,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/16/\",\n" +
            "        \"name\": \"Nellore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 827,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/827/\",\n" +
            "        \"name\": \"nirmal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 642,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/642/\",\n" +
            "        \"name\": \"nuzvid\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 517,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/517/\",\n" +
            "        \"name\": \"ongole\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1007,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1007/\",\n" +
            "        \"name\": \"Palakole\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1013,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1013/\",\n" +
            "        \"name\": \"Palmaner\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 975,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/975/\",\n" +
            "        \"name\": \"Parkal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1016,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1016/\",\n" +
            "        \"name\": \"Parvathipuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1529,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1529/\",\n" +
            "        \"name\": \"Patancheru\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 965,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/965/\",\n" +
            "        \"name\": \"Peddapalli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 18,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/18/\",\n" +
            "        \"name\": \"Prakasam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1001,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1001/\",\n" +
            "        \"name\": \"Proddatur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 526,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/526/\",\n" +
            "        \"name\": \"punjagutta\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 383,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/383/\",\n" +
            "        \"name\": \"Rajahmundry\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 997,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/997/\",\n" +
            "        \"name\": \"Rajampet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 990,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/990/\",\n" +
            "        \"name\": \"Ramachandrapuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 19,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/19/\",\n" +
            "        \"name\": \"Rangareddi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 989,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/989/\",\n" +
            "        \"name\": \"Razole\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 993,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/993/\",\n" +
            "        \"name\": \"Repalle\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 995,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/995/\",\n" +
            "        \"name\": \"Samalkot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 966,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/966/\",\n" +
            "        \"name\": \"Sanga Reddy\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 994,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/994/\",\n" +
            "        \"name\": \"Sattenapalle\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 967,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/967/\",\n" +
            "        \"name\": \"Siddipet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 20,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/20/\",\n" +
            "        \"name\": \"Srikakulam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1015,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1015/\",\n" +
            "        \"name\": \"Srikalahasti\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1000,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1000/\",\n" +
            "        \"name\": \"Tadepalligudem\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1522,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1522/\",\n" +
            "        \"name\": \"Tadipatri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1006,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1006/\",\n" +
            "        \"name\": \"Tanuku\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 982,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/982/\",\n" +
            "        \"name\": \"Tekkali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 988,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/988/\",\n" +
            "        \"name\": \"Tenali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 391,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/391/\",\n" +
            "        \"name\": \"Tirupati\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1369,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1369/\",\n" +
            "        \"name\": \"Tuni\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 341,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/341/\",\n" +
            "        \"name\": \"Vijayawada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 964,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/964/\",\n" +
            "        \"name\": \"Vikarabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 21,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/21/\",\n" +
            "        \"name\": \"Visakhapatnam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 456,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/456/\",\n" +
            "        \"name\": \"Vizag\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 22,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/22/\",\n" +
            "        \"name\": \"Vizayanagaram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1386,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1386/\",\n" +
            "        \"name\": \"Vizianagaram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 980,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/980/\",\n" +
            "        \"name\": \"Wanaparthy\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 24,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/24/\",\n" +
            "        \"name\": \"West Godavari\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 968,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/968/\",\n" +
            "        \"name\": \"Zahirabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/2/\"\n" +
            "    }\n" +
            "]";

    private static String ARUNACHHAL_PRADESH ="[\n" +
            "    {\n" +
            "        \"id\": 25,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/25/\",\n" +
            "        \"name\": \"Akashiganga\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1035,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1035/\",\n" +
            "        \"name\": \"Arunachal Pradesh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1366,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1366/\",\n" +
            "        \"name\": \"Banderdewa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 26,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/26/\",\n" +
            "        \"name\": \"Bhalukpong\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 27,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/27/\",\n" +
            "        \"name\": \"Bhismaknagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 28,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/28/\",\n" +
            "        \"name\": \"Bomdila\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1028,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1028/\",\n" +
            "        \"name\": \"Changlang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1403,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1403/\",\n" +
            "        \"name\": \"Dibang Valley\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1398,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1398/\",\n" +
            "        \"name\": \"East Kameng\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1400,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1400/\",\n" +
            "        \"name\": \"East Siang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 29,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/29/\",\n" +
            "        \"name\": \"Itanagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1029,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1029/\",\n" +
            "        \"name\": \"Kurung Kumey\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1402,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1402/\",\n" +
            "        \"name\": \"Lohit\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1404,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1404/\",\n" +
            "        \"name\": \"Lower Dibang Valley\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1030,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1030/\",\n" +
            "        \"name\": \"Lower Subansiri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 30,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/30/\",\n" +
            "        \"name\": \"Malinithan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 520,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/520/\",\n" +
            "        \"name\": \"naharlagun\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 521,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/521/\",\n" +
            "        \"name\": \"nirjuli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 923,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/923/\",\n" +
            "        \"name\": \"Papum Pare\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 832,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/832/\",\n" +
            "        \"name\": \"passighat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1031,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1031/\",\n" +
            "        \"name\": \"Tawang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1032,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1032/\",\n" +
            "        \"name\": \"Tirap\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1399,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1399/\",\n" +
            "        \"name\": \"Upper Siang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1401,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1401/\",\n" +
            "        \"name\": \"Upper Subansiri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1033,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1033/\",\n" +
            "        \"name\": \"West Kameng\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1034,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1034/\",\n" +
            "        \"name\": \"West Siang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/3/\"\n" +
            "    }\n" +
            "]";
    private static String ASSAM = "[\n" +
            "    {\n" +
            "        \"id\": 1570,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1570/\",\n" +
            "        \"name\": \"Baksa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 565,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/565/\",\n" +
            "        \"name\": \"barpeta\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1627,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1627/\",\n" +
            "        \"name\": \"Bijoynagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 476,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/476/\",\n" +
            "        \"name\": \"bokakhat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 392,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/392/\",\n" +
            "        \"name\": \"Bongaigaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1395,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1395/\",\n" +
            "        \"name\": \"Cachar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1569,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1569/\",\n" +
            "        \"name\": \"Chirang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1036,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1036/\",\n" +
            "        \"name\": \"Darrang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 841,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/841/\",\n" +
            "        \"name\": \"Dhemaji\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 585,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/585/\",\n" +
            "        \"name\": \"Dhubri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 676,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/676/\",\n" +
            "        \"name\": \"dhuliajan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 359,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/359/\",\n" +
            "        \"name\": \"Dibrugarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 31,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/31/\",\n" +
            "        \"name\": \"Digboi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1396,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1396/\",\n" +
            "        \"name\": \"Dima Hasao\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 32,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/32/\",\n" +
            "        \"name\": \"Dispur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 522,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/522/\",\n" +
            "        \"name\": \"duliajan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1037,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1037/\",\n" +
            "        \"name\": \"Goalpara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 393,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/393/\",\n" +
            "        \"name\": \"Golaghat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 512,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/512/\",\n" +
            "        \"name\": \"gossaigaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 33,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/33/\",\n" +
            "        \"name\": \"Guwahati\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 34,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/34/\",\n" +
            "        \"name\": \"Haflong\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1397,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1397/\",\n" +
            "        \"name\": \"Hailakandi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 35,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/35/\",\n" +
            "        \"name\": \"Hajo\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 566,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/566/\",\n" +
            "        \"name\": \"jagiroad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 358,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/358/\",\n" +
            "        \"name\": \"Jorhat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 356,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/356/\",\n" +
            "        \"name\": \"Kamrup\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1038,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1038/\",\n" +
            "        \"name\": \"Karbi Anglong\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 912,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/912/\",\n" +
            "        \"name\": \"Karimganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 840,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/840/\",\n" +
            "        \"name\": \"Kokrajhar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1572,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1572/\",\n" +
            "        \"name\": \"Lakhimpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 36,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/36/\",\n" +
            "        \"name\": \"Majuli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1042,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1042/\",\n" +
            "        \"name\": \"Mangaldai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 914,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/914/\",\n" +
            "        \"name\": \"Marigaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 357,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/357/\",\n" +
            "        \"name\": \"Nagaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1039,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1039/\",\n" +
            "        \"name\": \"Nalbari\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1424,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1424/\",\n" +
            "        \"name\": \"Narayanpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 482,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/482/\",\n" +
            "        \"name\": \"north lakhimpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1041,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1041/\",\n" +
            "        \"name\": \"Nowgong\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 37,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/37/\",\n" +
            "        \"name\": \"Sibsagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 360,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/360/\",\n" +
            "        \"name\": \"Silchar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1571,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1571/\",\n" +
            "        \"name\": \"Sivasagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1040,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1040/\",\n" +
            "        \"name\": \"Sonitpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 38,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/38/\",\n" +
            "        \"name\": \"Tezpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 351,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/351/\",\n" +
            "        \"name\": \"Tinsukia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1567,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1567/\",\n" +
            "        \"name\": \"Udalguri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/4/\"\n" +
            "    }\n" +
            "]";
    private static String BIHAR = "[\n" +
            "    {\n" +
            "        \"id\": 1043,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1043/\",\n" +
            "        \"name\": \"Anandpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1074,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1074/\",\n" +
            "        \"name\": \"Araria\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 509,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/509/\",\n" +
            "        \"name\": \"arrah\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1059,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1059/\",\n" +
            "        \"name\": \"Arwal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1050,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1050/\",\n" +
            "        \"name\": \"Aurangabad(bh)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1065,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1065/\",\n" +
            "        \"name\": \"Banka\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1066,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1066/\",\n" +
            "        \"name\": \"Barauni\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1076,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1076/\",\n" +
            "        \"name\": \"Barkakhana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 483,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/483/\",\n" +
            "        \"name\": \"begusarai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1047,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1047/\",\n" +
            "        \"name\": \"Bettiah\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1069,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1069/\",\n" +
            "        \"name\": \"Bhabhua\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 39,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/39/\",\n" +
            "        \"name\": \"Bhagalpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1046,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1046/\",\n" +
            "        \"name\": \"Bhojpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 958,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/958/\",\n" +
            "        \"name\": \"Bihar Sharif\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1370,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1370/\",\n" +
            "        \"name\": \"Bihta\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 506,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/506/\",\n" +
            "        \"name\": \"bodh gaya\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1045,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1045/\",\n" +
            "        \"name\": \"Buxar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 837,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/837/\",\n" +
            "        \"name\": \"Chhapra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1061,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1061/\",\n" +
            "        \"name\": \"Dalsingsarai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 724,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/724/\",\n" +
            "        \"name\": \"danapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 540,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/540/\",\n" +
            "        \"name\": \"darbhanga\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1072,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1072/\",\n" +
            "        \"name\": \"Dehri-on-sone\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1051,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1051/\",\n" +
            "        \"name\": \"East Champaran\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 40,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/40/\",\n" +
            "        \"name\": \"Gaya\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1077,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1077/\",\n" +
            "        \"name\": \"Gobindpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1079,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1079/\",\n" +
            "        \"name\": \"Gopal Ganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1052,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1052/\",\n" +
            "        \"name\": \"Gopalganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 702,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/702/\",\n" +
            "        \"name\": \"hajipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 575,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/575/\",\n" +
            "        \"name\": \"hazipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1523,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1523/\",\n" +
            "        \"name\": \"Hilsa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 962,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/962/\",\n" +
            "        \"name\": \"Jamui\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1060,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1060/\",\n" +
            "        \"name\": \"Jehanabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1549,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1549/\",\n" +
            "        \"name\": \"Kahalgaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1053,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1053/\",\n" +
            "        \"name\": \"Kaimur (bhabua)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 927,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/927/\",\n" +
            "        \"name\": \"Katihar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1412,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1412/\",\n" +
            "        \"name\": \"Khagaria\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1075,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1075/\",\n" +
            "        \"name\": \"Kishanganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1071,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1071/\",\n" +
            "        \"name\": \"Kochas\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1070,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1070/\",\n" +
            "        \"name\": \"Kudra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1048,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1048/\",\n" +
            "        \"name\": \"Laheriasarai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1054,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1054/\",\n" +
            "        \"name\": \"Lakhisarai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1055,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1055/\",\n" +
            "        \"name\": \"Madhepura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1049,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1049/\",\n" +
            "        \"name\": \"Madhubani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1078,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1078/\",\n" +
            "        \"name\": \"Marhourah\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 928,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/928/\",\n" +
            "        \"name\": \"Mohania\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1064,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1064/\",\n" +
            "        \"name\": \"Monghyr\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 514,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/514/\",\n" +
            "        \"name\": \"motihari\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1063,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1063/\",\n" +
            "        \"name\": \"Munger\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 394,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/394/\",\n" +
            "        \"name\": \"Muzaffarpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1044,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1044/\",\n" +
            "        \"name\": \"Nalanda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 516,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/516/\",\n" +
            "        \"name\": \"nawadah\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 41,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/41/\",\n" +
            "        \"name\": \"Patna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 518,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/518/\",\n" +
            "        \"name\": \"purnia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1073,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1073/\",\n" +
            "        \"name\": \"Rohtas\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1068,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1068/\",\n" +
            "        \"name\": \"Saharsa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 905,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/905/\",\n" +
            "        \"name\": \"Samastipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1056,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1056/\",\n" +
            "        \"name\": \"Saran\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 568,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/568/\",\n" +
            "        \"name\": \"sasaram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 918,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/918/\",\n" +
            "        \"name\": \"Sharif Bihar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1062,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1062/\",\n" +
            "        \"name\": \"Sheikhpura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1080,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1080/\",\n" +
            "        \"name\": \"Sheohar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1057,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1057/\",\n" +
            "        \"name\": \"Sitamarhi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 519,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/519/\",\n" +
            "        \"name\": \"siwan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1527,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1527/\",\n" +
            "        \"name\": \"Sonepur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1067,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1067/\",\n" +
            "        \"name\": \"Supaul\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1081,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1081/\",\n" +
            "        \"name\": \"Vaishali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1058,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1058/\",\n" +
            "        \"name\": \"West Champaran\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/5/\"\n" +
            "    }\n" +
            "]";
    private static String CHANDIGARH = "[\n" +
            "    {\n" +
            "        \"id\": 42,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/42/\",\n" +
            "        \"name\": \"Chandigarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 13,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/6/\"\n" +
            "    }\n" +
            "]";
    private static String CHHATTISGARH = "\n" +
            "[\n" +
            "    {\n" +
            "        \"id\": 484,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/484/\",\n" +
            "        \"name\": \"ambikapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1583,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1583/\",\n" +
            "        \"name\": \"Balod\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 694,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/694/\",\n" +
            "        \"name\": \"baloda bazaar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 510,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/510/\",\n" +
            "        \"name\": \"bastar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1584,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1584/\",\n" +
            "        \"name\": \"Bemetara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1427,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1427/\",\n" +
            "        \"name\": \"Bhanpuri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 43,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/43/\",\n" +
            "        \"name\": \"Bhilai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1538,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1538/\",\n" +
            "        \"name\": \"Bijapur (CH)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 44,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/44/\",\n" +
            "        \"name\": \"Bilaspur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1423,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1423/\",\n" +
            "        \"name\": \"Dantewada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 587,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/587/\",\n" +
            "        \"name\": \"Dhamtari\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 384,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/384/\",\n" +
            "        \"name\": \"Durg\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1585,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1585/\",\n" +
            "        \"name\": \"Gariaband\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 361,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/361/\",\n" +
            "        \"name\": \"Jagdalpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 757,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/757/\",\n" +
            "        \"name\": \"janjgir\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1371,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1371/\",\n" +
            "        \"name\": \"Jashpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1587,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1587/\",\n" +
            "        \"name\": \"Kabirdham\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1365,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1365/\",\n" +
            "        \"name\": \"Kanker\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1422,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1422/\",\n" +
            "        \"name\": \"Kawardha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1586,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1586/\",\n" +
            "        \"name\": \"Kondagaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 362,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/362/\",\n" +
            "        \"name\": \"Korba\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1372,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1372/\",\n" +
            "        \"name\": \"Koriya\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 714,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/714/\",\n" +
            "        \"name\": \"Mahasamund\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1532,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1532/\",\n" +
            "        \"name\": \"Manendragarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1588,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1588/\",\n" +
            "        \"name\": \"Mungeli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1537,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1537/\",\n" +
            "        \"name\": \"Narayanpur (CH)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 631,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/631/\",\n" +
            "        \"name\": \"raigarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 45,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/45/\",\n" +
            "        \"name\": \"Raipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 695,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/695/\",\n" +
            "        \"name\": \"rajim\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 704,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/704/\",\n" +
            "        \"name\": \"rajnandgaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1594,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1594/\",\n" +
            "        \"name\": \"Ramnujganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1590,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1590/\",\n" +
            "        \"name\": \"Sukma\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1589,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1589/\",\n" +
            "        \"name\": \"Surajpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 443,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/443/\",\n" +
            "        \"name\": \"Surguja\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/7/\"\n" +
            "    }\n" +
            "]";
    private static String DADRA_NAGAR = "[\n" +
            "    {\n" +
            "        \"id\": 46,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/46/\",\n" +
            "        \"name\": \"Dadra and Nagar Haveli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/8/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 438,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/438/\",\n" +
            "        \"name\": \"Silvassa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/8/\"\n" +
            "    }\n" +
            "]";
    private static String DAMAN_DIU = "[\n" +
            "    {\n" +
            "        \"id\": 47,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/47/\",\n" +
            "        \"name\": \"Daman\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/9/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 48,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/48/\",\n" +
            "        \"name\": \"Diu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/9/\"\n" +
            "    }\n" +
            "]";
    private static String DELHI = "[\n" +
            "    {\n" +
            "        \"id\": 49,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/49/\",\n" +
            "        \"name\": \"New Delhi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/10/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 475,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/475/\",\n" +
            "        \"name\": \"Delhi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 1,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/10/\"\n" +
            "    }\n" +
            "]";
    private static String GOA = "[\n" +
            "    {\n" +
            "        \"id\": 1643,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1643/\",\n" +
            "        \"name\": \"Bardez\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1373,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1373/\",\n" +
            "        \"name\": \"Bicholim\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 764,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/764/\",\n" +
            "        \"name\": \"cancona\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1428,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1428/\",\n" +
            "        \"name\": \"Cuncolim\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1630,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1630/\",\n" +
            "        \"name\": \"Curchorem\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1429,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1429/\",\n" +
            "        \"name\": \"Dharbandora\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 50,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/50/\",\n" +
            "        \"name\": \"Goa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 342,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/342/\",\n" +
            "        \"name\": \"Mandgaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 572,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/572/\",\n" +
            "        \"name\": \"mapusa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1374,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1374/\",\n" +
            "        \"name\": \"Margao\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 922,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/922/\",\n" +
            "        \"name\": \"Nagoa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 574,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/574/\",\n" +
            "        \"name\": \"nuvem\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 352,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/352/\",\n" +
            "        \"name\": \"Panaji\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 821,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/821/\",\n" +
            "        \"name\": \"ponda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 579,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/579/\",\n" +
            "        \"name\": \"Porvorim\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 382,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/382/\",\n" +
            "        \"name\": \"Shivali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 774,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/774/\",\n" +
            "        \"name\": \"taleigao\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 379,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/379/\",\n" +
            "        \"name\": \"Vasco Da Gama\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 524,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/524/\",\n" +
            "        \"name\": \"verna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/11/\"\n" +
            "    }\n" +
            "]";
    private static String GUJRAT ="[\n" +
            "    {\n" +
            "        \"id\": 51,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/51/\",\n" +
            "        \"name\": \"Ahmedabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 8,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 882,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/882/\",\n" +
            "        \"name\": \"Amod\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 803,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/803/\",\n" +
            "        \"name\": \"amreli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 52,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/52/\",\n" +
            "        \"name\": \"Anand\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 53,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/53/\",\n" +
            "        \"name\": \"Ankleshwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1384,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1384/\",\n" +
            "        \"name\": \"Banaskantha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1376,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1376/\",\n" +
            "        \"name\": \"Bansda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 531,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/531/\",\n" +
            "        \"name\": \"bardoli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1430,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1430/\",\n" +
            "        \"name\": \"Barwala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 819,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/819/\",\n" +
            "        \"name\": \"bavla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 644,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/644/\",\n" +
            "        \"name\": \"bhachau\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 54,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/54/\",\n" +
            "        \"name\": \"Bharuch\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 55,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/55/\",\n" +
            "        \"name\": \"Bhavnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 457,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/457/\",\n" +
            "        \"name\": \"Bhuj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 56,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/56/\",\n" +
            "        \"name\": \"Bhuj-Rudramata\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1087,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1087/\",\n" +
            "        \"name\": \"Bilimora\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 921,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/921/\",\n" +
            "        \"name\": \"Bodeli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 822,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/822/\",\n" +
            "        \"name\": \"botad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1092,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1092/\",\n" +
            "        \"name\": \"Dabhoi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 601,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/601/\",\n" +
            "        \"name\": \"dahod\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1385,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1385/\",\n" +
            "        \"name\": \"Dang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1375,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1375/\",\n" +
            "        \"name\": \"Deesa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 598,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/598/\",\n" +
            "        \"name\": \"dehgam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1431,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1431/\",\n" +
            "        \"name\": \"Devgadh Baria\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1564,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1564/\",\n" +
            "        \"name\": \"Dharampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 684,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/684/\",\n" +
            "        \"name\": \"dholka\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 942,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/942/\",\n" +
            "        \"name\": \"Dhrangadhra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 353,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/353/\",\n" +
            "        \"name\": \"Gandhidham\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 57,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/57/\",\n" +
            "        \"name\": \"Gandhinagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 453,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/453/\",\n" +
            "        \"name\": \"Godhra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 826,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/826/\",\n" +
            "        \"name\": \"gondal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 386,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/386/\",\n" +
            "        \"name\": \"Himat Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1432,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1432/\",\n" +
            "        \"name\": \"Idar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 58,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/58/\",\n" +
            "        \"name\": \"Jamnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1433,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1433/\",\n" +
            "        \"name\": \"Jasdan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1434,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1434/\",\n" +
            "        \"name\": \"Jetpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 385,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/385/\",\n" +
            "        \"name\": \"Junagadh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1096,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1096/\",\n" +
            "        \"name\": \"Kachchh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 889,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/889/\",\n" +
            "        \"name\": \"Kadi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 772,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/772/\",\n" +
            "        \"name\": \"kalol\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1095,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1095/\",\n" +
            "        \"name\": \"Kamrej\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 59,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/59/\",\n" +
            "        \"name\": \"Kapadvanj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1435,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1435/\",\n" +
            "        \"name\": \"Karjan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1085,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1085/\",\n" +
            "        \"name\": \"Khambhalia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1090,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1090/\",\n" +
            "        \"name\": \"Kheda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 650,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/650/\",\n" +
            "        \"name\": \"kheralu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1436,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1436/\",\n" +
            "        \"name\": \"Kim\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 363,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/363/\",\n" +
            "        \"name\": \"Mahesana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1437,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1437/\",\n" +
            "        \"name\": \"Mahuva\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1438,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1438/\",\n" +
            "        \"name\": \"Mandal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1088,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1088/\",\n" +
            "        \"name\": \"Mandvi (kutchh)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1439,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1439/\",\n" +
            "        \"name\": \"Mansa (GJ)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 683,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/683/\",\n" +
            "        \"name\": \"modasa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 60,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/60/\",\n" +
            "        \"name\": \"Morbi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1440,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1440/\",\n" +
            "        \"name\": \"Moti Khavdi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 808,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/808/\",\n" +
            "        \"name\": \"mundra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 61,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/61/\",\n" +
            "        \"name\": \"Nadiad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1093,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1093/\",\n" +
            "        \"name\": \"Narmada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 62,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/62/\",\n" +
            "        \"name\": \"Navsari\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1441,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1441/\",\n" +
            "        \"name\": \"Olpad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1442,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1442/\",\n" +
            "        \"name\": \"Padra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 395,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/395/\",\n" +
            "        \"name\": \"Palanpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 888,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/888/\",\n" +
            "        \"name\": \"Palsana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1097,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1097/\",\n" +
            "        \"name\": \"Panch Mahals\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1091,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1091/\",\n" +
            "        \"name\": \"Panchmahal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 709,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/709/\",\n" +
            "        \"name\": \"patan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 63,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/63/\",\n" +
            "        \"name\": \"Porbandar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1443,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1443/\",\n" +
            "        \"name\": \"Prantij\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 64,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/64/\",\n" +
            "        \"name\": \"Rajkot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1094,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1094/\",\n" +
            "        \"name\": \"Rajpardi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 65,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/65/\",\n" +
            "        \"name\": \"Rajpipla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1383,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1383/\",\n" +
            "        \"name\": \"Sabarkantha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1629,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1629/\",\n" +
            "        \"name\": \"Sanand\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 740,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/740/\",\n" +
            "        \"name\": \"santrampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1444,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1444/\",\n" +
            "        \"name\": \"Sayan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1089,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1089/\",\n" +
            "        \"name\": \"Sola Housing Board Col\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 66,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/66/\",\n" +
            "        \"name\": \"Surat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 794,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/794/\",\n" +
            "        \"name\": \"surenderanagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 562,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/562/\",\n" +
            "        \"name\": \"surendra nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 67,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/67/\",\n" +
            "        \"name\": \"Surendranagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1543,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1543/\",\n" +
            "        \"name\": \"Tapi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1563,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1563/\",\n" +
            "        \"name\": \"Umbergaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1086,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1086/\",\n" +
            "        \"name\": \"Union Territory\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 589,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/589/\",\n" +
            "        \"name\": \"unjha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 68,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/68/\",\n" +
            "        \"name\": \"Vadodara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 69,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/69/\",\n" +
            "        \"name\": \"Valsad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 623,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/623/\",\n" +
            "        \"name\": \"valvada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 380,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/380/\",\n" +
            "        \"name\": \"Vapi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 756,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/756/\",\n" +
            "        \"name\": \"veraval\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 710,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/710/\",\n" +
            "        \"name\": \"vijapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1445,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1445/\",\n" +
            "        \"name\": \"Viramgam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 656,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/656/\",\n" +
            "        \"name\": \"visnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/12/\"\n" +
            "    }\n" +
            "]";
    private static String HARYANA ="\n" +
            "[\n" +
            "    {\n" +
            "        \"id\": 70,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/70/\",\n" +
            "        \"name\": \"Ambala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 769,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/769/\",\n" +
            "        \"name\": \"assandh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 558,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/558/\",\n" +
            "        \"name\": \"bahadurgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 643,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/643/\",\n" +
            "        \"name\": \"ballabhgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1446,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1446/\",\n" +
            "        \"name\": \"Barara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 541,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/541/\",\n" +
            "        \"name\": \"bhiwadi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 71,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/71/\",\n" +
            "        \"name\": \"Bhiwani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 816,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/816/\",\n" +
            "        \"name\": \"charkhi dadri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 716,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/716/\",\n" +
            "        \"name\": \"cheeka\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1631,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1631/\",\n" +
            "        \"name\": \"Dharuhera\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1447,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1447/\",\n" +
            "        \"name\": \"Ellenabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 72,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/72/\",\n" +
            "        \"name\": \"Faridabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 73,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/73/\",\n" +
            "        \"name\": \"Fatehbad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1448,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1448/\",\n" +
            "        \"name\": \"Ganaur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 645,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/645/\",\n" +
            "        \"name\": \"gharaunda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 713,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/713/\",\n" +
            "        \"name\": \"gohana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 74,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/74/\",\n" +
            "        \"name\": \"Gurgaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 797,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/797/\",\n" +
            "        \"name\": \"hansi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1099,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1099/\",\n" +
            "        \"name\": \"Hisar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 75,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/75/\",\n" +
            "        \"name\": \"Hissar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 852,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/852/\",\n" +
            "        \"name\": \"Hodal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 364,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/364/\",\n" +
            "        \"name\": \"Jadadhri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 892,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/892/\",\n" +
            "        \"name\": \"Jagadhri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 76,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/76/\",\n" +
            "        \"name\": \"Jhajjar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 77,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/77/\",\n" +
            "        \"name\": \"Jind\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 78,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/78/\",\n" +
            "        \"name\": \"Kaithal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 609,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/609/\",\n" +
            "        \"name\": \"kalka\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 79,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/79/\",\n" +
            "        \"name\": \"Karnal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 727,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/727/\",\n" +
            "        \"name\": \"kundli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1098,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1098/\",\n" +
            "        \"name\": \"Kurukshatra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 80,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/80/\",\n" +
            "        \"name\": \"Kurukshetra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 81,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/81/\",\n" +
            "        \"name\": \"Mahendergarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 779,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/779/\",\n" +
            "        \"name\": \"mandi dabwali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 549,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/549/\",\n" +
            "        \"name\": \"manesar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 860,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/860/\",\n" +
            "        \"name\": \"Meham\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1647,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1647/\",\n" +
            "        \"name\": \"Mewat\",\n" +
            "        \"show_in_lead_form\": true,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1449,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1449/\",\n" +
            "        \"name\": \"Murthal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1646,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1646/\",\n" +
            "        \"name\": \"Nalhar\",\n" +
            "        \"show_in_lead_form\": true,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 673,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/673/\",\n" +
            "        \"name\": \"naraingarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 515,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/515/\",\n" +
            "        \"name\": \"narnaul\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 492,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/492/\",\n" +
            "        \"name\": \"narwana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1450,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1450/\",\n" +
            "        \"name\": \"Nuh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 536,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/536/\",\n" +
            "        \"name\": \"palwal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 82,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/82/\",\n" +
            "        \"name\": \"Panchkula\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 83,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/83/\",\n" +
            "        \"name\": \"Panipat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 767,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/767/\",\n" +
            "        \"name\": \"pataudi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 849,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/849/\",\n" +
            "        \"name\": \"Pehowa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 681,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/681/\",\n" +
            "        \"name\": \"radaur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 84,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/84/\",\n" +
            "        \"name\": \"Rewari\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 85,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/85/\",\n" +
            "        \"name\": \"Rohtak\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 768,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/768/\",\n" +
            "        \"name\": \"saffidon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 744,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/744/\",\n" +
            "        \"name\": \"samalkha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1451,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1451/\",\n" +
            "        \"name\": \"Sampla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 491,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/491/\",\n" +
            "        \"name\": \"shahbad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 86,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/86/\",\n" +
            "        \"name\": \"Sirsa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 689,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/689/\",\n" +
            "        \"name\": \"sohna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 87,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/87/\",\n" +
            "        \"name\": \"Sonepat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 88,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/88/\",\n" +
            "        \"name\": \"Yamunanagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/13/\"\n" +
            "    }\n" +
            "]";
    private static String HIMACHAL_PRADESH ="[\n" +
            "    {\n" +
            "        \"id\": 496,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/496/\",\n" +
            "        \"name\": \"amb\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1104,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1104/\",\n" +
            "        \"name\": \"Barsar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1534,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1534/\",\n" +
            "        \"name\": \"Bilaspur(HP)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 654,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/654/\",\n" +
            "        \"name\": \"chamba\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 89,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/89/\",\n" +
            "        \"name\": \"Dalhousie\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 507,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/507/\",\n" +
            "        \"name\": \"damtal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 620,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/620/\",\n" +
            "        \"name\": \"dehra gopipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1106,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1106/\",\n" +
            "        \"name\": \"Dharmasala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 396,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/396/\",\n" +
            "        \"name\": \"Hamirpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1107,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1107/\",\n" +
            "        \"name\": \"Hamirpur(hp)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 773,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/773/\",\n" +
            "        \"name\": \"jassur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 365,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/365/\",\n" +
            "        \"name\": \"Kangra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1417,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1417/\",\n" +
            "        \"name\": \"Kinnaur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 90,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/90/\",\n" +
            "        \"name\": \"Kullu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1418,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1418/\",\n" +
            "        \"name\": \"Lahaul-Spiti\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1426,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1426/\",\n" +
            "        \"name\": \"Manali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 354,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/354/\",\n" +
            "        \"name\": \"Mandi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 397,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/397/\",\n" +
            "        \"name\": \"Nagrota\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1103,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1103/\",\n" +
            "        \"name\": \"Nahan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1619,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1619/\",\n" +
            "        \"name\": \"Nalagarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 814,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/814/\",\n" +
            "        \"name\": \"nurpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1105,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1105/\",\n" +
            "        \"name\": \"Palampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 711,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/711/\",\n" +
            "        \"name\": \"paonta sahib\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1101,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1101/\",\n" +
            "        \"name\": \"Rampur Bushahr\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1102,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1102/\",\n" +
            "        \"name\": \"Rekong Peo\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 952,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/952/\",\n" +
            "        \"name\": \"Rohru\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 91,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/91/\",\n" +
            "        \"name\": \"Shimla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1100,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1100/\",\n" +
            "        \"name\": \"Simla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1416,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1416/\",\n" +
            "        \"name\": \"Sirmaur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 92,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/92/\",\n" +
            "        \"name\": \"Solan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 906,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/906/\",\n" +
            "        \"name\": \"Una\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/14/\"\n" +
            "    }\n" +
            "]";
    private static String JAMMU_KASHMIR ="\n" +
            "[\n" +
            "    {\n" +
            "        \"id\": 677,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/677/\",\n" +
            "        \"name\": \"akhnoor\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 583,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/583/\",\n" +
            "        \"name\": \"Anantnag\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1121,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1121/\",\n" +
            "        \"name\": \"Badharwah\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1114,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1114/\",\n" +
            "        \"name\": \"Bakshi Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1124,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1124/\",\n" +
            "        \"name\": \"Balwal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1452,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1452/\",\n" +
            "        \"name\": \"Bandipore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 664,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/664/\",\n" +
            "        \"name\": \"baramula\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1110,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1110/\",\n" +
            "        \"name\": \"Budgam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1119,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1119/\",\n" +
            "        \"name\": \"Channi Himmat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1111,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1111/\",\n" +
            "        \"name\": \"Doda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1525,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1525/\",\n" +
            "        \"name\": \"Ganderbal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1109,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1109/\",\n" +
            "        \"name\": \"Gandhi Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 93,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/93/\",\n" +
            "        \"name\": \"Gulmarg\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 343,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/343/\",\n" +
            "        \"name\": \"Jammu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1108,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1108/\",\n" +
            "        \"name\": \"Jammu Tawi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1120,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1120/\",\n" +
            "        \"name\": \"Janipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1419,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1419/\",\n" +
            "        \"name\": \"Kargil\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 775,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/775/\",\n" +
            "        \"name\": \"kathua\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1632,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1632/\",\n" +
            "        \"name\": \"Katra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1453,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1453/\",\n" +
            "        \"name\": \"Kulgam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 746,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/746/\",\n" +
            "        \"name\": \"kupwara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 94,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/94/\",\n" +
            "        \"name\": \"Ladakh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 95,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/95/\",\n" +
            "        \"name\": \"Leh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1123,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1123/\",\n" +
            "        \"name\": \"New Plot Jammu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 96,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/96/\",\n" +
            "        \"name\": \"Pahalgam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1112,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1112/\",\n" +
            "        \"name\": \"Poonch\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1116,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1116/\",\n" +
            "        \"name\": \"Prem Nagar Doda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 617,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/617/\",\n" +
            "        \"name\": \"pulwama\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1113,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1113/\",\n" +
            "        \"name\": \"Rajauri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 669,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/669/\",\n" +
            "        \"name\": \"rajouri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1117,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1117/\",\n" +
            "        \"name\": \"Rehari Colony\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1122,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1122/\",\n" +
            "        \"name\": \"Residency Road Jammu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 658,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/658/\",\n" +
            "        \"name\": \"rs pura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1118,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1118/\",\n" +
            "        \"name\": \"Sainik Coloney\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1115,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1115/\",\n" +
            "        \"name\": \"Shastri Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 97,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/97/\",\n" +
            "        \"name\": \"Srinagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 468,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/468/\",\n" +
            "        \"name\": \"Srinagar Gpo\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1454,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1454/\",\n" +
            "        \"name\": \"Sunderbani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 398,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/398/\",\n" +
            "        \"name\": \"Udhampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/15/\"\n" +
            "    }\n" +
            "]";
    private static String JHARKHAND ="[\n" +
            "    {\n" +
            "        \"id\": 98,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/98/\",\n" +
            "        \"name\": \"Adityapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 935,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/935/\",\n" +
            "        \"name\": \"Baharagora\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1136,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1136/\",\n" +
            "        \"name\": \"Baidyanath Deoghar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 99,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/99/\",\n" +
            "        \"name\": \"Bokaro\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1644,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1644/\",\n" +
            "        \"name\": \"Chaibasa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1143,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1143/\",\n" +
            "        \"name\": \"Chakulia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1125,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1125/\",\n" +
            "        \"name\": \"Chatra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 511,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/511/\",\n" +
            "        \"name\": \"daltanganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 721,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/721/\",\n" +
            "        \"name\": \"daltongang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 573,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/573/\",\n" +
            "        \"name\": \"deoghar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 100,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/100/\",\n" +
            "        \"name\": \"Dhanbad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 439,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/439/\",\n" +
            "        \"name\": \"Dumka\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1126,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1126/\",\n" +
            "        \"name\": \"East Singhbhum\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1414,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1414/\",\n" +
            "        \"name\": \"Garhwa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1144,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1144/\",\n" +
            "        \"name\": \"Ghatsila\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1127,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1127/\",\n" +
            "        \"name\": \"Giridh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 782,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/782/\",\n" +
            "        \"name\": \"giridih\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1140,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1140/\",\n" +
            "        \"name\": \"Gobindpur (JH)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 640,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/640/\",\n" +
            "        \"name\": \"godda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1139,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1139/\",\n" +
            "        \"name\": \"Gomoh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 950,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/950/\",\n" +
            "        \"name\": \"Gumla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1128,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1128/\",\n" +
            "        \"name\": \"Hazaribag\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 101,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/101/\",\n" +
            "        \"name\": \"Hazaribagh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 102,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/102/\",\n" +
            "        \"name\": \"Jamshedpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1129,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1129/\",\n" +
            "        \"name\": \"Jamtara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1546,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1546/\",\n" +
            "        \"name\": \"Khunti\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1137,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1137/\",\n" +
            "        \"name\": \"Kodarma\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1130,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1130/\",\n" +
            "        \"name\": \"Koderma\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1131,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1131/\",\n" +
            "        \"name\": \"Latehar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 619,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/619/\",\n" +
            "        \"name\": \"lohardaga\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1141,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1141/\",\n" +
            "        \"name\": \"Musabani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 567,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/567/\",\n" +
            "        \"name\": \"pakur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1413,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1413/\",\n" +
            "        \"name\": \"Palamu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1142,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1142/\",\n" +
            "        \"name\": \"Patamda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1541,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1541/\",\n" +
            "        \"name\": \"Phusro\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 546,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/546/\",\n" +
            "        \"name\": \"ramgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 103,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/103/\",\n" +
            "        \"name\": \"Ranchi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1132,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1132/\",\n" +
            "        \"name\": \"Sahibganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1133,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1133/\",\n" +
            "        \"name\": \"Seraikela-kharsawan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1135,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1135/\",\n" +
            "        \"name\": \"Simdega\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1138,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1138/\",\n" +
            "        \"name\": \"Sindri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1134,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1134/\",\n" +
            "        \"name\": \"West Singhbhum\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/16/\"\n" +
            "    }\n" +
            "]";
    private static String KARNATAKA ="\n" +
            "[\n" +
            "    {\n" +
            "        \"id\": 105,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/105/\",\n" +
            "        \"name\": \"Bangalore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 2,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 616,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/616/\",\n" +
            "        \"name\": \"anekal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1547,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1547/\",\n" +
            "        \"name\": \"Ankola\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1165,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1165/\",\n" +
            "        \"name\": \"Arsikere\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1455,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1455/\",\n" +
            "        \"name\": \"Athani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1159,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1159/\",\n" +
            "        \"name\": \"Athni\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 104,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/104/\",\n" +
            "        \"name\": \"Bagalkot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1157,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1157/\",\n" +
            "        \"name\": \"Bailhongal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1456,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1456/\",\n" +
            "        \"name\": \"Bangarapet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1457,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1457/\",\n" +
            "        \"name\": \"Bantwal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1655,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1655/\",\n" +
            "        \"name\": \"Belagavi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 106,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/106/\",\n" +
            "        \"name\": \"Belgaum\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 107,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/107/\",\n" +
            "        \"name\": \"Bellary\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 657,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/657/\",\n" +
            "        \"name\": \"belthangady\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1169,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1169/\",\n" +
            "        \"name\": \"Bhadravathi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 108,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/108/\",\n" +
            "        \"name\": \"Bidar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 109,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/109/\",\n" +
            "        \"name\": \"Bijapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 110,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/110/\",\n" +
            "        \"name\": \"Chamrajnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1164,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1164/\",\n" +
            "        \"name\": \"Channapatna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 793,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/793/\",\n" +
            "        \"name\": \"chickmangalur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 858,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/858/\",\n" +
            "        \"name\": \"Chikkaballapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 111,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/111/\",\n" +
            "        \"name\": \"Chikmagalur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 913,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/913/\",\n" +
            "        \"name\": \"Chikodi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 112,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/112/\",\n" +
            "        \"name\": \"Chitradurga\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 113,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/113/\",\n" +
            "        \"name\": \"Coorg\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 114,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/114/\",\n" +
            "        \"name\": \"Dakshina Kannada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1170,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1170/\",\n" +
            "        \"name\": \"Davangare\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 115,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/115/\",\n" +
            "        \"name\": \"Davangere\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1458,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1458/\",\n" +
            "        \"name\": \"Devanahalli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1388,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1388/\",\n" +
            "        \"name\": \"Dharwad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 116,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/116/\",\n" +
            "        \"name\": \"Dharwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 117,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/117/\",\n" +
            "        \"name\": \"Gadag\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 786,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/786/\",\n" +
            "        \"name\": \"gangavati\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 932,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/932/\",\n" +
            "        \"name\": \"Gokak\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 399,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/399/\",\n" +
            "        \"name\": \"Gonikoppal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 118,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/118/\",\n" +
            "        \"name\": \"Gulbarga\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1150,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1150/\",\n" +
            "        \"name\": \"Hampi Power Ho\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1149,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1149/\",\n" +
            "        \"name\": \"Harapanahalli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 119,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/119/\",\n" +
            "        \"name\": \"Hassan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 120,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/120/\",\n" +
            "        \"name\": \"Haveri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 597,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/597/\",\n" +
            "        \"name\": \"hoskote\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 387,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/387/\",\n" +
            "        \"name\": \"Hospet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 345,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/345/\",\n" +
            "        \"name\": \"Hubli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1160,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1160/\",\n" +
            "        \"name\": \"Hukkeri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1154,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1154/\",\n" +
            "        \"name\": \"Indi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1155,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1155/\",\n" +
            "        \"name\": \"Jamkhandi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1657,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1657/\",\n" +
            "        \"name\": \"Jnanasahyadri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1557,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1557/\",\n" +
            "        \"name\": \"Kadapa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1654,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1654/\",\n" +
            "        \"name\": \"Kalaburagi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 668,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/668/\",\n" +
            "        \"name\": \"karkala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 762,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/762/\",\n" +
            "        \"name\": \"karwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 728,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/728/\",\n" +
            "        \"name\": \"kengari\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1387,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1387/\",\n" +
            "        \"name\": \"Kodagu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 121,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/121/\",\n" +
            "        \"name\": \"Kolar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1163,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1163/\",\n" +
            "        \"name\": \"Kollegal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1168,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1168/\",\n" +
            "        \"name\": \"Koppa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 122,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/122/\",\n" +
            "        \"name\": \"Koppal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1166,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1166/\",\n" +
            "        \"name\": \"Kulshekar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1147,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1147/\",\n" +
            "        \"name\": \"Kumta\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 749,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/749/\",\n" +
            "        \"name\": \"kundapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1459,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1459/\",\n" +
            "        \"name\": \"Kunigal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 946,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/946/\",\n" +
            "        \"name\": \"Kushalanagara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 947,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/947/\",\n" +
            "        \"name\": \"Madikeri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 123,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/123/\",\n" +
            "        \"name\": \"Mandya\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 344,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/344/\",\n" +
            "        \"name\": \"Mangalore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1167,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1167/\",\n" +
            "        \"name\": \"Manipal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 622,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/622/\",\n" +
            "        \"name\": \"moodbidri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 723,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/723/\",\n" +
            "        \"name\": \"mulki\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 400,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/400/\",\n" +
            "        \"name\": \"Murudeshwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 682,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/682/\",\n" +
            "        \"name\": \"murudeswar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 124,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/124/\",\n" +
            "        \"name\": \"Mysore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1145,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1145/\",\n" +
            "        \"name\": \"Nanjangud\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1148,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1148/\",\n" +
            "        \"name\": \"Nargund\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 785,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/785/\",\n" +
            "        \"name\": \"puttur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1161,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1161/\",\n" +
            "        \"name\": \"Raibag\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 125,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/125/\",\n" +
            "        \"name\": \"Raichur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 718,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/718/\",\n" +
            "        \"name\": \"ramanagara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1158,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1158/\",\n" +
            "        \"name\": \"Ramdurg\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1146,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1146/\",\n" +
            "        \"name\": \"Ranebennur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1566,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1566/\",\n" +
            "        \"name\": \"Sagara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1153,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1153/\",\n" +
            "        \"name\": \"Shahabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 126,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/126/\",\n" +
            "        \"name\": \"Shimoga\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1152,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1152/\",\n" +
            "        \"name\": \"Shorapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 824,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/824/\",\n" +
            "        \"name\": \"sindhnur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 872,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/872/\",\n" +
            "        \"name\": \"Sirsi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1162,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1162/\",\n" +
            "        \"name\": \"Srirangapatna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 806,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/806/\",\n" +
            "        \"name\": \"sullia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 663,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/663/\",\n" +
            "        \"name\": \"surthkal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1460,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1460/\",\n" +
            "        \"name\": \"Thokottu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1156,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1156/\",\n" +
            "        \"name\": \"Tilakwadi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 820,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/820/\",\n" +
            "        \"name\": \"tiptur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 127,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/127/\",\n" +
            "        \"name\": \"Tumkur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 128,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/128/\",\n" +
            "        \"name\": \"Udupi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 916,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/916/\",\n" +
            "        \"name\": \"Uppinangady\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 129,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/129/\",\n" +
            "        \"name\": \"Uttara Kannada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1658,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1658/\",\n" +
            "        \"name\": \"Vijayapura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1151,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1151/\",\n" +
            "        \"name\": \"Yadgiri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1656,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1656/\",\n" +
            "        \"name\": \"Yelahanka\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/17/\"\n" +
            "    }\n" +
            "]";
    private static String KERLA ="[\n" +
            "    {\n" +
            "        \"id\": 712,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/712/\",\n" +
            "        \"name\": \"adoor\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 593,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/593/\",\n" +
            "        \"name\": \"alappuza\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 130,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/130/\",\n" +
            "        \"name\": \"Alappuzha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 460,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/460/\",\n" +
            "        \"name\": \"Alleppey\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1172,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1172/\",\n" +
            "        \"name\": \"Alleppy\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 479,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/479/\",\n" +
            "        \"name\": \"aluva\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 569,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/569/\",\n" +
            "        \"name\": \"angamaly\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 499,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/499/\",\n" +
            "        \"name\": \"attingal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1176,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1176/\",\n" +
            "        \"name\": \"Badagara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1461,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1461/\",\n" +
            "        \"name\": \"Balaramapuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 429,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/429/\",\n" +
            "        \"name\": \"Calicut\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 461,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/461/\",\n" +
            "        \"name\": \"Cannanore (Kannur)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 895,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/895/\",\n" +
            "        \"name\": \"Chalakudy\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 552,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/552/\",\n" +
            "        \"name\": \"changanassery\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1462,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1462/\",\n" +
            "        \"name\": \"Changaramkulam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 915,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/915/\",\n" +
            "        \"name\": \"Chavakkad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 883,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/883/\",\n" +
            "        \"name\": \"Chengannur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 618,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/618/\",\n" +
            "        \"name\": \"cherpulassery\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 555,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/555/\",\n" +
            "        \"name\": \"cherthala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 752,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/752/\",\n" +
            "        \"name\": \"chillakuddy\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 131,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/131/\",\n" +
            "        \"name\": \"Cochin\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 747,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/747/\",\n" +
            "        \"name\": \"edappal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 132,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/132/\",\n" +
            "        \"name\": \"Ernakulam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1521,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1521/\",\n" +
            "        \"name\": \"Guruvayoor\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 133,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/133/\",\n" +
            "        \"name\": \"Idukki\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 733,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/733/\",\n" +
            "        \"name\": \"irinjalakuda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 634,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/634/\",\n" +
            "        \"name\": \"kaipamangalam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1641,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1641/\",\n" +
            "        \"name\": \"Kalady\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 735,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/735/\",\n" +
            "        \"name\": \"kalamassery\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 920,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/920/\",\n" +
            "        \"name\": \"Kallambalam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 134,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/134/\",\n" +
            "        \"name\": \"Kalpetta\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 685,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/685/\",\n" +
            "        \"name\": \"kanhangad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 839,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/839/\",\n" +
            "        \"name\": \"Kanjirappally\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 700,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/700/\",\n" +
            "        \"name\": \"kanjrapally\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 135,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/135/\",\n" +
            "        \"name\": \"Kannur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 588,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/588/\",\n" +
            "        \"name\": \"karikkad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 828,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/828/\",\n" +
            "        \"name\": \"karunagapally\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 577,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/577/\",\n" +
            "        \"name\": \"Karunagappally\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 136,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/136/\",\n" +
            "        \"name\": \"Kasaragod\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 556,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/556/\",\n" +
            "        \"name\": \"kattappana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 437,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/437/\",\n" +
            "        \"name\": \"Kayamkulam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 346,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/346/\",\n" +
            "        \"name\": \"Kochi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 708,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/708/\",\n" +
            "        \"name\": \"kodangallur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 137,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/137/\",\n" +
            "        \"name\": \"Kollam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1463,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1463/\",\n" +
            "        \"name\": \"Koothattukulam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1603,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1603/\",\n" +
            "        \"name\": \"Koratty\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 790,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/790/\",\n" +
            "        \"name\": \"kothamanglam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 823,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/823/\",\n" +
            "        \"name\": \"kottakkal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 602,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/602/\",\n" +
            "        \"name\": \"kottarakara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 138,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/138/\",\n" +
            "        \"name\": \"Kottayam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 897,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/897/\",\n" +
            "        \"name\": \"Koyilandy\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 551,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/551/\",\n" +
            "        \"name\": \"kozhenchery\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 139,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/139/\",\n" +
            "        \"name\": \"Kozikode\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 753,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/753/\",\n" +
            "        \"name\": \"kunnamkulam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1652,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1652/\",\n" +
            "        \"name\": \"Kuttanad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 525,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/525/\",\n" +
            "        \"name\": \"mahe\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 739,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/739/\",\n" +
            "        \"name\": \"maillatty\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1177,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1177/\",\n" +
            "        \"name\": \"Malappuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 140,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/140/\",\n" +
            "        \"name\": \"Malapuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1464,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1464/\",\n" +
            "        \"name\": \"Mallappally\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 758,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/758/\",\n" +
            "        \"name\": \"manjari\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 857,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/857/\",\n" +
            "        \"name\": \"Mannarkkad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 633,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/633/\",\n" +
            "        \"name\": \"mavelikara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 538,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/538/\",\n" +
            "        \"name\": \"mavelikkara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 462,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/462/\",\n" +
            "        \"name\": \"Muvattupuzha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1623,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1623/\",\n" +
            "        \"name\": \"Nedumangad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 706,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/706/\",\n" +
            "        \"name\": \"neyattinkara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 701,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/701/\",\n" +
            "        \"name\": \"nilambur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 737,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/737/\",\n" +
            "        \"name\": \"nilamel\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1171,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1171/\",\n" +
            "        \"name\": \"Olavakkot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1173,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1173/\",\n" +
            "        \"name\": \"Ottapalam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 553,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/553/\",\n" +
            "        \"name\": \"pala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 141,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/141/\",\n" +
            "        \"name\": \"Palakkad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1615,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1615/\",\n" +
            "        \"name\": \"Panoor\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 898,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/898/\",\n" +
            "        \"name\": \"Parassala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1465,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1465/\",\n" +
            "        \"name\": \"Paravur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 142,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/142/\",\n" +
            "        \"name\": \"Pathanamthitta\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 666,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/666/\",\n" +
            "        \"name\": \"pattambi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 599,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/599/\",\n" +
            "        \"name\": \"payannur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1616,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1616/\",\n" +
            "        \"name\": \"Perambra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 381,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/381/\",\n" +
            "        \"name\": \"Perintalmanna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 801,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/801/\",\n" +
            "        \"name\": \"perumbavoor\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1174,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1174/\",\n" +
            "        \"name\": \"Ponani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 610,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/610/\",\n" +
            "        \"name\": \"punalur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 401,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/401/\",\n" +
            "        \"name\": \"Quilon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 835,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/835/\",\n" +
            "        \"name\": \"ramanattukara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 614,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/614/\",\n" +
            "        \"name\": \"ranni\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 594,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/594/\",\n" +
            "        \"name\": \"sasthamkottah\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1175,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1175/\",\n" +
            "        \"name\": \"Shertally\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1622,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1622/\",\n" +
            "        \"name\": \"Sultan Bathery\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 554,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/554/\",\n" +
            "        \"name\": \"talayolaparambu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 649,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/649/\",\n" +
            "        \"name\": \"taliparamba\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 833,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/833/\",\n" +
            "        \"name\": \"tellicherry\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1625,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1625/\",\n" +
            "        \"name\": \"Thalassery\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 856,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/856/\",\n" +
            "        \"name\": \"Thamarassery\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1178,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1178/\",\n" +
            "        \"name\": \"Thariyode\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 637,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/637/\",\n" +
            "        \"name\": \"thirur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 548,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/548/\",\n" +
            "        \"name\": \"thiruvalla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 347,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/347/\",\n" +
            "        \"name\": \"Thiruvananthapuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 557,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/557/\",\n" +
            "        \"name\": \"thodupuzha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 143,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/143/\",\n" +
            "        \"name\": \"Thrissur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 884,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/884/\",\n" +
            "        \"name\": \"Thrithallur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 578,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/578/\",\n" +
            "        \"name\": \"Tirur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 366,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/366/\",\n" +
            "        \"name\": \"Trichur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 632,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/632/\",\n" +
            "        \"name\": \"triprayar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 655,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/655/\",\n" +
            "        \"name\": \"tripuntara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 144,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/144/\",\n" +
            "        \"name\": \"Trivandrum\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 954,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/954/\",\n" +
            "        \"name\": \"Uppala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1466,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1466/\",\n" +
            "        \"name\": \"Urakam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 777,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/777/\",\n" +
            "        \"name\": \"vadakara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 917,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/917/\",\n" +
            "        \"name\": \"Vaikom\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 818,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/818/\",\n" +
            "        \"name\": \"vettichira\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 804,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/804/\",\n" +
            "        \"name\": \"wadakkanchery\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 561,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/561/\",\n" +
            "        \"name\": \"wayanad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/18/\"\n" +
            "    }\n" +
            "]";
    private static String LAKSHADWEEP ="\n" +
            "[\n" +
            "    {\n" +
            "        \"id\": 581,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/581/\",\n" +
            "        \"name\": \"Kavaratti\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/19/\"\n" +
            "    }\n" +
            "    {\n" +
            "        \"id\": 145,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/145/\",\n" +
            "        \"name\": \"Lakshadweep\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/19/\"\n" +
            "    },\n" +
            "]";
    private static String MADHYA_PRADESH ="[\n" +
            "    {\n" +
            "        \"id\": 152,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/152/\",\n" +
            "        \"name\": \"Bhopal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 10,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1573,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1573/\",\n" +
            "        \"name\": \"Agar Malwa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 146,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/146/\",\n" +
            "        \"name\": \"Anuppur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 147,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/147/\",\n" +
            "        \"name\": \"Ashoknagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 148,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/148/\",\n" +
            "        \"name\": \"Balaghat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1467,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1467/\",\n" +
            "        \"name\": \"Barwaha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 149,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/149/\",\n" +
            "        \"name\": \"Barwani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 150,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/150/\",\n" +
            "        \"name\": \"Betul\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 151,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/151/\",\n" +
            "        \"name\": \"Bhind\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 153,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/153/\",\n" +
            "        \"name\": \"Bhuranpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 901,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/901/\",\n" +
            "        \"name\": \"Biaora\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 498,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/498/\",\n" +
            "        \"name\": \"burhanpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 607,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/607/\",\n" +
            "        \"name\": \"chattarpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 155,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/155/\",\n" +
            "        \"name\": \"Chhaindwara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 154,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/154/\",\n" +
            "        \"name\": \"Chhatarpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 434,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/434/\",\n" +
            "        \"name\": \"Dabra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 156,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/156/\",\n" +
            "        \"name\": \"Damoh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 157,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/157/\",\n" +
            "        \"name\": \"Datia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1468,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1468/\",\n" +
            "        \"name\": \"Depalpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 158,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/158/\",\n" +
            "        \"name\": \"Dewas\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 159,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/159/\",\n" +
            "        \"name\": \"Dhar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 160,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/160/\",\n" +
            "        \"name\": \"Dindori\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1469,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1469/\",\n" +
            "        \"name\": \"Ganj Basoda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 426,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/426/\",\n" +
            "        \"name\": \"Guna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 161,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/161/\",\n" +
            "        \"name\": \"Gwalior\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 162,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/162/\",\n" +
            "        \"name\": \"Harda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 163,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/163/\",\n" +
            "        \"name\": \"Hoshangabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 164,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/164/\",\n" +
            "        \"name\": \"Indore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1180,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1180/\",\n" +
            "        \"name\": \"Indore Yeshwant Road\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 165,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/165/\",\n" +
            "        \"name\": \"Jabalpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 166,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/166/\",\n" +
            "        \"name\": \"Jhabua\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 167,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/167/\",\n" +
            "        \"name\": \"Katni\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 168,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/168/\",\n" +
            "        \"name\": \"Khandwa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 169,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/169/\",\n" +
            "        \"name\": \"Khargone\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1470,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1470/\",\n" +
            "        \"name\": \"Kotma\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1182,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1182/\",\n" +
            "        \"name\": \"Lashkar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1471,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1471/\",\n" +
            "        \"name\": \"Maihar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 170,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/170/\",\n" +
            "        \"name\": \"Mandla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 171,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/171/\",\n" +
            "        \"name\": \"Mandsaur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1472,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1472/\",\n" +
            "        \"name\": \"Mhow\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 172,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/172/\",\n" +
            "        \"name\": \"Morena\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1179,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1179/\",\n" +
            "        \"name\": \"Narasimhapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 173,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/173/\",\n" +
            "        \"name\": \"Narsinghpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 174,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/174/\",\n" +
            "        \"name\": \"Neemuch\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 175,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/175/\",\n" +
            "        \"name\": \"Panna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 176,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/176/\",\n" +
            "        \"name\": \"Raisen\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 177,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/177/\",\n" +
            "        \"name\": \"Rajgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1181,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1181/\",\n" +
            "        \"name\": \"Rathlam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 178,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/178/\",\n" +
            "        \"name\": \"Ratlam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 179,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/179/\",\n" +
            "        \"name\": \"Rewa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 180,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/180/\",\n" +
            "        \"name\": \"Sagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1473,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1473/\",\n" +
            "        \"name\": \"Sanwer\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 181,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/181/\",\n" +
            "        \"name\": \"Satna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 182,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/182/\",\n" +
            "        \"name\": \"Sehore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 183,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/183/\",\n" +
            "        \"name\": \"Seoni\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 184,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/184/\",\n" +
            "        \"name\": \"Shahdol\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 185,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/185/\",\n" +
            "        \"name\": \"Shajapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 186,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/186/\",\n" +
            "        \"name\": \"Sheopur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 187,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/187/\",\n" +
            "        \"name\": \"Shivpuri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 188,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/188/\",\n" +
            "        \"name\": \"Sidhi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 697,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/697/\",\n" +
            "        \"name\": \"singrauli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 189,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/189/\",\n" +
            "        \"name\": \"Tikamgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 190,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/190/\",\n" +
            "        \"name\": \"Ujjain\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 191,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/191/\",\n" +
            "        \"name\": \"Umaria\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 192,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/192/\",\n" +
            "        \"name\": \"Vidisha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 563,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/563/\",\n" +
            "        \"name\": \"waidhan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/20/\"\n" +
            "    }\n" +
            "]";
    private static String MAHARASHTRA ="[\n" +
            "    {\n" +
            "        \"id\": 201,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/201/\",\n" +
            "        \"name\": \"Mumbai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 5,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 205,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/205/\",\n" +
            "        \"name\": \"Pune\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 6,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1204,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1204/\",\n" +
            "        \"name\": \"Ahmed Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 193,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/193/\",\n" +
            "        \"name\": \"Ahmednagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1196,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1196/\",\n" +
            "        \"name\": \"Ahmednagarv\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 741,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/741/\",\n" +
            "        \"name\": \"akluj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 194,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/194/\",\n" +
            "        \"name\": \"Akola\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1531,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1531/\",\n" +
            "        \"name\": \"Alandi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 834,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/834/\",\n" +
            "        \"name\": \"alibaug\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1476,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1476/\",\n" +
            "        \"name\": \"Ambajogai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1477,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1477/\",\n" +
            "        \"name\": \"Ambegaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1611,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1611/\",\n" +
            "        \"name\": \"Ambernath\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 195,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/195/\",\n" +
            "        \"name\": \"Amravati\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1474,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1474/\",\n" +
            "        \"name\": \"Ankleshwar (MH)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1202,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1202/\",\n" +
            "        \"name\": \"Asegaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 196,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/196/\",\n" +
            "        \"name\": \"Aurangabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1612,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1612/\",\n" +
            "        \"name\": \"Badlapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1478,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1478/\",\n" +
            "        \"name\": \"Ballarpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 485,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/485/\",\n" +
            "        \"name\": \"baramati\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 924,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/924/\",\n" +
            "        \"name\": \"Barshi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 535,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/535/\",\n" +
            "        \"name\": \"beed\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 613,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/613/\",\n" +
            "        \"name\": \"bhandara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1198,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1198/\",\n" +
            "        \"name\": \"Bhandra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1203,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1203/\",\n" +
            "        \"name\": \"Bhayandar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1187,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1187/\",\n" +
            "        \"name\": \"Bhiwandi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1194,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1194/\",\n" +
            "        \"name\": \"Bhusawal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 652,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/652/\",\n" +
            "        \"name\": \"boisar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 887,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/887/\",\n" +
            "        \"name\": \"Brahmapuri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1199,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1199/\",\n" +
            "        \"name\": \"Buldana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1192,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1192/\",\n" +
            "        \"name\": \"Chalisgaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 367,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/367/\",\n" +
            "        \"name\": \"Chandrapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1639,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1639/\",\n" +
            "        \"name\": \"Chandwad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1193,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1193/\",\n" +
            "        \"name\": \"Chinchwad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 646,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/646/\",\n" +
            "        \"name\": \"chiplun\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 867,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/867/\",\n" +
            "        \"name\": \"Dahanu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1479,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1479/\",\n" +
            "        \"name\": \"Dapoli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1195,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1195/\",\n" +
            "        \"name\": \"Daund\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1186,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1186/\",\n" +
            "        \"name\": \"Devgadh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 197,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/197/\",\n" +
            "        \"name\": \"Dhule\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1480,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1480/\",\n" +
            "        \"name\": \"Dindori (MH)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 653,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/653/\",\n" +
            "        \"name\": \"dombilvli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1205,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1205/\",\n" +
            "        \"name\": \"Gadchiroli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 690,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/690/\",\n" +
            "        \"name\": \"gadingalaj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 590,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/590/\",\n" +
            "        \"name\": \"gondia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1197,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1197/\",\n" +
            "        \"name\": \"Gondia Civil Lines\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 452,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/452/\",\n" +
            "        \"name\": \"Hingoli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 815,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/815/\",\n" +
            "        \"name\": \"ichalkaransi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 799,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/799/\",\n" +
            "        \"name\": \"indapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1481,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1481/\",\n" +
            "        \"name\": \"Islampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 198,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/198/\",\n" +
            "        \"name\": \"Jalgaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 648,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/648/\",\n" +
            "        \"name\": \"jalna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1482,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1482/\",\n" +
            "        \"name\": \"Jaysingpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 523,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/523/\",\n" +
            "        \"name\": \"kalyan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 760,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/760/\",\n" +
            "        \"name\": \"kankavali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 783,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/783/\",\n" +
            "        \"name\": \"kapholi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 817,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/817/\",\n" +
            "        \"name\": \"karad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 871,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/871/\",\n" +
            "        \"name\": \"Karjat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 612,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/612/\",\n" +
            "        \"name\": \"kavathe mhankal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1200,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1200/\",\n" +
            "        \"name\": \"Khamgaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1483,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1483/\",\n" +
            "        \"name\": \"Khandala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1475,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1475/\",\n" +
            "        \"name\": \"Kharghar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 199,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/199/\",\n" +
            "        \"name\": \"Kolhapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 836,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/836/\",\n" +
            "        \"name\": \"kongaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 732,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/732/\",\n" +
            "        \"name\": \"kudal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 402,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/402/\",\n" +
            "        \"name\": \"Latur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 200,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/200/\",\n" +
            "        \"name\": \"Mahabaleshwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 850,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/850/\",\n" +
            "        \"name\": \"Mahad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 763,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/763/\",\n" +
            "        \"name\": \"malegaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1185,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1185/\",\n" +
            "        \"name\": \"Malvan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1191,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1191/\",\n" +
            "        \"name\": \"Matheran\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1621,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1621/\",\n" +
            "        \"name\": \"Mira Road\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 639,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/639/\",\n" +
            "        \"name\": \"miraj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1484,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1484/\",\n" +
            "        \"name\": \"Murgud\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 202,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/202/\",\n" +
            "        \"name\": \"Nagpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 203,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/203/\",\n" +
            "        \"name\": \"Nanded\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 591,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/591/\",\n" +
            "        \"name\": \"nandurbar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 825,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/825/\",\n" +
            "        \"name\": \"narayangaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 204,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/204/\",\n" +
            "        \"name\": \"Nashik\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1188,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1188/\",\n" +
            "        \"name\": \"Nasik\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 463,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/463/\",\n" +
            "        \"name\": \"Navi Mumbai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1189,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1189/\",\n" +
            "        \"name\": \"New Bombay\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 624,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/624/\",\n" +
            "        \"name\": \"osmanabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1190,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1190/\",\n" +
            "        \"name\": \"Palghar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 938,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/938/\",\n" +
            "        \"name\": \"Palus\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 911,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/911/\",\n" +
            "        \"name\": \"Pandharpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 486,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/486/\",\n" +
            "        \"name\": \"panvel\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1201,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1201/\",\n" +
            "        \"name\": \"Paratwada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 759,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/759/\",\n" +
            "        \"name\": \"parbhani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 755,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/755/\",\n" +
            "        \"name\": \"patas\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1485,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1485/\",\n" +
            "        \"name\": \"Pen\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1486,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1486/\",\n" +
            "        \"name\": \"Phaltan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 388,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/388/\",\n" +
            "        \"name\": \"Pimpri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 528,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/528/\",\n" +
            "        \"name\": \"pimpri chinchwad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 638,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/638/\",\n" +
            "        \"name\": \"rahata\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1208,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1208/\",\n" +
            "        \"name\": \"Raigad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1206,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1206/\",\n" +
            "        \"name\": \"Raigarh(mh)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1487,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1487/\",\n" +
            "        \"name\": \"Rajapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 403,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/403/\",\n" +
            "        \"name\": \"Ratnagiri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1533,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1533/\",\n" +
            "        \"name\": \"Raver\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 543,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/543/\",\n" +
            "        \"name\": \"sangamner\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 206,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/206/\",\n" +
            "        \"name\": \"Sangli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 861,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/861/\",\n" +
            "        \"name\": \"Saswad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 207,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/207/\",\n" +
            "        \"name\": \"Satara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1183,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1183/\",\n" +
            "        \"name\": \"Sawantwadi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1613,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1613/\",\n" +
            "        \"name\": \"Shahad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1544,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1544/\",\n" +
            "        \"name\": \"Shirdi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 881,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/881/\",\n" +
            "        \"name\": \"Shirur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 902,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/902/\",\n" +
            "        \"name\": \"Shrigonda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 798,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/798/\",\n" +
            "        \"name\": \"shrirampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 944,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/944/\",\n" +
            "        \"name\": \"Sillod\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1207,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1207/\",\n" +
            "        \"name\": \"Sindhudurg\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 688,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/688/\",\n" +
            "        \"name\": \"sinnar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 208,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/208/\",\n" +
            "        \"name\": \"Solapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 209,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/209/\",\n" +
            "        \"name\": \"Thane\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 508,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/508/\",\n" +
            "        \"name\": \"ulhasnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1556,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1556/\",\n" +
            "        \"name\": \"Vadkhal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 787,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/787/\",\n" +
            "        \"name\": \"vaduj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 447,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/447/\",\n" +
            "        \"name\": \"Vaijanath\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 542,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/542/\",\n" +
            "        \"name\": \"vasai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 529,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/529/\",\n" +
            "        \"name\": \"vashi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1184,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1184/\",\n" +
            "        \"name\": \"Vengurla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 899,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/899/\",\n" +
            "        \"name\": \"Virar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1540,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1540/\",\n" +
            "        \"name\": \"Vita\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 719,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/719/\",\n" +
            "        \"name\": \"wagholi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1548,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1548/\",\n" +
            "        \"name\": \"Wai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 813,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/813/\",\n" +
            "        \"name\": \"waluj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 904,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/904/\",\n" +
            "        \"name\": \"Wani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 210,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/210/\",\n" +
            "        \"name\": \"Wardha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1488,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1488/\",\n" +
            "        \"name\": \"Warud\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 703,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/703/\",\n" +
            "        \"name\": \"washim\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 446,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/446/\",\n" +
            "        \"name\": \"Yavatmal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/21/\"\n" +
            "    }\n" +
            "]";
    private static String MANIPUR ="[\n" +
            "    {\n" +
            "        \"id\": 211,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/211/\",\n" +
            "        \"name\": \"Bishnupur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 212,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/212/\",\n" +
            "        \"name\": \"Chandel\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 213,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/213/\",\n" +
            "        \"name\": \"Churachandpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 214,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/214/\",\n" +
            "        \"name\": \"Imphal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1210,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1210/\",\n" +
            "        \"name\": \"Leikai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 215,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/215/\",\n" +
            "        \"name\": \"Manipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 216,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/216/\",\n" +
            "        \"name\": \"Senapati\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 217,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/217/\",\n" +
            "        \"name\": \"Tamenglong\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1213,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1213/\",\n" +
            "        \"name\": \"Thoubal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 218,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/218/\",\n" +
            "        \"name\": \"Ukhrul\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/22/\"\n" +
            "    }\n" +
            "]";
    private static String MEGHALAYA ="[\n" +
            "    {\n" +
            "        \"id\": 219,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/219/\",\n" +
            "        \"name\": \"East Gara Hills\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1405,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1405/\",\n" +
            "        \"name\": \"East Garo Hills\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 220,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/220/\",\n" +
            "        \"name\": \"East Khasi Hills\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 221,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/221/\",\n" +
            "        \"name\": \"Jaintia Hills\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 473,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/473/\",\n" +
            "        \"name\": \"Jorabat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 937,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/937/\",\n" +
            "        \"name\": \"Jowai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 770,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/770/\",\n" +
            "        \"name\": \"khliehriat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 842,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/842/\",\n" +
            "        \"name\": \"Nongpoh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 919,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/919/\",\n" +
            "        \"name\": \"Nongstoin\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 222,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/222/\",\n" +
            "        \"name\": \"Ri Bhoi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 368,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/368/\",\n" +
            "        \"name\": \"Shillong\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 223,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/223/\",\n" +
            "        \"name\": \"South Garo Hills\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 532,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/532/\",\n" +
            "        \"name\": \"tura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 224,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/224/\",\n" +
            "        \"name\": \"West Garo Hills\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 225,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/225/\",\n" +
            "        \"name\": \"West Khasi Hills\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/23/\"\n" +
            "    }\n" +
            "]";
    private static String MIZORAM ="[\n" +
            "    {\n" +
            "        \"id\": 226,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/226/\",\n" +
            "        \"name\": \"Aizawl\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1214,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1214/\",\n" +
            "        \"name\": \"Aizwal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 227,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/227/\",\n" +
            "        \"name\": \"Champai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1407,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1407/\",\n" +
            "        \"name\": \"Champhai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 228,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/228/\",\n" +
            "        \"name\": \"Kolasib\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1408,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1408/\",\n" +
            "        \"name\": \"Lawngtlai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 229,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/229/\",\n" +
            "        \"name\": \"Lunglei\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 230,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/230/\",\n" +
            "        \"name\": \"Mamit\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 231,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/231/\",\n" +
            "        \"name\": \"Mizoram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1409,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1409/\",\n" +
            "        \"name\": \"Saiha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1406,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1406/\",\n" +
            "        \"name\": \"Serchhip\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 232,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/232/\",\n" +
            "        \"name\": \"Vairengte\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/24/\"\n" +
            "    }\n" +
            "]";
    private static String NAGALAND ="[\n" +
            "    {\n" +
            "        \"id\": 233,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/233/\",\n" +
            "        \"name\": \"Dimapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1411,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1411/\",\n" +
            "        \"name\": \"Kiphire\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 234,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/234/\",\n" +
            "        \"name\": \"Kohima\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1215,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1215/\",\n" +
            "        \"name\": \"Longleng\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 235,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/235/\",\n" +
            "        \"name\": \"Mokokchung\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 236,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/236/\",\n" +
            "        \"name\": \"Mon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1410,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1410/\",\n" +
            "        \"name\": \"Peren\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 237,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/237/\",\n" +
            "        \"name\": \"Phek\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 238,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/238/\",\n" +
            "        \"name\": \"Tuensang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 239,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/239/\",\n" +
            "        \"name\": \"Wokha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 240,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/240/\",\n" +
            "        \"name\": \"Zunheboto\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/25/\"\n" +
            "    }\n" +
            "]";
    private static String ORISSA ="\n" +
            "[\n" +
            "    {\n" +
            "        \"id\": 404,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/404/\",\n" +
            "        \"name\": \"Angul\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1232,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1232/\",\n" +
            "        \"name\": \"Aska\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1227,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1227/\",\n" +
            "        \"name\": \"Athgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1393,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1393/\",\n" +
            "        \"name\": \"Balangir\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 241,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/241/\",\n" +
            "        \"name\": \"Balasore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1216,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1216/\",\n" +
            "        \"name\": \"Baleswar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 430,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/430/\",\n" +
            "        \"name\": \"Bargarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 667,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/667/\",\n" +
            "        \"name\": \"baripada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 242,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/242/\",\n" +
            "        \"name\": \"Berhampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 933,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/933/\",\n" +
            "        \"name\": \"Bhadrak\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1233,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1233/\",\n" +
            "        \"name\": \"Bhanja Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 854,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/854/\",\n" +
            "        \"name\": \"Bhawanipatna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 243,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/243/\",\n" +
            "        \"name\": \"Bhubaneshwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 659,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/659/\",\n" +
            "        \"name\": \"bolangir\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1217,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1217/\",\n" +
            "        \"name\": \"Boudh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1661,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1661/\",\n" +
            "        \"name\": \"Burla\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1226,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1226/\",\n" +
            "        \"name\": \"Chandini Chouk\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1231,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1231/\",\n" +
            "        \"name\": \"Chhatrapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 244,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/244/\",\n" +
            "        \"name\": \"Cuttack\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1394,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1394/\",\n" +
            "        \"name\": \"Debagarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 692,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/692/\",\n" +
            "        \"name\": \"dhenkanal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1389,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1389/\",\n" +
            "        \"name\": \"Gajapati\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1218,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1218/\",\n" +
            "        \"name\": \"Ganjam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1228,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1228/\",\n" +
            "        \"name\": \"Jagatsinghpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 672,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/672/\",\n" +
            "        \"name\": \"jajpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1229,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1229/\",\n" +
            "        \"name\": \"Jaleswar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 472,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/472/\",\n" +
            "        \"name\": \"Jeypore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 441,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/441/\",\n" +
            "        \"name\": \"Jharsuguda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1219,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1219/\",\n" +
            "        \"name\": \"Kalahandi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1390,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1390/\",\n" +
            "        \"name\": \"Kandhamal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1220,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1220/\",\n" +
            "        \"name\": \"Kendrapara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1221,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1221/\",\n" +
            "        \"name\": \"Kendujhar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 458,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/458/\",\n" +
            "        \"name\": \"Keonjhar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1222,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1222/\",\n" +
            "        \"name\": \"Khorda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1225,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1225/\",\n" +
            "        \"name\": \"Khurda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 586,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/586/\",\n" +
            "        \"name\": \"Koraput\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1391,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1391/\",\n" +
            "        \"name\": \"Malkangiri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 474,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/474/\",\n" +
            "        \"name\": \"Mayurbhanj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1223,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1223/\",\n" +
            "        \"name\": \"Nabarangapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 731,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/731/\",\n" +
            "        \"name\": \"nayagarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1392,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1392/\",\n" +
            "        \"name\": \"Nuapada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1234,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1234/\",\n" +
            "        \"name\": \"Paralakhemundi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1235,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1235/\",\n" +
            "        \"name\": \"Phulbani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1224,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1224/\",\n" +
            "        \"name\": \"Puri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1230,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1230/\",\n" +
            "        \"name\": \"Rairangapu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 891,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/891/\",\n" +
            "        \"name\": \"Rayagada\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 245,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/245/\",\n" +
            "        \"name\": \"Rourkela\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 246,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/246/\",\n" +
            "        \"name\": \"Sambalpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1610,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1610/\",\n" +
            "        \"name\": \"Sonepur(OR)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 450,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/450/\",\n" +
            "        \"name\": \"Sundergarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/26/\"\n" +
            "    }\n" +
            "]";
    private static String PONDICHERRY ="[\n" +
            "    {\n" +
            "        \"id\": 247,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/247/\",\n" +
            "        \"name\": \"Pondicherry\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/27/\"\n" +
            "    }\n" +
            "]";
    private static String PUNJAB ="[\n" +
            "    {\n" +
            "        \"id\": 405,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/405/\",\n" +
            "        \"name\": \"Abohar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 248,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/248/\",\n" +
            "        \"name\": \"Amritsar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 249,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/249/\",\n" +
            "        \"name\": \"Anandpur Sahib\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 671,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/671/\",\n" +
            "        \"name\": \"banga\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 494,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/494/\",\n" +
            "        \"name\": \"barnala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1489,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1489/\",\n" +
            "        \"name\": \"Batala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1238,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1238/\",\n" +
            "        \"name\": \"Bathinda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 250,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/250/\",\n" +
            "        \"name\": \"Bhatinda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 564,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/564/\",\n" +
            "        \"name\": \"dasuya\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 605,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/605/\",\n" +
            "        \"name\": \"datala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 734,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/734/\",\n" +
            "        \"name\": \"derabassi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1490,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1490/\",\n" +
            "        \"name\": \"Devigarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 886,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/886/\",\n" +
            "        \"name\": \"Doraha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 251,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/251/\",\n" +
            "        \"name\": \"Faridkot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1415,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1415/\",\n" +
            "        \"name\": \"Fatehgarh Sahib\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 722,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/722/\",\n" +
            "        \"name\": \"fazilka\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 252,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/252/\",\n" +
            "        \"name\": \"Ferozepur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1239,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1239/\",\n" +
            "        \"name\": \"Firozpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 726,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/726/\",\n" +
            "        \"name\": \"garhankar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 464,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/464/\",\n" +
            "        \"name\": \"Gobindgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 253,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/253/\",\n" +
            "        \"name\": \"Gurdaspur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1237,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1237/\",\n" +
            "        \"name\": \"Gurudaspur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 254,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/254/\",\n" +
            "        \"name\": \"Hoshiarpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 699,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/699/\",\n" +
            "        \"name\": \"jagraon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1491,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1491/\",\n" +
            "        \"name\": \"Jaitu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 807,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/807/\",\n" +
            "        \"name\": \"jalalabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 255,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/255/\",\n" +
            "        \"name\": \"Jalandhar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 784,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/784/\",\n" +
            "        \"name\": \"jandiala guru\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 256,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/256/\",\n" +
            "        \"name\": \"Kapurthala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 369,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/369/\",\n" +
            "        \"name\": \"Khanna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 743,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/743/\",\n" +
            "        \"name\": \"kharar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 257,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/257/\",\n" +
            "        \"name\": \"Ludhiana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 715,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/715/\",\n" +
            "        \"name\": \"machiwara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 754,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/754/\",\n" +
            "        \"name\": \"mahalpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 802,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/802/\",\n" +
            "        \"name\": \"malout\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 647,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/647/\",\n" +
            "        \"name\": \"mandi gobindgar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 406,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/406/\",\n" +
            "        \"name\": \"Mandi Gobindgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 851,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/851/\",\n" +
            "        \"name\": \"Mansa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 258,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/258/\",\n" +
            "        \"name\": \"Moga\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 355,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/355/\",\n" +
            "        \"name\": \"Mohali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 407,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/407/\",\n" +
            "        \"name\": \"Mukatsar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1492,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1492/\",\n" +
            "        \"name\": \"Mukerian\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 931,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/931/\",\n" +
            "        \"name\": \"Muktsar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 878,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/878/\",\n" +
            "        \"name\": \"Nabha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 495,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/495/\",\n" +
            "        \"name\": \"nakodar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 259,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/259/\",\n" +
            "        \"name\": \"Nangal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 408,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/408/\",\n" +
            "        \"name\": \"Nawanshahr\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1494,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1494/\",\n" +
            "        \"name\": \"Nihal Singh Wala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 260,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/260/\",\n" +
            "        \"name\": \"Pathankot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 261,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/261/\",\n" +
            "        \"name\": \"Patiala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 831,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/831/\",\n" +
            "        \"name\": \"patran\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 621,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/621/\",\n" +
            "        \"name\": \"phagwara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1562,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1562/\",\n" +
            "        \"name\": \"Phillaur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1495,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1495/\",\n" +
            "        \"name\": \"Pohir\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1493,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1493/\",\n" +
            "        \"name\": \"Raikot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 544,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/544/\",\n" +
            "        \"name\": \"rajpura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 959,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/959/\",\n" +
            "        \"name\": \"Rampura Phul\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 409,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/409/\",\n" +
            "        \"name\": \"RoopNagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 262,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/262/\",\n" +
            "        \"name\": \"Ropar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1554,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1554/\",\n" +
            "        \"name\": \"Samrala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 263,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/263/\",\n" +
            "        \"name\": \"Sanghol\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 264,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/264/\",\n" +
            "        \"name\": \"Sangrur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 870,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/870/\",\n" +
            "        \"name\": \"Sirhind-Fategarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 955,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/955/\",\n" +
            "        \"name\": \"Sultanpur Lodhi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 738,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/738/\",\n" +
            "        \"name\": \"talwara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 693,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/693/\",\n" +
            "        \"name\": \"tanda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 493,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/493/\",\n" +
            "        \"name\": \"tanda urmar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1236,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1236/\",\n" +
            "        \"name\": \"Tarn Taran\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 679,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/679/\",\n" +
            "        \"name\": \"zira\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 445,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/445/\",\n" +
            "        \"name\": \"Zirakpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/28/\"\n" +
            "    }\n" +
            "]";
    private static String RAJASTHAN = "[\n" +
            "    {\n" +
            "        \"id\": 269,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/269/\",\n" +
            "        \"name\": \"Jaipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 8,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 271,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/271/\",\n" +
            "        \"name\": \"Kota\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 11,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1628,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1628/\",\n" +
            "        \"name\": \"Abu Road\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 265,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/265/\",\n" +
            "        \"name\": \"Ajmer\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 266,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/266/\",\n" +
            "        \"name\": \"Alwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1496,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1496/\",\n" +
            "        \"name\": \"Bagru\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1595,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1595/\",\n" +
            "        \"name\": \"Balesar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1607,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1607/\",\n" +
            "        \"name\": \"Bali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 611,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/611/\",\n" +
            "        \"name\": \"balotra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1651,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1651/\",\n" +
            "        \"name\": \"Banasthali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 465,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/465/\",\n" +
            "        \"name\": \"Banswara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 651,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/651/\",\n" +
            "        \"name\": \"baran\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 720,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/720/\",\n" +
            "        \"name\": \"barmer\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1497,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1497/\",\n" +
            "        \"name\": \"Bassi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1247,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1247/\",\n" +
            "        \"name\": \"Bayana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 781,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/781/\",\n" +
            "        \"name\": \"beawar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 761,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/761/\",\n" +
            "        \"name\": \"behror\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 487,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/487/\",\n" +
            "        \"name\": \"bharatpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 267,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/267/\",\n" +
            "        \"name\": \"Bhilwara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1498,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1498/\",\n" +
            "        \"name\": \"Bhiwadi (RJ)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1598,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1598/\",\n" +
            "        \"name\": \"Bhopalgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 863,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/863/\",\n" +
            "        \"name\": \"Bijolia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 268,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/268/\",\n" +
            "        \"name\": \"Bikaner\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1599,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1599/\",\n" +
            "        \"name\": \"Bilara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 431,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/431/\",\n" +
            "        \"name\": \"Bundi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 600,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/600/\",\n" +
            "        \"name\": \"chaksu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 874,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/874/\",\n" +
            "        \"name\": \"Chirawa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 466,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/466/\",\n" +
            "        \"name\": \"Chittorgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 830,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/830/\",\n" +
            "        \"name\": \"chomu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 497,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/497/\",\n" +
            "        \"name\": \"churu\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 750,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/750/\",\n" +
            "        \"name\": \"Dausa\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1246,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1246/\",\n" +
            "        \"name\": \"Deeg\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 953,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/953/\",\n" +
            "        \"name\": \"Deoli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1251,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1251/\",\n" +
            "        \"name\": \"Dholpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 809,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/809/\",\n" +
            "        \"name\": \"didwana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 729,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/729/\",\n" +
            "        \"name\": \"dungarpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1609,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1609/\",\n" +
            "        \"name\": \"Falna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 869,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/869/\",\n" +
            "        \"name\": \"Fatehnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1256,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1256/\",\n" +
            "        \"name\": \"Ganganagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1248,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1248/\",\n" +
            "        \"name\": \"Gangapur Rs\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 436,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/436/\",\n" +
            "        \"name\": \"Hanumangarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 961,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/961/\",\n" +
            "        \"name\": \"Hindaun\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 894,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/894/\",\n" +
            "        \"name\": \"Jaisalmer\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1605,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1605/\",\n" +
            "        \"name\": \"Jaitaran\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1255,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1255/\",\n" +
            "        \"name\": \"Jalore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 748,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/748/\",\n" +
            "        \"name\": \"jhalawar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 539,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/539/\",\n" +
            "        \"name\": \"jhunjhunun\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 270,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/270/\",\n" +
            "        \"name\": \"Jodhpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1245,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1245/\",\n" +
            "        \"name\": \"Kankroli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1249,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1249/\",\n" +
            "        \"name\": \"Karauli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 717,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/717/\",\n" +
            "        \"name\": \"kekri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 859,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/859/\",\n" +
            "        \"name\": \"Kishangarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 751,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/751/\",\n" +
            "        \"name\": \"kotputli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1545,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1545/\",\n" +
            "        \"name\": \"Kuchaman City\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1602,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1602/\",\n" +
            "        \"name\": \"Luni\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1242,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1242/\",\n" +
            "        \"name\": \"Madanaganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1253,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1253/\",\n" +
            "        \"name\": \"Makrana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1243,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1243/\",\n" +
            "        \"name\": \"Marwar Jn\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1244,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1244/\",\n" +
            "        \"name\": \"Mavli Jn\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 745,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/745/\",\n" +
            "        \"name\": \"merta city\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 449,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/449/\",\n" +
            "        \"name\": \"Nagaur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1241,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1241/\",\n" +
            "        \"name\": \"Nasirabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 885,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/885/\",\n" +
            "        \"name\": \"Nathdwara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 868,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/868/\",\n" +
            "        \"name\": \"Neem-Ka-Thana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1499,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1499/\",\n" +
            "        \"name\": \"Neemrana\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1539,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1539/\",\n" +
            "        \"name\": \"Nimbahera\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 900,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/900/\",\n" +
            "        \"name\": \"Nohar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1500,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1500/\",\n" +
            "        \"name\": \"Nokha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1601,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1601/\",\n" +
            "        \"name\": \"Osian\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 411,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/411/\",\n" +
            "        \"name\": \"Pali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1597,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1597/\",\n" +
            "        \"name\": \"Phalodi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1636,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1636/\",\n" +
            "        \"name\": \"Pilani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1600,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1600/\",\n" +
            "        \"name\": \"Piparcity\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1536,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1536/\",\n" +
            "        \"name\": \"Pratapgarh(RJ)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1501,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1501/\",\n" +
            "        \"name\": \"Rajgarh (RJ)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 530,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/530/\",\n" +
            "        \"name\": \"rajnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 488,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/488/\",\n" +
            "        \"name\": \"rajsamand\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1608,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1608/\",\n" +
            "        \"name\": \"Rani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1252,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1252/\",\n" +
            "        \"name\": \"Ratangarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1250,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1250/\",\n" +
            "        \"name\": \"Rawat Bhata\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 930,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/930/\",\n" +
            "        \"name\": \"Sagwara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1240,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1240/\",\n" +
            "        \"name\": \"Sambhar Lake\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1528,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1528/\",\n" +
            "        \"name\": \"Sanchore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 595,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/595/\",\n" +
            "        \"name\": \"sawai madhopur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 707,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/707/\",\n" +
            "        \"name\": \"shahpura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1596,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1596/\",\n" +
            "        \"name\": \"Shergarh (RJ)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 412,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/412/\",\n" +
            "        \"name\": \"Sikar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 730,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/730/\",\n" +
            "        \"name\": \"sirohi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1561,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1561/\",\n" +
            "        \"name\": \"Sodawas\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1606,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1606/\",\n" +
            "        \"name\": \"Sojat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1254,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1254/\",\n" +
            "        \"name\": \"Sri Madhopur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 413,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/413/\",\n" +
            "        \"name\": \"Sriganganagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 949,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/949/\",\n" +
            "        \"name\": \"Sumerpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 630,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/630/\",\n" +
            "        \"name\": \"suratgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 615,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/615/\",\n" +
            "        \"name\": \"tonk\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 272,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/272/\",\n" +
            "        \"name\": \"Udaipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/29/\"\n" +
            "    }\n" +
            "]";
    private static String SIKKIM ="[\n" +
            "    {\n" +
            "        \"id\": 370,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/370/\",\n" +
            "        \"name\": \"East Sikkim\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/30/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 273,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/273/\",\n" +
            "        \"name\": \"Gangtok\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/30/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 274,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/274/\",\n" +
            "        \"name\": \"Gezing\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/30/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 275,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/275/\",\n" +
            "        \"name\": \"Jorethang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/30/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 675,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/675/\",\n" +
            "        \"name\": \"jortang\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/30/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 276,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/276/\",\n" +
            "        \"name\": \"mangan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/30/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 277,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/277/\",\n" +
            "        \"name\": \"Pelling\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/30/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 278,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/278/\",\n" +
            "        \"name\": \"Singtam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/30/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 279,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/279/\",\n" +
            "        \"name\": \"Yuksam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/30/\"\n" +
            "    }\n" +
            "]";
    private static String TAMILNADU ="[\n" +
            "    {\n" +
            "        \"id\": 280,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/280/\",\n" +
            "        \"name\": \"Chennai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 7,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1288,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1288/\",\n" +
            "        \"name\": \"Alagappapuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1269,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1269/\",\n" +
            "        \"name\": \"Ambasamudram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1502,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1502/\",\n" +
            "        \"name\": \"Annur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1287,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1287/\",\n" +
            "        \"name\": \"Arakkonam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1298,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1298/\",\n" +
            "        \"name\": \"Arasaradi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1292,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1292/\",\n" +
            "        \"name\": \"Arcot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1299,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1299/\",\n" +
            "        \"name\": \"Ariyalur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1290,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1290/\",\n" +
            "        \"name\": \"Arni\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1264,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1264/\",\n" +
            "        \"name\": \"Aruppukottai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1503,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1503/\",\n" +
            "        \"name\": \"Attur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1257,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1257/\",\n" +
            "        \"name\": \"Avadi Camp\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1279,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1279/\",\n" +
            "        \"name\": \"Avinashi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 939,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/939/\",\n" +
            "        \"name\": \"Bhavani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 845,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/845/\",\n" +
            "        \"name\": \"Chingleput\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 281,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/281/\",\n" +
            "        \"name\": \"Coimbatore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1284,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1284/\",\n" +
            "        \"name\": \"Coonoor\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 282,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/282/\",\n" +
            "        \"name\": \"Cuddalore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1289,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1289/\",\n" +
            "        \"name\": \"Devakottai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 956,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/956/\",\n" +
            "        \"name\": \"Dharapuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 283,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/283/\",\n" +
            "        \"name\": \"Dharmapuri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 284,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/284/\",\n" +
            "        \"name\": \"Dindigul\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1633,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1633/\",\n" +
            "        \"name\": \"Egmore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 285,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/285/\",\n" +
            "        \"name\": \"Erode\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 742,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/742/\",\n" +
            "        \"name\": \"gobichettipalayam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1617,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1617/\",\n" +
            "        \"name\": \"Gudalur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1504,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1504/\",\n" +
            "        \"name\": \"Harur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 480,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/480/\",\n" +
            "        \"name\": \"hosur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1282,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1282/\",\n" +
            "        \"name\": \"Kadamparai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 765,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/765/\",\n" +
            "        \"name\": \"kancheepuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 286,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/286/\",\n" +
            "        \"name\": \"Kanchipuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 287,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/287/\",\n" +
            "        \"name\": \"Kanyakumari\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 527,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/527/\",\n" +
            "        \"name\": \"karaikal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 788,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/788/\",\n" +
            "        \"name\": \"karaikudi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 288,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/288/\",\n" +
            "        \"name\": \"Karur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1505,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1505/\",\n" +
            "        \"name\": \"Katpadi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1637,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1637/\",\n" +
            "        \"name\": \"Kodaikanal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1285,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1285/\",\n" +
            "        \"name\": \"Kotagiri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1272,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1272/\",\n" +
            "        \"name\": \"Kovilpatti\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 289,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/289/\",\n" +
            "        \"name\": \"Krishnagiri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1274,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1274/\",\n" +
            "        \"name\": \"Kulittalai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 596,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/596/\",\n" +
            "        \"name\": \"kumbakonam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1280,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1280/\",\n" +
            "        \"name\": \"Lalgudi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 290,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/290/\",\n" +
            "        \"name\": \"Madurai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1286,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1286/\",\n" +
            "        \"name\": \"Manamadurai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1270,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1270/\",\n" +
            "        \"name\": \"Mannargudi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 943,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/943/\",\n" +
            "        \"name\": \"Marthandam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 910,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/910/\",\n" +
            "        \"name\": \"Mayiladuthurai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1268,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1268/\",\n" +
            "        \"name\": \"Melakaveri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1263,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1263/\",\n" +
            "        \"name\": \"Melur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 680,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/680/\",\n" +
            "        \"name\": \"mettupalayam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 896,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/896/\",\n" +
            "        \"name\": \"Muttom\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 291,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/291/\",\n" +
            "        \"name\": \"Nagapattinam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 414,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/414/\",\n" +
            "        \"name\": \"Nagarcoil\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 292,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/292/\",\n" +
            "        \"name\": \"Namakkal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 293,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/293/\",\n" +
            "        \"name\": \"Neyveli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1425,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1425/\",\n" +
            "        \"name\": \"Nilgiris\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 945,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/945/\",\n" +
            "        \"name\": \"Omalur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 559,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/559/\",\n" +
            "        \"name\": \"ooty\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1506,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1506/\",\n" +
            "        \"name\": \"P.N Palayam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1296,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1296/\",\n" +
            "        \"name\": \"Palani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 846,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/846/\",\n" +
            "        \"name\": \"Palayamkottai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 455,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/455/\",\n" +
            "        \"name\": \"Palayankottai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1271,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1271/\",\n" +
            "        \"name\": \"Papanasam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1293,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1293/\",\n" +
            "        \"name\": \"Paramakudi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1278,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1278/\",\n" +
            "        \"name\": \"Parli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 505,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/505/\",\n" +
            "        \"name\": \"pattukottai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 294,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/294/\",\n" +
            "        \"name\": \"Perambalur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1267,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1267/\",\n" +
            "        \"name\": \"Periyakulam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1642,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1642/\",\n" +
            "        \"name\": \"Perundurai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 545,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/545/\",\n" +
            "        \"name\": \"pollachi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1614,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1614/\",\n" +
            "        \"name\": \"Poonamallee\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 295,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/295/\",\n" +
            "        \"name\": \"Pudukkottai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 771,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/771/\",\n" +
            "        \"name\": \"rajapalayam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 296,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/296/\",\n" +
            "        \"name\": \"Ramanathapuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1291,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1291/\",\n" +
            "        \"name\": \"Ranipet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 627,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/627/\",\n" +
            "        \"name\": \"red hills\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 297,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/297/\",\n" +
            "        \"name\": \"Salem\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1273,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1273/\",\n" +
            "        \"name\": \"Sankarankoil\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1295,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1295/\",\n" +
            "        \"name\": \"Sholavandan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1275,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1275/\",\n" +
            "        \"name\": \"Shrivaikuntam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1266,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1266/\",\n" +
            "        \"name\": \"Sirkali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 298,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/298/\",\n" +
            "        \"name\": \"Sivaganga\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 415,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/415/\",\n" +
            "        \"name\": \"Sivakasi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1634,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1634/\",\n" +
            "        \"name\": \"Sriperumbudur Taluk\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1276,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1276/\",\n" +
            "        \"name\": \"Srirangam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1265,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1265/\",\n" +
            "        \"name\": \"Srivalliputtr\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 875,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/875/\",\n" +
            "        \"name\": \"Sulur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1297,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1297/\",\n" +
            "        \"name\": \"Tallakulam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1530,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1530/\",\n" +
            "        \"name\": \"Tenkasi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 299,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/299/\",\n" +
            "        \"name\": \"Thanjavur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 300,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/300/\",\n" +
            "        \"name\": \"The Nilgiris\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 301,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/301/\",\n" +
            "        \"name\": \"Theni\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 660,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/660/\",\n" +
            "        \"name\": \"thenkasi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1553,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1553/\",\n" +
            "        \"name\": \"Thiruttani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 812,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/812/\",\n" +
            "        \"name\": \"thiruvallur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 636,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/636/\",\n" +
            "        \"name\": \"thiruvarur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 302,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/302/\",\n" +
            "        \"name\": \"Thoothukudi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1277,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1277/\",\n" +
            "        \"name\": \"Thucklay\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1262,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1262/\",\n" +
            "        \"name\": \"Tindivanam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 864,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/864/\",\n" +
            "        \"name\": \"Tiruchengode\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 303,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/303/\",\n" +
            "        \"name\": \"Tiruchirappalli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1258,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1258/\",\n" +
            "        \"name\": \"Tirukkoyilur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 304,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/304/\",\n" +
            "        \"name\": \"Tirunelveli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1294,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1294/\",\n" +
            "        \"name\": \"Tirupattur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 416,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/416/\",\n" +
            "        \"name\": \"Tirupur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 305,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/305/\",\n" +
            "        \"name\": \"Tirurvarur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 306,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/306/\",\n" +
            "        \"name\": \"Tiruvallur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 307,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/307/\",\n" +
            "        \"name\": \"Tiruvannamalai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 592,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/592/\",\n" +
            "        \"name\": \"tiruvannamali\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 417,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/417/\",\n" +
            "        \"name\": \"Tuticorin\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1283,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1283/\",\n" +
            "        \"name\": \"Udagamandalam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1281,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1281/\",\n" +
            "        \"name\": \"Udamalpet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 560,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/560/\",\n" +
            "        \"name\": \"udumalpet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1261,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1261/\",\n" +
            "        \"name\": \"Ulundurpet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 471,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/471/\",\n" +
            "        \"name\": \"Uthangudi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1507,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1507/\",\n" +
            "        \"name\": \"Valliyur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 308,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/308/\",\n" +
            "        \"name\": \"Vellore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 780,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/780/\",\n" +
            "        \"name\": \"villupuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 309,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/309/\",\n" +
            "        \"name\": \"Viluppuram\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 310,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/310/\",\n" +
            "        \"name\": \"Virudhunagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1260,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1260/\",\n" +
            "        \"name\": \"Vriddachalam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1259,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1259/\",\n" +
            "        \"name\": \"Vridhachalam\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/31/\"\n" +
            "    }\n" +
            "]";
    private static String TRIURA ="[\n" +
            "    {\n" +
            "        \"id\": 311,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/311/\",\n" +
            "        \"name\": \"Agartala\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/32/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1300,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1300/\",\n" +
            "        \"name\": \"Dhalai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/32/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 504,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/504/\",\n" +
            "        \"name\": \"dhaleshwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/32/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 705,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/705/\",\n" +
            "        \"name\": \"dharmanagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/32/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 371,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/371/\",\n" +
            "        \"name\": \"Kunjvan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/32/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1301,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1301/\",\n" +
            "        \"name\": \"North Tripura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/32/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1304,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1304/\",\n" +
            "        \"name\": \"Radhakishorepur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/32/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1302,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1302/\",\n" +
            "        \"name\": \"South Tripura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/32/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1303,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1303/\",\n" +
            "        \"name\": \"West Tripura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/32/\"\n" +
            "    }\n" +
            "]";
    private static String UTTARAKHNAD ="[\n" +
            "    {\n" +
            "        \"id\": 322,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/322/\",\n" +
            "        \"name\": \"Almora\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 323,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/323/\",\n" +
            "        \"name\": \"Bageshwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1338,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1338/\",\n" +
            "        \"name\": \"Bijnore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 324,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/324/\",\n" +
            "        \"name\": \"Chamoli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 325,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/325/\",\n" +
            "        \"name\": \"Champawat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 326,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/326/\",\n" +
            "        \"name\": \"Dehradun\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1339,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1339/\",\n" +
            "        \"name\": \"Dhampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 603,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/603/\",\n" +
            "        \"name\": \"gopeshwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 376,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/376/\",\n" +
            "        \"name\": \"Haldwani\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 327,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/327/\",\n" +
            "        \"name\": \"Haridwar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 584,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/584/\",\n" +
            "        \"name\": \"Kashipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1626,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1626/\",\n" +
            "        \"name\": \"Khatima\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1343,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1343/\",\n" +
            "        \"name\": \"Kheri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 951,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/951/\",\n" +
            "        \"name\": \"Kotdwara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1337,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1337/\",\n" +
            "        \"name\": \"Lansdowne\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 328,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/328/\",\n" +
            "        \"name\": \"Nainital\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 329,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/329/\",\n" +
            "        \"name\": \"Pauri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 330,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/330/\",\n" +
            "        \"name\": \"Pithoragarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1650,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1650/\",\n" +
            "        \"name\": \"Ramnagar\",\n" +
            "        \"show_in_lead_form\": true,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1514,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1514/\",\n" +
            "        \"name\": \"Ramnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1344,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1344/\",\n" +
            "        \"name\": \"Ranikhet\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1512,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1512/\",\n" +
            "        \"name\": \"Rishikesh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 375,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/375/\",\n" +
            "        \"name\": \"Roorkee\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 331,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/331/\",\n" +
            "        \"name\": \"Rudraprayag\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 410,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/410/\",\n" +
            "        \"name\": \"Rudrapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1340,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1340/\",\n" +
            "        \"name\": \"Saharanpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1341,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1341/\",\n" +
            "        \"name\": \"Shamli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1618,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1618/\",\n" +
            "        \"name\": \"Sitarganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1513,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1513/\",\n" +
            "        \"name\": \"Srinagar-Garhwal\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 332,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/332/\",\n" +
            "        \"name\": \"Tehri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 333,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/333/\",\n" +
            "        \"name\": \"Udham Singh Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1542,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1542/\",\n" +
            "        \"name\": \"Vikasnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/33/\"\n" +
            "    }\n" +
            "]";
    private static String UTTER_PRADESH ="[\n" +
            "    {\n" +
            "        \"id\": 320,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/320/\",\n" +
            "        \"name\": \"Lucknow\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 9,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 312,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/312/\",\n" +
            "        \"name\": \"Agra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 662,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/662/\",\n" +
            "        \"name\": \"akbarpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 313,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/313/\",\n" +
            "        \"name\": \"Aligarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 314,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/314/\",\n" +
            "        \"name\": \"Allahabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1335,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1335/\",\n" +
            "        \"name\": \"Ambedkar Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1640,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1640/\",\n" +
            "        \"name\": \"Amethi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1324,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1324/\",\n" +
            "        \"name\": \"Amroha\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 687,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/687/\",\n" +
            "        \"name\": \"anpara\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1323,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1323/\",\n" +
            "        \"name\": \"Auraiya\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 315,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/315/\",\n" +
            "        \"name\": \"Ayodhya\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 418,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/418/\",\n" +
            "        \"name\": \"Azamgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1336,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1336/\",\n" +
            "        \"name\": \"Bagpat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 908,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/908/\",\n" +
            "        \"name\": \"Bahraich\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1509,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1509/\",\n" +
            "        \"name\": \"Bakshi Ka Talab\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 427,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/427/\",\n" +
            "        \"name\": \"Balia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 725,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/725/\",\n" +
            "        \"name\": \"ballia\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1312,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1312/\",\n" +
            "        \"name\": \"Balrampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 810,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/810/\",\n" +
            "        \"name\": \"banda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1313,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1313/\",\n" +
            "        \"name\": \"Bansi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 890,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/890/\",\n" +
            "        \"name\": \"Barabanki\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 686,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/686/\",\n" +
            "        \"name\": \"baraut\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 419,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/419/\",\n" +
            "        \"name\": \"Bareilly\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 428,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/428/\",\n" +
            "        \"name\": \"Basti\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 316,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/316/\",\n" +
            "        \"name\": \"Benares\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 866,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/866/\",\n" +
            "        \"name\": \"Bhadohi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 570,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/570/\",\n" +
            "        \"name\": \"bharich\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 432,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/432/\",\n" +
            "        \"name\": \"Bijnor\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 791,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/791/\",\n" +
            "        \"name\": \"budaun\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 604,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/604/\",\n" +
            "        \"name\": \"bulandshahr\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1319,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1319/\",\n" +
            "        \"name\": \"Bulandshar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1378,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1378/\",\n" +
            "        \"name\": \"Chandauli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1510,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1510/\",\n" +
            "        \"name\": \"Chandausi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1326,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1326/\",\n" +
            "        \"name\": \"Chitrakoot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1535,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1535/\",\n" +
            "        \"name\": \"Dadri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 317,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/317/\",\n" +
            "        \"name\": \"Deoband\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 435,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/435/\",\n" +
            "        \"name\": \"Deoria\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1305,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1305/\",\n" +
            "        \"name\": \"Etah\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 318,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/318/\",\n" +
            "        \"name\": \"Etawah\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 389,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/389/\",\n" +
            "        \"name\": \"Faizabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 865,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/865/\",\n" +
            "        \"name\": \"Farrukhabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1307,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1307/\",\n" +
            "        \"name\": \"Fatehgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 873,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/873/\",\n" +
            "        \"name\": \"Fatehpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1325,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1325/\",\n" +
            "        \"name\": \"Firozabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 934,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/934/\",\n" +
            "        \"name\": \"Gajraula\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1327,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1327/\",\n" +
            "        \"name\": \"Gautam Buddha Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 674,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/674/\",\n" +
            "        \"name\": \"gazipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 349,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/349/\",\n" +
            "        \"name\": \"Ghaziabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1315,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1315/\",\n" +
            "        \"name\": \"Ghazipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 629,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/629/\",\n" +
            "        \"name\": \"gonda\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 373,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/373/\",\n" +
            "        \"name\": \"Gorakhpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 420,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/420/\",\n" +
            "        \"name\": \"Greater Noida\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 957,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/957/\",\n" +
            "        \"name\": \"Hamirpur(UP)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 925,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/925/\",\n" +
            "        \"name\": \"Hapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 580,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/580/\",\n" +
            "        \"name\": \"Hardoi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 626,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/626/\",\n" +
            "        \"name\": \"hathras\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1382,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1382/\",\n" +
            "        \"name\": \"Jalaun\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 513,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/513/\",\n" +
            "        \"name\": \"jaunpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 374,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/374/\",\n" +
            "        \"name\": \"Jhansi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1328,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1328/\",\n" +
            "        \"name\": \"Jyotiba Phule Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 877,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/877/\",\n" +
            "        \"name\": \"Kannauj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 319,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/319/\",\n" +
            "        \"name\": \"Kanpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1329,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1329/\",\n" +
            "        \"name\": \"Kanpur Dehat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1330,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1330/\",\n" +
            "        \"name\": \"Kanpur Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 926,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/926/\",\n" +
            "        \"name\": \"Kasganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1420,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1420/\",\n" +
            "        \"name\": \"Kaushambi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1320,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1320/\",\n" +
            "        \"name\": \"Khurja\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1317,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1317/\",\n" +
            "        \"name\": \"Kunraghat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1331,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1331/\",\n" +
            "        \"name\": \"Kushinagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 796,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/796/\",\n" +
            "        \"name\": \"lakhimpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1311,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1311/\",\n" +
            "        \"name\": \"Lalganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 855,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/855/\",\n" +
            "        \"name\": \"Lalitpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1381,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1381/\",\n" +
            "        \"name\": \"Maharajganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1511,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1511/\",\n" +
            "        \"name\": \"Mahmudabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 829,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/829/\",\n" +
            "        \"name\": \"mahoba\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 608,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/608/\",\n" +
            "        \"name\": \"mainpuri\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 421,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/421/\",\n" +
            "        \"name\": \"Mathura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1332,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1332/\",\n" +
            "        \"name\": \"Mau\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1322,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1322/\",\n" +
            "        \"name\": \"Mau Nath Bhanjan\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 372,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/372/\",\n" +
            "        \"name\": \"Meerut\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 321,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/321/\",\n" +
            "        \"name\": \"Mirzapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 778,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/778/\",\n" +
            "        \"name\": \"modinagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 422,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/422/\",\n" +
            "        \"name\": \"Moradabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1309,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1309/\",\n" +
            "        \"name\": \"Muzaffar Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 423,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/423/\",\n" +
            "        \"name\": \"Muzaffarnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1508,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1508/\",\n" +
            "        \"name\": \"Naugarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1306,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1306/\",\n" +
            "        \"name\": \"Nawabganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 348,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/348/\",\n" +
            "        \"name\": \"Noida\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 698,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/698/\",\n" +
            "        \"name\": \"orai\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1318,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1318/\",\n" +
            "        \"name\": \"Padrauna\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1342,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1342/\",\n" +
            "        \"name\": \"Pilibhit\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 451,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/451/\",\n" +
            "        \"name\": \"Pratapgarh\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1310,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1310/\",\n" +
            "        \"name\": \"Rae Bareli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1377,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1377/\",\n" +
            "        \"name\": \"Raebareli\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 628,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/628/\",\n" +
            "        \"name\": \"raibareilly\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 766,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/766/\",\n" +
            "        \"name\": \"rampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1308,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1308/\",\n" +
            "        \"name\": \"Rasra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 929,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/929/\",\n" +
            "        \"name\": \"Rath\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 502,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/502/\",\n" +
            "        \"name\": \"renukoot\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 537,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/537/\",\n" +
            "        \"name\": \"robertsganj\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1321,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1321/\",\n" +
            "        \"name\": \"Sahahjahanpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 467,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/467/\",\n" +
            "        \"name\": \"Saharanpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 489,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/489/\",\n" +
            "        \"name\": \"sahibabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1645,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1645/\",\n" +
            "        \"name\": \"Sambhal\",\n" +
            "        \"show_in_lead_form\": true,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1316,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1316/\",\n" +
            "        \"name\": \"Sandila\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1333,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1333/\",\n" +
            "        \"name\": \"Sant Kabir Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1421,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1421/\",\n" +
            "        \"name\": \"Sant Ravidas Nagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 442,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/442/\",\n" +
            "        \"name\": \"Shahjahanpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 940,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/940/\",\n" +
            "        \"name\": \"Shikohabad\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1379,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1379/\",\n" +
            "        \"name\": \"Shravasti\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1380,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1380/\",\n" +
            "        \"name\": \"Siddharthnagar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 862,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/862/\",\n" +
            "        \"name\": \"Sitapur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1334,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1334/\",\n" +
            "        \"name\": \"Sonbhadra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 534,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/534/\",\n" +
            "        \"name\": \"sonebhadra\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 503,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/503/\",\n" +
            "        \"name\": \"sultanpur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 941,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/941/\",\n" +
            "        \"name\": \"Unchahar\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 670,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/670/\",\n" +
            "        \"name\": \"unnao\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 334,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/334/\",\n" +
            "        \"name\": \"Uttarkashi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 350,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/350/\",\n" +
            "        \"name\": \"Varanasi\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1314,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1314/\",\n" +
            "        \"name\": \"Zamania\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/34/\"\n" +
            "    }\n" +
            "]";
    private static String WEST_BENGAL ="[\n" +
            "    {\n" +
            "        \"id\": 338,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/338/\",\n" +
            "        \"name\": \"Kolkata\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 4,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1358,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1358/\",\n" +
            "        \"name\": \"24 Parganas\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1355,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1355/\",\n" +
            "        \"name\": \"24pgns(n)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1356,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1356/\",\n" +
            "        \"name\": \"24pgns(s)\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1345,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1345/\",\n" +
            "        \"name\": \"Alipore Rto\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 876,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/876/\",\n" +
            "        \"name\": \"Arambag\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 377,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/377/\",\n" +
            "        \"name\": \"Asansol\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 678,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/678/\",\n" +
            "        \"name\": \"bahrampur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1348,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1348/\",\n" +
            "        \"name\": \"Balurghat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 880,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/880/\",\n" +
            "        \"name\": \"Bangaon\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 776,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/776/\",\n" +
            "        \"name\": \"bankura\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 848,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/848/\",\n" +
            "        \"name\": \"Barasat\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 424,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/424/\",\n" +
            "        \"name\": \"Barddhman\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 500,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/500/\",\n" +
            "        \"name\": \"barrackpore\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1363,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/cities/1363/\",\n" +
            "        \"name\": \"Baruipur\",\n" +
            "        \"show_in_lead_form\": false,\n" +
            "        \"order_score\": 99,\n" +
            "        \"state\": \"http://www.collegedekho.com/api/1/states/35/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 909,\n" +
            "        \"name\": \"Basirhat\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1526,\n" +
            "        \"name\": \"Beldanga\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 533,\n" +
            "        \"name\": \"berhampore\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 843,\n" +
            "        \"name\": \"Birbhum\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1515,\n" +
            "        \"name\": \"Bishnupur (WB)\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 879,\n" +
            "        \"name\": \"Bolpur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 433,\n" +
            "        \"name\": \"Burdwan\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1620,\n" +
            "        \"name\": \"Chakdaha\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1516,\n" +
            "        \"name\": \"Chandrakona\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 893,\n" +
            "        \"name\": \"Chinsurah\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 696,\n" +
            "        \"name\": \"contai\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 477,\n" +
            "        \"name\": \"coochbehar\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 963,\n" +
            "        \"name\": \"Dalkhola\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1555,\n" +
            "        \"name\": \"Dankuni\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 335,\n" +
            "        \"name\": \"Darjeeling\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1357,\n" +
            "        \"name\": \"Darjiling\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1349,\n" +
            "        \"name\": \"Dhubulia\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1364,\n" +
            "        \"name\": \"Diamond Harbour\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 454,\n" +
            "        \"name\": \"Dinajpur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 336,\n" +
            "        \"name\": \"Durgapur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1517,\n" +
            "        \"name\": \"Ghatal\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 805,\n" +
            "        \"name\": \"haldia\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 440,\n" +
            "        \"name\": \"Hooghly\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 425,\n" +
            "        \"name\": \"Howrah\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1559,\n" +
            "        \"name\": \"Islampur (WB)\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 571,\n" +
            "        \"name\": \"jaigaon\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1353,\n" +
            "        \"name\": \"Jalpaiguri\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1518,\n" +
            "        \"name\": \"Kalyani\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1352,\n" +
            "        \"name\": \"Kandi\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1346,\n" +
            "        \"name\": \"Katwa\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 337,\n" +
            "        \"name\": \"Kharagpur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 501,\n" +
            "        \"name\": \"krishnanagar\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 844,\n" +
            "        \"name\": \"Madhyamgram\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1560,\n" +
            "        \"name\": \"Malbazar\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 378,\n" +
            "        \"name\": \"Malda\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1354,\n" +
            "        \"name\": \"Mall\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 478,\n" +
            "        \"name\": \"midnapore\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 459,\n" +
            "        \"name\": \"Murshidabad\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1351,\n" +
            "        \"name\": \"Nabadwip\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 448,\n" +
            "        \"name\": \"Nadia\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1519,\n" +
            "        \"name\": \"Naihati\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 582,\n" +
            "        \"name\": \"Panagarh\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1659,\n" +
            "        \"name\": \"Pundibari\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 853,\n" +
            "        \"name\": \"Purulia\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1359,\n" +
            "        \"name\": \"Puruliya\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1362,\n" +
            "        \"name\": \"Raghunathaganj\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 625,\n" +
            "        \"name\": \"raiganj\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1347,\n" +
            "        \"name\": \"Rampurhat\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1350,\n" +
            "        \"name\": \"Ranaghat\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1551,\n" +
            "        \"name\": \"Raniganj\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1550,\n" +
            "        \"name\": \"Rupnarainpur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1660,\n" +
            "        \"name\": \"Salt Lake City\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1624,\n" +
            "        \"name\": \"Santiniketan\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 795,\n" +
            "        \"name\": \"seori\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 339,\n" +
            "        \"name\": \"Siliguri\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1360,\n" +
            "        \"name\": \"South Dinajpur\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 847,\n" +
            "        \"name\": \"Tamluk\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1520,\n" +
            "        \"name\": \"Tarakeshwar\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 936,\n" +
            "        \"name\": \"Uluberia\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 444,\n" +
            "        \"name\": \"Uttar Dinajpur\"\n" +
            "    }\n" +
            "]";
    private static String TELANGANA ="[\n" +
            "    {\n" +
            "        \"id\": 2,\n" +
            "        \"name\": \"Adilabad\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1635,\n" +
            "        \"name\": \"Hyderabad-T\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9,\n" +
            "        \"name\": \"Karimnagar\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10,\n" +
            "        \"name\": \"Khammam\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 13,\n" +
            "        \"name\": \"Mahbubnagar\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 14,\n" +
            "        \"name\": \"Medak\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 979,\n" +
            "        \"name\": \"Miryalaguda\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 15,\n" +
            "        \"name\": \"Nalgonda\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1649,\n" +
            "        \"name\": \"Narketpally\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 17,\n" +
            "        \"name\": \"Nizamabad\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 490,\n" +
            "        \"name\": \"ramagundam\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1026,\n" +
            "        \"name\": \"Rangareddy\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1604,\n" +
            "        \"name\": \"Sathupalli\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 340,\n" +
            "        \"name\": \"Secunderabad\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1368,\n" +
            "        \"name\": \"Suryapet\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 23,\n" +
            "        \"name\": \"Warangal\"\n" +
            "    }\n" +
            "]";


    public static String DEGREE_LEVEL_1_JSON = "[\n" +
            "    {\n" +
            "        \"id\": 10531,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10531/\",\n" +
            "        \"name\": \"Auxiliary Nursing and Midwifery-A.N.M\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10498,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10498/\",\n" +
            "        \"name\": \"B. Com + MBA\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10458,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10458/\",\n" +
            "        \"name\": \"B.A.+LL.B\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10619,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10619/\",\n" +
            "        \"name\": \"B.B.A. + L.L.B.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10459,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10459/\",\n" +
            "        \"name\": \"B.Com+LL.B\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10652,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10652/\",\n" +
            "        \"name\": \"B.Com+MBA\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10510,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10510/\",\n" +
            "        \"name\": \"B.Com. + B.Ed.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10639,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10639/\",\n" +
            "        \"name\": \"B.Com. + M.Com.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10544,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10544/\",\n" +
            "        \"name\": \"B.Des + M.Des (Integrated)\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10636,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10636/\",\n" +
            "        \"name\": \"B.Des. + M.B.A.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10640,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10640/\",\n" +
            "        \"name\": \"B.Ed+M.Ed\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10449,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10449/\",\n" +
            "        \"name\": \"B.Ed.-B.Ed.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10536,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10536/\",\n" +
            "        \"name\": \"B.Pharm + M.Pharm\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10537,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10537/\",\n" +
            "        \"name\": \"B.Pharm + MBA\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10451,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10451/\",\n" +
            "        \"name\": \"B.Pharma\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10649,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10649/\",\n" +
            "        \"name\": \"B.S. + M.DES.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10647,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10647/\",\n" +
            "        \"name\": \"B.S. + M.TECH.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10650,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10650/\",\n" +
            "        \"name\": \"B.S. + MBA\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10610,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10610/\",\n" +
            "        \"name\": \"B.S.L. +  LL.B\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10630,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10630/\",\n" +
            "        \"name\": \"B.sc+ MBA\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10509,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10509/\",\n" +
            "        \"name\": \"B.Sc. + B.Ed\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10500,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10500/\",\n" +
            "        \"name\": \"B.Sc. + LL.B\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10550,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10550/\",\n" +
            "        \"name\": \"B.Tech + L.L.B\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10549,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10549/\",\n" +
            "        \"name\": \"B.Tech + L.L.M\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10541,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10541/\",\n" +
            "        \"name\": \"B.Tech + M.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10453,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10453/\",\n" +
            "        \"name\": \"B.Tech + M.Tech\",\n" +
            "        \"short_name\": \"B.Tech + M.Tech\",\n" +
            "        \"uri\": \"btech_mtech\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 30.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10588,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10588/\",\n" +
            "        \"name\": \"B.tech+M.tech\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10635,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10635/\",\n" +
            "        \"name\": \"B.Tech+MBA\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10648,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10648/\",\n" +
            "        \"name\": \"B.TECH. + M.DES.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10592,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10592/\",\n" +
            "        \"name\": \"B.Tech. + M.Sc.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10508,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10508/\",\n" +
            "        \"name\": \"BA + B.Ed\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10503,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10503/\",\n" +
            "        \"name\": \"Bachelor in Banking and Insurance-B.B.I.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10606,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10606/\",\n" +
            "        \"name\": \"Bachelor in Event Management-B.E.M\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10546,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10546/\",\n" +
            "        \"name\": \"Bachelor in Hospitality management\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10545,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10545/\",\n" +
            "        \"name\": \"Bachelor in Risk Management\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10519,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10519/\",\n" +
            "        \"name\": \"Bachelor of Applied Management-B.A.M\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1494,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/1494/\",\n" +
            "        \"name\": \"Bachelor of Architecture-B.Arch\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10483,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10483/\",\n" +
            "        \"name\": \"Bachelor of Arts in Journalism and Mass Communication-BAJM\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10567,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10567/\",\n" +
            "        \"name\": \"Bachelor of Arts+Bachelor of Education-B.A.+B.Ed.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10573,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10573/\",\n" +
            "        \"name\": \"Bachelor of Arts+Bachelor of Law - B.A.+L.L.B.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10614,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10614/\",\n" +
            "        \"name\": \"Bachelor of Arts+Master of Arts\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9567,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9567/\",\n" +
            "        \"name\": \"Bachelor of Arts-B.A.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10426,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10426/\",\n" +
            "        \"name\": \"Bachelor of Ayurvedic Medicine and Surgery-B.A.M.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10622,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10622/\",\n" +
            "        \"name\": \"Bachelor of Business Administration + Master of Management Studies (B.B.A. + M.M.S.)\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10556,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10556/\",\n" +
            "        \"name\": \"Bachelor of Business Administration+Hospitality and Events Management-B.B.A.+H.E.M.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9417,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9417/\",\n" +
            "        \"name\": \"Bachelor of Business Administration-B.B.A.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9420,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9420/\",\n" +
            "        \"name\": \"Bachelor of Business Management-B.B.M.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9469,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9469/\",\n" +
            "        \"name\": \"Bachelor of Business Studies-B.B.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10444,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10444/\",\n" +
            "        \"name\": \"Bachelor of Commerce-B.Com\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10626,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10626/\",\n" +
            "        \"name\": \"Bachelor of Computer Application + Master of Business Administration [B.C.A. + M.B.A.]\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10507,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10507/\",\n" +
            "        \"name\": \"Bachelor of Dental Surgery-B.D.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10530,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10530/\",\n" +
            "        \"name\": \"Bachelor of Dental Surgery-B.D.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10484,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10484/\",\n" +
            "        \"name\": \"Bachelor of Design-B.Des\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10574,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10574/\",\n" +
            "        \"name\": \"Bachelor of Elementary Education-B.El.Ed\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10423,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10423/\",\n" +
            "        \"name\": \"Bachelor of Engineering-B.E.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10481,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10481/\",\n" +
            "        \"name\": \"Bachelor of Fashion and Apparel Design-B.F.A.D.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10552,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10552/\",\n" +
            "        \"name\": \"Bachelor of Fashion Technology\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10615,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10615/\",\n" +
            "        \"name\": \"BACHELOR OF FILM AND TELEVISION TECHNOLOGY-B.F.T.T.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10631,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10631/\",\n" +
            "        \"name\": \"BACHELOR OF FINANCIAL MARKETING MANAGEMENT (BFMM)\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10580,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10580/\",\n" +
            "        \"name\": \"Bachelor of Financial Markets-B.F.M\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10418,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10418/\",\n" +
            "        \"name\": \"Bachelor of Fine Arts-B.F.A.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9664,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9664/\",\n" +
            "        \"name\": \"Bachelor of Fishery Science-B. F. Sc.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9468,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9468/\",\n" +
            "        \"name\": \"Bachelor of Foreign Trade-B.F.T.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10584,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10584/\",\n" +
            "        \"name\": \"Bachelor of Home Science- B.H.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10425,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10425/\",\n" +
            "        \"name\": \"Bachelor of Homoeopathic Medicine and Surgery-B.H.M.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10582,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10582/\",\n" +
            "        \"name\": \"Bachelor of Hospital Management\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10532,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10532/\",\n" +
            "        \"name\": \"Bachelor of Hospitality Science-B.H.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10491,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10491/\",\n" +
            "        \"name\": \"Bachelor of Hotel Management & Catering Technology-B.H.M.C.T.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10613,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10613/\",\n" +
            "        \"name\": \"Bachelor of Hotel Management and Catering Technology + Master of Business Administration-B.HM.C.T + M.B.A.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9366,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9366/\",\n" +
            "        \"name\": \"Bachelor of Hotel Management-B.H.M.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10591,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10591/\",\n" +
            "        \"name\": \"Bachelor of Human Resources Management\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9594,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9594/\",\n" +
            "        \"name\": \"Bachelor of Information & Management-B.I.M.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9557,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9557/\",\n" +
            "        \"name\": \"Bachelor of International Business Administration-B.I.B.A.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10598,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10598/\",\n" +
            "        \"name\": \"Bachelor of International Hospitality Administration\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10468,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10468/\",\n" +
            "        \"name\": \"Bachelor of Journalism and Mass Communication-B.J.M.C.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10612,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10612/\",\n" +
            "        \"name\": \"Bachelor of Law + Master of Law (L.L.B.+L.L.M.)\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10439,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10439/\",\n" +
            "        \"name\": \"Bachelor of Law-LLB\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10629,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10629/\",\n" +
            "        \"name\": \"BACHELOR OF LEGAL SCIENCE (B.L.S) + BACHELOR OF LAW (LL.B.)\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10593,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10593/\",\n" +
            "        \"name\": \"Bachelor of Legal Science + Bachelor of Law-B. L. S. + LL. B\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10539,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10539/\",\n" +
            "        \"name\": \"Bachelor of Library Sciences-B.Lib\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9424,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9424/\",\n" +
            "        \"name\": \"Bachelor of Management Studies-B.M.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10492,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10492/\",\n" +
            "        \"name\": \"Bachelor of Mass Communication-B.M.C.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9514,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9514/\",\n" +
            "        \"name\": \"Bachelor of Mass Media-B.M.M.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10628,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10628/\",\n" +
            "        \"name\": \"Bachelor of Media Science- B.M.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10560,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10560/\",\n" +
            "        \"name\": \"Bachelor of Medical Lab Technology-B.M.L.T.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 8480,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/8480/\",\n" +
            "        \"name\": \"Bachelor of Medicine and Bachelor of Surgery-M.B.B.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10568,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10568/\",\n" +
            "        \"name\": \"Bachelor of Multimedia communication-B.M.M.C\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10572,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10572/\",\n" +
            "        \"name\": \"Bachelor of multimedia-B.M.M.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10586,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10586/\",\n" +
            "        \"name\": \"Bachelor of Music-B.Mus.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10577,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10577/\",\n" +
            "        \"name\": \"Bachelor of Naturopathy and Yogic Sciences-B.N.Y.S\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10554,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10554/\",\n" +
            "        \"name\": \"Bachelor of Occupational Therapy\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10617,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10617/\",\n" +
            "        \"name\": \"Bachelor of Optometry\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10559,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10559/\",\n" +
            "        \"name\": \"Bachelor of Para Medical Technology- B.P.M.T.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10558,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10558/\",\n" +
            "        \"name\": \"Bachelor of Paramedical Technology\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9850,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9850/\",\n" +
            "        \"name\": \"Bachelor of Physical Education-B. P. E. D.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10427,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10427/\",\n" +
            "        \"name\": \"Bachelor of Physiotherapy-B.P.Th.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10479,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10479/\",\n" +
            "        \"name\": \"Bachelor of Planning-B.Plan.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10595,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10595/\",\n" +
            "        \"name\": \"Bachelor of Science+Master of Science-B.Sc+M.Sc\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10477,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10477/\",\n" +
            "        \"name\": \"Bachelor of Science-B.S\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9518,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/9518/\",\n" +
            "        \"name\": \"Bachelor of Science-B.Sc.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10621,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10621/\",\n" +
            "        \"name\": \"Bachelor of Siddha Medicine and Surgery - B.S.M.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10601,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10601/\",\n" +
            "        \"name\": \"Bachelor of Social Welfare- B.S.W.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10522,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10522/\",\n" +
            "        \"name\": \"Bachelor of Social Work-B.S.W.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2090,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/2090/\",\n" +
            "        \"name\": \"Bachelor of Technology-B.Tech\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10597,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10597/\",\n" +
            "        \"name\": \"Bachelor of Textile-B.Text.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10616,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10616/\",\n" +
            "        \"name\": \"Bachelor of Tourism & Travel Management-B.T.T.M.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10463,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10463/\",\n" +
            "        \"name\": \"Bachelor of Tourism Management-B.T.M.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10566,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10566/\",\n" +
            "        \"name\": \"Bachelor of Unani Medicine and Surgery-B.U.M.S.\",\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10505,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10505/\",\n" +
            "        \"name\": \"Bachelor of Veterinary Science-B.Vs.\",\n" +
            "        \"short_name\": \"B.Vs.\",\n" +
            "        \"uri\": \"bvs\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 20.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10533,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10533/\",\n" +
            "        \"name\": \"Bachelor of Visual Arts-B.V.A.\",\n" +
            "        \"short_name\": \"B.V.A.\",\n" +
            "        \"uri\": \"bachelor-of-visual-arts-bva\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 720.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10625,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10625/\",\n" +
            "        \"name\": \"Bachelor of Visual Communication-B.V.C.\",\n" +
            "        \"short_name\": \"B.V.C.\",\n" +
            "        \"uri\": \"bachelor-of-visual-communication-bvc\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 1600.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10565,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10565/\",\n" +
            "        \"name\": \"Bachelor of Vocational-B.Voc.\",\n" +
            "        \"short_name\": \"B.Voc\",\n" +
            "        \"uri\": \"bachelor-of-vocational-bvoc\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 1000.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10583,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10583/\",\n" +
            "        \"name\": \"Bachelor Preparatory Programme- B.P.P.\",\n" +
            "        \"short_name\": \"B.P.P.\",\n" +
            "        \"uri\": \"bachelor-preparatory-programme-bpp\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 2900.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10431,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10431/\",\n" +
            "        \"name\": \"Bachelors in Accounting and Finance-BAF\",\n" +
            "        \"short_name\": \"BAF\",\n" +
            "        \"uri\": \"baf\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 33100.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10578,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10578/\",\n" +
            "        \"name\": \"Bachelors of Homoeopathic Medical Science\",\n" +
            "        \"short_name\": \"B.H.O.M.\",\n" +
            "        \"uri\": \"bachelors-of-homoeopathic-medical-science\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 210.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10262,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10262/\",\n" +
            "        \"name\": \"BAM+MAM\",\n" +
            "        \"short_name\": \"BM/BAM + MAM\",\n" +
            "        \"uri\": \"bam_mam\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 10.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10511,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10511/\",\n" +
            "        \"name\": \"BBA + B.Ed.\",\n" +
            "        \"short_name\": \"B.B.A. + B.Ed.\",\n" +
            "        \"uri\": \"bba_bed\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10488,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10488/\",\n" +
            "        \"name\": \"BBA + BDBM\",\n" +
            "        \"short_name\": \"BBA + BDBM\",\n" +
            "        \"uri\": \"bba_bdbm\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10460,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10460/\",\n" +
            "        \"name\": \"BBA+LL.B\",\n" +
            "        \"short_name\": \"BBA (Hons.)+LL.B\",\n" +
            "        \"uri\": \"bba_llb\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10632,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10632/\",\n" +
            "        \"name\": \"BBA+LLB\",\n" +
            "        \"short_name\": \"BBA+LLB\",\n" +
            "        \"uri\": \"bballb\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 880.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10237,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10237/\",\n" +
            "        \"name\": \"BBA+MBA\",\n" +
            "        \"short_name\": \"BBA+MBA\",\n" +
            "        \"uri\": \"bba_mba\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 260.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10627,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10627/\",\n" +
            "        \"name\": \"BBA-MBA\",\n" +
            "        \"short_name\": \"BBA\",\n" +
            "        \"uri\": \"bba-mba\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 27100.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10247,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10247/\",\n" +
            "        \"name\": \"BBM+MBA\",\n" +
            "        \"short_name\": \"BBM+MBA\",\n" +
            "        \"uri\": \"bbm_mba\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 10.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10457,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10457/\",\n" +
            "        \"name\": \"BCA\",\n" +
            "        \"short_name\": \"BCA + MCA\",\n" +
            "        \"uri\": \"bca\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 170.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10490,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10490/\",\n" +
            "        \"name\": \"BCA + BDCM\",\n" +
            "        \"short_name\": \"BCA + BDCM\",\n" +
            "        \"uri\": \"bca_bdcm\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10521,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10521/\",\n" +
            "        \"name\": \"BCA + MCA (Dual Degree)\",\n" +
            "        \"short_name\": \"BCA + MCA\",\n" +
            "        \"uri\": \"bca_mca\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 170.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10637,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10637/\",\n" +
            "        \"name\": \"BCA+MBA\",\n" +
            "        \"short_name\": \"BCA+MBA\",\n" +
            "        \"uri\": \"bcamba\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 40.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10638,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10638/\",\n" +
            "        \"name\": \"BCA+MSC\",\n" +
            "        \"short_name\": \"BCA+MSC\",\n" +
            "        \"uri\": \"bcamsc\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10456,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10456/\",\n" +
            "        \"name\": \"BS+MS\",\n" +
            "        \"short_name\": \"BS+MS\",\n" +
            "        \"uri\": \"bs_ms\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 140.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10517,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10517/\",\n" +
            "        \"name\": \"BSW + LLB\",\n" +
            "        \"short_name\": \"BSW LLB\",\n" +
            "        \"uri\": \"bsw_llb\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 20.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10279,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10279/\",\n" +
            "        \"name\": \"Btech+MBA\",\n" +
            "        \"short_name\": \"B.Tech. + MBA\",\n" +
            "        \"uri\": \"btech_mba\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 90.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10430,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10430/\",\n" +
            "        \"name\": \"Charted Accountant-C.A.\",\n" +
            "        \"short_name\": \"C.A.\",\n" +
            "        \"uri\": \"ca\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 74000.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10561,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10561/\",\n" +
            "        \"name\": \"Doctor of Pharmacy-Pharm.D.\",\n" +
            "        \"short_name\": \"Pharm.D.\",\n" +
            "        \"uri\": \"doctor-of-pharmacy-pharmd\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 6600.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10624,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10624/\",\n" +
            "        \"name\": \"Female Multipurpose Health Worker\",\n" +
            "        \"short_name\": \"\",\n" +
            "        \"uri\": \"female-multipurpose-health-worker\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10506,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10506/\",\n" +
            "        \"name\": \"General Nursing & Midwifery-G.N.M.\",\n" +
            "        \"short_name\": \"G.N.M.\",\n" +
            "        \"uri\": \"gnm\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 12100.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10607,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10607/\",\n" +
            "        \"name\": \"Global Integrated Engineering Degree - B.S.+M.S.\",\n" +
            "        \"short_name\": \"B.S.+M.S.\",\n" +
            "        \"uri\": \"global-integrated-engineering-degree-bsms\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 140.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10654,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10654/\",\n" +
            "        \"name\": \"Graduate Diploma Program\",\n" +
            "        \"short_name\": \"diploma\",\n" +
            "        \"uri\": \"ug-diploma\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 22200.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10645,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10645/\",\n" +
            "        \"name\": \"LLB+MBA\",\n" +
            "        \"short_name\": \"LLB+MBA\",\n" +
            "        \"uri\": \"llbmba\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 50.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10623,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10623/\",\n" +
            "        \"name\": \"Male Multipurpose Health Worker\",\n" +
            "        \"short_name\": \"\",\n" +
            "        \"uri\": \"male-multipurpose-health-worker\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 0.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10602,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10602/\",\n" +
            "        \"name\": \"Revised Auxiliary Nurse and Midwife-R.A.N.M.\",\n" +
            "        \"short_name\": \"R.A.N.M.\",\n" +
            "        \"uri\": \"revised-auxiliary-nurse-and-midwife-ranm\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 210.0\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10603,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10603/\",\n" +
            "        \"name\": \"Revised General Nursing and Midwifery-R.G.N.M.\",\n" +
            "        \"short_name\": \"R.G.N.M.\",\n" +
            "        \"uri\": \"revised-general-nursing-and-midwifery-rgnm\",\n" +
            "        \"level\": 1,\n" +
            "        \"popular_score\": 140.0\n" +
            "    }\n" +
            "]";



    public static String DEGREE_JSON = "[\n" +
            "    {\n" +
            "        \"id\": 10531,\n" +
            "        \"name\": \"Auxiliary Nursing and Midwifery-A.N.M\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10498,\n" +
            "        \"name\": \"B. Com + MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10458,\n" +
            "        \"name\": \"B.A.+LL.B\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10619,\n" +
            "        \"name\": \"B.B.A. + L.L.B.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10459,\n" +
            "        \"name\": \"B.Com+LL.B\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10652,\n" +
            "        \"name\": \"B.Com+MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10510,\n" +
            "        \"name\": \"B.Com. + B.Ed.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10639,\n" +
            "        \"name\": \"B.Com. + M.Com.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10544,\n" +
            "        \"name\": \"B.Des + M.Des (Integrated)\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10636,\n" +
            "        \"name\": \"B.Des. + M.B.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10640,\n" +
            "        \"name\": \"B.Ed+M.Ed\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10449,\n" +
            "        \"name\": \"B.Ed.-B.Ed.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10536,\n" +
            "        \"name\": \"B.Pharm + M.Pharm\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10537,\n" +
            "        \"name\": \"B.Pharm + MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10451,\n" +
            "        \"name\": \"B.Pharma\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10649,\n" +
            "        \"name\": \"B.S. + M.DES.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10647,\n" +
            "        \"name\": \"B.S. + M.TECH.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10650,\n" +
            "        \"name\": \"B.S. + MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10610,\n" +
            "        \"name\": \"B.S.L. +  LL.B\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10630,\n" +
            "        \"name\": \"B.sc+ MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10509,\n" +
            "        \"name\": \"B.Sc. + B.Ed\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10500,\n" +
            "        \"name\": \"B.Sc. + LL.B\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10550,\n" +
            "        \"name\": \"B.Tech + L.L.B\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10549,\n" +
            "        \"name\": \"B.Tech + L.L.M\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10541,\n" +
            "        \"name\": \"B.Tech + M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10453,\n" +
            "        \"name\": \"B.Tech + M.Tech\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10588,\n" +
            "        \"name\": \"B.tech+M.tech\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10635,\n" +
            "        \"name\": \"B.Tech+MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10648,\n" +
            "        \"name\": \"B.TECH. + M.DES.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10592,\n" +
            "        \"name\": \"B.Tech. + M.Sc.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10508,\n" +
            "        \"name\": \"BA + B.Ed\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10503,\n" +
            "        \"name\": \"Bachelor in Banking and Insurance-B.B.I.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10606,\n" +
            "        \"name\": \"Bachelor in Event Management-B.E.M\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10546,\n" +
            "        \"name\": \"Bachelor in Hospitality management\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10545,\n" +
            "        \"name\": \"Bachelor in Risk Management\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10579,\n" +
            "        \"name\": \"Bachelor of Applied Management+Master of Applied Management-B.A.M.+M.A.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10519,\n" +
            "        \"name\": \"Bachelor of Applied Management-B.A.M\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1494,\n" +
            "        \"name\": \"Bachelor of Architecture-B.Arch\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10483,\n" +
            "        \"name\": \"Bachelor of Arts in Journalism and Mass Communication-BAJM\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10567,\n" +
            "        \"name\": \"Bachelor of Arts+Bachelor of Education-B.A.+B.Ed.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10573,\n" +
            "        \"name\": \"Bachelor of Arts+Bachelor of Law - B.A.+L.L.B.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10614,\n" +
            "        \"name\": \"Bachelor of Arts+Master of Arts\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9567,\n" +
            "        \"name\": \"Bachelor of Arts-B.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10426,\n" +
            "        \"name\": \"Bachelor of Ayurvedic Medicine and Surgery-B.A.M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10622,\n" +
            "        \"name\": \"Bachelor of Business Administration + Master of Management Studies (B.B.A. + M.M.S.)\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10556,\n" +
            "        \"name\": \"Bachelor of Business Administration+Hospitality and Events Management-B.B.A.+H.E.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9417,\n" +
            "        \"name\": \"Bachelor of Business Administration-B.B.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9420,\n" +
            "        \"name\": \"Bachelor of Business Management-B.B.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9469,\n" +
            "        \"name\": \"Bachelor of Business Studies-B.B.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10564,\n" +
            "        \"name\": \"Bachelor of Commerce+Master of Commerce-B.com+M.com\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10444,\n" +
            "        \"name\": \"Bachelor of Commerce-B.Com\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10626,\n" +
            "        \"name\": \"Bachelor of Computer Application + Master of Business Administration [B.C.A. + M.B.A.]\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10557,\n" +
            "        \"name\": \"Bachelor of Computer Application+Master of Computer Application-B.C.A.+M.C.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10507,\n" +
            "        \"name\": \"Bachelor of Dental Surgery-B.D.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10530,\n" +
            "        \"name\": \"Bachelor of Dental Surgery-B.D.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10484,\n" +
            "        \"name\": \"Bachelor of Design-B.Des\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10574,\n" +
            "        \"name\": \"Bachelor of Elementary Education-B.El.Ed\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10423,\n" +
            "        \"name\": \"Bachelor of Engineering-B.E.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10481,\n" +
            "        \"name\": \"Bachelor of Fashion and Apparel Design-B.F.A.D.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10552,\n" +
            "        \"name\": \"Bachelor of Fashion Technology\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10615,\n" +
            "        \"name\": \"BACHELOR OF FILM AND TELEVISION TECHNOLOGY-B.F.T.T.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10631,\n" +
            "        \"name\": \"BACHELOR OF FINANCIAL MARKETING MANAGEMENT (BFMM)\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10580,\n" +
            "        \"name\": \"Bachelor of Financial Markets-B.F.M\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10418,\n" +
            "        \"name\": \"Bachelor of Fine Arts-B.F.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9664,\n" +
            "        \"name\": \"Bachelor of Fishery Science-B. F. Sc.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9468,\n" +
            "        \"name\": \"Bachelor of Foreign Trade-B.F.T.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10584,\n" +
            "        \"name\": \"Bachelor of Home Science- B.H.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10425,\n" +
            "        \"name\": \"Bachelor of Homoeopathic Medicine and Surgery-B.H.M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10582,\n" +
            "        \"name\": \"Bachelor of Hospital Management\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10532,\n" +
            "        \"name\": \"Bachelor of Hospitality Science-B.H.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10491,\n" +
            "        \"name\": \"Bachelor of Hotel Management & Catering Technology-B.H.M.C.T.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10613,\n" +
            "        \"name\": \"Bachelor of Hotel Management and Catering Technology + Master of Business Administration-B.HM.C.T + M.B.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9366,\n" +
            "        \"name\": \"Bachelor of Hotel Management-B.H.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10591,\n" +
            "        \"name\": \"Bachelor of Human Resources Management\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9594,\n" +
            "        \"name\": \"Bachelor of Information & Management-B.I.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9557,\n" +
            "        \"name\": \"Bachelor of International Business Administration-B.I.B.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10598,\n" +
            "        \"name\": \"Bachelor of International Hospitality Administration\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10468,\n" +
            "        \"name\": \"Bachelor of Journalism and Mass Communication-B.J.M.C.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10612,\n" +
            "        \"name\": \"Bachelor of Law + Master of Law (L.L.B.+L.L.M.)\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10439,\n" +
            "        \"name\": \"Bachelor of Law-LLB\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10629,\n" +
            "        \"name\": \"BACHELOR OF LEGAL SCIENCE (B.L.S) + BACHELOR OF LAW (LL.B.)\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10593,\n" +
            "        \"name\": \"Bachelor of Legal Science + Bachelor of Law-B. L. S. + LL. B\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10539,\n" +
            "        \"name\": \"Bachelor of Library Sciences-B.Lib\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9424,\n" +
            "        \"name\": \"Bachelor of Management Studies-B.M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10553,\n" +
            "        \"name\": \"Bachelor of Management+Master of Applied Management\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10492,\n" +
            "        \"name\": \"Bachelor of Mass Communication-B.M.C.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9514,\n" +
            "        \"name\": \"Bachelor of Mass Media-B.M.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10628,\n" +
            "        \"name\": \"Bachelor of Media Science- B.M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10560,\n" +
            "        \"name\": \"Bachelor of Medical Lab Technology-B.M.L.T.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 8480,\n" +
            "        \"name\": \"Bachelor of Medicine and Bachelor of Surgery-M.B.B.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10568,\n" +
            "        \"name\": \"Bachelor of Multimedia communication-B.M.M.C\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10572,\n" +
            "        \"name\": \"Bachelor of multimedia-B.M.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10586,\n" +
            "        \"name\": \"Bachelor of Music-B.Mus.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10577,\n" +
            "        \"name\": \"Bachelor of Naturopathy and Yogic Sciences-B.N.Y.S\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10554,\n" +
            "        \"name\": \"Bachelor of Occupational Therapy\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10617,\n" +
            "        \"name\": \"Bachelor of Optometry\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10559,\n" +
            "        \"name\": \"Bachelor of Para Medical Technology- B.P.M.T.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10558,\n" +
            "        \"name\": \"Bachelor of Paramedical Technology\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9850,\n" +
            "        \"name\": \"Bachelor of Physical Education-B. P. E. D.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10427,\n" +
            "        \"name\": \"Bachelor of Physiotherapy-B.P.Th.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10479,\n" +
            "        \"name\": \"Bachelor of Planning-B.Plan.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10595,\n" +
            "        \"name\": \"Bachelor of Science+Master of Science-B.Sc+M.Sc\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10477,\n" +
            "        \"name\": \"Bachelor of Science-B.S\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9518,\n" +
            "        \"name\": \"Bachelor of Science-B.Sc.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10621,\n" +
            "        \"name\": \"Bachelor of Siddha Medicine and Surgery - B.S.M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10601,\n" +
            "        \"name\": \"Bachelor of Social Welfare- B.S.W.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10522,\n" +
            "        \"name\": \"Bachelor of Social Work-B.S.W.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2090,\n" +
            "        \"name\": \"Bachelor of Technology-B.Tech\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10597,\n" +
            "        \"name\": \"Bachelor of Textile-B.Text.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10616,\n" +
            "        \"name\": \"Bachelor of Tourism & Travel Management-B.T.T.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10463,\n" +
            "        \"name\": \"Bachelor of Tourism Management-B.T.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10566,\n" +
            "        \"name\": \"Bachelor of Unani Medicine and Surgery-B.U.M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10505,\n" +
            "        \"name\": \"Bachelor of Veterinary Science-B.Vs.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10533,\n" +
            "        \"name\": \"Bachelor of Visual Arts-B.V.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10625,\n" +
            "        \"name\": \"Bachelor of Visual Communication-B.V.C.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10565,\n" +
            "        \"name\": \"Bachelor of Vocational-B.Voc.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10583,\n" +
            "        \"name\": \"Bachelor Preparatory Programme- B.P.P.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10431,\n" +
            "        \"name\": \"Bachelors in Accounting and Finance-BAF\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10578,\n" +
            "        \"name\": \"Bachelors of Homoeopathic Medical Science\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10262,\n" +
            "        \"name\": \"BAM+MAM\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10562,\n" +
            "        \"name\": \"Basic Teacher Certificate-B.T.C.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10511,\n" +
            "        \"name\": \"BBA + B.Ed.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10488,\n" +
            "        \"name\": \"BBA + BDBM\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10460,\n" +
            "        \"name\": \"BBA+LL.B\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10632,\n" +
            "        \"name\": \"BBA+LLB\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10237,\n" +
            "        \"name\": \"BBA+MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10627,\n" +
            "        \"name\": \"BBA-MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10247,\n" +
            "        \"name\": \"BBM+MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10457,\n" +
            "        \"name\": \"BCA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10490,\n" +
            "        \"name\": \"BCA + BDCM\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10521,\n" +
            "        \"name\": \"BCA + MCA (Dual Degree)\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10637,\n" +
            "        \"name\": \"BCA+MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10638,\n" +
            "        \"name\": \"BCA+MSC\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10456,\n" +
            "        \"name\": \"BS+MS\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10517,\n" +
            "        \"name\": \"BSW + LLB\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10279,\n" +
            "        \"name\": \"Btech+MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9620,\n" +
            "        \"name\": \"Certificate\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10430,\n" +
            "        \"name\": \"Charted Accountant-C.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9419,\n" +
            "        \"name\": \"Diploma\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10600,\n" +
            "        \"name\": \"Diplomate of National Board-D.N.B.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10528,\n" +
            "        \"name\": \"Doctor of Medicine-M.D.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10561,\n" +
            "        \"name\": \"Doctor of Pharmacy-Pharm.D.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 3351,\n" +
            "        \"name\": \"Doctor of Philosophy-Ph.D\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10634,\n" +
            "        \"name\": \"Doctorate of Medicine\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10641,\n" +
            "        \"name\": \"Executive - MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10642,\n" +
            "        \"name\": \"Executive PGDM\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10624,\n" +
            "        \"name\": \"Female Multipurpose Health Worker\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10506,\n" +
            "        \"name\": \"General Nursing & Midwifery-G.N.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10607,\n" +
            "        \"name\": \"Global Integrated Engineering Degree - B.S.+M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10654,\n" +
            "        \"name\": \"Graduate Diploma Program\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10645,\n" +
            "        \"name\": \"LLB+MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10646,\n" +
            "        \"name\": \"LLM+MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10474,\n" +
            "        \"name\": \"M.Ed.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10538,\n" +
            "        \"name\": \"M.Pharm + MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10590,\n" +
            "        \"name\": \"M.Sc + Ph.D\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10518,\n" +
            "        \"resource_uri\": \"http://www.collegedekho.com/api/1/degrees/10518/\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10495,\n" +
            "        \"name\": \"M.Sc. + MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10547,\n" +
            "        \"name\": \"M.Sc. + PhD\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10599,\n" +
            "        \"name\": \"M.Text\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10623,\n" +
            "        \"name\": \"Male Multipurpose Health Worker\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10245,\n" +
            "        \"name\": \"Master in Applied Management-M.A.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10357,\n" +
            "        \"name\": \"Master in Business Studies-M.B.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10447,\n" +
            "        \"name\": \"Master in Design-M.Des\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10604,\n" +
            "        \"name\": \"Master in Fashion & Apparel Design-M.F.A.D.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10605,\n" +
            "        \"name\": \"Master in Fashion Retail Management-M.F.R.M\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10368,\n" +
            "        \"name\": \"Master In Human Resources Development Management-M.H.R.D.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9130,\n" +
            "        \"name\": \"Master in Physiotherapy-M.P.T.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10513,\n" +
            "        \"name\": \"Master in Social Work-M.S.W.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10437,\n" +
            "        \"name\": \"Master in Technology-M.Tech\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10512,\n" +
            "        \"name\": \"Master in Tourism Administration-M.T.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10520,\n" +
            "        \"name\": \"Master of Applied Management-M.A.M\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1504,\n" +
            "        \"name\": \"Master of Architecture-M.Arch\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10482,\n" +
            "        \"name\": \"Master of Arts in Journalism and Mass Communication-MAJM\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10421,\n" +
            "        \"name\": \"Master of Arts-M.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10575,\n" +
            "        \"name\": \"Master of Ayurvedic Medicine and Surgery-M.A.M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9588,\n" +
            "        \"name\": \"Master of Business Administration-M.B.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10589,\n" +
            "        \"name\": \"Master of Business Economics-M.B.E.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10576,\n" +
            "        \"name\": \"Master of Business Management-M.B.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10461,\n" +
            "        \"name\": \"Master of Chirurgiae-M.Ch.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10569,\n" +
            "        \"name\": \"Master of Commerce+Master of Business Administration-M.Com.+M.B.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 381,\n" +
            "        \"name\": \"Master of Commerce-M. Com\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10526,\n" +
            "        \"name\": \"Master of Computer Application-M.C.A\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10445,\n" +
            "        \"name\": \"Master of Computer Management-M.C.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 8332,\n" +
            "        \"name\": \"Master of Dental Surgery-M.D.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2650,\n" +
            "        \"name\": \"Master of Engineering-M.E.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10523,\n" +
            "        \"name\": \"Master of Fashion Management-M.F.M-Fashion\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10514,\n" +
            "        \"name\": \"Master of Finance & Control-M.F.C.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10570,\n" +
            "        \"name\": \"Master of Finance and Accounting+Master of Business Administration-M.F.A.+M.B.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10524,\n" +
            "        \"name\": \"Master of Finance and Accounting-M.F.Ac.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10416,\n" +
            "        \"name\": \"Master of Fine Arts-M.F.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10594,\n" +
            "        \"name\": \"Master of Fishery Science-M.F.Sc.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10581,\n" +
            "        \"name\": \"Master of Foreign Trade-M.F.T\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10472,\n" +
            "        \"name\": \"Master of Hospital Administration-MHA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10585,\n" +
            "        \"name\": \"Master of Hospitality Management-M.H.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10651,\n" +
            "        \"name\": \"Master of Information Management\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10571,\n" +
            "        \"name\": \"Master of International Business+Master of Business Administration-M.I.B.+M.B.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10611,\n" +
            "        \"name\": \"Master of Journalism & Mass Communication (MJMC) +  PGP in Media Management-M.J.M.C. + P.G.P.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10470,\n" +
            "        \"name\": \"Master of Journalism and Mass Communication-MJMC\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10643,\n" +
            "        \"name\": \"Master of Law + Ph.D\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10067,\n" +
            "        \"name\": \"Master of Laws-L.L.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10540,\n" +
            "        \"name\": \"Master of Library Sciences-M.Lib\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9989,\n" +
            "        \"name\": \"Master of Management Studies-M.M.S\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10473,\n" +
            "        \"name\": \"Master of Mass Communication-MMC\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10587,\n" +
            "        \"name\": \"Master of Music-M.Mus.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10555,\n" +
            "        \"name\": \"Master of Occupational Therapy-M.O.Th.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10618,\n" +
            "        \"name\": \"Master of Optometry-M.O.T.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10446,\n" +
            "        \"name\": \"Master of Personnel Management-MPM\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9007,\n" +
            "        \"name\": \"Master of Pharmacy-M.Pharma\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10417,\n" +
            "        \"name\": \"Master of Phillosophy-M. Phil.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10644,\n" +
            "        \"name\": \"Master of Philosophy + Ph.D\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10551,\n" +
            "        \"name\": \"Master of Physical Education\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10429,\n" +
            "        \"name\": \"Master of Physiotherapy-M.P.Th.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10525,\n" +
            "        \"name\": \"Master of Planning-M.Plan.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10108,\n" +
            "        \"name\": \"Master of Science - MS-M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10529,\n" +
            "        \"name\": \"Master of Surgery-M.S.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10608,\n" +
            "        \"name\": \"Master of Textile Management- M.T.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10542,\n" +
            "        \"name\": \"Master of Tourism Administration-M.T.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10487,\n" +
            "        \"name\": \"Master of Tourism Management-M.T.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10620,\n" +
            "        \"name\": \"Master of Veterinary Science-M.V.Sc.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10534,\n" +
            "        \"name\": \"Master of Visual Arts-M.V.A.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10633,\n" +
            "        \"name\": \"Master of Vocational - M.Voc\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10330,\n" +
            "        \"name\": \"Masters in financial management-M.F.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10462,\n" +
            "        \"name\": \"Masters in Hotel Management-M.H.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10543,\n" +
            "        \"name\": \"Masters in International Business-M.I.B\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10344,\n" +
            "        \"name\": \"Masters in Marketing Management-M.M.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10596,\n" +
            "        \"name\": \"Masters of Prosthetics and Orthotics - M.P.O.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9973,\n" +
            "        \"name\": \"Masters Programme in Sports Management-M.P.S.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10563,\n" +
            "        \"name\": \"MBA+PGDM\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10489,\n" +
            "        \"name\": \"MCA + PGDCM\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10516,\n" +
            "        \"name\": \"MTech + PhD\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 4428,\n" +
            "        \"name\": \"Other\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10112,\n" +
            "        \"name\": \"PGD+EMBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10198,\n" +
            "        \"name\": \"PGEXP\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10448,\n" +
            "        \"name\": \"PGRI-PGRI\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10609,\n" +
            "        \"name\": \"Post Doctoral of Medicine-D.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10527,\n" +
            "        \"name\": \"Post Graduate Diploma\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10653,\n" +
            "        \"name\": \"Post Graduate Diploma in Business Management - P.G.D.B.M\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9944,\n" +
            "        \"name\": \"Post Graduate Diploma-P.G.D.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10548,\n" +
            "        \"name\": \"Post Graduate Program in Management\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10493,\n" +
            "        \"name\": \"Post Graduate Programme in Business Analytics + MBA-PGPBA + MBA\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9629,\n" +
            "        \"name\": \"Professional diploma-PGP\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10602,\n" +
            "        \"name\": \"Revised Auxiliary Nurse and Midwife-R.A.N.M.\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10603,\n" +
            "        \"name\": \"Revised General Nursing and Midwifery-R.G.N.M.\"\n" +
            "    }\n" +
            "]";

    public static String SUB_LEVEL_JSON = "[\n" +
            "    {\n" +
            "        \"id\": 7,\n" +
            "        \"name\": \"10th\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9,\n" +
            "        \"name\": \"11th\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 8,\n" +
            "        \"name\": \"12th\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"College 1st Yr\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2,\n" +
            "        \"name\": \"College 2nd Yr\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 3,\n" +
            "        \"name\": \"College 3rd Yr\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 4,\n" +
            "        \"name\": \"College 4th Yr\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 13,\n" +
            "        \"name\": \"Certificate\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 14,\n" +
            "        \"name\": \"Diploma\"\n" +
            "    }\n" +
            "]";


    public static String PREF_LEVEL_JSON = "[\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"Under Graduation\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2,\n" +
            "        \"name\": \"Post Graduation\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 3,\n" +
            "        \"name\": \"Diploma\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 5,\n" +
            "        \"name\": \"Certification\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 4,\n" +
            "        \"name\": \"PHD\"\n" +
            "    }\n" +
            "]";

    public static String STREAMS_10TH = " [\n" +
            "                {\n" +
            "                    \"id\": 37,\n" +
            "                    \"name\": \"All Subjects\"\n" +
            "                }\n" +
            "            ]";

    public static String SCHOLL_STREAMS = "[\n" +
            "                {\n" +
            "                    \"id\": 16,\n" +
            "                    \"name\": \"Others\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 33,\n" +
            "                    \"name\": \"Maths\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 34,\n" +
            "                    \"name\": \"Biology\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 35,\n" +
            "                    \"name\": \"Commerce\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 36,\n" +
            "                    \"name\": \"Arts\"\n" +
            "                }\n" +
            "            ]";

    public static String UG_STREAMS ="[\n" +
            "                {\n" +
            "                    \"id\": 1,\n" +
            "                    \"name\": \"Finance / Commerce / Accounts / Banking\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 2,\n" +
            "                    \"name\": \"Designing\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 3,\n" +
            "                    \"name\": \"Engineering\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 4,\n" +
            "                    \"name\": \"Management \"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 5,\n" +
            "                    \"name\": \"Hospitality / Aviation / Tourism \"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 6,\n" +
            "                    \"name\": \"Information Technology\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 7,\n" +
            "                    \"name\": \"Media Films / Mass Communication\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 8,\n" +
            "                    \"name\": \"Beauty / Medicine / Health Care\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 9,\n" +
            "                    \"name\": \"Retail\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 12,\n" +
            "                    \"name\": \" Arts / Languages / Literature\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 13,\n" +
            "                    \"name\": \"Law / Politics / Humanities\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 14,\n" +
            "                    \"name\": \"Sciences\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 15,\n" +
            "                    \"name\": \"Professional / Vocational Courses\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 18,\n" +
            "                    \"name\": \"Physical Education\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 32,\n" +
            "                    \"name\": \"Animation / Multimedia\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 16,\n" +
            "                    \"name\": \"Others\"\n" +
            "                }\n" +
            "            ]";


    public static String getCitiJson(int position) {
        if(position == 1)
            return  ANDMAN_CITIES;
        else if (position == 2)
            return ANDRA_PRADESH;
        else if (position == 3)
            return ARUNACHHAL_PRADESH;
        else if (position == 4)
            return ASSAM;
        else if (position == 5)
            return BIHAR;
        else if (position == 6)
            return CHANDIGARH;
        else if (position == 7)
            return CHHATTISGARH;
        else if (position == 8)
            return DADRA_NAGAR;
        else if (position == 9)
            return DAMAN_DIU;
        else if (position == 10)
            return DELHI;
        else if (position == 11)
            return GOA;
        else if (position == 12)
            return GUJRAT;
        else if (position == 13)
            return HARYANA;
        else if (position == 14)
            return HIMACHAL_PRADESH;
        else if (position == 15)
            return JAMMU_KASHMIR;
        else if (position == 16)
            return JHARKHAND;
        else if (position == 17)
            return KARNATAKA;
        else if (position == 18)
            return KERLA;
        else if (position == 19)
            return LAKSHADWEEP;
        else if (position == 20)
            return MADHYA_PRADESH;
        else if (position == 21)
            return MAHARASHTRA;
        else if (position == 22)
            return MANIPUR;
        else if (position == 23)
            return MEGHALAYA;
        else if (position == 24)
            return MIZORAM;
        else if (position == 25)
            return NAGALAND;
        else if (position == 26)
            return ORISSA;
        else if (position == 27)
            return PONDICHERRY;
        else if (position == 28)
            return PUNJAB;
        else if (position == 29)
            return RAJASTHAN;
        else if (position == 30)
            return SIKKIM;
        else if (position == 31)
            return TAMILNADU;
        else if (position == 32)
            return TRIURA;
        else if (position == 33)
            return UTTARAKHNAD;
        else if (position == 34)
            return UTTER_PRADESH;
        else if (position == 35)
            return WEST_BENGAL;
        else if (position == 36)
            return TELANGANA;

        return ANDRA_PRADESH;

    }

    public static String getLoanRequiredAmount(int id){
        if(id == BELOW_ONE_LAKH)
            return "Below 1 Lakh";
        else if(id == ONE_TO_THREE_LAKH)
            return "1-3 Lakhs";
        else if(id == THREE_TO_FIVE_LAKH)
            return "3-5 Lakhs";
        else if(id == ABOVE_FIVE_LAKH)
            return "Above 5Lakhs";

        return "To be decided";

    }

    public static String getStreamJson(int id) {
        if (id == LEVEL_TWELFTH)
            return SCHOLL_STREAMS;
        else
            return UG_STREAMS;

    }


    public static String getCurrentScoreTypeName(int id){
        if(id == MARKS)
            return "Marks";
        else if(id == GRADES)
            return "Grades";
        else if(id == PERCENTAGE)
            return "Percentage";
        else if(id == RANK)
            return "Rank";

        return "Percentile";

    }

    public static String getExamStatusName(int id) {
        if(id == EXAM_GIVEN)
            return "Given";
        else
            return "Preparing";
    }

    public static String getMotherTongueName(int id) {
        if(id == MOTHER_TONGUE_ASSAMESE)
            return "Assamese";
        else if(id ==  MOTHER_TONGUE_BENGALI)
            return "Bengali";
        else if(id ==  MOTHER_TONGUE_BODO)
            return "Bodo";
        else if(id ==  MOTHER_TONGUE_DOGRI)
            return "Dogri";
        else if(id == MOTHER_TONGUE_GUJRATI)
            return "Gujarati";
        else if(id ==  MOTHER_TONGUE_HINDI)
            return "Hindi";
        else if(id ==  MOTHER_TONGUE_KANNADA)
            return "Kannada";
        else if(id ==  MOTHER_TONGUE_KASHMIRI)
            return "Kashmiri";
        else if(id ==  MOTHER_TONGUE_KONKANI)
            return "Konkani";
        else if(id == MOTHER_TONGUE_MAITHILI)
            return "Maithili";
        else if(id == MOTHER_TONGUE_MALAYALAM )
            return "Malayalam";
        else if(id ==  MOTHER_TONGUE_MANIPURI)
            return "Manipuri";
        else if(id ==  MOTHER_TONGUE_MARATHI)
            return "Marathi";
        else if(id ==  MOTHER_TONGUE_NEPALI)
            return "Nepali";
        else if(id == MOTHER_TONGUE_ODIA)
            return "Odia";
        else if(id ==  MOTHER_TONGUE_PUNJABI)
            return "Punjabi";
        else if(id ==  MOTHER_TONGUE_SANSKRIT)
            return "Sanskrit";
        else if(id == MOTHER_TONGUE_SANTALI)
            return "Santali";
        else if(id == MOTHER_TONGUE_SINDHI)
            return "Sindhi";
        else if(id == MOTHER_TONGUE_TAMIL)
            return "Tamil";
        else if(id ==  MOTHER_TONGUE_TELUGU)
            return "Telugu";
        else if(id ==  MOTHER_TONGUE_URDU)
            return "Urdu";
        else if(id == MOTHER_TONGUE_ENGLISH)
            return "English";
        return "Other";
    }
    public static String getSocialCategoryName(int id) {
        if(id == CATEGORY_GENERAL)
            return "General";
        else  if(id == CATEGORY_OBC)
            return "OBC";
        else  if(id == CATEGORY_SC)
            return "SC";
        else  if(id == CATEGORY_ST)
            return "ST";
        return "Other";
    }


    public static String getEducationModeName(int id) {
        if(id == MODE_FULL_TIME)
            return "Full Time";
        else  if(id == MODE_PART_TIME)
            return "Part Time";
        else  if(id == MODE_EXECUTIVE)
            return "Executive";
        else  if(id == MODE_DISTANCE)
            return "Distance-Learning";
        else  if(id == MODE_ONLINE)
            return "Online/e-Learning";
        return "Other";
    }

    public static String getYearName(int id) {
        if(id == YEAR_2013)
            return "Before 2013";
        else  if(id == YEAR_2014)
            return "2014";
        else  if(id == YEAR_2015)
            return "2015";
        else  if(id == YEAR_2016)
            return "2016";
        else  if(id == YEAR_2017)
            return "2017";
        else  if(id == YEAR_2018)
            return "2018";
        return "After 2018";
    }

    public static String getFeeRangeName(int id) {
        if(id == 1)
            return "0-1 lac";
        else if(id == 2)
            return "1-2 lacs";
        else if(id == 3)
            return "2-3 lacs";
        else if(id == 4)
            return "3-4 lacs";
        return "Above 4 lacs";
    }


    public static ArrayList<ProfileSpinnerItem> getMotherTongueList(){
        if(motherTongueList == null)
            motherTongueList = new ArrayList<>();

        if(motherTongueList.size() == 24)
            return  motherTongueList;

        for (int i = 1; i < 25; i++) {
            ProfileSpinnerItem item = new  ProfileSpinnerItem();
            item.setId(i);
            item.setName(getMotherTongueName(i));
            motherTongueList.add(item);

        }
        return  motherTongueList;
    }

    public static ArrayList<ProfileSpinnerItem> getSocialCategoryList(){
        if(socialCategoryList == null)
            socialCategoryList = new ArrayList<>();

        if(socialCategoryList.size() == 5)
            return socialCategoryList;

        for (int i = 1; i < 6; i++) {
            ProfileSpinnerItem item = new  ProfileSpinnerItem();
            item.setId(i);
            item.setName(getSocialCategoryName(i));
            socialCategoryList.add(item);
        }
        return socialCategoryList;
    }

    /**
     *
     * @return
     */

    public static ArrayList<ProfileSpinnerItem> getFeesRangeList(){
        if(feesRangeList == null)
            feesRangeList = new ArrayList<>();

        if(feesRangeList.size() == 5)
            return feesRangeList;

        for (int i = 1; i < 6; i++) {
            ProfileSpinnerItem item = new  ProfileSpinnerItem();
            item.setId(i);
            item.setName(getFeeRangeName(i));
            feesRangeList.add(item);
        }
        return feesRangeList;
    }


    public static ArrayList<ProfileSpinnerItem> getEducationModeList(){
        if(educationModeList == null)
            educationModeList = new ArrayList<>();

        if(educationModeList.size() == 6)
            return educationModeList;

        for (int i = 1; i < 7; i++) {
            if(i == 3)continue; // For 3 value,  we don't have any mode name.
            ProfileSpinnerItem item = new  ProfileSpinnerItem();
            item.setId(i);
            item.setName(getEducationModeName(i));
            educationModeList.add(item);
        }
        // add other in the last
        ProfileSpinnerItem item = new  ProfileSpinnerItem();
        item.setId(0);
        item.setName(getEducationModeName(0));
        educationModeList.add(item);
        return educationModeList;
    }

    public static ArrayList<ProfileSpinnerItem> getCurrentPassingYearList(){
        if(currentPassingYearList == null)
            currentPassingYearList = new ArrayList<>();

        if(currentPassingYearList.size() == 4)
            return currentPassingYearList;

        for (int i = 2013; i < 2017; i++) {
            ProfileSpinnerItem item = new  ProfileSpinnerItem();
            item.setId(i);
            item.setName(getYearName(i));
            currentPassingYearList.add(item);
        }
        return currentPassingYearList;
    }
    public static ArrayList<ProfileSpinnerItem> getPreferredAdmissionYearList(){
        if(preferredAddmisionYearList == null)
            preferredAddmisionYearList = new ArrayList<>();

        if(preferredAddmisionYearList.size() == 4)
            return preferredAddmisionYearList;

        for (int i = 2016; i < 2020; i++) {
            ProfileSpinnerItem item = new  ProfileSpinnerItem();
            item.setId(i);
            item.setName(getYearName(i));
            preferredAddmisionYearList.add(item);
        }
        return preferredAddmisionYearList;
    }

    public static int getSubLevel(int which, int level){

        if(level == ProfileMacro.LEVEL_TWELFTH){
            if(which == 0)
                return ProfileMacro.CURRENT_SUB_LEVEL_SCHOOL_10TH;
            else if(which == 1)
                return ProfileMacro.CURRENT_SUB_LEVEL_SCHOOL_11TH;
            else
                return ProfileMacro.CURRENT_SUB_LEVEL_SCHOOL_12TH;
        }else  if(level == ProfileMacro.LEVEL_UNDER_GRADUATE){
            if(which == 0)
                return ProfileMacro.CURRENT_SUB_LEVEL_COLLEGE_1;
            else if(which == 1)
                return ProfileMacro.CURRENT_SUB_LEVEL_COLLEGE_2;
            else if(which == 2)
                return ProfileMacro.CURRENT_SUB_LEVEL_COLLEGE_3;
            else
                return ProfileMacro.CURRENT_SUB_LEVEL_COLLEGE_4;
        }
        else {
            if (which == 0)
                return ProfileMacro.CURRENT_SUB_LEVEL_PG_1;
            else
                return ProfileMacro.CURRENT_SUB_LEVEL_PG_2;
        }

    }



   // language macros
    public static int MOTHER_TONGUE_ASSAMESE = 1;
    public static int MOTHER_TONGUE_BENGALI = 2;
    public static int MOTHER_TONGUE_BODO = 3;
    public static int MOTHER_TONGUE_DOGRI = 4;
    public static int MOTHER_TONGUE_GUJRATI = 5;
    public static int MOTHER_TONGUE_HINDI = 6;
    public static int MOTHER_TONGUE_MARATHI = 7;
    public static int MOTHER_TONGUE_NEPALI = 8;
    public static int MOTHER_TONGUE_ODIA = 9;
    public static int MOTHER_TONGUE_PUNJABI = 10;
    public static int MOTHER_TONGUE_SANSKRIT = 11;
    public static int MOTHER_TONGUE_SANTALI = 12;
    public static int MOTHER_TONGUE_SINDHI = 13;
    public static int MOTHER_TONGUE_TAMIL = 14;
    public static int MOTHER_TONGUE_TELUGU = 15;
    public static int MOTHER_TONGUE_URDU = 16;
    public static int MOTHER_TONGUE_OTHERS = 17;
    public static int MOTHER_TONGUE_ENGLISH = 18;
    public static int MOTHER_TONGUE_KASHMIRI = 19;
    public static int MOTHER_TONGUE_KONKANI = 20;
    public static int MOTHER_TONGUE_MAITHILI = 21;
    public static int MOTHER_TONGUE_MALAYALAM = 22;
    public static int MOTHER_TONGUE_MANIPURI = 23;
    public static int MOTHER_TONGUE_KANNADA = 24;


    // passing year
    public static int YEAR_2013 = 2013;
    public static int YEAR_2014 = 2014;
    public static int YEAR_2015 = 2015;
    public static int YEAR_2016 = 2016;
    public static int YEAR_2017 = 2017;
    public static int YEAR_2018 = 2018;


   //  social category
    public static int CATEGORY_GENERAL = 1;
    public static int CATEGORY_OBC = 2;
    public static int CATEGORY_SC = 3;
    public static int CATEGORY_ST = 4;
    public static int CATEGORY_OTHERS = 5;

   // score macros
    public static int MARKS = 1;
    public static int GRADES = 2;
    public static int PERCENTAGE = 3;
    public static int RANK = 4;
    public static int PERCENTILE = 5;

    // required loan Amount
    public static int BELOW_ONE_LAKH = 1;
    public static int ONE_TO_THREE_LAKH = 2;
    public static int THREE_TO_FIVE_LAKH = 3;
    public static int ABOVE_FIVE_LAKH = 4;
    public static int TO_BE_DECIDED = 5;

    // required loan Amount
    public static int MODE_FULL_TIME = 1;
    public static int MODE_PART_TIME = 2;
    public static int MODE_EXECUTIVE = 5;
    public static int MODE_DISTANCE = 4;
    public static int MODE_ONLINE = 6;
    public static int MODE_OTHER = 0;

    // sub level Ids
    public static int CURRENT_SUB_LEVEL_SCHOOL_10TH = 7;
    public static int CURRENT_SUB_LEVEL_SCHOOL_11TH = 9;
    public static int CURRENT_SUB_LEVEL_SCHOOL_12TH = 8;
    public static int CURRENT_SUB_LEVEL_COLLEGE_1 = 1;
    public static int CURRENT_SUB_LEVEL_COLLEGE_2 = 2;
    public static int CURRENT_SUB_LEVEL_COLLEGE_3 = 3;
    public static int CURRENT_SUB_LEVEL_COLLEGE_4 = 4;
    public static int CURRENT_SUB_LEVEL_PG_1 = 5;
    public static int CURRENT_SUB_LEVEL_PG_2 = 6;

    // set preferred level
    public static int LEVEL_UNDER_GRADUATE = 1;
    public static int LEVEL_POST_GRADUATE = 2;
    public static int LEVEL_DIPLOMA = 3;
    public static int LEVEL_PHD = 4;
    public static int LEVEL_CERTIFICATION = 5;
    public static int LEVEL_TENTH = 6;
    public static int LEVEL_TWELFTH= 7;


    // user is preparing for exams
    public static int PREPARING_FOR_EXAM  = 1;
    public static int NOT_PREPARING_FOR_EXAM = 0;

    // profile Map keys
    public static String IS_PREPARING = "is_preparing";

    // language macros
    public static int EXAM_GIVEN = 1;
    public static int EXAM_PREPARING = 2;

    // Request Macros
    public static String CURRENT_EDUCATION = "current_education";
    public static String PREFERRED_EDUCATION = "preferred_education";

    // verified
    public static int NUMBER_VERIFIED = 1;
    public static int EXAMS_SELECTED = 1;
    public static int ANONYMOUS_USER = 1;

    // exam type
    public static int STREAM_EXAM = 1;
    public static int OTHER_EXAM = 0;

    // gender
    public static int GENDER_NOT_PROVIDED = 0;
    public static int GENDER_MALE = 1;
    public static int GENDER_FEMALE = 2;

    // Sub Level String Arrays
    public static CharSequence[] SUB_LEVEL_SCHOOL = {"In 10th", "In 11th", "In 12th"};
    public static CharSequence[] SUB_LEVEL_COLLEGE = {"College 1st Year", "College 2nd Year", "College 3rd Year", "College 4th Year"};
    public static CharSequence[] SUB_LEVEL_PG = {"PG 1st year", "PG 2nd year"};

    private static ArrayList<ProfileSpinnerItem> motherTongueList ;
    private static ArrayList<ProfileSpinnerItem> socialCategoryList;
    private static ArrayList<ProfileSpinnerItem> feesRangeList;
    private static ArrayList<ProfileSpinnerItem> educationModeList;
    private static ArrayList<ProfileSpinnerItem> currentPassingYearList;
    private static ArrayList<ProfileSpinnerItem> preferredAddmisionYearList;

}
