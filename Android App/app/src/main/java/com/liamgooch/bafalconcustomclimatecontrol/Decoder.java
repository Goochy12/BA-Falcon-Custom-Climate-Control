package com.liamgooch.bafalconcustomclimatecontrol;

import java.util.ArrayList;
import java.util.HashMap;

import static com.liamgooch.bafalconcustomclimatecontrol.HexCodes.*;

public class Decoder {

    //enum mappings for climate controls
    enum Mappings {
        AC, OPEN_CABIN, CLOSED_CABIN, FACE, FEET, FACE_FEET, FRONT_DEMIST, FEET_FRONT_DEMIST, REAR_DEMIST, FRONT_LEFT_DOOR, FRONT_RIGHT_DOOR, BACK_LEFT_DOOR, BACK_RIGHT_DOOR, BOOT
    }

    //MAPPINGS
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

    private ArrayList<Mappings> rearDemist = null;

    private ArrayList<Mappings> rearDemistFrontLeft = null;
    private ArrayList<Mappings> rearDemistFrontRight = null;
    private ArrayList<Mappings> rearDemistBackLeft = null;
    private ArrayList<Mappings> rearDemistBackRight = null;
    private ArrayList<Mappings> rearDemistBoot = null;

    private ArrayList<Mappings> rearDemistFrontLeftFrontRight = null;
    private ArrayList<Mappings> rearDemistFrontLeftBackLeft = null;
    private ArrayList<Mappings> rearDemistFrontLeftBackRight = null;
    private ArrayList<Mappings> rearDemistFrontLeftBoot = null;
    private ArrayList<Mappings> rearDemistFrontRightBackLeft = null;
    private ArrayList<Mappings> rearDemistFrontRightBackRight = null;
    private ArrayList<Mappings> rearDemistFrontRightBoot = null;
    private ArrayList<Mappings> rearDemistBackLeftBackRight = null;
    private ArrayList<Mappings> rearDemistBackLeftBoot = null;
    private ArrayList<Mappings> rearDemistBackRightBoot = null;

    private ArrayList<Mappings> rearDemistFrontLeftFrontRightBackLeft = null;
    private ArrayList<Mappings> rearDemistFrontLeftFrontRightBackRight = null;
    private ArrayList<Mappings> rearDemistFrontLeftFrontRightBoot = null;
    private ArrayList<Mappings> rearDemistFrontLeftBackLeftBackRight = null;
    private ArrayList<Mappings> rearDemistFrontLeftBackLeftBoot = null;
    private ArrayList<Mappings> rearDemistFrontLeftBackRightBoot = null;
    private ArrayList<Mappings> rearDemistFrontRightBackLeftBackRight = null;
    private ArrayList<Mappings> rearDemistFrontRightBackLeftBoot = null;
    private ArrayList<Mappings> rearDemistFrontRightBackRightBoot = null;
    private ArrayList<Mappings> rearDemistBackLeftBackRightBoot = null;

    private ArrayList<Mappings> rearDemistFrontLeftFrontRightBackLeftBackRight = null;
    private ArrayList<Mappings> rearDemistFrontLeftFrontRightBackLeftBoot = null;
    private ArrayList<Mappings> rearDemistFrontLeftFrontRightBackRightBoot = null;
    private ArrayList<Mappings> rearDemistFrontLeftBackLeftBackRightBoot = null;
    private ArrayList<Mappings> rearDemistFrontRightBackLeftBackRightBoot = null;

    private ArrayList<Mappings> rearDemistFrontLeftFrontRightBackLeftBackRightBoot = null;

    private HashMap<Integer, ArrayList<Mappings>> himHashMap = null;
    private HashMap<Integer, ArrayList<Mappings>> bemHashMap = null;


