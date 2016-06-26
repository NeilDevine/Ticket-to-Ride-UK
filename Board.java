///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.awt.event.*;
import java.awt.geom.*;
/**
 * This class represents the game board in Ticket to Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class Board extends JComponent 
implements MouseListener, MouseMotionListener
{
    // An adjacency list for the cities
    private City[] adjList;

    // A list for every road
    private ArrayList<Road> allRoads;

    private Image image; // The image of the board
    private double scaledX, scaledY; 
    // Scaled doubles depending on screen size
    boolean enabled = true; // Whether the board is active
    boolean doubleRoutes; // Whether double routes may be claimed

    Road roadOn; // The road a user is currently on
    Road roadClicked; // The road a user currently clicked
    City cityOn; // The city a user is currently on

    boolean usingRightOfWay = false; // Whether right of way may be used

    City cityLineA; // For displaying lines between cities
    City cityLineB; // For displaying lines between cities

    City cityLineC; // For displaying lines between cities

    ArrayList<City> toHighlight = new ArrayList<City>();

    /**
     * Constructor for all objects of class Board.
     * @param d The dimension of the Board
     * @param doubleRoutes Whether double routes are allowed
     */
    public Board(Dimension d, boolean doubleRoutes){
        this.doubleRoutes = doubleRoutes;
        try{
            image = ImageIO.read(new File("photos/map.jpg"));
        }catch(IOException e){
            System.out.println("Exception");
        }finally{
            if(image != null){
                image.flush();
            }
        }
        // Scale the image

        int x1 = image.getWidth(this);
        int y1 = image.getHeight(this);
        scaledX = (double)d.width / x1;
        scaledY = (double)d.height / y1;

        ArrayList<City> cities = new ArrayList<City>();

        // Create all cities

        // Ireland
        int x = 461;
        int y = 1063;
        City dublin = new City("Dublin", 'I', x,y);

        x = 517;
        y = 925;
        City dundalk = new City("Dundalk", 'I', x , y);

        x = 637;
        y = 835; 
        City belfast = new City("Belfast", 'I', x, y);

        x = 538;
        y = 636;
        City londonderry = new City("Londonderry", 'I', x, y);

        x = 302;
        y = 721;
        City sligo = new City("sligo", 'I', x, y);

        x = 134;
        y = 890;
        City galway = new City("Galway", 'I', x, y);

        x = 137;
        y = 1057; 
        City limerick = new City("Limerick", 'I', x, y);

        x = 109;
        y = 1226;
        City cork = new City("Cork", 'I', x, y);

        x = 343;
        y = 1255;
        City rosslare = new City("Rosslare", 'I', x, y);

        x = 321;
        y = 1023; 
        City tullamore = new City("Tullamore", 'I', x, y);

        x = 217;
        y = 1743;
        City penzance = new City("Penzance", 'E', x, y);

        x = 437;
        y = 1763;
        City plymouth = new City("Plymouth", 'E', x, y);

        x = 729;
        y = 1695;
        City bristol = new City("Bristol", 'E', x, y);

        x = 851;
        y = 1885; 
        City southampton = new City("Southampton", 'E', x, y);

        x = 1003;
        y = 1949;
        City brighton = new City("Brighton", 'E', x, y);

        x = 1229;
        y = 1947;
        City dover = new City("Dover", 'E', x, y);

        x = 1069;
        y = 1797;
        City london = new City("London", 'E', x, y);

        x = 1253;
        y = 1777;
        City ipswich = new City("Ipswich", 'E', x, y);

        x = 1359;
        y = 1649;
        City norwich = new City("Norwich", 'E', x, y);

        x = 1211;
        y = 1307;
        City hull = new City("Hull", 'E', x, y);

        x = 1085;
        y = 1215;
        City leeds = new City("Leeds", 'E', x, y);

        x = 1171;
        y = 1003;
        City newcastle = new City("Newcastle", 'E', x, y);

        x = 1005;
        y = 957;
        City carlisle = new City("Carlisle", 'E', x, y);

        x = 909; 
        y = 1075;
        City barrow = new City("Barrow", 'E', x, y);

        x = 847;
        y = 1203;
        City liverpool = new City("Liverpool", 'E', x, y);

        x = 959;
        y = 1283;
        City manchester = new City("Manchester", 'E', x, y);

        x = 917;
        y = 1511;
        City birmingham = new City("Birmingham", 'E', x, y);

        x = 1043;
        y = 1447;
        City nottingham = new City("Nottingham", 'E', x, y);

        x = 1011;   
        y = 1621;
        City northampton = new City("Northampton", 'E', x, y);

        x = 931;
        y = 1749;
        City reading = new City("Reading", 'E', x, y);

        x = 1145;    
        y = 1661;
        City cambridge = new City("Cambridge", 'E', x, y);

        x = 969;
        y = 101;
        City stornoway = new City("Stornoway", 's', x, y);

        x = 1051;
        y = 213;
        City ullapool = new City("Ullapool", 's', x, y);

        x = 1341;
        y = 214;
        City wick = new City("Wick", 's', x, y);

        x = 1126;
        y = 326;
        City inverness = new City("Inverness", 's', x, y);

        x = 939;
        y = 438;
        City fortWilliam = new City("Fort William", 's', x, y);

        x = 1318;
        y = 526;
        City aberdeen = new City("Aberdeen", 's', x, y);

        x = 1185;
        y = 610;
        City dundee = new City("Dundee", 's', x, y);

        x = 950;
        y = 677;
        City glasgow = new City("Glasgow", 's', x, y);

        x = 1090;
        y = 720;
        City edinburgh = new City("Edinburgh", 's', x, y);

        x = 785;
        y = 824;
        City stranraer = new City("Stranraer", 's', x, y);

        x = 665;
        y = 1169;
        City holyhead = new City("Holyhead", 'w', x, y);

        x = 615;
        y = 1382;
        City aberystwyth = new City("Aberystwyth", 'w', x, y);

        x = 530;
        y = 1505;
        City carmarthen = new City("Carmarthen", 'w', x, y);

        x = 715;
        y = 1480;
        City llandrindodWells = new City("Llandrindod Wells", 'w', x, y);

        x = 635;
        y = 1606;
        City cardiff = new City("Cardiff", 'w', x, y);

        x = 733;
        y = 2130;
        City france1 = new City("France", 'F', x, y);

        x = 1400;
        y = 2050;
        City france2 = new City("France", 'F', x, y);

        x = -100;
        y = -100;
        City newYork = new City("New York", 'U', x, y);

        //ROADS
        // Set up all roads
        allRoads = new ArrayList<Road>();
        Road r = new Road(belfast, londonderry, Color.ORANGE);
        r.addRect(611,813, 631,802, 597,740, 577,751);
        r.addRect(576,746, 595,736, 562,673, 542,684);
        belfast.addRoad(r);
        londonderry.addRoad(r);
        allRoads.add(r);

        r = new Road(londonderry, sligo, Color.GREEN);
        r.addRect(489,656, 481,635, 415,661, 423,682);
        r.addRect(419,683, 411,662, 344,688, 352,709);
        londonderry.addRoad(r);
        sligo.addRoad(r);
        allRoads.add(r);

        r = new Road(dundalk, londonderry, Color.PINK);
        r.addRect(504,895, 526,888, 505,821, 483,827);
        r.addRect(482,814, 505,815, 511,745, 488,743);
        r.addRect(490,733, 512,740, 533,673, 512,666);
        dundalk.addRoad(r);
        londonderry.addRoad(r);
        allRoads.add(r);

        r = new Road(londonderry, fortWilliam, Color.GRAY, 1);
        r.addRect(577,613,  637,583,  648,602,  588,632);
        r.addRect(643,581,  705,549,  713,568,  653,600);
        r.addRect(710,545,  771,515,  780,533,  720,565);
        r.addRect(777,515,  839,480,  848,499,  788,530);
        r.addRect(845,477,  903,448,  914,463,  856,496);
        londonderry.addRoad(r);
        fortWilliam.addRoad(r);
        allRoads.add(r);

        r = new Road(londonderry, glasgow, Color.GRAY, 1);
        r.addRect(619,638,  684,643,  682,664,  616,656);
        r.addRect(690,643,  760,648,  756,669,  688,664);
        r.addRect(766,650,  835,655,  833,676,  764,671);
        r.addRect(842,656,  907,662,  907,680,  840,676);
        londonderry.addRoad(r);
        glasgow.addRoad(r);
        allRoads.add(r);

        r = new Road(londonderry, stranraer, Color.GRAY, 1);
        r.addRect(590,660,  643,696,  630,715,  578,675);
        r.addRect(649,701,  705,741,  693,758,  637,718);
        r.addRect(710,745,  763,782,  753,797,  699,762);
        londonderry.addRoad(r);
        stranraer.addRoad(r);
        allRoads.add(r);

        r = new Road(dundalk, sligo, Color.BLACK);
        r.addRect(481,907, 496,893, 447,842, 431,858);
        r.addRect(427,853, 441,838, 383,788, 377,804);
        r.addRect(374,800, 389,784, 339,735, 324,750);
        dundalk.addRoad(r);
        sligo.addRoad(r);
        allRoads.add(r);

        r = new Road(sligo, galway, Color.ORANGE);
        r.addRect(263,743, 277,762, 226,808, 210,793);
        r.addRect(208,795, 223,813, 171,861, 155,845);
        sligo.addRoad(r);
        galway.addRoad(r);
        allRoads.add(r);

        r = new Road(sligo, tullamore, Color.BLUE);
        r.addRect(297,759,  320,758,  325,830,  300,831);
        r.addRect(301,835,  324,835,  328,905,  305,907);
        r.addRect(306,911,  328,910,  333,982,  310,983);
        sligo.addRoad(r);
        tullamore.addRoad(r);
        allRoads.add(r);

        r = new Road(belfast, dundalk, Color.WHITE);
        r.addRect(607,859, 592,842, 538,888, 553,906);
        belfast.addRoad(r);
        dundalk.addRoad(r);
        allRoads.add(r);

        r = new Road(belfast, dundalk, Color.RED);
        r.addRect(556,909, 571,925, 624,879, 610,862);
        dundalk.addRoad(r);
        belfast.addRoad(r);
        allRoads.add(r);

        r = new Road(belfast, stranraer, Color.GRAY, 1);
        r.addRect(680,823,  744,819,  746,836,  682,840);
        dundalk.addRoad(r);
        belfast.addRoad(r);
        allRoads.add(r);

        r = new Road(belfast, barrow, Color.GRAY, 1);
        r.addRect(671,856,  721,897,  707,914,  658,868);
        r.addRect(727,901,  780,946,  765,963,  712,918);
        r.addRect(785,950,  837,995,  823,1011,  770,966);
        r.addRect(842,1000,  891,1043,  878,1057,  828,1016);
        belfast.addRoad(r);
        barrow.addRoad(r);
        allRoads.add(r);

        r = new Road(galway, limerick, Color.YELLOW);
        r.addRect(123,941,  146,940,  146,1012,  123,1012);
        galway.addRoad(r);
        limerick.addRoad(r);
        allRoads.add(r);

        r = new Road(galway, tullamore, Color.GRAY);
        r.addRect(186,897,  241,940,  228,957,  172,915);
        r.addRect(247,943,  302,986,  289,1003,  233,961);
        galway.addRoad(r);
        tullamore.addRoad(r);
        allRoads.add(r);

        r = new Road(galway, tullamore, Color.GRAY);
        r.addRect(169,918,  224,961,  211,978,  157,935);
        r.addRect(230,965,  285,1007,  272,1025,  217,982);
        galway.addRoad(r);
        tullamore.addRoad(r);
        allRoads.add(r);

        r = new Road(dundalk, dublin, Color.BLUE);
        r.addRect(478,1026, 501,1033, 522,966, 500, 958);
        dublin.addRoad(r);
        dundalk.addRoad(r);
        allRoads.add(r);

        r = new Road(dundalk, dublin, Color.YELLOW);
        r.addRect(452,1016, 473,1025, 496,958, 475,950);
        dundalk.addRoad(r);
        dublin.addRoad(r);
        allRoads.add(r);

        r = new Road(dundalk, holyhead, Color.GRAY, 1);
        r.addRect(552,946,  586,1003,  567,1013,  536,954);
        r.addRect(590,1007,  623,1069,  605,1079,  571,1018);
        r.addRect(627,1073,  657,1130,  641,1140,  609,1085);
        dundalk.addRoad(r);
        holyhead.addRoad(r);
        allRoads.add(r);

        r = new Road(limerick, cork, Color.PINK);
        r.addRect(119,1098,  142,1103,  127, 1173,  103,1167);
        limerick.addRoad(r);
        cork.addRoad(r);
        allRoads.add(r);

        r = new Road(limerick, tullamore, Color.GRAY);
        r.addRect(185,1040,  255,1029,  257,1051,  188,1061);
        limerick.addRoad(r);
        tullamore.addRoad(r);
        allRoads.add(r);

        r = new Road(tullamore, cork, Color.YELLOW);
        r.addRect(292,1038,  308,1056,  256,1106,  240,1087);
        r.addRect(237,1089,  253,1108,  199,1156,  184,1138);
        r.addRect(180,1141,  196,1159,  143,1206,  127,1189);
        cork.addRoad(r);
        tullamore.addRoad(r);
        allRoads.add(r);

        r = new Road(tullamore, rosslare, Color.RED);
        r.addRect(310,1066,  332,1067,  330,1138,  306,1138);
        r.addRect(306,1147,  330,1144,  337,1214,   316,1217);
        rosslare.addRoad(r);
        tullamore.addRoad(r);
        allRoads.add(r);

        r = new Road(tullamore, dublin, Color.GREEN);
        r.addRect(429,1046, 435,1025, 365,1008, 360,1030);
        dublin.addRoad(r);
        tullamore.addRoad(r);
        allRoads.add(r);

        r = new Road(tullamore, dublin, Color.ORANGE);
        r.addRect(359,1035,  430,1050,  423,1074,  353,1058);
        dublin.addRoad(r);
        tullamore.addRoad(r);
        allRoads.add(r);

        r = new Road(dublin, rosslare, Color.WHITE);
        r.addRect(445,1097, 425,1085, 388,1146, 407,1158);
        r.addRect(405,1162, 386,1150, 348,1211, 367,1222);
        dublin.addRoad(r);
        rosslare.addRoad(r);
        allRoads.add(r);

        r = new Road(dublin, rosslare, Color.BLACK);
        r.addRect(449,1100,  468,1111,  432,1172,  412,1161);
        r.addRect(409,1164,  428,1176,  391,1237,  372,1225);
        dublin.addRoad(r);
        rosslare.addRoad(r);
        allRoads.add(r);

        r = new Road(dublin, holyhead, Color.GRAY, 1);
        r.addRect(503,1079,  560,1112,  548,1131,  492,1094);
        r.addRect(565,1116,  621,1151,  612,1168,  554,1133);
        dublin.addRoad(r);
        holyhead.addRoad(r);
        allRoads.add(r);

        r = new Road(rosslare, holyhead, Color.GRAY, 1);
        r.addRect(399,1242,  463,1221,  470,1243,  403,1260);
        r.addRect(469,1220,  536,1200,  542,1222,  476,1241);
        r.addRect(542,1199,  607,1182,  611,1200,  548,1220);
        rosslare.addRoad(r);
        holyhead.addRoad(r);
        allRoads.add(r);

        r = new Road(rosslare, aberystwyth, Color.GRAY, 1);
        r.addRect(385,1278,  448,1301,  439,1323,  378,1295);
        r.addRect(453,1304,  517,1330,  509,1351,  445,1324);
        r.addRect(523,1333,  583,1359,  577,1378,  515,1353);
        rosslare.addRoad(r);
        aberystwyth.addRoad(r);
        allRoads.add(r);

        r = new Road(rosslare, carmarthen, Color.GRAY, 1);
        r.addRect(338,1299,  338,1365,  315,1364,  319,1298);
        r.addRect(340,1371,  368,1435,  347,1444,  320,1379);
        r.addRect(372,1440,  428,1480,  416,1498,  359,1458);
        r.addRect(434,1483,  497,1496,  496,1516,  430,1504);
        rosslare.addRoad(r);
        carmarthen.addRoad(r);
        allRoads.add(r);

        r = new Road(cork, rosslare, Color.BLUE);
        r.addRect(155,1212,  225,1225,  222,1247,  150,1235);
        r.addRect(230,1226,  300,1239,  296,1260,  227,1248);
        cork.addRoad(r);
        rosslare.addRoad(r);
        allRoads.add(r);

        r = new Road(cork, penzance, Color.GRAY, 2);
        r.addRect(121,1267,  138,1331,  115,1337,  103,1272);
        r.addRect(139,1338,  154,1406,  132,1410,  117,1343);
        r.addRect(155,1411,  170,1480,  148,1485,  133,1416);
        r.addRect(173,1485,  187,1554,  165,1559,  149,1491);
        r.addRect(189,1559,  203,1628,  181,1633,  166,1564);
        r.addRect(206,1634,  218,1698,  197,1701,  183,1639);
        cork.addRoad(r);
        penzance.addRoad(r);
        allRoads.add(r);

        /**  SCOTLAND BELOW */
        /** ====================================*/

        r = new Road(stornoway, fortWilliam, Color.GRAY, 1);
        r.addRect(926,95,  934,110,  879,145,  868,127);
        r.addRect(874,152,  836,211,  816,198,  855,141);
        r.addRect(834,216,  830,287,  807,286,  811,216);
        r.addRect(831,293,  861,356,  842,365,  811,303);
        r.addRect(865,360,  915,404,  903,418,  851,378);
        stornoway.addRoad(r);
        fortWilliam.addRoad(r);
        allRoads.add(r);

        r = new Road(stornoway, ullapool, Color.GRAY, 1);
        r.addRect(1004,125,  1038,178,  1021,188,  986,134);
        stornoway.addRoad(r);
        ullapool.addRoad(r);
        allRoads.add(r);

        r = new Road(stornoway, wick, Color.GRAY, 2);
        r.addRect(1006,90,  1074,88,  1074,112,  1006,109);
        r.addRect(1079,90,  1150,90,  1150,112,  1079,112);
        r.addRect(1154,90,  1227,90,  1227,112,  1154,112);
        r.addRect(1231,90,  1301,90,  1301,112,  1231,112);
        r.addRect(1327,106,  1348,168,  1328,174,  1305,114);
        stornoway.addRoad(r);
        wick.addRoad(r);
        allRoads.add(r);

        r = new Road(ullapool, fortWilliam, Color.PINK);
        r.addRect(1033,254,  1002,318,  981,309,  1012,245);
        r.addRect(1000,323,  968,386,  948,377,  979,313);
        ullapool.addRoad(r);
        fortWilliam.addRoad(r);
        allRoads.add(r);

        r = new Road(ullapool, inverness, Color.ORANGE);
        r.addRect(1089,233,  1122,295,  1103,306,  1070,243);
        ullapool.addRoad(r);
        inverness.addRoad(r);
        allRoads.add(r);

        r = new Road(ullapool, wick, Color.YELLOW);
        r.addRect(1084,194,  1148,164,  1158,186,  1093,215);
        r.addRect(1162,162,  1234,162,  1234,185,  1162,185);
        r.addRect(1244,163,  1314,182,  1308,204,  1237,185);
        ullapool.addRoad(r);
        wick.addRoad(r);
        allRoads.add(r);

        r = new Road(wick, inverness, Color.RED);
        r.addRect(1289,219,  1299,239,  1237,273,  1226,254);
        r.addRect(1222,256,  1232,274,  1170,309,  1160,290);
        wick.addRoad(r);
        inverness.addRoad(r);
        allRoads.add(r);

        r = new Road(wick, aberdeen, Color.GRAY, 1);
        r.addRect(1348,255,  1349,322,  1325,321,  1328,255);
        r.addRect(1348,328,  1348,397,  1324,397,  1328,328);
        r.addRect(1348,403,  1348,469,  1325,467,  1325,403);
        wick.addRoad(r);
        aberdeen.addRoad(r);
        allRoads.add(r);

        r = new Road(inverness, fortWilliam, Color.BLACK);
        r.addRect(1094,334,  1106,354,  1046,390,  1032,372);
        r.addRect(1029,374,  1042,392,  981,430,  969,412);
        inverness.addRoad(r);
        fortWilliam.addRoad(r);
        allRoads.add(r);

        r = new Road(inverness, dundee, Color.BLUE);
        r.addRect(1145,355,  1161,425,  1138,429,  1124,360);
        r.addRect(1161,430,  1177,499,  1155,504,  1139,435);
        r.addRect(1177,504,  1192,572,  1170,577,  1155,509);
        inverness.addRoad(r);
        dundee.addRoad(r);
        allRoads.add(r);

        r = new Road(inverness, aberdeen, Color.PINK);
        r.addRect(1158,327,  1226,350,  1219,371,  1150,348);
        r.addRect(1239,361,  1285,415,  1268,428,  1222,375);
        r.addRect(1293,425,  1315,493,  1294,501,  1271,434);
        inverness.addRoad(r);
        aberdeen.addRoad(r);
        allRoads.add(r);

        r = new Road(fortWilliam, glasgow, Color.ORANGE);
        r.addRect(962,491,  964,563,  940,563,  938,493);
        r.addRect(964,567,  965,639,  942,639,  940,569);
        fortWilliam.addRoad(r);
        glasgow.addRoad(r);
        allRoads.add(r);

        r = new Road(fortWilliam, dundee, Color.GREEN);
        r.addRect(981,449,  1039,491,  1025,510,  968,468);
        r.addRect(1042,495,  1100,536,  1087,555,  1029,513);
        r.addRect(1104,540,  1161,581,  1149,599,  1091,557);
        fortWilliam.addRoad(r);
        dundee.addRoad(r);
        allRoads.add(r);

        r = new Road(aberdeen, dundee, Color.WHITE);
        r.addRect(1275,538,  1286,558,  1226,596,  1213,576);
        aberdeen.addRoad(r);
        dundee.addRoad(r);
        allRoads.add(r);

        r = new Road(aberdeen, edinburgh, Color.GRAY, 1);
        r.addRect(1314,562,  1315,628,  1292,628,  1293,562);
        r.addRect(1313,644,  1284,707,  1263,697,  1292,634);
        r.addRect(1261,702,  1275,720,  1222,765,  1207,747);
        r.addRect(1200,748,  1191,769,  1134,738,  1142,720);
        aberdeen.addRoad(r);
        edinburgh.addRoad(r);
        allRoads.add(r);

        r = new Road(aberdeen, newcastle, Color.GRAY, 1);
        r.addRect(1340,560,  1343,628,  1320,629,  1320,563);
        r.addRect(1343,634,  1344,705,  1320,703,  1319,634);
        r.addRect(1343,710,  1337,779,  1314,778,  1320,709);
        r.addRect(1335,790,  1319,857,  1295,850,  1313,784);
        r.addRect(1314,868,  1278,929,  1259,917,  1293,857);
        r.addRect(1270,940,  1222,984,  1210,969,  1255,923);
        aberdeen.addRoad(r);
        newcastle.addRoad(r);
        allRoads.add(r);

        r = new Road(dundee, edinburgh, Color.YELLOW);
        r.addRect(1139,617,  1158,632,  1113,687,  1095,672);
        dundee.addRoad(r);
        edinburgh.addRoad(r);
        allRoads.add(r);

        r = new Road(dundee, edinburgh, Color.RED);
        r.addRect(1160,635,  1178,649,  1133,703,  1116,690);
        dundee.addRoad(r);
        edinburgh.addRoad(r);
        allRoads.add(r);

        r = new Road(glasgow, stranraer, Color.RED);
        r.addRect(917,696,  931,714,  877,760,  862,742);
        r.addRect(858,745,  873,762,  818,807,  804,791);
        glasgow.addRoad(r);
        stranraer.addRoad(r);
        allRoads.add(r);

        r = new Road(glasgow, edinburgh, Color.BLUE);
        r.addRect(995,659,  1063,679,  1057,701,  988,681);
        glasgow.addRoad(r);
        edinburgh.addRoad(r);
        allRoads.add(r);

        r = new Road(glasgow, edinburgh, Color.BLACK);
        r.addRect(987,685,  1056,705,  1050,726,  981,707);
        glasgow.addRoad(r);
        edinburgh.addRoad(r);
        allRoads.add(r);

        r = new Road(edinburgh, stranraer, Color.WHITE);
        r.addRect(1048,743,  1054,764,  986,785,  979,763);
        r.addRect(975,764,  981,785,  913,806,  907,785);
        r.addRect(902,787,  908,808,  840,828,  834,807);
        edinburgh.addRoad(r);
        stranraer.addRoad(r);
        allRoads.add(r);

        r = new Road(edinburgh, carlisle, Color.ORANGE);
        r.addRect(1073,777,  1052,846,  1031,838,  1051,772);
        r.addRect(1050,851,  1031,918,  1009,911,  1029,844);
        edinburgh.addRoad(r);
        carlisle.addRoad(r);
        allRoads.add(r);

        r = new Road(edinburgh, newcastle, Color.GREEN);
        r.addRect(1102,751,  1121,819,  1100,824,  1082,757);
        r.addRect(1122,824,  1141,891,  1120,897,  1101,831);
        r.addRect(1143,896,  1162,964,  1142,970,  1122,903);
        edinburgh.addRoad(r);
        newcastle.addRoad(r);
        allRoads.add(r);

        r = new Road(edinburgh, newcastle, Color.PINK);
        r.addRect(1129,743,  1147,811,  1126,817,  1107,749);
        r.addRect(1149,816,  1168,883,  1147,890,  1128,822);
        r.addRect(1169,889,  1189,956,  1168,963,  1148,896);
        edinburgh.addRoad(r);
        newcastle.addRoad(r);
        allRoads.add(r);

        r = new Road(stranraer, carlisle, Color.GRAY, 1);
        r.addRect(799,852,  825,913,  804,921,  782,861);
        r.addRect(831,917,  891,951,  880,970,  819,937);
        r.addRect(897,953,  962,941,  966,961,  901,975);
        stranraer.addRoad(r);
        carlisle.addRoad(r);
        allRoads.add(r);

        /**    whales below */
        /** =============================================
         * ==============================================
         */
        r = new Road(holyhead, aberystwyth, Color.GRAY, 1);
        r.addRect(656,1210,  641,1274,  620,1267,  638,1205);
        r.addRect(641,1280,  639,1345,  618,1347,  618,1280);
        holyhead.addRoad(r);
        aberystwyth.addRoad(r);
        allRoads.add(r);

        r = new Road(holyhead, llandrindodWells, Color.BLUE);
        r.addRect(693,1206,  705,1277,  682,1281,  670,1210);
        r.addRect(705,1282,  718,1352,  695,1356,  683,1286);
        r.addRect(718,1357,  730,1427,  708,1430,  696,1361);
        holyhead.addRoad(r);
        llandrindodWells.addRoad(r);
        allRoads.add(r);

        r = new Road(holyhead, liverpool, Color.GRAY, 1);
        r.addRect(697,1136,  759,1111,  765,1132,  703,1153);
        r.addRect(789,1118,  834,1166,  821,1181,  773,1134);
        holyhead.addRoad(r);
        liverpool.addRoad(r);
        allRoads.add(r);

        r = new Road(aberystwyth, carmarthen, Color.YELLOW);
        r.addRect(587,1404,  605,1418,  565,1475,  546,1461);
        aberystwyth.addRoad(r);
        carmarthen.addRoad(r);
        allRoads.add(r);

        r = new Road(aberystwyth, llandrindodWells, Color.WHITE);
        r.addRect(651,1399,  704,1447,  688,1465,  635,1416);
        aberystwyth.addRoad(r);
        llandrindodWells.addRoad(r);
        allRoads.add(r);

        r = new Road(carmarthen, cardiff, Color.RED);
        r.addRect(569,1518,  621,1567,  606,1583,  553,1536);
        carmarthen.addRoad(r);
        cardiff.addRoad(r);
        allRoads.add(r);

        r = new Road(llandrindodWells, manchester, Color.GREEN);
        r.addRect(740,1427,  790,1379,  806,1394,  755,1444);
        r.addRect(797,1372,  853,1331,  866,1349,  810,1390);
        r.addRect(858,1328,  916,1287,  928,1306,  870,1345);
        llandrindodWells.addRoad(r);
        manchester.addRoad(r);
        allRoads.add(r);

        r = new Road(llandrindodWells, birmingham, Color.RED);
        r.addRect(745,1457,812,1434,  819,1455,  754,1478);
        r.addRect(835,1436,  898,1471,  886,1490,  825,1455);
        llandrindodWells.addRoad(r);
        birmingham.addRoad(r);
        allRoads.add(r);

        r = new Road(llandrindodWells, cardiff, Color.PINK);
        r.addRect(679,1500,  696,1513,  655,1570,  637,1557);
        llandrindodWells.addRoad(r);
        cardiff.addRoad(r);
        allRoads.add(r);

        r = new Road(cardiff, birmingham, Color.BLUE);
        r.addRect(661,1574,  728,1549,  735,1570,  670,1595);
        r.addRect(733,1548,  798,1523,  806,1542,  741,1568);
        r.addRect(804,1521,  868,1495,  878,1515,  812,1541);
        cardiff.addRoad(r);
        birmingham.addRoad(r);
        allRoads.add(r);

        r = new Road(cardiff, birmingham, Color.ORANGE);
        r.addRect(671,1600,  738,1576,  746,1595,  680,1622);
        r.addRect(742,1573,  809,1547,  816,1569,  751,1593);
        r.addRect(814,1545,  878,1521,  887,1541,  821,1566);
        cardiff.addRoad(r);
        birmingham.addRoad(r);
        allRoads.add(r);

        r = new Road(cardiff, bristol, Color.GRAY, 1);
        r.addRect(664,1626,  716,1662,  703,1678,  652,1640);
        cardiff.addRoad(r);
        bristol.addRoad(r);
        allRoads.add(r);

        r = new Road(cardiff, penzance, Color.GRAY, 1);
        r.addRect(597,1597,  599,1616,  535,1629,  531,1607);
        r.addRect(524,1607,  528,1630,  460,1642,  455,1619);
        r.addRect(450,1620,  454,1642,  385,1655,  381,1631);
        r.addRect(372,1634,  379,1656,  314,1677,  305,1655);
        r.addRect(292,1663,  307,1679,  257,1723,  243,1708);
        cardiff.addRoad(r);
        penzance.addRoad(r);
        allRoads.add(r);

        /**   england*/
        r = new Road(carlisle, newcastle, Color.YELLOW);
        r.addRect(1054,954, 1047,977, 1116,999, 1123,976);
        carlisle.addRoad(r);
        newcastle.addRoad(r);
        allRoads.add(r);

        r = new Road(carlisle, barrow, Color.RED);
        r.addRect(990,991, 971,978, 931,1038, 950,1051);
        carlisle.addRoad(r);
        barrow.addRoad(r);
        allRoads.add(r);

        r = new Road(newcastle, hull, Color.GRAY, 1);
        r.addRect(1220,1000, 1214,1019, 1273,1047, 1284,1025);
        r.addRect(1277,1050, 1319,1108, 1338,1094, 1296,1037);
        r.addRect(1320,1113, 1328,1184, 1351,1181, 1343,1110);
        r.addRect(1328,1188, 1299,1253, 1320,1262, 1349,1198);
        r.addRect(1297,1257, 1244,1300, 1251,1313, 1310,1277);
        newcastle.addRoad(r);
        hull.addRoad(r);
        allRoads.add(r);

        r = new Road(newcastle, leeds, Color.WHITE);
        r.addRect(1136,1031, 1159,1039, 1134,1107, 1111, 1098);
        r.addRect(1110,1101, 1131,1110, 1105,1177, 1084,1169);
        newcastle.addRoad(r);
        leeds.addRoad(r);
        allRoads.add(r);;

        r = new Road(newcastle, leeds, Color.ORANGE);
        r.addRect(1163,1040, 1183,1048, 1158,1116,1138,1107);
        r.addRect(1135,1112, 1156,1119, 1130,1186, 1110,1177);
        newcastle.addRoad(r);
        leeds.addRoad(r);
        allRoads.add(r);

        // penzance, Plymouth
        r = new Road(penzance, plymouth, Color.BLACK);
        r.addRect(251, 1735, 248,1757, 321, 1768, 323, 1744);
        r.addRect(327,1745, 324, 1769, 396,1779, 399,1756);       

        penzance.addRoad(r);
        plymouth.addRoad(r);
        allRoads.add(r);

        r = new Road(penzance, plymouth, Color.GRAY, 1);
        r.addRect(218,1784, 248,1847, 271,1835, 238,1777);
        r.addRect(275,1837, 272,1861, 347,1866, 345,1840);
        r.addRect(349,1839, 367,1859, 417,1811, 401,1795);

        penzance.addRoad(r);
        plymouth.addRoad(r);
        allRoads.add(r);

        // plymouth, bristol

        r = new Road(plymouth, bristol, Color.YELLOW);
        r.addRect(476,1750, 482,1774, 553,1758, 547,1733);
        r.addRect(552,1732, 556,1757, 628,1743, 622,1717);
        r.addRect(626,1716, 630,1742, 699,1726, 696,1704);

        bristol.addRoad(r);
        plymouth.addRoad(r);
        allRoads.add(r);

        // plymouth, southampton
        r = new Road(plymouth, southampton, Color.GRAY, 1);
        r.addRect(455,1787, 514,1814, 504,1833, 445,1803);
        r.addRect(519,1815, 586,1839, 577,1861, 511,1836);
        r.addRect(590,1840, 659, 1857, 653,1880, 585,1861);
        r.addRect(665,1858, 734,1866, 731,1890, 661,1880);
        r.addRect(739,1867, 806,1871, 807,1888, 739,1890);

        plymouth.addRoad(r);
        southampton.addRoad(r);
        allRoads.add(r);

        // bristol, southampton
        r = new Road(southampton, bristol, Color.GREEN);
        r.addRect(833,1841, 812,1853, 776,1790, 797,1780);
        r.addRect(793,1776, 775,1787, 739,1725, 759,1714);
        bristol.addRoad(r);
        southampton.addRoad(r);
        allRoads.add(r);

        // bristol, reading
        r = new Road(reading, bristol, Color.WHITE);
        r.addRect(768,1679, 835,1704, 827,1725, 759,1702);
        r.addRect(839,1706, 907,1728, 899,1751, 832,1727);
        bristol.addRoad(r);
        reading.addRoad(r);
        allRoads.add(r);

        // southampton, brighton
        r = new Road(southampton, brighton, Color.BLUE);
        r.addRect(898,1908, 967,1927, 961,1950, 893,1930);
        brighton.addRoad(r);
        southampton.addRoad(r);
        allRoads.add(r);

        // southampton, reading
        r = new Road(southampton, reading, Color.ORANGE);
        r.addRect(855,1832, 899,1775, 916,1789, 874,1846);
        reading.addRoad(r);
        southampton.addRoad(r);
        allRoads.add(r);

        // southampton, london
        r = new Road(southampton, london, Color.RED);
        r.addRect(880,1853, 947,1827, 955,1847, 888,1875);
        r.addRect(951,1826, 1017,1800, 1025,1820, 959,1847);
        london.addRoad(r);
        southampton.addRoad(r);
        allRoads.add(r);

        r = new Road(southampton, london, Color.BLACK);
        r.addRect(890,1878, 956,1852, 964,1875, 899,1898);
        r.addRect(960,1850, 1028,1825, 1035,1846, 969,1872);
        london.addRoad(r);
        southampton.addRoad(r);
        allRoads.add(r);

        r = new Road(southampton, newYork, Color.GRAY, 3);
        r.addRect(811,1899, 750,1929, 762,1950, 820,1910);
        r.addRect(745,1933, 684,1967, 695,1987, 757,1954);
        r.addRect(678,1970, 614,1997, 623,2019, 688,1992);
        r.addRect(608,1999, 541,2017, 547,2040, 615,2022);
        r.addRect(536,2018, 466,2030, 470,2053, 540,2042);
        r.addRect(461,2031, 391,2040, 394,2064, 464,2055);
        r.addRect(385,2042, 315,2047, 317,2071, 388,2065);
        r.addRect(310,2047, 239,2043, 238,2068, 310,2071);
        r.addRect(234,2043, 165,2029, 160,2053, 229,2066);
        r.addRect(160,2028, 94,2009, 89,2024, 152,2051);
        southampton.addRoad(r);
        newYork.addRoad(r);
        allRoads.add(r);

        r = new Road(southampton, france1, Color.GRAY, 1);
        r.addRect(832,1918, 850,1927, 827,1985, 807,1976);
        r.addRect(804,1983, 823,1995, 788,2055, 769,2043);
        r.addRect(766,2049, 782,2062, 738,2114, 726,2100);
        southampton.addRoad(r);
        france1.addRoad(r);
        allRoads.add(r);

        r = new Road(southampton, france1, Color.GRAY, 1);
        r.addRect(856,1932, 874,1938, 851,2000, 830,1991);
        r.addRect(828,1997, 846,2008, 812,2067, 792,2057);
        r.addRect(789,2063, 806,2076, 762,2128, 749,2115);
        southampton.addRoad(r);
        france1.addRoad(r);
        allRoads.add(r);

        // brighton, london
        r = new Road(brighton,london, Color.GRAY);
        r.addRect(1052,1848, 1073,1858, 1041,1922, 1021,1912);
        brighton.addRoad(r);
        london.addRoad(r);
        allRoads.add(r);

        // brighton, dover
        r = new Road(brighton,dover, Color.PINK);
        r.addRect(1046,1938, 1118,1938, 1117,1962, 1046,1962);
        r.addRect(1122,1938, 1193,1938, 1193,1961, 1122,1962);
        brighton.addRoad(r);
        dover.addRoad(r);
        allRoads.add(r);

        // dover, france2
        r = new Road(dover,france2, Color.GRAY, 1);
        r.addRect(1271,1958, 1326,1992, 1314,2012, 1259,1975);
        r.addRect(1332,1996, 1387,2034, 1377,2050, 1321,2014);
        dover.addRoad(r);
        france2.addRoad(r);
        allRoads.add(r);

        // dover, london
        r = new Road(dover,london, Color.GRAY);
        r.addRect(1160,1867, 1213,1915, 1198,1931, 1145,1885);
        r.addRect(1103,1816, 1157,1865, 1142,1880, 1090,1832);
        dover.addRoad(r);
        london.addRoad(r);
        allRoads.add(r);

        // reading, birmingham
        r = new Road(reading, birmingham, Color.GRAY);
        r.addRect(906,1642, 927,1710, 907,1717, 885,1649);
        r.addRect(913,1567, 905,1637, 882,1636, 890,1564);
        reading.addRoad(r);
        birmingham.addRoad(r);
        allRoads.add(r);

        // reading, northampton
        r = new Road(reading, northampton, Color.RED);
        r.addRect(937,1713, 972,1650, 991,1660, 958,1723);
        reading.addRoad(r);
        northampton.addRoad(r);
        allRoads.add(r);

        // reading, london
        r = new Road(reading, london, Color.GREEN);
        r.addRect(966,1746, 1033,1769, 1026,1790, 959,1766);
        reading.addRoad(r);
        london.addRoad(r);
        allRoads.add(r);

        // london, northampton
        r = new Road(london, northampton, Color.PINK);
        r.addRect(1027,1659, 1045,1729, 1023,1734, 1005,1666);
        london.addRoad(r);
        northampton.addRoad(r);
        allRoads.add(r);

        r = new Road(london, northampton, Color.BLUE);
        r.addRect(1054,1652, 1070,1722, 1049,1727, 1032,1658);
        london.addRoad(r);
        northampton.addRoad(r);
        allRoads.add(r);

        // london, cambridge
        r = new Road(london, cambridge, Color.ORANGE);
        r.addRect(1109,1683, 1129,1693, 1099,1758, 1079,1748);
        london.addRoad(r);
        cambridge.addRoad(r);
        allRoads.add(r);

        r = new Road(london, cambridge, Color.YELLOW);
        r.addRect(1134,1695, 1155,1706, 1124,1769, 1103,1760);
        london.addRoad(r);
        cambridge.addRoad(r);
        allRoads.add(r);

        // london, lpswich
        r = new Road(london, ipswich, Color.WHITE);
        r.addRect(1203,1771, 1204,1793, 1134,1803, 1130,1780);
        london.addRoad(r);
        ipswich.addRoad(r);
        allRoads.add(r);

        // lpswich, cambridge
        r = new Road(ipswich, cambridge, Color.BLACK);
        r.addRect(1185,1688, 1236,1736, 1221,1752, 1169,1703);
        ipswich.addRoad(r);
        cambridge.addRoad(r);
        allRoads.add(r);

        // lpswich, norwich
        r = new Road(ipswich, norwich, Color.GREEN);
        r.addRect(1321,1682, 1339,1694, 1299,1753, 1280,1740);
        ipswich.addRoad(r);
        norwich.addRoad(r);
        allRoads.add(r);

        // northampton, birmingham
        r = new Road(northampton, birmingham, Color.WHITE);
        r.addRect(934,1544, 984,1595, 967,1611, 917,1560);
        northampton.addRoad(r);
        birmingham.addRoad(r);
        allRoads.add(r);

        r = new Road(northampton, birmingham, Color.GREEN);
        r.addRect(953,1525, 1003,1576, 987,1592, 938,1540);
        northampton.addRoad(r);
        birmingham.addRoad(r);
        allRoads.add(r);

        // northampton, nottingham
        r = new Road(northampton, nottingham, Color.ORANGE);
        r.addRect(1050,1496, 1041,1567, 1018,1565, 1028,1493);
        northampton.addRoad(r);
        nottingham.addRoad(r);
        allRoads.add(r);

        // northampton, cambridge
        r = new Road(northampton, cambridge, Color.GRAY);
        r.addRect(1056,1609, 1123,1636, 1114,1655, 1048,1630);
        northampton.addRoad(r);
        cambridge.addRoad(r);
        allRoads.add(r);

        // cambridge, nottingham 
        r = new Road(cambridge, nottingham, Color.GRAY);
        r.addRect(1118,1554, 1149,1620, 1128,1629, 1097,1564);
        r.addRect(1086,1487, 1116,1551, 1095,1561, 1066,1496);
        cambridge.addRoad(r);
        nottingham.addRoad(r);
        allRoads.add(r);

        // cambridge, norwich 
        r = new Road(cambridge, norwich, Color.RED);
        r.addRect(1183,1652, 1253,1664, 1249,1687, 1180,1674);
        r.addRect(1258,1665, 1325,1643, 1333,1663, 1266,1685);
        cambridge.addRoad(r);
        norwich.addRoad(r);
        allRoads.add(r);

        // norwich, nottingham
        r = new Road(norwich, nottingham, Color.WHITE);
        r.addRect(1278,1575, 1338,1614, 1326,1633, 1265,1594);
        r.addRect(1214,1534, 1273,1572, 1261,1592, 1201,1553);
        r.addRect(1149,1496, 1208,1532, 1197,1551, 1137,1512);
        r.addRect(1146,1491, 1134,1511, 1073,1472, 1087,1453);
        norwich.addRoad(r);
        nottingham.addRoad(r);
        allRoads.add(r);

        // norwich, hull
        r = new Road(norwich, hull, Color.GRAY);
        r.addRect(1291,1551, 1352,1590, 1339,1608, 1280,1570);
        r.addRect(1238,1498, 1288,1549, 1273,1564, 1222,1514);
        r.addRect(1219,1424, 1235,1492, 1214,1500, 1196,1431);
        r.addRect(1223,1348, 1219,1421, 1194,1419, 1201,1348);
        norwich.addRoad(r);
        hull.addRoad(r);
        allRoads.add(r);

        // birmingham, manchester
        r = new Road(birmingham, manchester, Color.BLACK);
        r.addRect(916,1384, 939,1390, 924,1459, 901,1454);
        r.addRect(934,1311, 955,1315, 941,1385, 917,1381);
        birmingham.addRoad(r);
        manchester.addRoad(r);
        allRoads.add(r);

        r = new Road(birmingham, manchester, Color.YELLOW);
        r.addRect(943,1392, 965,1397, 950,1466, 928,1459);
        r.addRect(960,1317, 983,1322, 967,1393, 944,1386);
        birmingham.addRoad(r);
        manchester.addRoad(r);
        allRoads.add(r);

        // birmingham, nottingham
        r = new Road(birmingham, nottingham, Color.GRAY);
        r.addRect(943,1475, 1008,1447, 1018,1468, 952,1497);
        birmingham.addRoad(r);
        nottingham.addRoad(r);
        allRoads.add(r);

        // nottingham, leeds
        r = new Road(nottingham, leeds, Color.PINK);
        r.addRect(1076,1335, 1063,1404, 1041,1400, 1055,1330);
        r.addRect(1091,1261, 1078,1331, 1055,1327, 1068,1256);
        nottingham.addRoad(r);
        leeds.addRoad(r);
        allRoads.add(r);

        // nottingham, hull
        r = new Road(nottingham, hull, Color.BLACK);
        r.addRect(1120,1369, 1136,1387, 1081,1431, 1067,1414);
        r.addRect(1181,1321, 1195,1339, 1140,1385, 1125,1367);
        nottingham.addRoad(r);
        hull.addRoad(r);
        allRoads.add(r);

        //manchester
        //manchester, liverpool
        r = new Road(manchester, liverpool, Color.ORANGE);
        r.addRect(882,1202,  944,1237,  933,1258,  871,1221);
        manchester.addRoad(r);
        liverpool.addRoad(r);
        allRoads.add(r);

        r = new Road(manchester, liverpool, Color.PINK);
        r.addRect(868,1226,  930,1261,  918,1282,  857,1245);
        manchester.addRoad(r);
        liverpool.addRoad(r);
        allRoads.add(r);

        //manchester, leeds
        r = new Road(manchester, leeds, Color.RED);
        r.addRect(1037,1209,  1049,1227,  989,1267,  977,1248);
        manchester.addRoad(r);
        leeds.addRoad(r);
        allRoads.add(r);

        r = new Road(manchester, leeds, Color.BLUE);
        r.addRect(1051,1232,  1063,1251,  1004,1289,  992,1270);
        manchester.addRoad(r);
        leeds.addRoad(r);
        allRoads.add(r);

        //hull
        //hull, leeds
        r = new Road(hull, leeds, Color.YELLOW);
        r.addRect(1126,1229,  1186,1270,  1174,1289,  1113,1249);
        hull.addRoad(r);
        leeds.addRoad(r);
        allRoads.add(r);

        //liverpool
        //liverpool, barrow
        r = new Road(liverpool, barrow, Color.GRAY, 1);
        r.addRect(902,1116,  877,1172,  858,1164,  884,1108);
        liverpool.addRoad(r);
        barrow.addRoad(r);
        allRoads.add(r);

        //liverpool, leeds
        r = new Road(liverpool, leeds, Color.BLACK);
        r.addRect(886,1174,  959,1174,  959,1199,  886,1199);
        r.addRect(962,1174,  1035,1176,  1034,1198,  962,1198);
        liverpool.addRoad(r);
        leeds.addRoad(r);
        allRoads.add(r);

        //leeds
        //leeds,barrow
        r = new Road(leeds,barrow, Color.GREEN);
        r.addRect(1028,1112,  1069,1170,  1050,1183,  1009,1125);
        r.addRect(948,1073,  1014,1102,  1005,1122,  939,1095);
        leeds.addRoad(r);
        barrow.addRoad(r);
        allRoads.add(r);        

        // Add all cities
        cities.add(dublin);
        cities.add(dundalk);
        cities.add(belfast);
        cities.add(londonderry);
        cities.add(sligo);
        cities.add(galway);
        cities.add(limerick);
        cities.add(cork);
        cities.add(rosslare);
        cities.add(tullamore);

        cities.add(penzance);
        cities.add(plymouth);
        cities.add(southampton);
        cities.add(brighton);
        cities.add(dover);
        cities.add(london);
        cities.add(ipswich);
        cities.add(norwich);
        cities.add(hull);
        cities.add(newcastle);
        cities.add(carlisle);
        cities.add(barrow);
        cities.add(liverpool);
        cities.add(leeds);
        cities.add(manchester);
        cities.add(nottingham);
        cities.add(birmingham);
        cities.add(northampton);
        cities.add(cambridge);
        cities.add(reading);
        cities.add(bristol);

        cities.add(stornoway);
        cities.add(ullapool);
        cities.add(wick);
        cities.add(inverness);
        cities.add(fortWilliam);
        cities.add(aberdeen);
        cities.add(dundee);
        cities.add(glasgow);
        cities.add(edinburgh);
        cities.add(stranraer);

        cities.add(holyhead);
        cities.add(aberystwyth);
        cities.add(carmarthen);
        cities.add(llandrindodWells);
        cities.add(cardiff);

        cities.add(france1);
        cities.add(france2);

        cities.add(newYork);

        // Set up the board
        adjList = new City[cities.size()];
        for(int i = 0; i < cities.size(); i++)
            adjList[i] = cities.get(i);
        addMouseListener(this);
        addMouseMotionListener(this);

        scale(d);
    }

    /**
     * Adds the Road to both the starting and destination Cities.
     * 
     * @param   a Starting city.
     * @param   b Destination city.
     * @param   c The color of the Road.
     */
    private void addRoad(City a, City b, Color c){
        Road road = new Road(a,b,c);
        adjList[indexOf(a)].addRoad(road);
        adjList[indexOf(b)].addRoad(road);
    }

    /**
     * Helper method that returns the index of a certain City in 
     * City[] adjList.
     * 
     * @param   city The City being searched for.
     * 
     * @return  The index of the City, or -1 if the City is not found.
     */
    private int indexOf(City city){
        for(int i = 0; i < adjList.length; i++){
            if(adjList[i] == city) return i;
        }
        return -1;
    }

    /**
     * Method that scales the board to the size of the screen.
     * 
     * @param   d The dimension to scale to
     */
    public void scale(Dimension d){
        for(City city : adjList){
            city.setX((int)(city.getX() * scaledX));
            city.setY((int)(city.getY() * scaledY));
        }

        for(Road r : allRoads){
            for(Polygon p : r.rects){
                p.xpoints = scaleArr(p.xpoints, scaledX);
                p.ypoints = scaleArr(p.ypoints, scaledY);
            }
        }
    }

    /**
     * Method that returns a scaled version of int[] toScale by multiplying
     * every value by num.
     * 
     * @param   toScale Given array.
     * @param   num The number to multiply with each value in 
     * toScale.
     * 
     * @return  A scaled version of int[] toScale.
     */
    private int[] scaleArr(int[] toScale, double num){
        int[] toRet = Arrays.copyOf(toScale, toScale.length);
        for(int i = 0; i < toScale.length; i++){
            toRet[i] = (int) (toScale[i] * num);
        }
        return toRet;
    }

    /**
     * Method that paints the board.
     * 
     * @param   g The Graphics object to be painted to.
     */
    protected void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor( Color.BLUE );

        g2d.scale(scaledX, scaledY);
        g2d.drawImage(image, 0, 0, this);
        g2d.scale(1/scaledX, 1/scaledY);

        if(cityLineA != null)
            cityLineA.highlight(g2d, Color.YELLOW);
        if(cityLineB != null)
            cityLineB.highlight(g2d, Color.YELLOW);
        if(cityLineC != null)
            cityLineC.highlight(g2d, Color.YELLOW);
        for(City c : toHighlight){
            c.highlight(g2d,Color.YELLOW);
        }
        if(cityOn != null)
            cityOn.highlight(g2d, Color.YELLOW);
        if(roadOn != null)
            roadOn.highlight(g2d);

        for(Road r : allRoads){
            if(r.occupying != null){
                r.highlightClaimed(g2d);
            }
        }

        if(cityLineA != null && cityLineB != null){
            g2d.setColor( Color.BLUE );
            g2d.setStroke(new BasicStroke(3));
            g2d.draw(new Line2D.Float(cityLineA.getX(), cityLineA.getY(), 
                    cityLineB.getX(), cityLineB.getY()));
        }
        if(cityLineC != null && cityLineB != null){
            g2d.setColor( Color.BLUE );
            g2d.setStroke(new BasicStroke(3));
            g2d.draw(new Line2D.Float(cityLineA.getX(), cityLineA.getY(),
                    cityLineC.getX(), cityLineC.getY()));
        }
    }

    /**
     * Occurs when the mouse button is clicked on a component, gets
     * either a City or Road depending on what is clicked.
     * 
     * @param   e The MouseEvent object
     */
    public void mouseClicked(MouseEvent e){
        if(!enabled) return;
        Point p = e.getPoint();

        if(roadOn != null){
            // user clicked on a road
            if(!doubleRoutes){
                if(doubleRoute(roadOn) != null){
                    if(doubleRoute(roadOn).occupying.size() == 0)
                        roadClicked = roadOn;
                }
                else{
                    roadClicked = roadOn;
                }
            }
            else{
                roadClicked = roadOn;
            }
        }
        repaint();
        //e.consume();
    }

    /**
     * Cases that occur when the mouse button is pressed on a component.
     * 
     * @param   e The MouseEvent object
     */
    public void mousePressed(MouseEvent e){
        //e.consume();
    }

    /**
     * Occurs when the mouse button is clicked on a component.
     * 
     * @param   e The MouseEvent object
     */
    public void mouseReleased(MouseEvent e){
        //e.consume();
    }

    /**
     * Occurs when the mouse enters a component.
     * 
     * @param   e The MouseEvent object
     */
    public void mouseEntered(MouseEvent e){
        //e.consume();
    }

    /**
     * Occurs when the mouse exits a component.
     * 
     * @param   e The MouseEvent object
     */
    public void mouseExited(MouseEvent e){
        //e.consume();
    }

    /**
     * Occurs when a mouse button is pressed on a component and dragged.
     * 
     * @param   e The MouseEvent object
     */
    public void mouseMoved(MouseEvent e){
        if(!enabled) return;
        boolean found = false;
        for(City c : adjList){
            if(c.contains(e.getPoint()) && !found){
                cityOn = c;
                found = true;
            }
            else if(!found){
                cityOn = null;
            }
        }

        found = false;
        for(Road r : allRoads){
            if(r.contains(e.getPoint()) && !found){
                roadOn = r;
                found = true;
            }
            else if(!found){
                roadOn = null;
            }
        }
        repaint();
        //e.consume();
    }

    /**
     * Occurs when a mouse is dragged into a component but nothing is
     * pressed.
     * 
     * @param   e The MouseEvent object
     */
    public void mouseDragged(MouseEvent e){
        //e.consume();
    }

    /**
     * If two Cities have two different Roads connecting them, takes the
     * given Road and returns the other Road.
     * 
     * @param   r The inputted Road.
     * 
     * @return  The other Road if there is one, null otherwise.
     */
    public Road doubleRoute(Road r){
        City a = r.cityA;
        City b = r.cityB;
        for(Road road : allRoads){
            if(road == r) continue;
            if((road.cityA == a && road.cityB == b)
            || (road.cityA == b && road.cityB == a))
                return road;
        }
        return null;
    }

    /**
     * Method that calls recIsConnected.
     * 
     * @param   a Starting City.
     * @param   b Destination City.
     * @param   p The Player whose paths are being searched.
     * 
     * @return  Whether a is connected to b
     */
    public boolean isConnected(City a, City b, Player p){
        for(Road r : allRoads)
            r.visited = false;
        return recIsConnected(a,b,p);
    }

    /**
     * Recursive method that takes in two Cities and says whether or not 
     * they are connected by one path.
     * 
     * @param   a Starting City.
     * @param   b Destination City.
     * @param   p The Player whose paths are being searched.
     * 
     * @return  True if the cities are connected by p, false otherwise.
     */
    public boolean recIsConnected(City a, City b, Player p){
        if(a == b) return true;
        for(int i = 0; i < a.roads.size(); i++){
            Road road = a.roads.get(i);
            if(!road.visited && road.occupying.contains(p)){
                road.visited = true;
                City adjCity = road.cityA == a ? road.cityB : road.cityA;
                if(recIsConnected(adjCity, b, p))
                    return true;
                road.visited = false;
            }
        }
        return false;
    }

    /**
     * Method that finds the longest path between 2 Cities.
     * 
     * @param   a Starting City.
     * @param   b Destination City.
     * @param   p The Player whose paths are being searched.
     */
    public int longestPath(City a, City b, Player p){
        int max = -1;
        for(int i = 0; i < a.roads.size(); i++){
            Road road = a.roads.get(i);
            if(!road.visited && road.occupying.contains(p)){
                road.visited = true;
                City adjCity = road.cityA == a ? road.cityB : road.cityA;
                int pathLength = longestPath(adjCity, b, p);
                if(pathLength != -1){
                    pathLength += road.length();
                    if(pathLength > max)
                        max = pathLength;
                }
                road.visited = false;
            }
        }
        if(max == -1 && a == b)
            max = 0;
        return max;
    }

    /**
     * Method that finds the longest path on the entire Board for a certain
     * player.
     * 
     * @param   p The Player whose paths are being searched.
     */
    public int longestPath(Player p){
        int max = -1;
        for(Road r : allRoads)
            r.visited = false;

        for(City city1 : adjList){
            for(City city2 : adjList){
                for(Road r : allRoads)
                    r.visited = false;
                int pathLength = longestPath(city1, city2, p);
                if(pathLength > max){
                    max = pathLength;
                }

            }
        }
        return max;
    }

    // Note: not case sensitive
    /**
     * Method that returns a certain City.
     * 
     * @param   name The City to be returned.
     */
    public City getCity(String name){
        for(City city : adjList){
            if(city.getName().replaceAll(" ", "").toLowerCase().equals
            (name.toLowerCase().replaceAll(" ", "")))
                return city;
        }
        return null;
    }

    /**
     * Gives you the second France.
     * 
     * @return  The second France on the board.
     */
    public City getSecondFrance(){
        int count = 0;
        for(City city : adjList){
            if(city.getName().equals("France")){
                count++;
                if(count == 2)
                    return city;
            }
        }
        return null;
    }

}
