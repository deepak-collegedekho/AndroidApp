package cn.jeesoft.widget.pickerview;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 0.1 king 2015-11
 */
final class MWheelOptions {
    private CharacterPickerView view;
    private LoopView wv_option1;
    private LoopView wv_option2;
    private LoopView wv_option3;

    private List<GaradiItem> mOptions1Items;
    private List<List<GaradiItem>> mOptions2Items;
    private List<List<List<GaradiItem>>> mOptions3Items;
    private CharacterPickerView.OnOptionChangedListener mOnOptionChangedListener;

    public View getView() {
        return view;
    }

    public MWheelOptions(CharacterPickerView view) {
        super();
        this.view = view;
    }

    public void setOnOptionChangedListener(
            CharacterPickerView.OnOptionChangedListener listener) {
        this.mOnOptionChangedListener = listener;
    }

    public void setPicker(ArrayList<GaradiItem> optionsItems) {
        setPicker(optionsItems, null, null);
    }

    public void setPicker(List<GaradiItem> options1Items,
                          List<List<GaradiItem>> options2Items) {
        setPicker(options1Items, options2Items, null);
    }

    public void setPicker(List<GaradiItem> options1Items,
                          List<List<GaradiItem>> options2Items,
                          List<List<List<GaradiItem>>> options3Items) {
        this.mOptions1Items = (options1Items == null ? new ArrayList<GaradiItem>() : options1Items);
        this.mOptions2Items = (options2Items == null ? new ArrayList<List<GaradiItem>>() : options2Items);
        this.mOptions3Items = (options3Items == null ? new ArrayList<List<List<GaradiItem>>>() : options3Items);

        wv_option1 = (LoopView) view.findViewById(R.id.j_options1);
        wv_option1.setArrayList(mOptions1Items);
        wv_option1.setCurrentItem(0);
        wv_option1.setNotLoop();

        wv_option1.setListener(new LoopListener() {
            @Override
            public void onItemSelect(int item) {
                if (!mOptions2Items.isEmpty()) {
                    wv_option2.setArrayList(mOptions2Items.get(item));
                    wv_option2.setCurrentItem(0);
                }
                if (!mOptions3Items.isEmpty()) {
                    wv_option3.setArrayList(mOptions3Items.get(item).get(0));
                    wv_option3.setCurrentItem(0);
                } else {
                    doItemChange();
                }
            }
        });

        wv_option2 = (LoopView) view.findViewById(R.id.j_options2);
        if (!mOptions2Items.isEmpty()) {
            wv_option2.setArrayList(mOptions2Items.get(0));
            wv_option2.setCurrentItem(0);
            wv_option2.setNotLoop();
            wv_option2.setListener(new LoopListener() {
                @Override
                public void onItemSelect(int item) {
                    if (!mOptions3Items.isEmpty()) {
                        wv_option3.setArrayList(mOptions3Items.get(wv_option1.getCurrentItem()).get(item));
                        wv_option3.setCurrentItem(0);
                    } else {
                        doItemChange();
                    }
                }
            });
        }

        wv_option3 = (LoopView) view.findViewById(R.id.j_options3);
        if (mOptions3Items != null && !mOptions3Items.isEmpty()) {
            if(mOptions3Items.get(0) != null && !mOptions3Items.get(0).isEmpty()) {
                wv_option3.setArrayList(mOptions3Items.get(0).get(0));
                wv_option3.setCurrentItem(0);
                wv_option3.setNotLoop();
                wv_option3.setListener(new LoopListener() {
                    @Override
                    public void onItemSelect(int item) {
                        doItemChange();
                    }
                });
            }
        }

        if (mOptions2Items.isEmpty())
            view.findViewById(R.id.j_layout2).setVisibility(View.GONE);
        if (mOptions3Items.isEmpty())
            view.findViewById(R.id.j_layout3).setVisibility(View.GONE);

        setCurrentItems(0, 0, 0);
    }

    private void doItemChange() {
        if (mOnOptionChangedListener != null) {
            int option1 = wv_option1.getCurrentItem();
            int option2 = wv_option2.getCurrentItem();
            int option3 = wv_option3.getCurrentItem();

            String levelID = (mOptions1Items.get(option1)).getLevelID();
            String subLevelID =(mOptions1Items.get(option1)).getId();
            String streamID = (mOptions2Items.get(option1)).get(option2).getId();
            int marks = Integer.parseInt((mOptions3Items.get(option1)).get(option2).get(option3).getId());
            marks = (((marks - 1) * 10) + 5);//get median of range, for eg. 1-10 return 5.

            mOnOptionChangedListener.onOptionChanged(view, levelID, subLevelID, streamID, String.valueOf(marks));
        }
    }


    public void setCyclic(boolean cyclic) {
        wv_option1.setCyclic(cyclic);
        wv_option2.setCyclic(cyclic);
        wv_option3.setCyclic(cyclic);
    }


    public int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = wv_option1.getCurrentItem();
        currentItems[1] = wv_option2.getCurrentItem();
        currentItems[2] = wv_option3.getCurrentItem();
        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        wv_option1.setCurrentItem(option1);
        wv_option2.setCurrentItem(option2);
        wv_option3.setCurrentItem(option3);
    }
}
