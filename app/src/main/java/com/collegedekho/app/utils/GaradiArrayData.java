package com.collegedekho.app.utils;

import com.collegedekho.app.entities.UserEducation;
import com.collegedekho.app.entities.UserEducationStreams;
import com.collegedekho.app.entities.UserEducationSublevels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.jeesoft.widget.pickerview.GaradiItem;

public class GaradiArrayData {

    private static final LinkedHashMap<GaradiItem, LinkedHashMap<GaradiItem, List<GaradiItem>>> DATAs = new LinkedHashMap<>();
    private static final LinkedHashMap<String, List<GaradiItem>> SUBLEVELS = new LinkedHashMap<>();
    private static List<GaradiItem> Marks = new ArrayList<>();

    private static void init(ArrayList<UserEducation> userEducationList) {
        if (!DATAs.isEmpty()) {
            return;
        }

        for (int n = 4; n <= 10; n++)
        {
            int from = 1 + (( n - 1 ) * 10);
            int to = n * 10;
            Marks.add(new GaradiItem(String.valueOf(from) + "-" + String.valueOf(to) + "%", String.valueOf(n)));
        }

        for (int i = 0; i < userEducationList.size(); i++) {
            LinkedHashMap<GaradiItem, List<GaradiItem>> streamAndMarks = new LinkedHashMap<>();
            ArrayList<GaradiItem> subLevelList = new ArrayList<>();
            UserEducation userEducation = userEducationList.get(i);
            ArrayList<UserEducationStreams> streamList = userEducation.getStreams();
            ArrayList<UserEducationSublevels> sublevelsList = userEducation.getSublevels();

            if(streamList.size() == 0)
                continue;

            for (int j = 0; j < streamList.size(); j++) {
                UserEducationStreams streams = streamList.get(j);

                streamAndMarks.put(new GaradiItem(streams.getName(), String.valueOf(streams.getId())), Marks);
            }
            for (int k = 0; k < sublevelsList.size(); k++) {
                UserEducationSublevels sublevel = sublevelsList.get(k);

                subLevelList.add(new GaradiItem(sublevel.getName(), String.valueOf(sublevel.getId())));
                DATAs.put(new GaradiItem(sublevel.getName(), String.valueOf(sublevel.getId()), String.valueOf(userEducation.getValue())), streamAndMarks);
            }
            SUBLEVELS.put(String.valueOf(userEducation.getValue()), subLevelList);
        }
    }

    public static LinkedHashMap<GaradiItem, LinkedHashMap<GaradiItem, List<GaradiItem>>> getAllData(ArrayList<UserEducation> userEducationList) {
        init(userEducationList);
        return new LinkedHashMap<>(DATAs);
    }

    public static List<GaradiItem> getSublevels(String item) {
        return SUBLEVELS.get(item);
    }
}