    /**
     * Constructor to add all mappings to each possible combination
     */
    public Decoder() {
        himHashMap = new HashMap<>();
        bemHashMap = new HashMap<>();

        acOpenFace = new ArrayList<>();
        acOpenFace.add(Mappings.AC);
        acOpenFace.add(Mappings.OPEN_CABIN);
        acOpenFace.add(Mappings.FACE);
        himHashMap.put(acOpenFaceHexCode, acOpenFace);

        acOpenFeet = new ArrayList<>();
        acOpenFeet.add(Mappings.AC);
        acOpenFeet.add(Mappings.OPEN_CABIN);
        acOpenFeet.add(Mappings.FEET);
        himHashMap.put(acOpenFeetHexCode, acOpenFeet);

        acOpenFaceFeet = new ArrayList<>();
        acOpenFaceFeet.add(Mappings.AC);
        acOpenFaceFeet.add(Mappings.OPEN_CABIN);
        acOpenFaceFeet.add(Mappings.FACE_FEET);
        himHashMap.put(acOpenFaceFeetHexCode, acOpenFaceFeet);

        acOpenFrontDemist = new ArrayList<>();
        acOpenFrontDemist.add(Mappings.AC);
        acOpenFrontDemist.add(Mappings.OPEN_CABIN);
        acOpenFrontDemist.add(Mappings.FRONT_DEMIST);
        himHashMap.put(acOpenFrontDemistHexCode, acOpenFrontDemist);

        acOpenFeetFrontDemist = new ArrayList<>();
        acOpenFeetFrontDemist.add(Mappings.AC);
        acOpenFeetFrontDemist.add(Mappings.OPEN_CABIN);
        acOpenFeetFrontDemist.add(Mappings.FEET_FRONT_DEMIST);
        himHashMap.put(acOpenFeetFrontDemistHexCode, acOpenFeetFrontDemist);

        acClosedFace = new ArrayList<>();
        acClosedFace.add(Mappings.AC);
        acClosedFace.add(Mappings.CLOSED_CABIN);
        acClosedFace.add(Mappings.FACE);
        himHashMap.put(acClosedFaceHexCode, acClosedFace);

        acClosedFeet = new ArrayList<>();
        acClosedFeet.add(Mappings.AC);
        acClosedFeet.add(Mappings.CLOSED_CABIN);
        acClosedFeet.add(Mappings.FEET);
        himHashMap.put(acClosedFeetHexCode, acClosedFeet);

        acClosedFaceFeet = new ArrayList<>();
        acClosedFaceFeet.add(Mappings.AC);
        acClosedFaceFeet.add(Mappings.CLOSED_CABIN);
        acClosedFaceFeet.add(Mappings.FACE_FEET);
        himHashMap.put(acClosedFaceFeetHexCode, acClosedFaceFeet);

        acClosedFrontDemist = new ArrayList<>();
        acClosedFrontDemist.add(Mappings.AC);
        acClosedFrontDemist.add(Mappings.CLOSED_CABIN);
        acClosedFrontDemist.add(Mappings.FRONT_DEMIST);
        himHashMap.put(acClosedFrontDemistHexCode, acClosedFrontDemist);

        acClosedFeetFrontDemist = new ArrayList<>();
        acClosedFeetFrontDemist.add(Mappings.AC);
        acClosedFeetFrontDemist.add(Mappings.CLOSED_CABIN);
        acClosedFeetFrontDemist.add(Mappings.FEET_FRONT_DEMIST);
        himHashMap.put(acClosedFeetFrontDemistHexCode, acClosedFeetFrontDemist);

        openFace = new ArrayList<>();
        openFace.add(Mappings.OPEN_CABIN);
        openFace.add(Mappings.FACE);
        himHashMap.put(openFaceHexCode, openFace);

        openFeet = new ArrayList<>();
        openFeet.add(Mappings.OPEN_CABIN);
        openFeet.add(Mappings.FEET);
        himHashMap.put(openFeetHexCode, openFeet);

        openFaceFeet = new ArrayList<>();
        openFaceFeet.add(Mappings.OPEN_CABIN);
        openFaceFeet.add(Mappings.FACE_FEET);
        himHashMap.put(openFaceFeetHexCode, openFaceFeet);

        openFrontDemist = new ArrayList<>();
        openFrontDemist.add(Mappings.OPEN_CABIN);
        openFrontDemist.add(Mappings.FRONT_DEMIST);
        himHashMap.put(openFrontDemistHexCode, openFrontDemist);

        openFeetFrontDemist = new ArrayList<>();
        openFeetFrontDemist.add(Mappings.OPEN_CABIN);
        openFeetFrontDemist.add(Mappings.FEET_FRONT_DEMIST);
        himHashMap.put(openFeetFrontDemistHexCode, openFeetFrontDemist);

        closedFace = new ArrayList<>();
        closedFace.add(Mappings.CLOSED_CABIN);
        closedFace.add(Mappings.FACE);
        himHashMap.put(closedFaceHexCode, closedFace);

        closedFeet = new ArrayList<>();
        closedFeet.add(Mappings.CLOSED_CABIN);
        closedFeet.add(Mappings.FEET);
        himHashMap.put(closedFeetHexCode, closedFeet);

        closedFaceFeet = new ArrayList<>();
        closedFaceFeet.add(Mappings.CLOSED_CABIN);
        closedFaceFeet.add(Mappings.FACE_FEET);
        himHashMap.put(closedFaceFeetHexCode, closedFaceFeet);

        closedFeetFrontDemist = new ArrayList<>();
        closedFeetFrontDemist.add(Mappings.CLOSED_CABIN);
        closedFeetFrontDemist.add(Mappings.FEET_FRONT_DEMIST);
        himHashMap.put(closedFeetFrontDemistHexCode, closedFeetFrontDemist);

//        BEM

        rearDemist = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        bemHashMap.put(rearDemistHexCode, rearDemist);

        rearDemistFrontLeft = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftHexCode, rearDemist);

        rearDemistFrontRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontRightHexCode, rearDemist);

        rearDemistBackLeft = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        bemHashMap.put(rearDemistBackLeftHexCode, rearDemist);

        rearDemistBackRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistBackRightHexCode, rearDemist);

        rearDemistBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistBootHexCode, rearDemist);

        rearDemistFrontLeftFrontRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontLeftFrontRightHexCode, rearDemist);

        rearDemistFrontLeftBackLeft = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontLeftBackLeftHexCode, rearDemist);

        rearDemistFrontLeftBackRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontLeftBackRightHexCode, rearDemist);

        rearDemistFrontLeftBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftBootHexCode, rearDemist);

        rearDemistFrontRightBackLeft = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontRightBackLeftHexCode, rearDemist);

        rearDemistFrontRightBackRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontRightBackRightHexCode, rearDemist);

        rearDemistFrontRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontRightBootHexCode, rearDemist);

        rearDemistBackLeftBackRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistBackLeftBackRightHexCode, rearDemist);

        rearDemistBackLeftBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistBackLeftBootHexCode, rearDemist);

        rearDemistBackRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistBackRightBootHexCode, rearDemist);

        rearDemistFrontLeftFrontRightBackLeft = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackLeftHexCode, rearDemist);

        rearDemistFrontLeftFrontRightBackRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackRightHexCode, rearDemist);

        rearDemistFrontLeftFrontRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftFrontRightBootHexCode, rearDemist);

        rearDemistFrontLeftBackLeftBackRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontLeftBackLeftBackRightHexCode, rearDemist);

        rearDemistFrontLeftBackLeftBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftBackLeftBootHexCode, rearDemist);

        rearDemistFrontLeftBackRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftBackRightBootHexCode, rearDemist);

        rearDemistFrontRightBackLeftBackRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontRightBackLeftBackRightHexCode, rearDemist);

        rearDemistFrontRightBackLeftBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontRightBackLeftBootHexCode, rearDemist);

        rearDemistFrontRightBackRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontRightBackRightBootHexCode, rearDemist);

        rearDemistBackLeftBackRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistBackLeftBackRightBootHexCode, rearDemist);

        rearDemistFrontLeftFrontRightBackLeftBackRight = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackLeftBackRightHexCode, rearDemist);

        rearDemistFrontLeftFrontRightBackLeftBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackLeftBootHexCode, rearDemist);

        rearDemistFrontLeftFrontRightBackRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackRightBootHexCode, rearDemist);

        rearDemistFrontLeftBackLeftBackRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftBackLeftBackRightBootHexCode, rearDemist);

        rearDemistFrontRightBackLeftBackRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontRightBackLeftBackRightBootHexCode, rearDemist);

        rearDemistFrontLeftFrontRightBackLeftBackRightBoot = new ArrayList<>();
        rearDemist.add(Mappings.REAR_DEMIST);
        rearDemist.add(Mappings.FRONT_LEFT_DOOR);
        rearDemist.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemist.add(Mappings.BACK_LEFT_DOOR);
        rearDemist.add(Mappings.BACK_RIGHT_DOOR);
        rearDemist.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackLeftBackRightBootHexCode, rearDemist);
    }

    /**
     * Method to get the HIM ID
     *
     * @return - the HIM ID
     */
    public int getHimID() {
        return himID;
    }

    /**
     * Method to get the BEM ID
     *
     * @return - the BEM ID
     */
    public int getBemID() {
        return bemID;
    }

    /**
     * Method to get a decoded HIM list based on an inputted code
     *
     * @param code - the HIM code from the vehicle
     * @return - a list of enums detailing which climate settings are currently active
     */
    public ArrayList<Mappings> getHimDecodedList(int code) {
        return himHashMap.get(code);
    }

    /**
     * Method to get a decoded BEM list based on an inputted code
     *
     * @param code - the BEM code from the vehicle
     * @return - a list of enums detailing which climate settings are currently active
     */
    public ArrayList<Mappings> getBemDecodedList(int code) {
        return bemHashMap.get(code);
    }
}
