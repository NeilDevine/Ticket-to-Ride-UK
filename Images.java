///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
/**
 * This is an enum for all images related to our version of the Ticket to 
 * Ride board game.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public enum Images
{
    red_train,
    blue_train,
    green_train,
    black_train,
    orange_train,
    pink_train,
    white_train,
    yellow_train,
    locamotive_train,

    boiler_lagging,
    booster,
    diesel_power,
    double_heading,
    equalising_beam,
    ireland_france_concession,
    mechanical_stoker,
    propellers,
    right_of_way,
    risky_contracts,
    scotland_concession,
    steam_turbine,
    superheated_steam_boiler,
    thermo_compressor,
    wales_concession,
    water_tenders,

    aberdeen_glasgow,
    galway_barrow,
    belfast_manchester,
    edinburgh_birmingham,
    penzance_london,
    ullapool_dundee,
    londonderry_dublin,
    aberystwyth_cardiff,
    nottingham_ipswich,
    manchester_london,
    stornoway_glasgow,
    limerick_cardiff,
    glasgow_manchester,
    wick_dundee,
    london_france,
    inverness_leeds,
    holyhead_cardiff,
    edinburgh_london,
    glasgow_france,
    liverpool_hull,
    birmingham_cambridge,
    southampton_london,
    leeds_london,
    liverpool_southampton,
    birmingham_london,
    cambridge_london,
    cardiff_london,
    manchester_norwich,
    londonderry_stranraer,
    cork_leeds,
    fortwilliam_edinburgh,
    liverpool_llandrindodWells,
    wick_edinburgh,
    rosslare_aberystwyth,
    rosslare_carmarthen,
    galway_dublin,
    stranraer_tullamore,
    belfast_dublin,
    sligo_holyhead,
    manchester_plymouth,
    newcastle_southampton,
    dublin_london,
    cardiff_reading,
    londonderry_birmingham,
    glasgow_dublin,
    dundalk_carlisle,
    inverness_belfast,
    stornoway_aberdeen,
    bristol_southampton,
    norwich_ipswich,
    plymouth_reading,
    dublin_cork,
    london_brighton,
    northampton_dover,
    leeds_france,
    leeds_manchester,
    newcastle_hull,

    train_card_back,
    dest_card,

    paul0( "png"),

    paul1( "png"),

    paul2( "png"),

    paul3( "png"),

    paul4( "png"),

    george0( "png"),

    george1( "png"),

    george2( "png"),

    george3( "png"),

    george4( "png"),

    john0( "png"),

    john1( "png"),

    john2( "png"),

    john3( "png"),

    john4( "png"),

    ringo0( "png"),

    ringo1( "png"),

    ringo2( "png"),

    ringo3( "png"),

    ringo4( "png"),

    check("png");

    Image image;

    /**
     * Checks to see whether or not the image exists. If it does,
     * it gets rid of the image to free memory with flush.
     */
    Images(){
        try{
            image = ImageIO.read(new File("photos/" + this + ".jpg"));
        }catch(IOException e){
            System.out.println("File not found");
        }finally{
            if(image != null)
                image.flush();
        }
    }

    /**
     * Checks to see whether or not the image exists. If it does,
     * it gets rid of the image to free memory with flush.
     * 
     * @param end Used to signify what kind of a file type is being
     * read.
     */
    Images(String end){
        try{
            image = ImageIO.read(new File("photos/" + this + "." +
                    end));
        }catch(IOException e){
            System.out.println("File not found");
        }finally{
            if(image != null)
                image.flush();
        }
    }

    /**
     * Goes through images and frees memory space
     */
    public static void close(){
        Images[] images = values();
        for(Images i : images){
            if(i.image != null){
                i.image.flush();
                i.image = null;
            }
        }
    }
}
