package com.spazomatic;

import java.io.IOException;

import javax.annotation.Generated;
import javax.swing.text.MaskFormatter;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;



public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
    	
    	generateNabsta();
    }
    private static void generateNabsta() throws IOException, Exception{
        Schema schema = new Schema(1, "com.spazomatic.nabsta.db");
        schema.setDefaultJavaPackageTest("com.spazomatic.nabsta.db.dao.test");
        schema.setDefaultJavaPackageDao("com.spazomatic.nabsta.db.dao");
        
        Entity image = schema.addEntity("Image");
        image.addIdProperty();
        image.addStringProperty("name");
        image.addStringProperty("file_name");        

        Entity artist = schema.addEntity("Artist");
        artist.addIdProperty();
        artist.addStringProperty("name");
        Property artist_image_id_property = artist.addLongProperty("image_id_fk").getProperty();
        artist.addToOne(image, artist_image_id_property);
        
        Entity track = schema.addEntity("Track");
        track.addIdProperty();
        track.addStringProperty("name");
        track.addStringProperty("file_name");
        track.addStringProperty("bitmap_file_name");
        track.addLongProperty("is_mixed_track");
        Property track_song_id_property = track.addLongProperty("song_id_fk").getProperty();
        Property track_artist_id_property = track.addLongProperty("artist_id_fk").getProperty();
        Property track_image_id_property = track.addLongProperty("image_id_fk").getProperty();
        track.addToOne(artist, track_artist_id_property);
        track.addToOne(image, track_image_id_property);
        
        
        Entity song = schema.addEntity("Song");
        song.addIdProperty().getProperty();
        song.addStringProperty("name");
        song.addStringProperty("dir_name");
        song.addDoubleProperty("length");
        song.addDateProperty("time_stamp");
        Property song_artist_id_property = song.addLongProperty("artist_id_fk").getProperty();
        Property song_master_track_id_property = song.addLongProperty("master_track_id_fk").getProperty();
        Property song_image_id_property = song.addLongProperty("image_id_fk").getProperty();
        song.addToOne(artist, song_artist_id_property);        
        song.addToOne(track, song_master_track_id_property);
        song.addToOne(image, song_image_id_property);
        
        ToMany artistTracks = artist.addToMany(track, track_artist_id_property);
        artistTracks.setName("tracks");
        
        ToMany artistSongs = artist.addToMany(song, song_artist_id_property);
        artistSongs.setName("songs");
        
        ToMany songTracks = song.addToMany(track, track_song_id_property);
        songTracks.setName("tracks");
        
        new DaoGenerator().generateAll(schema, "/Users/samuelsegal/dev/nabsta");    	
    }
    private void generateBoozentory() throws IOException, Exception{
        Schema schema = new Schema(1, "com.spazomatic.boozentory.dao");
        schema.setDefaultJavaPackageTest("com.spazomatic.boozentory.dao.test");
        schema.setDefaultJavaPackageDao("com.spazomatic.boozentory.dao.dao");
        
        Entity liquorType = schema.addEntity("LiquorType");
        liquorType.addIdProperty();
        liquorType.addStringProperty("type");
        
        Entity bar = schema.addEntity("Bar");
        bar.addIdProperty();
        bar.addStringProperty("pic");
        bar.addStringProperty("logo");
        bar.addStringProperty("name");
        bar.addStringProperty("description");
        
        Entity liquor = schema.addEntity("Liquor");
        liquor.addIdProperty();
        liquor.addStringProperty("name");
        liquor.addStringProperty("pic");
        liquor.addDoubleProperty("price");
        Property liquorTypeIdProperty = liquor.addLongProperty("fkLiquorTypeIdType").getProperty();
        liquor.addToOne(liquorType, liquorTypeIdProperty);
        Property barIdProperty = liquor.addLongProperty("fkLiquorBarIdBar").getProperty();
        liquor.addToOne(bar, barIdProperty);
        
        Entity inventory = schema.addEntity("Inventory");
        inventory.addIdProperty();
        inventory.addDateProperty("date");
        inventory.addStringProperty("name");
        Property invBarIdProperty = inventory.addLongProperty("fkInventoryBarIdBar").getProperty();
        inventory.addToOne(bar, invBarIdProperty);
        
        Entity inventoryRecord = schema.addEntity("InventoryRecord");
        inventoryRecord.addIdProperty();
        inventoryRecord.addDoubleProperty("qty");
        inventoryRecord.addDoubleProperty("price");
        Property liquorIdProperty = inventoryRecord.addLongProperty("fkLiquorIdLiquor").getProperty();
        inventoryRecord.addToOne(liquor, liquorIdProperty);
        Property inventoryIdProperty = inventoryRecord.addLongProperty("fkInventoryIdInventory").getProperty();
        inventoryRecord.addToOne(inventory, inventoryIdProperty); 
        
        new DaoGenerator().generateAll(schema, "/Users/samuelsegal/Documents/workspace/Boozentory/app/src/main/java");    	
    }
}
