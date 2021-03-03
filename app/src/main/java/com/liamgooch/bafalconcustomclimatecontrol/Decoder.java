package com.liamgooch.bafalconcustomclimatecontrol;

import java.util.ArrayList;
import java.util.HashMap;

public class Decoder {



    enum Mappings {
        AC, OPEN_CABIN, CLOSED_CABIN, FACE, FEET, FACE_FEET, FRONT_DEMIST, FEET_FRONT_DEMIST
    }

    //MAPPINGS
// private String acOpenFace = 0xB2;
// private String acOpenFeet = 0xAA;
// private String acOpenFaceFeet = 0xBA;
// private String acOpenFrontDemist = 0xA6;
// private String acOpenFeetFrontDemist = 0xAE;
// private String acClosedFace = 0xD2;
// private String acClosedFeet = 0xCA;
// private String acClosedFaceFeet = 0xDA;
// private String acClosedFrontDemist = 0xC6;
// private String acClosedFeetFront = 0xCE;
// private String openFace = 0x32;
// private String openFeet = 0x2A;
// private String openFaceFeet = 0x3A;
// private String openFrontDemist = 0x26;
// private String openFeetFrontDemist = 0x2E;
// private String closedFace = 0x52;
// private String closedFeet = 0x4A;
// private String closedFaceFeet = 0x5A;
// private String closedFeetFrontDemist = 0x4E;
    private ArrayList<Mappings> acOpenFace = null;
    private ArrayList<Mappings> acOpenFeet = null;
    private ArrayList<Mappings> acOpenFaceFeet = null;
    private ArrayList<Mappings> acOpenFrontDemist = null;
    private ArrayList<Mappings> acOpenFeetFrontDemist = null;
    private ArrayList<Mappings> acClosedFace = null;
    private ArrayList<Mappings> acClosedFeet = null;
    private ArrayList<Mappings> acClosedFaceFeet = null;
    private ArrayList<Mappings> acClosedFrontDemist = null;
    private ArrayList<Mappings> acClosedFeetFrontDemist = null;
    private ArrayList<Mappings> openFace = null;
    private ArrayList<Mappings> openFeet = null;
    private ArrayList<Mappings> openFaceFeet = null;
    private ArrayList<Mappings> openFrontDemist = null;
    private ArrayList<Mappings> openFeetFrontDemist = null;
    private ArrayList<Mappings> closedFace = null;
    private ArrayList<Mappings> closedFeet = null;
    private ArrayList<Mappings> closedFaceFeet = null;
    private ArrayList<Mappings> closedFeetFrontDemist = null;
    private String himID = "353";

    private String bemID = "403";
    private HashMap<String, ArrayList<Mappings>> hashMap = null;


