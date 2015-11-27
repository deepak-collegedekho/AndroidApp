package com.collegedekho.app.utils;

import android.content.Context;

import com.collegedekho.app.entities.UserEducation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jeesoft.widget.pickerview.CharacterPickerView;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;
import cn.jeesoft.widget.pickerview.GaradiItem;

public class GaradiWindowHelper {

    private static List<GaradiItem> options1Items = null;
    private static List<List<GaradiItem>> options2Items = null;
    private static List<List<List<GaradiItem>>> options3Items = null;

    public static interface OnOptionsSelectListener {
        public void onOptionsSelect(String province, String city, String area);
    }

    private GaradiWindowHelper() {
    }

    public static CharacterPickerWindow builder(Context context, final OnOptionsSelectListener listener) {
        CharacterPickerWindow mOptions = new CharacterPickerWindow(context);
        setPickerData(mOptions.getPickerView(), null);
        mOptions.setSelectOptions(0, 0, 0);
        mOptions.setOnoptionsSelectListener(new CharacterPickerWindow.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if (listener != null) {
                    String province = (options1Items.get(options1)).getValue();
                    String city = (options2Items.get(options1).get(option2)).getValue();
                    String area = (options3Items.get(options1).get(option2).get(options3)).getValue();
                    listener.onOptionsSelect(province, city, area);
                }
            }
        });
        return mOptions;
    }

    public static void setPickerData(CharacterPickerView view, ArrayList<UserEducation> userEducationList) {
        if (options1Items == null) {
            options1Items = new ArrayList();
            options2Items = new ArrayList<>();
            options3Items = new ArrayList();

            final Map<GaradiItem, Map<GaradiItem, List<GaradiItem>>> allCitys = GaradiArrayData.getAllData(userEducationList);
            for (Map.Entry<GaradiItem, Map<GaradiItem, List<GaradiItem>>> entry1 : allCitys.entrySet()) {
                GaradiItem key1 = entry1.getKey();
                Map<GaradiItem, List<GaradiItem>> value1 = entry1.getValue();

                options1Items.add(key1);

                ArrayList options2Items_01 = new ArrayList();
                List<List<GaradiItem>> options3Items_01 = new ArrayList<>();
                for (Map.Entry<GaradiItem, List<GaradiItem>> entry2 : value1.entrySet()) {
                    GaradiItem key2 = entry2.getKey();
                    List<GaradiItem> value2 = entry2.getValue();

                    options2Items_01.add(key2);
                    options3Items_01.add(new ArrayList(value2));
                }
                options2Items.add(options2Items_01);
                options3Items.add(options3Items_01);
            }
        }

        view.setPicker(options1Items, options2Items, options3Items);
    }

}
