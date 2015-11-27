package com.collegedekho.app.utils;

import com.collegedekho.app.entities.UserEducation;
import com.collegedekho.app.entities.UserEducationStreams;
import com.collegedekho.app.entities.UserEducationSublevels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.jeesoft.widget.pickerview.GaradiItem;

public class GaradiArrayData {

    private static final Map<GaradiItem, Map<GaradiItem, List<GaradiItem>>> DATAs = new LinkedHashMap<>();
    private static final Map<String, List<GaradiItem>> SUBLEVELS = new LinkedHashMap<>();
    private static List<GaradiItem> Marks = new ArrayList<>();

    private static void init(ArrayList<UserEducation> userEducationList) {
        if (!DATAs.isEmpty()) {
            return;
        }

        for (int n = 1; n <= 10; n++)
        {
            int from = 1 + (( n - 1 ) * 10);
            int to = n * 10;
            Marks.add(new GaradiItem(String.valueOf(from) + "-" + String.valueOf(to) + "%", String.valueOf(n)));
        }

        for (int i = 0; i < userEducationList.size(); i++) {
            Map<GaradiItem, List<GaradiItem>> streamAndMarks = new HashMap<>();
            ArrayList<GaradiItem> sublevels = new ArrayList<>();
            UserEducation userEducation = userEducationList.get(i);
            ArrayList<UserEducationStreams> streamList = userEducation.getStreams();
            ArrayList<UserEducationSublevels> sublevelsList = userEducation.getSublevels();
            for (int j = 0; j < streamList.size(); j++) {
                UserEducationStreams streams = streamList.get(j);

                streamAndMarks.put(new GaradiItem(streams.getName(), String.valueOf(streams.getId())), Marks);
            }
            for (int k = 0; k < sublevelsList.size(); k++) {
                UserEducationSublevels sublevel = sublevelsList.get(k);

                sublevels.add(new GaradiItem(sublevel.getName(), String.valueOf(sublevel.getId())));
            }
            DATAs.put(new GaradiItem(userEducation.getName(), String.valueOf(userEducation.getValue())), streamAndMarks);
            SUBLEVELS.put(String.valueOf(userEducation.getValue()), sublevels);
        }
    }

    public static Map<GaradiItem, Map<GaradiItem, List<GaradiItem>>> getAllData(ArrayList<UserEducation> userEducationList) {
        init(userEducationList);
        return new HashMap<>(DATAs);
    }

    public static List<GaradiItem> getSublevels(String item) {
        return SUBLEVELS.get(item);
    }
}