    public Decoder() {
        hashMap = new HashMap<>();

        acOpenFace = new ArrayList<>();
        acOpenFace.add(Mappings.AC);
        acOpenFace.add(Mappings.OPEN_CABIN);
        acOpenFace.add(Mappings.FACE);
        hashMap.put("b2", acOpenFace);

        acOpenFeet = new ArrayList<>();
        acOpenFeet.add(Mappings.AC);
        acOpenFeet.add(Mappings.OPEN_CABIN);
        acOpenFeet.add(Mappings.FEET);
        hashMap.put("aa", acOpenFeet);

        acOpenFaceFeet = new ArrayList<>();
        acOpenFaceFeet.add(Mappings.AC);
        acOpenFaceFeet.add(Mappings.OPEN_CABIN);
        acOpenFaceFeet.add(Mappings.FACE_FEET);
        hashMap.put("ba", acOpenFaceFeet);

        acOpenFrontDemist = new ArrayList<>();
        acOpenFrontDemist.add(Mappings.AC);
        acOpenFrontDemist.add(Mappings.OPEN_CABIN);
        acOpenFrontDemist.add(Mappings.FRONT_DEMIST);
        hashMap.put("a6", acOpenFrontDemist);

        acOpenFeetFrontDemist = new ArrayList<>();
        acOpenFeetFrontDemist.add(Mappings.AC);
        acOpenFeetFrontDemist.add(Mappings.OPEN_CABIN);
        acOpenFeetFrontDemist.add(Mappings.FEET_FRONT_DEMIST);
        hashMap.put("ae", acOpenFeetFrontDemist);

        acClosedFace = new ArrayList<>();
        acClosedFace.add(Mappings.AC);
        acClosedFace.add(Mappings.CLOSED_CABIN);
        acClosedFace.add(Mappings.FACE);
        hashMap.put("d2", acClosedFace);

        acClosedFeet = new ArrayList<>();
        acClosedFeet.add(Mappings.AC);
        acClosedFeet.add(Mappings.CLOSED_CABIN);
        acClosedFeet.add(Mappings.FEET);
        hashMap.put("ca", acClosedFeet);

        acClosedFaceFeet = new ArrayList<>();
        acClosedFaceFeet.add(Mappings.AC);
        acClosedFaceFeet.add(Mappings.CLOSED_CABIN);
        acClosedFaceFeet.add(Mappings.FACE_FEET);
        hashMap.put("da", acClosedFaceFeet);

        acClosedFrontDemist = new ArrayList<>();
        acClosedFrontDemist.add(Mappings.AC);
        acClosedFrontDemist.add(Mappings.CLOSED_CABIN);
        acClosedFrontDemist.add(Mappings.FRONT_DEMIST);
        hashMap.put("c6", acClosedFrontDemist);

        acClosedFeetFrontDemist = new ArrayList<>();
        acClosedFeetFrontDemist.add(Mappings.AC);
        acClosedFeetFrontDemist.add(Mappings.CLOSED_CABIN);
        acClosedFeetFrontDemist.add(Mappings.FEET_FRONT_DEMIST);
        hashMap.put("ce", acClosedFeetFrontDemist);

        openFace = new ArrayList<>();
        openFace.add(Mappings.OPEN_CABIN);
        openFace.add(Mappings.FACE);
        hashMap.put("32", openFace);

        openFeet = new ArrayList<>();
        openFeet.add(Mappings.OPEN_CABIN);
        openFeet.add(Mappings.FEET);
        hashMap.put("2a", openFeet);

        openFaceFeet = new ArrayList<>();
        openFaceFeet.add(Mappings.OPEN_CABIN);
        openFaceFeet.add(Mappings.FACE_FEET);
        hashMap.put("3a", openFaceFeet);

        openFrontDemist = new ArrayList<>();
        openFrontDemist.add(Mappings.OPEN_CABIN);
        openFrontDemist.add(Mappings.FRONT_DEMIST);
        hashMap.put("26", openFrontDemist);

        openFeetFrontDemist = new ArrayList<>();
        openFeetFrontDemist.add(Mappings.OPEN_CABIN);
        openFeetFrontDemist.add(Mappings.FEET_FRONT_DEMIST);
        hashMap.put("2e", openFeetFrontDemist);

        closedFace = new ArrayList<>();
        closedFace.add(Mappings.CLOSED_CABIN);
        closedFace.add(Mappings.FACE);
        hashMap.put("52", closedFace);

        closedFeet = new ArrayList<>();
        closedFeet.add(Mappings.CLOSED_CABIN);
        closedFeet.add(Mappings.FEET);
        hashMap.put("4a", closedFeet);

        closedFaceFeet = new ArrayList<>();
        closedFaceFeet.add(Mappings.CLOSED_CABIN);
        closedFaceFeet.add(Mappings.FACE_FEET);
        hashMap.put("5a", closedFaceFeet);

        closedFeetFrontDemist = new ArrayList<>();
        closedFeetFrontDemist.add(Mappings.CLOSED_CABIN);
        closedFeetFrontDemist.add(Mappings.FEET_FRONT_DEMIST);
        hashMap.put("4e", closedFeetFrontDemist);
    }

    public String getHimID() {
        return himID;
    }

    public String getBemID() {
        return bemID;
    }

    public ArrayList<Mappings> getDecodedList(String code) {
        return hashMap.get(code);
    }
}
