package com.yusesc.myapplication;

import android.app.Application;

import java.util.HashMap;

public class IdolHash extends Application {
    private HashMap<String, Integer> hm = new HashMap<>();

    public void setVal(){
        if(hm.size()==0) {
            hm.put("Jijel", R.drawable.aespa_jijel);
            hm.put("Karina", R.drawable.aespa_karina);
            hm.put("Mingming", R.drawable.aespa_ningning);
            hm.put("Winter", R.drawable.aespa_winter);
            hm.put("Jenny",R.drawable.blackpink_jenny);
            hm.put("Jisu",R.drawable.blackpink_jisu);
            hm.put("Risa",R.drawable.blackpink_risa);
            hm.put("Rose",R.drawable.blackpink_rose);
            hm.put("Jeongguk",R.drawable.bts_jeongguk);
            hm.put("Jhop",R.drawable.bts_jhop);
            hm.put("Jimin",R.drawable.bts_jimin);
            hm.put("Jin",R.drawable.bts_jin);
            hm.put("RM",R.drawable.bts_rm);
            hm.put("Sugar",R.drawable.bts_suga);
            hm.put("V",R.drawable.bts_v);
            hm.put("Sinbl",R.drawable.girlfriend_sinbi);
            hm.put("Sowon",R.drawable.girlfriend_sowon);
            hm.put("Umji",R.drawable.girlfriend_umji);
            hm.put("eunha",R.drawable.girlfriend_eunha);
            hm.put("Yerin",R.drawable.girlfriend_yerin);
            hm.put("Yuju",R.drawable.girlfriend_yuju);
            hm.put("Jackson",R.drawable.got7_jackson);
            hm.put("JB",R.drawable.got7_jayb);
            hm.put("JingYoung",R.drawable.got7_jinyoung);
            hm.put("Yougyeom",R.drawable.got7_yougyeom);
            hm.put("YoungJae",R.drawable.got7_youngjae);
            hm.put("Bambam",R.drawable.got7_bambam);
            hm.put("Mark",R.drawable.got7_mark);
            hm.put("ARin",R.drawable.ohmygirl_arin);
            hm.put("Bini",R.drawable.ohmygirl_binnie);
            hm.put("Hyojung",R.drawable.ohmygirl_hyojung);
            hm.put("Jiho",R.drawable.ohmygirl_jiho);
            hm.put("Mini",R.drawable.ohmygirl_mimi);
            hm.put("Seunghi",R.drawable.ohmygirl_seunghi);
            hm.put("Yua",R.drawable.ohmygirl_yua);
            hm.put("Irin",R.drawable.redvelvet_irin);
            hm.put("Joy",R.drawable.redvelvet_joy);
            hm.put("Seulgi",R.drawable.redvelvet_seulgi);
            hm.put("Wendy",R.drawable.redvelvet_wendy);
            hm.put("Yeri",R.drawable.redvelvet_yeri);
            hm.put("Chaeyoung",R.drawable.twice_chaeyoung);
            hm.put("Dahyeon",R.drawable.twice_dahyeon);
            hm.put("Jungyeon",R.drawable.twice_jungyeon);
            hm.put("Jihyo",R.drawable.twice_jihyo);
            hm.put("Mina",R.drawable.twice_mina);
            hm.put("Momo",R.drawable.twice_momo);
            hm.put("Nayeon",R.drawable.twice_nayeon);
            hm.put("Sana",R.drawable.twice_sana);
            hm.put("Tzuyu",R.drawable.twice_tzuyu);
            hm.put("Baejingyoung",R.drawable.wannaone_baejinyoung);
            hm.put("Gangdaniel",R.drawable.wannaone_gangdaniel);
            hm.put("Haseonghoon",R.drawable.wannaone_haseonghoon);
            hm.put("Hwangminhyeon",R.drawable.wannaone_hwangminhyeon);
            hm.put("Kimjaehwan",R.drawable.wannaone_kimjaehwan);
            hm.put("Leedaehwi",R.drawable.wannaone_leedaehwi);
            hm.put("Laiguanlin",R.drawable.wannaone_laiguanlin);
            hm.put("Ongseongwoo",R.drawable.wannaone_ongseongwoo);
            hm.put("Parkjihoon",R.drawable.wannaone_parkjihoon);
            hm.put("Parkwoojin",R.drawable.wannaone_parkwoojin);
            hm.put("Yoonjisung",R.drawable.wannaone_yoonjisung);
        }
    }

    public int getVal(String key){
        return hm.get(key);
    }
}
