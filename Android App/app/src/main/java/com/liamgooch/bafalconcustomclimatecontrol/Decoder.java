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
        rearDemistFrontLeft.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeft.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftHexCode, rearDemistFrontLeft);

        rearDemistFrontRight = new ArrayList<>();
        rearDemistFrontRight.add(Mappings.REAR_DEMIST);
        rearDemistFrontRight.add(Mappings.FRONT_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontRightHexCode, rearDemistFrontRight);

        rearDemistBackLeft = new ArrayList<>();
        rearDemistBackLeft.add(Mappings.REAR_DEMIST);
        rearDemistBackLeft.add(Mappings.FRONT_RIGHT_DOOR);
        bemHashMap.put(rearDemistBackLeftHexCode, rearDemistBackLeft);

        rearDemistBackRight = new ArrayList<>();
        rearDemistBackRight.add(Mappings.REAR_DEMIST);
        rearDemistBackRight.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistBackRightHexCode, rearDemistBackRight);

        rearDemistBoot = new ArrayList<>();
        rearDemistBoot.add(Mappings.REAR_DEMIST);
        rearDemistBoot.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistBootHexCode, rearDemistBoot);

        rearDemistFrontLeftFrontRight = new ArrayList<>();
        rearDemistFrontLeftFrontRight.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftFrontRight.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftFrontRight.add(Mappings.FRONT_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontLeftFrontRightHexCode, rearDemistFrontLeftFrontRight);

        rearDemistFrontLeftBackLeft = new ArrayList<>();
        rearDemistFrontLeftBackLeft.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftBackLeft.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftBackLeft.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontLeftBackLeftHexCode, rearDemistFrontLeftBackLeft);

        rearDemistFrontLeftBackRight = new ArrayList<>();
        rearDemistFrontLeftBackRight.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftBackRight.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftBackRight.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontLeftBackRightHexCode, rearDemistFrontLeftBackRight);

        rearDemistFrontLeftBoot = new ArrayList<>();
        rearDemistFrontLeftBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftBoot.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftBootHexCode, rearDemistFrontLeftBoot);

        rearDemistFrontRightBackLeft = new ArrayList<>();
        rearDemistFrontRightBackLeft.add(Mappings.REAR_DEMIST);
        rearDemistFrontRightBackLeft.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontRightBackLeft.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontRightBackLeftHexCode, rearDemistFrontRightBackLeft);

        rearDemistFrontRightBackRight = new ArrayList<>();
        rearDemistFrontRightBackRight.add(Mappings.REAR_DEMIST);
        rearDemistFrontRightBackRight.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontRightBackRight.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontRightBackRightHexCode, rearDemistFrontRightBackRight);

        rearDemistFrontRightBoot = new ArrayList<>();
        rearDemistFrontRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontRightBoot.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontRightBootHexCode, rearDemistFrontRightBoot);

        rearDemistBackLeftBackRight = new ArrayList<>();
        rearDemistBackLeftBackRight.add(Mappings.REAR_DEMIST);
        rearDemistBackLeftBackRight.add(Mappings.BACK_LEFT_DOOR);
        rearDemistBackLeftBackRight.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistBackLeftBackRightHexCode, rearDemistBackLeftBackRight);

        rearDemistBackLeftBoot = new ArrayList<>();
        rearDemistBackLeftBoot.add(Mappings.REAR_DEMIST);
        rearDemistBackLeftBoot.add(Mappings.BACK_LEFT_DOOR);
        rearDemistBackLeftBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistBackLeftBootHexCode, rearDemistBackLeftBoot);

        rearDemistBackRightBoot = new ArrayList<>();
        rearDemistBackRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistBackRightBoot.add(Mappings.BACK_RIGHT_DOOR);
        rearDemistBackRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistBackRightBootHexCode, rearDemistBackRightBoot);

        rearDemistFrontLeftFrontRightBackLeft = new ArrayList<>();
        rearDemistFrontLeftFrontRightBackLeft.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftFrontRightBackLeft.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBackLeft.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontLeftFrontRightBackLeft.add(Mappings.BACK_LEFT_DOOR);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackLeftHexCode, rearDemistFrontLeftFrontRightBackLeft);

        rearDemistFrontLeftFrontRightBackRight = new ArrayList<>();
        rearDemistFrontLeftFrontRightBackRight.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftFrontRightBackRight.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBackRight.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontLeftFrontRightBackRight.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackRightHexCode, rearDemistFrontLeftFrontRightBackRight);

        rearDemistFrontLeftFrontRightBoot = new ArrayList<>();
        rearDemistFrontLeftFrontRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftFrontRightBoot.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBoot.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontLeftFrontRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftFrontRightBootHexCode, rearDemistFrontLeftFrontRightBoot);

        rearDemistFrontLeftBackLeftBackRight = new ArrayList<>();
        rearDemistFrontLeftBackLeftBackRight.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftBackLeftBackRight.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftBackLeftBackRight.add(Mappings.BACK_LEFT_DOOR);
        rearDemistFrontLeftBackLeftBackRight.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontLeftBackLeftBackRightHexCode, rearDemistFrontLeftBackLeftBackRight);

        rearDemistFrontLeftBackLeftBoot = new ArrayList<>();
        rearDemistFrontLeftBackLeftBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftBackLeftBoot.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftBackLeftBoot.add(Mappings.BACK_LEFT_DOOR);
        rearDemistFrontLeftBackLeftBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftBackLeftBootHexCode, rearDemistFrontLeftBackLeftBoot);

        rearDemistFrontLeftBackRightBoot = new ArrayList<>();
        rearDemistFrontLeftBackRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftBackRightBoot.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftBackRightBoot.add(Mappings.BACK_RIGHT_DOOR);
        rearDemistFrontLeftBackRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftBackRightBootHexCode, rearDemistFrontLeftBackRightBoot);

        rearDemistFrontRightBackLeftBackRight = new ArrayList<>();
        rearDemistFrontRightBackLeftBackRight.add(Mappings.REAR_DEMIST);
        rearDemistFrontRightBackLeftBackRight.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontRightBackLeftBackRight.add(Mappings.BACK_LEFT_DOOR);
        rearDemistFrontRightBackLeftBackRight.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontRightBackLeftBackRightHexCode, rearDemistFrontRightBackLeftBackRight);

        rearDemistFrontRightBackLeftBoot = new ArrayList<>();
        rearDemistFrontRightBackLeftBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontRightBackLeftBoot.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontRightBackLeftBoot.add(Mappings.BACK_LEFT_DOOR);
        rearDemistFrontRightBackLeftBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontRightBackLeftBootHexCode, rearDemistFrontRightBackLeftBoot);

        rearDemistFrontRightBackRightBoot = new ArrayList<>();
        rearDemistFrontRightBackRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontRightBackRightBoot.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontRightBackRightBoot.add(Mappings.BACK_RIGHT_DOOR);
        rearDemistFrontRightBackRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontRightBackRightBootHexCode, rearDemistFrontRightBackRightBoot);

        rearDemistBackLeftBackRightBoot = new ArrayList<>();
        rearDemistBackLeftBackRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistBackLeftBackRightBoot.add(Mappings.BACK_LEFT_DOOR);
        rearDemistBackLeftBackRightBoot.add(Mappings.BACK_RIGHT_DOOR);
        rearDemistBackLeftBackRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistBackLeftBackRightBootHexCode, rearDemistBackLeftBackRightBoot);

        rearDemistFrontLeftFrontRightBackLeftBackRight = new ArrayList<>();
        rearDemistFrontLeftFrontRightBackLeftBackRight.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftFrontRightBackLeftBackRight.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBackRight.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBackRight.add(Mappings.BACK_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBackRight.add(Mappings.BACK_RIGHT_DOOR);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackLeftBackRightHexCode, rearDemistFrontLeftFrontRightBackLeftBackRight);

        rearDemistFrontLeftFrontRightBackLeftBoot = new ArrayList<>();
        rearDemistFrontLeftFrontRightBackLeftBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftFrontRightBackLeftBoot.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBoot.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBoot.add(Mappings.BACK_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackLeftBootHexCode, rearDemistFrontLeftFrontRightBackLeftBoot);

        rearDemistFrontLeftFrontRightBackRightBoot = new ArrayList<>();
        rearDemistFrontLeftFrontRightBackRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftFrontRightBackRightBoot.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBackRightBoot.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontLeftFrontRightBackRightBoot.add(Mappings.BACK_RIGHT_DOOR);
        rearDemistFrontLeftFrontRightBackRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackRightBootHexCode, rearDemistFrontLeftFrontRightBackRightBoot);

        rearDemistFrontLeftBackLeftBackRightBoot = new ArrayList<>();
        rearDemistFrontLeftBackLeftBackRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftBackLeftBackRightBoot.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftBackLeftBackRightBoot.add(Mappings.BACK_LEFT_DOOR);
        rearDemistFrontLeftBackLeftBackRightBoot.add(Mappings.BACK_RIGHT_DOOR);
        rearDemistFrontLeftBackLeftBackRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftBackLeftBackRightBootHexCode, rearDemistFrontLeftBackLeftBackRightBoot);

        rearDemistFrontRightBackLeftBackRightBoot = new ArrayList<>();
        rearDemistFrontRightBackLeftBackRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontRightBackLeftBackRightBoot.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontRightBackLeftBackRightBoot.add(Mappings.BACK_LEFT_DOOR);
        rearDemistFrontRightBackLeftBackRightBoot.add(Mappings.BACK_RIGHT_DOOR);
        rearDemistFrontRightBackLeftBackRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontRightBackLeftBackRightBootHexCode, rearDemistFrontRightBackLeftBackRightBoot);

        rearDemistFrontLeftFrontRightBackLeftBackRightBoot = new ArrayList<>();
        rearDemistFrontLeftFrontRightBackLeftBackRightBoot.add(Mappings.REAR_DEMIST);
        rearDemistFrontLeftFrontRightBackLeftBackRightBoot.add(Mappings.FRONT_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBackRightBoot.add(Mappings.FRONT_RIGHT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBackRightBoot.add(Mappings.BACK_LEFT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBackRightBoot.add(Mappings.BACK_RIGHT_DOOR);
        rearDemistFrontLeftFrontRightBackLeftBackRightBoot.add(Mappings.BOOT);
        bemHashMap.put(rearDemistFrontLeftFrontRightBackLeftBackRightBootHexCode, rearDemistFrontLeftFrontRightBackLeftBackRightBoot);
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
     * Method to get the temp ID
     *
     * @return - the temp ID
     */
    public int getTempID() {
        return tempID;
    }

    /**
     * Method to get the fan ID
     *
     * @return - the fan ID
     */
    public int getFanID() {
        return fanID;
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
